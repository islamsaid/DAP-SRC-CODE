package com.asset.dailyappbackendservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UserModel  {

    private Integer userId;
    private String name;
    private String username;
    private Integer profileId;
    private Integer lockFlag;

    public UserModel(Integer userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public UserModel(Integer userId, String username, Integer profileId) {
        this.userId = userId;
        this.username = username;
        this.profileId = profileId;
    }
}

