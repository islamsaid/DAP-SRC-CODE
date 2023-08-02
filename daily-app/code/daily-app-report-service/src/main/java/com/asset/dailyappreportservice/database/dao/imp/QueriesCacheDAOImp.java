package com.asset.dailyappreportservice.database.dao.imp;

import com.asset.dailyappreportservice.database.extractors.AllQueriesExtractor;
import com.asset.dailyappreportservice.defines.DatabaseStructs;
import com.asset.dailyappreportservice.defines.Defines;
import com.asset.dailyappreportservice.defines.ErrorCodes;
import com.asset.dailyappreportservice.exception.ReportsException;
import com.asset.dailyappreportservice.logger.DailyAppLogger;
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


    public HashMap<Integer, String> getAll()throws ReportsException {
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
            ex.printStackTrace();
            throw new ReportsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }
}
