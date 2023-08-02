package com.asset.dailyappbackendservice.security;

import com.asset.dailyappbackendservice.configration.Properties;
import com.asset.dailyappbackendservice.logger.DailyAppLogger;
import com.asset.dailyappbackendservice.model.UserModel;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.function.Function;
import java.util.function.Predicate;

@Component
public class JwtTokenUtil {

    @Autowired
    Properties properties;
    Predicate<String> tokenExpiration = (token) -> {
        Date expiration = getClaimFromToken(token, Claims::getExpiration);
        return expiration.before(new Date());
    };
    private Function<String, Claims> getAllClaimsFromToken = (token) -> Jwts.parser()
            .setSigningKey(properties.getAccessTokenKey())
            .parseClaimsJws(token)
            .getBody();
    Predicate<String> validateToken = (token) -> {
        try {
            DailyAppLogger.DEBUG_LOGGER.debug("Validating on token..");
            token = token.substring(7);
            if (tokenExpiration.test(token)) {
                DailyAppLogger.DEBUG_LOGGER.debug("token expired");
                return false;
            }
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.debug("an error occurred : " + ex.getMessage());
            DailyAppLogger.DEBUG_LOGGER.info("an error occurred ");
            DailyAppLogger.ERROR_LOGGER.error("Failed to validate Token " + ex.getMessage(), ex);
            return false;
        }
        DailyAppLogger.DEBUG_LOGGER.debug("token-expiry has been validated");
        return true;
    };

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken.apply(token);
        return claimsResolver.apply(claims);
    }
    public String destroyToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(properties.getAccessTokenKey()).parseClaimsJws(token).getBody();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        DailyAppLogger.DEBUG_LOGGER.info("System Current Time = " + now);
        token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(now)
                .signWith(SignatureAlgorithm.HS256, properties.getAccessTokenKey().trim())
                .compact();
        return token;
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        DailyAppLogger.DEBUG_LOGGER.debug("Expiry == " + expiration);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public UserModel getUserModelFromToken(String token) {
        token = token.substring(7);
        try {
            Claims claims = getAllClaimsFromToken.apply(token);
            return new UserModel((Integer) claims.get("userId"),
                    claims.get("username").toString(), (Integer) claims.get("profileId"));
        }catch (Exception ex){
            DailyAppLogger.DEBUG_LOGGER.error("Exception occurred during getAllClaimsFromToken execution ", ex);
            DailyAppLogger.ERROR_LOGGER.error("Exception occurred during getAllClaimsFromToken execution ", ex);
            return null;
        }


    }

}
