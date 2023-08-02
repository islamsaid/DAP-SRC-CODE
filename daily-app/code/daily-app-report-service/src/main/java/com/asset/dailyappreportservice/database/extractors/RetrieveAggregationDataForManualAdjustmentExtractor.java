package com.asset.dailyappreportservice.database.extractors;

import com.asset.dailyappreportservice.defines.DatabaseStructs;
import com.asset.dailyappreportservice.model.ManualAdjustment.AggregatedDataModel;
import com.asset.dailyappreportservice.model.response.RetrieveAggregationDataResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Component
public class RetrieveAggregationDataForManualAdjustmentExtractor implements ResultSetExtractor<List<AggregatedDataModel>> {
    @Override
    public List<AggregatedDataModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<AggregatedDataModel> aggregatedDataModelList = new ArrayList<>();
        AggregatedDataModel aggregationDataResponse =null;
        while (resultSet.next()) {
            aggregationDataResponse =new AggregatedDataModel();
            aggregationDataResponse.setDateKey(resultSet.getInt(DatabaseStructs.DAILY_DATA_AGGREGATION.DATE_KEY));
            String type = resultSet.getString(DatabaseStructs.AGGREGATION_DATA_RESULT.RATE_PLAN_TYPE);
            int typeInt = "Prepaid".equals(type) ? 1 : "Postpaid".equals(type) ? 2 : 0;
            aggregationDataResponse.setRatePlanType(typeInt);
            aggregationDataResponse.setRatePlanGroupKey(resultSet.getInt(DatabaseStructs.DAILY_DATA_AGGREGATION.RATE_PLAN_GROUP_KEY));
            aggregationDataResponse.setRatePlan(resultSet.getString(DatabaseStructs.AGGREGATION_DATA_RESULT.RATE_PLAN));
            aggregationDataResponse.setRatePlanKey(resultSet.getInt(DatabaseStructs.DAILY_DATA_AGGREGATION.RATE_PLAN_KEY));
            aggregationDataResponse.setOpening(resultSet.getInt(DatabaseStructs.AGGREGATION_DATA_RESULT.OPENING));
            aggregationDataResponse.setClosing(resultSet.getInt(DatabaseStructs.AGGREGATION_DATA_RESULT.CLOSING));
            long variance =Long.valueOf( aggregationDataResponse.getClosing())-Long.valueOf(aggregationDataResponse.getOpening());
            aggregationDataResponse.setVariance(variance);

            aggregatedDataModelList.add(aggregationDataResponse);

        }
        return aggregatedDataModelList;
    }
}
