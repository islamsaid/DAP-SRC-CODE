package com.asset.dailyapplookupservice.database.extractors;

import com.asset.dailyapplookupservice.defines.DatabaseStructs;
import com.asset.dailyapplookupservice.defines.Defines;
import com.asset.dailyapplookupservice.defines.ErrorCodes;
import com.asset.dailyapplookupservice.exception.LookupException;
import com.asset.dailyapplookupservice.logger.DailyAppLogger;
import com.asset.dailyapplookupservice.model.rateplan.RatePlanGroupModel;
import com.asset.dailyapplookupservice.model.rateplan.RatePlanModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RatePlanGroupsExtractor implements ResultSetExtractor<List<RatePlanGroupModel>> {
    private String column = "";
    @Override
    public List<RatePlanGroupModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
        DailyAppLogger.DEBUG_LOGGER.debug("[RPG] Data Mapping");
        List<RatePlanGroupModel> groups = new ArrayList<>();
        HashMap<Integer, RatePlanModel> ratePlanGroupMap = new HashMap<>();
        while (rs.next()) {
            RatePlanGroupModel ratePlanGroupModel = new RatePlanGroupModel();
            RatePlanModel ratePlan = new RatePlanModel();
            try {
                column = DatabaseStructs.RATE_PLAN_GROUP.RATE_PLAN_GROUP_KEY;
                ratePlanGroupModel.setRatePlanGroupKey(rs.getInt(DatabaseStructs.RATE_PLAN_GROUP.RATE_PLAN_GROUP_KEY));

                column = DatabaseStructs.RATE_PLAN_GROUP.RATE_PLAN_GROUP;
                ratePlanGroupModel.setRatePlanGroup(rs.getString(DatabaseStructs.RATE_PLAN_GROUP.RATE_PLAN_GROUP));

                column = DatabaseStructs.RATE_PLAN_GROUP.SHOW_FLAG;
                ratePlanGroupModel.setShowFlag(rs.getInt(DatabaseStructs.RATE_PLAN_GROUP.SHOW_FLAG));

                column = DatabaseStructs.RATE_PLAN_GROUP.DESCRIPTION;
                ratePlanGroupModel.setDescription(rs.getString(DatabaseStructs.RATE_PLAN_GROUP.DESCRIPTION));

                groups.add(ratePlanGroupModel);
            }catch (Exception e){
                DailyAppLogger.ERROR_LOGGER.error("[RP-Extraction] exception in column=[{}] ==> {}", column, e);
                DailyAppLogger.DEBUG_LOGGER.error("[RP-Extraction] exception in column=[{}] ==> {}", column, e);
                e.printStackTrace();
                throw new LookupException(ErrorCodes.ERROR.DATABASE_MAPPING_ERROR, Defines.SEVERITY.FATAL);
            }

        }
        return groups;

    }
}
