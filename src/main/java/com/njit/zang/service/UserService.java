package com.njit.zang.service;

import com.njit.zang.mapper.UserDao;
import com.njit.zang.model.User;
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

    public User selectByPrimaryKey(String id) {
        return userDao.selectByPrimaryKey(id).setPassword("");
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
        List<UserSendContent> sendContentList = new ArrayList();
        feedList.stream().forEach(f->{
            sendContentList.add(userDao.selectContentByFeedId(f));
        });
        return sendContentList;
    }

    public List<UserSendContent> selectContentByUid(String uid){
        return userDao.selectContentByUid(uid);
    }

    public boolean checkName(String name){
        if(userDao.checkName(name) != null)
            return true;
        return false;
    }

    public boolean checkEmail(String email){
        if(userDao.checkEmail(email) != null)
            return true;
        return false;
    }

    public User insert(User user){
        user.setUid(UUID.randomUUID().toString().substring(24));
        user.setPassword(MD5Utils.Encode(user.getPassword()));
        userDao.insert(user);
        user.setPassword("");
        return user;
    }

}
