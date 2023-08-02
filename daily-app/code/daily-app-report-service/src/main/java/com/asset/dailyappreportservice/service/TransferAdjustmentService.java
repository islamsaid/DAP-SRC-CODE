package com.asset.dailyappreportservice.service;

import com.asset.dailyappreportservice.configuration.Properties;
import com.asset.dailyappreportservice.constants.RatePlanType;
import com.asset.dailyappreportservice.database.dao.imp.ManualAdjustmentDao;
import com.asset.dailyappreportservice.database.dao.imp.TransferAdjustmentDAOImp;
import com.asset.dailyappreportservice.defines.Defines;
import com.asset.dailyappreportservice.defines.ErrorCodes;
import com.asset.dailyappreportservice.exception.ReportsException;
import com.asset.dailyappreportservice.logger.DailyAppLogger;
import com.asset.dailyappreportservice.model.request.AddBatchTransferAdjustmentRequest;
import com.asset.dailyappreportservice.model.request.AddTransferAdjustmentRequest;
import com.asset.dailyappreportservice.model.request.TransferAdjustmentRequest;
import com.asset.dailyappreportservice.model.response.GetAllTransferAdjustment;
import com.asset.dailyappreportservice.model.response.GetTransferAdjustment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransferAdjustmentService {
    @Autowired
    TransferAdjustmentDAOImp transferAdjustmentDAOImp;
    @Autowired
    private ManualAdjustmentDao dataAggregationDao;
    @Autowired
    private Properties properties;

    public GetAllTransferAdjustment RetrieveAllTransferAdjustment(TransferAdjustmentRequest transferAdjustmentRequeset) {
        List<GetTransferAdjustment> allTransferAdjustment = transferAdjustmentDAOImp.RetrieveAllTransferAdjustment(transferAdjustmentRequeset);
        if (allTransferAdjustment == null || allTransferAdjustment.isEmpty()) {
            DailyAppLogger.DEBUG_LOGGER.debug("The Entered Date Has No Aggregated Data");
            DailyAppLogger.ERROR_LOGGER.error("The Entered Date Has No Aggregated Data");
            throw new ReportsException(ErrorCodes.ERROR.DATE_HAS_NO_DATA, Defines.SEVERITY.ERROR);
        }
        DailyAppLogger.DEBUG_LOGGER.debug("Number of retrieved transfer-adjustment rows = {}", allTransferAdjustment.size());
        return new GetAllTransferAdjustment(allTransferAdjustment);
    }

    public void addTransferAdjustment(AddBatchTransferAdjustmentRequest transferAdjustmentRequest) {
        Integer pgGroupKeyForPostPaid = dataAggregationDao.getPgGroupKeyByPgKey(properties.getPriceGroupKeyForGroupSelectionForPostPaid());
        Integer pgGroupKeyForPrePaid = dataAggregationDao.getPgGroupKeyByPgKey(properties.getPriceGroupKeyForGroupSelectionForPrePaid());

        for (int i = 0; i < transferAdjustmentRequest.getAddTransferAdjustmentRequests().size(); i++) {
            DailyAppLogger.DEBUG_LOGGER.debug("-------------For Row number:{} [1-based]---------------", i + 1);
            checkIfRatePlanKeyNull(transferAdjustmentRequest.getAddTransferAdjustmentRequests().get(i), i);

            if (transferAdjustmentRequest.getAddTransferAdjustmentRequests().get(i).getNumberOfSubs() > properties.getMaximumNumberOfSubscriptions()) {
                DailyAppLogger.DEBUG_LOGGER.debug("Number of Subs Exceeds {} in Row Number = {}", properties.getMaximumNumberOfSubscriptions(), i + 1);
                DailyAppLogger.ERROR_LOGGER.error("Number of Subs Exceeds {} in Row Number = {}", properties.getMaximumNumberOfSubscriptions(), i + 1);
                throw new ReportsException(ErrorCodes.ERROR.SUBSCRIPTIONS_EXCEEDS_3000, Defines.SEVERITY.ERROR, String.valueOf(i + 1));
            }

            if (transferAdjustmentRequest.getAddTransferAdjustmentRequests().get(i).getRatePlanType() == 2) {
                if (pgGroupKeyForPostPaid == null || pgGroupKeyForPostPaid.equals(null)) {
                    DailyAppLogger.DEBUG_LOGGER.debug("pgGroupKeyForPostPaid is NULL");
                    DailyAppLogger.ERROR_LOGGER.error("pgGroupKeyForPrePaid is NULL");
                    throw new ReportsException(ErrorCodes.ERROR.PG_GROUP_FOR_PRICE_GROUP_NULL, Defines.SEVERITY.ERROR, "pg group null");
                }
                transferAdjustmentRequest.getAddTransferAdjustmentRequests().set(i,
                        buildTransferAdjustment(
                                transferAdjustmentRequest.getAddTransferAdjustmentRequests().get(i),
                                properties.getPgKeyForPostPaid(),
                                properties.getDwhStatusKeyForPostPaid(),
                                pgGroupKeyForPostPaid,
                                properties.getUserIdForClosing())
                );
            } else if (transferAdjustmentRequest.getAddTransferAdjustmentRequests().get(i).getRatePlanType() == 1) {
                if (pgGroupKeyForPrePaid == null || pgGroupKeyForPrePaid.equals(null)) {
                    DailyAppLogger.DEBUG_LOGGER.debug("pgGroupKeyForPrePaid is null");
                    DailyAppLogger.ERROR_LOGGER.error("pgGroupKeyForPrePaid is null");
                    throw new ReportsException(ErrorCodes.ERROR.PG_GROUP_FOR_PRICE_GROUP_NULL, Defines.SEVERITY.ERROR, "pg group null");
                }
                transferAdjustmentRequest.getAddTransferAdjustmentRequests().set(i,
                        buildTransferAdjustment(
                                transferAdjustmentRequest.getAddTransferAdjustmentRequests().get(i),
                                properties.getPgKeyForPrePaid(),
                                properties.getDwhStatusKeyForPrePaid(),
                                pgGroupKeyForPrePaid,
                                properties.getUserIdForClosing())
                );
            } else {
                DailyAppLogger.DEBUG_LOGGER.error("Invalid rate-plan type={}", transferAdjustmentRequest.getAddTransferAdjustmentRequests().get(i).getRatePlanType());
                DailyAppLogger.ERROR_LOGGER.error("Invalid rate-plan type={}", transferAdjustmentRequest.getAddTransferAdjustmentRequests().get(i).getRatePlanType());
                throw new ReportsException(ErrorCodes.ERROR.INVALID_RATE_PLAN_TYPE, Defines.SEVERITY.FATAL, String.valueOf(i + 1));
            }
        }

        transferAdjustmentDAOImp.addTransferAdjustment(transferAdjustmentRequest.getAddTransferAdjustmentRequests());
        DailyAppLogger.DEBUG_LOGGER.debug("done adding TransferAdjustments, #submitted-rows={}", transferAdjustmentRequest.getAddTransferAdjustmentRequests().size());
    }

    private void checkIfRatePlanKeyNull(AddTransferAdjustmentRequest transferAdjustmentRequest, int transferAdjustmentRequestIndex) {
        DailyAppLogger.DEBUG_LOGGER.debug("RatePlanKey={}", transferAdjustmentRequest.getRatePlanKey());
        if (transferAdjustmentRequest.getRatePlanKey() == 0) {
            DailyAppLogger.DEBUG_LOGGER.debug("rate plan shouldn't be null in row:{} ", transferAdjustmentRequestIndex + 1);
            DailyAppLogger.ERROR_LOGGER.error("rate plan shouldn't be null in row:{}  ", transferAdjustmentRequestIndex + 1);
            throw new ReportsException(ErrorCodes.ERROR.RATE_PLAN_KEY_NULL, Defines.SEVERITY.ERROR, String.valueOf(transferAdjustmentRequestIndex + 1));
        }
    }

    private AddTransferAdjustmentRequest buildTransferAdjustment(AddTransferAdjustmentRequest transferAdjustmentRequest, Integer pgKey, Integer dwhStatusKey, Integer pgGroupKey, Integer userId) {
        {
            transferAdjustmentRequest.setPgGroupKey(pgGroupKey);
            transferAdjustmentRequest.setPriceGroupKey(pgKey);
            transferAdjustmentRequest.setDwhStatusKey(dwhStatusKey);
            transferAdjustmentRequest.setUserId(userId);
            return transferAdjustmentRequest;
        }
    }
}
