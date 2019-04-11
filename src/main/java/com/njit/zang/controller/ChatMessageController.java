package com.njit.zang.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Ordering;
import com.njit.zang.Socket.WebSocketServer;
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

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/clearReadStatus")
    public Result clearReadStatus(HttpSession session){
        String uid = (String) session.getAttribute("uid");
        chatService.clearReadStatusByUid(uid);
        return Result.builder().code(Result.SUCCESS_CODE).build();
    }

    @PostMapping("/sendMsg")
    public Result sendMessage(@RequestBody Chat chat,HttpSession session){
        String uid = (String) session.getAttribute("uid");
        User user = userService.selectByPrimaryKey(uid);
        log.info(user.toString());
        chat.setFromUserId(uid);
        chat.setTime(new Date().getTime());

        ChatDto chatDto = new ChatDto();
        chatDto.setAvatar(user.getAvatar())
                .setNickname(user.getNickname())
                .setTime(new Date().getTime())
                .setContent(chat.getMessage())
                .setUid(uid);

        try {
            if(WebSocketServer.sendInfo(JSONObject.toJSONString(chatDto), chat.getToUserId()+uid) == -1){
                WebSocketServer.sendInfo(JSONObject.toJSONString(chatDto),chat.getToUserId());
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        chatService.insert(chat);

        Map m =  getALlMsg(chat.getToUserId(),uid);
        return Result.builder().code(Result.SUCCESS_CODE).res(m).build();
    }

    @GetMapping("/getMsg")
    public Result getMsg(@RequestParam("toUserId") String toUserId, HttpSession session){
        String uid = (String) session.getAttribute("uid");
        chatService.updateChatNotice(toUserId,uid);
        Map m = getALlMsg(toUserId,uid);
        return Result.builder().code(Result.SUCCESS_CODE).res(m).build();
    }

    @GetMapping("/getChatNotice")
    public Result getMsgNotice(HttpSession session){
        String uid = (String) session.getAttribute("uid");
        List<String> userList = chatService.selectUserList(uid);
        List<ChatDto> chatNotice = new ArrayList<>();
        userList.stream().forEach(u->{
            chatNotice.add(getChatNotice(u,uid));
        });
        ChatDtoCompare chatDtoCompare = new ChatDtoCompare();
        Ordering<ChatDto> ordering = Ordering.from(chatDtoCompare);
        Collections.sort(chatNotice,ordering);
        return Result.builder().code(Result.SUCCESS_CODE).res(chatNotice).build();
    }

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
            Chat chat = chatService.selectHistoryChat(fromUserId, toUserId);
            chatDto.setTime(chat.getTime())
                    .setContent(chat.getMessage());
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
