package com.njit.zang.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * feed_notice
 * @author 
 */
@Data
@Accessors(chain = true)
public class FeedNotice implements Serializable {
    private String fnId;

    private String fromUserId;

    private String toUserId;

    private String content;

    private Integer read = 0;

    private Long time;

    /**
     * 0：点赞/1：收藏/2：回复评论/3：评论帖子/4：关注/-1：取消点赞/-2：取消收藏/-3：取消关注
     */
    private Integer type;

    private String feedId;

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
        FeedNotice other = (FeedNotice) that;
        return (this.getFnId() == null ? other.getFnId() == null : this.getFnId().equals(other.getFnId()))
            && (this.getFromUserId() == null ? other.getFromUserId() == null : this.getFromUserId().equals(other.getFromUserId()))
            && (this.getToUserId() == null ? other.getToUserId() == null : this.getToUserId().equals(other.getToUserId()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getRead() == null ? other.getRead() == null : this.getRead().equals(other.getRead()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getFeedId() == null ? other.getFeedId() == null : this.getFeedId().equals(other.getFeedId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFnId() == null) ? 0 : getFnId().hashCode());
        result = prime * result + ((getFromUserId() == null) ? 0 : getFromUserId().hashCode());
        result = prime * result + ((getToUserId() == null) ? 0 : getToUserId().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getRead() == null) ? 0 : getRead().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getFeedId() == null) ? 0 : getFeedId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", fnId=").append(fnId);
        sb.append(", fromUserId=").append(fromUserId);
        sb.append(", toUserId=").append(toUserId);
        sb.append(", content=").append(content);
        sb.append(", read=").append(read);
        sb.append(", time=").append(time);
        sb.append(", type=").append(type);
        sb.append(", feedId=").append(feedId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}