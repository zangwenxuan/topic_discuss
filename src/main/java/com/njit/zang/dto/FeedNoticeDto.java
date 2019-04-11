package com.njit.zang.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by Administrator on 2019/4/1.
 */
@Data
@Accessors(chain = true)
public class FeedNoticeDto {
    private String fnId;

    private String fromUserId;

    private String toUserId;

    private String content;

    private Integer read;

    private Long time;

    /**
     * 0：点赞/1：收藏/2：回复评论/3：评论帖子/4：关注
     */
    private Integer type;

    private String feedId;

    private String nickname;

    private String avatar;
}
