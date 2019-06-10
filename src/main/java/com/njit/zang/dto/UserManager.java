package com.njit.zang.dto;

import lombok.Data;

/**
 * Created by Administrator on 2019/5/27.
 */
@Data
public class UserManager {
    private String uid;
    private String email;
    private String nickname;
    private int feedNum;
    private int followerNum;
    private int followingNum;
}
