package com.njit.zang.model;

import lombok.Data;

import java.io.Serializable;

/**
 * comment
 * @author 
 */
@Data
public class Comment implements Serializable {
    private Integer commentId;

    /**
     * 发表评论的用户的id
     */
    private String userUid;

    /**
     * 该评论对应的帖子的id
     */
    private String sendContentFeedId;

    /**
     * 发表该评论的时间
     */
    private Long time;

    private String comCon;

    private static final long serialVersionUID = 1L;

}