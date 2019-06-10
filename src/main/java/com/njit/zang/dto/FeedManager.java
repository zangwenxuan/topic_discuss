package com.njit.zang.dto;

import lombok.Data;

/**
 * Created by Administrator on 2019/5/27.
 */
@Data
public class FeedManager {
    private String feedId;
    private String authorId;
    private String nickname;
    private long releaseTime;
    private int keepNum;
    private int likeNum;
    private int commentNum;
}
