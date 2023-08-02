package com.asset.dailyappusermanagementservice.models.shared;

public class LkPrivilegeModel{
    private Integer id;
    private String name;
    private String pageName;
    private String backEndUrl;
    private String description;

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

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }


    public String getBackEndUrl() {
        return backEndUrl;
    }

    public void setBackEndUrl(String backEndUrl) {
        this.backEndUrl = backEndUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "LkPrivilegeModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pageName='" + pageName + '\'' +
                ", backEndUrl='" + backEndUrl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
