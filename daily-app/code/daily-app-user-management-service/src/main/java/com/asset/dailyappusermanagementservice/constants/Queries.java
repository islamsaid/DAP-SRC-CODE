package com.asset.dailyappusermanagementservice.constants;

public enum Queries {
    GET_ALL_GROUP(1),
    ADD_PROFILE(2),
    GET_PROFILE(3),
    GET_ALL_PROFILES(4),
    DELETE_PROFILE(5),
    UPDATE_PROFILE(6),
    UPDATE_PRICE_GROUP(7),
    GET_PRICE_GROUP_GROUPS(8),
    ADD_PRIVILEGE(15),
    GET_PRIVILEGE(16),
    GET_ALL_PRIVILEGE(17),
    DELETE_PRIVILEGE(18),
    FIND_PRIVILEGE_BY_ID(19),
    UPDATE_PRIVILEGE(20),
    GET_COUNT_OF_PROFILES_PRIVILEGE(21),

    GET_ALL_USERS(24),
    GET_USER_BY_ID(40),
    Add_USER(41),
    UPDATE_USER(42),
    DELETE_USER_BY_ID(43),
    CLEAR_USERS(44),
    INSERT_DAILY_PROFILE_PRIVILEGES(51),
    FIND_PROFILE_IF_EXIST(52),
    deleteProfileFeaturesQuery(53),
    get_active_profile(55),
    FIND_PROFILE_NAME_IF_EXIST(72);
    public final int id;
    Queries(int id) {
    this.id= id;
    }
}
