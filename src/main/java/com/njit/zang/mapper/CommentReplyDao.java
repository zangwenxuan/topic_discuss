package com.njit.zang.mapper;

import com.njit.zang.model.CommentReply;
import com.njit.zang.model.CommentReplyExample;
import java.util.List;

import com.njit.zang.model.CommentReplyPro;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentReplyDao {
    long countByExample(CommentReplyExample example);

    int deleteByExample(CommentReplyExample example);

    int deleteByPrimaryKey(String commentReplyId);

    int insert(CommentReply record);

    int insertSelective(CommentReply record);

    int countByFeedId(String feedId);

    List<CommentReplyPro> selectProByCommentId(Integer commentId);

    List<CommentReply> selectByExample(CommentReplyExample example);

    CommentReply selectByPrimaryKey(String commentReplyId);

    @Select("SELECT\n" +
            "`comment`.send_content_feed_id\n" +
            "FROM\n" +
            "comment_reply\n" +
            "INNER JOIN `comment` ON comment_reply.comment_id = `comment`.comment_id\n" +
            "WHERE\n" +
            "comment_reply.comment_reply_id = #{commentReplyId}\n")
    String selectFeedId(@Param("commentReplyId") String commentReplyId);

    int updateByExampleSelective(@Param("record") CommentReply record, @Param("example") CommentReplyExample example);

    int updateByExample(@Param("record") CommentReply record, @Param("example") CommentReplyExample example);

    int updateByPrimaryKeySelective(CommentReply record);

    int updateByPrimaryKey(CommentReply record);
}