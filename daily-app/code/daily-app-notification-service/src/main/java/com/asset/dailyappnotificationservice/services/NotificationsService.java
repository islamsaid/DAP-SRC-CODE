package com.asset.dailyappnotificationservice.services;

import com.asset.dailyappnotificationservice.dao.NotificationsDao;
import com.asset.dailyappnotificationservice.defines.Defines;
import com.asset.dailyappnotificationservice.defines.ErrorCodes;
import com.asset.dailyappnotificationservice.exceptions.NotificationsException;
import com.asset.dailyappnotificationservice.logger.DailyAppLogger;
import com.asset.dailyappnotificationservice.models.NotificationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

@Service
@Component
public class NotificationsService {
    @Autowired
    NotificationsDao notificationsDao;

    public ConcurrentHashMap<Integer, String> loadServiceClassesKeys() {
        ConcurrentHashMap<Integer, String> keysMap = notificationsDao.getServiceClassKeys();
        if (!keysMap.isEmpty() || keysMap != null)
            return keysMap;
        DailyAppLogger.DEBUG_LOGGER.error("Service Class Table is Empty");
        DailyAppLogger.ERROR_LOGGER.error("Service Class Table is Empty");
        throw new NotificationsException(ErrorCodes.ERROR.NO_RECORDS_FOUND, Defines.SEVERITY.ERROR);
    }

    public ConcurrentHashMap<Integer, String> loadTariffModelsKeys() {
        ConcurrentHashMap<Integer, String> keysMap = notificationsDao.getTariffModelKeys();
        if (!keysMap.isEmpty() || keysMap != null)
            return keysMap;
        DailyAppLogger.DEBUG_LOGGER.error("Tariff Model Table is Empty");
        DailyAppLogger.ERROR_LOGGER.error("Tariff Model Table is Empty");
        throw new NotificationsException(ErrorCodes.ERROR.NO_RECORDS_FOUND, Defines.SEVERITY.ERROR);
    }

    public ConcurrentHashMap<Integer, String> loadPriceGroupsKeys() {
        ConcurrentHashMap<Integer, String> keysMap = notificationsDao.getPriceGroupKeys();
        if (!keysMap.isEmpty() || keysMap != null)
            return keysMap;
        DailyAppLogger.DEBUG_LOGGER.error("Price Group Table is Empty");
        DailyAppLogger.ERROR_LOGGER.error("Price Group Table is Empty");
        throw new NotificationsException(ErrorCodes.ERROR.NO_RECORDS_FOUND, Defines.SEVERITY.ERROR);
    }

    public ConcurrentHashMap<Integer, String> loadRatePlansKeys() {
        ConcurrentHashMap<Integer, String> keysMap = notificationsDao.getRatePlanKeys();
        if (!keysMap.isEmpty() || keysMap != null)
            return keysMap;
        DailyAppLogger.DEBUG_LOGGER.error("Rate Plan Table is Empty");
        DailyAppLogger.ERROR_LOGGER.error("Rate Plan Table is Empty");
        throw new NotificationsException(ErrorCodes.ERROR.NO_RECORDS_FOUND, Defines.SEVERITY.ERROR);
    }

    public void insertNotificationsBatch(List<NotificationModel> notifications) {
        notificationsDao.insertNotificationBatch(notifications);
    }

    public List<NotificationModel> getAllNotifications() {
        List<NotificationModel> notifications = notificationsDao.getAllNotifications();
        if(notifications.isEmpty() || notifications == null)
            DailyAppLogger.INFO_LOGGER.debug("No available notifications");
        return notifications;
    }

    public void expireNotification(NotificationModel notification){
        notificationsDao.expireNotification(notification);
    }

    public Boolean checkNotificationExistenceByCheckSum(String checkSum){
        NotificationModel notification = notificationsDao.getNotificationByCheckSum(checkSum);
        if(notification == null)
            return false;
        return true;
    }
}
