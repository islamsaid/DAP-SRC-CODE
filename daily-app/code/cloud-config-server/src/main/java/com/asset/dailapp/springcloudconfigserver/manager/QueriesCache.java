package com.asset.dailapp.springcloudconfigserver.manager;


import com.asset.dailapp.springcloudconfigserver.database.dao.QueriesCacheDAOImp;
import com.asset.dailapp.springcloudconfigserver.defines.Defines;
import com.asset.dailapp.springcloudconfigserver.defines.ErrorCodes;
import com.asset.dailapp.springcloudconfigserver.exception.ConfigServerException;
import com.asset.dailapp.springcloudconfigserver.logger.DailyAppLogger;
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
                DailyAppLogger.DEBUG_LOGGER.error("No queries found");
                throw new ConfigServerException(ErrorCodes.ERROR.QUERIES_NOT_FOUND, Defines.SEVERITY.ERROR);
            }
        } catch (Exception ex){
            DailyAppLogger.DEBUG_LOGGER.error("error while retrieving queries : " + ex);
            DailyAppLogger.ERROR_LOGGER.error("error while retrieving queries : " +  ex);
            ex.printStackTrace();
            throw new ConfigServerException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());

        }

    }
}