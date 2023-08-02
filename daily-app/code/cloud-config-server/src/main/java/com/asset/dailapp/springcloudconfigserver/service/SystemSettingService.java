package com.asset.dailapp.springcloudconfigserver.service;

import com.asset.dailapp.springcloudconfigserver.database.dao.SystemSettingDaoImp;
import com.asset.dailapp.springcloudconfigserver.defines.Defines;
import com.asset.dailapp.springcloudconfigserver.defines.ErrorCodes;
import com.asset.dailapp.springcloudconfigserver.exception.ConfigServerException;
import com.asset.dailapp.springcloudconfigserver.logger.DailyAppLogger;
import com.asset.dailapp.springcloudconfigserver.model.SystemSettingModelForApplication;
import com.asset.dailapp.springcloudconfigserver.model.request.UpdateSystemSettingModelRequestList;
import com.asset.dailapp.springcloudconfigserver.model.response.GetAllSystemSettingModelResponse;
import com.asset.dailapp.springcloudconfigserver.model.response.SystemSettingModelResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.ConfigurationException;
import java.util.HashMap;
import java.util.List;

@Component
public class SystemSettingService {
    @Autowired
    SystemSettingDaoImp systemSettingDaoImp;

    public GetAllSystemSettingModelResponse retrieveAllSystemSetting() throws ConfigServerException {
        DailyAppLogger.DEBUG_LOGGER.debug("Start retrieving all System Setting");
        HashMap<String, List<SystemSettingModelForApplication>> systemSettingList = systemSettingDaoImp.getAll();
        if (systemSettingList == null || systemSettingList.isEmpty()) {
            DailyAppLogger.DEBUG_LOGGER.error("No System setting found");
            throw new ConfigServerException(ErrorCodes.ERROR.NO_SYSTEM_SETTING_EXIST, Defines.SEVERITY.ERROR);
        }

        systemSettingList.get("general")
                .removeIf(setting -> setting.getCode().equalsIgnoreCase("ACCESS_TOKEN_KEY"));


        DailyAppLogger.DEBUG_LOGGER.debug("Done retrieving all System setting");
        return new GetAllSystemSettingModelResponse(systemSettingList);
    }

    public void updateSystemSetting(UpdateSystemSettingModelRequestList updateSystemSettingModelRequestList) {
        DailyAppLogger.DEBUG_LOGGER.debug("update  retrieving all System Setting");
        systemSettingDaoImp.updateSystemSetting(updateSystemSettingModelRequestList.getUpdateSystemSettingModelRequestList());
    }
}
