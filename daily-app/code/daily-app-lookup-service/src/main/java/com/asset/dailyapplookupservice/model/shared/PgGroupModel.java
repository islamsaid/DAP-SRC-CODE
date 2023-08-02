package com.asset.dailyapplookupservice.model.shared;

public class PgGroupModel {

    private String pgGroupKey;
    private String pgGroup;
    private int showFlag;
    private String description;

    public String getPgGroupKey() {
        return pgGroupKey;
    }



    public void setPgGroupKey(String pgGroupKey) {
        this.pgGroupKey = pgGroupKey;
    }

    public String getPgGroup() {
        return pgGroup;
    }

    public void setPgGroup(String pgGroup) {
        this.pgGroup = pgGroup;
    }

    public int getShowFlag() {
        return showFlag;
    }

    public void setShowFlag(int showFlag) {
        this.showFlag = showFlag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
