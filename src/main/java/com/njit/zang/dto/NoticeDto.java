package com.njit.zang.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.checkerframework.checker.units.qual.A;

import java.util.List;

/**
 * Created by Administrator on 2019/4/8.
 */
@Data
@Accessors(chain = true)
public class NoticeDto {
    private List<FeedNoticeDto> unreadFeedNotice;

    List<FeedNoticeDto> historyFeedNotice;

    List<SubscribeNoticeDto> unreadSubscribeNotice;

    List<SubscribeNoticeDto> historySubscribeNotice;

}
