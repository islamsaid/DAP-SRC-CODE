package com.asset.dailyappreportservice.model.response;

import java.util.List;

public class GetAllTransferAdjustment {
    List<GetTransferAdjustment> getTransferAdjustmentList;

    public List<GetTransferAdjustment> getGetTransferAdjustmentList() {
        return getTransferAdjustmentList;
    }

    public GetAllTransferAdjustment(List<GetTransferAdjustment> getTransferAdjustmentList) {
        this.getTransferAdjustmentList = getTransferAdjustmentList;
    }

    public void setGetTransferAdjustmentList(List<GetTransferAdjustment> getTransferAdjustmentList) {
        this.getTransferAdjustmentList = getTransferAdjustmentList;
    }
}
