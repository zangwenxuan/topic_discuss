package com.njit.zang.service;

import com.njit.zang.dto.MessageNum;
import com.njit.zang.mapper.CommentDao;
import com.njit.zang.mapper.CommentReplyDao;
import com.njit.zang.model.Comment;
import com.njit.zang.model.CommentReplyPro;
import com.njit.zang.model.CommentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/3/21.
 */
@Service
public class CommentService {
    @Autowired
    public CommentDao commentDao;

    @Autowired
    private CommentReplyDao commentReplyDao;

    public List<CommentUser> selectCommentByFeedId(String feedId){
        return commentDao.selectByFeedId(feedId);
    }

    public List<CommentReplyPro> selectProByCommentId(int commentId){
        return commentReplyDao.selectProByCommentId(commentId);
    }

    public int insert(Comment record){
        return commentDao.insert(record);
    }

    public MessageNum countMessage(String feedId){
        MessageNum m = new MessageNum();
        m.setFeedId(feedId);
        m.setNum(commentDao.countByFeedId(feedId)+commentReplyDao.countByFeedId(feedId));
        return m;
    }
}
