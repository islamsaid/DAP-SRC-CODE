package com.asset.loggingService.service;

import com.asset.loggingService.dao.LoggingDao;
import com.asset.loggingService.loggers.DailyAppLogger;
import com.asset.loggingService.model.GetLogsRequest;
import com.asset.loggingService.model.LogModel;

import java.util.ArrayList;
import java.util.List;

import com.asset.loggingService.model.TrxUserDto;
import com.asset.loggingService.model.response.GetLogsResponse;
import com.asset.loggingService.security.JwtTokenUtil;
import com.asset.loggingService.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author islam.said
 */
@Component
public class LoggingService {

    private final LoggingDao loggingDao;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    Utils utils;

    @Autowired
    public LoggingService(LoggingDao loggingDao) {
        this.loggingDao = loggingDao;
    }

    @Transactional
    public void insertLogs(ArrayList<TrxUserDto> trxUserDtoArrayList) {
        DailyAppLogger.DEBUG_LOGGER.debug(" insert transaction user and transaction user details  in db");
        for ( TrxUserDto trxUserDto: trxUserDtoArrayList) {
            loggingDao.insertLogs(trxUserDto);
        }
    }

    public GetLogsResponse getLogs(GetLogsRequest getLogsRequest) {
        DailyAppLogger.DEBUG_LOGGER.debug("check if user id null ");
        if (getLogsRequest.getUserId() == null) {
            int userId=jwtTokenUtil.getUserModelFromToken(getLogsRequest.getToken()).getUserId();
            DailyAppLogger.DEBUG_LOGGER.debug("user id from toke : " +userId);
            getLogsRequest.setUserId(Integer.toString(userId));
        }
        DailyAppLogger.DEBUG_LOGGER.debug("convert echo date tp date format ddd/mm/yyyy ");
        getLogsRequest.setDate(utils.convertEpochToDate(getLogsRequest.getEchoDate()));
        List<TrxUserDto> logModelList = loggingDao.getLogs(getLogsRequest);

        return  new GetLogsResponse(logModelList);
    }
}
