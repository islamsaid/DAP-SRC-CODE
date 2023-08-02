package com.asset.dailyappnotificationservice.defines;

public class DatabaseStructs
{
    public static final class DAILY_QUERIES {
        public static final String TABLE_NAME = "DAILY_QUERIES";
        public static final String ID = "ID";
        public static final String SEQ = "SEQ";
        public static final String SQL = "SQL";
        public static final String QUERIES_DESC = "QUERIES_DESC";
        public static final String QUERIES_NAME = "QUERIES_NAME";
    }

    public static final class DAILY_NOTIFICATIONS{
        public static final String TABLE_NAME = "DAILY_NOTIFICATIONS";
        public static final String ID = "ID";
        public static final String NOTIFICATION_HEADER = "NOTIFICATION_HEADER";
        public static final String NOTIFICATION_DETAILS = "NOTIFICATION_DETAILS";
        public static final String NOTIFICATION_TYPE = "NOTIFICATION_TYPE";
        public static final String CREATED_TIME = "CREATED_TIME";
        public static final String ACTION_DONE = "ACTION_DONE";
        public static final String CHECK_SUM = "CHECK_SUM";
        public static final String REQUEST_ID = "REQUEST_ID";
        public static final String CREATION_DATE = "CREATION_DATE";
        public static final String DATA_ID = "DATA_ID";
        public static final String DATA_NAME = "DATA_NAME";
    }

    public static final class MODEL_TYPE{
        public static final String SERVICE_CLASS = "SERVICE_CLASS";
        public static final String TARIFF_MODEL = "TARIFF_MODEL";
        public static final String PRICE_GROUP = "PRICE_GROUP";
        public static final String RATE_PLAN = "RATE_PLAN";
    }

    public static final class SERVICE_CLASS{
        public static final String SERVICE_CLASS_NAME = "SERVICE_CLASS";
        public static final String SERVICE_CLASS_KEY = "SERVICE_CLASS_KEY";
    }

    public static final class TARIFF_MODEL{
        public static final String TARIFF_MODEL_NAME = "TARIFF_MODEL";
        public static final String TARIFF_MODEL_KEY = "TARIFF_MODEL_KEY";
    }

    public static final class PRICE_GROUP{
        public static final String PRICE_GROUP_NAME = "PRICE_GROUP";
        public static final String PRICE_GROUP_KEY = "PRICE_GROUP_KEY";
    }

    public static final class RATE_PLAN{
        public static final String RATE_PLAN_NAME = "RATE_PLAN";
        public static final String RATE_PLAN_KEY = "RATE_PLAN_KEY";
    }


}
