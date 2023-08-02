package com.asset.dailyapplookupservice.model.rateplan;

import lombok.Data;
import java.util.List;

@Data
public class RatePlanGroupModel
{
    private Integer ratePlanGroupKey;
    private String ratePlanGroup;
    private Integer showFlag;
    private String description;

    private List<RatePlanModel> ratePlans;

    @Override
    public String toString() {
        return "RatePlanGroupModel{" +
                "ratePlanGroupKey=" + ratePlanGroupKey +
                ", ratePlanGroup='" + ratePlanGroup + '\'' +
                ", showFlag=" + showFlag +
                ", description='" + description + '\'' +
                '}';
    }
}
