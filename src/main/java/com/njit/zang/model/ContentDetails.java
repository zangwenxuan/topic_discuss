package com.njit.zang.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/3/20.
 */
@Data
public class ContentDetails implements Serializable {
    private String feedId;
    private String authorId;
    private String nickname;
    private String avatar;
    private String content;
    private Long releaseTime;
    private List<String> picList;
    private List<String> themeList;

    public void changeUrl(){
        List<String> list = new ArrayList();
        if(picList!=null) {
            picList.stream().forEach(p -> {
                list.add("http://localhost:8080/pic/" + p);
            });
            this.setPicList(list);
        }
    }

    public void fillAvatar(){
        if(avatar!=""){
            avatar = "http://localhost:8080/pic/"+avatar;
        }
    }
}
