package com.asset.dailyapplookupservice.model.rateplan;

import lombok.Data;

@Data
public class RatePlanModel
{
    private Integer ratePlanKey;
    private String ratePlanCode;
    private String ratePlan;
    private Integer ratePlanType;
    private Integer contractType;
    private Integer ratePlanGroupKey;
    private Integer consumerFlag;
    private Integer enterpriseFlag;
    private Integer preFlag;
    private Integer postFlag;
    private Integer activationSourceFlag;
    private Integer showFlag;
    private Integer deactivationFlag;
    private Integer hybrid;
    private String combined;
    private String dataVoiceAdslFlag;
    private String forIvrRev;
    private String forIvrCost;
    private String bundle;
    private String postPreFlag;
    private String buzConsFlag;
    private String loyalFlag;
    private String segment;
    private String ciGrouping;
    private String deferredFlag;
}
