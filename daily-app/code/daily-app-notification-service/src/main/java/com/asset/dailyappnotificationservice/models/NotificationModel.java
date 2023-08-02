package com.asset.dailyappnotificationservice.models;

import lombok.Data;

@Data
public class NotificationModel {
    private Integer id;
    private String notificationHeader;
    private String notificationDetails;
    private Integer notificationType;
    private Integer actionDone;
    private String checkSum;       
    private Integer dataId;
    private String dataName;
    private String creationDate;

    public NotificationModel() {
    }

    public NotificationModel(String notificationHeader, String notificationDetails, Integer notificationType, Integer actionDone, String checkSum, Integer dataId, String dataName) {
        this.notificationHeader = notificationHeader;
        this.notificationDetails = notificationDetails;
        this.notificationType = notificationType;
        this.actionDone = actionDone;
        this.checkSum = checkSum;
        this.dataId = dataId;
        this.dataName = dataName;
    }
}
