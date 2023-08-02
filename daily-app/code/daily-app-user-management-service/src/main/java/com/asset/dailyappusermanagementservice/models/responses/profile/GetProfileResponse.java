package com.asset.dailyappusermanagementservice.models.responses.profile;

import com.asset.dailyappusermanagementservice.models.user.UserProfileModel;

public class GetProfileResponse   {
    private UserProfileModel profile;

    public GetProfileResponse() {
    }

    public GetProfileResponse(UserProfileModel profile) {
        this.profile = profile;
    }

    public UserProfileModel getProfile() {
        return profile;
    }

    public void setProfile(UserProfileModel profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "GetProfileResponse{" +
                "profile=" + profile +
                '}';
    }
}
