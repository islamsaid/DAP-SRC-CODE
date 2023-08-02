package com.asset.loggingService.model;

import java.util.Random;

public class TransactionActionModel {
    String actionId;
    int sqlId;

    public TransactionActionModel(String actionId, int sqlId) {
        this.actionId = actionId;
        this.sqlId = sqlId;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public int getSqlId() {
        return sqlId;
    }

    public void setSqlId(int sqlId) {
        this.sqlId = sqlId;
    }


    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        Random random = new Random();
        System.out.println(random.nextInt());

    }
}
