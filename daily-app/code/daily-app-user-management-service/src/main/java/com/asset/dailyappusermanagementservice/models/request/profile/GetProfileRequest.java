package com.asset.dailyappusermanagementservice.models.request.profile;

import com.asset.dailyappusermanagementservice.models.request.BaseRequest;

public class GetProfileRequest extends BaseRequest {

    private Integer profileId;

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }
}
