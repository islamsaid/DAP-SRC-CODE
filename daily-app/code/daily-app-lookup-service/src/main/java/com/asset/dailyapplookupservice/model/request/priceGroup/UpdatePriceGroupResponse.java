package com.asset.dailyapplookupservice.model.request.priceGroup;


import com.asset.dailyapplookupservice.model.request.BaseRequest;

public class UpdatePriceGroupResponse extends BaseRequest {
    String priceGroupCode;
    String pgGroupKey;

    public String getPriceGroupCode() {
        return priceGroupCode;
    }

    public void setPriceGroupCode(String priceGroupCode) {
        this.priceGroupCode = priceGroupCode;
    }

    public String getPgGroupKey() {
        return pgGroupKey;
    }

    public void setPgGroupKey(String pgGroupKey) {
        this.pgGroupKey = pgGroupKey;
    }

    @Override
    public String toString() {
        return "UpdatePriceGroupResponse{" +
                "priceGroupCode='" + priceGroupCode + '\'' +
                ", pgGroupKey='" + pgGroupKey + '\'' +
                '}';
    }
}
