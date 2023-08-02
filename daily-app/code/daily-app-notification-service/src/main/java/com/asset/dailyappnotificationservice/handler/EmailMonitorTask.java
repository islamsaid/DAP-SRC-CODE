package com.asset.dailyappnotificationservice.handler;

import com.asset.dailyappnotificationservice.logger.DailyAppLogger;
import com.asset.genericbroker.core.CampaignMonitorTask;

/**
 *
 * @author islam.said
 */
public class EmailMonitorTask extends CampaignMonitorTask {

    @Override
    public void run() {
        DailyAppLogger.INFO_LOGGER.info(campaignManager.getCampaignIdentifier() + " refreshing");
    }
}
