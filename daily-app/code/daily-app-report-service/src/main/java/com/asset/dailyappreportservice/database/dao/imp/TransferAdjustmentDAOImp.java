package com.asset.dailyappreportservice.database.dao.imp;

import com.asset.dailyappreportservice.constants.Queries;
import com.asset.dailyappreportservice.defines.Defines;
import com.asset.dailyappreportservice.defines.ErrorCodes;
import com.asset.dailyappreportservice.exception.ReportsException;
import com.asset.dailyappreportservice.logger.DailyAppLogger;
import com.asset.dailyappreportservice.manager.QueriesCache;
import com.asset.dailyappreportservice.model.request.AddTransferAdjustmentRequest;
import com.asset.dailyappreportservice.model.request.TransferAdjustmentRequest;
import com.asset.dailyappreportservice.model.response.GetTransferAdjustment;
import com.asset.dailyappreportservice.security.JwtTokenUtil;
import com.asset.dailyappreportservice.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class TransferAdjustmentDAOImp {
    @Autowired
    private Utils utils;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final BeanPropertyRowMapper<GetTransferAdjustment> getAllBalancesBean = new BeanPropertyRowMapper<>(GetTransferAdjustment.class);

    public List<GetTransferAdjustment> RetrieveAllTransferAdjustment(TransferAdjustmentRequest transferAdjustmentRequest) {
        String retrieveAllTransferAdjustment = QueriesCache.allQueries.get(Queries.GET_ALL_TRANSFER_ADJUSTMENT.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Query#{} = {}", Queries.GET_ALL_TRANSFER_ADJUSTMENT.id, retrieveAllTransferAdjustment);
        try {
            String date = utils.convertEpochToDate(transferAdjustmentRequest.getDate());
            return jdbcTemplate.query(retrieveAllTransferAdjustment, getAllBalancesBean, date);
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error while execute " + retrieveAllTransferAdjustment);
            DailyAppLogger.ERROR_LOGGER.error("error while executing query#{}= {} -> {}",
                    Queries.GET_ALL_TRANSFER_ADJUSTMENT.id, retrieveAllTransferAdjustment, ex);
            ex.printStackTrace();
            throw new ReportsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public void addTransferAdjustment(List<AddTransferAdjustmentRequest> addTransferAdjustmentRequestList) {
        String addTransferAdjustment = QueriesCache.allQueries.get(Queries.INSERT_TRANSFER_DATA.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Query#{} = {}", Queries.INSERT_TRANSFER_DATA.id, addTransferAdjustment);
        DailyAppLogger.DEBUG_LOGGER.info(addTransferAdjustmentRequestList);
        try {
            jdbcTemplate.batchUpdate(addTransferAdjustment, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) {
                    try {
                        AddTransferAdjustmentRequest data = addTransferAdjustmentRequestList.get(i);

                        ps.setInt(1, data.getNumberOfSubs());
                        ps.setInt(2, data.getDataKey());
                        ps.setInt(3, data.getPriceGroupKey());
                        ps.setInt(4, data.getRatePlanKey());
                        ps.setInt(5, data.getTrxTypeKey());
                        ps.setInt(6, data.getRatePlanGroupKey());
                        ps.setInt(7, data.getDwhStatusKey());
                        ps.setInt(8, data.getUserId());
                        ps.setInt(9, data.getPgGroupKey());
                        ps.setString(10, utils.getCurrentDate());
                    } catch (SQLException ex) {
                        DailyAppLogger.DEBUG_LOGGER.error("error while execute " + addTransferAdjustment);
                        DailyAppLogger.ERROR_LOGGER.error("error while execute " + addTransferAdjustment, ex);
                        ex.printStackTrace();
                        throw new ReportsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
                    }
                }

                @Override
                public int getBatchSize() {
                    return addTransferAdjustmentRequestList.size();
                }
            });
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error while executing " + addTransferAdjustment, ex);
            DailyAppLogger.ERROR_LOGGER.error("error while executing query#{}= {} - {}",
                    Queries.INSERT_TRANSFER_DATA.id, addTransferAdjustment, ex);
            ex.printStackTrace();
            throw new ReportsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }

    }


}

