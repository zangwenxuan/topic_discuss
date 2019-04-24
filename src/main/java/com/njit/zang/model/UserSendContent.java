package com.njit.zang.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/3/20.
 */
@Data
public class UserSendContent {
    private String authorId;
    private String feedId;
    private long releaseTime;
    private String nickname;
    private String avatar;
    private String cover;
    private String content;
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
