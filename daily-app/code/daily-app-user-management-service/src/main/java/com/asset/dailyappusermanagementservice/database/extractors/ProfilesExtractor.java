package com.asset.dailyappusermanagementservice.database.extractors;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.asset.dailyappusermanagementservice.defines.DatabaseStructs;
import com.asset.dailyappusermanagementservice.models.shared.LkPrivilegeModel;
import com.asset.dailyappusermanagementservice.models.user.UserProfileModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;


@Component
public class ProfilesExtractor implements ResultSetExtractor<HashMap<Integer, UserProfileModel>> {
    private Integer id;
    private String name;
    private String pageName;

    private String backEndUrl;
    private String description;
    @Override
    public HashMap<Integer, UserProfileModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {

        HashMap<Integer, UserProfileModel> profilesMap = new HashMap();
        while (resultSet.next()) {
            Integer profileId = resultSet.getInt(DatabaseStructs.DAILY_PROFILES.ID);
            Integer featureId = resultSet.getInt(DatabaseStructs.DAILY_PRIVILEGES.PRIVILEGE_ID_ALIAS);
            if (profilesMap.get(profileId) == null) {

                UserProfileModel profileModel = new UserProfileModel();
                profileModel.setId(resultSet.getInt(DatabaseStructs.DAILY_PROFILES.ID));
                profileModel.setName(resultSet.getString(DatabaseStructs.DAILY_PROFILES.NAME));
                ArrayList<LkPrivilegeModel> privileges = new ArrayList<>();

                if (!featureId.equals(0)) {
                    LkPrivilegeModel lkPrivilegeModel = new LkPrivilegeModel();
                    lkPrivilegeModel.setId(resultSet.getInt(DatabaseStructs.DAILY_PRIVILEGES.PRIVILEGE_ID_ALIAS));
                    lkPrivilegeModel.setName(resultSet.getString(DatabaseStructs.DAILY_PRIVILEGES.PRIVILEGE_NAME_ALIAS));
                    lkPrivilegeModel.setPageName(resultSet.getString(DatabaseStructs.DAILY_PRIVILEGES.PAGE_NAME));
                    lkPrivilegeModel.setBackEndUrl(resultSet.getString(DatabaseStructs.DAILY_PRIVILEGES.BACK_END_URL));
                    lkPrivilegeModel.setDescription(resultSet.getString(DatabaseStructs.DAILY_PRIVILEGES.DESCRIPTION));
                    privileges.add(lkPrivilegeModel);
                    profileModel.setPrivileges(privileges);

                }


                profilesMap.put(profileId, profileModel);

            } else if (!featureId.equals(0)) {
                UserProfileModel profileModel = profilesMap.get(profileId);

                LkPrivilegeModel lkPrivilegeModel = new LkPrivilegeModel();
                lkPrivilegeModel.setId(resultSet.getInt(DatabaseStructs.DAILY_PRIVILEGES.PRIVILEGE_ID_ALIAS));
                lkPrivilegeModel.setName(resultSet.getString(DatabaseStructs.DAILY_PRIVILEGES.PRIVILEGE_NAME_ALIAS));
                lkPrivilegeModel.setPageName(resultSet.getString(DatabaseStructs.DAILY_PRIVILEGES.PAGE_NAME));
                lkPrivilegeModel.setBackEndUrl(resultSet.getString(DatabaseStructs.DAILY_PRIVILEGES.BACK_END_URL));
                lkPrivilegeModel.setDescription(resultSet.getString(DatabaseStructs.DAILY_PRIVILEGES.DESCRIPTION));
                profileModel.getPrivileges().add(lkPrivilegeModel);
            }
        }

        return profilesMap;
    }

}
