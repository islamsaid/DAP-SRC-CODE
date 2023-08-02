package com.asset.dailyappusermanagementservice.controllers;

import com.asset.dailyappusermanagementservice.defines.Defines;
import com.asset.dailyappusermanagementservice.defines.ErrorCodes;
import com.asset.dailyappusermanagementservice.logger.DailyAppLogger;
import com.asset.dailyappusermanagementservice.models.request.LoginRequest;
import com.asset.dailyappusermanagementservice.models.responses.BaseResponse;
import com.asset.dailyappusermanagementservice.models.responses.LoginResponse;
import com.asset.dailyappusermanagementservice.service.UserService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*")
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    Environment environment;

    @RequestMapping(value = Defines.ContextPaths.LOGIN, method = RequestMethod.POST)
    public BaseResponse<LoginResponse> userLogin(HttpServletRequest req, @RequestBody LoginRequest loginRequest) throws Exception {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        LoginResponse response = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        DailyAppLogger.DEBUG_LOGGER.info("IP => " + InetAddress.getLocalHost().getHostAddress() + ":" + environment.getProperty("server.port"));
        DailyAppLogger.INFO_LOGGER.info("IP => " + InetAddress.getLocalHost().getHostAddress() + ":" + environment.getProperty("server.port"));
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", 0, traceId, response);
    }

    @RequestMapping(value = Defines.ContextPaths.LOGOUT, method = RequestMethod.POST)
    public BaseResponse<String> Logout(HttpServletRequest req, @RequestBody LoginRequest userRequest) {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        DailyAppLogger.DEBUG_LOGGER.debug("Logout request received...");
        userService.endUserToken(userRequest.getUsername());
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", 0, traceId, null);
    }
}

