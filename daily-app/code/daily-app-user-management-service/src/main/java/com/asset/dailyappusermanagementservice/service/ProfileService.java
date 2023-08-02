package com.asset.dailyappusermanagementservice.service;

import com.asset.dailyappusermanagementservice.database.dao.imp.ProfileDaoImp;
import com.asset.dailyappusermanagementservice.database.dao.imp.UsersDaoImp;
import com.asset.dailyappusermanagementservice.defines.Defines;
import com.asset.dailyappusermanagementservice.defines.ErrorCodes;
import com.asset.dailyappusermanagementservice.exception.UserManagementException;
import com.asset.dailyappusermanagementservice.logger.DailyAppLogger;
import com.asset.dailyappusermanagementservice.models.request.profile.AddPrivilegesProfileRequest;
import com.asset.dailyappusermanagementservice.models.request.profile.DeleteProfilePrivilegesRequest;
import com.asset.dailyappusermanagementservice.models.responses.profile.GetAllProfilesResponse;
import com.asset.dailyappusermanagementservice.models.responses.profile.GetProfileResponse;
import com.asset.dailyappusermanagementservice.models.shared.LkPrivilegeModel;
import com.asset.dailyappusermanagementservice.models.user.UserModel;
import com.asset.dailyappusermanagementservice.models.user.UserProfileModel;
import com.asset.dailyappusermanagementservice.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class ProfileService {
    @Autowired
    private ProfileDaoImp profileDaoImp;

    @Autowired
    private UsersDaoImp usersDaoImp;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    public int addProfile(UserProfileModel profile) throws UserManagementException {
        DailyAppLogger.DEBUG_LOGGER.debug("Extracting user data from token");
        UserModel user = jwtTokenUtil.getUserModelFromToken(profile.getToken()); //todo  service
        profile.setCreatedBY(user.getUserId());
        DailyAppLogger.DEBUG_LOGGER.debug("created by user id = " + user.getUserId());
        Integer profileId = profileDaoImp.add(profile);

        if (profileId == null) {
            DailyAppLogger.DEBUG_LOGGER.debug("Failed to add profile");
            throw new UserManagementException(ErrorCodes.ERROR.UNKNOWN_ERROR, Defines.SEVERITY.ERROR, "profile");
        }
        profileDaoImp.addProfileFeatures(profileId, profile.getPrivileges());

        return profileId;
    }

    public GetProfileResponse retrieveProfile(Integer profileId) throws UserManagementException {
        DailyAppLogger.DEBUG_LOGGER.debug("start retrieving profile of id [{}]", profileId);
        UserProfileModel profile = profileDaoImp.get(profileId);

        if (profile == null) {
            DailyAppLogger.DEBUG_LOGGER.debug("Profile with id [" + profileId + "] was not found");
            throw new UserManagementException(ErrorCodes.ERROR.PROFILE_NOT_FOUND, Defines.SEVERITY.ERROR);
        }
        DailyAppLogger.DEBUG_LOGGER.info(profile);
        return new GetProfileResponse(profile);
    }

    public HashMap<Integer, UserProfileModel> retrieveAllProfilesWithPrivileges() throws UserManagementException {
        HashMap<Integer, UserProfileModel> profilesMap = profileDaoImp.retrieveProfilesWithFeatures();
        if (profilesMap == null || profilesMap.isEmpty()) {
            DailyAppLogger.DEBUG_LOGGER.error("No profiles found");
            DailyAppLogger.ERROR_LOGGER.error("No profiles found");
            throw new UserManagementException(ErrorCodes.ERROR.NO_PROFILES_FOUND, Defines.SEVERITY.FATAL);
        }

        DailyAppLogger.DEBUG_LOGGER.debug("Done -- #Retrieved {} profiles", profilesMap.size());
        return profilesMap;
    }

    public GetAllProfilesResponse retrieveAllProfiles() throws UserManagementException {
        List<UserProfileModel> profiles = profileDaoImp.getAll();
        if (profiles == null || profiles.isEmpty()) {
            DailyAppLogger.DEBUG_LOGGER.error("No profiles found");
            DailyAppLogger.ERROR_LOGGER.error("No profiles found");
            throw new UserManagementException(ErrorCodes.ERROR.NO_PROFILES_FOUND, Defines.SEVERITY.ERROR);
        }
        DailyAppLogger.DEBUG_LOGGER.debug("Done -- #Retrieved profiles = {}", profiles.size());
        return new GetAllProfilesResponse(profiles);
    }

    public void deleteProfile(Integer profileId) throws UserManagementException {
        DailyAppLogger.DEBUG_LOGGER.debug("Start deleting profile features");
        profileDaoImp.deleteProfileFeatures(profileId);
        DailyAppLogger.DEBUG_LOGGER.debug("Start deleting profile definition");
        profileDaoImp.delete(profileId);
    }

    public void updateProfile(UserProfileModel profile) throws UserManagementException {
        DailyAppLogger.DEBUG_LOGGER.debug("Start updating profile [" + profile.getName() + "]");
        int updated = profileDaoImp.update(profile);

        if (updated <= 0) {
            DailyAppLogger.DEBUG_LOGGER.error("Failed to update profile");
            throw new UserManagementException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "profile");
        }
        if (profile.getPrivileges() != null) {
            DailyAppLogger.DEBUG_LOGGER.debug("Start syncing profile features");
            updatePrivilege(profile);

        }
        DailyAppLogger.DEBUG_LOGGER.debug("Updated profile successfully");
    }

    public void AddProfilePrivileges(AddPrivilegesProfileRequest addPrivilegesProfileRequest) throws UserManagementException {
        profileDaoImp.addProfileFeatures(addPrivilegesProfileRequest.getProfileId(),
                addPrivilegesProfileRequest.getPrivileges());
    }

    public void DeleteProfilePrivileges(DeleteProfilePrivilegesRequest deleteProfilePrivilegesRequest) throws UserManagementException {
        profileDaoImp.deleteProfileFeatures(deleteProfilePrivilegesRequest.getProfileId(),
                deleteProfilePrivilegesRequest.getPrivileges());
    }

    private void updatePrivilege(UserProfileModel profile) throws UserManagementException {
        DailyAppLogger.DEBUG_LOGGER.debug("Revoking old profile features...");
        int deleted = profileDaoImp.deleteProfileFeatures(profile.getId());
        ArrayList<LkPrivilegeModel> features = new ArrayList<>();
        if (profile.getPrivileges() != null && !profile.getPrivileges().isEmpty()) {
            features.addAll(profile.getPrivileges());
        }
        if (!features.isEmpty()) {
            DailyAppLogger.DEBUG_LOGGER.debug("Adding new profile features...");
            profileDaoImp.addProfileFeatures(profile.getId(), features);
        }
    }

}
