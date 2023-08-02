package com.asset.dailyappnotificationservice.defines;

public class ErrorCodes {
    public static class SUCCESS {
        public final static int SUCCESS = 0;
    }

    public static class ERROR{
        public final static int FULL_QUEUE = -10;

        public final static int DATABASE_ERROR = -404;
        public final static int EXECUTION_ERROR = -405;

        public final static int UNKNOWN_ERROR = -406;
        public final static int NO_RECORDS_FOUND = -407;
        public final static int QUERIES_NOT_FOUND = -418;

        public final static int DATABASE_MAPPING_ERROR = -424;
        public final static int NO_EMAIL_ACCOUNTS_FOUND = -425;
    }
}
