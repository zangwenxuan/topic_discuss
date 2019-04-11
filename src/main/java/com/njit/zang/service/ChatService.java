package com.njit.zang.service;

import com.njit.zang.mapper.ChatDao;
import com.njit.zang.model.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2019/4/3.
 */
@Service
public class ChatService {
    @Autowired
    private ChatDao chatDao;

    public void insert(Chat chat){
        chatDao.insert(chat);
    }

    public List<Chat> select(String fromUserId,String toUserId){
        return chatDao.selectMessage(fromUserId,toUserId);
    }

    public List selectUserList(String uid){
        return chatDao.selectUserList(uid);
    }

    public List selectUnreadChatList(String fromUserId,String toUserId){
        return chatDao.selectUnreadChatList(fromUserId,toUserId);
    }

    public Chat selectHistoryChat(String fromUserId,String toUserId){
        return chatDao.selectHistoryChat(fromUserId,toUserId);
    }

    public void updateChatNotice(String fromUserId,String toUserId){
        chatDao.updateChatNotice(fromUserId,toUserId);
    }

    public void clearReadStatusByUid(String uid){
        chatDao.clearReadStatusByUid(uid);
    }
}
