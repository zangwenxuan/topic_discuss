package com.njit.zang.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2019/3/29.
 */
@Data
public class LikeKeepList {
    private List<Liked> likeList;
    private List<Kept> keepList;
    private String uid;
}
