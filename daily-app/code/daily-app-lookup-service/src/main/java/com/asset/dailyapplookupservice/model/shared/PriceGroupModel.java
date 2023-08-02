package com.asset.dailyapplookupservice.model.shared;

public class PriceGroupModel {
    private Integer priceGroupKey;
    private String priceGroup;
    private String priceGroupCode;

    private PgGroupModel pgGroupKey;

    public PgGroupModel getPgGroupKey() {
        return pgGroupKey;
    }

    public void setPgGroupKey(PgGroupModel pgGroupKey) {
        this.pgGroupKey = pgGroupKey;
    }

    public Integer getPriceGroupKey() {
        return priceGroupKey;
    }

    public void setPriceGroupKey(Integer priceGroupKey) {
        this.priceGroupKey = priceGroupKey;
    }

    public String getPriceGroup() {
        return priceGroup;
    }

    public void setPriceGroup(String priceGroup) {
        this.priceGroup = priceGroup;
    }

    public String getPriceGroupCode() {
        return priceGroupCode;
    }

    public void setPriceGroupCode(String priceGroupCode) {
        this.priceGroupCode = priceGroupCode;
    }

    @Override
    public String toString() {
        return "PriceGroupModel{" +
                "priceGroupKey=" + priceGroupKey +
                ", priceGroup='" + priceGroup + '\'' +
                ", priceGroupCode='" + priceGroupCode + '\'' +
                ", pgGroupKey=" + pgGroupKey +
                '}';
    }
}
