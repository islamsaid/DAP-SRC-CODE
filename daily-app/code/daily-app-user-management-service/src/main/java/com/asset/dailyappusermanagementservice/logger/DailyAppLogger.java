package com.asset.dailyappusermanagementservice.logger;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;

public class DailyAppLogger {
    public static final Logger INFO_LOGGER = LogManager.getLogger("infoLogger");
    public static final Logger DEBUG_LOGGER = LogManager.getLogger("debugLogger");
    public static final Logger ERROR_LOGGER = LogManager.getLogger("errorLogger");


    private static String format(String msg) {
        StringBuilder formattedMsg = new StringBuilder();
        StackTraceElement ste = getCallerFromStack();
        formattedMsg.append("[")
                .append(ste.getClassName().substring(ste.getClassName().lastIndexOf(".") + 1))
                .append(".")
                .append(ste.getMethodName())
                .append("] ")
                .append(msg);
        return formattedMsg.toString();
    }

    private static StackTraceElement getCallerFromStack() {
        return Thread.currentThread().getStackTrace()[4];
    }
}
