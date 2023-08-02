package com.asset.dailyapplookupservice.defines;

public class Defines {

    public static class ContextPaths {

        public static final String BASE_CONTEXT_PATH = "/dailyapp";

        public static final String PRICE_GROUP = "/price-group";
        public static final String PRICE_GROUP_GROUPS = "/price-group-groups";
        public static final String RATE_PLAN = "/rate-plan";
        public static final String RATE_PLAN_GROUPS = "/rate-plan-groups";
        public static final String SERVICE_CLASSES = "/service-classes";
        public static final String TARIFF_MODEL = "/tariff-model";

    }

    public static class WEB_ACTIONS {

        public static final String GET = "/get";
        public static final String GET_ALL = "/get-all";
        public static final String GET_ALL_PRIVILEGES = "/get-all-privileges";
        public static final String ADD_PRIVILEGE = "/add/privilege";
        public static final String DELETE_PRIVILEGE = "/delete/privilege";

        public static final String ADD = "/add";
        public static final String UPDATE = "/update";
        public static final String UPDATE_BATCH = "/update-batch";

        public static final String UPDATE_ALL = "/update-all";
        public static final String DELETE = "/delete";
        public static final String DELETE_ALL = "/delete-all";
        public static final String UPLOAD = "/upload";
        public static final String DOWNLOAD = "/download";
        public static final String CHECK = "/check";
    }


    public static class SEVERITY {

        public static final Integer CLEAR = 0;
        public static final Integer VALIDATION = 1;
        public static final Integer ERROR = 2;
        public static final Integer FATAL = 3;
        public static final Integer WARNING = 5;

    }

}
