package com.asset.dailyappusermanagementservice.service;

import com.asset.dailyappusermanagementservice.cache.UsersSessions;
import com.asset.dailyappusermanagementservice.configurations.Properties;
import com.asset.dailyappusermanagementservice.database.dao.imp.ProfileDaoImp;
import com.asset.dailyappusermanagementservice.database.dao.imp.UsersDaoImp;
import com.asset.dailyappusermanagementservice.defines.Defines;
import com.asset.dailyappusermanagementservice.defines.ErrorCodes;
import com.asset.dailyappusermanagementservice.exception.UserManagementException;
import com.asset.dailyappusermanagementservice.logger.DailyAppLogger;
import com.asset.dailyappusermanagementservice.models.responses.LoginResponse;
import com.asset.dailyappusermanagementservice.models.responses.profile.GetProfileResponse;
import com.asset.dailyappusermanagementservice.models.responses.user.GetAllUsersResponse;
import com.asset.dailyappusermanagementservice.models.user.UserModel;
import com.asset.dailyappusermanagementservice.models.user.UserProfileModel;
import com.asset.dailyappusermanagementservice.security.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    Properties properties;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UsersDaoImp usersDaoImp;

    @Autowired
    private ProfileDaoImp profileDaoImp;

    @Autowired
    private ProfileService profileService;
    LdapService ldapService;

    public LoginResponse login(String name, String password) throws UserManagementException {
        long startTime = System.currentTimeMillis();
        LoginResponse resp = new LoginResponse();

        UserModel user = retrieveUserByName(name);
        detectActiveUser(name);

        if (properties.getLdapAuthenticationFlag()) {
            DailyAppLogger.DEBUG_LOGGER.info("Start integration with LDAP with ntAccount[" + name + "] and password [***]");
            DailyAppLogger.INFO_LOGGER.info("Start integration with LDAP with ntAccount[" + name + "] and password [***]");
            ldapService.authenticateUser(name, password);
            DailyAppLogger.DEBUG_LOGGER.info("Integration with LDAP done successfully in " + (System.currentTimeMillis() - startTime) + " msec");
            DailyAppLogger.INFO_LOGGER.info("Integration with LDAP done successfully in " + (System.currentTimeMillis() - startTime) + " msec");
        }

        DailyAppLogger.DEBUG_LOGGER.info("Login with user [" + user + "]");
        DailyAppLogger.DEBUG_LOGGER.debug("Generating token for user");
        String authToken = generateToken(user);

        UsersSessions.userSessionsMap.put(name, authToken);
        resp.setUser(user);
        resp.setToken(authToken);
        return resp;
    }

    private boolean isActiveUser(String username) {
        try {
            DailyAppLogger.DEBUG_LOGGER.info("has previous token = {} ", UsersSessions.userSessionsMap.containsKey(username));
            return Optional.ofNullable(UsersSessions.userSessionsMap.get(username))
                    .map(jwtTokenUtil::isTokenExpired)
                    .map((expired) -> {
                        DailyAppLogger.DEBUG_LOGGER.info("is token expired = {}", expired);
                        if (expired)
                            UsersSessions.userSessionsMap.remove(username);
                        return !expired;
                    })
                    .orElse(false);
        } catch (ExpiredJwtException ex) {
            DailyAppLogger.DEBUG_LOGGER.error("EXPIRED-JWT-EXCEPTION occurred", ex);
            DailyAppLogger.ERROR_LOGGER.error("EXPIRED-JWT-EXCEPTION occurred", ex);
            ex.printStackTrace();
            UsersSessions.userSessionsMap.remove(username);
            return false;
        }
    }

    private void detectActiveUser(String username) {
        DailyAppLogger.DEBUG_LOGGER.debug("detecting user's active sessions");
        if (isActiveUser(username)) {
            DailyAppLogger.DEBUG_LOGGER.error("Username [{}] has more than 1 session opened", username);
            DailyAppLogger.ERROR_LOGGER.error("Username [{}] has more than 1 session opened", username);
            //  jwtTokenUtil.destroyToken(UsersSessions.userSessionsMap.get(username));
            //  UsersSessions.userSessionsMap.remove(username);
            throw new UserManagementException(ErrorCodes.ERROR.CONCURRENT_SESSIONS_DETECTED, Defines.SEVERITY.ERROR);
        }
        DailyAppLogger.DEBUG_LOGGER.info("User is inactive and able to create a new session");
    }

    public void endUserToken(String username) {
        if (UsersSessions.userSessionsMap.containsKey(username)) {
            DailyAppLogger.DEBUG_LOGGER.debug("Destroying [{}] 's token...", username);
            jwtTokenUtil.destroyToken(UsersSessions.userSessionsMap.get(username));
            UsersSessions.userSessionsMap.remove(username);
            return;
        }
        DailyAppLogger.DEBUG_LOGGER.warn("username is not exist in the sessions map");
    }

    private String generateToken(UserModel userModel) throws UserManagementException {
        final String token = jwtTokenUtil.generateToken(userModel);
        return token;
    }

    public Integer createUser(UserModel userModel) throws UserManagementException {
        DailyAppLogger.DEBUG_LOGGER.debug("Start Adding new user --> user to be added: {} ", userModel);
        profileService.retrieveProfile(userModel.getProfileId()); //assureProfileExistence
        assureUsernameUniqueness(userModel.getUsername());

        Integer id = usersDaoImp.createUser(userModel);
        if (id.equals(0) || id == null)
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR);
        return id;
    }

    public GetAllUsersResponse retrieveUsers() throws UserManagementException {
        List<UserModel> users = usersDaoImp.retrieveUsers();
        if (users == null || users.isEmpty()) {
            DailyAppLogger.DEBUG_LOGGER.error("No users were found");
            DailyAppLogger.ERROR_LOGGER.error("No users were found");
            throw new UserManagementException(ErrorCodes.ERROR.NO_USERS_FOUND, Defines.SEVERITY.ERROR);
        }
        DailyAppLogger.DEBUG_LOGGER.debug("Done retrieving # {} users", users.size());
        return new GetAllUsersResponse(users);
    }

    public UserModel retrieveUserByName(String userName) throws UserManagementException {
        UserModel user = usersDaoImp.retrieveUserByUsername(userName);
        if (user == null) {
            DailyAppLogger.DEBUG_LOGGER.error("User not found");
            throw new UserManagementException(ErrorCodes.ERROR.USER_NOT_FOUND, Defines.SEVERITY.ERROR);
        }
        UserProfileModel userProfile = profileDaoImp.get(user.getProfileId());
        if (userProfile != null) {
            if (userProfile.getIsActive() == 0) {
                DailyAppLogger.DEBUG_LOGGER.error("profile not active");
                throw new UserManagementException(ErrorCodes.ERROR.PROFILE_NOT_ACTIVE, Defines.SEVERITY.ERROR);
            }
            user.setProfileModel(userProfile);
        }
        return user;
    }

    public UserModel retrieveUserById(Integer id, Boolean enableProfileChecking) throws UserManagementException {
        DailyAppLogger.DEBUG_LOGGER.debug("Start retrieving user of id {}", id);
        UserModel user = usersDaoImp.retrieveUserById(id);
        if (user == null) {
            DailyAppLogger.DEBUG_LOGGER.debug("cannot find user id");
            DailyAppLogger.ERROR_LOGGER.error("cannot find user id");
            throw new UserManagementException(ErrorCodes.ERROR.USER_NOT_FOUND, Defines.SEVERITY.ERROR);
        }
        if (enableProfileChecking) {
            UserProfileModel userProfile = profileService.retrieveProfile(user.getProfileId()).getProfile();
            user.setProfileModel(userProfile);
        }
        DailyAppLogger.DEBUG_LOGGER.info(user);
        return user;
    }

    public Integer updateUser(Integer id, UserModel updatedUser) throws UserManagementException {
        UserModel userEntity = retrieveUserById(id, false);
        DailyAppLogger.INFO_LOGGER.info("pre-updated user = {}", userEntity);
        DailyAppLogger.INFO_LOGGER.info("updated user = {}", updatedUser);

        if (!userEntity.getProfileId().equals(updatedUser.getProfileId()))
            profileService.retrieveProfile(updatedUser.getProfileId()); //assure updated user's profile existence.

        DailyAppLogger.DEBUG_LOGGER.debug("Checking Username Uniqueness...");
        UserModel checkedUser = usersDaoImp.retrieveUserByUsername(updatedUser.getUsername());
        if (checkedUser != null && !checkedUser.getUserId().equals(userEntity.getUserId())) {
            DailyAppLogger.DEBUG_LOGGER.debug("Username Should be Unique");
            DailyAppLogger.ERROR_LOGGER.error("Username Should be Unique");
            throw new UserManagementException(ErrorCodes.ERROR.USERNAME_SHOULD_BE_UNIQUE, Defines.SEVERITY.ERROR);
        }

        return usersDaoImp.updateUser(updatedUser, id);
    }

    public void deleteUser(Integer id) throws UserManagementException {
        UserModel user = usersDaoImp.retrieveUserById(id);
        if (user == null) {
            DailyAppLogger.DEBUG_LOGGER.debug("User Is Not Found");
            DailyAppLogger.ERROR_LOGGER.error("User Is Not Found");
            throw new UserManagementException(ErrorCodes.ERROR.USER_NOT_FOUND, Defines.SEVERITY.ERROR);
        }
        usersDaoImp.deleteUser(id);
        DailyAppLogger.DEBUG_LOGGER.debug("User {} {} is deleted", user.getUsername(), user.getUserId());
    }

    public void clearUsers() {
        usersDaoImp.clearUsers();
    }

    public Boolean isUserExists(Integer userId) throws UserManagementException {
        DailyAppLogger.DEBUG_LOGGER.debug("Checking if user with ID[" + userId + "] exists in DB");
        return usersDaoImp.findUser(userId) > 0;
    }

    public Integer retrieveUsersByProfileId(Integer profileId) throws UserManagementException {
        DailyAppLogger.DEBUG_LOGGER.debug("Retrieving users with profile ID [" + profileId + "]");
        return usersDaoImp.retrieveUsersByProfileId(profileId);
    }


    public Boolean assureUsernameUniqueness(String username) {
        DailyAppLogger.DEBUG_LOGGER.debug("Checking username uniqueness...");
        if (usersDaoImp.retrieveUserByUsername(username) != null) {
            DailyAppLogger.DEBUG_LOGGER.debug("Username Should be Unique");
            DailyAppLogger.ERROR_LOGGER.error("Username Should be Unique");
            throw new UserManagementException(ErrorCodes.ERROR.USERNAME_SHOULD_BE_UNIQUE, Defines.SEVERITY.ERROR);
        }
        DailyAppLogger.DEBUG_LOGGER.debug("username is unique.");
        return true;
    }
}
