/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.dailyapplookupservice.controllers;


import com.asset.dailyapplookupservice.cache.MessageCache;
import com.asset.dailyapplookupservice.defines.Defines;
import com.asset.dailyapplookupservice.defines.ErrorCodes;
import com.asset.dailyapplookupservice.exception.LookupException;
import com.asset.dailyapplookupservice.logger.DailyAppLogger;
import com.asset.dailyapplookupservice.model.response.BaseResponse;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ControllerExceptionInterceptor extends ResponseEntityExceptionHandler {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MessageCache messagesCache;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<BaseResponse> handelAllExceptions(Exception ex, WebRequest req) {
        DailyAppLogger.DEBUG_LOGGER.error(" An error has occurred exc: " + ex.getMessage());
        DailyAppLogger.ERROR_LOGGER.error(" An error has occurred and the error code message: ", ex);
        BaseResponse<String> response = new BaseResponse();
        String traceId=  ThreadContext.get("traceId");
        response.setTraceId(traceId);
        response.setStatusCode(ErrorCodes.ERROR.UNKNOWN_ERROR);
        response.setStatusMessage(messagesCache.getErrorMsg(ErrorCodes.ERROR.UNKNOWN_ERROR));
        response.setSeverity(Defines.SEVERITY.FATAL);
        DailyAppLogger.DEBUG_LOGGER.debug("Api Response is " + response);
        ThreadContext.remove("traceId");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(LookupException.class)
    public final ResponseEntity<BaseResponse> handelUserManagementException(LookupException ex, WebRequest req) {
        DailyAppLogger.DEBUG_LOGGER.error(" An error has occurred exc: " + ex.getArgs());
        DailyAppLogger.ERROR_LOGGER.error(" An error has occurred and the error code message: ", ex);
        DailyAppLogger.DEBUG_LOGGER.debug("create Api Response");
        BaseResponse<String> response = new BaseResponse();
        response.setStatusCode(ex.getErrorCode());
        String msg = messagesCache.getErrorMsg(ex.getErrorCode());
        if (msg.equals(""))
        	 msg = messagesCache.getWarningMsg(ex.getErrorCode());
        if (ex.getArgs() != null) {
            msg = messagesCache.replaceArgument(msg, ex.getArgs());
        }
        response.setStatusMessage(msg);
        String traceId=  ThreadContext.get("traceId");
        response.setTraceId(traceId);
        response.setSeverity(ex.getErrorSeverity());
        ThreadContext.remove("traceId");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
