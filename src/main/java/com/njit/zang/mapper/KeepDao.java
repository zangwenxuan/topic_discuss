package com.njit.zang.mapper;

import com.njit.zang.model.Keep;
import com.njit.zang.model.KeepExample;
import java.util.List;

import com.njit.zang.model.UserSendContent;
import org.apache.ibatis.annotations.*;

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

    @Select("SELECT\n" +
            "keep.feed_id,\n" +
            "send_content.content,\n" +
            "send_content.release_time,\n" +
            "send_content.author_id,\n" +
            "`user`.nickname,\n" +
            "`user`.avatar\n" +
            "FROM\n" +
            "keep INNER JOIN\n" +
            "(send_content,`user`) ON send_content.feed_id = keep.feed_id AND `user`.uid = send_content.author_id\n" +
            "WHERE\n" +
            "keep.uid = #{uid}\n" +
            "order by keep.sort desc")
    List<UserSendContent> selectFeedByUid(String uid);

    @Select("select feed_id from keep where uid = #{uid} and feed_id = #{feedId}")
    String selectByKeep(@Param("uid") String uid, @Param("feedId")String feedId);

    @Insert("insert into keep (feed_id,uid)values(#{feedId},#{uid})")
    int insertKeep(@Param("uid")String uid,@Param("feedId")String feedId);

    @Delete("delete from keep where uid = #{uid} and feed_id = #{feedId}")
    int deleteKeep(@Param("uid")String uid,@Param("feedId")String feedId);

    List<Keep> selectByExample(KeepExample example);

    int updateByExampleSelective(@Param("record") Keep record, @Param("example") KeepExample example);

    int updateByExample(@Param("record") Keep record, @Param("example") KeepExample example);
}