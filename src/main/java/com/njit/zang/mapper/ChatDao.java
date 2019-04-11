package com.njit.zang.mapper;

import com.njit.zang.model.Chat;
import com.njit.zang.model.ChatExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ChatDao {
    long countByExample(ChatExample example);

    int deleteByExample(ChatExample example);

    int insert(Chat record);

    int insertSelective(Chat record);

    @Select("SELECT\n" +
            "chat.from_user_id\n" +
            "FROM\n" +
            "chat\n" +
            "WHERE\n" +
            "chat.to_user_id = #{uid}\n" +
            "GROUP BY\n" +
            "chat.from_user_id\n")
    List<String> selectUserList(@Param("uid") String uid);

    @Select("SELECT\n" +
            "chat.from_user_id,\n" +
            "chat.to_user_id,\n" +
            "chat.message,\n" +
            "chat.time,\n" +
            "chat.`read`\n" +
            "FROM\n" +
            "chat\n" +
            "WHERE\n" +
            "chat.to_user_id = #{toUserId} AND\n" +
            "chat.from_user_id = #{fromUserId} AND\n" +
            "chat.`read` = 0\n" +
            "ORDER BY\n" +
            "chat.time DESC\n")
    List<Chat> selectUnreadChatList(@Param("fromUserId") String fromUserId, @Param("toUserId")String toUserId);

    @Select("SELECT\n" +
            "chat.from_user_id,\n" +
            "chat.to_user_id,\n" +
            "chat.message,\n" +
            "chat.time,\n" +
            "chat.`read`\n" +
            "FROM\n" +
            "chat\n" +
            "WHERE\n" +
            "chat.to_user_id = #{toUserId} AND\n" +
            "chat.from_user_id = #{fromUserId} AND\n" +
            "chat.`read` = -1\n" +
            "ORDER BY\n" +
            "chat.time DESC\n" +
            "LIMIT 1\n")
    Chat selectHistoryChat(@Param("fromUserId") String fromUserId,@Param("toUserId")String toUserId);

    @Select("UPDATE chat SET `read` = -1 " +
            "WHERE " +
            "from_user_id = #{fromUserId} " +
            "and to_user_id = #{toUserId} " +
            "and `read` = 0")
    void updateChatNotice(@Param("fromUserId") String fromUserId,@Param("toUserId")String toUserId);

    @Select("UPDATE chat SET `read` = -1 " +
            "WHERE " +
            "to_user_id = #{uid} " +
            "and `read` = 0")
    void clearReadStatusByUid(@Param("uid") String uid);

    List<Chat> selectMessage(@Param("fromUserId")String fromUserId, @Param("toUserId")String toUserId);

    List<Chat> selectByExample(ChatExample example);

    int updateByExampleSelective(@Param("record") Chat record, @Param("example") ChatExample example);

    int updateByExample(@Param("record") Chat record, @Param("example") ChatExample example);
}