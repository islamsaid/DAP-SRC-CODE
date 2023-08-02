package com.asset.dailyappnotificationservice.dao;

import com.asset.dailyappnotificationservice.constants.Queries;
import com.asset.dailyappnotificationservice.defines.Defines;
import com.asset.dailyappnotificationservice.defines.ErrorCodes;
import com.asset.dailyappnotificationservice.exceptions.NotificationsException;
import com.asset.dailyappnotificationservice.logger.DailyAppLogger;
import com.asset.dailyappnotificationservice.manager.NotificationsManager;
import com.asset.emailbroker.integration.model.EmailAccount;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author islam.said
 */
@Component
public class AccountDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;  

    private final BeanPropertyRowMapper<EmailAccount> accountMapper = new BeanPropertyRowMapper<>(EmailAccount.class);
    
    public List<EmailAccount> getAllAccount() {
        String getAllAccountQuery = null;
        try {
            getAllAccountQuery = NotificationsManager.queries.get(Queries.GET_ACCOUNTS.id);
            return jdbcTemplate.query(getAllAccountQuery, accountMapper);
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.debug("Query#{} = {}", Queries.GET_ACCOUNTS.id, getAllAccountQuery);
            DailyAppLogger.DEBUG_LOGGER.error("error getting all accounts from db ", ex);
            DailyAppLogger.ERROR_LOGGER.error("error while execute " + getAllAccountQuery, ex);
            ex.printStackTrace();
            throw new NotificationsException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }
}
