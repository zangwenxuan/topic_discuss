package com.njit.zang.controller;

import com.njit.zang.annotation.UserLoginToken;
import com.njit.zang.dto.FollowedUser;
import com.njit.zang.dto.Result;
import com.njit.zang.model.FeedNotice;
import com.njit.zang.model.User;
import com.njit.zang.service.FollowService;
import com.njit.zang.service.NoticeService;
import com.njit.zang.service.SendContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Administrator on 2019/3/29.
 */
@RestController()
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    private FollowService followService;

    @Autowired
    private SendContentService sendContentService;

    @Autowired
    private NoticeService noticeService;

    @GetMapping("getPersonalFollow")
    public Result getFollow(String uid,HttpSession session){
        List followerList = followService.selectByMaster(uid);
        List masterList = followService.selectByFollower(uid);
        Map m = new HashMap();
        if(followerList != null){
            m.put("followerNum",followerList.size());
        }else {
            m.put("followerNum",0);
        }
        if(masterList != null){
            m.put("masterNum", masterList.size());
        }else {
            m.put("masterNum",0);
        }
        return Result.builder().code(Result.SUCCESS_CODE).res(m).build();

    }

    @UserLoginToken
    @PostMapping("newFollow")
    public Result insert(@RequestBody Map map, HttpSession session){
        String uid = (String)session.getAttribute("uid");
        followService.insert(uid,(String)map.get("master"));

        FeedNotice feedNotice = new FeedNotice();
        feedNotice.setFromUserId(uid)
                .setToUserId((String)map.get("master"))
                .setType(4)
                .setTime(new Date().getTime());
        noticeService.insertFeedNotice(feedNotice);

        return Result.builder().code(Result.SUCCESS_CODE).res(true).build();
    }

    @UserLoginToken
    @DeleteMapping("cancelFollow")
    public Result cancelFollow(@RequestBody Map map, HttpSession session){
        String uid = (String)session.getAttribute("uid");
        followService.delete(uid,(String)map.get("master"));

        FeedNotice feedNotice = new FeedNotice();
        feedNotice.setFromUserId(uid)
                .setToUserId((String)map.get("master"))
                .setType(-3)
                .setTime(new Date().getTime());
        noticeService.insertFeedNotice(feedNotice);

        return Result.builder().code(Result.SUCCESS_CODE).res(false).build();
    }

    @GetMapping("queryFollowerCount")
    public Result queryFollowerCount(String masterId){
        List l =followService.selectByMaster(masterId);
        int length = 0;
        if(l!=null) {
            length = l.size();
        }
        return Result.builder().code(Result.SUCCESS_CODE).res(length).build();
    }

    @GetMapping("queryMasterCount")
    public Result queryMasterCount(String followerId){
        List l = followService.selectByFollower(followerId);
        int length = 0;
        if(l!=null)
            length = l.size();
        return Result.builder().code(Result.SUCCESS_CODE).res(length).build();
    }

    @GetMapping("queryUserCard")
    public Result queryUserCard(@RequestParam("uid") String uid,HttpSession session){
        String userId = (String)session.getAttribute("uid");
        Map m = new HashMap();
        m.put("followerNum",followService.queryFollowerCount(uid));
        m.put("feedNum",sendContentService.countFeed(uid));
        if(userId!=null) {
            m.put("isFollowed", followService.selectByFollow(userId, uid));
        }else{
            m.put("isFollowed",false);
        }
        return Result.builder().code(Result.SUCCESS_CODE).res(m).build();
    }

    @GetMapping("queryFollower")
    public Result queryFollower(String uid,HttpSession session){
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
    @GetMapping("queryFollowing")
    public Result queryFollowing(String uid,HttpSession session){
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
}
