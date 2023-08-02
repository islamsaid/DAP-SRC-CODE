package com.asset.dailyapplookupservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class Properties
{

    private int priceGroupKeyForGroupSelectionForPostPaid;
    private int priceGroupKeyForGroupSelectionForPrePaid;

    public int getPriceGroupKeyForGroupSelectionForPostPaid() {
        return priceGroupKeyForGroupSelectionForPostPaid;
    }

    public int getPriceGroupKeyForGroupSelectionForPrePaid() {
        return priceGroupKeyForGroupSelectionForPrePaid;
    }

    public void setPriceGroupKeyForGroupSelectionForPostPaid(int priceGroupKeyForGroupSelectionForPostPaid) {
        this.priceGroupKeyForGroupSelectionForPostPaid = priceGroupKeyForGroupSelectionForPostPaid;
    }

    public void setPriceGroupKeyForGroupSelectionForPrePaid(int priceGroupKeyForGroupSelectionForPrePaid) {
        this.priceGroupKeyForGroupSelectionForPrePaid = priceGroupKeyForGroupSelectionForPrePaid;
    }
}
