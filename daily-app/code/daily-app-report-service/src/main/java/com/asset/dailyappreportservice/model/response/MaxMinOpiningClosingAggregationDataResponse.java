package com.asset.dailyappreportservice.model.response;

import com.asset.dailyappreportservice.model.ManualAdjustment.AggregatedDataModel;

import java.util.List;

public class MaxMinOpiningClosingAggregationDataResponse {
    List<AggregatedDataModel> minimum;
    List<AggregatedDataModel> maximum;

    public MaxMinOpiningClosingAggregationDataResponse(List<AggregatedDataModel> minimum, List<AggregatedDataModel> maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public List<AggregatedDataModel> getMinimum() {
        return minimum;
    }

    public void setMinimum(List<AggregatedDataModel> minimum) {
        this.minimum = minimum;
    }

    public List<AggregatedDataModel> getMaximum() {
        return maximum;
    }

    public void setMaximum(List<AggregatedDataModel> maximum) {
        this.maximum = maximum;
    }
}

