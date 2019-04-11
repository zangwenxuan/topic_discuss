package com.njit.zang.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * subscribe_notice
 * @author 
 */
@Data
@Accessors(chain = true)
public class SubscribeNotice implements Serializable {
    private String snId;

    private String fromUserId;

    private String toUserId;

    private String content;

    private Long time;

    private String feedId;

    private Integer read = 0;

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
        SubscribeNotice other = (SubscribeNotice) that;
        return (this.getSnId() == null ? other.getSnId() == null : this.getSnId().equals(other.getSnId()))
            && (this.getFromUserId() == null ? other.getFromUserId() == null : this.getFromUserId().equals(other.getFromUserId()))
            && (this.getToUserId() == null ? other.getToUserId() == null : this.getToUserId().equals(other.getToUserId()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getFeedId() == null ? other.getFeedId() == null : this.getFeedId().equals(other.getFeedId()))
            && (this.getRead() == null ? other.getRead() == null : this.getRead().equals(other.getRead()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSnId() == null) ? 0 : getSnId().hashCode());
        result = prime * result + ((getFromUserId() == null) ? 0 : getFromUserId().hashCode());
        result = prime * result + ((getToUserId() == null) ? 0 : getToUserId().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getFeedId() == null) ? 0 : getFeedId().hashCode());
        result = prime * result + ((getRead() == null) ? 0 : getRead().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", snId=").append(snId);
        sb.append(", fromUserId=").append(fromUserId);
        sb.append(", toUserId=").append(toUserId);
        sb.append(", content=").append(content);
        sb.append(", time=").append(time);
        sb.append(", feedId=").append(feedId);
        sb.append(", read=").append(read);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}