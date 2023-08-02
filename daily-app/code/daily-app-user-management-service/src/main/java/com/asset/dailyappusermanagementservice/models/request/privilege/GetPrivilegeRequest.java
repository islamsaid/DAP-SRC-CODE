package com.asset.dailyappusermanagementservice.models.request.privilege;

import com.asset.dailyappusermanagementservice.models.request.BaseRequest;

public class GetPrivilegeRequest extends BaseRequest {
    int privilegeId;

    public int getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(int privilegeId) {
        this.privilegeId = privilegeId;
    }
}
