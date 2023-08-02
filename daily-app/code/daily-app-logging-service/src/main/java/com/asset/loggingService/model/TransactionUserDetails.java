package com.asset.loggingService.model;

public class TransactionUserDetails {
    String paramName;
    String oldVal;
    String newVal;

    public TransactionUserDetails() {
    }

    public TransactionUserDetails(String PARAM_NAME, String OLD_VAL, String NEW_VAL) {

        this.paramName = PARAM_NAME;
        this.oldVal = OLD_VAL;
        this.newVal = NEW_VAL;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getOldVal() {
        return oldVal;
    }

    public void setOldVal(String oldVal) {
        this.oldVal = oldVal;
    }

    public String getNewVal() {
        return newVal;
    }

    public void setNewVal(String newVal) {
        this.newVal = newVal;
    }
}
