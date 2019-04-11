package com.njit.zang.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2019/3/26.
 */
@Data
public class Feed {
    private String authorId;
    private String content;
    private List pic;
    private List themeList;
}
