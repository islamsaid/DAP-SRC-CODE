package com.asset.dailyapplookupservice.constants;

public enum Type {
    CONSUMER("consumer", 1), ENTERPRISE("enterprise", 2), ALL("all", 3);

    private final String key;
    private final Integer value;

    Type(String key, Integer value) {
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
