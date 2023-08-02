package com.asset.dailyapplookupservice.database.dao.preparedStatements;

import com.asset.dailyapplookupservice.defines.Defines;
import com.asset.dailyapplookupservice.defines.ErrorCodes;
import com.asset.dailyapplookupservice.exception.LookupException;
import com.asset.dailyapplookupservice.logger.DailyAppLogger;
import com.asset.dailyapplookupservice.model.rateplan.RatePlanModel;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RatePlansBatchPreparedStatement implements BatchPreparedStatementSetter {
    private List<RatePlanModel> ratePlanModelList;

    public RatePlansBatchPreparedStatement(List<RatePlanModel> ratePlanModelList){
        this.ratePlanModelList = ratePlanModelList;
    }

    @Override
    public void setValues(PreparedStatement ps, int i) throws SQLException {
        try {
            RatePlanModel ratePlan = ratePlanModelList.get(i);
            DailyAppLogger.DEBUG_LOGGER.debug("Rate-Plan Name = {} ==> is prepared to be updated", ratePlan.getRatePlan());

            ps.setInt(1, ratePlan.getRatePlanGroupKey());
            ps.setInt(2, ratePlan.getShowFlag());
            ps.setString(3, ratePlan.getRatePlanCode());

        } catch (SQLException ex) {
            DailyAppLogger.DEBUG_LOGGER.error("Preparing SQL Statement Exception ==> {}", ex.getMessage());
            DailyAppLogger.ERROR_LOGGER.error("Preparing SQL Statement Exception ==> {}", ex.getMessage());
            throw new LookupException(ErrorCodes.ERROR.DATABASE_MAPPING_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @Override
    public int getBatchSize() {
        return ratePlanModelList.size();
    }
}
