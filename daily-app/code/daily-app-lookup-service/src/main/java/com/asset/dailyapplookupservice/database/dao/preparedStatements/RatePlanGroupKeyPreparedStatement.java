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

public class RatePlanGroupKeyPreparedStatement implements BatchPreparedStatementSetter {
    private List<RatePlanModel> ratePlanModelList;

    public RatePlanGroupKeyPreparedStatement(List<RatePlanModel> ratePlanModelList) {
        this.ratePlanModelList = ratePlanModelList;
    }

    @Override
    public void setValues(PreparedStatement ps, int i) throws SQLException {
        RatePlanModel ratePlan = ratePlanModelList.get(i);
        DailyAppLogger.DEBUG_LOGGER.info("Assign Rate-Plan-Code = {} to RPG = {}", ratePlan.getRatePlanCode(), ratePlan.getRatePlanGroupKey());

        ps.setInt(1, ratePlan.getRatePlanGroupKey());
        ps.setString(2, ratePlan.getRatePlanCode());

    }

    @Override
    public int getBatchSize() {
        return ratePlanModelList.size();
    }
}
