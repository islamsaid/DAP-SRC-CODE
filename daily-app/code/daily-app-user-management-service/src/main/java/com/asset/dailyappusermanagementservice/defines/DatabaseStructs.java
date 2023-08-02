package com.asset.dailyappusermanagementservice.defines;

public class DatabaseStructs {
    public static final class ADM_USERS {
        public static final String TABLE_NAME = "ADM_USERS";
        public static final String USER_ID = "USER_ID";
        public static final String NAME = "NAME";
        public static final String USERNAME = "USERNAME";
        public static final String PROFILE_ID = "PROFILE_ID";
        public static final String LOCK_FLAG = "LOCK_FLAG";
        public static final String SEQUENCE_NAME = "SEQ_USERS";
    }
    public static final class DAILY_PROFILES {
        public static final String TABLE_NAME = "DAILY_PROFILES";
        public static final String TABLE_ALIAS = "PROFILES";
        public static final String ID = "ID";
        public static final String PROFILE_ID_ALIAS = "PROFILE_ID";
        public static final String PROFILE_NAME_ALIAS = "PROFILE_NAME";

        public static final String NAME = "NAME";
        public static final String IS_ACTIVE = "IS_ACTIVE";
        public static final String CREATED_DATE = "CREATED_DATE";
        public static final String CREATED_BY = "CREATED_BY";
        public static final String SEQUENCE_NAME = "SEQ_PROFILES";
    }
    public static final class DAILY_PROFILE_PRIVILEGES {

        public static final String TABLE_NAME = "DAILY_PROFILE_PRIVILEGES";
        public static final String PROFILE_ID = "PROFILE_ID";
        public static final String PRIVILEGES_ID = "PRIVILEGES_ID";
        public static final String ORDERING = "ORDERING";

    }
    public static final class DAILY_PRIVILEGES {

        public static final String TABLE_NAME = "DAILY_PRIVILEGES";
        public static final String PRIVILEGE_ID_ALIAS = "PRIVILEGE_ID";
        public static final String ID = "ID";
        public static final String NAME = "NAME";
        //alias
        public static final String PRIVILEGE_NAME_ALIAS = "PRIVILEGE_NAME";
        public static final String PAGE_NAME = "PAGE_NAME";
        public static final String BACK_END_URL = "BACK_END_URL";
        public static final String DESCRIPTION = "DESCRIPTION";
        public static final String SEQUENCE_NAME =  "SEQ_PRIVILEGES";

    }
public  static  final  class PRICE_GROUP{
    public static final String TABLE_NAME = "PRICE_GROUP";
    public static final String PRICE_GROUP_KEY ="PRICE_GROUP_KEY";
    public static final String PRICE_GROUP ="PRICE_GROUP";
    public static final String PRICE_GROUP_CODE ="PRICE_GROUP_CODE";
    public static final String ENTRY_DATE ="ENTRY_DATE";
    public static final String MODIFICATION_DATE ="MODIFICATION_DATE";
    public static final String PG_GROUP_KEY ="PG_GROUP_KEY";
}

    public  static  final  class PG_GROUP{
        public static final String TABLE_NAME = "PG_GROUP";
        public static final String PG_GROUP_KEY ="PG_GROUP_KEY";
        public static final String PG_GROUP ="PG_GROUP";
        public static final String SHOW_FLAG ="SHOW_FLAG";
        public static final String DESCRIPTION ="DESCRIPTION";
    }

public  static final class DAILY_QUERIES {
    public static final String TABLE_NAME = "DAILY_QUERIES";
    public static final String ID = "ID";
    public static final String SEQ = "SEQ";
    public static final String SQL = "SQL";
    public static final String QUERIES_DESC = "QUERIES_DESC";
    public static final String QUERIES_NAME = "QUERIES_NAME";
}

}