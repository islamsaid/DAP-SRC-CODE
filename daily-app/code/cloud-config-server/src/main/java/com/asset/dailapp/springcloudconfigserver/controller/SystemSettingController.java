package com.asset.dailapp.springcloudconfigserver.controller;

import com.asset.dailapp.springcloudconfigserver.defines.Defines;
import com.asset.dailapp.springcloudconfigserver.defines.ErrorCodes;
import com.asset.dailapp.springcloudconfigserver.exception.ConfigServerException;
import com.asset.dailapp.springcloudconfigserver.logger.DailyAppLogger;
import com.asset.dailapp.springcloudconfigserver.model.request.UpdateSystemSettingModelRequestList;
import com.asset.dailapp.springcloudconfigserver.model.response.BaseResponse;
import com.asset.dailapp.springcloudconfigserver.model.response.GetAllSystemSettingModelResponse;
import com.asset.dailapp.springcloudconfigserver.service.SystemSettingService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = Defines.ContextPaths.SYSTEM_SETTING)
public class SystemSettingController {
    @Autowired
    SystemSettingService systemSettingService;

    @RequestMapping(value = Defines.WEB_ACTIONS.GET_ALL, method = RequestMethod.GET)
    public BaseResponse<GetAllSystemSettingModelResponse> getAll() throws ConfigServerException {
        String   traceId  = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        DailyAppLogger.DEBUG_LOGGER.info("Received get all system SettingService request ");
        GetAllSystemSettingModelResponse resp = systemSettingService.retrieveAllSystemSetting();
        DailyAppLogger.DEBUG_LOGGER.info("Get all system SettingService request finished successfully");
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId,resp);

    }

    @RequestMapping(value = Defines.WEB_ACTIONS.UPDATE, method = RequestMethod.POST)
    public BaseResponse  updateSystemSetting(@RequestBody UpdateSystemSettingModelRequestList updateSystemSettingModelRequestList) throws ConfigServerException {
        String   traceId  = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        DailyAppLogger.DEBUG_LOGGER.info("UPDATE system SettingService request ");
         systemSettingService.updateSystemSetting(updateSystemSettingModelRequestList);
        DailyAppLogger.DEBUG_LOGGER.info("system SettingService request finished successfully");
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId,null);

    }
}
