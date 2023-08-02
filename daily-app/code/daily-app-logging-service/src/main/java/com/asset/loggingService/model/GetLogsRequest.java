package com.asset.loggingService.model;

import com.asset.loggingService.model.request.BaseRequest;

public class GetLogsRequest extends BaseRequest {
    String userId;
    long echoDate;
    String date;


    public long getEchoDate() {
        return echoDate;
    }

    public void setEchoDate(long echoDate) {
        this.echoDate = echoDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
