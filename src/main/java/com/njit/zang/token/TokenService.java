package com.njit.zang.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.njit.zang.model.User;
import com.njit.zang.utils.MD5Utils;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2019/3/14.
 */
@Service
public class TokenService {
    public String getToken(User user) {
        String token="";
        token= JWT.create().withAudience(user.getUid())
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
