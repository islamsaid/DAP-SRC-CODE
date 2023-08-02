package com.asset.dailyappusermanagementservice.service;

import com.asset.dailyappusermanagementservice.database.dao.imp.PrivilegeDAOImp;
import com.asset.dailyappusermanagementservice.defines.Defines;
import com.asset.dailyappusermanagementservice.defines.ErrorCodes;
import com.asset.dailyappusermanagementservice.exception.UserManagementException;
import com.asset.dailyappusermanagementservice.logger.DailyAppLogger;
import com.asset.dailyappusermanagementservice.models.responses.privilege.GetAllPrivilegeResponse;
import com.asset.dailyappusermanagementservice.models.responses.privilege.GetPrivilegeResponse;
import com.asset.dailyappusermanagementservice.models.shared.LkPrivilegeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrivilegeService {
    @Autowired
    PrivilegeDAOImp privilegeDAOImp;


    public void addPrivilege(LkPrivilegeModel privilege) throws UserManagementException {
        DailyAppLogger.DEBUG_LOGGER.debug("Adding privilege");
        Integer privilegeId = privilegeDAOImp.add(privilege);
        DailyAppLogger.DEBUG_LOGGER.debug("New privilege id =" + privilegeId);
        if (privilegeId == null) {
            DailyAppLogger.DEBUG_LOGGER.error("Failed to add privilege");
            throw new UserManagementException(ErrorCodes.ERROR.UNKNOWN_ERROR, Defines.SEVERITY.ERROR, "privilege");
        }

    }


    public GetPrivilegeResponse retrievePrivilege(Integer privilegeId) throws UserManagementException {
        LkPrivilegeModel privilegeModel = privilegeDAOImp.get(privilegeId);
        DailyAppLogger.DEBUG_LOGGER.info(privilegeModel);
        if (privilegeModel == null) {
            DailyAppLogger.DEBUG_LOGGER.error("privilege with id [" + privilegeId + "] was not found");
            DailyAppLogger.ERROR_LOGGER.error("privilege with id [" + privilegeId + "] was not found");
            throw new UserManagementException(ErrorCodes.ERROR.PRIVILEGE_NOT_FOUND, Defines.SEVERITY.ERROR);
        }
        return new GetPrivilegeResponse(privilegeModel);
    }

    public GetAllPrivilegeResponse retrieveAllPrivilege() throws UserManagementException {
        List<LkPrivilegeModel> privileges = privilegeDAOImp.getAll();
        DailyAppLogger.DEBUG_LOGGER.debug("#Retrieved privileges = " + privileges.size());
        if (privileges == null || privileges.isEmpty()) {
            DailyAppLogger.DEBUG_LOGGER.error("No privileges found");
            throw new UserManagementException(ErrorCodes.ERROR.PRIVILEGE_NOT_FOUND, Defines.SEVERITY.ERROR);
        }
        return new GetAllPrivilegeResponse(privileges);
    }


    public void deletePrivilege(Integer privilegeId) throws UserManagementException {
        int deleted = privilegeDAOImp.delete(privilegeId);
        if (deleted <= 0) {
            DailyAppLogger.DEBUG_LOGGER.error("Failed to delete privilege");
            throw new UserManagementException(ErrorCodes.ERROR.DELETE_FAILED, Defines.SEVERITY.ERROR, "privilege");
        }
        DailyAppLogger.DEBUG_LOGGER.debug("deleted successfully");
    }

    public void updatePrivilege(LkPrivilegeModel privilege) {
        int updated = privilegeDAOImp.update(privilege);
        if (updated <= 0) {
            DailyAppLogger.DEBUG_LOGGER.error("Failed to update privilege");
            throw new UserManagementException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "privilege");
        }
        DailyAppLogger.DEBUG_LOGGER.debug("Updated privilege successfully");
    }

    public Integer retrieveProfileByPrivilegeId(Integer privilegeId) {
        DailyAppLogger.DEBUG_LOGGER.debug("Retrieving users with profile ID [" + privilegeId + "]");
        return privilegeDAOImp.retrieveProfileByPrivilegeId(privilegeId);
    }
}
