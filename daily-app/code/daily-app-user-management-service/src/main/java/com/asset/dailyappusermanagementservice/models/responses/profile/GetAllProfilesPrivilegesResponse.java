package com.asset.dailyappusermanagementservice.models.responses.profile;

import com.asset.dailyappusermanagementservice.models.user.UserProfileModel;

import java.util.HashMap;

public class GetAllProfilesPrivilegesResponse {

    private HashMap<Integer, UserProfileModel> profilesList;

    public GetAllProfilesPrivilegesResponse() {
    }

    public GetAllProfilesPrivilegesResponse(HashMap<Integer, UserProfileModel>  profilesList) {
        this.profilesList = profilesList;
    }

    public HashMap<Integer, UserProfileModel>  getProfilesList() {
        return profilesList;
    }

    public void setProfilesList(HashMap<Integer, UserProfileModel>  profilesList) {
        this.profilesList = profilesList;
    }
}
