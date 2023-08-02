package com.asset.loggingService.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
@ConfigurationProperties
@PropertySource("classpath:Messages.properties")
public class CacheMessages {
    private  final Map<String, Map<Integer, String>> MESSAGES_MAP = new HashMap<>();

    public Map<String, Map<Integer, String>> getCacheMessages() {
        return MESSAGES_MAP;
    }

    public String getSuccessMsg(Integer code) {

        if (MESSAGES_MAP != null && MESSAGES_MAP.get("success") != null
                && MESSAGES_MAP.get("success").get(code) != null) {
            return MESSAGES_MAP.get("success").get(code);
        }
        return "";
    }
    public String replaceArgument(String msg, String arg) {

        if (msg != null && !msg.isBlank()) {

            if (arg.contains(",")) {
                msg = msg.replace("$1", arg.split(",")[0]);
                return msg.replace("$2", arg.split(",")[1]);
            } else {
                return msg.replace("$1", arg);
            }
        }
        return "";
    }

    public  String getErrorMsg(Integer code) {

        if (MESSAGES_MAP != null && MESSAGES_MAP.get("error") != null
                && MESSAGES_MAP.get("error").get(code) != null) {
            return MESSAGES_MAP.get("error").get(code);
        }
        return "";
    }

}
