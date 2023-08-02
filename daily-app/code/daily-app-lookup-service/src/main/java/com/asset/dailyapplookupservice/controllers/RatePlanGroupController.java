package com.asset.dailyapplookupservice.controllers;

import com.asset.dailyapplookupservice.defines.Defines;
import com.asset.dailyapplookupservice.defines.ErrorCodes;
import com.asset.dailyapplookupservice.logger.DailyAppLogger;
import com.asset.dailyapplookupservice.model.rateplan.RatePlanGroupModel;
import com.asset.dailyapplookupservice.model.response.BaseResponseForAddObject;
import com.asset.dailyapplookupservice.model.request.rateplan.RatePlanGroupRequest;
import com.asset.dailyapplookupservice.model.response.BaseResponse;
import com.asset.dailyapplookupservice.service.RatePlanService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = Defines.ContextPaths.RATE_PLAN_GROUPS)
public class RatePlanGroupController
{
    @Autowired
    RatePlanService ratePlanService;

    @RequestMapping(value = Defines.WEB_ACTIONS.GET_ALL, method = RequestMethod.POST)
    public BaseResponse<List<RatePlanGroupModel>> getAllRatePlanGroups()
    {
        String   traceId  = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        List<RatePlanGroupModel> groups = ratePlanService.getAllRatePlanGroups();
        ThreadContext.remove(traceId);
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, groups);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.GET, method = RequestMethod.POST)
    public BaseResponse<RatePlanGroupModel> getRatePlanGroupByKey(@RequestBody RatePlanGroupRequest keyRequest)
    {
        DailyAppLogger.DEBUG_LOGGER.debug("Get Rate Plan Group of Key = " + keyRequest.getRatePlanGroupKey());
        String   traceId  = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        RatePlanGroupModel groupModel = ratePlanService.getRatePlanGroupByKey(keyRequest.getRatePlanGroupKey());
        ThreadContext.remove(traceId);
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, groupModel);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.ADD, method = RequestMethod.POST)
    public BaseResponse<BaseResponseForAddObject> addRatePlanGroup(@RequestBody RatePlanGroupModel groupModel)
    {
        DailyAppLogger.DEBUG_LOGGER.info("Add RatePlanGroup Request " + groupModel);
        String   traceId  = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        Integer key = ratePlanService.addRatePlanGroup(groupModel);
        BaseResponseForAddObject baseResponseForAddObject = new BaseResponseForAddObject(String.valueOf(key));
        ThreadContext.remove(traceId);
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, baseResponseForAddObject);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.UPDATE, method = RequestMethod.POST)
    public BaseResponse<String> updateRatePlanGroupWithRP(@RequestBody RatePlanGroupModel groupModel)
    {
        DailyAppLogger.DEBUG_LOGGER.info("Update RatePlanGroup Request " + groupModel);
        String   traceId  = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        ratePlanService.updateRatePlanGroupAndRatePlans(groupModel);
        ThreadContext.remove(traceId);
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, null);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.DELETE, method = RequestMethod.POST)
    public BaseResponse<String> deleteRatePlanGroup(@RequestBody RatePlanGroupModel groupModel)
    {
        DailyAppLogger.DEBUG_LOGGER.info("Delete RatePlanGroup of id = " + groupModel.getRatePlanGroupKey());
        String   traceId  = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        ratePlanService.deleteRatePlanGroup(groupModel.getRatePlanGroupKey());
        ThreadContext.remove(traceId);
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, null);
    }
}