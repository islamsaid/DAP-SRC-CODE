package com.asset.dailyappusermanagementservice.controllers;

import com.asset.dailyappusermanagementservice.defines.Defines;
import com.asset.dailyappusermanagementservice.defines.ErrorCodes;
import com.asset.dailyappusermanagementservice.exception.UserManagementException;
import com.asset.dailyappusermanagementservice.logger.DailyAppLogger;
import com.asset.dailyappusermanagementservice.models.responses.BaseResponseForAddObject;
import com.asset.dailyappusermanagementservice.models.request.user.*;
import com.asset.dailyappusermanagementservice.models.responses.BaseResponse;
import com.asset.dailyappusermanagementservice.models.responses.user.*;
import com.asset.dailyappusermanagementservice.models.user.UserModel;
import com.asset.dailyappusermanagementservice.service.UserService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = Defines.ContextPaths.USER)
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = Defines.WEB_ACTIONS.GET_ALL, method = RequestMethod.POST)
    public BaseResponse<GetAllUsersResponse> getAll() throws UserManagementException {
        String traceId = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        GetAllUsersResponse response = userService.retrieveUsers();
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, response);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.GET, method = RequestMethod.POST)
    public BaseResponse<UserModel> getUserById(@RequestBody GetUserRequest userRequest){
        DailyAppLogger.DEBUG_LOGGER.debug("GET Request is Received with parameter id = {}", userRequest.getUserId());
        String   traceId  = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        UserModel response = userService.retrieveUserById(userRequest.getUserId(), true);
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, response);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.ADD, method = RequestMethod.POST)
    public BaseResponse<BaseResponseForAddObject> addUser(@RequestBody UserModel user){
        String   traceId  = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        int userId= userService.createUser(user);
        BaseResponseForAddObject baseResponseForAddObject = new BaseResponseForAddObject(String.valueOf(userId));
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, baseResponseForAddObject);
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.UPDATE, method = RequestMethod.POST)
    public BaseResponse<String> updateUser(@RequestBody UserModel user){
        String   traceId  = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        userService.updateUser(user.getUserId(), user);
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, "Updated");
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.DELETE, method = RequestMethod.POST)
    public BaseResponse<String> deleteUser(@RequestBody GetUserRequest userRequest){
        DailyAppLogger.DEBUG_LOGGER.debug("DELETE Request is Received with user id = {}", userRequest.getUserId());
        String   traceId  = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        userService.deleteUser(userRequest.getUserId());
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, "Deleted");
    }

    @RequestMapping(value = Defines.WEB_ACTIONS.DELETE_ALL, method = RequestMethod.POST)
    public BaseResponse<String> deleteAllUsers(){
        DailyAppLogger.DEBUG_LOGGER.debug("DELETE_ALL Request is Received");
        String   traceId  = UUID.randomUUID().toString();
        ThreadContext.put("traceId", traceId);
        userService.clearUsers();
        ThreadContext.remove("traceId");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "Success", 0, traceId, "Cleared");
    }
}

