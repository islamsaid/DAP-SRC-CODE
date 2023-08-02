package com.asset.dailyappreportservice.defines;

public class DatabaseStructs {

    public static final class DAILY_QUERIES {
        public static final String TABLE_NAME = "DAILY_QUERIES";
        public static final String ID = "ID";
        public static final String SEQ = "SEQ";
        public static final String SQL = "SQL";
        public static final String QUERIES_DESC = "QUERIES_DESC";
        public static final String QUERIES_NAME = "QUERIES_NAME";
    }

    public static final class DAILY_DATA_AGGREGATION {
        public static final String TABLE_NAME = "DAILY_DATA_AGGREGATION";
        public static final String NO_OF_SUBS = "NO_OF_SUBS";
        public static final String DATE_KEY = "DATE_KEY";
        public static final String PRICE_GROUP_KEY = "PRICE_GROUP_KEY";
        public static final String RATE_PLAN_KEY = "RATE_PLAN_KEY";
        public static final String TRX_TYPE_KEY = "TRX_TYPE_KEY";
        public static final String RATE_PLAN_GROUP_KEY = "RATE_PLAN_GROUP_KEY";
        public static final String DWH_STATUS_KEY = "DWH_STATUS_KEY";
        public static final String ACTIVATION_REASON = "ACTIVATION_REASON";
        public static final String USER_ID = "USER_ID";
        public static final String VALUE_SEGMENT = "VALUE_SEGMENT";
        public static final String PG_GROUP_KEY = "PG_GROUP_KEY";
        public static final String ENTRY_DATE_KEY = "ENTRY_DATE_KEY";
        public static final String SIEBEL_SEGMENT = "SIEBEL_SEGMENT";
    }

    public  static  final class AGGREGATION_DATA_RESULT {
        public static final String DATE_KEY = "DATE_KEY";
        public static final String FULL_DATE = "FULL_DATE";
        public static final String IN_SUBS = "IN_SUBS";
        public static final String OUT_SUBS = "OUT_SUBS";
        public static final String RATE_PLAN = "RATE_PLAN";
        public static final String RATE_PLAN_KEY = "RATE_PLAN_KEY";
        public static final String OPENING = "OPENING";
        public static final String CLOSING = "CLOSING";
        public static final String DWH_STATUS = "DWH_STATUS";
        public static final String DWH_STATUS_KEY = "DWH_STATUS_KEY";
        public static final String RATE_PLAN_TYPE = "RATE_PLAN_TYPE";



    }

}