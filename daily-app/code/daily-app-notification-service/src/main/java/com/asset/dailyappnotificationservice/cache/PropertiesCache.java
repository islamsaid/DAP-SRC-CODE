package com.asset.dailyappnotificationservice.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author islam.said
 */
@ConfigurationProperties
@Configuration
public class PropertiesCache {

    private RollingPoolProperties rollingPoolProperties;
    private Integer maxNumberOfRetries;
    private Integer refreshRate;
    private Integer corePoolSize;
    private Integer emailQueueSize;
    private Integer emailDequeuingMaxBatchSize;
    private Integer notificationLifetimeInDays;

    private String emailSenderAddress;
    private String emailReceivers;
    private boolean sendEmailFlag;

    public boolean getSendEmailFlag() {
        return sendEmailFlag;
    }

    public void setSendEmailFlag(boolean sendEmailFlag) {
        this.sendEmailFlag = sendEmailFlag;
    }

    public String getEmailSenderAddress() {
        return emailSenderAddress;
    }

    public void setEmailSenderAddress(String emailSenderAddress) {
        this.emailSenderAddress = emailSenderAddress;
    }

    public String getEmailReceivers() {
        return emailReceivers;
    }

    public void setEmailReceivers(String emailReceivers) {
        this.emailReceivers = emailReceivers;
    }
    public Integer getNotificationLifetimeInDays() {
        return notificationLifetimeInDays;
    }

    public void setNotificationLifetimeInDays(Integer notificationLifetimeInDays) {
        this.notificationLifetimeInDays = notificationLifetimeInDays;
    }

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
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

    public Integer getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(Integer refreshRate) {
        this.refreshRate = refreshRate;
    }

    public Integer getEmailQueueSize() {
        return emailQueueSize;
    }

    public void setEmailQueueSize(Integer emailQueueSize) {
        this.emailQueueSize = emailQueueSize;
    }

    public Integer getEmailDequeuingMaxBatchSize() {
        return emailDequeuingMaxBatchSize;
    }

    public void setEmailDequeuingMaxBatchSize(Integer emailDequeuingMaxBatchSize) {
        this.emailDequeuingMaxBatchSize = emailDequeuingMaxBatchSize;
    }

}
