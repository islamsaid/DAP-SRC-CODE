package com.asset.dailyappnotificationservice.dao;

import com.asset.dailyappnotificationservice.cache.PropertiesCache;
import com.asset.dailyappnotificationservice.constants.Queries;
import com.asset.dailyappnotificationservice.dao.extractors.KeysExtractor;
import com.asset.dailyappnotificationservice.dao.extractors.NotificationsExtractor;
import com.asset.dailyappnotificationservice.dao.preparedStatements.InsertNotificationsPreparedStatement;
import com.asset.dailyappnotificationservice.defines.DatabaseStructs;
import com.asset.dailyappnotificationservice.defines.Defines;
import com.asset.dailyappnotificationservice.defines.ErrorCodes;
import com.asset.dailyappnotificationservice.exceptions.NotificationsException;
import com.asset.dailyappnotificationservice.logger.DailyAppLogger;
import com.asset.dailyappnotificationservice.manager.NotificationsManager;
import com.asset.dailyappnotificationservice.models.NotificationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

@Component
public class NotificationsDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    PropertiesCache properties;

    public ConcurrentHashMap<Integer, String> getModelKeys(String modelType, Integer queryID) {
        String sqlQuery = null;
        try {
            sqlQuery = NotificationsManager.queries.get(queryID);
            return jdbcTemplate.query(sqlQuery, new KeysExtractor(modelType));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.debug("Query#{} = {}", queryID, sqlQuery);
            DailyAppLogger.DEBUG_LOGGER.error("Error with Getting {} Keys {}", modelType, ex);
            DailyAppLogger.ERROR_LOGGER.error("Error with Getting {} Keys {}", modelType, ex);
            ex.printStackTrace();
            throw new NotificationsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR);
        }
    }

    public ConcurrentHashMap<Integer, String> getServiceClassKeys() {
        String sqlQuery = null;
        try {
            sqlQuery = NotificationsManager.queries.get(Queries.GET_SERVICE_CLASS_KEYS.id);
            return jdbcTemplate.query(sqlQuery, new KeysExtractor(DatabaseStructs.MODEL_TYPE.SERVICE_CLASS));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.debug("Query#{} = {}", Queries.GET_SERVICE_CLASS_KEYS.id, sqlQuery);
            DailyAppLogger.DEBUG_LOGGER.error("Error with Getting service Class Keys ", ex);
            DailyAppLogger.ERROR_LOGGER.error("Error with Getting service Class Keys ", ex);
            ex.printStackTrace();
            throw new NotificationsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR);
        }
    }

    public ConcurrentHashMap<Integer, String> getTariffModelKeys() {
        String sqlQuery = null;
        try {
            sqlQuery = NotificationsManager.queries.get(Queries.GET_TARIFF_MODEL_KEYS.id); //Key, Name
            return jdbcTemplate.query(sqlQuery, new KeysExtractor(DatabaseStructs.MODEL_TYPE.TARIFF_MODEL));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.debug("Query#{} = {}", Queries.GET_TARIFF_MODEL_KEYS.id, sqlQuery);
            DailyAppLogger.DEBUG_LOGGER.error("Error with Getting Tariff Model Keys ", ex);
            DailyAppLogger.ERROR_LOGGER.error("Error with Getting Tariff Model Keys ", ex);
            ex.printStackTrace();
            throw new NotificationsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR);
        }
    }

    public ConcurrentHashMap<Integer, String> getPriceGroupKeys() {
        String sqlQuery = null;
        try {
            sqlQuery = NotificationsManager.queries.get(Queries.GET_PRICE_GROUP_KEYS.id); //Key, Name
            return jdbcTemplate.query(sqlQuery, new KeysExtractor(DatabaseStructs.MODEL_TYPE.PRICE_GROUP));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.debug("Query#{} = {}", Queries.GET_PRICE_GROUP_KEYS.id, sqlQuery);
            DailyAppLogger.DEBUG_LOGGER.error("Error with Getting Price Group Keys ", ex);
            DailyAppLogger.ERROR_LOGGER.error("Error with Getting Price Group Keys ", ex);
            ex.printStackTrace();
            throw new NotificationsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR);
        }
    }

    public ConcurrentHashMap<Integer, String> getRatePlanKeys() {
        String sqlQuery = null;
        try {
            sqlQuery = NotificationsManager.queries.get(Queries.GET_RATE_PLAN_KEYS.id); //Key, Name
            return jdbcTemplate.query(sqlQuery, new KeysExtractor(DatabaseStructs.MODEL_TYPE.RATE_PLAN));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.debug("Query#{} = {}", Queries.GET_RATE_PLAN_KEYS.id, sqlQuery);
            DailyAppLogger.DEBUG_LOGGER.error("Error with Getting Rate Plan Keys ", ex);
            DailyAppLogger.ERROR_LOGGER.error("Error with Getting Rate Plan Keys ", ex);
            ex.printStackTrace();
            throw new NotificationsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR);
        }
    }

    public void insertNotificationBatch(List<NotificationModel> notifications) {
        String sqlQuery = null;
        try {
            sqlQuery = NotificationsManager.queries.get(Queries.ADD_NOTIFICATION.id);
            jdbcTemplate.batchUpdate(sqlQuery, new InsertNotificationsPreparedStatement(notifications));
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.debug("Query#{} = {}", Queries.ADD_NOTIFICATION.id, sqlQuery);
            DailyAppLogger.ERROR_LOGGER.error("Error notifications cannot be inserted", ex);
            DailyAppLogger.DEBUG_LOGGER.error("Error notifications cannot be inserted", ex);
            ex.printStackTrace();
            throw new NotificationsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR);
        }
    }

    public List<NotificationModel> getAllNotifications() {
        String sqlQuery = null;
        try {
            sqlQuery = NotificationsManager.queries.get(Queries.GET_ALL_NOTIFICATIONS.id);
            return jdbcTemplate.query(sqlQuery, new NotificationsExtractor(), properties.getNotificationLifetimeInDays());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.debug("Query#{} = {}", Queries.GET_ALL_NOTIFICATIONS.id, sqlQuery);
            DailyAppLogger.ERROR_LOGGER.error("Error notifications cannot be retrieved", ex);
            DailyAppLogger.DEBUG_LOGGER.error("Error notifications cannot be retrieved", ex);
            ex.printStackTrace();
            throw new NotificationsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR);
        }
    }

    public void expireNotification(NotificationModel notification){
        String sqlQuery = null;
        try {
            sqlQuery = NotificationsManager.queries.get(Queries.EXPIRE_NOTIFICATION.id);
            jdbcTemplate.update(sqlQuery, notification.getNotificationType(), notification.getDataId());
        }catch(Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.debug("Query#{} = {}", Queries.EXPIRE_NOTIFICATION.id, sqlQuery);
            DailyAppLogger.ERROR_LOGGER.error("Error setting action done by 0 ", ex);
            DailyAppLogger.DEBUG_LOGGER.error("Error setting action done by 0 ", ex);
            ex.printStackTrace();
            throw new NotificationsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR);
        }
    }

    public NotificationModel getNotificationById(Integer notificationId){
        String sqlQuery = null;
        try {
            sqlQuery = NotificationsManager.queries.get(Queries.GET_NOTIFICATION_BY_ID.id);
            return jdbcTemplate.queryForObject(sqlQuery, new BeanPropertyRowMapper<>(NotificationModel.class), notificationId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }catch(Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.debug("Query#{} = {}", Queries.GET_NOTIFICATION_BY_ID.id, sqlQuery);
            DailyAppLogger.DEBUG_LOGGER.error("Error getting notification By ID = {}, {}", notificationId, ex);
            DailyAppLogger.ERROR_LOGGER.error("Error getting notification By ID = {}, {}", notificationId, ex);
            ex.printStackTrace();
            throw new NotificationsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR);
        }
    }

    public NotificationModel getNotificationByCheckSum(String checkSum){
        String sqlQuery = null;
        try {
            sqlQuery = NotificationsManager.queries.get(Queries.GET_NOTIFICATION_BY_CHECK_SUM.id);
            return jdbcTemplate.queryForObject(sqlQuery, new BeanPropertyRowMapper<>(NotificationModel.class), checkSum);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }catch(Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.debug("Query#{} = {}", Queries.GET_NOTIFICATION_BY_CHECK_SUM.id, sqlQuery);
            DailyAppLogger.DEBUG_LOGGER.error("Error GET_NOTIFICATION_BY_CHECK_SUM  [{}]  {}", checkSum, ex);
            DailyAppLogger.ERROR_LOGGER.error("Error GET_NOTIFICATION_BY_CHECK_SUM  [{}]  {}", checkSum, ex);
            ex.printStackTrace();
            throw new NotificationsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR);
        }
    }
}
