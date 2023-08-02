package com.asset.dailyappbackendservice.service;

import com.asset.dailyappbackendservice.database.dao.GetAllServicesUrlDAOImp;
import com.asset.dailyappbackendservice.logger.DailyAppLogger;
import com.asset.dailyappbackendservice.model.AllServiceModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RefreshConfigServerService {

    @Autowired
    GetAllServicesUrlDAOImp getAllServicesUrlDAOImp;
    @Autowired
    RestService restService;

    public String refreshConfigServers() {
        List<Map<String, Integer>> refreshResponse = new ArrayList<>();
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<AllServiceModel> allServiceModelList = getAllServicesUrlDAOImp.getAllServiceUrl();
            DailyAppLogger.DEBUG_LOGGER.debug("get all services url from db  "+ allServiceModelList);
            for (AllServiceModel serviceModel : allServiceModelList) {
                ResponseEntity<String> response = restService.callAllMicroServicesUsingUrl(serviceModel.getPort().concat(serviceModel.getUrl()));
                DailyAppLogger.DEBUG_LOGGER.debug("response for each service name   "+ serviceModel.getServiceName());
                DailyAppLogger.DEBUG_LOGGER.debug("response for each service   "+ response);
                Map<String, Integer> hashResponse = new HashMap<>();
                hashResponse.put(serviceModel.getServiceName(), response.getStatusCodeValue());
                refreshResponse.add(hashResponse);
            }
            return  gson.toJson(refreshResponse);
         } catch (Exception e) {
            DailyAppLogger.DEBUG_LOGGER.error("error while execute refreshConfigServers ", e);
            DailyAppLogger.ERROR_LOGGER.debug("error while execute refreshConfigServers", e);

        }
        return null;
    }

}
