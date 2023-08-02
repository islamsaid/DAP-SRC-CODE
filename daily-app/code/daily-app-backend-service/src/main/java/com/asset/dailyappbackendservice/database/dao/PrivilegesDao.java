package com.asset.dailyappbackendservice.database.dao;

import com.asset.dailyappbackendservice.logger.DailyAppLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PrivilegesDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> getPrivilegeURLsByProfileId(Integer profileId) {
        try {
            String sqlQuery = "SELECT BACK_END_URL  FROM DAILY_PRIVILEGES dp " +
                    "INNER JOIN DAILY_PROFILE_PRIVILEGES dpp ON dp.ID = dpp.PRIVILEGES_ID " +
                    "INNER JOIN DAILY_PROFILES prof ON PROF.ID = dpp.PROFILE_ID " +
                    "WHERE PROF.ID = ?";
            DailyAppLogger.DEBUG_LOGGER.debug("get privilege urls query = " + sqlQuery);
            return jdbcTemplate.query(sqlQuery, rs -> {
                List<String> urls = new ArrayList<>();
                while (rs.next()){
                    urls.add(rs.getString("BACK_END_URL"));
                }
                return urls;
            }, profileId);
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("Error getting privilege URLs from db", ex);
            DailyAppLogger.ERROR_LOGGER.error("Error getting privilege URLs from db", ex);
            ex.printStackTrace();
            return null;
        }
    }
}
