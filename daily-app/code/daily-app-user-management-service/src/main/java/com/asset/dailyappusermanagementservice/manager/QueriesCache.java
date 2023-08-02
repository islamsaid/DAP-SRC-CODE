package com.asset.dailyappusermanagementservice.manager;

import com.asset.dailyappusermanagementservice.database.dao.imp.QueriesCacheDAOImp;
import com.asset.dailyappusermanagementservice.defines.Defines;
import com.asset.dailyappusermanagementservice.defines.ErrorCodes;
import com.asset.dailyappusermanagementservice.exception.UserManagementException;
import com.asset.dailyappusermanagementservice.logger.DailyAppLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
public class QueriesCache {

    public static HashMap<Integer, String> allQueries;//cac
    @Autowired
    private QueriesCacheDAOImp queriesCacheDAOImp;

    @EventListener
    private void readeAllQueries(ApplicationReadyEvent event) throws IOException {
        try {
            DailyAppLogger.DEBUG_LOGGER.debug("Start Latest Packages ");
            DailyAppLogger.DEBUG_LOGGER.debug("Start retrieving all Queries from database");
            allQueries = queriesCacheDAOImp.getAll();
            if (allQueries == null || allQueries.isEmpty()) {
                DailyAppLogger.DEBUG_LOGGER.error("No privileges found");
                throw new UserManagementException(ErrorCodes.ERROR.QUERIES_NOT_FOUND, Defines.SEVERITY.ERROR);

            }
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error while execute " + ex);
            DailyAppLogger.ERROR_LOGGER.error("error while execute " + ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());

        }

    }
}
