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
public class AllProfilesExtractor implements ResultSetExtractor<List<UserProfileModel>> {
    @Override
    public List<UserProfileModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<UserProfileModel> profilesMap = new ArrayList<>();
        while (resultSet.next()) {
            UserProfileModel profileModel = new UserProfileModel();
                profileModel.setId(resultSet.getInt(DatabaseStructs.DAILY_PROFILES.ID));
                profileModel.setName(resultSet.getString(DatabaseStructs.DAILY_PROFILES.NAME));
                ArrayList<LkPrivilegeModel> privileges = new ArrayList<>();
                    LkPrivilegeModel lkPrivilegeModel = new LkPrivilegeModel();
                    lkPrivilegeModel.setId(resultSet.getInt(DatabaseStructs.DAILY_PRIVILEGES.PRIVILEGE_ID_ALIAS));
                    lkPrivilegeModel.setName(resultSet.getString(DatabaseStructs.DAILY_PRIVILEGES.PRIVILEGE_NAME_ALIAS));
                    lkPrivilegeModel.setPageName(resultSet.getString(DatabaseStructs.DAILY_PRIVILEGES.PAGE_NAME));
                    lkPrivilegeModel.setBackEndUrl(resultSet.getString(DatabaseStructs.DAILY_PRIVILEGES.BACK_END_URL));
                    lkPrivilegeModel.setDescription(resultSet.getString(DatabaseStructs.DAILY_PRIVILEGES.DESCRIPTION));
                    privileges.add(lkPrivilegeModel);
                    profileModel.setPrivileges(privileges);
                profilesMap.add(profileModel);

            }
        return profilesMap;
    }

}
