package com.asset.dailyappreportservice.model.ManualAdjustment;

import lombok.Data;

@Data
public class AggregatedDataModel
{
    Integer dateKey;
    long opening;
    long closing;
    long variance;
    String ratePlan;
    Integer ratePlanKey;
    Integer ratePlanGroupKey;
    Integer ratePlanType;
}
