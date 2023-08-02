package com.asset.dailyappusermanagementservice.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class Properties {
    private String accessTokenKey;
    private double accessTokenValidity;
    private Boolean ldapAuthenticationFlag;
    private String ldapDomainName;
    private String ldapServerUrl;
    private String ldapUsersSearchBase;

    public String getAccessTokenKey() {
        return accessTokenKey;
    }

    public void setAccessTokenKey(String accessTokenKey) {
        this.accessTokenKey = accessTokenKey;
    }

    public double getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(double accessTokenValidityHours) {
        this.accessTokenValidity = accessTokenValidityHours;
    }


    public Boolean getLdapAuthenticationFlag() {
        return ldapAuthenticationFlag;
    }

    public void setLdapAuthenticationFlag(Boolean ldapAuthenticationFlag) {
        this.ldapAuthenticationFlag = ldapAuthenticationFlag;
    }

    public String getLdapDomainName() {
        return ldapDomainName;
    }

    public void setLdapDomainName(String ldapDomainName) {
        this.ldapDomainName = ldapDomainName;
    }

    public String getLdapServerUrl() {
        return ldapServerUrl;
    }

    public void setLdapServerUrl(String ldapServerUrl) {
        this.ldapServerUrl = ldapServerUrl;
    }

    public String getLdapUsersSearchBase() {
        return ldapUsersSearchBase;
    }

    public void setLdapUsersSearchBase(String ldapUsersSearchBase) {
        this.ldapUsersSearchBase = ldapUsersSearchBase;
    }
}
