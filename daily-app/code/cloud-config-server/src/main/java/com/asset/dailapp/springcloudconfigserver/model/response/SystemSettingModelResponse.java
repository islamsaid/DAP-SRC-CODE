package com.asset.dailapp.springcloudconfigserver.model.response;

import com.asset.dailapp.springcloudconfigserver.model.SystemSettingModelForApplication;

import java.util.List;

public class SystemSettingModelResponse {
    private String applicationName;
    private List<SystemSettingModelForApplication> systemSettingModelForApplications;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public List<SystemSettingModelForApplication> getSystemSettingModelForApplications() {
        return systemSettingModelForApplications;
    }

    public void setSystemSettingModelForApplications(List<SystemSettingModelForApplication> systemSettingModelForApplications) {
        this.systemSettingModelForApplications = systemSettingModelForApplications;
    }
}
