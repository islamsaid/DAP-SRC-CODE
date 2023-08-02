package com.asset.dailyappusermanagementservice.models.responses.user;

import com.asset.dailyappusermanagementservice.models.user.UserModel;

import java.util.List;

public class GetAllUsersResponse {
    private List<UserModel> users;

    public GetAllUsersResponse() {
    }

    public GetAllUsersResponse(List<UserModel> users) {
        this.users = users;
    }

    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }

}
