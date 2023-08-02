package com.asset.dailyappreportservice.database.com.asset.dailyapplookupservice.database.dao.preparedStatements;

import com.asset.dailyappreportservice.defines.Defines;
import com.asset.dailyappreportservice.defines.ErrorCodes;
import com.asset.dailyappreportservice.exception.ReportsException;
import com.asset.dailyappreportservice.logger.DailyAppLogger;
import com.asset.dailyappreportservice.model.ManualAdjustment.DataKeysAggregationModel;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class InsertDataAggregationPreparedStatement  implements BatchPreparedStatementSetter
{
    private List<DataKeysAggregationModel> dataModelList;

    public InsertDataAggregationPreparedStatement(List<DataKeysAggregationModel> dataModelList) {
        this.dataModelList = dataModelList;
    }

    @Override
    public void setValues(PreparedStatement ps, int i) throws SQLException {
        try {
            DailyAppLogger.DEBUG_LOGGER.info("Preparing SQL Statement in progress...");
            DataKeysAggregationModel dataModel = dataModelList.get(i);
            DailyAppLogger.DEBUG_LOGGER.debug("manual data model is {}", dataModel);

            ps.setInt(1, dataModel.getNumberOfSubs());
            ps.setInt(2, dataModel.getDateKey());
            ps.setInt(3, dataModel.getPriceGroupKey());
            ps.setInt(4, dataModel.getRatePlanKey());
            ps.setInt(5, dataModel.getTrxTypeKey());
            ps.setInt(6, dataModel.getRatePlanGroupKey());
            ps.setInt(7, dataModel.getDwhStatusKey());
            ps.setInt(8, dataModel.getUserId());
            ps.setInt(9, dataModel.getPgGroupKey());
            ps.setString(10, getCurrentDate());
        }catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("Preparing SQL Statement Exception");
            DailyAppLogger.ERROR_LOGGER.error("Preparing SQL Statement Exception");
            throw new ReportsException(ErrorCodes.ERROR.DATABASE_MAPPING_ERROR, Defines.SEVERITY.ERROR);
        }
    }

    @Override
    public int getBatchSize() {
        return dataModelList.size();
    }

    private String getCurrentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Defines.FORMATS.DATE_FORMAT);
        return dtf.format(LocalDateTime.now());
    }
}
