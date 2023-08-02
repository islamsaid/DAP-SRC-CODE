package com.asset.dailyappusermanagementservice.database.dao.imp;

import com.asset.dailyappusermanagementservice.constants.Queries;
import com.asset.dailyappusermanagementservice.database.dao.UserDAO;
import com.asset.dailyappusermanagementservice.database.extractors.UserExtractor;
import com.asset.dailyappusermanagementservice.defines.DatabaseStructs;
import com.asset.dailyappusermanagementservice.defines.Defines;
import com.asset.dailyappusermanagementservice.defines.ErrorCodes;
import com.asset.dailyappusermanagementservice.exception.UserManagementException;
import com.asset.dailyappusermanagementservice.logger.DailyAppLogger;
import com.asset.dailyappusermanagementservice.manager.QueriesCache;
import com.asset.dailyappusermanagementservice.models.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class UsersDaoImp implements UserDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private StringBuilder sqlQuery;
    private String query;
    public Integer retrieveUsersByProfileId(Integer profileId) throws UserManagementException {
        try {
            sqlQuery = new StringBuilder();
            sqlQuery.append("SELECT COUNT(*) FROM ").append(DatabaseStructs.ADM_USERS.TABLE_NAME)
                    .append(" WHERE ").append(DatabaseStructs.ADM_USERS.PROFILE_ID).append(" = ?")
                    .append(" AND ")
                    .append(DatabaseStructs.ADM_USERS.LOCK_FLAG).append("= 0");
            return jdbcTemplate.queryForObject(sqlQuery.toString(), Integer.class, profileId);
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error while executing: " + sqlQuery);
            DailyAppLogger.ERROR_LOGGER.error("error while executing: " + sqlQuery, ex);
            ex.printStackTrace();
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL, ex.getMessage());
        }
    }

    public List<UserModel> retrieveUsers(){
        try {
            query = QueriesCache.allQueries.get(Queries.GET_ALL_USERS.id);
            DailyAppLogger.DEBUG_LOGGER.debug("SQL Query#{} = {}", Queries.GET_ALL_USERS.id, query);
            return jdbcTemplate.query(query, new UserExtractor());
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error while execute get users query#{} = {} {}", Queries.GET_ALL_USERS.id, query, ex);
            DailyAppLogger.ERROR_LOGGER.error("error while execute get users query#{} = {}", Queries.GET_ALL_USERS.id, query);
            DailyAppLogger.ERROR_LOGGER.error(ex);
            ex.printStackTrace();
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL, ex.getMessage());
        }
    }

    @Override
    public UserModel retrieveUserById(Integer userId){
        try {
            query = QueriesCache.allQueries.get(Queries.GET_USER_BY_ID.id);
            DailyAppLogger.DEBUG_LOGGER.debug("SQL Query#{} = {}", Queries.GET_USER_BY_ID.id, query);
            return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(UserModel.class), userId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error retrieving user by id ", ex);
            DailyAppLogger.ERROR_LOGGER.error("error retrieving user query#{} = {} {}", Queries.GET_USER_BY_ID.id, query, ex);
            ex.printStackTrace();
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL, ex.getMessage());
        }
    }

    @Override
    public UserModel retrieveUserByUsername(String username){
        try {
            sqlQuery = new StringBuilder();
            sqlQuery.append("SELECT * FROM ").append(DatabaseStructs.ADM_USERS.TABLE_NAME)
                    .append(" WHERE LOWER(").append(DatabaseStructs.ADM_USERS.USERNAME).append(")")
                    .append(" = ?")
                    .append(" AND ")
                    .append(DatabaseStructs.ADM_USERS.LOCK_FLAG).append("= 0");
            DailyAppLogger.DEBUG_LOGGER.debug("SQL Query = {}", sqlQuery);
            return jdbcTemplate.queryForObject(sqlQuery.toString(), new BeanPropertyRowMapper<>(UserModel.class), username.toLowerCase());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("Database error {} {}", sqlQuery, ex);
            DailyAppLogger.ERROR_LOGGER.error("Database error {} {}", sqlQuery, ex);
            ex.printStackTrace();
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL);
        }
    }

    public Integer findUser(Integer userId) throws UserManagementException {
        try {
            sqlQuery = new StringBuilder();
            sqlQuery.append("SELECT COUNT(*) FROM ").append(DatabaseStructs.ADM_USERS.TABLE_NAME)
                    .append(" WHERE ").append(DatabaseStructs.ADM_USERS.USER_ID).append(" = ?")
                    .append(" AND ")
                    .append(DatabaseStructs.ADM_USERS.LOCK_FLAG).append("= 0");
            return jdbcTemplate.queryForObject(sqlQuery.toString(), Integer.class, userId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error while executing: " + sqlQuery);
            DailyAppLogger.ERROR_LOGGER.error("error while executing: " + sqlQuery, ex);
            ex.printStackTrace();
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL, ex.getMessage());
        }
    }

    @Override
    public int createUser(UserModel user) {
        try {
            query = QueriesCache.allQueries.get(Queries.Add_USER.id);
            DailyAppLogger.DEBUG_LOGGER.info("SQL Query#{} = {}", Queries.Add_USER.id, query);
            return jdbcTemplate.update(query, user.getName() ,user.getUsername(), user.getProfileId(), user.getLockFlag());
        }catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error while executing query#" + Queries.Add_USER.id + " : " + query, ex);
            DailyAppLogger.ERROR_LOGGER.error("error while executing query#" + Queries.Add_USER.id + " : " + query, ex);
            ex.printStackTrace();
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL);
        }
    }

    @Override
    public int updateUser(UserModel user, int id){
        try {
            query = QueriesCache.allQueries.get(Queries.UPDATE_USER.id);
            DailyAppLogger.DEBUG_LOGGER.debug("SQL Query#{} = {}  [updated-user]", Queries.UPDATE_USER.id, query);
            return jdbcTemplate.update(query, user.getName(), user.getUsername(),
                    user.getLockFlag(), user.getProfileId(), id);
        }catch (Exception ex){
            DailyAppLogger.DEBUG_LOGGER.error("error: executing updateUser ", ex);
            DailyAppLogger.ERROR_LOGGER.error("error: executing Query#{}={} {}", Queries.UPDATE_USER.id, query, ex );
            ex.printStackTrace();
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL);
        }
    }

    @Override
    public void deleteUser(int id){
        try {
            query = QueriesCache.allQueries.get(Queries.DELETE_USER_BY_ID.id);
            DailyAppLogger.DEBUG_LOGGER.info("SQL Query#{} = " + query, Queries.DELETE_USER_BY_ID);
            jdbcTemplate.update(query, id);
        }catch (Exception ex){
            DailyAppLogger.ERROR_LOGGER.error("error while execute deleteUser  ", ex);
            ex.printStackTrace();
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL);
        }
    }

    @Override
    public void clearUsers(){
        try {
            query = QueriesCache.allQueries.get(Queries.CLEAR_USERS.id);
            DailyAppLogger.DEBUG_LOGGER.info("SQL Query#{} = " + query, Queries.CLEAR_USERS.id);
            jdbcTemplate.update(query.toString());
        }catch (Exception ex){
            DailyAppLogger.DEBUG_LOGGER.debug("error while execute deleteUser");
            DailyAppLogger.ERROR_LOGGER.error("error while execute deleteUser  ", ex);
            ex.printStackTrace();
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL);
        }
    }
}

