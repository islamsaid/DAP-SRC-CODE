package com.asset.dailyapplookupservice.controllers;

import com.asset.dailyapplookupservice.defines.Defines;
import com.asset.dailyapplookupservice.defines.ErrorCodes;
import com.asset.dailyapplookupservice.logger.DailyAppLogger;
import com.asset.dailyapplookupservice.model.response.BaseResponse;
import com.asset.dailyapplookupservice.model.response.serviceClass.ServiceClassesResponse;
import com.asset.dailyapplookupservice.model.response.serviceClass.ServiceClassResponse;
import com.asset.dailyapplookupservice.service.ServiceClassService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = Defines.ContextPaths.SERVICE_CLASSES)
public class ServiceClassController {

    @Autowired
    ServiceClassService serviceClassService;

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<ServiceClassesResponse> getAllServiceClasses() throws Exception {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", traceId);
        ServiceClassesResponse response = serviceClassService.getAllServiceClasses();
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0, traceId, response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse<Integer> updateServiceClass(@RequestBody ServiceClassResponse serviceClassResponse) throws Exception {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", traceId);
        DailyAppLogger.DEBUG_LOGGER.info("update service class with " + serviceClassResponse);
        Integer response = serviceClassService.updateServiceClass(serviceClassResponse);
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0, traceId, response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE_BATCH)
    public BaseResponse<Integer> updateServiceClassBatch(@RequestBody ServiceClassesResponse serviceClassesResponse) throws Exception {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", traceId);
        DailyAppLogger.DEBUG_LOGGER.info("Start update service class batch" + serviceClassesResponse);
        serviceClassService.updateServiceClassBatch(serviceClassesResponse.getServiceClasseList());
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,"success", 0, traceId, null);
    }
}
