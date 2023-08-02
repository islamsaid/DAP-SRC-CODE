package com.asset.loggingService.model.user;

import java.util.ArrayList;

public class UserProfileModel {
    private Integer id;
    private String name;
    private ArrayList<LkPrivilegeModel> privileges;

    public ArrayList<LkPrivilegeModel> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(ArrayList<LkPrivilegeModel> privileges) {
        this.privileges = privileges;
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

}

