package com.asset.dailyappreportservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class Properties
{
    private Integer pgKeyForPostPaid;
    private Integer pgKeyForPrePaid;
    private Integer dwhStatusKeyForPostPaid;
    private Integer dwhStatusKeyForPrePaid;
    private Integer priceGroupKeyForGroupSelectionForPostPaid;
    private Integer priceGroupKeyForGroupSelectionForPrePaid;
    private Integer trxTypeKeyForClosing;
    private Integer userIdForClosing;
    private Integer trxTypeKeyForNetAdds;
    private Integer maximumNumberOfSubscriptions;


    private Integer limit;  

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getMaximumNumberOfSubscriptions() {
        return maximumNumberOfSubscriptions;
    }

    public void setMaximumNumberOfSubscriptions(Integer maximumNumberOfSubscriptions) {
        this.maximumNumberOfSubscriptions = maximumNumberOfSubscriptions;
    }

    public Integer getPgKeyForPostPaid() {
        return pgKeyForPostPaid;
    }

    public void setPgKeyForPostPaid(Integer pgKeyForPostPaid) {
        this.pgKeyForPostPaid = pgKeyForPostPaid;
    }

    public Integer getPgKeyForPrePaid() {
        return pgKeyForPrePaid;
    }

    public void setPgKeyForPrePaid(Integer pgKeyForPrePaid) {
        this.pgKeyForPrePaid = pgKeyForPrePaid;
    }

    public Integer getDwhStatusKeyForPostPaid() {
        return dwhStatusKeyForPostPaid;
    }

    public void setDwhStatusKeyForPostPaid(Integer dwhStatusKeyForPostPaid) {
        this.dwhStatusKeyForPostPaid = dwhStatusKeyForPostPaid;
    }

    public Integer getDwhStatusKeyForPrePaid() {
        return dwhStatusKeyForPrePaid;
    }

    public void setDwhStatusKeyForPrePaid(Integer dwhStatusKeyForPrePaid) {
        this.dwhStatusKeyForPrePaid = dwhStatusKeyForPrePaid;
    }

    public Integer getPriceGroupKeyForGroupSelectionForPostPaid() {
        return priceGroupKeyForGroupSelectionForPostPaid;
    }

    public void setPriceGroupKeyForGroupSelectionForPostPaid(Integer priceGroupKeyForGroupSelectionForPostPaid) {
        this.priceGroupKeyForGroupSelectionForPostPaid = priceGroupKeyForGroupSelectionForPostPaid;
    }

    public Integer getPriceGroupKeyForGroupSelectionForPrePaid() {
        return priceGroupKeyForGroupSelectionForPrePaid;
    }

    public void setPriceGroupKeyForGroupSelectionForPrePaid(Integer priceGroupKeyForGroupSelectionForPrePaid) {
        this.priceGroupKeyForGroupSelectionForPrePaid = priceGroupKeyForGroupSelectionForPrePaid;
    }

    public Integer getTrxTypeKeyForClosing() {
        return trxTypeKeyForClosing;
    }

    public void setTrxTypeKeyForClosing(Integer trxTypeKeyForClosing) {
        this.trxTypeKeyForClosing = trxTypeKeyForClosing;
    }

    public Integer getUserIdForClosing() {
        return userIdForClosing;
    }

    public void setUserIdForClosing(Integer userIdForClosing) {
        this.userIdForClosing = userIdForClosing;
    }

    public Integer getTrxTypeKeyForNetAdds() {
        return trxTypeKeyForNetAdds;
    }

    public void setTrxTypeKeyForNetAdds(Integer trxTypeKeyForNetAdds) {
        this.trxTypeKeyForNetAdds = trxTypeKeyForNetAdds;
    }
}
