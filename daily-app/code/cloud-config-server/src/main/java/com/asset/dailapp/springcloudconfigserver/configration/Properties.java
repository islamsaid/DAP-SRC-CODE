package com.asset.dailapp.springcloudconfigserver.configration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class Properties {
    @Value ("${spring.cloud.config.server.default-profile}")
    private String profile;
    @Value ("${spring.cloud.config.server.default-label}")
    private String Label;

    public String getProfile() {
        return profile;
    }

    public String getLabel() {
        return Label;
    }


}
