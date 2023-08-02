package com.asset.dailapp.springcloudconfigserver.model.response;

import com.asset.dailapp.springcloudconfigserver.model.SystemSettingModelForApplication;

import java.util.HashMap;
import java.util.List;

public class GetAllSystemSettingModelResponse {
    HashMap<String, List<SystemSettingModelForApplication>>  systemSettingModelForApplicationHashMap;

    public GetAllSystemSettingModelResponse(HashMap<String, List<SystemSettingModelForApplication>>  systemSettingList) {
        this.systemSettingModelForApplicationHashMap = systemSettingList;
    }

    public HashMap<String, List<SystemSettingModelForApplication>> getSystemSettingModelForApplicationHashMap() {
        return systemSettingModelForApplicationHashMap;
    }

    public void setSystemSettingModelForApplicationHashMap(HashMap<String, List<SystemSettingModelForApplication>> systemSettingModelForApplicationHashMap) {
        this.systemSettingModelForApplicationHashMap = systemSettingModelForApplicationHashMap;
    }
}
