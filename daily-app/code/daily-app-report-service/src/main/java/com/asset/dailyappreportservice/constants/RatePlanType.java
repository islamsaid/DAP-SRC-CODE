package com.asset.dailyappreportservice.constants;

public enum RatePlanType {
    PRE(1, "preFlag"), POST(2, "postFlag");

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
