package com.asset.dailyappusermanagementservice.validation;

import com.asset.dailyappusermanagementservice.database.dao.imp.ProfileDaoImp;
import com.asset.dailyappusermanagementservice.defines.Defines;
import com.asset.dailyappusermanagementservice.defines.ErrorCodes;
import com.asset.dailyappusermanagementservice.exception.UserManagementException;
import com.asset.dailyappusermanagementservice.logger.DailyAppLogger;
import com.asset.dailyappusermanagementservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfileValidator {

    @Autowired
    private ProfileDaoImp profileDaoImp; //??

    @Autowired
    private UserService userService;

    public void isProfileExists(Integer profileId) throws UserManagementException {
        DailyAppLogger.DEBUG_LOGGER.debug("Checking profile existing id = " + profileId);
        if (profileId == null || ! profileExists(profileId)) {
            DailyAppLogger.DEBUG_LOGGER.debug("Validating profile failed , Profile with ID [" + profileId + "] does not exist");
            throw new UserManagementException(ErrorCodes.ERROR.PROFILE_NOT_FOUND, Defines.SEVERITY.VALIDATION);
        }
        DailyAppLogger.DEBUG_LOGGER.debug("profile exists");
    }

    public void profileHasNoUsers(Integer profileId) throws UserManagementException {
        if (profileId == null || profileHasChildren(profileId)) {
            DailyAppLogger.DEBUG_LOGGER.debug("Validating profile failed , Profile with ID [" + profileId + "] has children");
            throw new UserManagementException(ErrorCodes.ERROR.PROFILE_HAS_CHILDREN, Defines.SEVERITY.VALIDATION);
        }
    }

    private Boolean profileExists(Integer profileId) throws UserManagementException {
        DailyAppLogger.DEBUG_LOGGER.debug("Checking if profile with ID[" + profileId + "] exists in DB");
        return profileDaoImp.findProfileById(profileId) > 0;
    }
    private Boolean profileHasChildren(Integer profileId) throws UserManagementException {
        DailyAppLogger.DEBUG_LOGGER.debug("Checking if profile with ID[" + profileId + "] is assigned to users");
        return userService.retrieveUsersByProfileId(profileId) > 0;
    }

}
