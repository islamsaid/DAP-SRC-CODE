package com.asset.dailyappnotificationservice.cache;

import com.asset.emailbroker.core.cache.EmailBrokerPropertiesCache;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 *
 * @author islam.said
 */
@Primary
@Component
public class EmailProperties extends EmailBrokerPropertiesCache {

    @PostConstruct
    void init() {
        this.setLoadBalancerWorkersCount(4)
                .setHandlersCount(2)
                .setHandlersPoolSize(10)
                .setMaxHandlerCount(4);
    }
}
