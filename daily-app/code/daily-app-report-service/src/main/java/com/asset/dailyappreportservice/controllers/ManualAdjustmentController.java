package com.asset.dailyappreportservice.controllers;

import com.asset.dailyappreportservice.defines.Defines;
import com.asset.dailyappreportservice.defines.ErrorCodes;
import com.asset.dailyappreportservice.logger.DailyAppLogger;
import com.asset.dailyappreportservice.model.ManualAdjustment.AggregatedDataModel;
import com.asset.dailyappreportservice.model.ManualAdjustment.DataKeysAggregationModel;
import com.asset.dailyappreportservice.model.request.EpochDateRequest;
import com.asset.dailyappreportservice.model.request.SubmitManualAdjustmentRequest;
import com.asset.dailyappreportservice.model.response.BaseResponse;
import com.asset.dailyappreportservice.service.ManualAdjustmentService;
import com.asset.dailyappreportservice.util.Utils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = Defines.ContextPaths.MANUAL_ADJUSTMENT)
public class ManualAdjustmentController {
    @Autowired
    ManualAdjustmentService manualAdjustmentService;

    @RequestMapping(value = Defines.WEB_ACTIONS.GET_ALL, method = RequestMethod.POST)
    public BaseResponse<List<AggregatedDataModel>> getAllAggregationData(@RequestBody EpochDateRequest dateRequest){
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);

        List<AggregatedDataModel> response = manualAdjustmentService.getDataAggregationByDay(dateRequest.getDate());
        ThreadContext.remove("traceId");
        DailyAppLogger.DEBUG_LOGGER.debug("--------------------------------REQUEST ENDED--------------------------------");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, response);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.UPDATE, method = RequestMethod.POST)
    public BaseResponse<String> submitAdjustedData(@RequestBody SubmitManualAdjustmentRequest submitRequest){
        DailyAppLogger.DEBUG_LOGGER.debug("Submit Manual-data request has been received with #rows = {}", submitRequest.getAggregationList().size());
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);

        DailyAppLogger.DEBUG_LOGGER.info(submitRequest.getAggregationList());
        manualAdjustmentService.submitAdjustedData(submitRequest.getAggregationList());
        ThreadContext.remove("traceId");
        DailyAppLogger.DEBUG_LOGGER.debug("--------------------------------REQUEST ENDED--------------------------------");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, null);
    }
}
