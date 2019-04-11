package com.njit.zang.service;

import com.njit.zang.mapper.CommentReplyDao;
import com.njit.zang.model.CommentReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2019/3/22.
 */
@Service
public class CommentReplyService {
    @Autowired
    private CommentReplyDao commentReplyDao;

    public int insert(CommentReply record){
        return commentReplyDao.insert(record);
    }

    public String selectFeedId(String commentReplyId){
        return commentReplyDao.selectFeedId(commentReplyId);
    }
}
