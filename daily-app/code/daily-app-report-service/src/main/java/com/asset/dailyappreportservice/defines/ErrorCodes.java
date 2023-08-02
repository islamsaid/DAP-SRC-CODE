package com.asset.dailyappreportservice.defines;

public class ErrorCodes {
    public static class SUCCESS {
        public final static int SUCCESS = 0;
    }

    public static class ERROR {
        public final static int DATABASE_ERROR = -404;
        public final static int UNKNOWN_ERROR = -406;
        public final static int QUERIES_NOT_FOUND = -418;
        public final static int AggregationData_EMPTY = -420;
        public final static int DATE_HAS_NO_DATA = -421;
        public final static int DATABASE_MAPPING_ERROR = -430;
        public final static int SUBSCRIPTIONS_EXCEEDS_3000 = -431;
        public final static int PG_GROUP_FOR_PRICE_GROUP_NULL = -432;
        public final static int RATE_PLAN_KEY_NULL = -433;
        public final static int INVALID_RATE_PLAN_TYPE = -434;


    }

}
