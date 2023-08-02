export class Defines {
  public static AUTH_ENDPOINTS = {
    LOGIN_PATH: '/login',
    LOGOUT_PATH: '/logout',
    HANDLER_PATH: '/handler',
    TOKEN_PATH: '/store-token',
  };
  public static CRUD = {
    GET: '/get',
    ADD: '/add',
    UPDATE: '/update',
    DELETE: '/delete',
    GET_ALL: '/get-all',
    UPDATE_ALL: '/update-all',
    UPDATE_BATCH: '/update-batch',
  };
  public static USERS_ENDPOINTS = {
    USERS_MANAGEMENT_PATH: '/user-management-service',
    USER: '/user',
  };
  public static PROFILES_ENDPOINTS = {
    GET_PROFILES_LIST: '/profile/get-all',
    GET_PROFILE_BY_ID: '/profile/get',
    CREATE_PROFILE: '/profile/add',
    UPDATE_PROFILE: '/profile/update',
    DELETE_PROFILE: '/profile/delete',
  };
  public static PRIVILEGES_ENDPOINTS = {
    PRIVILEGE: '/privilege',
  };
  public static RATEPLANS_ENDPOINTS = {
    RATEPALN: '/rate-plan',
    RATEPALNGROUPS: '/rate-plan-groups',
  };

  public static ROUTES = {
    DAILY: 'daily',
    USERS: 'users',
    LIST: 'list',
    PROIFLES: 'profiles',
    PRICES: 'prices',
    PGGROUPS: 'pgGroups',
    ADD: 'add',
  };

  public static RESPONSE_STATUS = {
    NON_AUTH: -402,
    SUCCESS: 0,
    NO_DATA: -421,
    NOT_ALLOWED: -4003
  };

  public static LOOKUPS = {
    LOOKUP_PATH: '/lookup-service',
    CLASSES: '/service-classes',
    TARIFFS: '/tariff-model',
    PRICES: '/price-group',
    PG: '/price-group-groups',
  };

  public static VALIDATION_ENDPOINTS = {
    REPORT_SERVICE: '/report-service',
    VALIDATION_ENGINE: '/validation-engine',
    AGGREGATION_DATA: '/agg-data',
    BALANCES: '/balances',
    TRANSACTION_TYPE: '/transaction-type',
    DASH_BOARD: '/dashboard',
  };

  public static ADJUSTMENT_ENDPOINTS = {
    REPORT_SERVICE: '/report-service',
    MANUAL_ADJUSTMENT: '/manual-adjustment',
    AGGREGATION_DATA: '/agg-data',
  };
  public static TRANSFER_ENDPOINTS = {
    REPORT_SERVICE: '/report-service',
    TRANSFER_ADJUSTMENT: '/transfer-adjustment',
    AGGREGATION_DATA: '/agg-data',
  };
  public static SYSTEM_CONFIG_ENDPOINTS = {
    CONFIG_SERVICE: '/config-service',
    SYSTEM_SETTING: '/system-setting',
    ACTUATOR: '/actuator',
    REFRESH: '/refresh',
  };
  public static NOTIFICTION_ENDPOINTS = {
    NOTIFICATION_SERVICE: '/notification-service',
    NOTIFICATIONS: '/notifications',
  };
  public static NOTIFICATION_TYPE = {
    SERVICE_CLASS: 1,
    TARRIF_MODEL: 2,
    PRICE_GROUP: 3,
    RATE_PALEN: 4,
  };
  public static FOOTPRINT = {
    LOGGING_SERVICE: '/logging-service',
    LOGGING: '/logging',
    ADD_LOG: '/add-logs',
    GET_LOGS: '/get-logs',
  };

}
