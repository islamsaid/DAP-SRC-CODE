package com.asset.dailyappreportservice.controllers;

import com.asset.dailyappreportservice.defines.Defines;
import com.asset.dailyappreportservice.defines.ErrorCodes;
import com.asset.dailyappreportservice.exception.ReportsException;
import com.asset.dailyappreportservice.logger.DailyAppLogger;
import com.asset.dailyappreportservice.model.request.AddBatchTransferAdjustmentRequest;
import com.asset.dailyappreportservice.model.request.AddTransferAdjustmentRequest;
import com.asset.dailyappreportservice.model.request.TransferAdjustmentRequest;
import com.asset.dailyappreportservice.model.response.BaseResponse;
import com.asset.dailyappreportservice.model.response.GetAllTransferAdjustment;
import com.asset.dailyappreportservice.model.response.GetTransferAdjustment;
import com.asset.dailyappreportservice.service.TransferAdjustmentService;
import com.asset.dailyappreportservice.util.Utils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = Defines.ContextPaths.TRANSFER_ADJUSTMENT)
public class TransferAdjustmentController {
    @Autowired
    TransferAdjustmentService transferAdjustmentService;

    @RequestMapping(value = Defines.WEB_ACTIONS.GET_ALL, method = RequestMethod.POST)
    public BaseResponse<GetAllTransferAdjustment> RetrieveAllTransferAdjustment(@RequestBody TransferAdjustmentRequest transferAdjustmentRequeset) throws ReportsException {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);

        DailyAppLogger.DEBUG_LOGGER.debug("get-all TRANSFER ADJUSTMENTS of date={} -> epoch={}",
                Utils.convertEpochToDate(transferAdjustmentRequeset.getDate()), transferAdjustmentRequeset.getDate());
        GetAllTransferAdjustment resp = transferAdjustmentService.RetrieveAllTransferAdjustment(transferAdjustmentRequeset);
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, resp);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.ADD, method = RequestMethod.POST)
    public BaseResponse add(@RequestBody AddBatchTransferAdjustmentRequest transferAdjustmentRequest)  throws ReportsException{
        String   traceId  = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        DailyAppLogger.DEBUG_LOGGER.debug("#received rows={}",transferAdjustmentRequest.getAddTransferAdjustmentRequests().size());
        DailyAppLogger.DEBUG_LOGGER.info(transferAdjustmentRequest.getAddTransferAdjustmentRequests());
        transferAdjustmentService.addTransferAdjustment(transferAdjustmentRequest);
        ThreadContext.remove("traceId");
        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId,null);
    }

}
