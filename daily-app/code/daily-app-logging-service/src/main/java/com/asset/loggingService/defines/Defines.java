package com.asset.loggingService.defines;

public class Defines {

    public static class ContextPaths {

        public static final String BASE_CONTEXT_PATH = "/dailyapp";
        public static final String VALIDATE_ENGINE  = "/validation-engine";
        public static final String MANUAL_ADJUSTMENT  = "/manual-adjustment";
        public static final String TRANSFER_ADJUSTMENT   = "/transfer-adjustment/agg-data";


    }

    public static class WEB_ACTIONS {

        public static final String GET = "/get";
        public static final String GET_ALL = "/get-all";
        public static final String GET_ALL_PRIVILEGES = "/get-all-privileges";
        public static final String ADD_PRIVILEGE = "/add/privilege";
        public static final String DELETE_PRIVILEGE = "/delete/privilege";

        public static final String ADD = "/add";
        public static final String UPDATE = "/update";
        public static final String UPDATE_ALL = "/update-all";
        public static final String DELETE = "/delete";
        public static final String DELETE_ALL = "/delete-all";
        public static final String UPLOAD = "/upload";
        public static final String DOWNLOAD = "/download";
        public static final String CHECK = "/check";
        public static final String BALANCE_ALL  = "/balances/get-all";
        public static final String Retrieve_aggregation_data  = "/agg-data/get-all";
        public static final String transaction_type = "/transaction-type/get-all";
        public static final String ALL_transaction_type = "/all-transaction-type/get-all";

    }


    public static class SEVERITY {

        public static final Integer CLEAR = 0;
        public static final Integer VALIDATION = 1;
        public static final Integer ERROR = 2;
        public static final Integer FATAL = 3;
    }

    public static class FORMATS{
        public static final String DATE_FORMAT = "dd/MM/YYYY";
    }
}
