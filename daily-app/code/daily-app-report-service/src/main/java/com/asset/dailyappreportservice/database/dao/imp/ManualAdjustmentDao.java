package com.asset.dailyappreportservice.database.dao.imp;

import com.asset.dailyappreportservice.constants.Queries;
import com.asset.dailyappreportservice.database.com.asset.dailyapplookupservice.database.dao.preparedStatements.InsertDataAggregationPreparedStatement;
import com.asset.dailyappreportservice.database.extractors.RetrieveAggregationDataExtractor;
import com.asset.dailyappreportservice.database.extractors.RetrieveAggregationDataForManualAdjustmentExtractor;
import com.asset.dailyappreportservice.defines.Defines;
import com.asset.dailyappreportservice.defines.ErrorCodes;
import com.asset.dailyappreportservice.exception.ReportsException;
import com.asset.dailyappreportservice.logger.DailyAppLogger;
import com.asset.dailyappreportservice.manager.QueriesCache;
import com.asset.dailyappreportservice.model.ManualAdjustment.AggregatedDataModel;
import com.asset.dailyappreportservice.model.ManualAdjustment.DataKeysAggregationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ManualAdjustmentDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String sqlQuery;

    @Autowired
    private RetrieveAggregationDataForManualAdjustmentExtractor adjustmentExtractor;

    public List<AggregatedDataModel> getDataAggregationByDay(String date){
        try{
            sqlQuery = QueriesCache.allQueries.get(Queries.GET_AGGREGATION_DATA_BY_DAY.id);
            DailyAppLogger.DEBUG_LOGGER.debug("SQL Query#{} = {}", Queries.GET_AGGREGATION_DATA_BY_DAY.id, sqlQuery);
            return jdbcTemplate.query(sqlQuery, adjustmentExtractor, date, date);
        }catch (EmptyResultDataAccessException e) {
            return null;
        }catch (Exception e){
            DailyAppLogger.DEBUG_LOGGER.error("Database Error -> ", e);
            DailyAppLogger.ERROR_LOGGER.error("Database Error -> sql query#{} = {}");
            DailyAppLogger.ERROR_LOGGER.error(e);
            e.printStackTrace();
            throw new ReportsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL);
        }
    }

    public void insertBatchAggregatedData(List<DataKeysAggregationModel> dataModelList){
        try{
            sqlQuery = QueriesCache.allQueries.get(Queries.INSERT_AGGREGATED_DATA.id);
            DailyAppLogger.DEBUG_LOGGER.debug("SQL Query#{} = {}", Queries.INSERT_AGGREGATED_DATA.id, sqlQuery);
            jdbcTemplate.batchUpdate(sqlQuery, new InsertDataAggregationPreparedStatement(dataModelList));
        }catch (Exception e){
            DailyAppLogger.DEBUG_LOGGER.error("Database Error -> ", e);
            DailyAppLogger.ERROR_LOGGER.error("Database Error -> sql query#{} = {}");
            DailyAppLogger.ERROR_LOGGER.error(e);
            e.printStackTrace();
            throw new ReportsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL);
        }
    }

    public Integer getPgGroupKeyByPgKey(Integer pgKey){
        DailyAppLogger.DEBUG_LOGGER.debug("Price group Key = {}", pgKey);
        sqlQuery = QueriesCache.allQueries.get(Queries.GET_PG_GROUP_KEY_BY_PG_KEY.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Query#{} = {}", Queries.GET_PG_GROUP_KEY_BY_PG_KEY.id, sqlQuery);
        try{
            Integer pgGroupKey = jdbcTemplate.queryForObject(sqlQuery, Integer.class, pgKey);
            DailyAppLogger.DEBUG_LOGGER.debug("price-group-group key = {}", pgGroupKey);
            return pgGroupKey;
        }catch (Exception e){
            DailyAppLogger.DEBUG_LOGGER.error("db error Query#{} = {} -> {} ", Queries.GET_PG_GROUP_KEY_BY_PG_KEY.id, sqlQuery, e);
            DailyAppLogger.ERROR_LOGGER.error("db error Query#{} = {} -> {} ", Queries.GET_PG_GROUP_KEY_BY_PG_KEY.id, sqlQuery, e);
            e.printStackTrace();
            throw new ReportsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL);
        }
    }
}
