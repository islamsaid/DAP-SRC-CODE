package com.asset.loggingService.dao;

import com.asset.loggingService.constants.Queries;
import com.asset.loggingService.dao.extractors.GetAllLogsByNameExtractor;
import com.asset.loggingService.defines.Defines;
import com.asset.loggingService.defines.ErrorCodes;
import com.asset.loggingService.exceptions.LoggingServiceException;
import com.asset.loggingService.loggers.DailyAppLogger;
import com.asset.loggingService.managers.CacheManager;
import com.asset.loggingService.model.GetLogsRequest;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import com.asset.loggingService.model.TransactionUserDetails;
import com.asset.loggingService.model.TrxUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

/**
 * @author islam.said
 */
@Component
public class LoggingDao {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private GetAllLogsByNameExtractor getAllLogsByNameExtractor;
    @Autowired
    public LoggingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void insertLogs(TrxUserDto LogList) {
        int sqlId = CacheManager.GetTransactionActionCache.get(LogList.getActionId()).getSqlId();
        String query =CacheManager.allQueries.get(sqlId);
        DailyAppLogger.DEBUG_LOGGER.debug(" query :  "+query);
        String insertTransactionsUserLogs = CacheManager.allQueries.get(Queries.TRANSACTION_LOGGING.id);
        DailyAppLogger.DEBUG_LOGGER.debug("insert Transactions User Logs : "+insertTransactionsUserLogs);
        DailyAppLogger.DEBUG_LOGGER.debug("insert   logs in database ");
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((Connection connection) -> {
                PreparedStatement ps = connection.prepareStatement(
                        insertTransactionsUserLogs
                        , new String[]{"id"});
                ps.setString(1, LogList.getPageName());
                ps.setInt(2, LogList.getUserId());
                ps.setString(3, LogList.getSessionId());
                ps.setString(4, LogList.getRequestId());
                ps.setString(5, LogList.getRequestBody());
                ps.setString(6, LogList.getResponseBody());
                ps.setString(7, String.valueOf(CacheManager.GetTransactionActionCache.get(LogList.getActionId()).getActionId()));
                ps.setString(8, query);
                ps.setString(9, LogList.getUserName());
                ps.setString(10, LogList.getObjectIdentifier());


                //todo sql name

                return ps;
            }, keyHolder);
            Integer trxId = keyHolder.getKey() == null ? null : keyHolder.getKey().intValue();
            InsertInTransactionUserDetails(trxId, LogList.getTransactionUserDetails());
        } catch (Exception e) {
            DailyAppLogger.DEBUG_LOGGER.debug("error while execute " + insertTransactionsUserLogs, e);
            DailyAppLogger.ERROR_LOGGER.error("error while execute " + insertTransactionsUserLogs);
            throw new LoggingServiceException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }

    }


    public void InsertInTransactionUserDetails(int trxId, List<TransactionUserDetails> transactionUserDetails) {
        String insertTransactionsUserDetailsLogs = CacheManager.allQueries.get(Queries.SET_TX_USER_ACTIONS_DETAILS.id);
        DailyAppLogger.DEBUG_LOGGER.debug("insert Transactions User Details Logs:  " + insertTransactionsUserDetailsLogs);
        jdbcTemplate.batchUpdate(insertTransactionsUserDetailsLogs, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i)   {
                try {
                    TransactionUserDetails data = transactionUserDetails.get(i);
                    ps.setInt(1, trxId);
                    ps.setString(2, data.getParamName());
                    ps.setString(3, data.getOldVal());
                    ps.setString(4, data.getNewVal());
                    ps.setDate(5, Date.valueOf(LocalDate.now()));
                } catch (Exception e) {
                    DailyAppLogger.DEBUG_LOGGER.error("error while execute " + insertTransactionsUserDetailsLogs, e);
                    DailyAppLogger.ERROR_LOGGER.error("error while execute " + insertTransactionsUserDetailsLogs);
                    throw new LoggingServiceException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
                }
            }
            @Override
            public int getBatchSize() {
                return transactionUserDetails.size();
            }
        });
    }

    public List<TrxUserDto> getLogs(GetLogsRequest getLogsRequest) {
        String getLogs = CacheManager.allQueries.get(Queries.GET_TRANSACTION_LOGGING.id);
        DailyAppLogger.DEBUG_LOGGER.debug("get logs from db : "+getLogs);
        try {
            return  jdbcTemplate.query(getLogs, getAllLogsByNameExtractor,getLogsRequest.getDate(),
                    getLogsRequest.getDate(), getLogsRequest.getUserId() );
        } catch (Exception e) {
            DailyAppLogger.DEBUG_LOGGER.debug("error while execute " + getLogs, e);
            DailyAppLogger.ERROR_LOGGER.error("error while execute " + getLogs);
            throw new LoggingServiceException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }
}
