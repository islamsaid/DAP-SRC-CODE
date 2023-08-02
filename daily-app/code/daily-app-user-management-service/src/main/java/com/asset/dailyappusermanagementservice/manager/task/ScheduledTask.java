/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.dailyappusermanagementservice.manager.task;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.asset.dailyappusermanagementservice.logger.DailyAppLogger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduledTask {

    private static AtomicInteger tpsCounter = new AtomicInteger(0);
    private static AtomicInteger dailyCounter = new AtomicInteger(0);

    public void incrementTPSCount() {
        tpsCounter.incrementAndGet();
        dailyCounter.incrementAndGet();
    }

    public void decrementCount() {
        tpsCounter.decrementAndGet();
    }

    @Scheduled(fixedRate = 1000)
    public void resetTpsCount() {
//        DailyAppLogger.TPS_LOGGER.info("TPS count is : " + tpsCounter.get());
        tpsCounter = new AtomicInteger(0);
    }

    @Scheduled(fixedRate = 60000)
    public void printDailyCount() {
//        DailyAppLogger.TPS_LOGGER.info("Daily count is : " + dailyCounter.get());
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Africa/Cairo")
    public void resetCountAtMidnight() {
//        DailyAppLogger.TPS_LOGGER.info("Daily count at midnight is : " + dailyCounter.get());
        dailyCounter = new AtomicInteger(0);
    }
    


}
