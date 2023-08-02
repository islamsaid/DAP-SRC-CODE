package com.asset.dailyapplookupservice.controllers;

import com.asset.dailyapplookupservice.defines.Defines;
import com.asset.dailyapplookupservice.defines.ErrorCodes;
import com.asset.dailyapplookupservice.exception.LookupException;
import com.asset.dailyapplookupservice.logger.DailyAppLogger;
import com.asset.dailyapplookupservice.model.request.priceGroup.GetAllPriceGroupResponse;
import com.asset.dailyapplookupservice.model.request.priceGroup.UpdateBatchPriceGroupResponse;
import com.asset.dailyapplookupservice.model.request.priceGroup.UpdatePriceGroupResponse;
import com.asset.dailyapplookupservice.model.response.BaseResponse;
import com.asset.dailyapplookupservice.service.PriceGroupService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = Defines.ContextPaths.PRICE_GROUP)
public class PriceGroupController {
    @Autowired
    PriceGroupService priceGroupService;

    @RequestMapping(value = Defines.WEB_ACTIONS.GET_ALL, method = RequestMethod.GET)
    public BaseResponse<GetAllPriceGroupResponse> getAll() throws LookupException {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        GetAllPriceGroupResponse resp = priceGroupService.getAll();
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, resp);

    }

    @RequestMapping(value = Defines.WEB_ACTIONS.UPDATE, method = RequestMethod.POST)
    public BaseResponse update(@RequestBody UpdatePriceGroupResponse updateRequest) throws LookupException {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        DailyAppLogger.DEBUG_LOGGER.info("Received update Price group request [" + updateRequest + "]");
        priceGroupService.update(updateRequest);
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, null);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.UPDATE_BATCH, method = RequestMethod.POST)
    public BaseResponse UpdateBatchPriceGroup(@RequestBody UpdateBatchPriceGroupResponse updateRequest) throws LookupException {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        priceGroupService.UpdateBatchPriceGroup(updateRequest);
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, null);
    }

}
