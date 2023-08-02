package com.asset.dailyappnotificationservice.handler;

import com.asset.emailbroker.core.handler.EmailCampaignEventsHandler;
import com.asset.emailbroker.integration.model.EmailMessage;
import com.asset.genericbroker.core.CampaignManager;
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
public class EmailCampHandler extends EmailCampaignEventsHandler{

     @Override
    public void beforeStart(CampaignManager campaignManager) {
    }

    @Override
    public void afterStart(CampaignManager campaignManager) {
    }

    @Override
    public void handleStartException(CampaignManager campaignManager, Exception ex) {
    }

    @Override
    public void beforeRestart(CampaignManager campaignManager) {
    }

    @Override
    public void afterRestart(CampaignManager campaignManager) {
    }

    @Override
    public void handleRestartException(CampaignManager campaignManager, Exception ex) {
    }

    @Override
    public void beforeStop(CampaignManager campaignManager) {
    }

    @Override
    public void afterStop(CampaignManager campaignManager) {
    }

    @Override
    public void beforeStopNow(CampaignManager campaignManager) {
    }

    @Override
    public void afterStopNow(CampaignManager campaignManager, List<EmailMessage> drainedMessages) {
    }
    
}
