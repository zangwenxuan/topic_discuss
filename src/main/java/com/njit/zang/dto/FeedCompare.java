package com.njit.zang.dto;

import com.google.common.primitives.Longs;
import com.njit.zang.model.UserSendContent;

import java.util.Comparator;

/**
 * Created by Administrator on 2019/4/19.
 */
public class FeedCompare implements Comparator<UserSendContent> {

    @Override
    public int compare(UserSendContent userSendContent1,UserSendContent userSendContent2){
        return Longs.compare(userSendContent2.getReleaseTime(),userSendContent1.getReleaseTime());
    }
}
