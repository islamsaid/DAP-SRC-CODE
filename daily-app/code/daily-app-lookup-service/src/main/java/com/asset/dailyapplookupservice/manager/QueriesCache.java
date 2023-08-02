package com.asset.dailyapplookupservice.manager;

import com.asset.dailyapplookupservice.database.dao.imp.QueriesCacheDAOImp;
import com.asset.dailyapplookupservice.defines.Defines;
import com.asset.dailyapplookupservice.defines.ErrorCodes;
import com.asset.dailyapplookupservice.exception.LookupException;
import com.asset.dailyapplookupservice.logger.DailyAppLogger;
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
            if(allQueries== null || allQueries.isEmpty()){
                DailyAppLogger.DEBUG_LOGGER.error("No Queries found");
                throw new LookupException(ErrorCodes.ERROR.QUERIES_NOT_FOUND, Defines.SEVERITY.ERROR);
            }
        } catch (Exception ex){
            DailyAppLogger.DEBUG_LOGGER.error("error while execute " + ex);
            DailyAppLogger.ERROR_LOGGER.error("error while execute " +  ex);
        throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());

        }

    }
}