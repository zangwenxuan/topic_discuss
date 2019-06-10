package com.njit.zang.service;

import com.njit.zang.dto.FeedItem;
import com.njit.zang.dto.UserCard;
import com.njit.zang.mapper.UserDao;
import com.njit.zang.mapper.UserHasThemeDao;
import com.njit.zang.model.User;
import com.njit.zang.model.UserHasTheme;
import com.njit.zang.model.UserSendContent;
import com.njit.zang.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2019/3/19.
 */
@Service
@Slf4j
public class UserService {
    @Autowired
    public UserDao userDao;

    @Autowired
    private UserHasThemeDao userHasThemeDao;

    public User selectByPrimaryKey(String id) {
        return userDao.selectByPrimaryKey(id);
    }

    public User selectByNickname(String name){
        User user =userDao.selectUserByName(name);
        return user;
    }

    public User selectByEmail(String email){
        User user =userDao.selectUserByEmail(email);
        return user;
    }

    public int deleteByPrimaryKey(String id){
        return userDao.deleteByPrimaryKey(id);
    }

    public List<User> selectAll(){
        return userDao.selectAllUsers();
    }

    public List<UserSendContent> selectAllContent(){
        return userDao.selectAllContent();
    }

    public List<UserSendContent> selectContentByFeedList(List<String> feedList){
        if(feedList == null || feedList.size() == 0){
            return null;
        }
        List<UserSendContent> sendContentList = new ArrayList();
        feedList.stream().forEach(f->{
            UserSendContent u = userDao.selectContentByFeedId(f);
            if(u != null) {
                sendContentList.add(u);
            }
        });
        return sendContentList;
    }

    public List<UserSendContent> selectContentByUid(String uid){
        return userDao.selectContentByUid(uid);
    }

    public List<FeedItem> selectFeedItemByUid(String uid){
        return userDao.selectFeedItemByUid(uid);
    }

    public boolean checkName(String name){
        if(userDao.checkName(name) != null) {
            return true;
        }
        return false;
    }

    public boolean checkEmail(String email){
        if(userDao.checkEmail(email) != null) {
            return true;
        }
        return false;
    }

    public User insert(User user){
        user.setUid(UUID.randomUUID().toString().substring(24));
        user.setPassword(MD5Utils.Encode(user.getPassword()));
        userDao.insert(user);
        return user;
    }

    public void insertUserHasTheme(UserHasTheme u){
        userHasThemeDao.insert(u);
    }
    public void insertUserHasTheme(String uid,String themeName){
        UserHasTheme userHasTheme = new UserHasTheme();
        userHasTheme.setUserUid(uid)
                .setThemeName(themeName);
        userHasThemeDao.insert(userHasTheme);
    }

    public void deleteUserHasTheme(String uid,String themeName){
        userHasThemeDao.deleteUserHasTheme(uid,themeName);
    }
    public void deleteUserHasTheme(UserHasTheme u){
        userHasThemeDao.deleteUserHasTheme(u.getUserUid(),u.getThemeName());
    }

    public List<String> selectThemeList(String uid){
        return userHasThemeDao.selectThemeList(uid);
    }
    public void update(User u){
        if(u.getCover()!=null){
            userDao.updateCover(u.getCover(),u.getUid());
        }
        if(u.getSignature() != null){
            userDao.updateSignature(u.getSignature(),u.getUid());
        }
        if(u.getAvatar() != null) {
            userDao.updateAvatar(u.getAvatar(),u.getUid());
        }
        if(u.getPassword() != null) {
            u.setPassword(MD5Utils.Encode(u.getPassword()));
            userDao.updatePassword(u.getPassword(),u.getUid());
        }
        if(u.getEmail() != null){
            userDao.updateEmail(u.getEmail(),u.getUid());
        }
    }

    public List<User> searchUser(String nickname){
        return userDao.searchUser("%"+nickname+"%");
    }

    public UserCard queryUserCard(String uid){
        return userDao.queryUserCard(uid);
    }

}
