package com.asset.dailyapplookupservice.defines;

public class ErrorCodes {
    public static class SUCCESS {

        public final static int SUCCESS = 0;
    

    }
    public static class WARRNING {

        public final static int PG_GROUP_CAN_NOT_BE_NULL_IN_POST_PAID = 1;
        public final static int PG_GROUP_CAN_NOT_BE_NULL_IN_PRE_PAID = 2;
        public final static int PG_GROUP_CAN_NOT_BE_NULL_IN_POST_PAID_OR_PRE_PAID = 3;

    }

    public static class ERROR {
        public final static int DATABASE_ERROR = -404;
        public final static int UPDATE_FAILED = -405;
        public final static int UNKNOWN_ERROR = -406;
        public final static int GROUPS_NOT_FOUND = -417;
        public final static int QUERIES_NOT_FOUND = -418;
        public static final int PG_NOT_FOUND = -419;
        public final static int NO_RATE_PLANS_FOUND = -420;
        public final static int RATE_PLAN_DOES_NOT_EXIST = -421;
        public final static int NO_SERVICE_CLASSES_FOUND = -422;
        public final static int NO_TARIFF_MODELS_FOUND = -423;
        public final static int DATABASE_MAPPING_ERROR = -424;
        public final static int RATE_PLAN_GROUP_DOES_NOT_EXIST = -425;
        public final static int CANNOT_DELETE_PG_GROUP_USED_IN_CONFIGURATION_SYSTEM = -426;

    }

}
