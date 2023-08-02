export class PRIVILEGES {
  public static USERS_MANAGMENT = {
    GET_ALL: 1,
    GET_BY_ID:2,
    ADD: 3,
    UPDATE: 4,
    DELETE: 5,
  };
  public static PROFILES_MANAGMENT = {
    GET_ALL: 8,
    GET_BY_ID:7,
    ADD: 6,
    UPDATE: 10,
    DELETE: 9,
  };
  public static SERVICES_CLASSES = {
    GET_ALL: 11,
    UPDATE: 12,
  };
  public static SERVICES_TARIFFS = {
    GET_ALL: 13,
    UPDATE: 14,
  };

  public static RATE_PLANS = {
    GET_ALL: 15,
    UPDATE: 16,
    UPDATE_lIST: 17,
  };
  public static RATE_PLANS_GROUP = {
    GET_ALL: 18,
    GET_BY_ID:19,
    ADD: 21,
    UPDATE: 20,
    DELETE: 22,
  };
  public static PRICE_GROUP = {
    GET_ALL: 23,
    UPDATE: 24,
  };
  public static PG_GROUP = {
    GET_ALL: 25,
    GET_BY_ID:29,
    ADD: 26,
    UPDATE: 27,
    DELETE: 30,
  };

  public static VALIDATION = {
    BALANCE:31,
    GET_ALL: 32,
    UPDATE: 34,
  };

  public static MANUAL_ADJUSTMENT = {
    GET_ALL: 35,
    UPDATE: 36,
  };

  public static TRANSFER_ADJUSTMENT = {
    GET_ALL: 37,
    UPDATE: 38,
  };
  public static NOTIFICATIONS = {
    GET_ALL: 39,
    UPDATE_ACTION:40
  };
  public static CONFIGRATION = {
    GET_ALL: 44,
    UPDATE:41
  };
  public static LOGS = {
    GET_ALL: 42,
  };
  public static DASHBOARD = {
    MAX_MIN_AGG_DATE: 43,
  };


}
