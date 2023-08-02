package com.asset.loggingService.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 *
 * @author islam.said
 */
@Component
@ConfigurationProperties
@Configuration
public class PropertiesCache {

    private RollingPoolProperties rollingPoolProperties;
    private Integer maxNumberOfRetries;

    private String refreshRateForCaching;

    public String getRefreshRateForCaching() {
        return refreshRateForCaching;
    }

    public void setRefreshRateForCaching(String refreshRateForCaching) {
        this.refreshRateForCaching = refreshRateForCaching;
    }

    public RollingPoolProperties getRollingPoolProperties() {
        return rollingPoolProperties;
    }

    public void setRollingPoolProperties(RollingPoolProperties rollingPoolProperties) {
        this.rollingPoolProperties = rollingPoolProperties;
    }

    public Integer getMaxNumberOfRetries() {
        return maxNumberOfRetries;
    }

    public void setMaxNumberOfRetries(Integer maxNumberOfRetries) {
        this.maxNumberOfRetries = maxNumberOfRetries;
    }
}
