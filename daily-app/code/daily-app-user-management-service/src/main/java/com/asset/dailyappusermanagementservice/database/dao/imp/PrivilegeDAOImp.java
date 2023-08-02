package com.asset.dailyappusermanagementservice.database.dao.imp;

import com.asset.dailyappusermanagementservice.constants.Queries;
import com.asset.dailyappusermanagementservice.database.dao.CrudDAO;
import com.asset.dailyappusermanagementservice.defines.DatabaseStructs;
import com.asset.dailyappusermanagementservice.defines.Defines;
import com.asset.dailyappusermanagementservice.defines.ErrorCodes;
import com.asset.dailyappusermanagementservice.exception.UserManagementException;
import com.asset.dailyappusermanagementservice.logger.DailyAppLogger;
import com.asset.dailyappusermanagementservice.manager.QueriesCache;
import com.asset.dailyappusermanagementservice.models.shared.LkPrivilegeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

@Component
public class PrivilegeDAOImp implements CrudDAO<LkPrivilegeModel> {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<LkPrivilegeModel> lkPrivilegeMapper = new BeanPropertyRowMapper<>(LkPrivilegeModel.class);
    private final BeanPropertyRowMapper<LkPrivilegeModel> lkPrivilegesMapper = new BeanPropertyRowMapper<>(LkPrivilegeModel.class);

    String insertPrivilegeQuery;
    String retrievePrivilegeQuery;
    String retrievePrivilegesQuery;
    String updatePrivilegeQuery;
    String deletePrivilegeQuery;
    String findPrivilegeByIdQuery;
    String findProfileByPrivilegeIdQuery;

    @Override
    public Integer add(LkPrivilegeModel privilege) throws UserManagementException {
        try {
            insertPrivilegeQuery = QueriesCache.allQueries.get(Queries.ADD_PRIVILEGE.id);
            DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.ADD_PRIVILEGE.id, insertPrivilegeQuery);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((Connection connection) -> {
                PreparedStatement ps = connection.prepareStatement(
                        insertPrivilegeQuery, new String[]{DatabaseStructs.DAILY_PRIVILEGES.ID});
                ps.setString(1, privilege.getName());
                ps.setString(2, privilege.getPageName());
                ps.setString(3, privilege.getBackEndUrl());
                ps.setString(4, privilege.getDescription());

                return ps;
            }, keyHolder);
            Integer PrivilegeId = keyHolder.getKey() == null ? null : keyHolder.getKey().intValue();
            privilege.setId(PrivilegeId);
            return PrivilegeId;

        } catch (Exception ex) {
            int queryId = Queries.ADD_PRIVILEGE.id;
            DailyAppLogger.DEBUG_LOGGER.error("[privileges] Query#" + queryId + " Database error: ", ex);
            DailyAppLogger.ERROR_LOGGER.error("[privileges] Query#" + queryId + " Database error: ", ex);
            ex.printStackTrace();
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }

    }

    @Override
    public LkPrivilegeModel get(Integer privilegeId) throws UserManagementException {
        try {
            retrievePrivilegeQuery = QueriesCache.allQueries.get(Queries.GET_PRIVILEGE.id);
            DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.GET_PRIVILEGE.id, retrievePrivilegeQuery);

            return jdbcTemplate.queryForObject(retrievePrivilegeQuery, lkPrivilegeMapper, privilegeId);
        } catch (Exception ex) {
            int queryId = Queries.GET_PRIVILEGE.id;
            DailyAppLogger.DEBUG_LOGGER.error("[privileges] Query#" + queryId + " Database error: ", ex);
            DailyAppLogger.ERROR_LOGGER.error("[privileges] Query#" + queryId + " Database error: ", ex);
            ex.printStackTrace();
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @Override
    public int delete(Integer privilegeId) throws UserManagementException {
        deletePrivilegeQuery = QueriesCache.allQueries.get(Queries.DELETE_PRIVILEGE.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.DELETE_PRIVILEGE.id, deletePrivilegeQuery);
        try {
            return jdbcTemplate.update(deletePrivilegeQuery,
                    privilegeId);
        } catch (Exception ex) {
            int queryId = Queries.DELETE_PRIVILEGE.id;
            DailyAppLogger.DEBUG_LOGGER.error("[privileges] Query#" + queryId + " Database error: ", ex);
            DailyAppLogger.ERROR_LOGGER.error("[privileges] Query#" + queryId + " Database error: ", ex);
            ex.printStackTrace();
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @Override
    public int update(LkPrivilegeModel privilege) throws UserManagementException {
        updatePrivilegeQuery = QueriesCache.allQueries.get(Queries.UPDATE_PRIVILEGE.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.UPDATE_PRIVILEGE.id, updatePrivilegeQuery);
        try {
            return jdbcTemplate.update(updatePrivilegeQuery,
                    privilege.getName(), privilege.getPageName(),
                    privilege.getBackEndUrl(), privilege.getDescription(),
                    privilege.getId());
        } catch (Exception ex) {
            int queryId = Queries.UPDATE_PRIVILEGE.id;
            DailyAppLogger.DEBUG_LOGGER.error("[privileges] Query#" + queryId + " Database error: ", ex);
            DailyAppLogger.ERROR_LOGGER.error("[privileges] Query#" + queryId + " Database error: ", ex);
            ex.printStackTrace();
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @Override
    public List<LkPrivilegeModel> getAll() throws UserManagementException {
        try {
            retrievePrivilegesQuery = QueriesCache.allQueries.get(Queries.GET_ALL_PRIVILEGE.id);
            DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.GET_ALL_PRIVILEGE.id, retrievePrivilegesQuery);

            return jdbcTemplate.query(retrievePrivilegesQuery, lkPrivilegesMapper);
        } catch (Exception ex) {
            int queryId = Queries.GET_ALL_PRIVILEGE.id;
            DailyAppLogger.DEBUG_LOGGER.error("[privileges] Query#" + queryId + " Database error: ", ex);
            DailyAppLogger.ERROR_LOGGER.error("[privileges] Query#" + queryId + " Database error: ", ex);
            ex.printStackTrace();
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public Integer findPrivilegeById(Integer privilegeId) throws UserManagementException {
        try {
            findPrivilegeByIdQuery = QueriesCache.allQueries.get(Queries.FIND_PRIVILEGE_BY_ID.id);
            DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.FIND_PRIVILEGE_BY_ID.id, findPrivilegeByIdQuery);

            return jdbcTemplate.queryForObject(findPrivilegeByIdQuery, Integer.class, privilegeId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            int queryId = Queries.FIND_PRIVILEGE_BY_ID.id;
            DailyAppLogger.DEBUG_LOGGER.error("[privileges] Query#" + queryId + " Database error: ", ex);
            DailyAppLogger.ERROR_LOGGER.error("[privileges] Query#" + queryId + " Database error: ", ex);
            ex.printStackTrace();
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public int retrieveProfileByPrivilegeId(Integer privilegeId) {
        try {
            findProfileByPrivilegeIdQuery = QueriesCache.allQueries.get(Queries.GET_COUNT_OF_PROFILES_PRIVILEGE.id);
            DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.GET_COUNT_OF_PROFILES_PRIVILEGE.id, findProfileByPrivilegeIdQuery);
            return jdbcTemplate.queryForObject(findProfileByPrivilegeIdQuery, Integer.class, privilegeId);
        } catch (Exception ex) {
            int queryId = Queries.GET_COUNT_OF_PROFILES_PRIVILEGE.id;
            DailyAppLogger.DEBUG_LOGGER.error("[privileges] Query#" + queryId + " Database error: ", ex);
            DailyAppLogger.ERROR_LOGGER.error("[privileges] Query#" + queryId + " Database error: ", ex);
            ex.printStackTrace();
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }
}
