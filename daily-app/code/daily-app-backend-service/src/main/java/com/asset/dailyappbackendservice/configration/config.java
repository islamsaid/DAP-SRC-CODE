package com.asset.dailyappbackendservice.configration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class config{

    @Bean
    RestTemplate getRestTemplate(){
        return  new RestTemplate();
    }

}
