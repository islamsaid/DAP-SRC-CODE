package com.asset.dailyapplookupservice.database.extractors;

import com.asset.dailyapplookupservice.defines.DatabaseStructs;
import com.asset.dailyapplookupservice.defines.Defines;
import com.asset.dailyapplookupservice.defines.ErrorCodes;
import com.asset.dailyapplookupservice.exception.LookupException;
import com.asset.dailyapplookupservice.logger.DailyAppLogger;
import com.asset.dailyapplookupservice.model.rateplan.RatePlanModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RatePlanModelExtractor implements ResultSetExtractor<RatePlanModel> {
    private String column;

    @Override
    public RatePlanModel extractData(ResultSet rs) throws SQLException, DataAccessException {
        RatePlanModel ratePlan = null;
        column = "";
        while (rs.next()) {
            try {
                ratePlan = new RatePlanModel();

                column = DatabaseStructs.RATE_PLAN.RATE_PLAN;
                ratePlan.setRatePlan(rs.getString(DatabaseStructs.RATE_PLAN.RATE_PLAN));
                column = DatabaseStructs.RATE_PLAN.RATE_PLAN_KEY;
                ratePlan.setRatePlanKey(rs.getInt(DatabaseStructs.RATE_PLAN.RATE_PLAN_KEY));
                column = DatabaseStructs.RATE_PLAN.RATE_PLAN_GROUP_KEY;
                ratePlan.setRatePlanGroupKey(rs.getInt(DatabaseStructs.RATE_PLAN.RATE_PLAN_GROUP_KEY));

                column = DatabaseStructs.RATE_PLAN.CONTRACT_TYPE;
                String contractType = rs.getString(DatabaseStructs.RATE_PLAN.CONTRACT_TYPE);
                int contractTypeInt = "Consumer".equalsIgnoreCase(contractType) ? 1 : "Enterprise".equalsIgnoreCase(contractType) ? 2 : 3;
                ratePlan.setContractType(contractTypeInt);

                column = DatabaseStructs.RATE_PLAN.RATE_PLAN_TYPE;
                String type = rs.getString(DatabaseStructs.RATE_PLAN.RATE_PLAN_TYPE);
                int typeInt = "Prepaid".equalsIgnoreCase(type) ? 1 : "Postpaid".equalsIgnoreCase(type) ? 2 : 0;
                ratePlan.setRatePlanType(typeInt);

                column = DatabaseStructs.RATE_PLAN.SHOW_FLAG;
                ratePlan.setShowFlag(rs.getInt(DatabaseStructs.RATE_PLAN.SHOW_FLAG));
                column = DatabaseStructs.RATE_PLAN.ACTIVATION_SOURCE_FLAG;
                ratePlan.setActivationSourceFlag(rs.getInt(DatabaseStructs.RATE_PLAN.ACTIVATION_SOURCE_FLAG));

                column = DatabaseStructs.RATE_PLAN.FOR_IVR_COST;
                ratePlan.setForIvrCost(rs.getString(DatabaseStructs.RATE_PLAN.FOR_IVR_COST));
                column = DatabaseStructs.RATE_PLAN.FOR_IVR_REV;
                ratePlan.setForIvrRev(rs.getString(DatabaseStructs.RATE_PLAN.FOR_IVR_REV));
                column = DatabaseStructs.RATE_PLAN.COMBINED;
                ratePlan.setCombined(rs.getString(DatabaseStructs.RATE_PLAN.COMBINED));
                column = DatabaseStructs.RATE_PLAN.POST_PRE_FLAG;
                ratePlan.setPostPreFlag(rs.getString(DatabaseStructs.RATE_PLAN.POST_PRE_FLAG));
            }catch (Exception ex){
                DailyAppLogger.ERROR_LOGGER.error("[RP-Extraction] exception in column=[{}] ==> {}", column, ex);
                DailyAppLogger.DEBUG_LOGGER.error("[RP-Extraction] exception in column=[{}] ==> {}", column, ex);
                ex.printStackTrace();
                throw new LookupException(ErrorCodes.ERROR.DATABASE_MAPPING_ERROR, Defines.SEVERITY.FATAL);
            }

        }
        return ratePlan;
    }
}
