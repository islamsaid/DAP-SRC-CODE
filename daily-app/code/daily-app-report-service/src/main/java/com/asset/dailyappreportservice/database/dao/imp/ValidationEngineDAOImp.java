package com.asset.dailyappreportservice.database.dao.imp;

import com.asset.dailyappreportservice.constants.Queries;
import com.asset.dailyappreportservice.database.extractors.RetrieveAggregationDataExtractor;
import com.asset.dailyappreportservice.defines.Defines;
import com.asset.dailyappreportservice.defines.ErrorCodes;
import com.asset.dailyappreportservice.exception.ReportsException;
import com.asset.dailyappreportservice.logger.DailyAppLogger;
import com.asset.dailyappreportservice.manager.QueriesCache;
import com.asset.dailyappreportservice.model.request.EpochDateRequest;
import com.asset.dailyappreportservice.model.response.RetrieveAggregationDataResponse;
import com.asset.dailyappreportservice.model.validationEngine.AggregationRequest;
import com.asset.dailyappreportservice.model.validationEngine.GetAllBalances;
import com.asset.dailyappreportservice.model.validationEngine.TransactionModel;
import com.asset.dailyappreportservice.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidationEngineDAOImp {
    private final BeanPropertyRowMapper<GetAllBalances> getAllBalancesBean = new BeanPropertyRowMapper<>(GetAllBalances.class);
    private final BeanPropertyRowMapper<TransactionModel> transactionModel = new BeanPropertyRowMapper<>(TransactionModel.class);


    @Autowired
    private RetrieveAggregationDataExtractor retrieveAggregationDataExtractor;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Autowired
    private Utils utils;

    public GetAllBalances RetrieveBalances(EpochDateRequest validationEngineRequest) {
        String retrieveAllBalances = QueriesCache.allQueries.get(Queries.RERTRIEVE_balances.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Query#{} = {}", Queries.RERTRIEVE_balances.id, retrieveAllBalances);
        try {
            String date = utils.convertEpochToDate(validationEngineRequest.getDate());
            return jdbcTemplate.queryForObject(retrieveAllBalances, getAllBalancesBean, date, date);
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error(" error while executing  " + retrieveAllBalances, ex);
            DailyAppLogger.ERROR_LOGGER.error(" error while executing Query#{} = {} -> {}",
                    Queries.RERTRIEVE_balances.id, retrieveAllBalances, ex);
            ex.printStackTrace();
            throw new ReportsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public List<RetrieveAggregationDataResponse> RetrieveAggregationData(EpochDateRequest validationEngineRequest) {
        String retrieveAllBalances = QueriesCache.allQueries.get(Queries.RERTRIEVE_aggregation_data.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Query#{} = {}", Queries.RERTRIEVE_aggregation_data.id, retrieveAllBalances);
        try {
            String date = utils.convertEpochToDate(validationEngineRequest.getDate());
            return jdbcTemplate.query(retrieveAllBalances, retrieveAggregationDataExtractor, date, date, date);
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error(" error while executing  " + retrieveAllBalances, ex);
            DailyAppLogger.ERROR_LOGGER.error(" error while executing Query#{} = {} -> {}",
                    Queries.RERTRIEVE_aggregation_data.id, retrieveAllBalances, ex);
            ex.printStackTrace();
            throw new ReportsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public List<TransactionModel> GetTransactionType() {
        String retrieveAllTransactionType = QueriesCache.allQueries.get(Queries.RERTRIEVE_transaction_types_lookups.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Query#{} = {}", Queries.RERTRIEVE_transaction_types_lookups.id, retrieveAllTransactionType);
        try {
            return jdbcTemplate.query(retrieveAllTransactionType, transactionModel);
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error(" error while execute  " + retrieveAllTransactionType);
            DailyAppLogger.ERROR_LOGGER.error(" error while executing Query#{} = {} -> {}",
                    Queries.RERTRIEVE_transaction_types_lookups.id, retrieveAllTransactionType, ex);
            ex.printStackTrace();
            throw new ReportsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }


    public void updateValidEngineAggregation(AggregationRequest aggregationRequest) {
        String updateValidEngineAggregation = QueriesCache.allQueries.get(Queries.UPDATE_VALID_ENGINE_AGGREGATION.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Query#{} = {}", Queries.UPDATE_VALID_ENGINE_AGGREGATION.id, updateValidEngineAggregation);

        try {
            String date = utils.getCurrentDate();
            if (aggregationRequest.getInAdjustFlag()) {
                int diffCode = 1;
                DailyAppLogger.DEBUG_LOGGER.debug("Aggregation-Request {} , date={}, diffCode={}",
                        aggregationRequest, date, diffCode);
                jdbcTemplate.update(updateValidEngineAggregation,
                        aggregationRequest.getInSubTransactionTypeKey(),
                        date,
                        aggregationRequest.getRatePlanKey()
                        , aggregationRequest.getDayDateKey(),
                        diffCode,
                        aggregationRequest.getDwhStatusKey()
                );
            }
            if (aggregationRequest.getOutAdjustFlag()) {
                int diffCode = 2;
                DailyAppLogger.DEBUG_LOGGER.debug("Aggregation-Request {} , date={}, diffCode={}",
                        aggregationRequest, date, diffCode);
                jdbcTemplate.update(updateValidEngineAggregation,
                        aggregationRequest.getOutSubTransactionTypeKey(),
                        date,
                        aggregationRequest.getRatePlanKey()
                        , aggregationRequest.getDayDateKey(),
                        diffCode,
                        aggregationRequest.getDwhStatusKey()
                );
            }
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error(" error while execute  " + updateValidEngineAggregation);
            DailyAppLogger.ERROR_LOGGER.error(" error while executing Query#{} = {} -> {}",
                    Queries.UPDATE_VALID_ENGINE_AGGREGATION.id, updateValidEngineAggregation, ex);
            ex.printStackTrace();
            throw new ReportsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }

    }

    public void updateValidationEngine(AggregationRequest aggregationRequest) {
        String updateValidationEngine = QueriesCache.allQueries.get(Queries.UPDATE_VALIDATION_ENGINE.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Query#{} = {}", Queries.UPDATE_VALIDATION_ENGINE.id, updateValidationEngine);

        try {
            String date = utils.getCurrentDate();
            if (aggregationRequest.getInAdjustFlag()) {
                int diffCode = 1;
                DailyAppLogger.DEBUG_LOGGER.debug("Aggregation-Request {} , date={}, diffCode={}",
                        aggregationRequest, date, diffCode);
                jdbcTemplate.update(updateValidationEngine,
                        aggregationRequest.getInSubTransactionTypeKey(), date, aggregationRequest.getRatePlanKey()
                        , aggregationRequest.getDayDateKey(), diffCode, aggregationRequest.getDwhStatusKey());
            }
            if (aggregationRequest.getOutAdjustFlag()) {
                int diffCode = 2;
                DailyAppLogger.DEBUG_LOGGER.debug("Aggregation-Request {} , date={}, diffCode={}",
                        aggregationRequest, date, diffCode);
                jdbcTemplate.update(updateValidationEngine,
                        aggregationRequest.getOutSubTransactionTypeKey(), date, aggregationRequest.getRatePlanKey()
                        , aggregationRequest.getDayDateKey(), diffCode, aggregationRequest.getDwhStatusKey());
            }

        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error(" error while execute  " + updateValidationEngine);
            DailyAppLogger.ERROR_LOGGER.error(" error while executing Query#{} = {} -> {}",
                    Queries.UPDATE_VALIDATION_ENGINE.id, updateValidationEngine, ex);
            ex.printStackTrace();
            throw new ReportsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }

    }

    public int insertDailyDataAggregation(AggregationRequest aggregationRequest) {
        String insertDailyDataAggregation = QueriesCache.allQueries.get(Queries.INSERT_DAILY_DAILY_DATA_AGGREGATION.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Query#{} = {}", Queries.INSERT_DAILY_DAILY_DATA_AGGREGATION.id, insertDailyDataAggregation);

        try { //todo date
            String date = utils.getCurrentDate();
            DailyAppLogger.DEBUG_LOGGER.debug("Aggregation-Request {} , date={}",
                    aggregationRequest, date);
            return jdbcTemplate.update(insertDailyDataAggregation,
                    aggregationRequest.getUserId(),
                    date,
                    aggregationRequest.getDayDateKey(),
                    date);

        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error(" error while execute  " + insertDailyDataAggregation);
            DailyAppLogger.ERROR_LOGGER.error(" error while executing Query#{} = {} -> {}",
                    Queries.INSERT_DAILY_DAILY_DATA_AGGREGATION.id, insertDailyDataAggregation, ex);
            ex.printStackTrace();
            throw new ReportsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public int insertClosingDailyDataAggregation(AggregationRequest aggregationRequest) {
        String insertClosingDailyDataAggregation = QueriesCache.allQueries.get(Queries.INSERT_CLOSING_DAILY_DATA_AGGREGATION.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Query#{} = {}", Queries.INSERT_CLOSING_DAILY_DATA_AGGREGATION.id, insertClosingDailyDataAggregation);

        try {
            String date = utils.getCurrentDate();
            DailyAppLogger.DEBUG_LOGGER.debug("Aggregation-Request {} , date={}",
                    aggregationRequest, date);
            return jdbcTemplate.update(insertClosingDailyDataAggregation,
                    aggregationRequest.getUserId(),
                    date,
                    aggregationRequest.getDayDateKey(),
                    date);
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error(" error while execute  " + insertClosingDailyDataAggregation);
            DailyAppLogger.ERROR_LOGGER.error(" error while executing Query#{} = {} -> {}",
                    Queries.INSERT_CLOSING_DAILY_DATA_AGGREGATION.id, insertClosingDailyDataAggregation, ex);
            ex.printStackTrace();
            throw new ReportsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public int insertNetAddsDailyDataAggregation(AggregationRequest aggregationRequest) {
        String insertNetAddsDailyDataAggregation = QueriesCache.allQueries.get(Queries.INSERT_NET_ADDS_DAILY_DATA_AGGREGATION.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Query#{} = {}", Queries.INSERT_NET_ADDS_DAILY_DATA_AGGREGATION.id, insertNetAddsDailyDataAggregation);

        try {
            String date = utils.getCurrentDate();
            DailyAppLogger.DEBUG_LOGGER.debug("Aggregation-Request {} , date={}",
                    aggregationRequest, date);
            return jdbcTemplate.update(insertNetAddsDailyDataAggregation,
                    aggregationRequest.getUserId(),
                    date,
                    aggregationRequest.getDayDateKey(),
                    date);
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error(" error while execute  " + insertNetAddsDailyDataAggregation);
            DailyAppLogger.ERROR_LOGGER.error(" error while executing Query#{} = {} -> {}",
                    Queries.INSERT_NET_ADDS_DAILY_DATA_AGGREGATION.id, insertNetAddsDailyDataAggregation, ex);
            ex.printStackTrace();
            throw new ReportsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }


    public void updateAdjustFlagInValidEngineAggregation(AggregationRequest aggregationRequest) {
        String updateAdjustFlagValidationEngine = QueriesCache.allQueries.get(Queries.UPDATE_ADJUST_FLAG_VALIDATION_ENGINE.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Query#{} = {}", Queries.UPDATE_ADJUST_FLAG_VALIDATION_ENGINE.id, updateAdjustFlagValidationEngine);

        try {
            String date = utils.getCurrentDate();
            if (aggregationRequest.getInAdjustFlag()) {
                int diffCode = 1;
                DailyAppLogger.DEBUG_LOGGER.debug("Aggregation-Request {} , date={}, diffCode={}",
                        aggregationRequest, date, diffCode);
                jdbcTemplate.update(updateAdjustFlagValidationEngine,
                        aggregationRequest.getRatePlanKey(), aggregationRequest.getDayDateKey()
                        , diffCode, aggregationRequest.getDwhStatusKey());
            }
            if (aggregationRequest.getOutAdjustFlag()) {
                int diffCode = 2;
                DailyAppLogger.DEBUG_LOGGER.debug("Aggregation-Request {} , date={}, diffCode={}",
                        aggregationRequest, date, diffCode);
                jdbcTemplate.update(updateAdjustFlagValidationEngine,
                        aggregationRequest.getRatePlanKey(), aggregationRequest.getDayDateKey()
                        , diffCode, aggregationRequest.getDwhStatusKey());
            }

        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error(" error while execute  " + updateAdjustFlagValidationEngine);
            DailyAppLogger.ERROR_LOGGER.error(" error while executing Query#{} = {} -> {}",
                    Queries.UPDATE_ADJUST_FLAG_VALIDATION_ENGINE.id, updateAdjustFlagValidationEngine, ex);
            ex.printStackTrace();
            throw new ReportsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }
}