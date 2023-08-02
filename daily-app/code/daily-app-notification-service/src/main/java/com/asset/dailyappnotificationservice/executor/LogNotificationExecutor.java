package com.asset.dailyappnotificationservice.executor;

import com.asset.concurrent.executor.EagerBatchExecutor;
import com.asset.dailyappnotificationservice.cache.PropertiesCache;
import com.asset.dailyappnotificationservice.logger.DailyAppLogger;
import com.asset.dailyappnotificationservice.models.NotificationModel;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import com.asset.dailyappnotificationservice.services.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author islam.said
 */
@Component
public class LogNotificationExecutor extends EagerBatchExecutor <NotificationModel,NotificationModel>
{
    @Autowired
    private NotificationsService notificationsService;
    private final PropertiesCache propertiesCache;

    @Autowired
    public LogNotificationExecutor(PropertiesCache propertiesCache) {
          super(propertiesCache.getRollingPoolProperties().getMaxBatchSize(),
                new ThreadPoolExecutor(propertiesCache.getRollingPoolProperties().getCorePoolSize(),
                        propertiesCache.getRollingPoolProperties().getMaximumPoolSize(),
                        propertiesCache.getRollingPoolProperties().getKeepAliveTime(),
                        propertiesCache.getRollingPoolProperties().getUnit(),
                        new ArrayBlockingQueue<>(propertiesCache.getRollingPoolProperties().getMaxQueueSize())));
          this.propertiesCache = propertiesCache;
    }   
    
    @Override
    protected void consumeBatchList(ArrayList<NotificationModel> batchList)
    {
        int count = 0;
        boolean retry = true;
        while (retry) {
            try {
            //    loggingSMSFacade.insertSmsLogs(batchList);
                notificationsService.insertNotificationsBatch(batchList);
                retry = false;
            } catch (Exception ex) {
                if (++count >= propertiesCache.getMaxNumberOfRetries()) {
                    DailyAppLogger.ERROR_LOGGER.error("The Batch List failed to be insert", ex);
                    retry = false;
                }
            }
        }
    }
    
    

    @Override
    protected NotificationModel processBeforeAdding(NotificationModel payload) {
        return payload;
    }
    


}
