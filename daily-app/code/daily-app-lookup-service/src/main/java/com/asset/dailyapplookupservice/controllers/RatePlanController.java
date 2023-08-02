package com.asset.dailyapplookupservice.controllers;

import com.asset.dailyapplookupservice.defines.Defines;
import com.asset.dailyapplookupservice.defines.ErrorCodes;
import com.asset.dailyapplookupservice.logger.DailyAppLogger;
import com.asset.dailyapplookupservice.model.rateplan.RatePlanModel;
import com.asset.dailyapplookupservice.model.request.rateplan.RatePlansUpdateRequest;
import com.asset.dailyapplookupservice.model.response.BaseResponse;
import com.asset.dailyapplookupservice.model.response.rateplan.RatePlanResponse;
import com.asset.dailyapplookupservice.service.RatePlanService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = Defines.ContextPaths.RATE_PLAN)
public class RatePlanController {
    @Autowired
    RatePlanService ratePlanService;

    @RequestMapping(value = Defines.WEB_ACTIONS.GET_ALL, method = RequestMethod.POST)
    public BaseResponse<List<RatePlanResponse>> getAllRatePlans(){
        String   traceId  = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        List<RatePlanResponse> ratePlans = ratePlanService.getAllRatePlans();
        ThreadContext.remove(traceId);
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, ratePlans);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.UPDATE, method = RequestMethod.POST)
    public BaseResponse<String> updateRatePlan(@RequestBody RatePlanModel ratePlanModel){
        DailyAppLogger.DEBUG_LOGGER.info("Update RatePlan Request " + ratePlanModel);
        String   traceId  = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        ratePlanService.updateRatePlan(ratePlanModel);
        ThreadContext.remove(traceId);
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, null);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.UPDATE_BATCH, method = RequestMethod.POST)
    public BaseResponse<String> ratePlansBatchUpdate(@RequestBody RatePlansUpdateRequest ratePlans){
        String   traceId  = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        ratePlanService.ratePlansBatchUpdate(ratePlans.getRatePlans());
        ThreadContext.remove(traceId);
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, null);
    }
}
