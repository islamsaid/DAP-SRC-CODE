package com.asset.dailyapplookupservice.model.request.rateplan;

import com.asset.dailyapplookupservice.model.rateplan.RatePlanModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RatePlansUpdateRequest {
    @Autowired
    List<RatePlanModel> ratePlans;

    public List<RatePlanModel> getRatePlans() {
        return ratePlans;
    }

    public void setRatePlans(List<RatePlanModel> ratePlans) {
        this.ratePlans = ratePlans;
    }
}
