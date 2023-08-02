package com.asset.dailyappbackendservice.service;

import com.asset.dailyappbackendservice.cache.UserTokenCache;
import com.asset.dailyappbackendservice.model.UserTokenModel;
import org.springframework.stereotype.Component;

@Component
public class TokenHandlerService
{
    public void storeToken(UserTokenModel userTokenModel){
        UserTokenCache.userToken.put(userTokenModel.getUsername(), userTokenModel.getToken());
    }
}
