package com.asset.loggingService.model;

import com.asset.loggingService.model.request.BaseRequest;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TrxUserRequest extends BaseRequest {

    private Integer id;
    private String pageName;
    private int actionId;
    private int userId;
    private String sessionId;
    private String requestId;
    private String requestBody;
    private String responseBody;
    private String objectIdentifier ;

    private ConcurrentHashMap<String, Object> oldValue;
    private  ConcurrentHashMap<String, Object> newValue;

    public String getObjectIdentifier() {
        return objectIdentifier;
    }

    public void setObjectIdentifier(String objectIdentifier) {
        this.objectIdentifier = objectIdentifier;
    }

    public ConcurrentHashMap<String, Object> getOldValue() {
        return oldValue;
    }

    public void setOldValue(ConcurrentHashMap<String, Object> oldValue) {
        this.oldValue = oldValue;
    }

    public ConcurrentHashMap<String, Object> getNewValue() {
        return newValue;
    }

    public void setNewValue(ConcurrentHashMap<String, Object> newValue) {
        this.newValue = newValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

}