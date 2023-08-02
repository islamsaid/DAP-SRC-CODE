package com.asset.dailyappbackendservice.service;

import com.asset.dailyappbackendservice.logger.DailyAppLogger;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class RestService {
    private final RestTemplate restTemplate;


    public RestService(RestTemplate restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder;

    }

    public ResponseEntity<String> callAllMicroServicesUsingUrl(String url) {
        ResponseEntity<String> response = null;
        try {
            // create headers
            HttpHeaders headers = new HttpHeaders();
            // set `content-type` header
            headers.setContentType(MediaType.APPLICATION_JSON);
            // set `accept` header
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            // create a map for post parameters
            Map<String, Object> map = new HashMap<>();
            // build the request
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
          return  this.restTemplate.postForEntity(url, entity, String.class);

        } catch (Exception e) {
            DailyAppLogger.DEBUG_LOGGER.error("error while execute call All MicroServicesUsingUrl  " + url, e);
            DailyAppLogger.ERROR_LOGGER.debug("error while execute call All MicroServicesUsingUrl  " + url, e);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); //TODO
    }
}
