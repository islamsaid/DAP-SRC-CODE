package com.asset.dailyappusermanagementservice.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UserModel  {

    private Integer userId;
    private String name;
    private String username;
    private Integer profileId;
    private Integer lockFlag;
    private UserProfileModel profileModel;

    public UserModel() {
    }

    public UserModel(Integer userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    // properties used internally by the engine
    @JsonIgnore
    private String status;
    @JsonIgnore
    private String statusMessage;
}
