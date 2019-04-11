package com.njit.zang.model;

import lombok.Data;

/**
 * Created by Administrator on 2019/3/21.
 */
@Data
public class CommentReplyPro {
    private String commentReplyId;

    private String fromUserId;

    private String fromUserNickname;

    private String avatar;

    private String toUserId;

    private String toUserNickname;

    private String repCon;

    private Long repTime;

    private Integer commentId;

    private Integer repType;
}
