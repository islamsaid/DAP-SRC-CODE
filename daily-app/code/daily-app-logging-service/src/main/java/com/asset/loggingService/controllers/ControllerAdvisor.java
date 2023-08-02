package com.asset.loggingService.controllers;

import com.asset.loggingService.cache.CacheMessages;
import com.asset.loggingService.defines.Defines;
import com.asset.loggingService.defines.ErrorCodes;
import com.asset.loggingService.exceptions.LoggingServiceException;
import com.asset.loggingService.loggers.DailyAppLogger;
import com.asset.loggingService.model.response.BaseResponse;
import com.asset.loggingService.model.response.ResponseModel;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @Autowired
    private CacheMessages cacheMessages;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<BaseResponse> handelAllExceptions(Exception ex, WebRequest req) {
        DailyAppLogger.DEBUG_LOGGER.error(" An error has occurred exc: " + ex.getMessage());
        DailyAppLogger.ERROR_LOGGER.error(" An error has occurred and the error code message: ", ex);
        BaseResponse<String> response = new BaseResponse();
        String traceId=  ThreadContext.get("traceId");
        response.setTraceId(traceId);
        response.setStatusCode(ErrorCodes.ERROR.UNKNOWN_ERROR);
        response.setStatusMessage(cacheMessages.getErrorMsg(ErrorCodes.ERROR.UNKNOWN_ERROR));
        response.setSeverity(Defines.SEVERITY.FATAL);
        DailyAppLogger.DEBUG_LOGGER.debug("Api Response is " + response);
        ThreadContext.remove("traceId");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @ExceptionHandler(LoggingServiceException.class)
    public final ResponseEntity<BaseResponse> handelUserManagementException(LoggingServiceException ex, WebRequest req) {
        DailyAppLogger.DEBUG_LOGGER.error(" An error has occurred exc: " + ex.getArgs());
        DailyAppLogger.ERROR_LOGGER.error(" An error has occurred and the error code message: ", ex);
        DailyAppLogger.DEBUG_LOGGER.debug("create Api Response");
        BaseResponse<String> response = new BaseResponse();
        String traceId=  ThreadContext.get("traceId");
        response.setTraceId(traceId);
        response.setStatusCode(ex.getErrorCode());
        String msg = cacheMessages.getErrorMsg(ex.getErrorCode());
        if (ex.getArgs() != null) {
            msg = cacheMessages.replaceArgument(msg, ex.getArgs());
        }
        response.setStatusMessage(msg);
        response.setSeverity(Defines.SEVERITY.ERROR);
        ThreadContext.remove("traceId");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

