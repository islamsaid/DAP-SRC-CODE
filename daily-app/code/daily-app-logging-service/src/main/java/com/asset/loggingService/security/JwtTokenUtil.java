package com.asset.loggingService.security;

import com.asset.loggingService.model.user.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    private final Function<String, Claims> getAllClaimsFromToken = (token) -> Jwts.parser()
            .setSigningKey("DAILY_APP_2021")
            .parseClaimsJws(token)
            .getBody();

    public UserModel getUserModelFromToken(String token) {
        token = token.substring(7);
        Claims claims = getAllClaimsFromToken.apply(token);
        return new UserModel((Integer) claims.get("userId"), claims.get("username").toString());
    }

    public String getSessionId(String token) {
        token = token.substring(7);
        Claims claims = getAllClaimsFromToken.apply(token);
        return claims.get("sessionId").toString();
    }
}