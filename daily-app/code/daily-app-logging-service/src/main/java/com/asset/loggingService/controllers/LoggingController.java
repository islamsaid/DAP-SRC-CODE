package com.asset.loggingService.controllers;

import com.asset.loggingService.cache.PropertiesCache;
import com.asset.loggingService.constants.ApiEndPoints;
import com.asset.loggingService.defines.ErrorCodes;
//import com.asset.loggingService.executor.LogExecutor;
import com.asset.loggingService.executor.LogExecutor;
import com.asset.loggingService.loggers.DailyAppLogger;
import com.asset.loggingService.model.GetLogsRequest;
import com.asset.loggingService.model.TrxUserRequest;
import com.asset.loggingService.model.response.BaseResponse;
import com.asset.loggingService.model.response.GetLogsResponse;
import com.asset.loggingService.service.LoggingService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping(value = ApiEndPoints.LOGGING_MAPPING)
public class LoggingController {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    PropertiesCache propertiesCache;

    @Autowired
    private LogExecutor logExecutor;

    @Autowired
    LoggingService loggingService;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = ApiEndPoints.ADD_LOGS, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse addLogs(HttpServletRequest request, @RequestBody TrxUserRequest trxUserRequest) {
        DailyAppLogger.DEBUG_LOGGER.debug("GET TOKEN FROM REQUEST: "+request);
        String token= request.getHeader("Authorization");
        trxUserRequest.setToken(token);
        DailyAppLogger.DEBUG_LOGGER.debug("add LOG request has been received");
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        logExecutor.prepareExecute(trxUserRequest);
        ThreadContext.remove("traceId");
        DailyAppLogger.DEBUG_LOGGER.debug("logs  added successfully");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, null);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = ApiEndPoints.get_LOGS, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse<GetLogsResponse> getLogs(HttpServletRequest request, @RequestBody GetLogsRequest getLogsRequest) {
        DailyAppLogger.DEBUG_LOGGER.debug("get  logs by userID and date request has been received");
        String token= request.getHeader("Authorization");
        getLogsRequest.setToken(token);
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        GetLogsResponse getLogsResponse = loggingService.getLogs(getLogsRequest);
        ThreadContext.remove("traceId");
        DailyAppLogger.DEBUG_LOGGER.debug("get logs  successfully");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, getLogsResponse);
    }
}
