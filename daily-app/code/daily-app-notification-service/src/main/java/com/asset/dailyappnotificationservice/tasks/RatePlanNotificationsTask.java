package com.asset.dailyappnotificationservice.tasks;

import com.asset.dailyappnotificationservice.cache.PropertiesCache;
import com.asset.dailyappnotificationservice.defines.DatabaseStructs;
import com.asset.dailyappnotificationservice.defines.Defines;
import com.asset.dailyappnotificationservice.executor.LogNotificationExecutor;
import com.asset.dailyappnotificationservice.logger.DailyAppLogger;
import com.asset.dailyappnotificationservice.manager.NotificationsManager;
import com.asset.dailyappnotificationservice.models.EmailQueue;
import com.asset.dailyappnotificationservice.models.NotificationModel;
import com.asset.dailyappnotificationservice.services.NotificationsService;
import com.asset.emailbroker.integration.model.EmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

@Component
public class RatePlanNotificationsTask implements Runnable
{
    @Autowired
    private NotificationsService notificationsService;
    private ConcurrentHashMap<Integer, String> cacheRatePlans;
    private final NotificationsManager notificationsManager;
    private LogNotificationExecutor loggerNotification;
    private final EmailQueue emailQueue;

    @Autowired
    private PropertiesCache properties;

    @Autowired
    public RatePlanNotificationsTask(NotificationsManager notificationsManager, LogNotificationExecutor loggerNotification, EmailQueue emailQueue) {
        this.notificationsManager = notificationsManager;
        this.loggerNotification = loggerNotification;
        this.emailQueue = emailQueue;
    }

    @Override
    public void run() {
        cacheRatePlans = notificationsService.loadRatePlansKeys();
        for (Map.Entry<Integer,String> entry : cacheRatePlans.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();
            if (!notificationsManager.cacheRatePlans.containsKey(key)) {
                try {
                    notificationsManager.cacheRatePlans.put(key, value);
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
                    e.printStackTrace();
                }
            }
        }
        notificationsManager.cacheRatePlans.clear();
        notificationsManager.cacheRatePlans.putAll(cacheRatePlans);
    }

    private NotificationModel setNotification(Integer ratePlanKey, String ratePlanName)
    {
        StringBuilder checkSumInput = new StringBuilder();
        checkSumInput.append(ratePlanKey).append(Defines.MODELS.RATE_PLAN_TYPE);
        String checkSum = getChecksumCRC32(checkSumInput.toString().getBytes());
        DailyAppLogger.INFO_LOGGER.info("----------> RP checksum = {}", checkSum);

        String details = Defines.MODELS.generateDetails(DatabaseStructs.MODEL_TYPE.RATE_PLAN,
                ratePlanKey.toString(), ratePlanName).toString();

        NotificationModel notification = new NotificationModel(Defines.MODELS.RATE_PLAN_HEADER, details,
                Defines.MODELS.RATE_PLAN_TYPE, 1, checkSum, ratePlanKey, ratePlanName);
        return notification;
    }

    private static String getChecksumCRC32(byte[] bytes) {
        Checksum crc32 = new CRC32();
        crc32.update(bytes, 0, bytes.length);
        return String.valueOf(crc32.getValue());
    }

    private EmailMessage setEmailMessage(Integer ratePlanKey, String ratePlanName){
        String details = Defines.MODELS.generateDetails(DatabaseStructs.MODEL_TYPE.RATE_PLAN,
                ratePlanKey.toString(), ratePlanName).toString();

        EmailMessage email = new EmailMessage("test msg A1");
        email.setSubject(Defines.MODELS.RATE_PLAN_HEADER);
        email.setPlainText(details);
        email.setSenderAddress(properties.getEmailSenderAddress());
        email.setReceivers(getReceivers(properties.getEmailReceivers()));
        return email;
    }

    private String[] getReceivers(String receiversCsv){
        String[] receivers = receiversCsv.split(",");
        return receivers;
    }
}
