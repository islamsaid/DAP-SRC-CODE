package com.asset.dailyapplookupservice.database.extractors;

import com.asset.dailyapplookupservice.constants.RatePlanType;
import com.asset.dailyapplookupservice.defines.DatabaseStructs;
import com.asset.dailyapplookupservice.defines.Defines;
import com.asset.dailyapplookupservice.defines.ErrorCodes;
import com.asset.dailyapplookupservice.exception.LookupException;
import com.asset.dailyapplookupservice.logger.DailyAppLogger;
import com.asset.dailyapplookupservice.model.rateplan.RatePlanGroupModel;
import com.asset.dailyapplookupservice.model.rateplan.RatePlanModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class RatePlanByIDExtractor implements ResultSetExtractor<RatePlanGroupModel> {
    private String column = "";

    @Override
    public RatePlanGroupModel extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        DailyAppLogger.DEBUG_LOGGER.debug("[RPG-id] Data Mapping");
        RatePlanGroupModel ratePlanGroupModel = null;
        RatePlanModel ratePlanModel;
        ArrayList<RatePlanModel> ratePlanModels = new ArrayList<>();
        try {
            while (resultSet.next()) {
                ratePlanGroupModel = new RatePlanGroupModel();

                column = DatabaseStructs.RATE_PLAN_GROUP.RATE_PLAN_GROUP_KEY;
                ratePlanGroupModel.setRatePlanGroupKey(resultSet.getInt(DatabaseStructs.RATE_PLAN_GROUP.RATE_PLAN_GROUP_KEY));

                column = DatabaseStructs.RATE_PLAN_GROUP.RATE_PLAN_GROUP;
                ratePlanGroupModel.setRatePlanGroup(resultSet.getString(DatabaseStructs.RATE_PLAN_GROUP.RATE_PLAN_GROUP));

                column = DatabaseStructs.RATE_PLAN_GROUP.SHOW_FLAG_ALIAS;
                ratePlanGroupModel.setShowFlag(resultSet.getInt(DatabaseStructs.RATE_PLAN_GROUP.SHOW_FLAG_ALIAS));

                column = DatabaseStructs.RATE_PLAN_GROUP.DESCRIPTION;
                ratePlanGroupModel.setDescription(resultSet.getString(DatabaseStructs.RATE_PLAN_GROUP.DESCRIPTION));

                ratePlanGroupModel.setRatePlans(ratePlanModels);

                column = DatabaseStructs.RATE_PLAN.RATE_PLAN_KEY;
                Integer RatePlanId = resultSet.getInt(DatabaseStructs.RATE_PLAN.RATE_PLAN_KEY);
                DailyAppLogger.DEBUG_LOGGER.info(" --> RatePlanID = " + RatePlanId);

                if (!RatePlanId.equals(0)) {
                    ratePlanModel = new RatePlanModel();
                    ratePlanModel.setRatePlanKey(RatePlanId);

                    column = DatabaseStructs.RATE_PLAN.RATE_PLAN;
                    ratePlanModel.setRatePlan(resultSet.getString(DatabaseStructs.RATE_PLAN.RATE_PLAN));

                    column = DatabaseStructs.RATE_PLAN.RATE_PLAN_CODE;
                    ratePlanModel.setRatePlanCode(resultSet.getString(DatabaseStructs.RATE_PLAN.RATE_PLAN_CODE));

                    column = DatabaseStructs.RATE_PLAN.RATE_PLAN_TYPE;
                    String ratePlanType = resultSet.getString(DatabaseStructs.RATE_PLAN.RATE_PLAN_TYPE);
                    int ratePlanTypeCode = RatePlanType.POST.getValue().equalsIgnoreCase(ratePlanType) ? RatePlanType.POST.getKey() :
                            RatePlanType.PRE.getValue().equalsIgnoreCase(ratePlanType) ? RatePlanType.PRE.getKey() : 0;
                    DailyAppLogger.DEBUG_LOGGER.info("RatePlan id = {} ratePlanType = {" + ratePlanType + " , " + ratePlanTypeCode + "}", RatePlanId);
                    ratePlanModel.setRatePlanType(ratePlanTypeCode);

                    column = DatabaseStructs.RATE_PLAN.CONTRACT_TYPE;
                    String contractType = resultSet.getString(DatabaseStructs.RATE_PLAN.CONTRACT_TYPE);
                    int contractTypeCode = "consumer".equalsIgnoreCase(contractType) ? 1 : "enterprise".equalsIgnoreCase(contractType) ? 2 : 3;
                    ratePlanModel.setContractType(contractTypeCode);
                    DailyAppLogger.DEBUG_LOGGER.info("RatePlan id = {} ContractType = {" + contractType + " , " + contractTypeCode + "}", RatePlanId);

                    column = DatabaseStructs.RATE_PLAN.ACTIVATION_SOURCE_FLAG;
                    ratePlanModel.setActivationSourceFlag(resultSet.getInt(DatabaseStructs.RATE_PLAN.ACTIVATION_SOURCE_FLAG));

                    column = DatabaseStructs.RATE_PLAN.SHOW_FLAG;
                    ratePlanModel.setShowFlag(resultSet.getInt(DatabaseStructs.RATE_PLAN.SHOW_FLAG));

                    column = DatabaseStructs.RATE_PLAN.COMBINED;
                    ratePlanModel.setCombined(resultSet.getString(DatabaseStructs.RATE_PLAN.COMBINED));

                    column = DatabaseStructs.RATE_PLAN.POST_PRE_FLAG;
                    ratePlanModel.setPostPreFlag(resultSet.getString(DatabaseStructs.RATE_PLAN.POST_PRE_FLAG));

                    column = DatabaseStructs.RATE_PLAN.FOR_IVR_REV;
                    ratePlanModel.setForIvrRev(resultSet.getString(DatabaseStructs.RATE_PLAN.FOR_IVR_REV));

                    column = DatabaseStructs.RATE_PLAN.FOR_IVR_COST;
                    ratePlanModel.setForIvrCost(resultSet.getString(DatabaseStructs.RATE_PLAN.FOR_IVR_COST));
                    ratePlanModels.add(ratePlanModel);
                }
            }
            return ratePlanGroupModel;
        } catch (Exception e) {
            DailyAppLogger.ERROR_LOGGER.error("[RP-Extraction] exception in column=[{}] ==> {}", column, e);
            DailyAppLogger.DEBUG_LOGGER.error("[RP-Extraction] exception in column=[{}] ==> {}", column, e);
            e.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_MAPPING_ERROR, Defines.SEVERITY.FATAL);
        }
    }
}
