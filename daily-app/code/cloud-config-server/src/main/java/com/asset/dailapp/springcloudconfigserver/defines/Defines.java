package com.asset.dailapp.springcloudconfigserver.defines;

public class Defines {

    public static class ContextPaths {

        public static final String BASE_CONTEXT_PATH = "/cloud-config";
        public static final String SYSTEM_SETTING = "/config-service/system-setting";


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
    }


    public static class SEVERITY {

        public static final Integer CLEAR = 0;
        public static final Integer VALIDATION = 1;
        public static final Integer ERROR = 2;
        public static final Integer FATAL = 3;
    }

    public static class FORMATS{
        public static final String DATE_FORMAT = "dd/MM/yyyy";
    }
}
