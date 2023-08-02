package com.asset.dailyappreportservice.controllers;

import com.asset.dailyappreportservice.defines.Defines;
import com.asset.dailyappreportservice.defines.ErrorCodes;
import com.asset.dailyappreportservice.exception.ReportsException;
import com.asset.dailyappreportservice.logger.DailyAppLogger;
import com.asset.dailyappreportservice.model.request.AggregationListRequest;
import com.asset.dailyappreportservice.model.validationEngine.GetAllAggregationData;
import com.asset.dailyappreportservice.model.validationEngine.GetAllBalances;
import com.asset.dailyappreportservice.model.validationEngine.GetAllTransactionResponse;
import com.asset.dailyappreportservice.model.request.EpochDateRequest;
import com.asset.dailyappreportservice.model.response.BaseResponse;
import com.asset.dailyappreportservice.service.ValidationEngineService;
import com.asset.dailyappreportservice.util.Utils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;

//todo date
@RestController
@RequestMapping(value = Defines.ContextPaths.VALIDATE_ENGINE)
@Validated
public class ValidationEngineController {
    @Autowired
    ValidationEngineService balanceService;


    @RequestMapping(value = Defines.WEB_ACTIONS.BALANCE_ALL, method = RequestMethod.POST)
    public BaseResponse<GetAllBalances> RetrieveBalances(@Valid @RequestBody EpochDateRequest validationEngineRequest) throws ReportsException {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        DailyAppLogger.DEBUG_LOGGER.debug("Request balance of date = {} -> epoch = {}",
                Utils.convertEpochToDate(validationEngineRequest.getDate()), validationEngineRequest.getDate());
        GetAllBalances resp = balanceService.RetrieveBalances(validationEngineRequest);
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, resp);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.Retrieve_aggregation_data, method = RequestMethod.POST)
    public BaseResponse<GetAllAggregationData> RetrieveAggregationData(@Valid @RequestBody EpochDateRequest validationEngineRequest) throws ReportsException {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        DailyAppLogger.DEBUG_LOGGER.debug("Request AggregationData of date = {} -> epoch = {}",
                Utils.convertEpochToDate(validationEngineRequest.getDate()), validationEngineRequest.getDate());
        GetAllAggregationData resp = balanceService.RetrieveAggregationData(validationEngineRequest);
        DailyAppLogger.DEBUG_LOGGER.debug("Get all AggregationData request finished successfully");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, resp);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.transaction_type, method = RequestMethod.GET)
    public BaseResponse<GetAllTransactionResponse> GetTransactionType() throws ReportsException {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        GetAllTransactionResponse resp = balanceService.GetTransactionType();
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, resp);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.UPDATE, method = RequestMethod.POST)
    public BaseResponse update(HttpServletRequest request, @Valid @RequestBody AggregationListRequest aggregationListRequest) throws ReportsException, MethodArgumentNotValidException, MissingServletRequestParameterException {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        DailyAppLogger.DEBUG_LOGGER.info("add token ");
        String token = request.getHeader("Authorization");
        aggregationListRequest.setToken(token);
        DailyAppLogger.DEBUG_LOGGER.debug("#Received-Rows = {} to be submitted", aggregationListRequest.getAggregationLists().size());
        balanceService.update(aggregationListRequest);
        DailyAppLogger.DEBUG_LOGGER.debug("update aggregation and validation request ended");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId);
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> getFile(HttpServletResponse response) throws IOException {
        String filename = "validation.xlsx";
        InputStreamResource file = new InputStreamResource(balanceService.loadExcel());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }


}
