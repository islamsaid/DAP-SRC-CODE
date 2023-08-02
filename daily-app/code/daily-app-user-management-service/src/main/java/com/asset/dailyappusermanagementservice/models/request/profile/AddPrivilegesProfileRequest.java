package com.asset.dailyappusermanagementservice.models.request.profile;

import com.asset.dailyappusermanagementservice.models.request.BaseRequest;
import com.asset.dailyappusermanagementservice.models.shared.LkPrivilegeModel;

import java.util.List;

public class AddPrivilegesProfileRequest extends BaseRequest {
    private  int profileId;
    private List<LkPrivilegeModel> privileges;

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public List<LkPrivilegeModel> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<LkPrivilegeModel> privileges) {
        this.privileges = privileges;
    }

    @Override
    public String toString() {
        return "AddPrivilegesProfileRequest{" +
                "profileId=" + profileId +
                ", privileges=" + privileges +
                '}';
    }
}

