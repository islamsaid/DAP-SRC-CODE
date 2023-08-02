package com.asset.dailyappreportservice.model.request;

public class AddTransferAdjustmentRequest {

    int numberOfSubs;
    int dataKey;
    int ratePlanKey;
    int trxTypeKey;
    int ratePlanGroupKey;

    int ratePlanType;
    int dwhStatusKey;
    int pgGroupKey;

    private Integer priceGroupKey;
    private Integer userId;
    public int getRatePlanType() {
        return ratePlanType;
    }

    public void setRatePlanType(int ratePlanType) {
        this.ratePlanType = ratePlanType;
    }

    public Integer getPriceGroupKey() {
        return priceGroupKey;
    }

    public void setPriceGroupKey(Integer priceGroupKey) {
        this.priceGroupKey = priceGroupKey;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getNumberOfSubs() {
        return numberOfSubs;
    }

    public void setNumberOfSubs(int numberOfSubs) {
        this.numberOfSubs = numberOfSubs;
    }

    public int getDataKey() {
        return dataKey;
    }

    public void setDataKey(int dataKey) {
        this.dataKey = dataKey;
    }

    public int getRatePlanKey() {
        return ratePlanKey;
    }

    public void setRatePlanKey(int ratePlanKey) {
        this.ratePlanKey = ratePlanKey;
    }

    public int getTrxTypeKey() {
        return trxTypeKey;
    }

    public void setTrxTypeKey(int trxTypeKey) {
        this.trxTypeKey = trxTypeKey;
    }

    public int getRatePlanGroupKey() {
        return ratePlanGroupKey;
    }

    public void setRatePlanGroupKey(int ratePlanGroupKey) {
        this.ratePlanGroupKey = ratePlanGroupKey;
    }

    public int getDwhStatusKey() {
        return dwhStatusKey;
    }

    public void setDwhStatusKey(int dwhStatusKey) {
        this.dwhStatusKey = dwhStatusKey;
    }

    public int getPgGroupKey() {
        return pgGroupKey;
    }

    public void setPgGroupKey(int pgGroupKey) {
        this.pgGroupKey = pgGroupKey;
    }

    @Override
    public String toString() {
        return "AddTransferAdjustmentRequest{" +
                "numberOfSubs=" + numberOfSubs +
                ", dataKey=" + dataKey +
                ", ratePlanKey=" + ratePlanKey +
                ", trxTypeKey=" + trxTypeKey +
                ", ratePlanGroupKey=" + ratePlanGroupKey +
                ", ratePlanType=" + ratePlanType +
                ", dwhStatusKey=" + dwhStatusKey +
                ", pgGroupKey=" + pgGroupKey +
                ", priceGroupKey=" + priceGroupKey +
                '}';
    }
}
