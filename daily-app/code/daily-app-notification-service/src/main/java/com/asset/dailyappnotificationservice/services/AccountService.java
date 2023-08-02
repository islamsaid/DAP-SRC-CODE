package com.asset.dailyappnotificationservice.services;

import com.asset.dailyappnotificationservice.dao.AccountDao;
import com.asset.dailyappnotificationservice.defines.Defines;
import com.asset.dailyappnotificationservice.defines.ErrorCodes;
import com.asset.dailyappnotificationservice.exceptions.NotificationsException;
import com.asset.dailyappnotificationservice.logger.DailyAppLogger;
import com.asset.emailbroker.integration.model.EmailAccount;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author islam.said
 */
@Component
public class AccountService {

    @Autowired
    private AccountDao accountDao;

    public ArrayList<EmailAccount> getAllAccount() throws NotificationsException {
        DailyAppLogger.DEBUG_LOGGER.debug("Start retrieving all email account");
        ArrayList<EmailAccount> accounts = (ArrayList<EmailAccount>) accountDao.getAllAccount();
        if (accounts == null || accounts.isEmpty()) {
            DailyAppLogger.DEBUG_LOGGER.error("No accounts found");
            throw new NotificationsException(ErrorCodes.ERROR.NO_EMAIL_ACCOUNTS_FOUND, Defines.SEVERITY.ERROR, "serviceClass");
        }       
        DailyAppLogger.DEBUG_LOGGER.debug("Done retrieving all all email account");
        return accounts;
    }
}
