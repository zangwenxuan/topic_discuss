package com.njit.zang.mapper;

import com.njit.zang.dto.SubscribeNoticeDto;
import com.njit.zang.model.SubscribeNotice;
import com.njit.zang.model.SubscribeNoticeExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface SubscribeNoticeDao {
    long countByExample(SubscribeNoticeExample example);

    int deleteByExample(SubscribeNoticeExample example);

    int deleteByPrimaryKey(String snId);

    int insert(SubscribeNotice record);

    int insertSelective(SubscribeNotice record);

    List<SubscribeNoticeDto> selectUnreadList(String toUserId);

    List<SubscribeNoticeDto> selectHistoryList(String toUserId);

    List<SubscribeNotice> selectByExample(SubscribeNoticeExample example);

    SubscribeNotice selectByPrimaryKey(String snId);

    int updateByExampleSelective(@Param("record") SubscribeNotice record, @Param("example") SubscribeNoticeExample example);

    int updateByExample(@Param("record") SubscribeNotice record, @Param("example") SubscribeNoticeExample example);

    int updateByPrimaryKeySelective(SubscribeNotice record);

    int updateByPrimaryKey(String snId);
}