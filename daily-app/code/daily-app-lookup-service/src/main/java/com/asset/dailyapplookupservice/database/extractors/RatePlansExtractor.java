package com.asset.dailyapplookupservice.database.extractors;

import com.asset.dailyapplookupservice.defines.DatabaseStructs;
import com.asset.dailyapplookupservice.defines.Defines;
import com.asset.dailyapplookupservice.defines.ErrorCodes;
import com.asset.dailyapplookupservice.exception.LookupException;
import com.asset.dailyapplookupservice.logger.DailyAppLogger;
import com.asset.dailyapplookupservice.model.rateplan.RatePlanGroupModel;
import com.asset.dailyapplookupservice.model.rateplan.RatePlanModel;
import com.asset.dailyapplookupservice.model.response.rateplan.RatePlanResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RatePlansExtractor implements ResultSetExtractor<List<RatePlanResponse>> {
    private String column = "";

    @Override
    public List<RatePlanResponse> extractData(ResultSet rs) throws SQLException, DataAccessException {
        try {
            DailyAppLogger.DEBUG_LOGGER.debug("Start Data Extraction");
            List<RatePlanResponse> ratePlans = new ArrayList<>();
            while (rs.next()) {
                RatePlanResponse ratePlanResponse = new RatePlanResponse();

                column = DatabaseStructs.RATE_PLAN.RATE_PLAN_CODE;
                ratePlanResponse.setRatePlanCode(rs.getString(DatabaseStructs.RATE_PLAN.RATE_PLAN_CODE));
                column = DatabaseStructs.RATE_PLAN.RATE_PLAN;
                ratePlanResponse.setRatePlan(rs.getString(DatabaseStructs.RATE_PLAN.RATE_PLAN));
                column = DatabaseStructs.RATE_PLAN.RATE_PLAN_KEY;
                ratePlanResponse.setRatePlanKey(rs.getInt(DatabaseStructs.RATE_PLAN.RATE_PLAN_KEY));
                column = DatabaseStructs.RATE_PLAN.RATE_PLAN_GROUP_KEY;
                ratePlanResponse.setRatePlanGroupKey(rs.getInt(DatabaseStructs.RATE_PLAN.RATE_PLAN_GROUP_KEY));

                column = DatabaseStructs.RATE_PLAN.CONTRACT_TYPE;
                String contractType = rs.getString(DatabaseStructs.RATE_PLAN.CONTRACT_TYPE);
                int contractTypeInt = "consumer".equalsIgnoreCase(contractType) ? 1 : "enterprise".equalsIgnoreCase(contractType) ? 2 : 3;
                ratePlanResponse.setContractType(contractTypeInt);
                DailyAppLogger.DEBUG_LOGGER.info("RatePlan code = {} ContractType = {" + contractType + " , " + contractTypeInt + "}", ratePlanResponse.getRatePlanCode());

                column = DatabaseStructs.RATE_PLAN.RATE_PLAN_TYPE;
                String type = rs.getString(DatabaseStructs.RATE_PLAN.RATE_PLAN_TYPE);
                int typeInt = "prepaid".equalsIgnoreCase(type) ? 1 : "postpaid".equalsIgnoreCase(type) ? 2 : 0;
                ratePlanResponse.setRatePlanType(typeInt);
                DailyAppLogger.DEBUG_LOGGER.info("RatePlan code = {} ratePlanType = {" + type + " , " + typeInt + "}", ratePlanResponse.getRatePlanCode());

                column = DatabaseStructs.RATE_PLAN.SHOW_FLAG;
                ratePlanResponse.setShowFlag(rs.getInt(DatabaseStructs.RATE_PLAN.SHOW_FLAG));
                column = DatabaseStructs.RATE_PLAN.ACTIVATION_SOURCE_FLAG;
                ratePlanResponse.setActivationSourceFlag(rs.getInt(DatabaseStructs.RATE_PLAN.ACTIVATION_SOURCE_FLAG));

                column = DatabaseStructs.RATE_PLAN.FOR_IVR_COST;
                ratePlanResponse.setForIvrCost(rs.getString(DatabaseStructs.RATE_PLAN.FOR_IVR_COST));
                column = DatabaseStructs.RATE_PLAN.FOR_IVR_REV;
                ratePlanResponse.setForIvrRev(rs.getString(DatabaseStructs.RATE_PLAN.FOR_IVR_REV));
                column = DatabaseStructs.RATE_PLAN.COMBINED;
                ratePlanResponse.setCombined(rs.getString(DatabaseStructs.RATE_PLAN.COMBINED));
                column = DatabaseStructs.RATE_PLAN.POST_PRE_FLAG;
                ratePlanResponse.setPostPreFlag(rs.getString(DatabaseStructs.RATE_PLAN.POST_PRE_FLAG));

                ratePlans.add(ratePlanResponse);
            }
            return ratePlans;
        } catch (Exception e) {
            DailyAppLogger.ERROR_LOGGER.error("[RP-Extraction] exception in column=[{}] ==> {}", column, e);
            DailyAppLogger.DEBUG_LOGGER.error("[RP-Extraction] exception in column=[{}] ==> {}", column, e);
            e.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_MAPPING_ERROR, Defines.SEVERITY.FATAL);
        }
    }
}
