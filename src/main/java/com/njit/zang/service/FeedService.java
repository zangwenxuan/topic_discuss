package com.njit.zang.service;

import com.njit.zang.dto.KeepNum;
import com.njit.zang.dto.LikeNum;
import com.njit.zang.mapper.*;
import com.njit.zang.model.Pictures;
import com.njit.zang.model.SendContentHasTheme;
import com.njit.zang.model.Theme;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/3/26.
 */
@Service
public class FeedService {
    @Autowired
    private PicturesDao picturesDao;

    @Autowired
    private ThemeDao themeDao;

    @Autowired
    private KeepDao keepDao;

    @Autowired
    private LikeDao likeDao;

    @Autowired
    private SendContentHasThemeDao sendContentHasThemeDao;

    public void insertPicList(List<String> picList,String feedId){
        picList.stream().forEach(p->{
            Pictures pictures = new Pictures();
            pictures.setFeedId(feedId);
            pictures.setPName(p);
            picturesDao.insert(pictures);
        });
    }

    public List selectByFeedId(String feedId){
        return picturesDao.selectByFeedId(feedId);
    }

    public List selectThemeByFeedId(String feedId){
        return sendContentHasThemeDao.selectByFeedId(feedId);
    }

    public void isThemeExists(String themeName){
        if(themeDao.selectByThemeName(themeName) == null){
            Theme t = new Theme();
            t.setThemeName(themeName);
            themeDao.insert(t);
        }
    }

    public void insertContentTheme(List<String> themeList,String feedId){
        themeList.stream().forEach(t->{
            SendContentHasTheme sendContentHasTheme = new SendContentHasTheme();
            isThemeExists(t);
            sendContentHasTheme.setSendContentFeedId(feedId);
            sendContentHasTheme.setThemeName(t);
            sendContentHasThemeDao.insert(sendContentHasTheme);
        });
    }

    public List<String> selectByTheme(String theme){
        return sendContentHasThemeDao.selectByTheme(theme);
    }

    public KeepNum countKeep(String feedId) {
        KeepNum k = new KeepNum();
        k.setFeedId(feedId);
        k.setNum(keepDao.countByFeedId(feedId));
        return k;
    }

    public LikeNum countLike(String feedId){
        LikeNum l = new LikeNum();
        l.setFeedId(feedId);
        l.setNum(likeDao.countByFeedId(feedId));
        return l;
    }

    public List<String> selectByUid(String uid){
        return keepDao.selectByUserId(uid);
    }

    public boolean isLike(String feedId,String uid){
        if(likeDao.selectByFeedAndUser(feedId,uid)!=null)
            return true;
        return false;
    }

    public boolean isKeep(String feedId,String uid){
        if(keepDao.selectByFeedAndUser(feedId,uid)!=null)
            return true;
        return false;
    }

    public int deleteKeep(String feedId,String uid){
        return keepDao.deleteByFeedAndUser(feedId,uid);
    }

    public int deleteLike(String feedId,String uid){
        return likeDao.deleteByFeedAndUser(feedId,uid);
    }

    public int insertLike(String feedId,String uid){
        return likeDao.insertIgnore(feedId,uid);
    }

    public int insertKeep(String feedId,String uid){
        return keepDao.insertIgnore(feedId,uid);
    }
}
