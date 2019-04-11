package com.njit.zang.dto;

import lombok.Data;

/**
 * Created by Administrator on 2019/3/29.
 */
@Data
public class LikeKeep {
    private String uid;
    private String feedId;
    private Boolean isLiked;
    private Boolean isKeep;
}
