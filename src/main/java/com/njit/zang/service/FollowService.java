package com.njit.zang.service;

import com.njit.zang.dto.Result;
import com.njit.zang.mapper.FollowDao;
import com.njit.zang.model.Follow;
import com.njit.zang.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2019/3/29.
 */
@Service
public class FollowService {
    @Autowired
    FollowDao followDao;

    public void insert(String follower,String master){
        Follow follow = new Follow().setFollower(follower).setMaster(master);
        followDao.insert(follow);
    }

    public void delete(String follower,String master){
        followDao.delete(follower,master);
    }

    public boolean selectByFollow(String follower,String master){
        if(followDao.selectByFollow(follower,master)!=null){
            return true;
        }
        return false;
    }

    public List<User> selectByFollower(String follower){
        return followDao.selectByFollower(follower);
    }

    public List<User> selectByMaster(String master){
        return followDao.selectByMaster(master);
    }

    public int queryMasterCount(String followerId){
        List l = selectByFollower(followerId);
        int length = 0;
        if(l!=null) {
            length = l.size();
        }
        return length;
    }

    public int queryFollowerCount(String masterId){
        List l =selectByMaster(masterId);
        int length = 0;
        if(l!=null) {
            length = l.size();
        }
        return length;
    }
}
