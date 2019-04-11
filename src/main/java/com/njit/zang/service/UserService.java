package com.njit.zang.service;

import com.njit.zang.mapper.UserDao;
import com.njit.zang.model.User;
import com.njit.zang.model.UserSendContent;
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

    public User selectByPrimaryKey(String id){
        return userDao.selectByPrimaryKey(id);
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

    public int insert(User user){
        user.setUid(UUID.randomUUID().toString().substring(24));
        return userDao.insert(user);
    }

}
