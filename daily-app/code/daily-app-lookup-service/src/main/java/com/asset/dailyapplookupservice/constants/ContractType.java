package com.asset.dailyapplookupservice.constants;

public enum ContractType {
    PRE("preFlag", 1), POST("postFlag", 2);

    private final String key;
    private final Integer value;

    ContractType(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }
    public Integer getValue() {
        return value;
    }
}
