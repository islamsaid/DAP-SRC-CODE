package com.asset.loggingService.managers;

import com.asset.loggingService.cache.PropertiesCache;
import com.asset.loggingService.dao.QueriesCacheDAOImp;
import com.asset.loggingService.loggers.DailyAppLogger;
import com.asset.loggingService.model.TransactionActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;

@EnableScheduling
@Configuration
public class RefreshManager { 

    CacheManager cacheManager;
    private QueriesCacheDAOImp queriesCacheDAOImp;

    PropertiesCache propertiesCache;

    @Autowired
    RefreshManager(CacheManager cacheManager , PropertiesCache propertiesCache,QueriesCacheDAOImp queriesCacheDAOImp) {
        this.cacheManager = cacheManager;
        this.propertiesCache = propertiesCache;
        this.queriesCacheDAOImp=queriesCacheDAOImp;
    }


    @Scheduled(cron="*/10 * * * * *")
    public void refreshTransactionsActionCache() {
    HashMap<Integer, TransactionActionModel> GetTransactionCacheCopy ;
        GetTransactionCacheCopy=queriesCacheDAOImp.getAllTransactionsActionCache();
        if (GetTransactionCacheCopy!=null){
            CacheManager.GetTransactionActionCache = GetTransactionCacheCopy;
        }
    }
}
