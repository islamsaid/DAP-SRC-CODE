package com.asset.dailyapplookupservice.constants;

public enum Queries {
    GET_ALL_GROUP(1),
    ADD_PROFILE(2),
    GET_PROFILE(3),
    GET_ALL_PROFILES(4),
    DELETE_PROFILE(5),
    UPDATE_PROFILE(6),
    UPDATE_PRICE_GROUP(7),
    GET_PRICE_GROUP_GROUPS(8),

    GET_ALL_SERVICE_CLASS(9),
    UPDATE_SERVICE_CLASS(10),

    GET_ALL_TARIFF_MODEL(11),
    UPDATE_TARIFF_MODEL(12),
    GET_PG_GROUP(30),
    UPDATE_PG_GROUP(31),
    update_PRICEGROUP(32),
    INSERT_PG_GROUP(33),

    GET_RATE_PLAN_BY_CODE(25),
    GET_ALL_RATE_PLANS(26),
    GET_ALL_RATE_PLAN_GROUPS(27),
    ADD_RATE_PLAN_GROUP(28),
    GET_RATE_PLAN_BY_KEY(29),
    UPDATE_RATE_PLAN_GROUP(34),
    UPDATE_RATE_PLAN(23),
    GET_PRICE_GROUP_GROUPS_ById(54),
    GET_RATE_PLAN_GROUP_BY_KEY(61),
    GET_RATE_PLAN_GROUPS_WITH_RP(62),
    UPDATE_RP_GROUP_KEY_IN_RATE_PLANE(65),
    GET_RATE_PLAN_GROUP_BY_ID_WITH_RATE_PLANS(64),
    REMOVE_RATE_PLAN_GROUP_KEY_FROM_RATE_PLAN(67),
    DELETE_PG_GROUP(70),
    SET_PG_GROUP_KEY_NULL_IN_PRICE_GROUP(71),
    DELETE_RATE_PLAN_GROUP(69),
    GET_PG_GROUP_USING_PRICE_GROUP_ID(99),
    UPDATE_PG_GROUP_USING_PRICE_GROUP_KEY(100)
    ;

    public final int id;
    Queries(int id) {
    this.id= id;
    }
}