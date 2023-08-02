package com.asset.dailyapplookupservice.model.response.rateplan;

import lombok.Data;


@Data
public class RatePlanResponse
{
    String ratePlanCode;
    String ratePlan;
    Integer ratePlanKey;
    Integer ratePlanGroupKey;
    Integer ratePlanType;
    Integer contractType;
    Integer activationSourceFlag;
    Integer showFlag;
    String combined;
    String postPreFlag;
    String forIvrRev;
    String forIvrCost;
}
