package com.njit.zang.service;

import com.njit.zang.mapper.FeedNoticeDao;
import com.njit.zang.mapper.SubscribeNoticeDao;
import com.njit.zang.model.FeedNotice;
import com.njit.zang.model.SubscribeNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2019/4/1.
 */
@Service
public class NoticeService {

    @Autowired
    private FeedNoticeDao feedNoticeDao;

    @Autowired
    private SubscribeNoticeDao subscribeNoticeDao;

    public List selectUnreadFeedNotice(String uid){
        return feedNoticeDao.selectUnreadList(uid);
    }

    public List selectHistoryFeedNotice(String uid){
        return feedNoticeDao.selectHistoryList(uid);
    }

    public List selectUnreadSubscribeNotice(String uid){
        return subscribeNoticeDao.selectUnreadList(uid);
    }

    public List selectHistorySubscribeNotice(String uid){
        return subscribeNoticeDao.selectHistoryList(uid);
    }

    public void insertFeedNotice(FeedNotice feedNotice){
        feedNotice.setFnId(UUID.randomUUID().toString().substring(24))
                .setTime(System.currentTimeMillis());
        feedNoticeDao.insert(feedNotice);
    }

    public void insertSubscribeNotice(SubscribeNotice subscribeNotice){
        subscribeNotice.setSnId(UUID.randomUUID().toString().substring(24));
        subscribeNoticeDao.insert(subscribeNotice);
    }

    public void changeSubscribeStatus(String snId){
        subscribeNoticeDao.updateByPrimaryKey(snId);
    }

    public void changeFeedStatus(String fnId){
        feedNoticeDao.updateByPrimaryKey(fnId);
    }

}
