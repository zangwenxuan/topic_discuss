package com.njit.zang.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.checkerframework.checker.units.qual.A;

import java.io.Serializable;

/**
 * user
 * @author 
 */
@Data
@Accessors(chain = true)
public class User implements Serializable {
    private String uid;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    private String avatar;

    private String cover;

    private String signature;

    private static final long serialVersionUID = 1L;

}