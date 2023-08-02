package com.asset.dailyapplookupservice.model.response.pricegroup;

import com.asset.dailyapplookupservice.model.shared.PgGroupModel;
import com.asset.dailyapplookupservice.model.shared.PriceGroupModel;

import java.util.List;

public class GetPgGroupModel {

    PgGroupModel pgGroupModel;
    List<PriceGroupModel> priceGroupModelList;

    public PgGroupModel getPgGroupModel() {
        return pgGroupModel;
    }

    public void setPgGroupModel(PgGroupModel pgGroupModel) {
        this.pgGroupModel = pgGroupModel;
    }

    public List<PriceGroupModel> getPriceGroupModelList() {
        return priceGroupModelList;
    }

    public void setPriceGroupModelList(List<PriceGroupModel> priceGroupModelList) {
        this.priceGroupModelList = priceGroupModelList;
    }

    @Override
    public String toString() {
        return "GetPgGroupModel{" +
                "pgGroupModel=" + pgGroupModel +
                ", priceGroupModelList=" + priceGroupModelList +
                '}';
    }
}
