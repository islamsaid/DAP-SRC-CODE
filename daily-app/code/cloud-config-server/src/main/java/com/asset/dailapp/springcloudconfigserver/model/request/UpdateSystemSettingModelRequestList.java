package com.asset.dailapp.springcloudconfigserver.model.request;

import java.util.List;

public class UpdateSystemSettingModelRequestList {
    List<UpdateSystemSettingModelRequest> updateSystemSettingModelRequestList;

    public UpdateSystemSettingModelRequestList() {

    }

    public List<UpdateSystemSettingModelRequest> getUpdateSystemSettingModelRequestList() {
        return updateSystemSettingModelRequestList;
    }

    public void setUpdateSystemSettingModelRequestList(List<UpdateSystemSettingModelRequest> updateSystemSettingModelRequestList) {
        this.updateSystemSettingModelRequestList = updateSystemSettingModelRequestList;
    }

    public UpdateSystemSettingModelRequestList(List<UpdateSystemSettingModelRequest> updateSystemSettingModelRequestList) {
        this.updateSystemSettingModelRequestList = updateSystemSettingModelRequestList;
    }
}
