package com.njit.zang.mapper;

import com.njit.zang.dto.FeedItem;
import com.njit.zang.dto.UserCard;
import com.njit.zang.dto.UserManager;
import com.njit.zang.model.User;
import com.njit.zang.model.UserExample;
import java.util.List;

import com.njit.zang.model.UserSendContent;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(String uid);

    int insert(User record);

    int insertSelective(User record);

    @Select("select * from `user` where nickname = #{name}")
    User checkName(@Param("name") String name);

    @Select("select * from `user` where email = #{email}")
    User checkEmail(@Param("email") String email);

    @Select("select * from `user` where nickname = #{nickname}")
    User selectUserByName(@Param("nickname")String nickname);

    @Select("select * from `user` where email = #{email}")
    User selectUserByEmail(@Param("email") String email);

    @Update("update user set avatar = #{avatar} where uid = #{uid} ")
    int updateAvatar(@Param("avatar") String avatar,@Param("uid") String uid);

    @Update("update user set cover = #{cover} where uid = #{uid} ")
    int updateCover(@Param("cover") String cover,@Param("uid") String uid);

    @Update("update user set signature = #{signature} where uid = #{uid} ")
    int updateSignature(@Param("signature") String signature,@Param("uid") String uid);

    @Update("update user set password = #{password} where uid = #{uid}")
    int updatePassword(@Param("password") String password,@Param("uid") String uid);

    @Update("update user set email = #{email} where uid = #{uid}")
    int updateEmail(@Param("email") String email,@Param("uid") String uid);

    @Select("SELECT\n" +
            "`user`.uid,\n" +
            "`user`.nickname,\n" +
            "`user`.avatar,\n" +
            "`user`.cover,\n" +
            "`user`.signature,\n" +
            "user_follower.num as follower_num,\n" +
            "user_following.num as following_num,\n" +
            "user_feed.num as feed_num\n" +
            "FROM\n" +
            "`user`\n" +
            "LEFT JOIN\n" +
            "(SELECT count(*) as num ,follow.`master` from follow GROUP BY follow.`master`) as user_follower ON `user`.uid = user_follower.`master` LEFT JOIN\n" +
            "(SELECT count(*) as num ,follow.follower from follow GROUP BY follow.follower)as user_following ON `user`.uid = user_following.follower LEFT JOIN\n" +
            "(select count(*) as num ,send_content.author_id from send_content GROUP BY send_content.author_id) as user_feed ON `user`.uid = user_feed.author_id\n" +
            "where uid = #{uid}\n")
    UserCard queryUserCard(@Param("uid") String uid);

    @Select("SELECT\n" +
            "`user`.uid,\n" +
            "`user`.nickname,\n" +
            "`user`.avatar,\n" +
            "`user`.signature\n" +
            "FROM\n" +
            "`user`\n" +
            "WHERE\n" +
            "`user`.nickname LIKE #{nickname}")
    List<User> searchUser(String nickname);

    @Select("SELECT\n" +
            "`user`.uid,\n" +
            "`user`.email,\n" +
            "`user`.nickname,\n" +
            "feed_temp.num as feed_num,\n" +
            "follower.num as follower_num,\n" +
            "following.num as following_num\n" +
            "FROM\n" +
            "`user` LEFT JOIN \n" +
            "(select count(*) as num ,send_content.author_id from send_content GROUP BY send_content.author_id) as feed_temp ON `user`.uid = feed_temp.author_id LEFT JOIN\n" +
            "(select count(*) as num ,follow.`master` from follow GROUP BY follow.`master`) as follower ON `user`.uid = follower.`master` LEFT JOIN\n" +
            "(select count(*) as num ,follow.follower from follow GROUP BY follow.follower) as following ON following.follower = `user`.uid\n")
    List<UserManager> selectUserManagerList();

    @Delete("delete from `user` where uid = #{uid}")
    int deleteUser(@Param("uid") String uid);

    List<User> selectAllUsers();

    UserSendContent selectContentByFeedId(String feedId);

    List<UserSendContent> selectContentByUid(String uid);

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
            "`user`.uid = #{uid}\n" +
            "ORDER BY\n" +
            "send_content.release_time DESC")
    List<FeedItem> selectFeedItemByUid(@Param("uid") String uid);

    List<UserSendContent> selectAllContent();

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(String uid);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}