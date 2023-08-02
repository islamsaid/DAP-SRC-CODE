package com.asset.dailyappnotificationservice.handler;

import com.asset.dailyappnotificationservice.cache.PropertiesCache;
import com.asset.dailyappnotificationservice.logger.DailyAppLogger;
import com.asset.dailyappnotificationservice.models.EmailQueue;
import com.asset.emailbroker.core.handler.EmailDequeuerEventsHandler;
import com.asset.emailbroker.integration.model.EmailMessage;
import com.asset.genericbroker.core.CampaignManager;
import com.asset.genericbroker.core.model.SafeInteger;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author islam.said
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EmailDequeuerHandler extends EmailDequeuerEventsHandler {

    private final EmailQueue emailQueue;
    private final PropertiesCache propertiesCache;

    // static SafeInteger index = new SafeInteger(0);
    public EmailDequeuerHandler(EmailQueue emailQueue, PropertiesCache propertiesCache) {
        this.emailQueue = emailQueue;
        this.propertiesCache = propertiesCache;
    }

    @Override
    public boolean beforeDequeue(CampaignManager campaignManager, int dequeuerID) throws InterruptedException {
        return true;
    }

    @Override
    public List<EmailMessage> dequeue(CampaignManager campaignManager, Integer batchSize, int dequeuerID) throws InterruptedException {
        ArrayList<EmailMessage> msgList = new ArrayList<>();
        synchronized (msgList) {
            for (int i = 1; (!emailQueue.emailMessages.isEmpty() && i < propertiesCache.getEmailDequeuingMaxBatchSize()); i++) {
                EmailMessage email = emailQueue.emailMessages.poll();
                if (email != null) {
                    msgList.add(email);
                    DailyAppLogger.INFO_LOGGER.info("Dequeued");
                }
            }
            return msgList;
        }
//        synchronized (msgList) {
//            for (int i = 0; i < batchSize; i++) {
//
//                String msgID = index.addAndGet(1) + "";
//                EmailMessage em = new EmailMessage("test msg " + msgID);
//                em.setSender("BROKER");
//                em.setSubject("TEST " + msgID);
//                em.setPlainText("HELLO WORLD " + msgID);
////                em.setSenderAddress("BROKER@gmail.com");
//                em.setReceivers(new String[]{"ABO3amo@gmail.com", "CEEFEFE@gmail.com"});
//                em.setSender("BROKER");
//                msgList.add(em);
//            }
//        }
//        return msgList;
    }

    @Override
    public boolean handleDequeuedMessageException(Exception ex, CampaignManager campaignManager, EmailMessage message, List<EmailMessage> dequeuedBatch, int currentMessageIndex, int dequeuerID) {
        return false;
    }

    @Override
    public boolean afterSuccessfulDequeue(CampaignManager campaignManager, int dequeuedBatchSize, int dequeuerID) {
        return true;
    }

    @Override
    public void dequeuedNothing(CampaignManager campaignManager, int dequeuerID) {

    }

    @Override
    public EmailMessage getMessageFromList(List<EmailMessage> messageBatch, int index, int dequeuerID) {
        return messageBatch.get(index);
    }

    @Override
    public boolean beforeEnqueueToProcessing(CampaignManager campaignManager, EmailMessage message, int dequeuerID) throws InterruptedException {
        return true;
    }

    @Override
    public void afterEnqueueToProcessing(CampaignManager campaignManager, EmailMessage message, List<EmailMessage> messageBatch, int messageIndex, int dequeuerID) {

    }

    @Override
    public void handleCampaignShuttingDown(Exception ex, CampaignManager campaignManager, EmailMessage message, List<EmailMessage> dequeuedBatch, int currentMessageIndex, int dequeuerID) {
    }

}
