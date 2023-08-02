package com.asset.dailyappreportservice.model.validationEngine;

public class GetAllBalances {
    String opening;
    String closing;
    Double variance;

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

    public Double getVariance() {
        return variance;
    }

    public void setVariance(Double variance) {
        this.variance = variance;
    }

    @Override
    public String toString() {
        return "GetAllBalances{" +
                "opening='" + opening + '\'' +
                ", closing='" + closing + '\'' +
                ", variance=" + variance +
                '}';
    }
}
