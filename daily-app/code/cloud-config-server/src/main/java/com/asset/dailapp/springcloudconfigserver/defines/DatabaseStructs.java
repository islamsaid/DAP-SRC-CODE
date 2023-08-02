package com.asset.dailapp.springcloudconfigserver.defines;

public class DatabaseStructs {

    public  static final class DAILY_QUERIES {
        public static final String TABLE_NAME = "DAILY_QUERIES";
        public static final String ID = "ID";
        public static final String SEQ = "SEQ";
        public static final String SQL = "SQL";
        public static final String QUERIES_DESC = "QUERIES_DESC";
        public static final String QUERIES_NAME = "QUERIES_NAME";
    }

    public  static final class DAILY_SYSTEM_PROPERTIES {
        public static final String TABLE_NAME = "DAILY_SYSTEM_PROPERTIES";
        public static final String PROFILE = "PROFILE";
        public static final String APPLICATION = "APPLICATION";
        public static final String LABEL = "LABEL";
        public static final String CODE = "CODE";
        public static final String VALUE = "VALUE";
        public static final String TYPE = "TYPE";
        public static final String DESCRIPTION = "DESCRIPTION";
    }
}