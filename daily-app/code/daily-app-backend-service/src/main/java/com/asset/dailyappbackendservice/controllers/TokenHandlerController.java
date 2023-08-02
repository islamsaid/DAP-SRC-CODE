package com.asset.dailyappbackendservice.controllers;

import com.asset.dailyappbackendservice.configration.Properties;
import com.asset.dailyappbackendservice.defines.Defines;
import com.asset.dailyappbackendservice.defines.ErrorCodes;
import com.asset.dailyappbackendservice.logger.DailyAppLogger;
import com.asset.dailyappbackendservice.model.BaseResponse;
import com.asset.dailyappbackendservice.model.UserTokenModel;
import com.asset.dailyappbackendservice.service.TokenHandlerService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(Defines.ContextPaths.HANDLER)

public class TokenHandlerController {

    @Autowired
    TokenHandlerService tokenHandlerService;
    @RequestMapping(value = Defines.ContextPaths.STORE_TOKEN, method = RequestMethod.POST)
    public BaseResponse<String> storeToken(@RequestBody UserTokenModel userTokenModel) {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        DailyAppLogger.DEBUG_LOGGER.debug("----Store Token Request Started----");
        tokenHandlerService.storeToken(userTokenModel);
        ThreadContext.remove("traceId");
        DailyAppLogger.DEBUG_LOGGER.debug("----Store Token Request Ended----");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", 0, traceId, null);
    }

}
