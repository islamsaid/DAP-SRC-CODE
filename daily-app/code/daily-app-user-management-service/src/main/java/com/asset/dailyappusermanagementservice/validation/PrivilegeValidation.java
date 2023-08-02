package com.asset.dailyappusermanagementservice.validation;

import com.asset.dailyappusermanagementservice.database.dao.imp.PrivilegeDAOImp;
import com.asset.dailyappusermanagementservice.defines.Defines;
import com.asset.dailyappusermanagementservice.defines.ErrorCodes;
import com.asset.dailyappusermanagementservice.exception.UserManagementException;
import com.asset.dailyappusermanagementservice.logger.DailyAppLogger;
import com.asset.dailyappusermanagementservice.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrivilegeValidation {

    @Autowired
    private PrivilegeDAOImp privilegeDAOImp;
    @Autowired
    private PrivilegeService  privilegeService;

    public void isPrivilegeExists(Integer privilegeId) throws UserManagementException {
        DailyAppLogger.DEBUG_LOGGER.info("Start validating if Privilege[" + privilegeId + "] exists");
        if (privilegeId == 0 || ! privilegeExists(privilegeId)) {
            DailyAppLogger.DEBUG_LOGGER.debug("Validating Privilege failed , Privilege with ID [" + privilegeId + "] does not exist");
            throw new UserManagementException(ErrorCodes.ERROR.PRIVILEGE_NOT_FOUND, Defines.SEVERITY.VALIDATION);
        }
    }
    public void privilegeHasNoProfile(Integer privilegeId) throws UserManagementException {
        DailyAppLogger.DEBUG_LOGGER.info("validating privilege[" + privilegeId + "] has no children");
        if (privilegeId == null || privilegeHasChildren(privilegeId)) {
            DailyAppLogger.DEBUG_LOGGER.debug("Validating privilege failed , privilege with ID [" + privilegeId + "] has children");
            throw new UserManagementException(ErrorCodes.ERROR.PRIVILEGE_HAS_CHILDREN, Defines.SEVERITY.VALIDATION);
        }
    }

    private Boolean privilegeExists(Integer privilegeId) throws UserManagementException {
        DailyAppLogger.DEBUG_LOGGER.debug("Checking if privilege with ID[" + privilegeId + "] exists in DB");
        return privilegeDAOImp.findPrivilegeById(privilegeId) > 0;
    }

    private Boolean privilegeHasChildren(Integer PrivilegeId) throws UserManagementException {
        DailyAppLogger.DEBUG_LOGGER.debug("Checking if Privilege with ID[" + PrivilegeId + "] is assigned to users");
        return privilegeService.retrieveProfileByPrivilegeId(PrivilegeId) > 0;
    }
}
