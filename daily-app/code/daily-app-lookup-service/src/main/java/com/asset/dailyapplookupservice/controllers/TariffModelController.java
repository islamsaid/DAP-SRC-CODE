package com.asset.dailyapplookupservice.controllers;

import com.asset.dailyapplookupservice.defines.Defines;
import com.asset.dailyapplookupservice.defines.ErrorCodes;
import com.asset.dailyapplookupservice.logger.DailyAppLogger;
import com.asset.dailyapplookupservice.model.response.BaseResponse;
import com.asset.dailyapplookupservice.model.response.tariff.TariffModelsResponse;
import com.asset.dailyapplookupservice.model.response.tariff.TariffModelResponse;
import com.asset.dailyapplookupservice.service.TariffModelService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = Defines.ContextPaths.TARIFF_MODEL)
public class TariffModelController {

    @Autowired
    TariffModelService tariffModelService;

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<TariffModelsResponse> getAllTariffModels() throws Exception {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", traceId);
        TariffModelsResponse response = tariffModelService.getAllTariffModels();
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", 0, traceId, response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse<Integer> updateTariffModel(@RequestBody TariffModelResponse tariffModelResponse) throws Exception {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", traceId);
        DailyAppLogger.DEBUG_LOGGER.info("Start update tariff model " + tariffModelResponse);
        Integer response = tariffModelService.updateTariffModel(tariffModelResponse);
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", 0, traceId, response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE_BATCH)
    public BaseResponse<Integer> updateTariffModelBatch(@RequestBody TariffModelsResponse tariffModelsResponse) throws Exception {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", traceId);
        DailyAppLogger.DEBUG_LOGGER.info("Start update tariff model batch: " + tariffModelsResponse.getTariffModelList().size());
        tariffModelService.updateTariffModelBatch(tariffModelsResponse.getTariffModelList());
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", 0, traceId, null);
    }

}
