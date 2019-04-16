package com.njit.zang.dto;

import com.njit.zang.model.User;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by Administrator on 2019/4/15.
 */
@Data
@Accessors(chain = true)
public class FollowedUser {
    private boolean isFollowed = true;
    private User user;
}
