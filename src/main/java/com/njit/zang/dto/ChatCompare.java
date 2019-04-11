package com.njit.zang.dto;

import com.google.common.primitives.Longs;
import com.njit.zang.model.Chat;

import java.util.Comparator;

/**
 * Created by Administrator on 2019/4/4.
 */
public class ChatCompare implements Comparator<Chat> {
    @Override
    public int compare(Chat chat1,Chat chat2){
        return Longs.compare(chat1.getTime(),chat2.getTime());
    }
}
