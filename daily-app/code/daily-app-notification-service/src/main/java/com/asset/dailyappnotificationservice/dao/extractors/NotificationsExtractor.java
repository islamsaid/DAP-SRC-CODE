package com.asset.dailyappnotificationservice.dao.extractors;

import com.asset.dailyappnotificationservice.defines.DatabaseStructs;
import com.asset.dailyappnotificationservice.logger.DailyAppLogger;
import com.asset.dailyappnotificationservice.models.NotificationModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationsExtractor implements ResultSetExtractor<List<NotificationModel>> {
    @Override
    public List<NotificationModel> extractData(ResultSet rs) throws SQLException, DataAccessException {

        List<NotificationModel> notificationsList = new ArrayList<>();
        while (rs.next()) {
            NotificationModel notification = new NotificationModel();

            notification.setId(rs.getInt(DatabaseStructs.DAILY_NOTIFICATIONS.ID));
            notification.setNotificationHeader(rs.getString(DatabaseStructs.DAILY_NOTIFICATIONS.NOTIFICATION_HEADER));
            notification.setNotificationDetails(rs.getString(DatabaseStructs.DAILY_NOTIFICATIONS.NOTIFICATION_DETAILS));
            notification.setNotificationType(rs.getInt(DatabaseStructs.DAILY_NOTIFICATIONS.NOTIFICATION_TYPE));
            notification.setActionDone(rs.getInt(DatabaseStructs.DAILY_NOTIFICATIONS.ACTION_DONE));
            notification.setCheckSum(rs.getString(DatabaseStructs.DAILY_NOTIFICATIONS.CHECK_SUM));
            notification.setDataId(rs.getInt(DatabaseStructs.DAILY_NOTIFICATIONS.DATA_ID));
            notification.setDataName(rs.getString(DatabaseStructs.DAILY_NOTIFICATIONS.DATA_NAME));

            String date = getDate(rs.getString(DatabaseStructs.DAILY_NOTIFICATIONS.CREATION_DATE));
            notification.setCreationDate(date);

            notificationsList.add(notification);
        }
        return notificationsList;

    }

    private String getDate(String dateAndTime) {
        String[] splitDate = dateAndTime.split(" ");
        return splitDate[0];
    }
}
