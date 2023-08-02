package com.asset.loggingService.managers;


import com.asset.loggingService.dao.QueriesCacheDAOImp;
import com.asset.loggingService.defines.Defines;
import com.asset.loggingService.defines.ErrorCodes;
import com.asset.loggingService.exceptions.LoggingServiceException;
import com.asset.loggingService.loggers.DailyAppLogger;
import com.asset.loggingService.model.TransactionActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class CacheManager {
    public static HashMap<Integer, String> allQueries;//cac

    public static HashMap<Integer, TransactionActionModel> GetTransactionActionCache; //todo mod

    @Autowired
    private QueriesCacheDAOImp queriesCacheDAOImp;


    @EventListener
    private void readeAllQueries(ApplicationReadyEvent event) throws LoggingServiceException {
        try {
            DailyAppLogger.DEBUG_LOGGER.debug("Start retrieving all Queries from database  ");
            allQueries = queriesCacheDAOImp.getAll();
            DailyAppLogger.DEBUG_LOGGER.debug("#Retrieved Queries =  " + allQueries.size());

            if (allQueries == null || allQueries.isEmpty()) {
                DailyAppLogger.DEBUG_LOGGER.error("No queries found");
                DailyAppLogger.DEBUG_LOGGER.debug("No queries found");
                throw new LoggingServiceException(ErrorCodes.ERROR.QUERIES_NOT_FOUND, Defines.SEVERITY.ERROR);
            }
            getTransactionAction();
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.debug("error while execute " + ex);
            DailyAppLogger.ERROR_LOGGER.error("error while execute " + ex);
            ex.printStackTrace();
            throw new LoggingServiceException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());

        }


    }


    public void getTransactionAction() throws LoggingServiceException {
        DailyAppLogger.DEBUG_LOGGER.debug("Start retrieving TransactionAction from database");
        GetTransactionActionCache = queriesCacheDAOImp.getAllTransactionsActionCache();
        if (GetTransactionActionCache == null || GetTransactionActionCache.isEmpty()) {
            DailyAppLogger.DEBUG_LOGGER.error("No TransactionAction found");
            throw new LoggingServiceException(ErrorCodes.ERROR.QUERIES_NOT_FOUND, Defines.SEVERITY.ERROR);
        }
    }


}