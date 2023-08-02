package com.asset.dailyapplookupservice.constants;

public enum RatePlanType {
    PRE(1, "Prepaid"), POST(2, "Postpaid");

    private final Integer key;
    private final String value;

    RatePlanType(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }
}
