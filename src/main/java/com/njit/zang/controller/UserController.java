package com.njit.zang.controller;

import com.google.common.collect.Ordering;
import com.njit.zang.annotation.UserLoginToken;
import com.njit.zang.token.TokenService;
import com.njit.zang.utils.MD5Utils;
import com.njit.zang.utils.Mail;
import com.njit.zang.dto.*;
import com.njit.zang.model.User;
import com.njit.zang.model.UserSendContent;
import com.njit.zang.service.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Administrator on 2019/3/19.
 * @author zangwenxuan
 */
@Slf4j
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

    @Autowired
    public Mail mailSend;

    @Autowired
    public TokenService tokenService;

    @ApiOperation(value="获取用户详细信息", notes="根据id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String")
    @GetMapping("/test")
    public User selectUserById(String id){
        return userService.selectByPrimaryKey(id);
    }

    @GetMapping("getCaptcha")
    public Result getCaptcha(@Param("email") String email, HttpSession session){
        session.setAttribute("captcha",mailSend.sendMessage(email));
        return Result.builder().code(Result.SUCCESS_CODE).build();
    }

    @PostMapping("register")
    public Result register(@RequestBody RegisterUser registerUser,HttpSession session){
        String captcha = (String)session.getAttribute("captcha");
        if(captcha!=null && captcha.equals(registerUser.getCaptcha())){
            User user = userService.insert(registerUser.getUser());
            String token = tokenService.getToken(user);
            return Result.builder().code(Result.SUCCESS_CODE).res(token).build();
        }
        return Result.builder().code(Result.FAILED_CODE).build();
    }

    @PostMapping("check")
    public Result checkUser(@RequestBody User user, HttpSession session){
        User u = userService.selectByNickname(user.getUid());
        String password = MD5Utils.Encode(user.getPassword());
        if(u!=null&&u.getPassword().equals(password)){
            session.setAttribute("uid",u.getUid());
            return Result.builder().code(Result.SUCCESS_CODE).res(tokenService.getToken(u)).build();
        }
        u = userService.selectByEmail(user.getUid());
        if(u!=null&&u.getPassword().equals(password)){
            session.setAttribute("uid",u.getUid());
            return Result.builder().code(Result.SUCCESS_CODE).res(tokenService.getToken(u)).build();
        }

        return Result.builder().code(Result.SUCCESS_CODE).res(null).build();
    }

    @GetMapping("checkName")
    public Result checkName(@Param("name")String name){
        return Result.builder().code(Result.SUCCESS_CODE).res(!userService.checkName(name)).build();
    }

    @GetMapping("checkEmail")
    public Result checkEmail(@Param("email") String email){
        return Result.builder().code(Result.SUCCESS_CODE).res(!userService.checkEmail(email)).build();
    }

    @UserLoginToken
    @GetMapping("getCurrentUser")
    public Result getCurrentUser(HttpSession session){
        User u = userService.selectByPrimaryKey((String) session.getAttribute("uid"));
        return Result.builder().code(Result.SUCCESS_CODE).res(u).build();
    }

    @GetMapping("getUserInfo")
    public Result getUserInfo(String uid,HttpSession session){
        log.info(uid);
        if(uid.equals("undefined")){
            uid = (String) session.getAttribute("uid");
        }
        User u = userService.selectByPrimaryKey(uid);
        if(u == null){
            return Result.builder().code(404).res(null).error("该用户不存在").build();
        }
        return Result.builder().code(Result.SUCCESS_CODE).res(u).build();
    }

    @UserLoginToken
    @PostMapping("updateUser")
    public Result updateUser(@RequestBody User u,HttpSession session){
        String uid = (String) session.getAttribute("uid");
        u.setUid(uid);
        userService.update(u);
        return Result.builder().code(Result.SUCCESS_CODE).build();
    }

    @UserLoginToken
    @GetMapping("logout")
    public Result logout(HttpSession session){
        session.removeAttribute("uid");
        return Result.builder().code(Result.SUCCESS_CODE).build();
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
        return Result.builder().code(Result.SUCCESS_CODE).res(null).build();
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
        return Result.builder().code(404).error("该主题不存在").build();
    }

    @GetMapping("getPersonalFollower")
    public Result getMyFollower(String uid,HttpSession session){
        String currentUid = (String) session.getAttribute("uid");
        List<User> userList = followService.selectByFollower(uid);
        List<FollowedUser> followedUsers = new ArrayList<>();
        userList.stream().forEach(u->{
            if(followService.selectByFollow(currentUid,u.getUid())){
                followedUsers.add(new FollowedUser().setUser(u));
            }else {
                followedUsers.add(new FollowedUser().setUser(u).setFollowed(false));
            }
        });
        return Result.builder().code(Result.SUCCESS_CODE).res(followedUsers).build();
    }

    /**
    *   获取关注当前用户的用户
    */
    @GetMapping("getPersonalFollowing")
    public Result getMyFollowing(String uid,HttpSession session){
        String currentUid = (String) session.getAttribute("uid");
        List<User> userList = followService.selectByMaster(uid);
        List<FollowedUser> followedUsers = new ArrayList<>();
        userList.stream().forEach(u->{
            if(followService.selectByFollow(currentUid,u.getUid())){
                followedUsers.add(new FollowedUser().setUser(u));
            }else {
                followedUsers.add(new FollowedUser().setUser(u).setFollowed(false));
            }
        });
        return Result.builder().code(Result.SUCCESS_CODE).res(followedUsers).build();
    }

    @GetMapping("selectPersonalFeed")
    public Result selectPersonalFeed(String uid,HttpSession session){
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

    @GetMapping("selectPersonalKeep")
    public Result selectPersonalKeep(String uid ,HttpSession session){
        String currentUid = (String) session.getAttribute("uid");
        List<UserSendContent> contentList = feedService.selectKeepByUid(uid);
        if(contentList!=null){
            Map m = fillResult(contentList,currentUid);
            return Result.builder().code(Result.SUCCESS_CODE).res(m).build();
        }
        return Result.builder().code(Result.SUCCESS_CODE).res(null).build();
    }


    /**
    * 获取当前用户关注的用户所发布的帖子
    */
    @GetMapping("selectPersonalMaster")
    public Result selectPersonalMaster(String uid,HttpSession session){
        String currentUid = (String)session.getAttribute("uid");
        List<User> userList = followService.selectByFollower(uid);
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

    @UserLoginToken
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

    @UserLoginToken
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
