package com.njit.zang.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.njit.zang.model.User;
import com.njit.zang.utils.MD5Utils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/3/14.
 */
@Service
public class Token {
    public String getToken(User user) {
        String token="";
        token= JWT.create().withAudience(user.getUid())
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
