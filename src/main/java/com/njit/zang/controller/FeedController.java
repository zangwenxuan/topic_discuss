package com.njit.zang.controller;

import com.google.common.collect.Ordering;
import com.njit.zang.annotation.UserLoginToken;
import com.njit.zang.dto.*;
import com.njit.zang.model.User;
import com.njit.zang.model.UserSendContent;
import com.njit.zang.service.*;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Administrator on 2019/4/23.
 * @author zang
 */
@Slf4j
@RestController
@RequestMapping("/feed")
public class FeedController {
    @Autowired
    private FeedService feedService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private FollowService followService;

    @Autowired
    private SendContentService sendContentService;

    @GetMapping("selectPersonalFeed")
    public Result selectPersonalFeed(String uid, HttpSession session){
        String currentUid = (String)session.getAttribute("uid");
        List<UserSendContent> contentList = userService.selectContentByUid(uid);
        Ordering<UserSendContent> ordering = Ordering.from(new FeedCompare());
        Collections.sort(contentList,ordering);
        if(contentList!=null){
            Map m = fillResult(contentList,currentUid);
            return Result.builder().code(Result.SUCCESS_CODE).res(m).build();
        }
        return Result.builder().code(Result.FAILED_CODE).build();
    }

    /**
     * 获取当前用户关注的用户所发布的帖子
     */
    @GetMapping("selectSubscribeFeed")
    public Result selectSubscribe(HttpSession session){
        String currentUid = (String)session.getAttribute("uid");
        List<User> userList = followService.selectByFollower(currentUid);
        List<String> feedList = new ArrayList<>();
        userList.stream().forEach(u->{
            feedList.addAll(sendContentService.selectFeedListByUid(u.getUid()));
        });
        List<UserSendContent> contentList = userService.selectContentByFeedList(feedList);
        Ordering<UserSendContent> ordering = Ordering.from(new FeedCompare());
        if(contentList ==null){
            contentList = new ArrayList<>();
        }
        Collections.sort(contentList,ordering);
        Map m = fillResult(contentList,currentUid);
        return Result.builder().code(Result.SUCCESS_CODE).res(m).build();
    }


    @GetMapping("selectKeep")
    public Result selectKeep (String uid ,HttpSession session){
        String currentUid = (String) session.getAttribute("uid");
        List<UserSendContent> contentList = feedService.selectKeepByUid(uid);
        if(contentList!=null){
            Map m = fillResult(contentList,currentUid);
            return Result.builder().code(Result.SUCCESS_CODE).res(m).build();
        }
        return Result.builder().code(Result.SUCCESS_CODE).res(null).build();
    }

    @GetMapping("selectIndexFeed")
    public Result selectAllContent(HttpSession session){
        String uid = (String) session.getAttribute("uid");
        List<UserSendContent> contentList = userService.selectAllContent();
        if(contentList!=null){
            Map m = fillResult(contentList,uid);
            return Result.builder().code(Result.SUCCESS_CODE).res(m).build();
        }
        return Result.builder().code(Result.SUCCESS_CODE).res(null).build();
    }

    @GetMapping("selectFeedByTheme")
    public Result selectFeedByTheme(String themeName,HttpSession session){
        String uid = (String) session.getAttribute("uid");
        List<String> feedList = feedService.selectByTheme(themeName);
        List<UserSendContent> contentList = userService.selectContentByFeedList(feedList);
        if(contentList!=null){
            Map m = fillResult(contentList,uid);
            return Result.builder().code(Result.SUCCESS_CODE).res(m).build();
        }
        return Result.builder().code(404).error("该主题不存在").build();
    }

    @UserLoginToken
    @DeleteMapping("deleteFeed")
    public Result deleteFeed(@RequestBody Map m, HttpSession session ){
        sendContentService.deleteFeed((String)m.get("feedId"));
        return Result.builder().code(Result.SUCCESS_CODE).build();
    }


    public Map fillResult(List<UserSendContent> contentList,String uid){
        List<LikeNum> likeNum = new ArrayList();
        List<KeepNum> keepNum = new ArrayList();
        List<MessageNum> messageNum = new ArrayList();
        List<Liked> likeList = new ArrayList();
        List<Kept> keepList = new ArrayList();
        if(contentList != null) {
            contentList.stream().forEach(c -> {
                c.setPicList(feedService.selectPicByFeedId(c.getFeedId()));
                c.changeUrl();
                c.setThemeList(feedService.selectThemeByFeedId(c.getFeedId()));
                likeNum.add(feedService.countLike(c.getFeedId()));
                keepNum.add(feedService.countKeep(c.getFeedId()));
                messageNum.add(commentService.countMessage(c.getFeedId()));
                if (uid != null && feedService.isLike(c.getFeedId(), uid)) {
                    Liked l = new Liked();
                    l.setFeedId(c.getFeedId());
                    l.setIsLiked(true);
                    likeList.add(l);
                }
                if (uid != null && feedService.isKeep(c.getFeedId(), uid)) {
                    Kept k = new Kept();
                    k.setFeedId(c.getFeedId());
                    k.setIsKeep(true);
                    keepList.add(k);
                }
            });
        }
        Map m = new HashMap(12);
        m.put("likeNum",likeNum);
        m.put("likeList",likeList);
        m.put("keepNum",keepNum);
        m.put("keepList",keepList);
        m.put("messageNum",messageNum);
        m.put("contentList",contentList);
        return m;
    }
}
