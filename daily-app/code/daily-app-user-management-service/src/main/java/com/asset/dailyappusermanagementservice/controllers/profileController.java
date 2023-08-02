package com.asset.dailyappusermanagementservice.controllers;

import com.asset.dailyappusermanagementservice.defines.Defines;
import com.asset.dailyappusermanagementservice.defines.ErrorCodes;
import com.asset.dailyappusermanagementservice.exception.UserManagementException;
import com.asset.dailyappusermanagementservice.logger.DailyAppLogger;
import com.asset.dailyappusermanagementservice.models.responses.BaseResponseForAddObject;
import com.asset.dailyappusermanagementservice.models.request.profile.*;
import com.asset.dailyappusermanagementservice.models.responses.profile.DeleteProfileRequest;
import com.asset.dailyappusermanagementservice.models.responses.profile.GetAllProfilesPrivilegesResponse;
import com.asset.dailyappusermanagementservice.models.responses.profile.GetAllProfilesResponse;
import com.asset.dailyappusermanagementservice.models.responses.profile.GetProfileResponse;
import com.asset.dailyappusermanagementservice.models.responses.BaseResponse;
import com.asset.dailyappusermanagementservice.models.user.UserProfileModel;
import com.asset.dailyappusermanagementservice.service.ProfileService;
import com.asset.dailyappusermanagementservice.validation.ProfileValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = Defines.ContextPaths.PROFILE)
public class profileController {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ProfileValidator profileValidator;

    @RequestMapping(value = Defines.WEB_ACTIONS.ADD, method = RequestMethod.POST)
    public BaseResponse<BaseResponseForAddObject> add(HttpServletRequest request, @RequestBody AddProfileRequest addRequest) throws UserManagementException {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        DailyAppLogger.DEBUG_LOGGER.info("Add profile request has been received");
        String token = request.getHeader("Authorization");

        UserProfileModel profile = addRequest.getProfile();
        profile.setToken(token);
        DailyAppLogger.DEBUG_LOGGER.info("new profile: {} ", profile);
        Integer profileId = profileService.addProfile(profile);
        DailyAppLogger.DEBUG_LOGGER.info("Done adding profile request");

        BaseResponseForAddObject baseResponseForAddObject = new BaseResponseForAddObject(String.valueOf(profileId));
        ThreadContext.remove("traceId");
        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, baseResponseForAddObject);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.GET, method = RequestMethod.POST)
    public BaseResponse<GetProfileResponse> get(@RequestBody GetProfileRequest getRequest) throws UserManagementException {
        DailyAppLogger.DEBUG_LOGGER.info("GET profile request has been received");

        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        Integer profileId = getRequest.getProfileId();
        DailyAppLogger.DEBUG_LOGGER.debug("Received get profile of id=" + profileId);
        GetProfileResponse resp = profileService.retrieveProfile(profileId);
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, resp);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.GET_ALL, method = RequestMethod.POST)
    public BaseResponse<GetAllProfilesResponse> getAll() throws UserManagementException {
        DailyAppLogger.DEBUG_LOGGER.info("GET All profiles request has been received");

        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        GetAllProfilesResponse resp = profileService.retrieveAllProfiles();
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, resp);

    }

    @RequestMapping(value = Defines.WEB_ACTIONS.GET_ALL_PRIVILEGES, method = RequestMethod.POST)
    public BaseResponse<GetAllProfilesPrivilegesResponse> getAllWithPrivileges() throws UserManagementException {
        DailyAppLogger.DEBUG_LOGGER.info("GET All profiles with privileges request has been received");

        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        HashMap<Integer, UserProfileModel> profiles = profileService.retrieveAllProfilesWithPrivileges();
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, new GetAllProfilesPrivilegesResponse(profiles));
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.DELETE, method = RequestMethod.POST)
    public BaseResponse delete(@RequestBody DeleteProfileRequest deleteRequest) throws UserManagementException {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        DailyAppLogger.DEBUG_LOGGER.debug("Received delete profile with id = [" + deleteRequest.getProfileId() + "]");
        Integer profileId = deleteRequest.getProfileId();
        profileValidator.isProfileExists(profileId);
        profileValidator.profileHasNoUsers(profileId);
        profileService.deleteProfile(profileId);
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, null);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.UPDATE, method = RequestMethod.POST)
    public BaseResponse update(@RequestBody UpdateProfileRequest updateRequest) throws UserManagementException {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        DailyAppLogger.DEBUG_LOGGER.info("Received update profile request [" + updateRequest + "]");
        UserProfileModel profile = updateRequest.getProfile();
        profileValidator.isProfileExists(profile.getId());
        profileService.updateProfile(profile);
        DailyAppLogger.DEBUG_LOGGER.info("Update profile request finished successfully");
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, null);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.ADD_PRIVILEGE, method = RequestMethod.POST)
    public BaseResponse addPrivilegesToProfile(@RequestBody AddPrivilegesProfileRequest addPrivilegesProfileRequest) throws UserManagementException {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        DailyAppLogger.DEBUG_LOGGER.info("add privileges to profile request [" + addPrivilegesProfileRequest + "]");
        profileValidator.isProfileExists(addPrivilegesProfileRequest.getProfileId());
        profileService.AddProfilePrivileges(addPrivilegesProfileRequest);
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, null);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.DELETE_PRIVILEGE, method = RequestMethod.POST)
    public BaseResponse deletePrivilegesToProfile(@RequestBody DeleteProfilePrivilegesRequest addPrivilegeRequest) throws UserManagementException {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        DailyAppLogger.DEBUG_LOGGER.info("Received update profile request [" + addPrivilegeRequest + "]");
        // validations
        profileValidator.isProfileExists(addPrivilegeRequest.getProfileId());
        profileService.DeleteProfilePrivileges(addPrivilegeRequest);
        DailyAppLogger.DEBUG_LOGGER.info("Update profile request finished successfully");
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, null);
    }
}
