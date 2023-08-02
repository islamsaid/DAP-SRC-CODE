package com.asset.dailyappusermanagementservice.service;

import com.asset.dailyappusermanagementservice.configurations.Properties;
import com.asset.dailyappusermanagementservice.defines.ErrorCodes;
import com.asset.dailyappusermanagementservice.exception.UserManagementException;
import com.asset.dailyappusermanagementservice.logger.DailyAppLogger;
import com.asset.ldap.common.LDAPIntegration;
import com.asset.ldap.model.LdapUserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import com.asset.ldap.util.Util;

@Component
public class LdapService {
    @Autowired
    Properties properties;

    public LdapUserModel  authenticateUser(String username, String credential) throws UserManagementException {

        LdapUserModel userModel = null;
        String[] urls = properties.getLdapServerUrl().split(",");
        String domain = properties.getLdapDomainName();
        String searchBase = properties.getLdapUsersSearchBase();
        for (String url : urls) {
            try {
                LDAPIntegration ldapIntegration = new LDAPIntegration(url, domain);

                userModel = ldapIntegration.authenticateUser(username, credential, searchBase);
                if (userModel != null) {
                    return userModel;
                }
            } catch (Exception e) {
                String msg = "Exception in UserService.authenticateUser()";
                DailyAppLogger.DEBUG_LOGGER.info(msg + " IS " + e.getMessage());
                DailyAppLogger.ERROR_LOGGER.error(msg + " IS " + e.getMessage(), e);
            }
        }
        throw new UserManagementException(ErrorCodes.ERROR.LDAP_AUTH_FAILED);
    }

}
