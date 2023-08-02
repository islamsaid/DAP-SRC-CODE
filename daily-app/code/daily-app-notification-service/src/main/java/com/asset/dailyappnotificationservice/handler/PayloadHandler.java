package com.asset.dailyappnotificationservice.handler;

import com.asset.emailbroker.core.command.EmailSender;
import com.asset.emailbroker.core.handler.EmailPayloadHandler;
import com.asset.genericbroker.core.CampaignManager;
import com.asset.genericbroker.core.defines.Defines;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
/**
 *
 * @author islam.said
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PayloadHandler extends EmailPayloadHandler{

     @Override
    public boolean isSuccessfulResponse(Object response) {
        System.out.println(response.toString());
        return true;
    }

    @Override
    public boolean before(CampaignManager campaignManager, String account, String sessionIdentifier, EmailSender command) {
        return true;
    }

    @Override
    public boolean afterSuccessfulExecute(CampaignManager campaignManager, String account, String sessionIdentifier, EmailSender command, Object commandResponse) {
        return false;
    }

    @Override
    public boolean handleExecuteException(Exception ex, CampaignManager campaignManager, String account, String sessionIdentifier, EmailSender command, Object commandResponse, Integer attempts) {
        return false;
    }

    @Override
    public boolean handleSessionException(Exception ex, CampaignManager campaignManager, String account, String sessionIdentifier, EmailSender command, Integer attempts) {
        return false;
    }

    @Override
    public void handlePayloadError(Throwable throwable, Defines.Stage CallingStage, EmailSender command) {
    }

    @Override
    public void handleCampaignShutdown(Throwable ex, String account, Object commandResponse, EmailSender command) {
    }

    @Override
    public boolean handleSessionInvalidOnCreate(EmailSender command, String accountIdentifier) {
        return false;
    }
    
}
