package com.asset.dailyappnotificationservice.tasks;

import com.asset.dailyappnotificationservice.cache.PropertiesCache;
import com.asset.dailyappnotificationservice.defines.DatabaseStructs;
import com.asset.dailyappnotificationservice.defines.Defines;
import com.asset.dailyappnotificationservice.executor.LogNotificationExecutor;
import com.asset.dailyappnotificationservice.logger.DailyAppLogger;
import com.asset.dailyappnotificationservice.manager.NotificationsManager;
import com.asset.dailyappnotificationservice.models.EmailQueue;
import com.asset.dailyappnotificationservice.models.NotificationModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import com.asset.dailyappnotificationservice.services.NotificationsService;
import com.asset.emailbroker.integration.model.EmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class ServiceClassNotificationsTask implements Runnable {

    private final NotificationsManager notificationsManager;
    private ConcurrentHashMap<Integer, String> cacheServiceClasses;
    private LogNotificationExecutor loggerNotification;
    private final EmailQueue emailQueue;

    @Autowired
    private PropertiesCache properties;

    @Autowired
    private NotificationsService notificationsService;

    @Autowired
    public ServiceClassNotificationsTask(NotificationsManager notificationsManager, LogNotificationExecutor loggerNotification, EmailQueue emailQueue) {
        this.notificationsManager = notificationsManager;
        this.loggerNotification = loggerNotification;
        this.emailQueue = emailQueue;
    }

    @Override
    public void run() {
        ConcurrentHashMap<Integer, String> cacheServiceClasses = notificationsService.loadServiceClassesKeys();
        for (Map.Entry<Integer, String> entry : cacheServiceClasses.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();
            if (!notificationsManager.cacheServiceClasses.containsKey(key)) {
                try {
                    DailyAppLogger.INFO_LOGGER.info("Adding new service class notification...");
                    notificationsManager.cacheServiceClasses.put(key, value);

                    NotificationModel payload = setNotification(key, value);
                    DailyAppLogger.INFO_LOGGER.info("check notification uniqueness...");
                    if(!notificationsService.checkNotificationExistenceByCheckSum(payload.getCheckSum()))
                    {
                        DailyAppLogger.INFO_LOGGER.info("-----notification----");
                        loggerNotification.execute(payload);
                        if (properties.getSendEmailFlag()) {
                            DailyAppLogger.INFO_LOGGER.info("sending a notification with email");
                            EmailMessage email = setEmailMessage(key, value);
                            emailQueue.emailMessages.add(email);
                        }
                    }
                    else
                        DailyAppLogger.INFO_LOGGER.info("repeated checksum");
                } catch (Exception e) {
                    DailyAppLogger.ERROR_LOGGER.error("[[Service Class Notifications Exception]] ==> {}", e.getMessage());
                }
            }
        }
        notificationsManager.cacheServiceClasses.clear();
        notificationsManager.cacheServiceClasses.putAll(cacheServiceClasses);
    }

    private NotificationModel setNotification(Integer serviceClassKey, String serviceClassName) {
        StringBuilder checkSumInput = new StringBuilder();
        checkSumInput.append(serviceClassKey).append(Defines.MODELS.SERVICE_CLASS_TYPE);
        String checkSum = getChecksumCRC32(checkSumInput.toString().getBytes());
        DailyAppLogger.INFO_LOGGER.info("----------> SC checksum = {}", checkSum);

        String details = Defines.MODELS.generateDetails(DatabaseStructs.MODEL_TYPE.SERVICE_CLASS, serviceClassKey.toString(),
                serviceClassName).toString();

        NotificationModel notification = new NotificationModel(Defines.MODELS.SERVICE_CLASS_HEADER, details,
                Defines.MODELS.SERVICE_CLASS_TYPE, 1, checkSum, serviceClassKey, serviceClassName);
        return notification;
    }

    private EmailMessage setEmailMessage(Integer serviceClassKey, String serviceClassName) {
        String details = Defines.MODELS.generateDetails(DatabaseStructs.MODEL_TYPE.SERVICE_CLASS, serviceClassKey.toString(),
                serviceClassName).toString();

        EmailMessage email = new EmailMessage("test msg A1");
        email.setSubject(Defines.MODELS.SERVICE_CLASS_HEADER);
        email.setPlainText(details);
        email.setSenderAddress(properties.getEmailSenderAddress());
        email.setReceivers(splitReceivers(properties.getEmailReceivers()));
        return email;
    }

    private String[] splitReceivers(String receiversCsv) {
        String[] receivers = receiversCsv.split(",");
        return receivers;
    }

    private static String getChecksumCRC32(byte[] bytes) {
        Checksum crc32 = new CRC32();
        crc32.update(bytes, 0, bytes.length);
        return String.valueOf(crc32.getValue());
    }
}
