package com.asset.loggingService.model.user;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public Integer getLockFlag() {
        return lockFlag;
    }

    public void setLockFlag(Integer lockFlag) {
        this.lockFlag = lockFlag;
    }

    public UserProfileModel getProfileModel() {
        return profileModel;
    }

    public void setProfileModel(UserProfileModel profileModel) {
        this.profileModel = profileModel;
    }
}
