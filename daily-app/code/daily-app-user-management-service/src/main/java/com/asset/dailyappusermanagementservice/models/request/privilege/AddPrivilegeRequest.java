package com.asset.dailyappusermanagementservice.models.request.privilege;

import com.asset.dailyappusermanagementservice.models.request.BaseRequest;
import com.asset.dailyappusermanagementservice.models.shared.LkPrivilegeModel;

public class AddPrivilegeRequest extends BaseRequest {
    LkPrivilegeModel privilege;

    public LkPrivilegeModel getPrivilege() {
        return privilege;
    }

    public void setPrivilege(LkPrivilegeModel privilege) {
        this.privilege = privilege;
    }


}
