package com.njit.zang.controller;

import com.google.common.collect.Ordering;
import com.njit.zang.annotation.UserLoginToken;
import com.njit.zang.dto.*;
import com.njit.zang.model.User;
import com.njit.zang.model.UserSendContent;
import com.njit.zang.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
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

    @UserLoginToken
    @GetMapping("selectPersonalFeed")
    public Result selectPersonalFeed(String uid, HttpSession session){
        String currentUid = (String)session.getAttribute("uid");
        List<FeedItem> contentList = userService.selectFeedItemByUid(uid);
        Ordering<UserSendContent> ordering = Ordering.from(new FeedCompare());
        if(contentList!=null){
            fillResult(contentList,currentUid);
            return Result.builder().code(Result.SUCCESS_CODE).res(contentList).build();
        }
        return Result.builder().code(Result.FAILED_CODE).build();
    }

    /**
     * 获取当前用户关注的用户所发布的帖子
     */
   /* @GetMapping("selectSubscribeFeed")
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
    }*/

    @UserLoginToken
    @GetMapping("selectSubscribePage")
    public Result selectSubscribePage(String page,HttpSession session){
        String uid = (String) session.getAttribute("uid");
        List<FeedItem> contentList = feedService.selectSubscribe(page,uid);
        if(contentList!=null){
            fillResult(contentList,uid);
            return Result.builder().code(Result.SUCCESS_CODE).res(contentList).build();
        }
        return Result.builder().code(Result.SUCCESS_CODE).res(null).build();
    }

    @UserLoginToken
    @GetMapping("selectKeep")
    public Result selectKeep (String uid ,HttpSession session){
        String currentUid = (String) session.getAttribute("uid");
        List<FeedItem> contentList = feedService.selectKeepByUid(uid);
        if(contentList!=null){
            fillResult(contentList,currentUid);
            return Result.builder().code(Result.SUCCESS_CODE).res(contentList).build();
        }
        return Result.builder().code(Result.SUCCESS_CODE).res(null).build();
    }

/*    @GetMapping("selectIndexFeed")
    public Result selectAllContent(HttpSession session){
        String uid = (String) session.getAttribute("uid");
        List<UserSendContent> contentList = userService.selectAllContent();
        if(contentList!=null){
            Map m = fillResult(contentList,uid);
            return Result.builder().code(Result.SUCCESS_CODE).res(m).build();
        }
        return Result.builder().code(Result.SUCCESS_CODE).res(null).build();
    }*/

    @GetMapping("selectFeedPage")
    public Result selectFeedPage(String page,HttpSession session){
        String uid = (String) session.getAttribute("uid");
        List<FeedItem> contentList = feedService.selectFeedPage(page);
        if(contentList!=null){
            fillResult(contentList,uid);
            return Result.builder().code(Result.SUCCESS_CODE).res(contentList).build();
        }
        return Result.builder().code(Result.SUCCESS_CODE).res(null).build();
    }

/*    @GetMapping("selectFeedByTheme")
    public Result selectFeedByTheme(String themeName,HttpSession session){
        String uid = (String) session.getAttribute("uid");
        List<String> feedList = feedService.selectByTheme(themeName);
        List<UserSendContent> contentList = userService.selectContentByFeedList(feedList);
        if(contentList!=null){
            Map m = fillResult(contentList,uid);
            return Result.builder().code(Result.SUCCESS_CODE).res(m).build();
        }
        return Result.builder().code(404).error("该主题不存在").build();
    }*/

    @GetMapping("selectThemePage")
    public Result selectThemePage(String page,String themeName,HttpSession session){
        String uid = (String) session.getAttribute("uid");
        List<FeedItem> contentList = feedService.selectThemePage(page,themeName);
        if(contentList!=null){
            fillResult(contentList,uid);
            return Result.builder().code(Result.SUCCESS_CODE).res(contentList).build();
        }
        return Result.builder().code(Result.SUCCESS_CODE).res(null).build();
    }

    @GetMapping("selectHotTheme")
    public Result selectHotTheme(){
        List<String> hotThemeList = feedService.selectHotTheme();
        return Result.builder().code(Result.SUCCESS_CODE).res(hotThemeList).build();
    }

    @GetMapping("searchTheme")
    public Result searchTheme(String theme){
        List<String> themeList = feedService.searchTheme(theme);
        return Result.builder().code(Result.SUCCESS_CODE).res(themeList).build();
    }

    @GetMapping("searchUser")
    public Result searchUser(String nickname){
        List<User> userList = userService.searchUser(nickname);
        return Result.builder().code(Result.SUCCESS_CODE).res(userList).build();
    }

    @UserLoginToken
    @DeleteMapping("deleteFeed")
    public Result deleteFeed(@RequestBody Map m){
        sendContentService.deleteFeed((String)m.get("feedId"));
        return Result.builder().code(Result.SUCCESS_CODE).build();
    }


    public List<FeedItem> fillResult(List<FeedItem> contentList,String uid){
        if(contentList != null) {
            contentList.stream().forEach(c -> {
                c.setPicList(feedService.selectPicByFeedId(c.getFeedId()));
                c.changeUrl();
                c.setThemeList(feedService.selectThemeByFeedId(c.getFeedId()));
                if (uid != null && feedService.isLike(c.getFeedId(), uid)) {
                    c.setLike(true);
                }
                if (uid != null && feedService.isKeep(c.getFeedId(), uid)) {
                    c.setKeep(true);
                }
            });
        }
        return contentList;
    }
}
