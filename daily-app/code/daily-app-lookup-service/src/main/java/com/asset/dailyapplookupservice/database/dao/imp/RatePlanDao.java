package com.asset.dailyapplookupservice.database.dao.imp;

import com.asset.dailyapplookupservice.constants.Queries;
import com.asset.dailyapplookupservice.database.dao.preparedStatements.RatePlanGroupKeyPreparedStatement;
import com.asset.dailyapplookupservice.database.dao.preparedStatements.RatePlansBatchPreparedStatement;
import com.asset.dailyapplookupservice.database.extractors.RatePlanByIDExtractor;
import com.asset.dailyapplookupservice.database.extractors.RatePlanGroupsExtractor;
import com.asset.dailyapplookupservice.database.extractors.RatePlanModelExtractor;
import com.asset.dailyapplookupservice.database.extractors.RatePlansExtractor;
import com.asset.dailyapplookupservice.defines.DatabaseStructs;
import com.asset.dailyapplookupservice.defines.Defines;
import com.asset.dailyapplookupservice.defines.ErrorCodes;
import com.asset.dailyapplookupservice.exception.LookupException;
import com.asset.dailyapplookupservice.logger.DailyAppLogger;
import com.asset.dailyapplookupservice.manager.QueriesCache;
import com.asset.dailyapplookupservice.model.rateplan.RatePlanGroupModel;
import com.asset.dailyapplookupservice.model.rateplan.RatePlanModel;
import com.asset.dailyapplookupservice.model.response.rateplan.RatePlanResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;

@Component
public class RatePlanDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RatePlanByIDExtractor ratePlanByIDExtractor;

    private String sqlQuery;

    public List<RatePlanResponse> getAllRatePlans() {
        try {
            sqlQuery = QueriesCache.allQueries.get(Queries.GET_ALL_RATE_PLANS.id);
            DailyAppLogger.DEBUG_LOGGER.debug("SQL Query{}: {}", Queries.GET_ALL_RATE_PLANS.id, sqlQuery);
            return jdbcTemplate.query(sqlQuery, new RatePlansExtractor());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            int queryId = Queries.GET_ALL_RATE_PLANS.id;
            DailyAppLogger.DEBUG_LOGGER.error("Database Error: ", ex);
            DailyAppLogger.ERROR_LOGGER.error("Query#{} = {} Database Error: {}", queryId, sqlQuery, ex);
            ex.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL);
        }
    }

    public RatePlanModel getRatePlanByCode(String rpCode) {
        try {
            sqlQuery = QueriesCache.allQueries.get(Queries.GET_RATE_PLAN_BY_CODE.id);
            DailyAppLogger.DEBUG_LOGGER.debug("SQL Query{}: {}", Queries.GET_RATE_PLAN_BY_CODE.id, sqlQuery);
            return jdbcTemplate.query(sqlQuery, new RatePlanModelExtractor(), rpCode);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            int queryId = Queries.GET_ALL_RATE_PLANS.id;
            DailyAppLogger.DEBUG_LOGGER.error("Database Error: ", ex);
            DailyAppLogger.ERROR_LOGGER.error("Query#{} = {} Database Error: {}", queryId, sqlQuery, ex);
            ex.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL);
        }
    }

    public void updateRatePlan(RatePlanModel ratePlan) {
        try {
            sqlQuery = QueriesCache.allQueries.get(Queries.UPDATE_RATE_PLAN.id);
            DailyAppLogger.DEBUG_LOGGER.debug("SQL Query#{}: {}", Queries.UPDATE_RATE_PLAN.id, sqlQuery);
            jdbcTemplate.update(sqlQuery, ratePlan.getRatePlanGroupKey(), ratePlan.getShowFlag(), ratePlan.getRatePlanCode());
        } catch (Exception ex) {
            int queryId = Queries.UPDATE_RATE_PLAN.id;
            DailyAppLogger.DEBUG_LOGGER.error("Database Error: ", ex);
            DailyAppLogger.ERROR_LOGGER.error("Query#{} = {} Database Error: {}", queryId, sqlQuery, ex);
            ex.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL);
        }
    }

    public void ratePlansBatchUpdate(List<RatePlanModel> ratePlanModelList) {
        try {
            sqlQuery = QueriesCache.allQueries.get(Queries.UPDATE_RATE_PLAN.id);
            DailyAppLogger.DEBUG_LOGGER.debug("SQL Query#{}: {}", Queries.UPDATE_RATE_PLAN.id, sqlQuery);
            jdbcTemplate.batchUpdate(sqlQuery, new RatePlansBatchPreparedStatement(ratePlanModelList));
        } catch (Exception ex) {
            int queryId = Queries.UPDATE_RATE_PLAN.id;
            DailyAppLogger.DEBUG_LOGGER.error("Database Error: ", ex);
            DailyAppLogger.ERROR_LOGGER.error("Query#{} = {} Database Error: {}", queryId, sqlQuery, ex);
            ex.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL);
        }
    }

    public void ratePlansGroupKeysBatchUpdate(List<RatePlanModel> ratePlanModelList) {
        try {
            sqlQuery = QueriesCache.allQueries.get(Queries.UPDATE_RP_GROUP_KEY_IN_RATE_PLANE.id);
            DailyAppLogger.DEBUG_LOGGER.debug("SQL Query#{}: {}", Queries.UPDATE_RP_GROUP_KEY_IN_RATE_PLANE.id, sqlQuery);
            jdbcTemplate.batchUpdate(sqlQuery, new RatePlanGroupKeyPreparedStatement(ratePlanModelList));
        } catch (Exception e) {
            int queryId = Queries.UPDATE_RP_GROUP_KEY_IN_RATE_PLANE.id;
            DailyAppLogger.DEBUG_LOGGER.error("[RPG] Database Error: ", e);
            DailyAppLogger.ERROR_LOGGER.error("[RPG] query{} = {} Database Error: {}", queryId, sqlQuery, e);
            e.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL);
        }
    }

    public void setRatePlanGroupKeyInRatePlanByNull(Integer groupKey) {
        try {
            sqlQuery = QueriesCache.allQueries.get(Queries.REMOVE_RATE_PLAN_GROUP_KEY_FROM_RATE_PLAN.id);
            DailyAppLogger.DEBUG_LOGGER.debug("SQL Query#{}: {}", Queries.REMOVE_RATE_PLAN_GROUP_KEY_FROM_RATE_PLAN.id, sqlQuery);
            jdbcTemplate.update(sqlQuery, groupKey);
        } catch (Exception e) {
            int queryId = Queries.REMOVE_RATE_PLAN_GROUP_KEY_FROM_RATE_PLAN.id;
            DailyAppLogger.ERROR_LOGGER.error("[RPG] query{} = {} Database Error: {}", queryId, sqlQuery, e);
            e.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL);
        }
    }

    /*-----------Rate Plan Groups--------------*/
    public List<RatePlanGroupModel> getAllRatePlanGroups() {
        try {
            sqlQuery = QueriesCache.allQueries.get(Queries.GET_ALL_RATE_PLAN_GROUPS.id);
            DailyAppLogger.DEBUG_LOGGER.debug("SQL Query#{}: {}", Queries.GET_ALL_RATE_PLAN_GROUPS.id, sqlQuery);
            return jdbcTemplate.query(sqlQuery, new RatePlanGroupsExtractor());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            int queryId = Queries.GET_ALL_RATE_PLAN_GROUPS.id;
            DailyAppLogger.DEBUG_LOGGER.error("[RPG] database error: ", e);
            DailyAppLogger.ERROR_LOGGER.error("[RPG] database error: query#{} = {}  {}", queryId, sqlQuery, e);
            e.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL);
        }
    }

    public List<RatePlanGroupModel> getAllRatePlanGroupsWithRatePlans() {
        try {
            sqlQuery = QueriesCache.allQueries.get(Queries.GET_RATE_PLAN_GROUPS_WITH_RP.id);
            DailyAppLogger.DEBUG_LOGGER.debug("SQL Query#{}: {}", Queries.GET_RATE_PLAN_GROUPS_WITH_RP.id, sqlQuery);
            return jdbcTemplate.query(sqlQuery, new RatePlanGroupsExtractor());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            int queryId = Queries.GET_RATE_PLAN_GROUPS_WITH_RP.id;
            DailyAppLogger.DEBUG_LOGGER.error("Database Error: ", ex);
            DailyAppLogger.ERROR_LOGGER.error("Query#{} = {} Database Error: {}", queryId, sqlQuery, ex);
            ex.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL);
        }
    }

    public RatePlanGroupModel getRatePlanGroupByKey(Integer key) {
        try {
            sqlQuery = QueriesCache.allQueries.get(Queries.GET_RATE_PLAN_GROUP_BY_ID_WITH_RATE_PLANS.id);
            DailyAppLogger.DEBUG_LOGGER.debug("SQL Query#{}: {}", Queries.GET_RATE_PLAN_GROUP_BY_ID_WITH_RATE_PLANS.id, sqlQuery);
            return jdbcTemplate.query(sqlQuery, ratePlanByIDExtractor, key);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            int queryId = Queries.GET_RATE_PLAN_GROUP_BY_ID_WITH_RATE_PLANS.id;
            DailyAppLogger.DEBUG_LOGGER.error("[RPG] Query#" + queryId + " Database error: ", e);
            DailyAppLogger.ERROR_LOGGER.error("[RPG] Query#" + queryId + " Database error: ", e);
            e.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL);
        }
    }

    public Integer addRatePlanGroup(RatePlanGroupModel groupModel) {
        try {
            sqlQuery = QueriesCache.allQueries.get(Queries.ADD_RATE_PLAN_GROUP.id);
            DailyAppLogger.DEBUG_LOGGER.debug("SQL Query#{}: {}", Queries.ADD_RATE_PLAN_GROUP.id, sqlQuery);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlQuery, new String[]
                        {DatabaseStructs.RATE_PLAN_GROUP.RATE_PLAN_GROUP_KEY});
                ps.setString(1, groupModel.getRatePlanGroup());
                ps.setInt(2, groupModel.getShowFlag());
                ps.setString(3, groupModel.getDescription());

                return ps;
            }, keyHolder);
            DailyAppLogger.DEBUG_LOGGER.debug("[RPG] Inserted with new key = " + keyHolder.getKey().intValue());
            return keyHolder.getKey().intValue();
        } catch (Exception ex) {
            int queryId = Queries.ADD_RATE_PLAN_GROUP.id;
            DailyAppLogger.DEBUG_LOGGER.error("Database Error: ", ex);
            DailyAppLogger.ERROR_LOGGER.error("Query#{} = {} Database Error: {}", queryId, sqlQuery, ex);
            ex.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL);
        }
    }

    public void updateRatePlanGroup(RatePlanGroupModel groupModel) {
        try {
            sqlQuery = QueriesCache.allQueries.get(Queries.UPDATE_RATE_PLAN_GROUP.id);
            DailyAppLogger.DEBUG_LOGGER.debug("SQL Query#{}: {}", Queries.UPDATE_RATE_PLAN_GROUP.id, sqlQuery);
            jdbcTemplate.update(sqlQuery, groupModel.getRatePlanGroup(), groupModel.getShowFlag(),
                    groupModel.getDescription(), groupModel.getRatePlanGroupKey());
        } catch (Exception ex) {
            int queryId = Queries.UPDATE_RATE_PLAN_GROUP.id;
            DailyAppLogger.DEBUG_LOGGER.error("Database Error: ", ex);
            DailyAppLogger.ERROR_LOGGER.error("Query#{} = {} Database Error: {}", queryId, sqlQuery, ex);
            ex.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL);
        }
    }

    public void deleteRatePlanGroup(Integer ratePlanGroupKey) {
        try {
            sqlQuery = QueriesCache.allQueries.get(Queries.DELETE_RATE_PLAN_GROUP.id);
            DailyAppLogger.DEBUG_LOGGER.debug("SQL Query#{}: {}", Queries.DELETE_RATE_PLAN_GROUP.id, sqlQuery);
            jdbcTemplate.update(sqlQuery, ratePlanGroupKey);
        } catch (Exception ex) {
            int queryId = Queries.DELETE_RATE_PLAN_GROUP.id;
            DailyAppLogger.DEBUG_LOGGER.error("Database Error: ", ex);
            DailyAppLogger.ERROR_LOGGER.error("Query#{} = {} Database Error: {}", queryId, sqlQuery, ex);
            ex.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL);
        }
    }
}
