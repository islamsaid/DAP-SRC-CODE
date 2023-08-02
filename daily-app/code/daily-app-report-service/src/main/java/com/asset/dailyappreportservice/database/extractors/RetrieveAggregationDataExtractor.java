package com.asset.dailyappreportservice.database.extractors;

import com.asset.dailyappreportservice.defines.DatabaseStructs;
import com.asset.dailyappreportservice.model.response.RetrieveAggregationDataResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Component
public class RetrieveAggregationDataExtractor implements ResultSetExtractor<List<RetrieveAggregationDataResponse>> {
    @Override
    public List<RetrieveAggregationDataResponse> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<RetrieveAggregationDataResponse> aggregationDataResponseList = new ArrayList<>();
        RetrieveAggregationDataResponse aggregationDataResponse =null;
        while (resultSet.next()){
            aggregationDataResponse =new RetrieveAggregationDataResponse();
            aggregationDataResponse.setDateKey(resultSet.getString(DatabaseStructs.AGGREGATION_DATA_RESULT.DATE_KEY));
            aggregationDataResponse.setFullDate(resultSet.getString(DatabaseStructs.AGGREGATION_DATA_RESULT.FULL_DATE));
            aggregationDataResponse.setInSubs(resultSet.getString(DatabaseStructs.AGGREGATION_DATA_RESULT.IN_SUBS));
            aggregationDataResponse.setOutSubs(resultSet.getString(DatabaseStructs.AGGREGATION_DATA_RESULT.OUT_SUBS));
            aggregationDataResponse.setRatePlan(resultSet.getString(DatabaseStructs.AGGREGATION_DATA_RESULT.RATE_PLAN));
            aggregationDataResponse.setRatePlanKey(resultSet.getString(DatabaseStructs.AGGREGATION_DATA_RESULT.RATE_PLAN_KEY));
            aggregationDataResponse.setOpening(resultSet.getString(DatabaseStructs.AGGREGATION_DATA_RESULT.OPENING));
            aggregationDataResponse.setClosing(resultSet.getString(DatabaseStructs.AGGREGATION_DATA_RESULT.CLOSING));
            aggregationDataResponse.setDwhStatus(resultSet.getString(DatabaseStructs.AGGREGATION_DATA_RESULT.DWH_STATUS));
            aggregationDataResponse.setDwhStatusKey(resultSet.getString(DatabaseStructs.AGGREGATION_DATA_RESULT.DWH_STATUS_KEY));
            long variance =Long.valueOf( aggregationDataResponse.getClosing())-Long.valueOf(aggregationDataResponse.getOpening());
            aggregationDataResponse.setVariance(variance);

            aggregationDataResponseList.add(aggregationDataResponse);



        }


        return aggregationDataResponseList;
    }
}
