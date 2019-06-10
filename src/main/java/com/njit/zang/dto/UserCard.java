package com.njit.zang.dto;

import lombok.Data;

/**
 * Created by Administrator on 2019/5/28.
 */
@Data
public class UserCard {
    private String uid;
    private String cover;
    private String avatar;
    private String signature;
    private String nickname;
    private int followerNum;
    private int followingNum;
    private int feedNum;
    private boolean isFollowed;
}
