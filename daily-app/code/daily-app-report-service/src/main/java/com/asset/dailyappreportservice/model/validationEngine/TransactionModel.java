package com.asset.dailyappreportservice.model.validationEngine;

public class TransactionModel {
    String trxTypeKey;
    String trxTypeName;
    String  value;

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

    @Override
    public String toString() {
        return "TransactionModel{" +
                "trxTypeKey='" + trxTypeKey + '\'' +
                ", trxTypeName='" + trxTypeName + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
