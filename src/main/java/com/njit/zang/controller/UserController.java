package com.njit.zang.controller;

import com.njit.zang.dto.*;
import com.njit.zang.model.FeedNotice;
import com.njit.zang.model.SubscribeNotice;
import com.njit.zang.model.User;
import com.njit.zang.model.UserSendContent;
import com.njit.zang.service.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.jws.Oneway;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Administrator on 2019/3/19.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    public UserService userService;

    @Autowired
    public SendContentService sendContentService;

    @Autowired
    public CommentService commentService;

    @Autowired
    public FeedService feedService;

    @Autowired
    public NoticeService noticeService;

    @Autowired
    public FollowService followService;

    @ApiOperation(value="获取用户详细信息", notes="根据id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String")
    @GetMapping("/test")
    public User selectUserById(String id){
        return userService.selectByPrimaryKey(id);
    }

    @PostMapping("check")
    public Result checkUser(@RequestBody User user, HttpSession session){
        System.out.println(user.toString());
        User u = userService.selectByPrimaryKey(user.getUid());
        if(u!=null&&u.getPassword().equals(user.getPassword())){
            session.setAttribute("uid",u.getUid());
            return Result.builder().code(Result.SUCCESS_CODE).res(u).build();
        }
        return Result.builder().code(Result.FAILED_CODE).build();
    }

    @GetMapping("logout")
    public Result logout(HttpSession session){
        session.removeAttribute("uid");
        return null;
    }

    @GetMapping("selectAll")
    public Result selectAll(){
        List<User> userList = userService.selectAll();
        if(userList!=null){
            return Result.builder().code(Result.SUCCESS_CODE).res(userList).build();
        }
        return Result.builder().code(Result.FAILED_CODE).build();
    }

    @GetMapping("selectAllContent")
    public Result selectAllContent(HttpSession session){
        String uid = (String) session.getAttribute("uid");
        List<UserSendContent> contentList = userService.selectAllContent();
        if(contentList!=null){
            Map m = fillResult(contentList,uid);
            return Result.builder().code(Result.SUCCESS_CODE).res(m).build();
        }
        return Result.builder().code(Result.FAILED_CODE).build();
    }

    @GetMapping("selectContentByTheme")
    public Result selectContentByTheme(String themeName,HttpSession session){
        String uid = (String) session.getAttribute("uid");
        List<String> feedList = feedService.selectByTheme(themeName);
        List<UserSendContent> contentList = userService.selectContentByFeedList(feedList);
        if(contentList!=null){
            Map m = fillResult(contentList,uid);
            return Result.builder().code(Result.SUCCESS_CODE).res(m).build();
        }
        return Result.builder().code(Result.FAILED_CODE).build();
    }

    @GetMapping("selectMyFeed")
    public Result selectMyFeed(HttpSession session){
        String uid = (String)session.getAttribute("uid");
        List<String> feedList = sendContentService.selectFeedListByUid(uid);
        List<UserSendContent> contentList = userService.selectContentByFeedList(feedList);
        if(contentList!=null){
            Map m = fillResult(contentList,uid);
            return Result.builder().code(Result.SUCCESS_CODE).res(m).build();
        }
        return Result.builder().code(Result.FAILED_CODE).build();
    }

    @GetMapping("selectMyKeep")
    public Result selectMyKeep(HttpSession session){
        String uid = (String) session.getAttribute("uid");
        List<String> feedList = feedService.selectByUid(uid);
        List<UserSendContent> contentList = userService.selectContentByFeedList(feedList);
        if(contentList!=null){
            Map m = fillResult(contentList,uid);
            return Result.builder().code(Result.SUCCESS_CODE).res(m).build();
        }
        return Result.builder().code(Result.FAILED_CODE).build();
    }

    @GetMapping("selectMyMaster")
    public Result selectMyMaster(HttpSession session){
        String uid = (String)session.getAttribute("uid");
        List<User> userList = followService.selectByFollower(uid);
        List<String> feedList = new ArrayList<>();
        userList.stream().forEach(u->{
            feedList.addAll(sendContentService.selectFeedListByUid(u.getUid()));
        });
        List<UserSendContent> contentList = userService.selectContentByFeedList(feedList);
        if(contentList!=null){
            Map m = fillResult(contentList,uid);
            return Result.builder().code(Result.SUCCESS_CODE).res(m).build();
        }
        return Result.builder().code(Result.FAILED_CODE).build();
    }

    @GetMapping("getNotice")
    public Result getNotice(HttpSession session){
        String uid = (String)session.getAttribute("uid");
        List<FeedNoticeDto> unreadFeedNotice = noticeService.selectUnreadFeedNotice(uid);
        List<FeedNoticeDto> historyFeedNotice = noticeService.selectHistoryFeedNotice(uid);
        List<SubscribeNoticeDto> unreadSubscribeNotice = noticeService.selectUnreadSubscribeNotice(uid);
        List<SubscribeNoticeDto> historySubscribeNotice = noticeService.selectHistorySubscribeNotice(uid);
        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setHistoryFeedNotice(historyFeedNotice)
                .setHistorySubscribeNotice(historySubscribeNotice)
                .setUnreadFeedNotice(unreadFeedNotice)
                .setUnreadSubscribeNotice(unreadSubscribeNotice);
        return Result.builder().code(Result.SUCCESS_CODE).res(noticeDto).build();
    }

    @PutMapping("changeNoticeStatus")
    public Result changeNoticeStatus(@RequestBody NoticeDto noticeDto){
        noticeDto.getUnreadFeedNotice().stream().forEach(f->{
            noticeService.changeFeedStatus(f.getFnId());
        });
        noticeDto.getUnreadSubscribeNotice().stream().forEach(s->{
            noticeService.changeSubscribeStatus(s.getSnId());
        });
        return Result.builder().code(Result.SUCCESS_CODE).build();
    }

    public Map fillResult(List<UserSendContent> contentList,String uid){
        List<LikeNum> likeNum = new ArrayList();
        List<KeepNum> keepNum = new ArrayList();
        List<MessageNum> messageNum = new ArrayList();
        List<Liked> likeList = new ArrayList();
        List<Kept> keepList = new ArrayList();
        contentList.stream().forEach(c->{
            c.setPicList(feedService.selectByFeedId(c.getFeedId()));
            c.changeUrl();
            c.setThemeList(feedService.selectThemeByFeedId(c.getFeedId()));
            likeNum.add(feedService.countLike(c.getFeedId()));
            keepNum.add(feedService.countKeep(c.getFeedId()));
            messageNum.add(commentService.countMessage(c.getFeedId()));
            if(uid!=null&&feedService.isLike(c.getFeedId(),uid)){
                Liked l = new Liked();
                l.setFeedId(c.getFeedId());
                l.setIsLiked(true);
                likeList.add(l);
            }
            if(uid!=null&&feedService.isKeep(c.getFeedId(),uid)){
                Kept k = new Kept();
                k.setFeedId(c.getFeedId());
                k.setIsKeep(true);
                keepList.add(k);
            }
        });
        Map m = new HashMap();
        m.put("likeNum",likeNum);
        m.put("likeList",likeList);
        m.put("keepNum",keepNum);
        m.put("keepList",keepList);
        m.put("messageNum",messageNum);
        m.put("contentList",contentList);
        return m;
    }
}
