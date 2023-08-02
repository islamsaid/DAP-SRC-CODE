package com.asset.dailyappnotificationservice.dao.extractors;

import com.asset.dailyappnotificationservice.defines.DatabaseStructs;
import com.asset.dailyappnotificationservice.defines.Defines;
import com.asset.dailyappnotificationservice.defines.ErrorCodes;
import com.asset.dailyappnotificationservice.exceptions.NotificationsException;
import com.asset.dailyappnotificationservice.logger.DailyAppLogger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AllQueriesExtractor implements ResultSetExtractor<ConcurrentHashMap<Integer, String>> {

    @Override
    public ConcurrentHashMap<Integer, String> extractData(ResultSet resultSet) throws SQLException, DataAccessException {

        DailyAppLogger.DEBUG_LOGGER.debug(">>>>>> Start Extracting Queries From the Database...");
        ConcurrentHashMap<Integer, String> Queries = new ConcurrentHashMap<>();
        while (resultSet.next()) {
            Queries.put(resultSet.getInt(DatabaseStructs.DAILY_QUERIES.ID),
                    resultSet.getString(DatabaseStructs.DAILY_QUERIES.SQL));
        }
        DailyAppLogger.INFO_LOGGER.debug("Number of Loaded Queries = {}", Queries.size());
        return Queries;

    }
}
