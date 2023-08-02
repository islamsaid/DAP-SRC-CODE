package com.asset.dailyappusermanagementservice.models.request.privilege;

import com.asset.dailyappusermanagementservice.models.request.BaseRequest;

public class DeletePrivilegeRequest extends BaseRequest {
    private Integer privilegeId;

    public Integer getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Integer privilegeId) {
        this.privilegeId = privilegeId;
    }
}
