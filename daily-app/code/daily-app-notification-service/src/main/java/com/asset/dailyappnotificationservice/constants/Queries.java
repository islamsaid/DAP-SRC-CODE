package com.asset.dailyappnotificationservice.constants;

public enum Queries
{
    GET_RATE_PLAN_KEYS(79),
    GET_SERVICE_CLASS_KEYS(80),
    GET_PRICE_GROUP_KEYS(81),
    GET_TARIFF_MODEL_KEYS(82),
    ADD_NOTIFICATION(84),
    GET_ACCOUNTS(86),
    GET_ALL_NOTIFICATIONS(89),
    EXPIRE_NOTIFICATION(90),
    GET_NOTIFICATION_BY_ID(92),
    GET_NOTIFICATION_BY_CHECK_SUM(94)
            ;
    public final int id;
    Queries(int id) {
        this.id= id;
    }
}
