package com.asset.dailyappbackendservice.service;

import com.asset.dailyappbackendservice.database.dao.PrivilegesDao;
import com.asset.dailyappbackendservice.logger.DailyAppLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeService {
    @Autowired
    PrivilegesDao privilegesDao;

    public List<String> getPrivilegeURLsByProfileId(Integer profileId){
        DailyAppLogger.DEBUG_LOGGER.debug("Start Getting privilege urls for profile id = {}", profileId);
        List<String> urls = privilegesDao.getPrivilegeURLsByProfileId(profileId);
        DailyAppLogger.DEBUG_LOGGER.debug("#Retrieved_urls = {} ", urls.size());
        return urls;
    }
}
