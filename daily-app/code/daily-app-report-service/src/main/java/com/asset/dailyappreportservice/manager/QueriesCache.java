package com.asset.dailyappreportservice.manager;

import com.asset.dailyappreportservice.database.dao.imp.QueriesCacheDAOImp;
import com.asset.dailyappreportservice.defines.Defines;
import com.asset.dailyappreportservice.defines.ErrorCodes;
import com.asset.dailyappreportservice.exception.ReportsException;
import com.asset.dailyappreportservice.logger.DailyAppLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
public class QueriesCache {
    public  static  HashMap<Integer, String>allQueries ;//cac
    @Autowired
    private QueriesCacheDAOImp queriesCacheDAOImp;


    @EventListener
    private void readeAllQueries(ApplicationReadyEvent event) throws IOException {
        try {
            DailyAppLogger.DEBUG_LOGGER.debug("Start retrieving all Queries from database  ");
            allQueries = queriesCacheDAOImp.getAll();
            DailyAppLogger.DEBUG_LOGGER.debug("#Q = {} Queries", allQueries.size());

            if(allQueries== null || allQueries.isEmpty()){
                DailyAppLogger.DEBUG_LOGGER.error("No queries found");
                throw new ReportsException(ErrorCodes.ERROR.QUERIES_NOT_FOUND, Defines.SEVERITY.ERROR);
            }
        } catch (Exception ex){
            DailyAppLogger.DEBUG_LOGGER.error("error while retrieving queries -> {}",  ex);
            DailyAppLogger.ERROR_LOGGER.error("error while retrieving queries -> {}",  ex);
            ex.printStackTrace();
            throw new ReportsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }

    }
}