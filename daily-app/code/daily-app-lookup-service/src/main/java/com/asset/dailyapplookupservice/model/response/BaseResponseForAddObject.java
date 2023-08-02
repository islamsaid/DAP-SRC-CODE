package com.asset.dailyapplookupservice.model.response;

public class BaseResponseForAddObject {

    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BaseResponseForAddObject(String id) {
        this.id = id;
    }
}
