package com.asset.dailyappusermanagementservice.models.request.profile;

import com.asset.dailyappusermanagementservice.models.request.BaseRequest;
import com.asset.dailyappusermanagementservice.models.user.UserProfileModel;

public class AddProfileRequest extends BaseRequest {

    private UserProfileModel profile;

    public UserProfileModel getProfile() {
        return profile;
    }

    public void setProfile(UserProfileModel profile) {
        this.profile = profile;
    }

}
