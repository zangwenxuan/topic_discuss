package com.njit.zang.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/3/21.
 */
@Data
public class CommentUser implements Serializable {
    private Integer commentId;
    private String userUid;
    private Long time;
    private String comCon;
    private String nickname;
    private String avatar;
    private List<CommentReplyPro> commentReplyList;

    public void fillNickname(){
        commentReplyList.stream().forEach(c->{
            if(c.getRepType()==1){
                commentReplyList.stream().forEach(comR->{
                    if(comR.getFromUserId().equals(c.getToUserId())){
                        c.setToUserNickname(comR.getFromUserNickname());
                    }
                });
            }
        });
    }

    public void fillAvatar(){
        if(avatar!=""){
            avatar = "http://localhost:8080/pic/"+avatar;
        }
    }
}
