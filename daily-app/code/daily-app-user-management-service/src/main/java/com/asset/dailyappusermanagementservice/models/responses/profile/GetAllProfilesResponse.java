package com.asset.dailyappusermanagementservice.models.responses.profile;

import com.asset.dailyappusermanagementservice.models.user.UserProfileModel;

import java.util.HashMap;
import java.util.List;

public class GetAllProfilesResponse {

    private List<UserProfileModel> profilesList;
    public GetAllProfilesResponse() {
    }

    public GetAllProfilesResponse(List<UserProfileModel>  profilesList) {
        this.profilesList = profilesList;
    }

    public List<UserProfileModel>  getProfilesList() {
        return profilesList;
    }

    public void setProfilesList(List<UserProfileModel> profilesList) {
        this.profilesList = profilesList;
    }
}
