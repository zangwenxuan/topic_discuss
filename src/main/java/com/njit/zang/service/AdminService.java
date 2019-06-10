package com.njit.zang.service;

import com.njit.zang.dto.FeedManager;
import com.njit.zang.dto.UserManager;
import com.njit.zang.mapper.SendContentDao;
import com.njit.zang.mapper.UserDao;
import com.njit.zang.model.SendContent;
import com.njit.zang.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2019/5/27.
 */
@Service
public class AdminService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private SendContentDao sendContentDao;

    public List<UserManager> selectUserManagerList(){
        return userDao.selectUserManagerList();
    }

    public List<FeedManager> selectFeedManagerList(){
        return sendContentDao.selectFeedDetails();
    }

    public int deleteUser(User user){
        return userDao.deleteUser(user.getUid());
    }

    public int deleteFeed(SendContent sendContent){
        return sendContentDao.deleteFeed(sendContent.getFeedId());
    }

}
