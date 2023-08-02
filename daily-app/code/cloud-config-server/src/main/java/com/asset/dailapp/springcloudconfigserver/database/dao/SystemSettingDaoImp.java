package com.asset.dailapp.springcloudconfigserver.database.dao;

import com.asset.dailapp.springcloudconfigserver.configration.Properties;
import com.asset.dailapp.springcloudconfigserver.constants.Queries;
import com.asset.dailapp.springcloudconfigserver.database.extractors.SystemSettingExtractor;
import com.asset.dailapp.springcloudconfigserver.defines.Defines;
import com.asset.dailapp.springcloudconfigserver.defines.ErrorCodes;
import com.asset.dailapp.springcloudconfigserver.exception.ConfigServerException;
import com.asset.dailapp.springcloudconfigserver.logger.DailyAppLogger;
import com.asset.dailapp.springcloudconfigserver.manager.QueriesCache;
import com.asset.dailapp.springcloudconfigserver.model.SystemSettingModelForApplication;
import com.asset.dailapp.springcloudconfigserver.model.request.UpdateSystemSettingModelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Component
public class SystemSettingDaoImp {

    @Autowired
    SystemSettingExtractor systemSettingModel;
    @Autowired
    Properties properties;
    @Autowired
    JdbcTemplate jdbcTemplate;
    public HashMap<String, List<SystemSettingModelForApplication>> getAll() {
        String getAllSystemSetting = QueriesCache.allQueries.get(Queries.GET_ALL_SYSTEM_SETTING.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Start getting  sys setting " + getAllSystemSetting);
        try {
            return   jdbcTemplate.query(getAllSystemSetting,systemSettingModel,properties.getLabel(),properties.getProfile());
         } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error while execute " + getAllSystemSetting);
            DailyAppLogger.ERROR_LOGGER.debug("error while execute " + getAllSystemSetting, ex);
            throw new ConfigServerException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public void updateSystemSetting(List<UpdateSystemSettingModelRequest> updateSystemSettingModelRequestList) {
        String updateSystemSettingQuery = QueriesCache.allQueries.get(Queries.UPDATE_SYSTEM_SETTING.id);
        jdbcTemplate.batchUpdate(updateSystemSettingQuery, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i)  {

                    UpdateSystemSettingModelRequest data = updateSystemSettingModelRequestList.get(i);
                try {
                    ps.setString(1, data.getValue());
                    ps.setString(2, data.getCode());
                    ps.setString(3, data.getApplicationName());
                    ps.setString(4, properties.getLabel());
                    ps.setString(5, properties.getProfile());
                } catch (SQLException ex) {
                    DailyAppLogger.DEBUG_LOGGER.error("error while execute " + updateSystemSettingQuery);
                    DailyAppLogger.ERROR_LOGGER.debug("error while execute " + updateSystemSettingQuery, ex);
                    throw new ConfigServerException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
                }
            }
            @Override
            public int getBatchSize() {
                return updateSystemSettingModelRequestList.size();
            }
        });
    }
}
