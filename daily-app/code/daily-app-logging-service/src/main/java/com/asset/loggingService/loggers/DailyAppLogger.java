package com.asset.loggingService.loggers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DailyAppLogger {

    public static final Logger DEBUG_LOGGER = LogManager.getLogger("debugLogger");
    public static final Logger INFO_LOGGER = LogManager.getLogger("infoLogger");
    public static final Logger ERROR_LOGGER = LogManager.getLogger("errorLogger");
    public static final Logger INTERFACE_LOGGER = LogManager.getLogger("interfaceLogger");

    public static void error(String msg, Throwable e) {
        msg = format(msg);
        DEBUG_LOGGER.error(msg);
        ERROR_LOGGER.error(msg, e);
    }

    private static String format(String msg) {
        StringBuilder formattedMsg = new StringBuilder();
        StackTraceElement ste = getCallerFromStack();
        formattedMsg.append("[")
                .append(ste.getClassName().substring(ste.getClassName().lastIndexOf(".")))
                .append(".")
                .append(ste.getMethodName())
                .append("]")
                .append(msg);
        return formattedMsg.toString();

    }
    private static StackTraceElement getCallerFromStack(){
        return Thread.currentThread().getStackTrace()[4];
    }

}
