package com.asset.dailyappnotificationservice.defines;

public class Defines
{
    public static class CONTEXT_PATHS{
        public static final String BASE_CONTEXT_PATH = "/dailyapp";
        public static final String NOTIFICATIONS_SERVICE = "notifications";
    }

    public static class WEB_ACTIONS {
        public static final String GET = "/get";
        public static final String GET_ALL = "/get-all";
        public static final String ADD = "/add";

        public static final String UPDATE = "/update";
        public static final String UPDATE_BATCH = "/update-batch";
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
    }

    public static class FORMATS{
        public static final String DATE_FORMAT = "DD-MM-YYYY hh:mm:ss";
        public static final String SYSTIMESTAMP = "SYSTIMESTAMP";
    }

    public static class MODELS {
        public static final Integer SERVICE_CLASS_TYPE = 1;
        public static final String SERVICE_CLASS_HEADER = "New service class was added";

        public static final Integer TARIFF_MODEL_TYPE = 2;
        public static final String TARIFF_MODEL_HEADER = "New tariff model was added";

        public static final Integer PRICE_GROUP_TYPE = 3;
        public static final String PRICE_GROUP_HEADER = "New price group was added";

        public static final Integer RATE_PLAN_TYPE = 4;
        public static final String RATE_PLAN_HEADER = "New rate plan was added";

        public static StringBuilder generateDetails(String typeName, String modelKey, String modelName){
            StringBuilder details = new StringBuilder();
            details.append(typeName).append(" Key = ").append(modelKey).append(", Name = ").append(modelName);
            return details;
        }
    }
}
