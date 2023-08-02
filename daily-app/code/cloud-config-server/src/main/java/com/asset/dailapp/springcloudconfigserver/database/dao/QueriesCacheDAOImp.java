package com.asset.dailapp.springcloudconfigserver.database.dao;

import com.asset.dailapp.springcloudconfigserver.database.extractors.AllQueriesExtractor;
import com.asset.dailapp.springcloudconfigserver.defines.DatabaseStructs;
import com.asset.dailapp.springcloudconfigserver.defines.Defines;
import com.asset.dailapp.springcloudconfigserver.defines.ErrorCodes;
import com.asset.dailapp.springcloudconfigserver.exception.ConfigServerException;
import com.asset.dailapp.springcloudconfigserver.logger.DailyAppLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class QueriesCacheDAOImp {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    String getALlQueries;
    @Autowired
    private AllQueriesExtractor allQueriesExtractor;


    public HashMap<Integer, String> getAll() throws ConfigServerException {
        try {
            if (getALlQueries == null) {
                StringBuilder query = new StringBuilder();
                query.append("SELECT ")
                        .append(DatabaseStructs.DAILY_QUERIES.ID).append(" , ")
                        .append(DatabaseStructs.DAILY_QUERIES.SQL)
                        .append("  FROM ")
                        .append(DatabaseStructs.DAILY_QUERIES.TABLE_NAME);
                getALlQueries = query.toString();
            }

            return jdbcTemplate.query(getALlQueries, allQueriesExtractor);
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error while execute " + getALlQueries);
            DailyAppLogger.ERROR_LOGGER.error("error while execute " + getALlQueries, ex);
            throw new ConfigServerException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }
}
