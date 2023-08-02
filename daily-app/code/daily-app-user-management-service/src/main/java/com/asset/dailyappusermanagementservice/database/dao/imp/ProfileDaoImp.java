package com.asset.dailyappusermanagementservice.database.dao.imp;

import com.asset.dailyappusermanagementservice.constants.Queries;
import com.asset.dailyappusermanagementservice.database.dao.CrudDAO;
import com.asset.dailyappusermanagementservice.database.extractors.AllProfilesExtractor;
import com.asset.dailyappusermanagementservice.database.extractors.ProfileExtractor;
import com.asset.dailyappusermanagementservice.database.extractors.ProfilesExtractor;
import com.asset.dailyappusermanagementservice.defines.DatabaseStructs;
import com.asset.dailyappusermanagementservice.defines.Defines;
import com.asset.dailyappusermanagementservice.defines.ErrorCodes;
import com.asset.dailyappusermanagementservice.exception.UserManagementException;
import com.asset.dailyappusermanagementservice.logger.DailyAppLogger;
import com.asset.dailyappusermanagementservice.manager.QueriesCache;
import com.asset.dailyappusermanagementservice.models.shared.LkPrivilegeModel;
import com.asset.dailyappusermanagementservice.models.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

@Component
public class ProfileDaoImp implements CrudDAO<UserProfileModel> {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ProfilesExtractor profilesExtractor;
    @Autowired
    private AllProfilesExtractor allProfilesExtractor;
    private final BeanPropertyRowMapper<UserProfileModel> profileMapper = new BeanPropertyRowMapper<>(UserProfileModel.class);
    private String retrieveProfilesQuery;
    private String retrieveProfilesWithFeaturesQuery;
    private String retrieveUserProfileWithFeaturesQuery;
    private String deleteProfileQuery;
    private String updateProfileQuery;

    private String findProfileByIdQuery;
    private String findProfileByNameQuery;
    private String addProfileFeaturesQuery;
    private String deleteProfileFeaturesQuery;
    private String insertProfileQuery;
    private String retrieveProfileQuery;
    @Autowired
    private ProfileExtractor profileExtractor;

    public void IfProfileNameExist(UserProfileModel profile) throws UserManagementException {
        DailyAppLogger.DEBUG_LOGGER.debug("check profile name = [{}] existence", profile.getName());
        if (IfProfileNameExist(profile.getName()) != null) {
            DailyAppLogger.DEBUG_LOGGER.error("profile name Should be Unique");
            DailyAppLogger.ERROR_LOGGER.error("profile name  Should be Unique");
            throw new UserManagementException(ErrorCodes.ERROR.PROFILE_NAME_SHOULD_BE_UNIQUE, Defines.SEVERITY.ERROR);
        }
        DailyAppLogger.DEBUG_LOGGER.debug("profile name does not exist in the db");

    }

    private void IfProfileNameExistForUpdate(UserProfileModel profile) throws UserManagementException {
        DailyAppLogger.DEBUG_LOGGER.debug("check profile name = [{}] existence", profile.getName());
        UserProfileModel profileModel = IfProfileNameExist(profile.getName());
        if (profileModel != null && profileModel.getId() != profile.getId()) {
            DailyAppLogger.DEBUG_LOGGER.error("profile name Should be Unique");
            DailyAppLogger.ERROR_LOGGER.error("profile name  Should be Unique");
            throw new UserManagementException(ErrorCodes.ERROR.PROFILE_NAME_SHOULD_BE_UNIQUE, Defines.SEVERITY.ERROR);
        }
    }

    @Override
    public Integer add(UserProfileModel profile) throws UserManagementException {
        IfProfileNameExist(profile);
        try {
            insertProfileQuery = QueriesCache.allQueries.get(Queries.ADD_PROFILE.id);
            DailyAppLogger.DEBUG_LOGGER.debug("sql query#{} = {}", Queries.ADD_PROFILE.id, insertProfileQuery);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((Connection connection) -> {
                PreparedStatement ps = connection.prepareStatement(
                        insertProfileQuery
                        , new String[]{DatabaseStructs.DAILY_PROFILES.ID});
                ps.setString(1, profile.getName());
                ps.setDate(2, Date.valueOf(LocalDate.now()));
                ps.setInt(3, profile.getCreatedBY());
                return ps;
            }, keyHolder);
            Integer profileId = keyHolder.getKey() == null ? null : keyHolder.getKey().intValue();
            DailyAppLogger.DEBUG_LOGGER.debug("Created profile with #id = " + profileId);
            profile.setId(profileId);
            return profileId;

        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error while executing: " + insertProfileQuery, ex);
            DailyAppLogger.ERROR_LOGGER.error("error executing query#{} =: {}   {}", Queries.ADD_PROFILE.id, insertProfileQuery, ex);
            ex.printStackTrace();
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @Override
    public UserProfileModel get(Integer profileId) throws UserManagementException {
        try {
            retrieveProfileQuery = QueriesCache.allQueries.get(Queries.GET_PROFILE.id);
            DailyAppLogger.DEBUG_LOGGER.debug("Query#{} = {}", Queries.GET_PROFILE.id, retrieveProfileQuery);
            return jdbcTemplate.query(
                    retrieveProfileQuery, profileExtractor, profileId);
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error while execute query#{} -> " + retrieveProfileQuery, Queries.GET_PROFILE.id, ex);
            DailyAppLogger.ERROR_LOGGER.error("error while execute query#{} -> " + retrieveProfileQuery, Queries.GET_PROFILE.id);
            DailyAppLogger.ERROR_LOGGER.error(ex);
            ex.printStackTrace();
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public UserProfileModel getActiveProfile(Integer profileId) throws UserManagementException {
        try {
            retrieveProfileQuery = QueriesCache.allQueries.get(Queries.get_active_profile.id);

            return jdbcTemplate.query(
                    retrieveProfileQuery, profileExtractor, profileId);
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error while execute " + retrieveProfileQuery);
            DailyAppLogger.ERROR_LOGGER.error("error while execute " + retrieveProfileQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @Override
    public List<UserProfileModel> getAll() throws UserManagementException {
        try {
            retrieveProfilesQuery = QueriesCache.allQueries.get(Queries.GET_ALL_PROFILES.id);
            DailyAppLogger.DEBUG_LOGGER.debug("query#{} = {}", Queries.GET_ALL_PROFILES.id, retrieveProfilesQuery);
            return jdbcTemplate.query(retrieveProfilesQuery, profileMapper);
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error while executing query" + Queries.GET_ALL_PROFILES.id + " = " + retrieveProfilesQuery, ex);
            DailyAppLogger.ERROR_LOGGER.error("error executing query" + Queries.GET_ALL_PROFILES.id + " = " + retrieveProfilesQuery, ex);
            ex.printStackTrace();
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @Override
    public int delete(Integer profileId) throws UserManagementException {
        try {
            deleteProfileQuery = QueriesCache.allQueries.get(Queries.DELETE_PROFILE.id);
            DailyAppLogger.DEBUG_LOGGER.debug("SQL query#{} = {}", Queries.DELETE_PROFILE.id, deleteProfileQuery);
            return jdbcTemplate.update(deleteProfileQuery, profileId);
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error executing: " + deleteProfileQuery, ex);
            DailyAppLogger.ERROR_LOGGER.error("error executing query#{}: {}  {}", Queries.DELETE_PROFILE.id, deleteProfileQuery, ex);
            ex.printStackTrace();
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @Override
    public int update(UserProfileModel profile) throws UserManagementException {
        IfProfileNameExistForUpdate(profile);
        updateProfileQuery = QueriesCache.allQueries.get(Queries.UPDATE_PROFILE.id);
        try {

            return jdbcTemplate.update(updateProfileQuery,
                    profile.getName(),
                    profile.getIsActive(),
                    profile.getId()
            );
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error while executing: " + updateProfileQuery);
            DailyAppLogger.ERROR_LOGGER.error("error while executing: " + updateProfileQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public HashMap<Integer, UserProfileModel> retrieveProfilesWithFeatures() throws UserManagementException {
        try {
            if (retrieveProfilesWithFeaturesQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("SELECT *")
                        .append(" FROM ")
                        .append(DatabaseStructs.DAILY_PROFILES.TABLE_NAME)
                        .append(" LEFT JOIN ")
                        .append(DatabaseStructs.DAILY_PROFILE_PRIVILEGES.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.DAILY_PROFILE_PRIVILEGES.TABLE_NAME).append(".").append(DatabaseStructs.DAILY_PROFILE_PRIVILEGES.PROFILE_ID)
                        .append(" = ")
                        .append(DatabaseStructs.DAILY_PROFILES.TABLE_NAME).append(".").append(DatabaseStructs.DAILY_PROFILES.ID)
                        .append(" LEFT JOIN ")
                        .append(DatabaseStructs.DAILY_PRIVILEGES.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.DAILY_PRIVILEGES.TABLE_NAME).append(".").append(DatabaseStructs.DAILY_PRIVILEGES.ID)
                        .append(" = ")
                        .append(DatabaseStructs.DAILY_PROFILE_PRIVILEGES.TABLE_NAME).append(".").append(DatabaseStructs.DAILY_PROFILE_PRIVILEGES.PRIVILEGES_ID);
                retrieveProfilesWithFeaturesQuery = query.toString();
            }
            DailyAppLogger.DEBUG_LOGGER.info("QUERY was injected");
            DailyAppLogger.DEBUG_LOGGER.debug("sql query = " + retrieveProfilesWithFeaturesQuery);
            return jdbcTemplate.query(retrieveProfilesWithFeaturesQuery, profilesExtractor);
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error executing: " + retrieveProfilesWithFeaturesQuery, ex);
            DailyAppLogger.ERROR_LOGGER.error("error executing " + retrieveProfilesWithFeaturesQuery, ex);
            ex.printStackTrace();
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public int deleteProfileFeatures(Integer profileId) throws UserManagementException {
        try {
            deleteProfileFeaturesQuery = QueriesCache.allQueries.get(Queries.deleteProfileFeaturesQuery.id);
            DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.deleteProfileFeaturesQuery.id, deleteProfileFeaturesQuery);
            return jdbcTemplate.update(deleteProfileFeaturesQuery, profileId);
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error executing: " + deleteProfileFeaturesQuery, ex);
            DailyAppLogger.ERROR_LOGGER.error("error executing query{}: {}  {}", Queries.deleteProfileFeaturesQuery.id, deleteProfileFeaturesQuery, ex);
            ex.printStackTrace();
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public int[] deleteProfileFeatures(Integer profileId, List<LkPrivilegeModel> features) throws UserManagementException {

        try {
            if (deleteProfileFeaturesQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("DELETE FROM ").append(DatabaseStructs.DAILY_PROFILE_PRIVILEGES.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.DAILY_PROFILE_PRIVILEGES.PROFILE_ID).append(" = ? ")
                        .append(" AND ")
                        .append(DatabaseStructs.DAILY_PROFILE_PRIVILEGES.PRIVILEGES_ID).append(" = ?");


                deleteProfileFeaturesQuery = query.toString();
            }

            try (PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(deleteProfileFeaturesQuery)) {
                for (LkPrivilegeModel feature : features) {
                    int index = 1;

                    pstmt.setInt(index++, profileId);
                    pstmt.setInt(index++, feature.getId());

                    pstmt.addBatch();
                }

                return pstmt.executeBatch();
            }


        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error while executing: " + deleteProfileFeaturesQuery);
            DailyAppLogger.ERROR_LOGGER.error("error while executing: " + deleteProfileFeaturesQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public Integer findProfileById(Integer profileId) throws UserManagementException {
        try {
            findProfileByIdQuery = QueriesCache.allQueries.get(Queries.FIND_PROFILE_IF_EXIST.id);
            DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.FIND_PROFILE_IF_EXIST, findProfileByIdQuery);
            return jdbcTemplate.queryForObject(findProfileByIdQuery, Integer.class, profileId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error executing: " + findProfileByIdQuery, ex);
            DailyAppLogger.ERROR_LOGGER.error("error executing query#{}= {} : {}", Queries.FIND_PROFILE_IF_EXIST.id, findProfileByIdQuery, ex);
            ex.printStackTrace();
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    private UserProfileModel IfProfileNameExist(String profileName) throws UserManagementException {
        findProfileByNameQuery = QueriesCache.allQueries.get(Queries.FIND_PROFILE_NAME_IF_EXIST.id);
        try {
            return jdbcTemplate.queryForObject(findProfileByNameQuery, profileMapper, profileName);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error while executing: " + findProfileByNameQuery);
            DailyAppLogger.ERROR_LOGGER.error("error while executing: " + findProfileByNameQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public int[] addProfileFeatures(Integer profileId, List<LkPrivilegeModel> features) throws UserManagementException {
        addProfileFeaturesQuery = QueriesCache.allQueries.get(Queries.INSERT_DAILY_PROFILE_PRIVILEGES.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Query#{} = {}", Queries.INSERT_DAILY_PROFILE_PRIVILEGES.id, addProfileFeaturesQuery);
        try (PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(addProfileFeaturesQuery)) {
            for (LkPrivilegeModel feature : features) {
                int index = 1;

                pstmt.setInt(index++, profileId);
                pstmt.setInt(index++, feature.getId());

                pstmt.addBatch();
            }

            return pstmt.executeBatch();
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error executing: " + addProfileFeaturesQuery, ex);
            DailyAppLogger.ERROR_LOGGER.error("error executing: " + addProfileFeaturesQuery, ex);
            ex.printStackTrace();
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }


}

