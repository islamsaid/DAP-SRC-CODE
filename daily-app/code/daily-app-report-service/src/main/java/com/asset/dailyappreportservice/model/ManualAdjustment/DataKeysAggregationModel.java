package com.asset.dailyappreportservice.model.ManualAdjustment;

import lombok.Data;

@Data
public class DataKeysAggregationModel
{
    private Integer numberOfSubs;
    private Integer dateKey;
    private Integer priceGroupKey;
    private Integer ratePlanKey;
    private Integer trxTypeKey;
    private Integer ratePlanGroupKey;
    private Integer dwhStatusKey;
    private String activationReason;
    private Integer userId;
    private String valueSegment;
    private Integer pgGroupKey;
    private Integer entryDataKey;
    private String siebelSegment;

    private Integer ratePlanType;
}
