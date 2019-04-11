package com.njit.zang.controller;

import com.njit.zang.dto.*;
import com.njit.zang.model.*;
import com.njit.zang.service.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.thymeleaf.spring5.context.SpringContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2019/3/20.
 */
@RestController
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private SendContentService sendContentService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentReplyService commentReplyService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private FeedService feedService;

    @Autowired
    private FollowService followService;

    @GetMapping("/details")
    public Result selectContentDetails(String feedId,HttpSession session){
        String uid = (String) session.getAttribute("uid");
        ContentDetails contentDetails = sendContentService.selectContentDetails(feedId);
        contentDetails.setPicList(feedService.selectByFeedId(feedId));
        contentDetails.changeUrl();
        contentDetails.setThemeList(feedService.selectThemeByFeedId(feedId));
        //查询该帖子的收藏数，点赞数以及当前用户的收藏点赞状态
        KeepNum keepNum = feedService.countKeep(feedId);
        LikeNum likeNum = feedService.countLike(feedId);
        MessageNum messageNum = commentService.countMessage(feedId);
        Boolean isLiked = false;
        Boolean isKeep = false;
        if(uid!=null&&feedService.isKeep(feedId,uid)){
            isKeep = true;
        }
        if(uid!=null&&feedService.isLike(feedId,uid)){
            isLiked = true;
        }
        //查询该帖子对应的评论
        List<CommentUser> commentUserList = commentService.selectCommentByFeedId(feedId);
        commentUserList.stream().forEach(c->{
            c.setCommentReplyList(commentService.selectProByCommentId(c.getCommentId()));
            c.fillNickname();
        });
        Map m = new HashMap();
        m.put("likeNum",likeNum.getNum());
        m.put("keepNum",keepNum.getNum());
        m.put("isKeep",isKeep);
        m.put("isLiked",isLiked);
        m.put("messageNum",messageNum.getNum());
        try {
            if (contentDetails != null) {
                m.put("contentDetails", contentDetails);
                if(commentUserList!=null){
                    m.put("commentUserList",commentUserList);
                }
            }
        }catch (Exception e){e.printStackTrace();}
        return Result.builder().code(Result.SUCCESS_CODE).res(m).build();
    }

    @PostMapping("postComment")
    public Result postComment(@RequestBody Comment comment,HttpSession session){
        String uid = (String) session.getAttribute("uid");
        comment.setUserUid(uid);
        //将评论内容添加到数据库中
        commentService.insert(comment);
        //添加通知
        FeedNotice feedNotice = new FeedNotice();
        feedNotice.setContent(comment.getComCon())
                .setFromUserId(comment.getUserUid())
                .setFeedId(comment.getSendContentFeedId())
                .setToUserId(sendContentService.selectUidByFeedId(comment.getSendContentFeedId()))
                .setTime(comment.getTime()).setType(3);
        noticeService.insertFeedNotice(feedNotice);
        //获取最新的评论内容并返回
        List<CommentUser> commentUserList = commentService.selectCommentByFeedId(comment.getSendContentFeedId());
        commentUserList.stream().forEach(c->{
            c.setCommentReplyList(commentService.selectProByCommentId(c.getCommentId()));
            //查询评论列表中每个用户的昵称
            c.fillNickname();
        });
        return Result.builder().code(Result.SUCCESS_CODE).res(commentUserList).build();
    }

    @PostMapping("postCommentReply")
    public Result postCommentReply(@RequestBody CommentReply commentReply,HttpSession session){
        String uid = (String) session.getAttribute("uid");
        commentReply.setFromUserId(uid);
        //将回复评论的数据添加到数据库
        commentReply.setCommentReplyId(UUID.randomUUID().toString().substring(24));
        commentReplyService.insert(commentReply);
        //添加通知
        FeedNotice feedNotice = new FeedNotice();
        feedNotice.setTime(commentReply.getRepTime())
                  .setFromUserId(commentReply.getFromUserId())
                  .setContent(commentReply.getRepCon())
                  .setToUserId(commentReply.getToUserId())
                  .setFeedId(commentReplyService.selectFeedId(commentReply.getCommentReplyId()))
                  .setType(2);
        noticeService.insertFeedNotice(feedNotice);

        return Result.builder().code(Result.SUCCESS_CODE).build();
    }

    @GetMapping("freshComment")
    public Result freshComment(String feedId){
        List<CommentUser> commentUserList = commentService.selectCommentByFeedId(feedId);
        commentUserList.stream().forEach(c->{
            c.setCommentReplyList(commentService.selectProByCommentId(c.getCommentId()));
            c.fillNickname();
        });
        return Result.builder().code(Result.SUCCESS_CODE).res(commentUserList).build();
    }

    @PostMapping("uploadPic")
    public Result uploadPic(@RequestParam(value="importFile")MultipartFile file){
        System.out.println(file.getOriginalFilename());
        //生成随机图片名
        String fileName = UUID.randomUUID().toString().substring(24)+".png";
        //指定图片存放地址
        String path = "D:/upload/picture" ;
        File dest = new File(path + "/" + fileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();//父目录不存在则新建文件
        }
        System.out.println("绝对路径:"+dest);
        try {
            //将文件保存到本地
            file.transferTo(dest);
        }catch(Exception e){e.printStackTrace();}
        return Result.builder().code(Result.SUCCESS_CODE).res(fileName).build();
    }

    @PostMapping("sendFeed")
    public Result sendFeed(@RequestBody Feed feed,HttpSession session){
        feed.setAuthorId((String)session.getAttribute("uid"));
        //取出包装类中关于feed的数据，存放到数据库中
        String feedId = UUID.randomUUID().toString().substring(24);
        SendContent sendContent = new SendContent();
        sendContent.setContent(feed.getContent());
        sendContent.setAuthorId(feed.getAuthorId());
        sendContent.setFeedId(feedId);
        sendContentService.insert(sendContent);
        //为feed绑定图片
        feedService.insertPicList(feed.getPic(),feedId);
        //为feed绑定主题
        feedService.insertContentTheme(feed.getThemeList(),feedId);
        //添加通知
        insertNoticeToFollowers(sendContent);
        return Result.builder().code(Result.SUCCESS_CODE).build();
    }

    //为关注该用户的用户添加通知
    public void insertNoticeToFollowers(SendContent sendContent){
        //首先查出所有关注该用户的用户的id
        List<User> users = followService.selectByMaster(sendContent.getAuthorId());
        //初始化订阅通知类
        SubscribeNotice subscribeNotice = new SubscribeNotice();
        subscribeNotice.setContent(sendContent.getContent())
                .setFromUserId(sendContent.getAuthorId())
                .setTime(sendContent.getReleaseTime())
                .setFeedId(sendContent.getFeedId());
        //为每一位关注该用户的用户添加通知
        users.stream().forEach(u->{
            subscribeNotice.setToUserId(u.getUid());
            noticeService.insertSubscribeNotice(subscribeNotice);
        });


    }

    @PostMapping("freshFeed")
    public Result freshFeed(@RequestBody LikeKeepList likeKeepList, HttpSession session){
        String uid = (String) session.getAttribute("uid");
        //初始化FeedNotice类
        FeedNotice feedNotice = new FeedNotice();
        feedNotice.setFromUserId(uid)
                .setTime(new Date().getTime());
        //取出有变动的收藏列表并遍历
        likeKeepList.getKeepList().stream().forEach(k->{
            if(k.getIsKeep()){
                //如果有变化则添加通知
                if(feedService.insertKeep(k.getFeedId(),uid) == 1){
                    feedNotice.setFeedId(k.getFeedId())
                            .setContent(sendContentService.selectContentByFeedId(k.getFeedId()))
                            .setType(1)
                            .setToUserId(sendContentService.selectUidByFeedId(k.getFeedId()));
                    noticeService.insertFeedNotice(feedNotice);
                }
            }else{
                if(feedService.deleteKeep(k.getFeedId(),uid) == 1){
                    feedNotice.setFeedId(k.getFeedId())
                            .setContent(sendContentService.selectContentByFeedId(k.getFeedId()))
                            .setType(-2)
                            .setToUserId(sendContentService.selectUidByFeedId(k.getFeedId()));
                    noticeService.insertFeedNotice(feedNotice);
                }
            }
        });
        likeKeepList.getLikeList().stream().forEach(l->{
            if(l.getIsLiked()){
                if(1 == feedService.insertLike(l.getFeedId(),uid)){
                    feedNotice.setFeedId(l.getFeedId())
                            .setContent(sendContentService.selectContentByFeedId(l.getFeedId()))
                            .setType(0)
                            .setToUserId(sendContentService.selectUidByFeedId(l.getFeedId()));
                    noticeService.insertFeedNotice(feedNotice);
                }
            }else{
                if(1 == feedService.deleteLike(l.getFeedId(),uid)){
                    feedNotice.setFeedId(l.getFeedId())
                            .setContent(sendContentService.selectContentByFeedId(l.getFeedId()))
                            .setType(-1)
                            .setToUserId(sendContentService.selectUidByFeedId(l.getFeedId()));
                    noticeService.insertFeedNotice(feedNotice);
                }
            }
        });
        return Result.builder().code(Result.SUCCESS_CODE).build();
    }

    @PostMapping("freshByFeedId")
    public Result freshByFeedId(@RequestBody LikeKeep likeKeep,HttpSession session){
        String uid = (String) session.getAttribute("uid");
        likeKeep.setUid(uid);
        FeedNotice feedNotice = new FeedNotice();
        feedNotice.setFromUserId(likeKeep.getUid())
                .setFeedId(likeKeep.getFeedId())
                .setTime(new Date().getTime())
                .setToUserId(sendContentService.selectUidByFeedId(likeKeep.getFeedId()));
        if(likeKeep.getIsKeep()){
            if(1 == feedService.insertKeep(likeKeep.getFeedId(),likeKeep.getUid())){
                feedNotice.setType(1);
                noticeService.insertFeedNotice(feedNotice);
            }
        }else {
            if(1 == feedService.deleteKeep(likeKeep.getFeedId(), likeKeep.getUid())){
                feedNotice.setType(-2);
                noticeService.insertFeedNotice(feedNotice);
            }
        }
        if(likeKeep.getIsLiked()){
            if(1 == feedService.insertLike(likeKeep.getFeedId(),likeKeep.getUid())){
                feedNotice.setType(0);
                noticeService.insertFeedNotice(feedNotice);
            }
        }else {
            if(1 == feedService.deleteLike(likeKeep.getFeedId(), likeKeep.getUid())){
                feedNotice.setType(-1);
                noticeService.insertFeedNotice(feedNotice);
            }
        }
        return Result.builder().code(Result.SUCCESS_CODE).build();
    }
}
