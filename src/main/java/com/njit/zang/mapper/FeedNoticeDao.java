package com.njit.zang.mapper;

import com.njit.zang.dto.FeedNoticeDto;
import com.njit.zang.model.FeedNotice;
import com.njit.zang.model.FeedNoticeExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface FeedNoticeDao {
    long countByExample(FeedNoticeExample example);

    int deleteByExample(FeedNoticeExample example);

    int deleteByPrimaryKey(String fnId);

    int insert(FeedNotice record);

    int insertSelective(FeedNotice record);

    List<FeedNoticeDto> selectUnreadList(String toUserId);

    List<FeedNoticeDto> selectHistoryList(String toUserId);

    List<FeedNotice> selectByExample(FeedNoticeExample example);

    FeedNotice selectByPrimaryKey(String fnId);

    int updateByExampleSelective(@Param("record") FeedNotice record, @Param("example") FeedNoticeExample example);

    int updateByExample(@Param("record") FeedNotice record, @Param("example") FeedNoticeExample example);

    int updateByPrimaryKeySelective(FeedNotice record);

    int updateByPrimaryKey(String fnId);
}