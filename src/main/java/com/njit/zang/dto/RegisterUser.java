package com.njit.zang.dto;

import com.njit.zang.model.User;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by Administrator on 2019/4/12.
 */
@Data
@Accessors(chain = true)
public class RegisterUser {
    private String captcha;

    private User user;
}
