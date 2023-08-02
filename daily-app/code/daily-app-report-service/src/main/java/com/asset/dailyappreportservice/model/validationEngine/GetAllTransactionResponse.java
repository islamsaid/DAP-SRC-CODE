package com.asset.dailyappreportservice.model.validationEngine;

import java.util.List;

public class GetAllTransactionResponse {

    private List<TransactionModel> transactionModelList;

    public GetAllTransactionResponse(List<TransactionModel> transactionModelList) {
        this.transactionModelList = transactionModelList;
    }

    public List<TransactionModel> getTransactionModelList() {
        return transactionModelList;
    }

    public void setTransactionModelList(List<TransactionModel> transactionModelList) {
        this.transactionModelList = transactionModelList;
    }
}

