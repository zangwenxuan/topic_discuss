package com.njit.zang.mapper;

import com.njit.zang.dto.FeedItem;
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
            "`user`.nickname,\n" +
            "`user`.avatar,\n" +
            " send_content.content,\n" +
            "send_content.author_id as author_id,\n" +
            "send_content.feed_id,\n" +
            " send_content.release_time,\n" +
            "feed_keep.num as keep_num,\n" +
            "feed_like.num as like_num,\n" +
            "IFNULL(feed_comment.num ,0)+ IFNULL(feed_comment_reply.num ,0)as comment_num\n" +
            "FROM\n" +
            "keep INNER JOIN\n" +
            "(send_content,`user`) ON send_content.feed_id = keep.feed_id AND `user`.uid = send_content.author_id LEFT JOIN\n" +
            "(select count(*) as num ,keep.feed_id from keep GROUP BY keep.feed_id) as feed_keep ON send_content.feed_id = feed_keep.feed_id LEFT JOIN\n" +
            "(select count(*) as num ,liked.feed_id from liked GROUP BY liked.feed_id) as feed_like ON send_content.feed_id = feed_like.feed_id LEFT JOIN\n" +
            "(SELECT count(*) as num ,`comment`.send_content_feed_id FROM `comment`  GROUP BY `comment`.send_content_feed_id) as feed_comment ON send_content.feed_id = feed_comment.send_content_feed_id LEFT JOIN\n" +
            "(SELECT count(*) as num ,feed_comment_replys.send_content_feed_id from  (SELECT\n" +
            "           comment_reply.comment_id,\n" +
            "           `comment`.send_content_feed_id,\n" +
            "           comment_reply.comment_reply_id\n" +
            "           FROM\n" +
            "           comment_reply  LEFT JOIN `comment` ON `comment`.comment_id = comment_reply.comment_id)as feed_comment_replys \n" +
            "GROUP BY feed_comment_replys.send_content_feed_id) as feed_comment_reply ON send_content.feed_id = feed_comment_reply.send_content_feed_id\n" +
            "WHERE\n" +
            "keep.uid = #{uid}\n" +
            "ORDER BY\n" +
            "keep.sort DESC")
    List<FeedItem> selectFeedByUid(String uid);

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