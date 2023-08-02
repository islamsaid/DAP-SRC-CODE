package com.asset.dailyapplookupservice.model.request.priceGroup;

import com.asset.dailyapplookupservice.model.shared.PgGroupModel;
import com.asset.dailyapplookupservice.model.shared.PriceGroupModel;

import java.util.List;

public class AddPriceGroupGroupRequest {
   private PgGroupModel pgGroupModel;
   private List<PriceGroupModel>priceGroupModels;

    public PgGroupModel getPgGroupModel() {
        return pgGroupModel;
    }

    public void setPgGroupModel(PgGroupModel pgGroupModel) {
        this.pgGroupModel = pgGroupModel;
    }

    public List<PriceGroupModel> getPriceGroupModels() {
        return priceGroupModels;
    }

    public void setPriceGroupModels(List<PriceGroupModel> priceGroupModels) {
        this.priceGroupModels = priceGroupModels;
    }

    public PriceGroupModel BuildPriceGroupUsingPgGroup(PriceGroupModel priceGroupModel,PgGroupModel pgGroupModel) {
        priceGroupModel.setPgGroupKey(new PgGroupModel());
        priceGroupModel.getPgGroupKey().setPgGroupKey(pgGroupModel.getPgGroupKey());
        priceGroupModel.getPgGroupKey().setPgGroup(pgGroupModel.getPgGroup());
        priceGroupModel.getPgGroupKey().setShowFlag(pgGroupModel.getShowFlag());
        priceGroupModel.getPgGroupKey().setDescription(pgGroupModel.getDescription());
        return priceGroupModel;
    }

    @Override
    public String toString() {
        return "AddPriceGroupGroupRequest{" +
                "pgGroupModel=" + pgGroupModel +
                ", priceGroupModels=" + priceGroupModels +
                '}';
    }
}
