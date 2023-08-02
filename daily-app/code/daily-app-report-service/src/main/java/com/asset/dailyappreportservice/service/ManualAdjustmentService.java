package com.asset.dailyappreportservice.service;

import com.asset.dailyappreportservice.configuration.Properties;
import com.asset.dailyappreportservice.constants.RatePlanType;
import com.asset.dailyappreportservice.database.dao.imp.ManualAdjustmentDao;
import com.asset.dailyappreportservice.defines.Defines;
import com.asset.dailyappreportservice.defines.ErrorCodes;
import com.asset.dailyappreportservice.exception.ReportsException;
import com.asset.dailyappreportservice.logger.DailyAppLogger;
import com.asset.dailyappreportservice.model.ManualAdjustment.AggregatedDataModel;
import com.asset.dailyappreportservice.model.ManualAdjustment.DataKeysAggregationModel;
import com.asset.dailyappreportservice.model.request.EpochDateRequest;
import com.asset.dailyappreportservice.model.response.MaxMinOpiningClosingAggregationDataResponse;
import com.asset.dailyappreportservice.model.response.RetrieveAggregationDataResponse;
import com.asset.dailyappreportservice.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ManualAdjustmentService
{
    @Autowired
    private ManualAdjustmentDao dataAggregationDao;

    @Autowired
    private Utils utils;

    @Autowired
    private Properties properties;

    public List<AggregatedDataModel> getDataAggregationByDay(long epochDate) {
        String date = utils.convertEpochToDate(epochDate);
        DailyAppLogger.DEBUG_LOGGER.debug("Get aggregated-data of Date = {} -> epoch={}", date, epochDate);

        List<AggregatedDataModel> aggregatedDataList = dataAggregationDao.getDataAggregationByDay(date);
        if (aggregatedDataList == null || aggregatedDataList.isEmpty())
        {
            DailyAppLogger.DEBUG_LOGGER.debug("The Entered Date Has No Aggregated Data");
            DailyAppLogger.ERROR_LOGGER.error("The Entered Date Has No Aggregated Data");
            throw new ReportsException(ErrorCodes.ERROR.DATE_HAS_NO_DATA, Defines.SEVERITY.ERROR);
        }
        DailyAppLogger.DEBUG_LOGGER.debug("#Retrieved-Rows = {}", aggregatedDataList.size());
        return aggregatedDataList;
    }

    @Transactional
    public void submitAdjustedData(List<DataKeysAggregationModel> dataModelList){
        DailyAppLogger.DEBUG_LOGGER.debug("1-insert adjustment data in daily_data_aggregation table for each submitted row");
        insertAdjustmentData(dataModelList);
        DailyAppLogger.DEBUG_LOGGER.debug("2-insert closing data in daily_data_aggregation table for each submitted row");
        insertClosingData(dataModelList);
        DailyAppLogger.DEBUG_LOGGER.debug("3-insert net adds data in daily_data_aggregation table for each submitted row");
        insertNetAddsData(dataModelList);
        DailyAppLogger.DEBUG_LOGGER.debug("Data Has been Submitted Successfully");
    }

    private void insertAdjustmentData(List<DataKeysAggregationModel> dataModelList)
    {
        Integer pgGroupKeyForPostPaid = dataAggregationDao.getPgGroupKeyByPgKey(properties.getPriceGroupKeyForGroupSelectionForPostPaid());
        Integer pgGroupKeyForPrePaid = dataAggregationDao.getPgGroupKeyByPgKey(properties.getPriceGroupKeyForGroupSelectionForPrePaid());

        for (int i=0; i<dataModelList.size(); i++)
        {
            DailyAppLogger.DEBUG_LOGGER.debug("Row number: {}", i+1);
            if(dataModelList.get(i).getNumberOfSubs() > properties.getMaximumNumberOfSubscriptions())
            {
                DailyAppLogger.DEBUG_LOGGER.error("Number of Subs Exceeds {} in Row Number = {}", properties.getMaximumNumberOfSubscriptions(),i+1);
                DailyAppLogger.ERROR_LOGGER.error("Number of Subs Exceeds {} in Row Number = {}", properties.getMaximumNumberOfSubscriptions(), i+1);
                throw new ReportsException(ErrorCodes.ERROR.SUBSCRIPTIONS_EXCEEDS_3000, Defines.SEVERITY.ERROR, String.valueOf(i+1));
            }

            if (dataModelList.get(i).getRatePlanType() == 2) // post-paid
            {
                DailyAppLogger.DEBUG_LOGGER.debug("rate plan type = {}", RatePlanType.POST.getValue());
                dataModelList.set(i, prepareRow(dataModelList.get(i),
                        properties.getPgKeyForPostPaid(), properties.getDwhStatusKeyForPostPaid(), pgGroupKeyForPostPaid,
                        properties.getUserIdForClosing(), dataModelList.get(i).getTrxTypeKey()));
            }
            else if (dataModelList.get(i).getRatePlanType() == 1) // Pre-Paid
            {
                DailyAppLogger.DEBUG_LOGGER.debug("rate plan type = {}", RatePlanType.PRE.getValue());
                dataModelList.set(i, prepareRow(dataModelList.get(i),
                        properties.getPgKeyForPrePaid(), properties.getDwhStatusKeyForPrePaid(), pgGroupKeyForPrePaid,
                        properties.getUserIdForClosing(), dataModelList.get(i).getTrxTypeKey()));
            }
            else{
                DailyAppLogger.DEBUG_LOGGER.error("Invalid rate plan type = {}", dataModelList.get(i).getRatePlanType());
                DailyAppLogger.ERROR_LOGGER.error("Invalid rate plan type = {}", dataModelList.get(i).getRatePlanType());
                throw new ReportsException(ErrorCodes.ERROR.INVALID_RATE_PLAN_TYPE, Defines.SEVERITY.FATAL, String.valueOf(i+1));
            }
        }
        DailyAppLogger.DEBUG_LOGGER.info("1- post-processed list -> {}", dataModelList);
        dataAggregationDao.insertBatchAggregatedData(dataModelList);
    }

    private void insertClosingData(List<DataKeysAggregationModel> dataModelList)
    {
        for (int i=0; i<dataModelList.size(); i++)
        {
            DailyAppLogger.DEBUG_LOGGER.debug("Row number: {}", i+1);
            if (dataModelList.get(i).getRatePlanType() == 2) // post-paid
            {
                DailyAppLogger.DEBUG_LOGGER.debug("rate plan type = {}", RatePlanType.POST.getValue());
                Integer pgGroupKey = dataAggregationDao.getPgGroupKeyByPgKey(properties.getPriceGroupKeyForGroupSelectionForPostPaid());
                dataModelList.set(i, prepareRow(dataModelList.get(i),
                        properties.getPgKeyForPostPaid(), properties.getDwhStatusKeyForPostPaid(), pgGroupKey,
                        properties.getUserIdForClosing(), properties.getTrxTypeKeyForClosing()));
            }
            else if (dataModelList.get(i).getRatePlanType() == 1) // Pre-Paid
            {
                DailyAppLogger.DEBUG_LOGGER.debug("rate plan type = {}", RatePlanType.PRE.getValue());
                Integer pgGroupKey = dataAggregationDao.getPgGroupKeyByPgKey(properties.getPriceGroupKeyForGroupSelectionForPrePaid());
                dataModelList.set(i, prepareRow(dataModelList.get(i),
                        properties.getPgKeyForPrePaid(), properties.getDwhStatusKeyForPrePaid(), pgGroupKey,
                        properties.getUserIdForClosing(), properties.getTrxTypeKeyForClosing()));
            }
            else{
                DailyAppLogger.DEBUG_LOGGER.error("Invalid rate plan type = {}", dataModelList.get(i).getRatePlanType());
                DailyAppLogger.ERROR_LOGGER.error("Invalid rate plan type = {}", dataModelList.get(i).getRatePlanType());
                throw new ReportsException(ErrorCodes.ERROR.INVALID_RATE_PLAN_TYPE, Defines.SEVERITY.FATAL, String.valueOf(i+1));
            }
        }
        DailyAppLogger.DEBUG_LOGGER.info("2- post-processed list -> {}", dataModelList);
        dataAggregationDao.insertBatchAggregatedData(dataModelList);
    }

    private void insertNetAddsData(List<DataKeysAggregationModel> dataModelList)
    {
        for (int i=0; i<dataModelList.size(); i++)
        {
            DailyAppLogger.DEBUG_LOGGER.debug("Row number: {}", i+1);
            if (dataModelList.get(i).getRatePlanType() == 2) // post-paid
            {
                DailyAppLogger.DEBUG_LOGGER.debug("rate plan type = {}", RatePlanType.POST.getValue());
                Integer pgGroupKey = dataAggregationDao.getPgGroupKeyByPgKey(properties.getPriceGroupKeyForGroupSelectionForPostPaid());
                dataModelList.set(i, prepareRow(dataModelList.get(i),
                        properties.getPgKeyForPostPaid(), properties.getDwhStatusKeyForPostPaid(), pgGroupKey,
                        properties.getUserIdForClosing(), properties.getTrxTypeKeyForNetAdds()));
            }
            else if (dataModelList.get(i).getRatePlanType() == 1) // Pre-Paid
            {
                DailyAppLogger.DEBUG_LOGGER.debug("rate plan type = {}", RatePlanType.PRE.getValue());
                Integer pgGroupKey = dataAggregationDao.getPgGroupKeyByPgKey(properties.getPriceGroupKeyForGroupSelectionForPrePaid());
                dataModelList.set(i, prepareRow(dataModelList.get(i),
                        properties.getPgKeyForPrePaid(), properties.getDwhStatusKeyForPrePaid(), pgGroupKey,
                        properties.getUserIdForClosing(), properties.getTrxTypeKeyForNetAdds()));
            }
            else{
                DailyAppLogger.DEBUG_LOGGER.error("Invalid rate plan type = {}", dataModelList.get(i).getRatePlanType());
                DailyAppLogger.ERROR_LOGGER.error("Invalid rate plan type = {}", dataModelList.get(i).getRatePlanType());
                throw new ReportsException(ErrorCodes.ERROR.INVALID_RATE_PLAN_TYPE, Defines.SEVERITY.FATAL, String.valueOf(i+1));
            }
        }
        DailyAppLogger.DEBUG_LOGGER.info("3- post-processed list -> {} ", dataModelList);
        dataAggregationDao.insertBatchAggregatedData(dataModelList);
    }

    private DataKeysAggregationModel prepareRow(DataKeysAggregationModel dataModel, Integer pgKey, Integer dwhStatusKey, Integer pgGroupKey, Integer userId, Integer trxId)
    {
        dataModel.setPgGroupKey(pgGroupKey);
        dataModel.setPriceGroupKey(pgKey);
        dataModel.setDwhStatusKey(dwhStatusKey);
        dataModel.setUserId(userId);
        dataModel.setTrxTypeKey(trxId);

        return dataModel;
    }

    public MaxMinOpiningClosingAggregationDataResponse MaxMinOpiningClosingAggregationData(EpochDateRequest validationEngineRequest) {
        String date = utils.convertEpochToDate(validationEngineRequest.getDate());
        DailyAppLogger.DEBUG_LOGGER.debug("Entered Date = {} -> epoch = {}", utils.convertEpochToDate(validationEngineRequest.getDate()), validationEngineRequest.getDate());

        List<AggregatedDataModel> aggregationDataResponseList = dataAggregationDao.getDataAggregationByDay(date);
        DailyAppLogger.DEBUG_LOGGER.debug("#Retrieved-Records = {}", aggregationDataResponseList.size());

        List<AggregatedDataModel> maxAggregationData = MaxOpiningClosingAggregationData(aggregationDataResponseList);
        DailyAppLogger.DEBUG_LOGGER.debug("#Max-Opening-Records = {}", maxAggregationData.size());
        DailyAppLogger.DEBUG_LOGGER.info("Max-Opening List = {}", maxAggregationData);

        List<AggregatedDataModel> minAggregationData = MinOpiningClosingAggregationData(aggregationDataResponseList);
        DailyAppLogger.DEBUG_LOGGER.debug("#Min-Closing-Records = {}", minAggregationData.size());
        DailyAppLogger.DEBUG_LOGGER.info("Min-Closing List = {}", minAggregationData);

        return new MaxMinOpiningClosingAggregationDataResponse(minAggregationData,maxAggregationData);
    }

    private  List<AggregatedDataModel> MinOpiningClosingAggregationData(List<AggregatedDataModel> aggregationDataResponseList) {
        return  aggregationDataResponseList.stream().sorted(Comparator.comparing(AggregatedDataModel::getVariance))
                .limit(properties.getLimit())
                .collect(Collectors.toList());
    }
    private  List<AggregatedDataModel> MaxOpiningClosingAggregationData(List<AggregatedDataModel> aggregationDataResponseList) {
        return  aggregationDataResponseList.stream().sorted(Comparator.comparing(AggregatedDataModel::getVariance).reversed())
                .limit(properties.getLimit())
                .collect(Collectors.toList());
    }

}
