package com.asset.dailyapplookupservice.defines;

public class DatabaseStructs {

    public static final class PRICE_GROUP {
        public static final String TABLE_NAME = "PRICE_GROUP";
        public static final String PRICE_GROUP_KEY = "PRICE_GROUP_KEY";
        public static final String PRICE_GROUP = "PRICE_GROUP";
        public static final String PRICE_GROUP_CODE = "PRICE_GROUP_CODE";
        public static final String ENTRY_DATE = "ENTRY_DATE";
        public static final String MODIFICATION_DATE = "MODIFICATION_DATE";
        public static final String PG_GROUP_KEY = "PG_GROUP_KEY";
    }

    public static final class PG_GROUP {
        public static final String TABLE_NAME = "PG_GROUP";
        public static final String PG_GROUP_KEY = "PG_GROUP_KEY";
        public static final String PG_GROUP = "PG_GROUP";
        public static final String SHOW_FLAG = "SHOW_FLAG";
        public static final String DESCRIPTION = "DESCRIPTION";
    }

    public static final class RATE_PLAN{
        public static final String TABLE_NAME = "RATE_PLAN";
        public static final String RATE_PLAN_KEY = "RATE_PLAN_KEY";
        public static final String RATE_PLAN_CODE = "RATE_PLAN_CODE";
        public static final String RATE_PLAN = "RATE_PLAN";
        public static final String RATE_PLAN_TYPE = "RATE_PLAN_TYPE";
        public static final String CONTRACT_TYPE = "CONTRACT_TYPE";
        public static final String RATE_PLAN_GROUP_KEY = "RATE_PLAN_GROUP_KEY";
        public static final String CONSUMER_FLAG = "CONSUMER_FLAG";
        public static final String ENTERPRISE_FLAG = "ENTERPRISE_FLAG";
        public static final String PRE_FLAG = "PRE_FLAG";
        public static final String POST_FLAG = "POST_FLAG";
        public static final String ACTIVATION_SOURCE_FLAG = "ACTIVATION_SOURCE_FLAG";
        public static final String SHOW_FLAG = "SHOW_FLAG";
        public static final String DEACTIVATION_FLAG = "DEACTIVATION_FLAG";
        public static final String DEACTIVATION_FLAG_ALIAS = "DEAC_SOURCE_FLAG";
        public static final String HYBRID = "HYBRID";
        public static final String HYBRID_ALIAS = "HYBIRD_FLAG";

        public static final String COMBINED = "COMBINED";
        public static final String DATA_VOICE_ADSL_FLAG = "DATA_VOICE_ADSL_FLAG";
        public static final String FOR_IVR_REV = "FOR_IVR_REV";
        public static final String FOR_IVR_COST = "FOR_IVR_COST";
        public static final String BUNDLE = "BUNDLE";
        public static final String BUNDLE_ALIAS = "BUNDLE_FLAG";

        public static final String POST_PRE_FLAG = "POST_PRE_FLAG";
        public static final String BUZ_CONS_FLAG = "BUZ_CONS_FLAG";
        public static final String LOYAL_FLAG = "LOYAL_FLAG";
        public static final String SEGMENT = "SEGMENT";
        public static final String CI_GROUPING = "CI_GROUPING";
        public static final String DEFERRED_FLAG = "DEFERRED_FLAG";
    }

    public static final class RATE_PLAN_GROUP{
        public static final String TABLE_NAME = "RATE_PLAN_GROUP";
        public static final String RATE_PLAN_GROUP_KEY = "RATE_PLAN_GROUP_KEY";
        public static final String RATE_PLAN_GROUP_KEY_ALIAS = "GROUP_KEY";

        public static final String RATE_PLAN_GROUP = "RATE_PLAN_GROUP";
        public static final String SHOW_FLAG = "SHOW_FLAG";
        public static final String SHOW_FLAG_ALIAS = "SHOW_FLAG_RATE_PLAN_GROUP";

        public static final String DESCRIPTION = "DESCRIPTION";
    }

    public static final class DAILY_QUERIES {
        public static final String TABLE_NAME = "DAILY_QUERIES";
        public static final String ID = "ID";
        public static final String SEQ = "SEQ";
        public static final String SQL = "SQL";
        public static final String QUERIES_DESC = "QUERIES_DESC";
        public static final String QUERIES_NAME = "QUERIES_NAME";
    }

}