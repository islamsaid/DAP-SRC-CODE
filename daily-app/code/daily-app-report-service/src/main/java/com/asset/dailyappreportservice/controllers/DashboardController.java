package com.asset.dailyappreportservice.controllers;

import com.asset.dailyappreportservice.defines.Defines;
import com.asset.dailyappreportservice.defines.ErrorCodes;
import com.asset.dailyappreportservice.exception.ReportsException;
import com.asset.dailyappreportservice.logger.DailyAppLogger;
import com.asset.dailyappreportservice.model.request.EpochDateRequest;
import com.asset.dailyappreportservice.model.response.BaseResponse;
import com.asset.dailyappreportservice.model.response.MaxMinOpiningClosingAggregationDataResponse;
import com.asset.dailyappreportservice.service.ManualAdjustmentService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = Defines.ContextPaths.DASHBOARD)
public class DashboardController {
    @Autowired
    ManualAdjustmentService ManualAdjustmentService;

    @RequestMapping(value =  Defines.WEB_ACTIONS.GET_AGGREGATION_DATA, method = RequestMethod.POST)
    public BaseResponse<MaxMinOpiningClosingAggregationDataResponse> RetrieveMaxMinAggregationData(@Valid @RequestBody EpochDateRequest validationEngineRequest) throws ReportsException {
        String   traceId  = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        MaxMinOpiningClosingAggregationDataResponse resp = ManualAdjustmentService.MaxMinOpiningClosingAggregationData(validationEngineRequest);
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId,resp);
    }
}
