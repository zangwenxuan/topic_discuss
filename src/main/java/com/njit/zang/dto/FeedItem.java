package com.njit.zang.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/5/28.
 */
@Data
public class FeedItem {
    private String feedId;
    private String authorId;
    private String nickname;
    private String avatar;
    private String content;
    private long releaseTime;
    private int keepNum;
    private int likeNum;
    private int commentNum;
    private boolean like = false;
    private boolean keep = false;
    private List picList;
    private List themeList;

    public void changeUrl(){
        List<String> list = new ArrayList();
        picList.stream().forEach(p->{
            list.add("http://localhost:8080/pic/" +p);
        });
        this.setPicList(list);
    }
}
