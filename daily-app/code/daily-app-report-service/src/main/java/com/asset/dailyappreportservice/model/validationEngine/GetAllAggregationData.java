package com.asset.dailyappreportservice.model.validationEngine;

import com.asset.dailyappreportservice.model.response.RetrieveAggregationDataResponse;

import java.util.List;

public class GetAllAggregationData {
    List<RetrieveAggregationDataResponse> list;

    public GetAllAggregationData(List<RetrieveAggregationDataResponse> list) {
        this.list = list;
    }
    public GetAllAggregationData() {
    }
    public List<RetrieveAggregationDataResponse> getList() {
        return list;
    }

    public void setList(List<RetrieveAggregationDataResponse> list) {
        this.list = list;
    }
}
