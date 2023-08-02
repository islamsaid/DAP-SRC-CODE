package com.asset.loggingService.model;

import java.util.List;

public class TrxUserDto {
    private Integer id;
    private int actionId;

    private String actionName;
    private String pageName;
    private int userId;
    private String sessionId;
    private String requestId;
    private String requestBody;
    private String responseBody;
    private String objectIdentifier ;

    public String getObjectIdentifier() {
        return objectIdentifier;
    }

    public void setObjectIdentifier(String objectIdentifier) {
        this.objectIdentifier = objectIdentifier;
    }

    private String userName;
    private String date;
    private List<TransactionUserDetails> transactionUserDetails;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }


    public List<TransactionUserDetails> getTransactionUserDetails() {
        return transactionUserDetails;
    }

    public void setTransactionUserDetails(List<TransactionUserDetails> transactionUserDetails) {
        this.transactionUserDetails = transactionUserDetails;
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
    public String getActionName() { // todo 2 models
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}
