package com.njit.zang.mapper;

import com.njit.zang.model.Like;
import com.njit.zang.model.LikeExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface LikeDao {
    long countByExample(LikeExample example);

    int deleteByExample(LikeExample example);

    int insert(Like record);

    int insertSelective(Like record);

    int deleteByFeedAndUser(@Param("feedId") String feedId,@Param("uid") String uid);

    int insertIgnore(@Param("feedId") String feedId,@Param("uid") String uid);

    int countByFeedId(String feedId);

    String selectByFeedAndUser(@Param("feedId") String feedId,@Param("uid") String uid);

    List<String> selectByUserId(String uid);

    List<Like> selectByExample(LikeExample example);

    int updateByExampleSelective(@Param("record") Like record, @Param("example") LikeExample example);

    int updateByExample(@Param("record") Like record, @Param("example") LikeExample example);
}