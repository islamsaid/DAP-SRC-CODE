package com.asset.dailyapplookupservice.model.response.pricegroup;

import com.asset.dailyapplookupservice.model.shared.PgGroupModel;

import java.util.List;

public class GetAllGroupGroups {
    List<PgGroupModel> allPriceGroupGroupsResponses;

    public List<PgGroupModel> getAllPriceGroupGroupsResponses() {
        return allPriceGroupGroupsResponses;
    }

    public void setAllPriceGroupGroupsResponses(List<PgGroupModel> allPriceGroupGroupsResponses) {
        this.allPriceGroupGroupsResponses = allPriceGroupGroupsResponses;
    }

    public GetAllGroupGroups(List<PgGroupModel> allPriceGroupGroupsResponses) {
        this.allPriceGroupGroupsResponses = allPriceGroupGroupsResponses;
    }
}
