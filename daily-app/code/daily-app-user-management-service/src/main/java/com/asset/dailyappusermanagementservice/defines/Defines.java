package com.asset.dailyappusermanagementservice.defines;

public class Defines {

    public static class ContextPaths {

        public static final String BASE_CONTEXT_PATH = "/dailyapp";
        public static final String LOGIN = "/login";
        public static final String LOGOUT = "/logout";

        public static final String USER = "/user";
        public static final String PROFILE = "/profile";
        public static final String PROFILE_GROUP = "/price-group";
        public static final String PROFILE_GROUP_GROUPS = "/price-group-groups";

        public static final String PRIVILEGE = "/privilege";

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

    public static class SecurityKeywords {

        public static final String USERNAME = "username";
        public static final String PREFIX = "prefix";
        public static final String SESSION_ID = "sessionId";
        public static final String PROFILE_ROLE = "profile";
        public static final String PROFILE_ID = "profileId";
        public static final String USER_ID = "userId";
    }

    public static class SEVERITY {

        public static final Integer CLEAR = 0;
        public static final Integer VALIDATION = 1;
        public static final Integer ERROR = 2;
        public static final Integer FATAL = 3;
    }

}
