package com.asset.dailyappusermanagementservice.security;

import com.asset.dailyappusermanagementservice.configurations.Properties;
import com.asset.dailyappusermanagementservice.defines.Defines;
import com.asset.dailyappusermanagementservice.defines.ErrorCodes;
import com.asset.dailyappusermanagementservice.exception.UserManagementException;
import com.asset.dailyappusermanagementservice.logger.DailyAppLogger;
import com.asset.dailyappusermanagementservice.models.request.BaseRequest;
import com.asset.dailyappusermanagementservice.models.user.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.util.Date;
import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {
    @Autowired
    private Properties properties;


    public String generateToken(UserModel user) throws UserManagementException {
        return doGenerateToken(user);
    }

    private String doGenerateToken(UserModel user) throws UserManagementException {
        String token = "";
        try {
            UUID uuid = UUID.randomUUID();
            Claims claims = Jwts.claims().setSubject(user.getUsername());
            claims.put(Defines.SecurityKeywords.PREFIX, Defines.SecurityKeywords.PREFIX);
            claims.put(Defines.SecurityKeywords.SESSION_ID, uuid.toString());
            claims.put(Defines.SecurityKeywords.USERNAME, user.getUsername());
            claims.put(Defines.SecurityKeywords.USER_ID, user.getUserId());
            claims.put(Defines.SecurityKeywords.PROFILE_ID, user.getProfileId());

            long accessTokenValidityMilli = (long) (properties.getAccessTokenValidity() * 60 * 60 * 1000);

            DailyAppLogger.DEBUG_LOGGER.debug("accessTokenValidityHour : " + properties.getAccessTokenValidity() + "  =  accessTokenValidityMilli : " + accessTokenValidityMilli);
            token = Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidityMilli))
                    .signWith(SignatureAlgorithm.HS256, properties.getAccessTokenKey().trim())
                    .compact();
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.info("an error occurred ");
            DailyAppLogger.DEBUG_LOGGER.debug("an error occurred : " + ex.getMessage());
            DailyAppLogger.ERROR_LOGGER.error("an error occurred  " + ex.getMessage(), ex);
            throw new UserManagementException(ErrorCodes.ERROR.CANNOT_GENERATE_TOKEN);
        }
        DailyAppLogger.DEBUG_LOGGER.debug("token generated");
        return token;
    }
    public UserModel getUserModelFromToken(String token) {
        token = token.substring(7);
        Claims claims = getAllClaimsFromToken.apply(token);

        return new UserModel((Integer) claims.get("userId"), claims.get("username").toString());
    }

    private Function<String, Claims> getAllClaimsFromToken = (token) -> Jwts.parser()
            .setSigningKey(properties.getAccessTokenKey())
            .parseClaimsJws(token)
            .getBody();

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        DailyAppLogger.DEBUG_LOGGER.debug("Expiry == " + expiration);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken.apply(token);
        return claimsResolver.apply(claims);
    }

    public String destroyToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(properties.getAccessTokenKey()).parseClaimsJws(token).getBody();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        DailyAppLogger.DEBUG_LOGGER.info("System Current Time = " + now);
        DailyAppLogger.ERROR_LOGGER.info("System Current Time = " + now);
        token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(now)
                .signWith(SignatureAlgorithm.HS256, properties.getAccessTokenKey().trim())
                .compact();
        return token;
    }

}

