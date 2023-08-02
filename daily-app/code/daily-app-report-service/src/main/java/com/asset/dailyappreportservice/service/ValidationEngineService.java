package com.asset.dailyappreportservice.service;

import com.asset.dailyappreportservice.configuration.Properties;
import com.asset.dailyappreportservice.database.dao.imp.ValidationEngineDAOImp;
import com.asset.dailyappreportservice.defines.Defines;
import com.asset.dailyappreportservice.defines.ErrorCodes;
import com.asset.dailyappreportservice.excel.ExcelHelper;
import com.asset.dailyappreportservice.exception.ReportsException;
import com.asset.dailyappreportservice.logger.DailyAppLogger;
import com.asset.dailyappreportservice.model.request.AggregationListRequest;
import com.asset.dailyappreportservice.model.request.EpochDateRequest;
import com.asset.dailyappreportservice.model.response.RetrieveAggregationDataResponse;
import com.asset.dailyappreportservice.model.validationEngine.GetAllAggregationData;
import com.asset.dailyappreportservice.model.validationEngine.GetAllBalances;
import com.asset.dailyappreportservice.model.validationEngine.GetAllTransactionResponse;
import com.asset.dailyappreportservice.model.validationEngine.TransactionModel;
import com.asset.dailyappreportservice.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.List;

@Component
public class ValidationEngineService {

    @Autowired
    Properties properties;
    @Autowired
    ValidationEngineDAOImp validationEngineDAOImp;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    public GetAllBalances RetrieveBalances(EpochDateRequest validationEngineRequest) {
        GetAllBalances getAllBalances = validationEngineDAOImp.RetrieveBalances(validationEngineRequest);
        if (getAllBalances.getClosing() != null && getAllBalances.getOpening() != null) {
            getAllBalances.setVariance(Double.parseDouble(getAllBalances.getClosing()) - Double.parseDouble(getAllBalances.getOpening())); //TODO UNCOMMENT VARIANCE
        }
        DailyAppLogger.DEBUG_LOGGER.debug("retrieved balance: {}", getAllBalances);
        return getAllBalances;
    }

    public GetAllAggregationData RetrieveAggregationData(EpochDateRequest validationEngineRequest) {
        List<RetrieveAggregationDataResponse> aggregationDataResponses = validationEngineDAOImp.RetrieveAggregationData(validationEngineRequest);
        if (aggregationDataResponses == null || aggregationDataResponses.isEmpty())
        {
            DailyAppLogger.DEBUG_LOGGER.debug("The Entered Date Has No Aggregated Data");
            DailyAppLogger.ERROR_LOGGER.error("The Entered Date Has No Aggregated Data");
            throw new ReportsException(ErrorCodes.ERROR.DATE_HAS_NO_DATA, Defines.SEVERITY.ERROR);
        }
        DailyAppLogger.DEBUG_LOGGER.debug("#Retrieved Records = {}", aggregationDataResponses.size());
        return new GetAllAggregationData(aggregationDataResponses);
    }

    public GetAllTransactionResponse GetTransactionType() {
        List<TransactionModel> transactionModelList = validationEngineDAOImp.GetTransactionType();
        DailyAppLogger.DEBUG_LOGGER.debug("#TransactionTypes = {}", transactionModelList.size());
        DailyAppLogger.DEBUG_LOGGER.info("Transactions List = {}", transactionModelList);
        return new GetAllTransactionResponse(transactionModelList);
    }

    @Transactional(rollbackFor = ReportsException.class)// todo rollback
    public void update(AggregationListRequest aggregationListRequest) {
        int userId = jwtTokenUtil.parseJwt(aggregationListRequest.getToken()).getUserId();

        for (int i = 0; i < aggregationListRequest.getAggregationLists().size(); i++) {
            DailyAppLogger.DEBUG_LOGGER.debug("------------------------For Row number: {} [1-based]----------------------", i+1);
            aggregationListRequest.getAggregationLists().get(i).setUserId(userId);

            DailyAppLogger.DEBUG_LOGGER.debug("1-Update VALID_ENGINE_AGGREGATION  ");
            validationEngineDAOImp.updateValidEngineAggregation(aggregationListRequest.getAggregationLists().get(i));

            DailyAppLogger.DEBUG_LOGGER.debug("2-Update VALIDATION_ENGINE  ");
            validationEngineDAOImp.updateValidationEngine(aggregationListRequest.getAggregationLists().get(i)); //todo no table yet

            DailyAppLogger.DEBUG_LOGGER.debug("3-Insert daily records in DAILY_DATA_AGGREGATION  ");
            int insertDailyDataAggregation = validationEngineDAOImp.insertDailyDataAggregation(aggregationListRequest.getAggregationLists().get(i));

            DailyAppLogger.DEBUG_LOGGER.debug("4-Insert closing records in DAILY_DATA_AGGREGATION  ");
            int insertClosingDailyDataAggregation = validationEngineDAOImp.insertClosingDailyDataAggregation(aggregationListRequest.getAggregationLists().get(i));

            DailyAppLogger.DEBUG_LOGGER.debug("5-Insert net adds records in DAILY_DATA_AGGREGATION  ");
            int insertNetAddsDailyDataAggregation = validationEngineDAOImp.insertNetAddsDailyDataAggregation(aggregationListRequest.getAggregationLists().get(i));

            DailyAppLogger.DEBUG_LOGGER.debug("6-Update in VALID_ENGINE_AGGREGATION  ");
            validationEngineDAOImp.updateAdjustFlagInValidEngineAggregation(aggregationListRequest.getAggregationLists().get(i));
        }
        DailyAppLogger.DEBUG_LOGGER.info("Validated List: -> {} ", aggregationListRequest.getAggregationLists());
        DailyAppLogger.DEBUG_LOGGER.debug("Number of Submitted rows = {}", aggregationListRequest.getAggregationLists().size());
    }


    public ByteArrayInputStream loadExcel() {
        List<TransactionModel> transactionModelList = validationEngineDAOImp.GetTransactionType();
        DailyAppLogger.DEBUG_LOGGER.debug("Number of TransactionModels Loaded={}", transactionModelList.size());
        ByteArrayInputStream in = ExcelHelper.buildExcel(transactionModelList);
        return in;
    }

}
