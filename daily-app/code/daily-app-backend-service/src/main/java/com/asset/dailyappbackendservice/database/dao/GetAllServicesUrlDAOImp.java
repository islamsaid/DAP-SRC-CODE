package com.asset.dailyappbackendservice.database.dao;

import com.asset.dailyappbackendservice.model.AllServiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.asset.dailyappbackendservice.logger.DailyAppLogger;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetAllServicesUrlDAOImp {
    @Autowired
    JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<AllServiceModel> getAllServiceUrBean = new BeanPropertyRowMapper<>(AllServiceModel.class);

    public List<AllServiceModel> getAllServiceUrl() {
        List<AllServiceModel>  allServiceModels= new ArrayList<>();
        String getAllServiceUrl ="select * from DAILY_SERVICES";
        DailyAppLogger.DEBUG_LOGGER.debug("Start getting  Service url " + getAllServiceUrl);
        try {
            allServiceModels=   jdbcTemplate.query(getAllServiceUrl ,getAllServiceUrBean);
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error while execute " + getAllServiceUrl,ex);
            DailyAppLogger.ERROR_LOGGER.debug("error while execute " + getAllServiceUrl, ex);
        }
        return allServiceModels;
    }

}
