package com.asset.dailyapplookupservice.model.request.priceGroup;

import java.util.List;

public class UpdateBatchPriceGroupResponse {
    List<UpdatePriceGroupResponse> priceGroupList;

    public List<UpdatePriceGroupResponse> getPriceGroupList() {
        return priceGroupList;
    }

    public void setPriceGroupList(List<UpdatePriceGroupResponse> priceGroupList) {
        this.priceGroupList = priceGroupList;
    }
}
