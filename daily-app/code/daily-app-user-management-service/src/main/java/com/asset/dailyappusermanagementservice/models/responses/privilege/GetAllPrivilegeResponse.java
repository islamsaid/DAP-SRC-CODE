package com.asset.dailyappusermanagementservice.models.responses.privilege;


import com.asset.dailyappusermanagementservice.models.shared.LkPrivilegeModel;

import java.util.List;

public class GetAllPrivilegeResponse {
    private List<LkPrivilegeModel> privileges;

    public List<LkPrivilegeModel> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<LkPrivilegeModel> privileges) {
        this.privileges = privileges;
    }

    public GetAllPrivilegeResponse(List<LkPrivilegeModel> privileges) {
        this.privileges = privileges;
    }
}
