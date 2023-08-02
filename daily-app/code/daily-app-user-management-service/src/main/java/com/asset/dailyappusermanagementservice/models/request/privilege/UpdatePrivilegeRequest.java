package com.asset.dailyappusermanagementservice.models.request.privilege;

import com.asset.dailyappusermanagementservice.models.shared.LkPrivilegeModel;

public class UpdatePrivilegeRequest {
    private LkPrivilegeModel privilege;

    public LkPrivilegeModel getPrivilege() {
        return privilege;
    }

    public void setPrivilege(LkPrivilegeModel privilege) {
        this.privilege = privilege;
    }

    @Override
    public String toString() {
        return "UpdatePrivilegeRequest{" +
                "privilege=" + privilege +
                '}';
    }
}
