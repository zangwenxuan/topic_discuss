package com.njit.zang.mapper;

import com.njit.zang.dto.FeedItem;
import com.njit.zang.dto.FeedManager;
import com.njit.zang.model.ContentDetails;
import com.njit.zang.model.SendContent;
import com.njit.zang.model.SendContentExample;
import java.util.List;

import com.njit.zang.model.UserSendContent;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.junit.runners.Parameterized;

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

    @Select(" SELECT\n" +
            "      `user`.nickname,\n" +
            "      `user`.avatar,\n" +
            "      `user`.cover,\n" +
            "      `user`.signature,\n" +
            "      send_content.content,\n" +
            "      send_content.author_id as author_id,\n" +
            "      send_content.feed_id,\n" +
            "      send_content.release_time\n" +
            "      FROM\n" +
            "      `user`\n" +
            "      INNER JOIN send_content ON send_content.author_id = `user`.uid\n" +
            "      ORDER BY\n" +
            "      send_content.release_time DESC" +
            "      LIMIT #{pageStart}, 10")
    List<UserSendContent> selectFeedPage(@Param("pageStart") int pageStart);

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
            "send_content\n" +
            "LEFT JOIN `user` ON send_content.author_id = `user`.uid LEFT JOIN\n" +
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
            "ORDER BY\n" +
            "send_content.release_time DESC\n" +
            "LIMIT #{pageStart}, 10")
    List<FeedItem> selectFeedItemPage(@Param("pageStart") int pageStart);

    @Select("SELECT\n" +
            "send_content.feed_id,\n" +
            "send_content.author_id,\n" +
            "`user`.nickname,\n" +
            "send_content.release_time,\n" +
            "feed_keep.num as keep_num,\n" +
            "feed_like.num as like_num,\n" +
            "IFNULL(feed_comment.num ,0)+ IFNULL(feed_comment_reply.num ,0)as comment_num\n" +
            "FROM\n" +
            "send_content LEFT JOIN \n" +
            "`user` ON `user`.uid = send_content.author_id LEFT JOIN\n" +
            "(select count(*) as num ,keep.feed_id from keep GROUP BY keep.feed_id) as feed_keep ON send_content.feed_id = feed_keep.feed_id LEFT JOIN\n" +
            "(select count(*) as num ,liked.feed_id from liked GROUP BY liked.feed_id) as feed_like ON send_content.feed_id = feed_like.feed_id LEFT JOIN\n" +
            "(SELECT count(*) as num ,`comment`.send_content_feed_id FROM `comment`  GROUP BY `comment`.send_content_feed_id) as feed_comment ON send_content.feed_id = feed_comment.send_content_feed_id LEFT JOIN\n" +
            "(SELECT count(*) as num ,feed_comment_replys.send_content_feed_id from  (SELECT\n" +
            "comment_reply.comment_id,\n" +
            "`comment`.send_content_feed_id,\n" +
            "comment_reply.comment_reply_id\n" +
            "FROM\n" +
            "comment_reply  LEFT JOIN `comment` ON `comment`.comment_id = comment_reply.comment_id)as feed_comment_replys GROUP BY feed_comment_replys.send_content_feed_id) as feed_comment_reply ON send_content.feed_id = feed_comment_reply.send_content_feed_id")
    List<FeedManager> selectFeedDetails();

    @Delete("delete from send_content where feed_id = #{feedId}")
    int deleteFeed(String feedId);

    @Select("SELECT\n" +
            "`user`.nickname,\n" +
            "`user`.avatar,\n" +
            " send_content.content,\n" +
            " send_content.author_id as author_id,\n" +
            "send_content.feed_id,\n" +
            " send_content.release_time\n" +
            "FROM\n" +
            "send_content\n" +
            "LEFT JOIN `user` ON send_content.author_id = `user`.uid\n" +
            "WHERE send_content.feed_id = #{feedId}")
    FeedItem selectFeedItemByFeedId(@Param("feedId") String feedId);

    @Select("SELECT\n" +
            "send_content.feed_id,\n" +
            "send_content.content,\n" +
            "send_content.release_time,\n" +
            "send_content.author_id,\n" +
            "`user`.nickname,\n" +
            "`user`.avatar,\n" +
            "`user`.cover,\n" +
            "`user`.signature\n" +
            "FROM\n" +
            "send_content \n" +
            "LEFT JOIN \n" +
            "`user` on `user`.uid AND send_content.author_id = `user`.uid \n" +
            "WHERE\n" +
            "`user`.uid in (select follow.`master` from follow where follow.follower = #{uid})\n" +
            "ORDER BY send_content.release_time DESC \n" +
            "LIMIT #{pageStart},10\n")
    List<UserSendContent> selectSubscribe(@Param("pageStart") int pageStart,@Param("uid")String uid);

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
            "send_content\n" +
            "LEFT JOIN `user` ON send_content.author_id = `user`.uid LEFT JOIN\n" +
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
            "`user`.uid in (select follow.`master` from follow where follow.follower = #{uid})\n" +
            "ORDER BY\n" +
            "send_content.release_time DESC\n" +
            "LIMIT #{pageStart}, 10")
    List<FeedItem> selectItemSubscribe(@Param("pageStart") int pageStart,@Param("uid")String uid);

    @Select("SELECT\n" +
            "send_content.feed_id,\n" +
            "send_content.content,\n" +
            "send_content.release_time,\n" +
            "send_content.author_id,\n" +
            "`user`.nickname,\n" +
            "`user`.avatar,\n" +
            "`user`.cover,\n" +
            "`user`.signature\n" +
            "FROM\n" +
            "(send_content, send_content_has_theme)\n" +
            "LEFT JOIN `user` ON `user`.uid = send_content.author_id\n" +
            "WHERE\n" +
            "send_content_has_theme.theme_name = #{themeName} AND\n" +
            "send_content.feed_id = send_content_has_theme.send_content_feed_id\n" +
            "ORDER BY send_content.release_time DESC\n" +
            "LIMIT #{pageStart},10\n")
    List<UserSendContent> selectByTheme(@Param("pageStart") int pageStart,@Param("themeName") String themeName);

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
            "(send_content, send_content_has_theme)\n" +
            "LEFT JOIN `user` ON send_content.author_id = `user`.uid LEFT JOIN\n" +
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
            "send_content_has_theme.theme_name = #{themeName} AND\n" +
            "send_content.feed_id = send_content_has_theme.send_content_feed_id\n" +
            "ORDER BY\n" +
            "send_content.release_time DESC\n" +
            "LIMIT #{pageStart}, 10")
    List<FeedItem> selectFeedItemByTheme(@Param("pageStart") int pageStart,@Param("themeName") String themeName);

    List<SendContent> selectByExample(SendContentExample example);

    ContentDetails selectContentDetails(String feedId);

    SendContent selectByPrimaryKey(String feedId);

    int updateByExampleSelective(@Param("record") SendContent record, @Param("example") SendContentExample example);

    int updateByExample(@Param("record") SendContent record, @Param("example") SendContentExample example);

    int updateByPrimaryKeySelective(SendContent record);

    int updateByPrimaryKey(SendContent record);
}