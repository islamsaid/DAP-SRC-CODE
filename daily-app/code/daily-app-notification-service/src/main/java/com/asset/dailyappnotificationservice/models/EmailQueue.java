package com.asset.dailyappnotificationservice.models;

import com.asset.dailyappnotificationservice.cache.PropertiesCache;
import com.asset.emailbroker.integration.model.EmailMessage;
import java.util.concurrent.ArrayBlockingQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author islam.said
 */
@Component
public class EmailQueue {

    public final ArrayBlockingQueue<EmailMessage> emailMessages;

    @Autowired
    public EmailQueue(PropertiesCache properties) {
        emailMessages = new ArrayBlockingQueue<>(properties.getEmailQueueSize());
    }
}
