package com.asset.dailapp.springcloudconfigserver.defines;

public class ErrorCodes {
    public static class SUCCESS {
        public final static int SUCCESS = 0;
    }

    public static class ERROR {
        public final static int DATABASE_ERROR = -404;
        public final static int UNKNOWN_ERROR = -406;
        public final static int QUERIES_NOT_FOUND = -418;
        public final static int NO_SYSTEM_SETTING_EXIST = -420;

    }

}
