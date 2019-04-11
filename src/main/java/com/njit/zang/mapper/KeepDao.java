package com.njit.zang.mapper;

import com.njit.zang.model.Keep;
import com.njit.zang.model.KeepExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface KeepDao {
    long countByExample(KeepExample example);

    int deleteByExample(KeepExample example);

    int insert(Keep record);

    int insertSelective(Keep record);

    int deleteByFeedAndUser(@Param("feedId") String feedId,@Param("uid") String uid);

    int insertIgnore(@Param("feedId") String feedId,@Param("uid") String uid);

    int countByFeedId(String feedId);

    String selectByFeedAndUser(@Param("feedId") String feedId,@Param("uid") String uid);

    List<String> selectByUserId(String uid);

    List<Keep> selectByExample(KeepExample example);

    int updateByExampleSelective(@Param("record") Keep record, @Param("example") KeepExample example);

    int updateByExample(@Param("record") Keep record, @Param("example") KeepExample example);
}