package com.asset.dailyapplookupservice.database.extractors;

import com.asset.dailyapplookupservice.defines.DatabaseStructs;
import com.asset.dailyapplookupservice.defines.Defines;
import com.asset.dailyapplookupservice.defines.ErrorCodes;
import com.asset.dailyapplookupservice.exception.LookupException;
import com.asset.dailyapplookupservice.logger.DailyAppLogger;
import com.asset.dailyapplookupservice.model.shared.PgGroupModel;
import com.asset.dailyapplookupservice.model.shared.PriceGroupModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PriceGroupExtractor implements ResultSetExtractor<List<PriceGroupModel>> {
    String column;

    @Override
    public List<PriceGroupModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<PriceGroupModel> priceGroupModelList = new ArrayList<>();
        PriceGroupModel priceGroupModel;
        PgGroupModel pgGroupModel;
        try {
            column = "";
            while (resultSet.next()) {
                priceGroupModel = new PriceGroupModel();
                column = DatabaseStructs.PRICE_GROUP.PRICE_GROUP_KEY;
                priceGroupModel.setPriceGroupKey(resultSet.getInt(DatabaseStructs.PRICE_GROUP.PRICE_GROUP_KEY));

                column = DatabaseStructs.PRICE_GROUP.PRICE_GROUP;
                priceGroupModel.setPriceGroup(resultSet.getString(DatabaseStructs.PRICE_GROUP.PRICE_GROUP));

                column = DatabaseStructs.PRICE_GROUP.PRICE_GROUP_CODE;
                priceGroupModel.setPriceGroupCode(resultSet.getString(DatabaseStructs.PRICE_GROUP.PRICE_GROUP_CODE));

                column = DatabaseStructs.PRICE_GROUP.PRICE_GROUP;
                priceGroupModel.setPriceGroup(resultSet.getString(DatabaseStructs.PRICE_GROUP.PRICE_GROUP));

                column = DatabaseStructs.PRICE_GROUP.PG_GROUP_KEY;
                String PgGroupId = resultSet.getString(DatabaseStructs.PG_GROUP.PG_GROUP_KEY);
                if (PgGroupId != null && !PgGroupId.equals(0)) {
                    pgGroupModel = new PgGroupModel();

                    column = DatabaseStructs.PG_GROUP.DESCRIPTION;
                    pgGroupModel.setDescription(resultSet.getString(DatabaseStructs.PG_GROUP.DESCRIPTION));

                    column = DatabaseStructs.PG_GROUP.SHOW_FLAG;
                    pgGroupModel.setShowFlag(resultSet.getInt(DatabaseStructs.PG_GROUP.SHOW_FLAG));

                    column = DatabaseStructs.PG_GROUP.PG_GROUP;
                    pgGroupModel.setPgGroup(resultSet.getString(DatabaseStructs.PG_GROUP.PG_GROUP));

                    pgGroupModel.setPgGroupKey(PgGroupId);
                    priceGroupModel.setPgGroupKey(pgGroupModel);

                }
                priceGroupModelList.add(priceGroupModel);
            }
        } catch (Exception e) {
            DailyAppLogger.ERROR_LOGGER.error("[PG-Extraction] exception in column=[{}] ==> {}", column, e);
            DailyAppLogger.DEBUG_LOGGER.error("[PG-Extraction] exception in column=[{}] ==> {}", column, e);
            e.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_MAPPING_ERROR, Defines.SEVERITY.FATAL);
        }
        return priceGroupModelList;
    }
}
