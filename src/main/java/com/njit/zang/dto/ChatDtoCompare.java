package com.njit.zang.dto;

import com.google.common.primitives.Longs;
import com.njit.zang.model.Chat;

import java.util.Comparator;

/**
 * Created by Administrator on 2019/4/9.
 */
public class ChatDtoCompare implements Comparator<ChatDto> {
    @Override
    public int compare(ChatDto chatDto1,ChatDto chatDto2){
        return Longs.compare(chatDto2.getTime(),chatDto1.getTime());
    }
}
