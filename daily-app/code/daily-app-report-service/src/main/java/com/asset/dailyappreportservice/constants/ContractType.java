package com.asset.dailyappreportservice.constants;

public enum ContractType {
    CONSUMER("consumer", 1), ENTERPRISE("enterprise", 2), ALL("all", 3);

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
