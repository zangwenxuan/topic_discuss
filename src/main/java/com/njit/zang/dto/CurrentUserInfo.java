package com.njit.zang.dto;

import com.njit.zang.model.Follow;
import com.njit.zang.model.User;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by Administrator on 2019/4/29.
 */
@Data
@Accessors(chain = true)
public class CurrentUserInfo {

    private String uid;

    private String email;

    private String nickname;

    private String avatar;

    private String cover;

    private String signature;

    private List<String> themeList;

    private int feedNum;

    private int followerNum;

    private int followingNum;

    public CurrentUserInfo(User u){
        this.avatar = u.getAvatar();
        this.cover = u.getCover();
        this.email = u.getEmail();
        this.nickname = u.getNickname();
        this.signature = u.getSignature();
        this.uid = u.getUid();
    }

}
