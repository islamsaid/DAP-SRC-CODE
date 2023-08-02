package com.asset.dailyapplookupservice.model.request.priceGroup;


import com.asset.dailyapplookupservice.model.shared.PriceGroupModel;

import java.util.List;

public class GetAllPriceGroupResponse {

    private List<PriceGroupModel> PriceGroupModel;

    public List<PriceGroupModel> getPriceGroupModel() {
        return PriceGroupModel;
    }

    public void setPriceGroupModel(List<PriceGroupModel> priceGroupModel) {
        PriceGroupModel = priceGroupModel;
    }

    public GetAllPriceGroupResponse(List<PriceGroupModel> priceGroupModel) {
        PriceGroupModel = priceGroupModel;
    }
}
