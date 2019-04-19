package com.njit.zang.mapper;

import com.njit.zang.model.Like;
import com.njit.zang.model.LikeExample;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;

@Mapper
public interface LikeDao {
    long countByExample(LikeExample example);

    int deleteByExample(LikeExample example);

    int insert(Like record);

    int insertSelective(Like record);

    int deleteByFeedAndUser(@Param("feedId") String feedId,@Param("uid") String uid);

    int insertIgnore(@Param("feedId") String feedId,@Param("uid") String uid);

    @Select("select feed_id from liked where uid = #{uid} and feed_id = #{feedId}")
    String selectByLike(@Param("uid") String uid, @Param("feedId")String feedId);

    @Insert("insert into liked (feed_id,uid)values(#{feedId},#{uid})")
    int insertLike(@Param("uid")String uid,@Param("feedId")String feedId);

    @Delete("delete from liked where uid = #{uid} and feed_id = #{feedId}")
    int deleteLike(@Param("uid")String uid,@Param("feedId")String feedId);

    int countByFeedId(String feedId);

    String selectByFeedAndUser(@Param("feedId") String feedId,@Param("uid") String uid);

    List<String> selectByUserId(String uid);

    List<Like> selectByExample(LikeExample example);

    int updateByExampleSelective(@Param("record") Like record, @Param("example") LikeExample example);

    int updateByExample(@Param("record") Like record, @Param("example") LikeExample example);
}