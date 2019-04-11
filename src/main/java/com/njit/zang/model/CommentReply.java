package com.njit.zang.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * comment_reply
 * @author 
 */
@Data
@Accessors(chain = true)
public class CommentReply implements Serializable {
    private String commentReplyId;

    private String fromUserId;

    private String toUserId;

    private String repCon;

    private Long repTime;

    private Integer commentId;

    /**
     * 0：回复评论；1：回复他人回复
     */
    private Integer repType;

    private static final long serialVersionUID = 1L;


    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        CommentReply other = (CommentReply) that;
        return (this.getCommentReplyId() == null ? other.getCommentReplyId() == null : this.getCommentReplyId().equals(other.getCommentReplyId()))
            && (this.getFromUserId() == null ? other.getFromUserId() == null : this.getFromUserId().equals(other.getFromUserId()))
            && (this.getToUserId() == null ? other.getToUserId() == null : this.getToUserId().equals(other.getToUserId()))
            && (this.getRepCon() == null ? other.getRepCon() == null : this.getRepCon().equals(other.getRepCon()))
            && (this.getRepTime() == null ? other.getRepTime() == null : this.getRepTime().equals(other.getRepTime()))
            && (this.getCommentId() == null ? other.getCommentId() == null : this.getCommentId().equals(other.getCommentId()))
            && (this.getRepType() == null ? other.getRepType() == null : this.getRepType().equals(other.getRepType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCommentReplyId() == null) ? 0 : getCommentReplyId().hashCode());
        result = prime * result + ((getFromUserId() == null) ? 0 : getFromUserId().hashCode());
        result = prime * result + ((getToUserId() == null) ? 0 : getToUserId().hashCode());
        result = prime * result + ((getRepCon() == null) ? 0 : getRepCon().hashCode());
        result = prime * result + ((getRepTime() == null) ? 0 : getRepTime().hashCode());
        result = prime * result + ((getCommentId() == null) ? 0 : getCommentId().hashCode());
        result = prime * result + ((getRepType() == null) ? 0 : getRepType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", commentReplyId=").append(commentReplyId);
        sb.append(", fromUserId=").append(fromUserId);
        sb.append(", toUserId=").append(toUserId);
        sb.append(", repCon=").append(repCon);
        sb.append(", repTime=").append(repTime);
        sb.append(", commentId=").append(commentId);
        sb.append(", repType=").append(repType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}