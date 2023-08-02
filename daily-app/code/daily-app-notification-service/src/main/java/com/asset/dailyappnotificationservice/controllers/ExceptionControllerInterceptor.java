package com.asset.dailyappnotificationservice.controllers;

import com.asset.dailyappnotificationservice.cache.MessageCache;
import com.asset.dailyappnotificationservice.defines.Defines;
import com.asset.dailyappnotificationservice.defines.ErrorCodes;
import com.asset.dailyappnotificationservice.exceptions.NotificationsException;
import com.asset.dailyappnotificationservice.logger.DailyAppLogger;
import com.asset.dailyappnotificationservice.models.responses.BaseResponse;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerInterceptor
{
    @Autowired
    MessageCache messagesCache;

    @ExceptionHandler(value = NotificationsException.class)
    public ResponseEntity<BaseResponse> exception(NotificationsException ex) {
        DailyAppLogger.DEBUG_LOGGER.error("A Notifications EXCEPTION has been Received");
        DailyAppLogger.ERROR_LOGGER.error("A Notifications EXCEPTION has been Received");
        String errorMessage = messagesCache.getErrorMsg(ex.getErrorCode());
        if (ex.getArgs() != null)
            errorMessage = messagesCache.replaceArgument(errorMessage, ex.getArgs());
        BaseResponse<String> baseResponse = new BaseResponse(ex.getErrorCode(), errorMessage, Defines.SEVERITY.FATAL, null, null);
        ThreadContext.remove("traceId");
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @ExceptionHandler (value = Exception.class)
    public ResponseEntity<BaseResponse> exception(Exception ex) {
        DailyAppLogger.DEBUG_LOGGER.error("An unknown EXCEPTION has been occurred");
        DailyAppLogger.ERROR_LOGGER.error("An unknown EXCEPTION has been occurred");
        String errorMessage = messagesCache.getErrorMsg(ErrorCodes.ERROR.UNKNOWN_ERROR);
        BaseResponse<String> baseResponse = new BaseResponse(ErrorCodes.ERROR.UNKNOWN_ERROR, errorMessage,
                Defines.SEVERITY.FATAL, null, null);
        ThreadContext.remove("traceId");
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
