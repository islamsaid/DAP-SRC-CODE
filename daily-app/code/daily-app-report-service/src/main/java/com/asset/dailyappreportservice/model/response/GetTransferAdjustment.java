package com.asset.dailyappreportservice.model.response;

public class GetTransferAdjustment {

    String trxTypeKey;
    String trxTypeName;
    String value;
    String fullDate;
    String dateKey;
    String subs;

    public String getTrxTypeKey() {
        return trxTypeKey;
    }

    public void setTrxTypeKey(String trxTypeKey) {
        this.trxTypeKey = trxTypeKey;
    }

    public String getTrxTypeName() {
        return trxTypeName;
    }

    public void setTrxTypeName(String trxTypeName) {
        this.trxTypeName = trxTypeName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFullDate() {
        return fullDate;
    }

    public void setFullDate(String fullDate) {
        this.fullDate = fullDate;
    }

    public String getDateKey() {
        return dateKey;
    }

    public void setDateKey(String dateKey) {
        this.dateKey = dateKey;
    }

    public String getSubs() {
        return subs;
    }

    public void setSubs(String subs) {
        this.subs = subs;
    }
}
