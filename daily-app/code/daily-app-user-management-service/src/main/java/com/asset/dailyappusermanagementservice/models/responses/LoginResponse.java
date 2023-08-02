package com.asset.dailyappusermanagementservice.models.responses;

import com.asset.dailyappusermanagementservice.models.user.UserModel;
import com.asset.dailyappusermanagementservice.models.user.UserProfileModel;

public class LoginResponse {
    private String token;
    private UserModel user;
    public String getToken() {
        return token;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }


    public void setToken(String token) {
        this.token = token;
    }


}
