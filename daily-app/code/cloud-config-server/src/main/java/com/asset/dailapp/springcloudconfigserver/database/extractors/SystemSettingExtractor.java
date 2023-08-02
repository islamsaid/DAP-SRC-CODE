package com.asset.dailapp.springcloudconfigserver.database.extractors;

import com.asset.dailapp.springcloudconfigserver.configration.Properties;
import com.asset.dailapp.springcloudconfigserver.defines.DatabaseStructs;
import com.asset.dailapp.springcloudconfigserver.model.SystemSettingModelForApplication;
import com.asset.dailapp.springcloudconfigserver.model.response.SystemSettingModelResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class SystemSettingExtractor implements ResultSetExtractor<HashMap<String , List<SystemSettingModelForApplication>> > {


    @Override
    public HashMap<String , List<SystemSettingModelForApplication>>  extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        SystemSettingModelForApplication systemSettingModelForApplication = null;
        HashMap<String ,List<SystemSettingModelForApplication>>  systemSettingModelForApplicationHashMap= new HashMap<>();
        while (resultSet.next()){
            String applicationSysSetting= resultSet.getString(DatabaseStructs.DAILY_SYSTEM_PROPERTIES.APPLICATION) ;
            systemSettingModelForApplication = new SystemSettingModelForApplication();

            systemSettingModelForApplication.setCode(resultSet.getString(DatabaseStructs.DAILY_SYSTEM_PROPERTIES.CODE));
            systemSettingModelForApplication.setDescription(resultSet.getString(DatabaseStructs.DAILY_SYSTEM_PROPERTIES.DESCRIPTION));
            systemSettingModelForApplication.setValue(resultSet.getString(DatabaseStructs.DAILY_SYSTEM_PROPERTIES.VALUE));
            systemSettingModelForApplication.setType(resultSet.getInt(DatabaseStructs.DAILY_SYSTEM_PROPERTIES.TYPE));

            if(!systemSettingModelForApplicationHashMap.containsKey(applicationSysSetting)) {
                systemSettingModelForApplicationHashMap.put(applicationSysSetting,new ArrayList<>());
            }
            systemSettingModelForApplicationHashMap.get(applicationSysSetting).add(systemSettingModelForApplication);
        }
        return systemSettingModelForApplicationHashMap;
    }
}
