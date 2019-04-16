package com.njit.zang.mapper;

import com.njit.zang.model.ContentDetails;
import com.njit.zang.model.SendContent;
import com.njit.zang.model.SendContentExample;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SendContentDao {
    long countByExample(SendContentExample example);

    int deleteByExample(SendContentExample example);

    int deleteByPrimaryKey(String feedId);

    int insert(SendContent record);

    int insertSelective(SendContent record);

    int countByUid(String uid);

    @Select("SELECT\n" +
            "send_content.author_id\n" +
            "FROM\n" +
            "send_content\n" +
            "WHERE\n" +
            "send_content.feed_id = #{feedId}\n")
    String selectUidByFeedId(@Param("feedId") String feedId);

    @Select("SELECT\n" +
            "send_content.feed_id\n" +
            "FROM\n" +
            "send_content\n" +
            "WHERE\n" +
            "send_content.author_id = #{uid}\n")
    List<String> selectFeedListByUid(@Param("uid") String uid);

    @Delete("delete from send_content where feed_id = #{feedId}")
    int deleteFeed(String feedId);

    List<SendContent> selectByExample(SendContentExample example);

    ContentDetails selectContentDetails(String feedId);

    SendContent selectByPrimaryKey(String feedId);

    int updateByExampleSelective(@Param("record") SendContent record, @Param("example") SendContentExample example);

    int updateByExample(@Param("record") SendContent record, @Param("example") SendContentExample example);

    int updateByPrimaryKeySelective(SendContent record);

    int updateByPrimaryKey(SendContent record);
}