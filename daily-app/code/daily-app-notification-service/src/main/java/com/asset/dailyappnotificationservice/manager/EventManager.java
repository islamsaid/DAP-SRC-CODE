package com.asset.dailyappnotificationservice.manager;

import com.asset.dailyappnotificationservice.cache.EmailProperties;
import com.asset.dailyappnotificationservice.cache.PropertiesCache;
import com.asset.dailyappnotificationservice.cache.QueriesCache;
import com.asset.dailyappnotificationservice.handler.EmailCampHandler;
import com.asset.dailyappnotificationservice.handler.EmailDequeuerHandler;
import com.asset.dailyappnotificationservice.handler.EmailMonitorTask;
import com.asset.dailyappnotificationservice.handler.EmailProcessorHandler;
import com.asset.dailyappnotificationservice.handler.PayloadHandler;
import com.asset.dailyappnotificationservice.logger.DailyAppLogger;
import com.asset.dailyappnotificationservice.models.EmailQueue;
import com.asset.dailyappnotificationservice.services.AccountService;
import com.asset.dailyappnotificationservice.services.NotificationsService;
import com.asset.dailyappnotificationservice.tasks.PriceGroupNotificationsTask;
import com.asset.dailyappnotificationservice.tasks.RatePlanNotificationsTask;
import com.asset.dailyappnotificationservice.tasks.ServiceClassNotificationsTask;
import com.asset.dailyappnotificationservice.tasks.TariffModelNotificationsTask;
import com.asset.emailbroker.core.model.EmailPayload;
import com.asset.emailbroker.integration.model.EmailAccount;
import com.asset.genericbroker.core.CampaignManager;
import com.asset.genericbroker.core.GenericBrokerManager;
import com.asset.genericbroker.core.handler.CampaignEventsHandler;
import com.asset.genericbroker.core.handler.DequeuerEventsHandler;
import com.asset.genericbroker.core.handler.ProcessorEventsHandler;
import com.asset.genericbroker.core.model.CampaignConfiguration;
import com.asset.genericbroker.integration.model.Account;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventManager {

    private final ApplicationContext applicationContext;
    private final NotificationsManager notificationsManager;
    private final NotificationsService notificationsService;
    private final QueriesCache queryCache;
    private final AccountService accountService;
    private final GenericBrokerManager bm;
    private final PropertiesCache propertiesCache;
    private final EmailQueue emailQueue;
    @Autowired
    EmailProperties brokerProperties;

    @Autowired
    private ObjectProvider<CampaignManager> campaginManagerProvider;

    @Autowired
    public EventManager(ApplicationContext applicationContext, GenericBrokerManager bm, PropertiesCache propertiesCache, NotificationsManager notificationsManager,
            NotificationsService notificationsService, QueriesCache queryCache, AccountService accountService, EmailQueue emailQueue) {
        this.applicationContext = applicationContext;
        this.bm = bm;
        this.propertiesCache = propertiesCache;
        this.notificationsManager = notificationsManager;
        this.notificationsService = notificationsService;
        this.queryCache = queryCache;
        this.accountService = accountService;
        this.emailQueue = emailQueue;
    }

    @EventListener
    public void handleContextStarted(ApplicationStartedEvent event) throws Exception {
        DailyAppLogger.INFO_LOGGER.info("-------------------------------Start handleContextStarted-------------------------------");
        DailyAppLogger.INFO_LOGGER.info("Application started event received: " + event);
        Thread.currentThread().setName("Event-Manager");

        DailyAppLogger.INFO_LOGGER.info("Start loading Configurations...");
        NotificationsManager.queries = queryCache.readeAllQueries();
        NotificationsManager.cacheServiceClasses = notificationsService.loadServiceClassesKeys();
        NotificationsManager.cacheTariffModels = notificationsService.loadTariffModelsKeys();
        NotificationsManager.cachePriceGroups = notificationsService.loadPriceGroupsKeys();
        NotificationsManager.cacheRatePlans = notificationsService.loadRatePlansKeys();
        DailyAppLogger.INFO_LOGGER.info("End loading Configurations");

        DailyAppLogger.INFO_LOGGER.info("Start email broker...");
        HashMap<Class, PayloadHandler> payloadHandlers = new HashMap<>();
        payloadHandlers.put(EmailPayload.class, new PayloadHandler());
        ArrayList<Account> accounts = new ArrayList<>();
        ArrayList<EmailAccount> emailAccounts = accountService.getAllAccount();
        accounts.addAll(emailAccounts);

        bm.syncAccounts(accounts);

        CampaignConfiguration configuration = new CampaignConfiguration("Camp1", null, 2, 2, 4, 4, 2000, 10L, TimeUnit.SECONDS);

        CampaignEventsHandler campaignEventsHandler = new EmailCampHandler();
        DequeuerEventsHandler dequeuerEventsHandler = new EmailDequeuerHandler(emailQueue, propertiesCache);
        ProcessorEventsHandler processorEventsHandler = new EmailProcessorHandler();

        EmailMonitorTask campaignMonitor = new EmailMonitorTask();
        CampaignManager cm = campaginManagerProvider.getObject(configuration, campaignEventsHandler, dequeuerEventsHandler, processorEventsHandler, payloadHandlers, campaignMonitor);
        campaignMonitor.setCampaignManager(cm);

        ArrayList<String> camp1Acc = new ArrayList<>();
        for (EmailAccount emailAccount : emailAccounts)
            camp1Acc.add(emailAccount.getAccountId());

        cm.setAccounts(camp1Acc);
        bm.addCampaign(cm);

        DailyAppLogger.INFO_LOGGER.info("End email broker ...");

        notificationsManager.init();
        DailyAppLogger.INFO_LOGGER.info("Start scheduling notification");

        notificationsManager.submitTask(applicationContext.getBean(ServiceClassNotificationsTask.class,emailQueue));
        notificationsManager.submitTask(applicationContext.getBean(TariffModelNotificationsTask.class, emailQueue));
        notificationsManager.submitTask(applicationContext.getBean(PriceGroupNotificationsTask.class, emailQueue));
        notificationsManager.submitTask(applicationContext.getBean(RatePlanNotificationsTask.class, emailQueue));

        DailyAppLogger.INFO_LOGGER.info("End scheduling notification");
        DailyAppLogger.INFO_LOGGER.info("-------------------------------End handleContextStarted-------------------------------");
    }
}
