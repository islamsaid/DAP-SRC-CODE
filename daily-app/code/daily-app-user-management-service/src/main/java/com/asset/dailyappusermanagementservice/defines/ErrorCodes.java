package com.asset.dailyappusermanagementservice.defines;

public class ErrorCodes {
    public static class SUCCESS {

        public final static int SUCCESS = 0;
    }

    public static class ERROR {

        public final static int INVALID_USERNAME_OR_PASSWORD = -400;
        public final static int EXPIRED_TOKEN = -401;
        public final static int NOT_AUTHORIZED = -402;
        public final static int INVALID_TOKEN = -403;
        public final static int DATABASE_ERROR = -404;
        public final static int UPDATE_FAILED = -405;
        public final static int UNKNOWN_ERROR = -406;
        public final static int LDAP_AUTH_FAILED = -407;
        public final static int CANNOT_GENERATE_TOKEN = -408;
        public final static int USER_NOT_FOUND = -409;
        public final static int PROFILE_NOT_FOUND = -410;
        public final static int NO_PROFILES_FOUND = -411;
        public final static int NO_USERS_FOUND = -412;
        public final static int PROFILE_HAS_CHILDREN = -413;
        public final static int PRIVILEGE_NOT_FOUND = -414;
        public final static int DELETE_FAILED = -415;
        public final static int PRIVILEGE_HAS_CHILDREN = -416;
        public final static int GROUPS_NOT_FOUND = -417;

        public final static int QUERIES_NOT_FOUND = -418;
        public final static int USERNAME_SHOULD_BE_UNIQUE = -419;
        public final static int PROFILE_NOT_ACTIVE=-420;

        public static final int PROFILE_NAME_SHOULD_BE_UNIQUE = -421;
        public final static int CONCURRENT_SESSIONS_DETECTED=-422;

    }

}
