package com.njit.zang.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Ordering;
import com.njit.zang.Socket.WebSocketServer;
import com.njit.zang.annotation.UserLoginToken;
import com.njit.zang.dto.ChatCompare;
import com.njit.zang.dto.ChatDto;
import com.njit.zang.dto.ChatDtoCompare;
import com.njit.zang.dto.Result;
import com.njit.zang.model.Chat;
import com.njit.zang.model.User;
import com.njit.zang.service.ChatService;
import com.njit.zang.service.NoticeService;
import com.njit.zang.service.UserService;
import com.sun.org.apache.xerces.internal.dom.PSVIAttrNSImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Administrator on 2019/4/3.
 */
@RequestMapping("/chat")
@RestController()
@Slf4j
public class ChatMessageController {
    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @UserLoginToken
    @GetMapping("/clearReadStatus")
    public Result clearReadStatus(HttpSession session){
        String uid = (String) session.getAttribute("uid");
        chatService.clearReadStatusByUid(uid);
        return Result.builder().code(Result.SUCCESS_CODE).build();
    }

    @UserLoginToken
    @DeleteMapping("clearChatNotes")
    public Result clearChatNotes(HttpSession session){
        String uid = (String) session.getAttribute("uid");
        chatService.deleteAllChatNotes(uid);
        return Result.builder().code(Result.SUCCESS_CODE).build();
    }

    @UserLoginToken
    @DeleteMapping("/deleteChatNote")
    public Result deleteChatNote(@RequestBody Map m,HttpSession session){
        String fromUserId = (String) session.getAttribute("uid");
        String toUserId = (String) m.get("uid");
        chatService.deleteChatNotes(fromUserId,toUserId);
        return Result.builder().code(Result.SUCCESS_CODE).build();
    }

    @UserLoginToken
    @PostMapping("/sendMsg")
    public Result sendMessage(@RequestBody Chat chat,HttpSession session){
        String uid = (String) session.getAttribute("uid");
        User user = userService.selectByPrimaryKey(uid);
        log.info(user.toString());
        chat.setFromUserId(uid);
        chat.setTime(System.currentTimeMillis()).setRead(-1);

        ChatDto chatDto = new ChatDto();
        chatDto.setAvatar(user.getAvatar())
                .setNickname(user.getNickname())
                .setTime(System.currentTimeMillis())
                .setContent(chat.getMessage())
                .setUid(uid);

        try {
            if(WebSocketServer.sendInfo(JSONObject.toJSONString(chatDto), chat.getToUserId()+uid) == -1){
                WebSocketServer.sendInfo(JSONObject.toJSONString(chatDto),chat.getToUserId());
                chat.setRead(0);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        chatService.insert(chat);
        chatService.insertChatNotes(chat.getFromUserId(),chat.getToUserId());
        chatService.insertChatNotes(chat.getToUserId(),chat.getFromUserId());

        Map m =  getALlMsg(chat.getToUserId(),uid);
        return Result.builder().code(Result.SUCCESS_CODE).res(m).build();
    }

    @UserLoginToken
    @GetMapping("/getMsg")
    public Result getMsg(@RequestParam("toUserId") String toUserId, HttpSession session){
        String uid = (String) session.getAttribute("uid");
        chatService.updateChatNotice(toUserId,uid);
        Map m = getALlMsg(toUserId,uid);
        return Result.builder().code(Result.SUCCESS_CODE).res(m).build();
    }

    @UserLoginToken
    @GetMapping("/getChatNotice")
    public Result getMsgNotice(HttpSession session){
        String uid = (String) session.getAttribute("uid");
        List<String> userList = chatService.selectNoteList(uid);
        List<ChatDto> chatNotice = new ArrayList<>();
        userList.stream().forEach(u->{
            chatNotice.add(getChatNotice(u,uid));
        });
        ChatDtoCompare chatDtoCompare = new ChatDtoCompare();
        Ordering<ChatDto> ordering = Ordering.from(chatDtoCompare);
        Collections.sort(chatNotice,ordering);
        return Result.builder().code(Result.SUCCESS_CODE).res(chatNotice).build();
    }

    @UserLoginToken
    @PutMapping("/updateChatStatus")
    public Result updateChatStatus(@RequestBody Map map,HttpSession session){
        String fromUserId = (String)map.get("netFriend");
        String toUserId = (String)session.getAttribute("uid");
        chatService.updateChatNotice(fromUserId,toUserId);
        return Result.builder().code(Result.SUCCESS_CODE).build();
    }

    public ChatDto getChatNotice(String fromUserId,String toUserId){
        User user = userService.selectByPrimaryKey(fromUserId);
        ChatDto chatDto = new ChatDto();
        List<Chat> chatList =  chatService.selectUnreadChatList(fromUserId,toUserId);
        chatDto.setUid(fromUserId)
                .setNickname(user.getNickname())
                .setAvatar(user.getAvatar());
        if(chatList.size() > 0) {
            chatDto.setContent(chatList.get(0).getMessage())
                    .setTime(chatList.get(0).getTime())
                    .setCount(chatList.size());
        }else {
            Chat c = chatService.selectChat(toUserId,fromUserId);
            Chat chat = chatService.selectHistoryChat(fromUserId, toUserId);
            if(c != null && chat == null){
                chatDto.setTime(c.getTime())
                        .setContent(c.getMessage());
            }else if(c == null && chat != null){
                chatDto.setTime(chat.getTime())
                        .setContent(chat.getMessage());
            }else if(c!=null&&chat!=null&&c.getTime() - chat.getTime() > 0){
                chatDto.setTime(c.getTime())
                        .setContent(c.getMessage());
            }else {
                chatDto.setTime(chat.getTime())
                        .setContent(chat.getMessage());
            }
        }
        return chatDto;

    }

    public Map getALlMsg(String toUserId,String uid){
        List<Chat> msgList = new ArrayList<>();
        ChatCompare chatCompare = new ChatCompare();
        msgList.addAll(chatService.select(uid,toUserId));
        msgList.addAll(chatService.select(toUserId,uid));
        Ordering<Chat> ordering = Ordering.from(chatCompare);
        Collections.sort(msgList,ordering);
        User u = userService.selectByPrimaryKey(toUserId);
        Map m = new HashMap();
        m.put("msgList",msgList);
        m.put("guest",u);
        return m;
    }


}
