package com.njit.zang.model;

import lombok.Data;

import java.io.Serializable;

/**
 * send_content
 * @author 
 */
@Data
public class SendContent implements Serializable {
    private String feedId;

    /**
     * 该feed的内容
     */
    private String content;

    /**
     * 发布的时间
     */
    private Long releaseTime;

    /**
     * 发表该feed的用户的id
     */
    private String authorId;

    private static final long serialVersionUID = 1L;


}