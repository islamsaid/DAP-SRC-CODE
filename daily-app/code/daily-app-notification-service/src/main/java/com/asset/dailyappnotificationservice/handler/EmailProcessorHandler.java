package com.asset.dailyappnotificationservice.handler;

import com.asset.emailbroker.core.handler.EmailProcessorEventsHandler;
import com.asset.emailbroker.core.model.EmailPayload;
import com.asset.emailbroker.integration.model.EmailMessage;
import com.asset.genericbroker.core.CampaignManager;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
/**
 *
 * @author islam.said
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EmailProcessorHandler extends EmailProcessorEventsHandler{
     @Override
    public void beforeProcessing(CampaignManager campaignManager) {
    }

    @Override
    public void afterSuccessfulProcessing(CampaignManager campaignManager, EmailMessage message, EmailPayload processedMessage) {
    }

    @Override
    public void afterSuccessfulAccountEnqueue(CampaignManager campaignManager, EmailMessage message, EmailPayload processedMessage) {
    }

    @Override
    public boolean handleNoValidAccountsForCampaign(Exception ex, CampaignManager campaignManager, EmailMessage message, EmailPayload processedMessage) {
        return false;
    }

    @Override
    public void handleProcessorException(Exception ex, CampaignManager campaignManager, EmailMessage message) {
    }

    @Override
    public void handleCampaignShutdown(Exception ex, CampaignManager campaignManager, EmailMessage message) {
    }

    @Override
    public boolean handleProcessingException(Exception ex, CampaignManager campaignManager, EmailMessage message, EmailPayload payloadModel) {
        return false;
    }

    @Override
    public void handleNoSuitableAccountPayloadSender(Exception ex, CampaignManager campaignManager, EmailMessage message) {
    }

    @Override
    public void handleNoSuitableHandlerForPayload(Exception ex, CampaignManager campaignManager, EmailMessage message, EmailPayload payloadModel) {
    }
}
