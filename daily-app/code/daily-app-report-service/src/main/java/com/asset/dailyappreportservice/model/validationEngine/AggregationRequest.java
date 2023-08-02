package com.asset.dailyappreportservice.model.validationEngine;


import com.asset.dailyappreportservice.model.request.BaseRequest;

public class AggregationRequest extends BaseRequest {
    long dateKey;
    int inSubTransactionTypeKey;
    int outSubTransactionTypeKey;
    int ratePlanKey;
    int dwhStatusKey;
    boolean inAdjustFlag;
     boolean outAdjustFlag;
    int dayDateKey;

    public int getDayDateKey() {
        return dayDateKey;
    }

    public void setDayDateKey(int dayDateKey) {
        this.dayDateKey = dayDateKey;
    }

    public boolean isInAdjustFlag() {
        return inAdjustFlag;
    }

    public int getOutSubTransactionTypeKey() {
        return outSubTransactionTypeKey;
    }

    public void setOutSubTransactionTypeKey(int outSubTransactionTypeKey) {
        this.outSubTransactionTypeKey = outSubTransactionTypeKey;
    }

    public boolean isOutAdjustFlag() {
        return outAdjustFlag;
    }

    public long getDateKey() {
        return dateKey;
    }

    public void setDateKey(long dateKey) {
        this.dateKey = dateKey;
    }

    public int getInSubTransactionTypeKey() {
        return inSubTransactionTypeKey;
    }

    public void setInSubTransactionTypeKey(int inSubTransactionTypeKey) {
        this.inSubTransactionTypeKey = inSubTransactionTypeKey;
    }

    public int getRatePlanKey() {
        return ratePlanKey;
    }

    public void setRatePlanKey(int ratePlanKey) {
        this.ratePlanKey = ratePlanKey;
    }

    public int getDwhStatusKey() {
        return dwhStatusKey;
    }

    public void setDwhStatusKey(int dwhStatusKey) {
        this.dwhStatusKey = dwhStatusKey;
    }

    public boolean getInAdjustFlag() {
        return inAdjustFlag;
    }

    public void setInAdjustFlag(boolean inAdjustFlag) {
        this.inAdjustFlag = inAdjustFlag;
    }

    public boolean getOutAdjustFlag() {
        return outAdjustFlag;
    }

    public void setOutAdjustFlag(boolean outAdjustFlag) {
        this.outAdjustFlag = outAdjustFlag;
    }

    @Override
    public String toString() {
        return "AggregationRequest{" +
                "dateKey=" + dateKey +
                ", inSubTransactionTypeKey=" + inSubTransactionTypeKey +
                ", outSubTransactionTypeKey=" + outSubTransactionTypeKey +
                ", ratePlanKey=" + ratePlanKey +
                ", dwhStatusKey=" + dwhStatusKey +
                ", inAdjustFlag=" + inAdjustFlag +
                ", outAdjustFlag=" + outAdjustFlag +
                ", dayDateKey=" + dayDateKey +
                '}';
    }
}
