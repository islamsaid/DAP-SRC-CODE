package com.asset.dailyappreportservice.util;

import com.asset.dailyappreportservice.defines.Defines;
import com.asset.dailyappreportservice.logger.DailyAppLogger;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Component
public class Utils {
    public static String convertEpochToDate(long epochTime) { //DD/MM/YYYY'
        Date date = new Date(epochTime);
        SimpleDateFormat sdf = new SimpleDateFormat(Defines.FORMATS.DATE_FORMAT);
        String formatted = sdf.format(date);
        return formatted;
    }

    public String getCurrentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Defines.FORMATS.DATE_FORMAT);
        return dtf.format(LocalDateTime.now());
    }
}
