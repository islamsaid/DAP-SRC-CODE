package com.asset.dailyappnotificationservice.cache;

import com.asset.dailyappnotificationservice.dao.QueriesDao;
import com.asset.dailyappnotificationservice.defines.Defines;
import com.asset.dailyappnotificationservice.defines.ErrorCodes;
import com.asset.dailyappnotificationservice.exceptions.NotificationsException;
import com.asset.dailyappnotificationservice.logger.DailyAppLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class QueriesCache {

    @Autowired
    private QueriesDao queriesDao;

    public ConcurrentHashMap<Integer, String> readeAllQueries() throws IOException {
        DailyAppLogger.DEBUG_LOGGER.debug(">>>>>> Start Caching All Queries...");
        ConcurrentHashMap<Integer, String> allQueries = queriesDao.getAll();
        if (allQueries.isEmpty() || allQueries == null) {
            throw new NotificationsException(ErrorCodes.ERROR.QUERIES_NOT_FOUND, Defines.SEVERITY.FATAL);
        }
        DailyAppLogger.DEBUG_LOGGER.debug(">>>>>>>>>>>>>>>>>QUERIES ARE LOADED>>>>>>>>>>>>>>>>>");
        return allQueries;
    }
}
