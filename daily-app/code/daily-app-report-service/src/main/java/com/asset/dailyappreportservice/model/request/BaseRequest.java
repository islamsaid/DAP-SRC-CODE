package com.asset.dailyappreportservice.model.request;

import java.io.Serializable;

public class BaseRequest implements Serializable {

    private String token;
    private int UserId;
    private String sessionId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

}

