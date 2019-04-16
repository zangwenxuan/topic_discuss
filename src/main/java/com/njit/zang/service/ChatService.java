package com.njit.zang.service;

import com.njit.zang.mapper.ChatDao;
import com.njit.zang.mapper.ChatNotesDao;
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

    @Autowired
    private ChatNotesDao chatNotesDao;

    public void insert(Chat chat){
        chatDao.insert(chat);
    }

    public List<Chat> select(String fromUserId,String toUserId){
        return chatDao.selectMessage(fromUserId,toUserId);
    }

    public List selectUserList(String uid){
        return chatDao.selectUserList(uid);
    }

    //查找fromUserId发送到toUserId的未读聊天记录
    public List selectUnreadChatList(String fromUserId,String toUserId){
        return chatDao.selectUnreadChatList(fromUserId,toUserId);
    }

    //查找fromUserId发送到toUserId的已读聊天记录的第一条
    public Chat selectHistoryChat(String fromUserId,String toUserId){
        return chatDao.selectHistoryChat(fromUserId,toUserId);
    }

    //查找fromUserId发送到toUserId的聊天记录的第一条
    public Chat selectChat(String fromUserId,String toUserId){
        return chatDao.selectChat(fromUserId,toUserId);
    }

    //更新所有从fromUserId发送到toUserId的聊天记录的状态为已读
    public void updateChatNotice(String fromUserId,String toUserId){
        chatDao.updateChatNotice(fromUserId,toUserId);
    }

    //改变所有发送到该用户的聊天记录的状态为已读
    public void clearReadStatusByUid(String uid){
        chatDao.clearReadStatusByUid(uid);
    }

    //添加聊天房间记录
    public void insertChatNotes(String fromUserId,String toUserId){
        chatNotesDao.insertIgnore(fromUserId,toUserId);
    }

    //删除聊天房间记录
    public void deleteChatNotes(String fromUserId,String toUserId){
        chatNotesDao.delete(fromUserId,toUserId);
    }

    //删除所有聊天房间记录
    public void deleteAllChatNotes(String uid){
        chatNotesDao.deleteAllChatNotes(uid);
    }

    //获取该用户的所有聊天房间记录
    public List selectNoteList(String uid){
        return chatNotesDao.selectNoteList(uid);
    }
}
