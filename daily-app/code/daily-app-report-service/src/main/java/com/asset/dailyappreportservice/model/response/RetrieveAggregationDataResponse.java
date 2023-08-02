package com.asset.dailyappreportservice.model.response;

public class RetrieveAggregationDataResponse {
    String dateKey;
    String fullDate;
    String inSubs;
    String outSubs;
    String ratePlan;
    String ratePlanKey;
    String opening;
    String closing;
    String dwhStatus;
    String dwhStatusKey;
    long variance;

    public long getVariance() {
        return variance;
    }

    public void setVariance(long variance) {
        this.variance = variance;
    }

    public String getDateKey() {
        return dateKey;
    }

    public void setDateKey(String dateKey) {
        this.dateKey = dateKey;
    }

    public String getFullDate() {
        return fullDate;
    }

    public void setFullDate(String fullDate) {
        this.fullDate = fullDate;
    }

    public String getInSubs() {
        return inSubs;
    }

    public void setInSubs(String inSubs) {
        this.inSubs = inSubs;
    }

    public String getOutSubs() {
        return outSubs;
    }

    public void setOutSubs(String outSubs) {
        this.outSubs = outSubs;
    }

    public String getRatePlan() {
        return ratePlan;
    }

    public void setRatePlan(String ratePlan) {
        this.ratePlan = ratePlan;
    }

    public String getRatePlanKey() {
        return ratePlanKey;
    }

    public void setRatePlanKey(String ratePlanKey) {
        this.ratePlanKey = ratePlanKey;
    }

    public String getOpening() {
        return opening;
    }

    public void setOpening(String opening) {
        this.opening = opening;
    }

    public String getClosing() {
        return closing;
    }

    public void setClosing(String closing) {
        this.closing = closing;
    }

    public String getDwhStatus() {
        return dwhStatus;
    }

    public void setDwhStatus(String dwhStatus) {
        this.dwhStatus = dwhStatus;
    }

    public String getDwhStatusKey() {
        return dwhStatusKey;
    }

    public void setDwhStatusKey(String dwhStatusKey) {
        this.dwhStatusKey = dwhStatusKey;
    }
}