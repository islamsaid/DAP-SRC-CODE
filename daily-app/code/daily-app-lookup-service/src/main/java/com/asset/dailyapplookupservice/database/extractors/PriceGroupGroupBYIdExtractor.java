package com.asset.dailyapplookupservice.database.extractors;

import com.asset.dailyapplookupservice.defines.DatabaseStructs;
import com.asset.dailyapplookupservice.defines.Defines;
import com.asset.dailyapplookupservice.defines.ErrorCodes;
import com.asset.dailyapplookupservice.exception.LookupException;
import com.asset.dailyapplookupservice.logger.DailyAppLogger;
import com.asset.dailyapplookupservice.model.response.pricegroup.GetPgGroupModel;
import com.asset.dailyapplookupservice.model.shared.PgGroupModel;
import com.asset.dailyapplookupservice.model.shared.PriceGroupModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class PriceGroupGroupBYIdExtractor implements ResultSetExtractor<GetPgGroupModel> {
    private String column;

    @Override
    public GetPgGroupModel extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        GetPgGroupModel getPgGroupModel = new GetPgGroupModel();
        PgGroupModel pgGroupModel = null;
        PriceGroupModel priceGroupModel;
        ArrayList<PriceGroupModel> priceGroupModels = null;
        column = "";
        try {
            while (resultSet.next()) {

                if (pgGroupModel == null) {
                    pgGroupModel = new PgGroupModel();
                    priceGroupModels = new ArrayList<>();

                    column = DatabaseStructs.PG_GROUP.DESCRIPTION;
                    pgGroupModel.setDescription(resultSet.getString(DatabaseStructs.PG_GROUP.DESCRIPTION));

                    column = DatabaseStructs.PG_GROUP.SHOW_FLAG;
                    pgGroupModel.setShowFlag(resultSet.getInt(DatabaseStructs.PG_GROUP.SHOW_FLAG));

                    column = DatabaseStructs.PG_GROUP.PG_GROUP;
                    pgGroupModel.setPgGroup(resultSet.getString(DatabaseStructs.PG_GROUP.PG_GROUP));

                    getPgGroupModel.setPgGroupModel(pgGroupModel);
                    getPgGroupModel.setPriceGroupModelList(priceGroupModels);
                }

                Integer featureId = resultSet.getInt(DatabaseStructs.PRICE_GROUP.PRICE_GROUP_KEY);

                if (!featureId.equals(0)) {
                    priceGroupModel = new PriceGroupModel();
                    priceGroupModel.setPriceGroupKey(featureId);

                    column = DatabaseStructs.PRICE_GROUP.PRICE_GROUP_KEY;
                    priceGroupModel.setPriceGroupKey(resultSet.getInt(DatabaseStructs.PRICE_GROUP.PRICE_GROUP_KEY));

                    column = DatabaseStructs.PRICE_GROUP.PRICE_GROUP;
                    priceGroupModel.setPriceGroup(resultSet.getString(DatabaseStructs.PRICE_GROUP.PRICE_GROUP));

                    column = DatabaseStructs.PRICE_GROUP.PRICE_GROUP_CODE;
                    priceGroupModel.setPriceGroupCode(resultSet.getString(DatabaseStructs.PRICE_GROUP.PRICE_GROUP_CODE));

                    priceGroupModels.add(priceGroupModel);
                }
            }
            return getPgGroupModel;
        } catch (Exception e) {
            DailyAppLogger.ERROR_LOGGER.error("[PGG-Extraction] exception in column=[{}] ==> {}", column, e);
            DailyAppLogger.DEBUG_LOGGER.error("[PGG-Extraction] exception in column=[{}] ==> {}", column, e);
            e.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_MAPPING_ERROR, Defines.SEVERITY.FATAL);
        }
    }
}
