package com.asset.dailyappusermanagementservice.models.request.profile;
import com.asset.dailyappusermanagementservice.models.user.UserProfileModel;

public class UpdateProfileRequest {
    private UserProfileModel profile;

    public UserProfileModel getProfile() {
        return profile;
    }

    public void setProfile(UserProfileModel profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "UpdateProfileRequest{" +
                "profile=" + profile +
                '}';
    }
}
