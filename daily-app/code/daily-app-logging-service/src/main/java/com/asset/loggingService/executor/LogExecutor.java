    package com.asset.loggingService.executor;

import com.asset.concurrent.executor.EagerBatchExecutor;
import com.asset.loggingService.cache.PropertiesCache;
import com.asset.loggingService.loggers.DailyAppLogger;
import com.asset.loggingService.model.TransactionUserDetails;
import com.asset.loggingService.model.TrxUserDto;
import com.asset.loggingService.model.TrxUserRequest;
import com.asset.loggingService.security.JwtTokenUtil;
import com.asset.loggingService.service.LoggingService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

import com.asset.loggingService.service.TransactionAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author islam.said
 */
@Component
public class LogExecutor extends EagerBatchExecutor<TrxUserRequest, TrxUserDto> {
    @Autowired
    TransactionAdapter transactionAdapter;
    private final LoggingService loggingService;
    private final PropertiesCache propertiesCache;
    @Autowired
    public LogExecutor(LoggingService loggingService, PropertiesCache propertiesCache) {
        super(propertiesCache.getRollingPoolProperties().getMaxBatchSize(),
                new ThreadPoolExecutor(propertiesCache.getRollingPoolProperties().getCorePoolSize(),
                        propertiesCache.getRollingPoolProperties().getMaximumPoolSize(),
                        propertiesCache.getRollingPoolProperties().getKeepAliveTime(),
                        propertiesCache.getRollingPoolProperties().getUnit(),
                        new ArrayBlockingQueue<>(propertiesCache.getRollingPoolProperties().getMaxQueueSize())));
        this.loggingService = loggingService;
        this.propertiesCache = propertiesCache;
    }

    public void prepareExecute(TrxUserRequest trxUserRequest) {
            try {
                DailyAppLogger.DEBUG_LOGGER.debug("add  logs request has been received request and now in prepare execute:  " + trxUserRequest);
                execute(trxUserRequest);
            } catch (Exception e) {
                DailyAppLogger.DEBUG_LOGGER.debug("error while execute execute batch " + e);
                DailyAppLogger.ERROR_LOGGER.error("error while execute execute batch ");

            }
        }


    @Override
    protected void consumeBatchList(ArrayList<TrxUserDto> batchList) {
        int count = 0;
        boolean retry = true;
        DailyAppLogger.DEBUG_LOGGER.debug("start insert batch in db");
        while (retry) {
            try {
                loggingService.insertLogs(batchList);
                retry = false;
            } catch (Exception ex) {
                if (++count >= propertiesCache.getMaxNumberOfRetries()) {
                    DailyAppLogger.INFO_LOGGER.debug("The Batch List failed to be insert", ex);
                    DailyAppLogger.INFO_LOGGER.error("The Batch List failed to be insert");
                    retry = false;
                }
            }
        }
    }

    @Override
    protected TrxUserDto processBeforeAdding(TrxUserRequest payload) {
        DailyAppLogger.DEBUG_LOGGER.debug("start for preparing object after pass it to Eager BatchExecutor ");
        return transactionAdapter.getTransactionUserDao(payload);
    }

    public void shutdown() {
        try {
            DailyAppLogger.INFO_LOGGER.info("::SmsLogExecutor Shutting Down::");
            shutdownExecutor();
            awaitExecutorTermination();
            awaitBatchTermination();
            DailyAppLogger.INFO_LOGGER.info("::SmsLogExecutor Shutdown Successful::");
        } catch (Exception ex) {
            DailyAppLogger.INFO_LOGGER.error("Exception Occurred While Terminating SmsLogExecutor", ex);
        }
    }
}
