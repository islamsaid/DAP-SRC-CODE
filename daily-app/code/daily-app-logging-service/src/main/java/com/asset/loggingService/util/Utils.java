package com.asset.loggingService.util;

import com.asset.loggingService.defines.Defines;
import com.asset.loggingService.loggers.DailyAppLogger;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

@Component
public class Utils {
    public String convertEpochToDate(long epochTime) { //DD/MM/YYYY'
        DailyAppLogger.DEBUG_LOGGER.debug("convert echo date tp date format ddd/mm/yyyy , echo"+ epochTime);
        Date date = new Date(epochTime);
         SimpleDateFormat sdf = new SimpleDateFormat(Defines.FORMATS.DATE_FORMAT, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("Africa/Cairo"));
        DailyAppLogger.DEBUG_LOGGER.debug("SimpleDateFormat :"+ sdf);

        String formatted = sdf.format(date);
        DailyAppLogger.DEBUG_LOGGER.debug("date after format :"+ formatted);
        DailyAppLogger.DEBUG_LOGGER.debug("date zone:"+ sdf.getTimeZone());

        return formatted;
    }
}
