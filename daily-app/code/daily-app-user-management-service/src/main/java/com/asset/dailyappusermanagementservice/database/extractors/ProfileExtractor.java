package com.asset.dailyappusermanagementservice.database.extractors;

import com.asset.dailyappusermanagementservice.defines.DatabaseStructs;
import com.asset.dailyappusermanagementservice.models.shared.LkPrivilegeModel;
import com.asset.dailyappusermanagementservice.models.user.UserProfileModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProfileExtractor implements ResultSetExtractor<UserProfileModel> {

    @Override
    public UserProfileModel extractData(ResultSet resultSet) throws SQLException, DataAccessException {

        UserProfileModel profile = null;
        LkPrivilegeModel feature;
        ArrayList<LkPrivilegeModel> privileges = null;

        while (resultSet.next()) {

            if (profile == null) {
                profile = new UserProfileModel();
                privileges = new ArrayList<>();

                profile.setId(resultSet.getInt(DatabaseStructs.DAILY_PROFILES.PROFILE_ID_ALIAS));
                profile.setName(resultSet.getString(DatabaseStructs.DAILY_PROFILES.PROFILE_NAME_ALIAS));
                profile.setIsActive(resultSet.getInt(DatabaseStructs.DAILY_PROFILES.IS_ACTIVE));

                profile.setPrivileges(privileges);
             }

            Integer featureId = resultSet.getInt(DatabaseStructs.DAILY_PRIVILEGES.PRIVILEGE_ID_ALIAS);

            if (!featureId.equals(0)) {
                feature = new LkPrivilegeModel();
                feature.setId(featureId);
                feature.setName(resultSet.getString(DatabaseStructs.DAILY_PRIVILEGES.PRIVILEGE_NAME_ALIAS));
                 feature.setPageName(resultSet.getString(DatabaseStructs.DAILY_PRIVILEGES.PAGE_NAME));
                feature.setBackEndUrl(resultSet.getString(DatabaseStructs.DAILY_PRIVILEGES.BACK_END_URL));
                feature.setDescription(resultSet.getString(DatabaseStructs.DAILY_PRIVILEGES.DESCRIPTION));

                privileges.add(feature);

            }
        }
        return profile;
    }
}
