package com.asset.dailyapplookupservice.controllers;

import com.asset.dailyapplookupservice.defines.Defines;
import com.asset.dailyapplookupservice.defines.ErrorCodes;
import com.asset.dailyapplookupservice.exception.LookupException;
import com.asset.dailyapplookupservice.logger.DailyAppLogger;
import com.asset.dailyapplookupservice.model.response.BaseResponseForAddObject;
import com.asset.dailyapplookupservice.model.request.priceGroup.AddPriceGroupGroupRequest;
import com.asset.dailyapplookupservice.model.request.priceGroup.GetPgGroupRequestModel;
import com.asset.dailyapplookupservice.model.response.BaseResponse;
import com.asset.dailyapplookupservice.model.response.pricegroup.GetAllGroupGroups;
import com.asset.dailyapplookupservice.model.response.pricegroup.GetPgGroupModel;
import com.asset.dailyapplookupservice.service.PriceGroupService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = Defines.ContextPaths.PRICE_GROUP_GROUPS)
public class PriceGroupGroupsController {
    @Autowired
    PriceGroupService priceGroupService;

    @RequestMapping(value = Defines.WEB_ACTIONS.GET_ALL, method = RequestMethod.GET)
    public BaseResponse<GetAllGroupGroups> getAll() throws LookupException {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        GetAllGroupGroups resp = priceGroupService.getAllPriceGroupGroups();
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, resp);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.GET, method = RequestMethod.POST)
    public BaseResponse<GetPgGroupModel> getPgGroup(@RequestBody GetPgGroupRequestModel PgGroup) throws LookupException {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);

        DailyAppLogger.DEBUG_LOGGER.debug("Update PgGroup of id =  " + PgGroup.getPgGroupId());
        GetPgGroupModel resp = priceGroupService.getPriceGroupGroups(PgGroup);
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, resp);
    }


    @RequestMapping(value = Defines.WEB_ACTIONS.ADD, method = RequestMethod.POST)
    public BaseResponse<BaseResponseForAddObject> add(@RequestBody AddPriceGroupGroupRequest addPriceGroupGroupRequest) {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        DailyAppLogger.DEBUG_LOGGER.info("New PgGroup: " + addPriceGroupGroupRequest);
        int pgGroupId = priceGroupService.addPriceGroupGroups(addPriceGroupGroupRequest);
        ThreadContext.remove("traceId");
        BaseResponseForAddObject baseResponseForAddObject = new BaseResponseForAddObject(String.valueOf(pgGroupId));
         return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, baseResponseForAddObject);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.UPDATE, method = RequestMethod.POST)
    public BaseResponse update(@RequestBody AddPriceGroupGroupRequest addPriceGroupGroupRequest) {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        DailyAppLogger.DEBUG_LOGGER.info("Update Price-Group-Group Request " + addPriceGroupGroupRequest);
        priceGroupService.updatePriceGroupGroups(addPriceGroupGroupRequest);
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, null);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.DELETE, method = RequestMethod.POST)
    public BaseResponse<String> deletePgGroup(@RequestBody GetPgGroupRequestModel groupRequestModel) {
        DailyAppLogger.DEBUG_LOGGER.debug("DELETE PgGroup of id = {}", groupRequestModel.getPgGroupId());
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        priceGroupService.deletePgGroup(groupRequestModel.getPgGroupId());
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, "Deleted");
    }
}
