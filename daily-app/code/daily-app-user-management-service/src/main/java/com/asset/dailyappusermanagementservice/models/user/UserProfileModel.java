package com.asset.dailyappusermanagementservice.models.user;

import com.asset.dailyappusermanagementservice.models.request.BaseRequest;
import com.asset.dailyappusermanagementservice.models.shared.LkPrivilegeModel;

import java.util.ArrayList;

public class UserProfileModel  extends BaseRequest {
    private Integer id;
    private String name;
    private int  isActive;

    //todo created by
    private int createdBY;


    private ArrayList<LkPrivilegeModel> privileges;

    public ArrayList<LkPrivilegeModel> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(ArrayList<LkPrivilegeModel> privileges) {
        this.privileges = privileges;
    }
    public int getCreatedBY() {
        return createdBY;
    }

    public void setCreatedBY(Integer createdBY) {
        this.createdBY = createdBY;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserProfileModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isActive=" + isActive +
                ", createdBY=" + createdBY +
                ", privileges=" + privileges +
                '}';
    }
}

