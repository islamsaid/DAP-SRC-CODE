package com.asset.loggingService.service;

import com.asset.loggingService.loggers.DailyAppLogger;
import com.asset.loggingService.model.TransactionUserDetails;
import com.asset.loggingService.model.TrxUserDto;
import com.asset.loggingService.model.TrxUserRequest;
import com.asset.loggingService.model.user.UserModel;
import com.asset.loggingService.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TransactionAdapter {
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    public TrxUserDto getTransactionUserDao(TrxUserRequest payload) {
        DailyAppLogger.DEBUG_LOGGER.debug("Check If old Map Exist or Get Differences from new map and old map");
        List<TransactionUserDetails> details = CheckIfNewMapExistToGetDifferences(payload.getOldValue(), payload.getNewValue());
        return buildTransactionUserDto(payload, details);
    }

    private TrxUserDto buildTransactionUserDto(TrxUserRequest payload, List<TransactionUserDetails> details) {
        TrxUserDto trxUserDto = new TrxUserDto();
        UserModel userModel =  jwtTokenUtil.getUserModelFromToken(payload.getToken());
        String SessionId = jwtTokenUtil.getSessionId(payload.getToken());
        trxUserDto.setUserId(userModel.getUserId());
        trxUserDto.setUserName(userModel.getUsername());
        trxUserDto.setSessionId(SessionId);
        trxUserDto.setActionId(payload.getActionId());
        trxUserDto.setRequestId(payload.getRequestId());
        trxUserDto.setPageName(payload.getPageName());
        trxUserDto.setRequestBody(extractor(payload.getRequestBody()));
        trxUserDto.setResponseBody(extractor(payload.getResponseBody()));
        trxUserDto.setObjectIdentifier(extractor(payload.getObjectIdentifier()));
        trxUserDto.setTransactionUserDetails(details);
        return trxUserDto;
    }

    private List<TransactionUserDetails> CheckIfNewMapExistToGetDifferences(ConcurrentHashMap<String, Object> oldValueMap,
                                                                            ConcurrentHashMap<String, Object> newValueMap) {
       if ((oldValueMap == null || oldValueMap.size() ==0)&&(newValueMap == null || newValueMap.size() ==0)){
           DailyAppLogger.DEBUG_LOGGER.debug("no new map or old map exist");
           return   new ArrayList<>(); //no data will be added in  TransactionUserDetails
       }
       else if (oldValueMap == null || oldValueMap.size() ==0) {
           DailyAppLogger.DEBUG_LOGGER.debug("only new map exist");
           return addNewValueMapInTransactionUserDetails(newValueMap);
        } else {
           DailyAppLogger.DEBUG_LOGGER.debug("get only differences  between old and new map ");
           return differanceListFromTwoMaps(oldValueMap, newValueMap);
        }
    }

    private List<TransactionUserDetails> addNewValueMapInTransactionUserDetails(ConcurrentHashMap<String, Object> newValueMap) {
        List<TransactionUserDetails> details = new ArrayList<>();
            for (Map.Entry<String, Object> entry : newValueMap.entrySet()) {
                TransactionUserDetails detail = new TransactionUserDetails();
                detail.setParamName(entry.getKey());
                detail.setOldVal(null);
                detail.setNewVal(extractor(String.valueOf(entry.getValue())));
                details.add(detail);
        }
        DailyAppLogger.DEBUG_LOGGER.debug("add New Values Map In Transaction User Details : "+ details);
        return details;
    }

    private List<TransactionUserDetails> differanceListFromTwoMaps(ConcurrentHashMap<String, Object> oldValueMap,
                                                                   ConcurrentHashMap<String, Object> newValueMap) {
        List<TransactionUserDetails> details = new ArrayList<>();
            for (String sharedKey : oldValueMap.keySet()) {
                Object oldValue = oldValueMap.get(sharedKey);
                Object newValue = newValueMap.get(sharedKey);
                if (!Objects.deepEquals(newValue, oldValue)) {
                    TransactionUserDetails detail = new TransactionUserDetails();
                    detail.setParamName(sharedKey);
                    detail.setOldVal(extractor(String.valueOf(oldValue)));
                    detail.setNewVal(extractor(String.valueOf(newValue)));

                    details.add(detail);
                }
        }
        DailyAppLogger.DEBUG_LOGGER.debug("get differences between two maps  Details : "+ details);
        return details;
    }

    private String extractor(String value) {
        if (value.length() >= 4000) {
            DailyAppLogger.DEBUG_LOGGER.debug(" get only first 4000 character put it in db if value bigger than 4000");
            return value.substring(0, 4000);// get only first 4000 character put it in db
        } else
            return value;
    }
}

