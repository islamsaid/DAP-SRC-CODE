package com.asset.dailyappnotificationservice.manager;

import com.asset.dailyappnotificationservice.cache.PropertiesCache;
import com.asset.dailyappnotificationservice.defines.ErrorCodes;
import com.asset.dailyappnotificationservice.exceptions.NotificationsException;
import com.asset.dailyappnotificationservice.logger.DailyAppLogger;
import com.asset.dailyappnotificationservice.tasks.PriceGroupNotificationsTask;
import com.asset.dailyappnotificationservice.tasks.RatePlanNotificationsTask;
import com.asset.dailyappnotificationservice.tasks.ServiceClassNotificationsTask;
import com.asset.dailyappnotificationservice.tasks.TariffModelNotificationsTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationsManager<T extends Runnable>
{
    public static ConcurrentHashMap<Integer, String> queries = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer, String> cacheServiceClasses = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer, String> cacheTariffModels = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer, String> cachePriceGroups = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer, String> cacheRatePlans = new ConcurrentHashMap<>();

    private ScheduledExecutorService notificationExecutorService;

    @Autowired
    private PropertiesCache propertiesCache;

    public void init() {
        notificationExecutorService = Executors.newScheduledThreadPool(propertiesCache.getCorePoolSize());
    }

    public void submitTask(T notificationTask) throws NotificationsException{
        try {
            DailyAppLogger.INFO_LOGGER.info("Start submitting Task");
            notificationExecutorService.scheduleAtFixedRate(notificationTask, 0, propertiesCache.getRefreshRate(), TimeUnit.MILLISECONDS);
            DailyAppLogger.INFO_LOGGER.info("End submitting Task");
        } catch (RejectedExecutionException exception) {
            DailyAppLogger.DEBUG_LOGGER.error("Full Queue scheduler exception");
            DailyAppLogger.ERROR_LOGGER.error("Full Queue scheduler exception");
            throw new NotificationsException(ErrorCodes.ERROR.FULL_QUEUE);
        }
    }
}
