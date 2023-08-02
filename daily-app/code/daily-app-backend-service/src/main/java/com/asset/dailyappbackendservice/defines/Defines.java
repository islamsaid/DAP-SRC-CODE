package com.asset.dailyappbackendservice.defines;

public class Defines {

    public static class ContextPaths {
        public static final String LOGIN = "/user-management-service/login";
        public static final String LOGOUT = "/user-management-service/logout";

        public static final String REFRESH_PATH = "/config-service/actuator/refresh";

        public static final String STORE_TOKEN = "/store-token";

        public static final String HANDLER = "/handler";

    }

    public static class DEFAULT_PATHS{
        public static final String GET_NOTIFICATIONS = "/notification-service/notifications/get-all";
        public static final String ADD_LOGS = "/logging-service/logging/add-logs";
        public static final String DASHBOARD = "/report-service/dashboard/get";
        public static final String AGG_DASHBOARD = "/report-service/dashboard/agg-data/get";
    }

    public static class SEVERITY {

        public static final Integer CLEAR = 0;
        public static final Integer VALIDATION = 1;
        public static final Integer ERROR = 2;
        public static final Integer FATAL = 3;
    }
}
