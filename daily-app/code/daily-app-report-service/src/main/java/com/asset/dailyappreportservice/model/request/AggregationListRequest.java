package com.asset.dailyappreportservice.model.request;

import com.asset.dailyappreportservice.model.validationEngine.AggregationRequest;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AggregationListRequest  extends  BaseRequest{

    @NotNull
    List<AggregationRequest>  aggregationLists;

    public List<AggregationRequest> getAggregationLists() {
        return aggregationLists;
    }

    public void setAggregationLists(List<AggregationRequest> aggregationLists) {
        this.aggregationLists = aggregationLists;
    }

    public AggregationListRequest(List<AggregationRequest> aggregationLists) {
        this.aggregationLists = aggregationLists;
    }
    public AggregationListRequest() {
    }

}
