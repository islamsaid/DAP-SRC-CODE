package com.asset.dailyappreportservice.security;

import com.asset.dailyappreportservice.model.user.UserModel;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {


    public UserModel parseJwt(String token) {
        token = token.substring(7);
        Claims claims = getAllClaimsFromToken.apply(token);

        return new UserModel((Integer) claims.get("userId"), claims.get("username").toString());
    }

    private Function<String, Claims> getAllClaimsFromToken = (token) -> Jwts.parser()
            .setSigningKey("DAILY_APP_2021")
            .parseClaimsJws(token)
            .getBody();
}