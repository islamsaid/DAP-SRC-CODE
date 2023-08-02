package com.asset.dailyappusermanagementservice.controllers;

import com.asset.dailyappusermanagementservice.defines.Defines;
import com.asset.dailyappusermanagementservice.defines.ErrorCodes;
import com.asset.dailyappusermanagementservice.exception.UserManagementException;
import com.asset.dailyappusermanagementservice.logger.DailyAppLogger;
import com.asset.dailyappusermanagementservice.models.request.privilege.AddPrivilegeRequest;
import com.asset.dailyappusermanagementservice.models.request.privilege.DeletePrivilegeRequest;
import com.asset.dailyappusermanagementservice.models.request.privilege.GetPrivilegeRequest;
import com.asset.dailyappusermanagementservice.models.request.privilege.UpdatePrivilegeRequest;
import com.asset.dailyappusermanagementservice.models.responses.BaseResponse;
import com.asset.dailyappusermanagementservice.models.responses.privilege.GetAllPrivilegeResponse;
import com.asset.dailyappusermanagementservice.models.responses.privilege.GetPrivilegeResponse;
import com.asset.dailyappusermanagementservice.models.shared.LkPrivilegeModel;
import com.asset.dailyappusermanagementservice.service.PrivilegeService;
import com.asset.dailyappusermanagementservice.validation.PrivilegeValidation;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = Defines.ContextPaths.PRIVILEGE)
public class PrivilegeController {
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private PrivilegeValidation privilegeValidation;

    @RequestMapping(value = Defines.WEB_ACTIONS.ADD, method = RequestMethod.POST)
    public BaseResponse add(@RequestBody AddPrivilegeRequest addRequest) throws UserManagementException {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        DailyAppLogger.DEBUG_LOGGER.info("Received add Privilege request [" + addRequest.toString() + "]");
        LkPrivilegeModel privilege = addRequest.getPrivilege();
        privilegeService.addPrivilege(privilege);
        ThreadContext.remove("traceId");
        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, null);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.GET, method = RequestMethod.POST)
    public BaseResponse<GetPrivilegeResponse> get(@RequestBody GetPrivilegeRequest getRequest) throws UserManagementException {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        DailyAppLogger.DEBUG_LOGGER.info("Received get privilege id = [" + getRequest.getPrivilegeId() + "]");
        Integer PrivilegeId = getRequest.getPrivilegeId();
        GetPrivilegeResponse resp = privilegeService.retrievePrivilege(PrivilegeId);
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, resp);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.GET_ALL, method = RequestMethod.POST)
    public BaseResponse<GetAllPrivilegeResponse> getAll() throws UserManagementException {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        GetAllPrivilegeResponse resp = privilegeService.retrieveAllPrivilege();
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, resp);

    }

    @RequestMapping(value = Defines.WEB_ACTIONS.DELETE, method = RequestMethod.POST)
    public BaseResponse delete(@RequestBody DeletePrivilegeRequest deleteRequest) throws UserManagementException {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        DailyAppLogger.DEBUG_LOGGER.info("Received delete privilege id = [" + deleteRequest.getPrivilegeId() + "]");
        Integer privilegeId = deleteRequest.getPrivilegeId();
        privilegeValidation.privilegeHasNoProfile(deleteRequest.getPrivilegeId());
        privilegeService.deletePrivilege(privilegeId);
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, null);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.UPDATE, method = RequestMethod.POST)
    public BaseResponse update(@RequestBody UpdatePrivilegeRequest updateRequest) throws UserManagementException {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        DailyAppLogger.DEBUG_LOGGER.info("Received update privilege request [" + updateRequest + "]");
        LkPrivilegeModel privilege = updateRequest.getPrivilege();
        privilegeValidation.isPrivilegeExists(privilege.getId());
        privilegeService.updatePrivilege(privilege);
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, null);
    }

}
