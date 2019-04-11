package com.njit.zang.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by Administrator on 2019/4/8.
 */
@Data
@Accessors(chain = true)
public class ChatDto {
    private String uid;
    private String avatar;
    private String nickname;
    private long time;
    private String content;
    private int type = 10;
    private int count = 0;
}
