import { Defines } from './defines';
import { environment } from './../../../environments/environment';
export class ACTIONS {
  public static ACTIONS_DETAILS = [
    {
      url:
      environment.url +
      Defines.AUTH_ENDPOINTS.HANDLER_PATH +
      Defines.AUTH_ENDPOINTS.TOKEN_PATH,
      id: 1,
      pageName: 'login',
    },
    {
      url:
        environment.url +
        Defines.LOOKUPS.LOOKUP_PATH +
        Defines.LOOKUPS.CLASSES +
        Defines.CRUD.GET_ALL,
      id: 2,
      pageName: 'services class list',
    },
    {
      url:
        environment.url +
        Defines.LOOKUPS.LOOKUP_PATH +
        Defines.LOOKUPS.CLASSES +
        Defines.CRUD.UPDATE_BATCH,
      id: 3,
      pageName: 'update classes',
    },
    {
      url:
        environment.url +
        Defines.LOOKUPS.LOOKUP_PATH +
        Defines.LOOKUPS.TARIFFS +
        Defines.CRUD.GET_ALL,
      id: 4,
      pageName: 'services tariffs list',
    },
    {
      url:
        environment.url +
        Defines.LOOKUPS.LOOKUP_PATH +
        Defines.LOOKUPS.TARIFFS +
        Defines.CRUD.UPDATE_BATCH,
      id: 5,
      pageName: 'update tariffs',
    },
    {
      url:
        environment.url +
        Defines.LOOKUPS.LOOKUP_PATH +
        Defines.RATEPLANS_ENDPOINTS.RATEPALN +
        Defines.CRUD.GET_ALL,
      id: 6,
      pageName: 'rateplan list',
    },
    {
      url:
        environment.url +
        Defines.LOOKUPS.LOOKUP_PATH +
        Defines.RATEPLANS_ENDPOINTS.RATEPALN +
        Defines.CRUD.UPDATE_BATCH,
      id: 7,
      pageName: 'update rateplan',
    },
    {
      url:
        environment.url +
        Defines.LOOKUPS.LOOKUP_PATH +
        Defines.RATEPLANS_ENDPOINTS.RATEPALNGROUPS +
        Defines.CRUD.GET_ALL,
      id: 8,
      pageName: 'rateplan groups list',
    },
    {
      url:
        environment.url +
        Defines.LOOKUPS.LOOKUP_PATH +
        Defines.RATEPLANS_ENDPOINTS.RATEPALNGROUPS +
        Defines.CRUD.ADD,
      id: 9,
      pageName: 'add rateplan group',
    },
    {
      url:
        environment.url +
        Defines.LOOKUPS.LOOKUP_PATH +
        Defines.RATEPLANS_ENDPOINTS.RATEPALNGROUPS +
        Defines.CRUD.UPDATE,
      id: 10,
      pageName: 'update rateplan group',
    },
    {
      url:
        environment.url +
        Defines.LOOKUPS.LOOKUP_PATH +
        Defines.RATEPLANS_ENDPOINTS.RATEPALNGROUPS +
        Defines.CRUD.DELETE,
      id: 11,
      pageName: 'delete rateplan group',
    },
    {
      url:
        environment.url +
        Defines.LOOKUPS.LOOKUP_PATH +
        Defines.LOOKUPS.PRICES +
        Defines.CRUD.GET_ALL,
      id: 12,
      pageName: 'price group list',
    },
    {
      url:
        environment.url +
        Defines.LOOKUPS.LOOKUP_PATH +
        Defines.LOOKUPS.PRICES +
        Defines.CRUD.UPDATE_BATCH,
      id: 13,
      pageName: 'update price groups ',
    },
    {
      url:
        environment.url +
        Defines.LOOKUPS.LOOKUP_PATH +
        Defines.LOOKUPS.PG +
        Defines.CRUD.GET_ALL,
      id: 14,
      pageName: 'pg groups list',
    },
    {
      url:
        environment.url +
        Defines.LOOKUPS.LOOKUP_PATH +
        Defines.LOOKUPS.PG +
        Defines.CRUD.ADD,
      id: 15,
      pageName: 'add pg group',
    },
    {
      url:
        environment.url +
        Defines.LOOKUPS.LOOKUP_PATH +
        Defines.LOOKUPS.PG +
        Defines.CRUD.UPDATE,
      id: 16,
      pageName: 'update pg group',
    },
    {
      url:
        environment.url +
        Defines.LOOKUPS.LOOKUP_PATH +
        Defines.LOOKUPS.PG +
        Defines.CRUD.DELETE,
      id: 17,
      pageName: 'delete pg group',
    },
    {
      url:
        environment.url +
        Defines.VALIDATION_ENDPOINTS.REPORT_SERVICE +
        Defines.VALIDATION_ENDPOINTS.VALIDATION_ENGINE +
        Defines.VALIDATION_ENDPOINTS.BALANCES +
        Defines.CRUD.GET_ALL,
      id: 18,
      pageName: 'balance',
    },
    {
      url:
        environment.url +
        Defines.VALIDATION_ENDPOINTS.REPORT_SERVICE +
        Defines.VALIDATION_ENDPOINTS.VALIDATION_ENGINE +
        Defines.VALIDATION_ENDPOINTS.AGGREGATION_DATA +
        Defines.CRUD.GET_ALL,
      id: 19,
      pageName: 'validation list',
    },
    {
      url:
        environment.url +
        Defines.VALIDATION_ENDPOINTS.REPORT_SERVICE +
        Defines.VALIDATION_ENDPOINTS.VALIDATION_ENGINE +
        Defines.CRUD.UPDATE,
      id: 20,
      pageName: 'update validations',
    },
    {
      url: '',
      id: 21,
      pageName: 'export validations',
    },
    {
      url:
        environment.url +
        Defines.ADJUSTMENT_ENDPOINTS.REPORT_SERVICE +
        Defines.ADJUSTMENT_ENDPOINTS.MANUAL_ADJUSTMENT +
        Defines.CRUD.GET_ALL,
      id: 22,
      pageName: 'manual-adjusts',
    },
    {
      url:
        environment.url +
        Defines.ADJUSTMENT_ENDPOINTS.REPORT_SERVICE +
        Defines.ADJUSTMENT_ENDPOINTS.MANUAL_ADJUSTMENT +
        Defines.CRUD.UPDATE,
      id: 23,
      pageName: 'update manual-adjusts',
    },
    {
      url: '',
      id: 24,
      pageName: 'export manual',
    },
    {
      url:
        environment.url +
        Defines.TRANSFER_ENDPOINTS.REPORT_SERVICE +
        Defines.TRANSFER_ENDPOINTS.TRANSFER_ADJUSTMENT +
        Defines.TRANSFER_ENDPOINTS.AGGREGATION_DATA +
        Defines.CRUD.GET_ALL,
      id: 25,
      pageName: 'transfer-adjusts',
    },
    {
      url:
        environment.url +
        Defines.TRANSFER_ENDPOINTS.REPORT_SERVICE +
        Defines.TRANSFER_ENDPOINTS.TRANSFER_ADJUSTMENT +
        Defines.TRANSFER_ENDPOINTS.AGGREGATION_DATA +
        Defines.CRUD.ADD,
      id: 26,
      pageName: 'Add transfer-adjusts',
    },
    {
      url: '',
      id: 27,
      pageName: 'export transfer',
    },
    {
      url:
        environment.url +
        Defines.USERS_ENDPOINTS.USERS_MANAGEMENT_PATH +
        Defines.USERS_ENDPOINTS.USER +
        Defines.CRUD.GET_ALL,
      id: 28,
      pageName: 'users list',
    },
    {
      url:
        environment.url +
        Defines.USERS_ENDPOINTS.USERS_MANAGEMENT_PATH +
        Defines.USERS_ENDPOINTS.USER +
        Defines.CRUD.ADD,
      id: 29,
      pageName: 'add user',
    },
    {
      url:
        environment.url +
        Defines.USERS_ENDPOINTS.USERS_MANAGEMENT_PATH +
        Defines.USERS_ENDPOINTS.USER +
        Defines.CRUD.UPDATE,
      id: 30,
      pageName: 'update user',
    },
    {
      url:
        environment.url +
        Defines.USERS_ENDPOINTS.USERS_MANAGEMENT_PATH +
        Defines.USERS_ENDPOINTS.USER +
        Defines.CRUD.DELETE,
      id: 31,
      pageName: 'delete user',
    },
    {
      url:
        environment.url +
        Defines.USERS_ENDPOINTS.USERS_MANAGEMENT_PATH +
        Defines.PROFILES_ENDPOINTS.GET_PROFILES_LIST,
      id: 33,
      pageName: 'profiles list',
    },
    {
      url:
        environment.url +
        Defines.USERS_ENDPOINTS.USERS_MANAGEMENT_PATH +
        Defines.PROFILES_ENDPOINTS.CREATE_PROFILE,
      id: 34,
      pageName: 'add profile',
    },
    {
      url:
        environment.url +
        Defines.USERS_ENDPOINTS.USERS_MANAGEMENT_PATH +
        Defines.PROFILES_ENDPOINTS.UPDATE_PROFILE,
      id: 35,
      pageName: 'update profile',
    },
    {
      url:
        environment.url +
        Defines.USERS_ENDPOINTS.USERS_MANAGEMENT_PATH +
        Defines.PROFILES_ENDPOINTS.DELETE_PROFILE,
      id: 36,
      pageName: 'delete profile',
    },
    // {
    //   url:
    //     environment.url +
    //     Defines.NOTIFICTION_ENDPOINTS.NOTIFICATION_SERVICE +
    //     Defines.NOTIFICTION_ENDPOINTS.NOTIFICATIONS +
    //     Defines.CRUD.GET_ALL,
    //   id: 37,
    //   pageName: 'notifications',
    // },
    {
      url:'',
      id: 40,
      pageName: 'get all services classes key',
    },
    {
      url:'',
      id: 41,
      pageName: 'get all services tariffs key',
    },
    {
      url:'',
      id: 42,
      pageName: 'get all prices groups key',
    },
    {
      url:'',
      id: 43,
      pageName: 'get all rateplans key',
    },
    {
      url:'',
      id: 44,
      pageName: 'get accounts',
    },
    {
      url:'',
      id: 45,
      pageName: 'get notification by id',
    },
    {
      url:
        environment.url +
        Defines.SYSTEM_CONFIG_ENDPOINTS.CONFIG_SERVICE +
        Defines.SYSTEM_CONFIG_ENDPOINTS.SYSTEM_SETTING +
        Defines.CRUD.GET_ALL,
      id: 46,
      pageName: 'configuration list',
    },
    {
      url:
        environment.url +
        Defines.SYSTEM_CONFIG_ENDPOINTS.CONFIG_SERVICE +
        Defines.SYSTEM_CONFIG_ENDPOINTS.SYSTEM_SETTING +
        Defines.CRUD.UPDATE,
      id: 47,
      pageName: 'update configurations',
    },

    {
      url:
      environment.url +
      Defines.FOOTPRINT.LOGGING_SERVICE +
      Defines.FOOTPRINT.LOGGING +
      Defines.FOOTPRINT.GET_LOGS,
      id: 48,
      pageName: 'logs',
    },
    {
      url:environment.url +
      Defines.VALIDATION_ENDPOINTS.REPORT_SERVICE +
      Defines.VALIDATION_ENDPOINTS.DASH_BOARD +
      Defines.VALIDATION_ENDPOINTS.AGGREGATION_DATA +
      Defines.CRUD.GET,
      id: 49,
      pageName: 'min-max aggregation data',
    },
  ];
}
