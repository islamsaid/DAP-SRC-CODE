package com.asset.dailyappusermanagementservice.database.extractors;

import com.asset.dailyappusermanagementservice.defines.DatabaseStructs;
import com.asset.dailyappusermanagementservice.logger.DailyAppLogger;
import com.asset.dailyappusermanagementservice.models.user.UserModel;
import com.asset.dailyappusermanagementservice.models.user.UserProfileModel;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserExtractor  implements ResultSetExtractor<List<UserModel>> {

    @Override
    public List<UserModel> extractData(ResultSet rs) throws SQLException {
        try {
            List<UserModel> users = new ArrayList<>();
            while(rs.next())
            {
                UserModel user = new UserModel();
                UserProfileModel userProfile = new UserProfileModel();

                user.setUserId(rs.getInt(DatabaseStructs.ADM_USERS.USER_ID));
                user.setName(rs.getString(DatabaseStructs.ADM_USERS.NAME));
                user.setLockFlag(rs.getInt(DatabaseStructs.ADM_USERS.LOCK_FLAG));
                user.setProfileId(rs.getInt(DatabaseStructs.ADM_USERS.PROFILE_ID));
                user.setUsername(rs.getString(DatabaseStructs.ADM_USERS.USERNAME));

                userProfile.setId(user.getProfileId());
                userProfile.setName(rs.getString(DatabaseStructs.DAILY_PROFILES.PROFILE_NAME_ALIAS));

                user.setProfileModel(userProfile);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            DailyAppLogger.ERROR_LOGGER.error("SQL Exception ==> {}", e);
            DailyAppLogger.DEBUG_LOGGER.error("SQL Exception ==> {}", e);
            e.printStackTrace();
            return null;
        }
    }

}
