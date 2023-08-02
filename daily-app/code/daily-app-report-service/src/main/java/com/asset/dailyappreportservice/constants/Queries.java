package com.asset.dailyappreportservice.constants;

public enum Queries {

    RERTRIEVE_balances(35),
    RERTRIEVE_aggregation_data(36),
    RERTRIEVE_transaction_types_lookups(37),
    UPDATE_VALID_ENGINE_AGGREGATION(45),
    UPDATE_VALIDATION_ENGINE(46),
    INSERT_DAILY_DAILY_DATA_AGGREGATION(47),
    INSERT_CLOSING_DAILY_DATA_AGGREGATION(48),
    INSERT_NET_ADDS_DAILY_DATA_AGGREGATION(49),

    GET_AGGREGATION_DATA_BY_DAY(50),
    INSERT_AGGREGATED_DATA(74),
    GET_ALL_TRANSFER_ADJUSTMENT(66),
    INSERT_TRANSFER_DATA(68),
    RERTRIEVE_All_transaction_types_lookups(73),
    GET_PG_GROUP_KEY_BY_PG_KEY(75),
    UPDATE_ADJUST_FLAG_VALIDATION_ENGINE(95);
    public final int id;
    Queries(int id) {
    this.id= id;
    }
}
