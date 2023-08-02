package com.asset.dailyappnotificationservice.dao;

import com.asset.dailyappnotificationservice.dao.extractors.AllQueriesExtractor;
import com.asset.dailyappnotificationservice.defines.DatabaseStructs;
import com.asset.dailyappnotificationservice.defines.Defines;
import com.asset.dailyappnotificationservice.defines.ErrorCodes;
import com.asset.dailyappnotificationservice.exceptions.NotificationsException;
import com.asset.dailyappnotificationservice.logger.DailyAppLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class QueriesDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private StringBuilder sqlQuery;

    public ConcurrentHashMap<Integer, String> getAll(){
        try {
            sqlQuery = new StringBuilder();
            DailyAppLogger.DEBUG_LOGGER.debug(" Start Retrieving All Queries From the Database...");
            sqlQuery.append("select ").append(DatabaseStructs.DAILY_QUERIES.ID).append(", ")
                    .append(DatabaseStructs.DAILY_QUERIES.SQL).append(" from ")
                    .append(DatabaseStructs.DAILY_QUERIES.TABLE_NAME);
            DailyAppLogger.DEBUG_LOGGER.debug("embedded query = {}", sqlQuery);
            return jdbcTemplate.query(sqlQuery.toString(), new AllQueriesExtractor());
        }catch (Exception ex){
            DailyAppLogger.ERROR_LOGGER.error(">>>>>> Queries RETRIEVING Error ", ex);
            DailyAppLogger.DEBUG_LOGGER.error(">>>>>> Queries RETRIEVING Error ", ex);
            ex.printStackTrace();
            throw new NotificationsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR);
        }
    }
}
