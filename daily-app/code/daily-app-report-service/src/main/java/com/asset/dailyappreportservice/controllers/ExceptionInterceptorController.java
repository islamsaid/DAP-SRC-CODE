/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.dailyappreportservice.controllers;

import com.asset.dailyappreportservice.cache.MessageCache;
import com.asset.dailyappreportservice.defines.Defines;
import com.asset.dailyappreportservice.defines.ErrorCodes;
import com.asset.dailyappreportservice.exception.ReportsException;
import com.asset.dailyappreportservice.logger.DailyAppLogger;
import com.asset.dailyappreportservice.model.response.BaseResponse;
import org.apache.coyote.Response;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;

@ControllerAdvice
@RestController
public class
ExceptionInterceptorController   {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MessageCache messagesCache;


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public final ResponseEntity<BaseResponse> handelBodyParamExceptions(MethodArgumentNotValidException e) {
        DailyAppLogger.DEBUG_LOGGER.error("invalid body input");
        BaseResponse response = new BaseResponse();
        String traceId=  ThreadContext.get("traceId");
        response.setTraceId(traceId);
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setStatusMessage("invalid input for parameter " + Objects.requireNonNull(e.getFieldError()).getField());
        ThreadContext.remove("traceId");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
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

    @ExceptionHandler(ReportsException.class)
    public final ResponseEntity<BaseResponse> handelUserManagementException(ReportsException ex, WebRequest req) {
        DailyAppLogger.DEBUG_LOGGER.error(" An error has occurred exc: " + ex.getArgs());
        DailyAppLogger.ERROR_LOGGER.error(" An error has occurred and the error code message: ", ex);
        DailyAppLogger.DEBUG_LOGGER.debug("create Api Response");
        BaseResponse<String> response = new BaseResponse();
        String traceId=  ThreadContext.get("traceId");
        response.setTraceId(traceId);
        response.setStatusCode(ex.getErrorCode());
        String msg = messagesCache.getErrorMsg(ex.getErrorCode());
        if (ex.getArgs() != null) {
            msg = messagesCache.replaceArgument(msg, ex.getArgs());
        }
        response.setStatusMessage(msg);
        response.setSeverity(Defines.SEVERITY.ERROR);
        ThreadContext.remove("traceId");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public final ResponseEntity<BaseResponse> handelQueryParamExceptions(MissingServletRequestParameterException ex) {
        DailyAppLogger.DEBUG_LOGGER.error("invalid input");
        BaseResponse response = new BaseResponse();
        String traceId=  ThreadContext.get("traceId");
        response.setTraceId(traceId);
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setStatusMessage("invalid input" + ex.getMessage());
        ThreadContext.remove("traceId");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
