package com.asset.dailyappreportservice.model.request;

import java.util.List;

public class AddBatchTransferAdjustmentRequest {

    List<AddTransferAdjustmentRequest> addTransferAdjustmentRequests;

    public List<AddTransferAdjustmentRequest> getAddTransferAdjustmentRequests() {
        return addTransferAdjustmentRequests;
    }

    public void setAddTransferAdjustmentRequests(List<AddTransferAdjustmentRequest> addTransferAdjustmentRequests) {
        this.addTransferAdjustmentRequests = addTransferAdjustmentRequests;
    }
}
