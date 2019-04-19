package com.njit.zang.mapper;

import com.njit.zang.model.ChatNotes;
import com.njit.zang.model.ChatNotesExample;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.DeleteMapping;

public interface ChatNotesDao {
    long countByExample(ChatNotesExample example);

    int deleteByExample(ChatNotesExample example);

    int insert(ChatNotes record);

    int insertSelective(ChatNotes record);

    List<ChatNotes> selectByExample(ChatNotesExample example);

    int updateByExampleSelective(@Param("record") ChatNotes record, @Param("example") ChatNotesExample example);

    int updateByExample(@Param("record") ChatNotes record, @Param("example") ChatNotesExample example);

    @Select("insert into chat_notes (from_user_id,to_user_id)\n" +
            "   select #{fromUserId},#{toUserId}\n" +
            "    from dual\n" +
            "    where NOT EXISTS\n" +
            "    (SELECT * FROM chat_notes\n" +
            "    WHERE from_user_id = #{fromUserId} and to_user_id= #{toUserId})")
    void insertIgnore(@Param("fromUserId") String fromUserId,@Param("toUserId") String toUserId);

    @Delete("delete from chat_notes where from_user_id = #{fromUserId} and to_user_id = #{toUserId}")
    int delete(@Param("fromUserId") String fromUserId,@Param("toUserId") String toUserId);

    @Delete("delete from chat_notes where from_user_id = #{fromUserId}")
    int deleteAllChatNotes(String fromUserId);

    @Select("select to_user_id from chat_notes where from_user_id = #{uid}")
    List<String> selectNoteList(String uid);
}