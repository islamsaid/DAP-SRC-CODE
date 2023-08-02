package com.asset.loggingService.dao;

import com.asset.loggingService.dao.extractors.AllQueriesExtractor;
import com.asset.loggingService.dao.extractors.AllTransactionsActionExtractor;
import com.asset.loggingService.defines.DatabaseStructs;
import com.asset.loggingService.defines.Defines;
import com.asset.loggingService.defines.ErrorCodes;
import com.asset.loggingService.exceptions.LoggingServiceException;
import com.asset.loggingService.loggers.DailyAppLogger;
import com.asset.loggingService.model.TransactionActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class QueriesCacheDAOImp {
    String getALlQueries;
    String getALlTransactionActions;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AllQueriesExtractor allQueriesExtractor;
    @Autowired
    private AllTransactionsActionExtractor allTransactionsActionExtractor;

    public HashMap<Integer, String> getAll() throws LoggingServiceException {
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
            DailyAppLogger.DEBUG_LOGGER.error("error getting all queries by embedded query " + getALlQueries, ex);
            DailyAppLogger.ERROR_LOGGER.error("error getting all queries by embedded query " + getALlQueries, ex);
            ex.printStackTrace();
            throw new LoggingServiceException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public HashMap<Integer, TransactionActionModel> getAllTransactionsActionCache() {
        try {
            if (getALlTransactionActions == null) {
                StringBuilder query = new StringBuilder();
                query.append("SELECT * ")
                        .append("  FROM ")
                        .append(DatabaseStructs.TX_ACTIONS.TABLE_NAME);
                getALlTransactionActions = query.toString();
            }
            DailyAppLogger.DEBUG_LOGGER.debug("Embedded query --> " + getALlTransactionActions);
            return jdbcTemplate.query(getALlTransactionActions, allTransactionsActionExtractor);
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error while execute " + getALlTransactionActions, ex);
            DailyAppLogger.ERROR_LOGGER.error("error while execute " + getALlTransactionActions, ex);
            ex.printStackTrace();
            throw new LoggingServiceException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }
}
