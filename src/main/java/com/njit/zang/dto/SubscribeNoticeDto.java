package com.njit.zang.dto;

import com.sun.net.httpserver.Filter;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by Administrator on 2019/4/1.
 */
@Data
@Accessors(chain = true)
public class SubscribeNoticeDto {
    private String snId;

    private String fromUserId;

    private String toUserId;

    private String content;

    private Long time;

    private String feedId;

    private Integer read;

    private String nickname;

    private String avatar;
}
