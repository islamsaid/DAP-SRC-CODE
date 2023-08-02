package com.asset.loggingService.defines;

public class DatabaseStructs {

    public static final class DAILY_QUERIES {
        public static final String TABLE_NAME = "DAILY_QUERIES";
        public static final String ID = "ID";
        public static final String SEQ = "SEQ";
        public static final String SQL = "SQL";
        public static final String QUERIES_DESC = "QUERIES_DESC";
        public static final String QUERIES_NAME = "QUERIES_NAME";
    }

    public static final class TX_USER_ACTIONS {
        public static final String TABLE_NAME = "TX_USER_ACTIONS";
        public static final String PAGE_NAME = "PAGE_NAME";
        public static final String ID = "ID";

        public static final String ACTION_NAME = "ACTION_NAME";
        public static final String USER_ID = "USER_ID";
        public static final String USER_NAME = "USER_NAME";

        public static final String OBJECT_IDENTIFIER = "OBJECT_IDENTIFIER";
        public static final String SESSION_ID = "SESSION_ID";
        public static final String REQUEST_ID = "REQUEST_ID";
        public static final String REQUEST_BODY = "REQUEST_BODY";
        public static final String RESPONSE_BODY = "RESPONSE_BODY";
        public static final String CREATION_DATE = "CREATION_DATE";
        public static final String SQL_STATEMENT = "SQL_STATEMENT";
    }

    public static final class TX_USER_ACTIONS_DETAILS {
        public static final String TABLE_NAME = "TX_USER_ACTIONS_DETAILS";
        public static final String HEADER_ID = "HEADER_ID";
        public static final String PARAM_NAME = "PARAM_NAME";
        public static final String OLD_VAL = "OLD_VAL";
        public static final String NEW_VAL = "NEW_VAL";
        public static final String CREATION_DATE = "CREATION_DATE";

    }
    public static final class TX_ACTIONS {
        public static final String TABLE_NAME = "TX_ACTIONS";
        public static final String ID = "ID";
        public static final String ACTION_NAME = "ACTION_NAME";
        public static final String SQL_ID = "SQL_ID";
    }
}