package com.njit.zang.service;

import com.njit.zang.dto.FeedItem;
import com.njit.zang.mapper.SendContentDao;
import com.njit.zang.model.ContentDetails;
import com.njit.zang.model.SendContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/3/20.
 */
@Service
public class SendContentService {
    @Autowired
    SendContentDao sendContentDao;

    public ContentDetails selectContentDetails(String feedId){
            return sendContentDao.selectContentDetails(feedId);
    }

    public FeedItem insert(SendContent sendContent){
        sendContent.setReleaseTime(System.currentTimeMillis());
        sendContentDao.insert(sendContent);
        return selectFeedById(sendContent.getFeedId());
    }

    public FeedItem selectFeedById(String feedId){
        return sendContentDao.selectFeedItemByFeedId(feedId);
    }

    public int countFeed(String uid){
        return sendContentDao.countByUid(uid);
    }

    public void deleteFeed(String feedId){
        sendContentDao.deleteFeed(feedId);
    }

    public String selectUidByFeedId(String feedId){
        return sendContentDao.selectUidByFeedId(feedId);
    }

    public String selectContentByFeedId(String feedId){
        return sendContentDao.selectByPrimaryKey(feedId).getContent();
    }

    public List<String> selectFeedListByUid(String uid){
        return sendContentDao.selectFeedListByUid(uid);
    }
}
