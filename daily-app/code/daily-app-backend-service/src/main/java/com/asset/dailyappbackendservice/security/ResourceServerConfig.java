package com.asset.dailyappbackendservice.security;

import com.asset.dailyappbackendservice.cache.MessageCache;
import com.asset.dailyappbackendservice.cache.UserTokenCache;
import com.asset.dailyappbackendservice.defines.Defines;
import com.asset.dailyappbackendservice.defines.ErrorCodes;
import com.asset.dailyappbackendservice.logger.DailyAppLogger;
import com.asset.dailyappbackendservice.model.UserModel;
import com.asset.dailyappbackendservice.service.PrivilegeService;
import com.asset.dailyappbackendservice.service.RefreshConfigServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Configuration
public class ResourceServerConfig {

    private final ArrayList<String> AUTH_WHITELIST = new ArrayList<>(Arrays.asList(
            // -- Login
            Defines.ContextPaths.LOGIN,
            Defines.ContextPaths.LOGOUT,
            Defines.ContextPaths.HANDLER + Defines.ContextPaths.STORE_TOKEN
    ));

    private final List<String> defaultPrivileges = new ArrayList<>(Arrays.asList(
            Defines.DEFAULT_PATHS.GET_NOTIFICATIONS,
            Defines.DEFAULT_PATHS.ADD_LOGS
    ));
    private final String REFRESH_PATH = Defines.ContextPaths.REFRESH_PATH;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    MessageCache messageCache;
    @Autowired
    RefreshConfigServerService RestService;

    @Autowired
    PrivilegeService privilegeService;

    @Bean
    public GlobalFilter globalFilter() {
        return (exchange, chain) -> {
            String targetUrl = exchange.getRequest().getPath().toString();

            boolean hasPermission = AUTH_WHITELIST.stream().anyMatch(path -> path.equals(targetUrl));
            DailyAppLogger.DEBUG_LOGGER.debug("Received Request context --> [{}] - Has Permission = {}", targetUrl, hasPermission);
            if (!hasPermission) {
                if (!tokenExistsWithinHeaders(exchange))
                    return getResponse(exchange, ErrorCodes.ERROR.USER_NOT_AUTHORIZED, Defines.SEVERITY.ERROR);
                String authorizationToken = exchange.getRequest().getHeaders().get("Authorization").get(0);

                UserModel user = jwtTokenUtil.getUserModelFromToken(authorizationToken);
                if(user == null)
                    return getResponse(exchange, ErrorCodes.ERROR.USER_NOT_AUTHORIZED, Defines.SEVERITY.ERROR);

                Boolean validation = jwtTokenUtil.validateToken.test(authorizationToken) && isTokenCached(user.getUsername(), authorizationToken);
                DailyAppLogger.DEBUG_LOGGER.debug("is valid token = {}", validation);

                if (!validation) {
                    DailyAppLogger.DEBUG_LOGGER.debug("invalid token");
                    return getResponse(exchange, ErrorCodes.ERROR.USER_NOT_AUTHORIZED, Defines.SEVERITY.ERROR);
                }

                if(!profileHasAuthorization(user.getProfileId(), targetUrl))
                    return getResponse(exchange, ErrorCodes.ERROR.PROFILE_NOT_ELIGIBLE, Defines.SEVERITY.ERROR);
            }
            if (REFRESH_PATH.equals(targetUrl)){
                DailyAppLogger.DEBUG_LOGGER.debug("refreshing config service");
                String refreshResponse = RestService.refreshConfigServers();
                return  refreshActuatorResponse(exchange, refreshResponse);
            }
            return chain.filter(exchange);
        };
    }

    private Mono<Void> refreshActuatorResponse(ServerWebExchange exchange, String refreshResponse) {
        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        serverHttpResponse.setStatusCode(HttpStatus.OK);
        int responseOk = ErrorCodes.SUCCESS.SUCCESS;
        String message = messageCache.getSuccessMsg(responseOk);
        String traceId = UUID.randomUUID().toString();
        byte[] response = ("{\"statusCode\":" + responseOk + ",\"statusMessage\":\"" + message + " \",\"traceId\": " +
                "\""+ traceId + "\""+ " ,\"payload\": " + refreshResponse + " }").getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(response);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }

    private Mono<Void> getResponse(ServerWebExchange exchange, int errorCode, int severity) {
        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        serverHttpResponse.setStatusCode(HttpStatus.OK);
        String message = messageCache.getErrorMsg(errorCode);
        byte[] response = ("{\"statusCode\":" + errorCode +
                ",\"statusMessage\":\"" + message +
                " \",\"severity\": " + severity + " }").getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(response);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }

    private Boolean tokenExistsWithinHeaders(ServerWebExchange exchange){
        DailyAppLogger.DEBUG_LOGGER.debug("Checking authorization token header existence");
        if(exchange.getRequest().getHeaders().get("Authorization") == null){
            DailyAppLogger.DEBUG_LOGGER.debug("Authorization-Token is not found in the headers");
            DailyAppLogger.ERROR_LOGGER.error("Authorization-Token is not found in the headers");
            return false;
        }
        DailyAppLogger.DEBUG_LOGGER.debug("Authorization token exists");
        return true;
    }
    private Boolean isTokenCached(String username, String token){
        DailyAppLogger.DEBUG_LOGGER.debug("username extracted from the token = {}", username);
        if(UserTokenCache.userToken.containsKey(username) && token.equals(UserTokenCache.userToken.get(username))) {
            DailyAppLogger.DEBUG_LOGGER.debug("UserToken exists in userTokenCache");
            return true;
        }
        DailyAppLogger.DEBUG_LOGGER.debug("UserToken does not exist in userTokenCache");
        return false;
    }
    private Boolean profileHasAuthorization(Integer profileId, String targetUrl){
        DailyAppLogger.DEBUG_LOGGER.debug("Check profile's privileges of id = {}", profileId);
        boolean found = defaultPrivileges.stream().anyMatch(str -> str.equals(targetUrl));
        if(found) {
            DailyAppLogger.DEBUG_LOGGER.debug("target url matches a default URL");
            return true;
        }
        List<String> profileURLs = privilegeService.getPrivilegeURLsByProfileId(profileId);
        found = profileURLs.stream().anyMatch(str -> str.equals(targetUrl));
        DailyAppLogger.DEBUG_LOGGER.debug("This profile has privilege = {}", found);
        return found;
    }

}
