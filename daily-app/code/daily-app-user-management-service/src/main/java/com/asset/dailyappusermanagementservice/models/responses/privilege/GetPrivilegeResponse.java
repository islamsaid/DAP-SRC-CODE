package com.asset.dailyappusermanagementservice.models.responses.privilege;

import com.asset.dailyappusermanagementservice.models.shared.LkPrivilegeModel;

public class GetPrivilegeResponse {
    LkPrivilegeModel privileges;

    public LkPrivilegeModel getPrivileges() {
        return privileges;
    }

    public void setPrivileges(LkPrivilegeModel privileges) {
        this.privileges = privileges;
    }

    public GetPrivilegeResponse(LkPrivilegeModel privileges) {
        this.privileges = privileges;
    }
}
