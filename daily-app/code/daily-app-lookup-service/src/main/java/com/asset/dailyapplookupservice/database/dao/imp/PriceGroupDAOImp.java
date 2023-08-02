package com.asset.dailyapplookupservice.database.dao.imp;

import com.asset.dailyapplookupservice.configuration.Properties;
import com.asset.dailyapplookupservice.constants.Queries;
import com.asset.dailyapplookupservice.database.extractors.PriceGroupExtractor;
import com.asset.dailyapplookupservice.database.extractors.PriceGroupGroupBYIdExtractor;
import com.asset.dailyapplookupservice.defines.DatabaseStructs;
import com.asset.dailyapplookupservice.defines.Defines;
import com.asset.dailyapplookupservice.defines.ErrorCodes;
import com.asset.dailyapplookupservice.exception.LookupException;
import com.asset.dailyapplookupservice.logger.DailyAppLogger;
import com.asset.dailyapplookupservice.manager.QueriesCache;
import com.asset.dailyapplookupservice.model.request.priceGroup.AddPriceGroupGroupRequest;
import com.asset.dailyapplookupservice.model.request.priceGroup.GetPgGroupRequestModel;
import com.asset.dailyapplookupservice.model.request.priceGroup.UpdatePriceGroupResponse;
import com.asset.dailyapplookupservice.model.response.pricegroup.GetPgGroupModel;
import com.asset.dailyapplookupservice.model.shared.PgGroupModel;
import com.asset.dailyapplookupservice.model.shared.PriceGroupModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Component
public class PriceGroupDAOImp {
    private final BeanPropertyRowMapper<PgGroupModel> getAllPriceGroupGroups = new BeanPropertyRowMapper<>(PgGroupModel.class);
    @Autowired
    Properties properties;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PriceGroupExtractor priceGroupExtractor;
    @Autowired
    private PriceGroupGroupBYIdExtractor priceGroupGroupBYIdExtractor;

    public int UpdatePriceGroup(PriceGroupModel groupModel) throws LookupException {
        String updatePriceGroup = QueriesCache.allQueries.get(Queries.UPDATE_PRICE_GROUP.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.UPDATE_PRICE_GROUP.id, updatePriceGroup);
        try {
            return jdbcTemplate.update(updatePriceGroup, groupModel.getPgGroupKey().getPgGroupKey(), LocalDate.now(), groupModel.getPriceGroupCode());
        } catch (Exception ex) {
            int queryId = Queries.UPDATE_PRICE_GROUP.id;
            DailyAppLogger.DEBUG_LOGGER.error("database error: ", ex);
            DailyAppLogger.ERROR_LOGGER.error("error while execute # " + queryId + " " + updatePriceGroup, ex);
            ex.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public int updatePgGroup(PgGroupModel pgGroupModel) throws LookupException {
        String updatePgGroup = QueriesCache.allQueries.get(Queries.UPDATE_PG_GROUP.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.UPDATE_PG_GROUP.id, updatePgGroup);
        try {
            return jdbcTemplate.update(updatePgGroup, pgGroupModel.getPgGroup(), pgGroupModel.getShowFlag(), pgGroupModel.getDescription(), pgGroupModel.getPgGroupKey());
        } catch (Exception ex) {
            int queryId = Queries.UPDATE_PG_GROUP.id;
            DailyAppLogger.DEBUG_LOGGER.error("database error: ", ex);
            DailyAppLogger.ERROR_LOGGER.error("error while execute # " + queryId + " " + updatePgGroup, ex);
            ex.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public List<PriceGroupModel> getAll() throws LookupException {
        String getAllPriceGroupQuery = QueriesCache.allQueries.get(Queries.GET_ALL_GROUP.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.GET_ALL_GROUP.id, getAllPriceGroupQuery);
        try {
            return jdbcTemplate.query(getAllPriceGroupQuery, priceGroupExtractor);
        } catch (Exception ex) {
            int queryId = Queries.GET_ALL_GROUP.id;
            DailyAppLogger.DEBUG_LOGGER.error("database error: ", ex);
            DailyAppLogger.ERROR_LOGGER.error("error while execute # " + queryId + " " + getAllPriceGroupQuery, ex);
            ex.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public List<PgGroupModel> getAllPriceGroupGroups() {
        String getAllPriceGroupGroupsQuery = QueriesCache.allQueries.get(Queries.GET_PRICE_GROUP_GROUPS.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.GET_PRICE_GROUP_GROUPS.id, getAllPriceGroupGroupsQuery);
        try {
            return jdbcTemplate.query(getAllPriceGroupGroupsQuery, getAllPriceGroupGroups);
        } catch (Exception ex) {
            int queryId = Queries.GET_PRICE_GROUP_GROUPS.id;
            DailyAppLogger.DEBUG_LOGGER.error("database error: ", ex);
            DailyAppLogger.ERROR_LOGGER.error("error while execute # " + queryId + " " + getAllPriceGroupGroupsQuery, ex);
            ex.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public GetPgGroupModel getPriceGroupGroupById(GetPgGroupRequestModel pgGroup) {
        String getPriceGroupGroupByIdQuery = QueriesCache.allQueries.get(Queries.GET_PRICE_GROUP_GROUPS_ById.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.GET_PRICE_GROUP_GROUPS_ById.id, getPriceGroupGroupByIdQuery);
        try {
            GetPgGroupModel getPgGroupModel = jdbcTemplate.query(getPriceGroupGroupByIdQuery, priceGroupGroupBYIdExtractor, pgGroup.getPgGroupId());
            if (getPgGroupModel.getPgGroupModel() != null) {
                getPgGroupModel.getPgGroupModel().setPgGroupKey(pgGroup.getPgGroupId());
                return getPgGroupModel;
            }
            return null;
        } catch (Exception ex) {
            int queryId = Queries.GET_PRICE_GROUP_GROUPS_ById.id;
            DailyAppLogger.DEBUG_LOGGER.error("database error: ", ex);
            DailyAppLogger.ERROR_LOGGER.error("error while execute # " + queryId + " " + getPriceGroupGroupByIdQuery, ex);
            ex.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }


    public int getPgGroup(String pgGroupKey) throws LookupException {
        String getPgGroup = QueriesCache.allQueries.get(Queries.GET_PG_GROUP.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.GET_PG_GROUP.id, getPgGroup);
        try {
            return jdbcTemplate.queryForObject(getPgGroup, Integer.class, pgGroupKey);
        } catch (Exception ex) {
            int queryId = Queries.GET_PG_GROUP.id;
            DailyAppLogger.DEBUG_LOGGER.error("database error: ", ex);
            DailyAppLogger.ERROR_LOGGER.error("error while execute # " + queryId + " " + getPgGroup, ex);
            ex.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public Integer addPGGroups(AddPriceGroupGroupRequest addPriceGroupGroupRequest) {
        String addPgGroup = QueriesCache.allQueries.get(Queries.INSERT_PG_GROUP.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.INSERT_PG_GROUP.id, addPgGroup);
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((Connection connection) -> {
                PreparedStatement ps = connection.prepareStatement(addPgGroup, new String[]{DatabaseStructs.PG_GROUP.PG_GROUP_KEY});
                ps.setString(1, addPriceGroupGroupRequest.getPgGroupModel().getPgGroup());
                ps.setInt(2, addPriceGroupGroupRequest.getPgGroupModel().getShowFlag());
                ps.setString(3, addPriceGroupGroupRequest.getPgGroupModel().getDescription());

                return ps;
            }, keyHolder);
            int pgGroupId = keyHolder.getKey() == null ? null : keyHolder.getKey().intValue();
            addPriceGroupGroupRequest.getPgGroupModel().setPgGroupKey(String.valueOf(pgGroupId));
            return pgGroupId;
        } catch (Exception ex) {
            int queryId = Queries.INSERT_PG_GROUP.id;
            DailyAppLogger.DEBUG_LOGGER.error("database error: ", ex);
            DailyAppLogger.ERROR_LOGGER.error("error while execute # " + queryId + " " + addPgGroup, ex);
            ex.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public void UpdatePriceGroupBatch(List<UpdatePriceGroupResponse> priceGroupList) {
        String updatePriceGroup = QueriesCache.allQueries.get(Queries.UPDATE_PRICE_GROUP.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.UPDATE_PRICE_GROUP.id, updatePriceGroup);
        jdbcTemplate.batchUpdate(updatePriceGroup, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) {
                try {
                    UpdatePriceGroupResponse data = priceGroupList.get(i);


                    ps.setString(1, data.getPgGroupKey());
                    ps.setDate(2, Date.valueOf(LocalDate.now()));
                    ps.setString(3, data.getPriceGroupCode());


                } catch (SQLException ex) {
                    int queryId = Queries.UPDATE_PRICE_GROUP.id;
                    DailyAppLogger.DEBUG_LOGGER.error("database error: ", ex);
                    DailyAppLogger.ERROR_LOGGER.error("error while execute # " + queryId + " " + updatePriceGroup, ex);
                    ex.printStackTrace();
                    throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
                }
            }

            @Override
            public int getBatchSize() {
                return priceGroupList.size();
            }
        });

    }

    public void pgGroupExistInPriceGroup(String pgGroupId) {
        String getPgGroupUsingPriceGroupID = QueriesCache.allQueries.get(Queries.GET_PG_GROUP_USING_PRICE_GROUP_ID.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.GET_PG_GROUP_USING_PRICE_GROUP_ID.id, getPgGroupUsingPriceGroupID);

        try {
            String postPaidPgGroupId = jdbcTemplate.queryForObject(getPgGroupUsingPriceGroupID, String.class, properties.getPriceGroupKeyForGroupSelectionForPostPaid());
            DailyAppLogger.DEBUG_LOGGER.debug("postPaidPgGroupId  " + postPaidPgGroupId);

            String prePaidPgGroupId = jdbcTemplate.queryForObject(getPgGroupUsingPriceGroupID, String.class, properties.getPriceGroupKeyForGroupSelectionForPrePaid());
            DailyAppLogger.DEBUG_LOGGER.debug("prePaidPgGroupId  " + prePaidPgGroupId);

            if (postPaidPgGroupId == null || prePaidPgGroupId == null ||
                    postPaidPgGroupId.equals(pgGroupId) || prePaidPgGroupId.equals(pgGroupId)) {
                DailyAppLogger.DEBUG_LOGGER.error("pg group you are trying to delete is used by the configuration system ");
                throw new LookupException(ErrorCodes.ERROR.CANNOT_DELETE_PG_GROUP_USED_IN_CONFIGURATION_SYSTEM, Defines.SEVERITY.ERROR);
            }
        } catch (LookupException exception) {
            DailyAppLogger.DEBUG_LOGGER.error("error while execute deletePgGroup", getPgGroupUsingPriceGroupID + " error ", exception);
            DailyAppLogger.ERROR_LOGGER.error("error while execute deletePgGroup  " + exception.getMessage());
            throw new LookupException(ErrorCodes.ERROR.CANNOT_DELETE_PG_GROUP_USED_IN_CONFIGURATION_SYSTEM, Defines.SEVERITY.FATAL);
        } catch (Exception ex) {
            int queryId = Queries.GET_PG_GROUP_USING_PRICE_GROUP_ID.id;
            DailyAppLogger.DEBUG_LOGGER.error("database error: ", ex);
            DailyAppLogger.ERROR_LOGGER.error("error while execute # " + queryId + " " + getPgGroupUsingPriceGroupID, ex);
            ex.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL);
        }
    }

    public void setPgGroupKeyNullInPriceGroup(String pgGroupId) {
        String setPgGroupKeyNullInPriceGroup = QueriesCache.allQueries.get(Queries.SET_PG_GROUP_KEY_NULL_IN_PRICE_GROUP.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.SET_PG_GROUP_KEY_NULL_IN_PRICE_GROUP.id, setPgGroupKeyNullInPriceGroup);
        try {
            jdbcTemplate.update(setPgGroupKeyNullInPriceGroup, pgGroupId);
        } catch (Exception ex) {
            int queryId = Queries.SET_PG_GROUP_KEY_NULL_IN_PRICE_GROUP.id;
            DailyAppLogger.DEBUG_LOGGER.error("database error: ", ex);
            DailyAppLogger.ERROR_LOGGER.error("error while execute # " + queryId + " " + setPgGroupKeyNullInPriceGroup, ex);
            ex.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL);
        }
    }

    public void deletePgGroup(String pgGroupId) {
        String deletePgGroup = QueriesCache.allQueries.get(Queries.DELETE_PG_GROUP.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.DELETE_PG_GROUP.id, deletePgGroup);
        try {
            jdbcTemplate.update(deletePgGroup, pgGroupId);
        } catch (Exception ex) {
            int queryId = Queries.DELETE_PG_GROUP.id;
            DailyAppLogger.DEBUG_LOGGER.error("database error: ", ex);
            DailyAppLogger.ERROR_LOGGER.error("error while execute # " + queryId + " " + deletePgGroup, ex);
            ex.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL);
        }

    }

    public void assertPgGroupKeyExistenceForPriceGroup(String pgGroupKey) {
        String getPgGroupUsingPriceGroupID = QueriesCache.allQueries.get(Queries.GET_PG_GROUP_USING_PRICE_GROUP_ID.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.GET_PG_GROUP_USING_PRICE_GROUP_ID.id, getPgGroupUsingPriceGroupID);

        String updatePgGroupUsingPriceGroup = QueriesCache.allQueries.get(Queries.UPDATE_PG_GROUP_USING_PRICE_GROUP_KEY.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.UPDATE_PG_GROUP_USING_PRICE_GROUP_KEY.id, updatePgGroupUsingPriceGroup);

        try {
            String postPaidPgGroupId = jdbcTemplate.queryForObject(getPgGroupUsingPriceGroupID, String.class, properties.getPriceGroupKeyForGroupSelectionForPostPaid());
            String prePaidPgGroupId = jdbcTemplate.queryForObject(getPgGroupUsingPriceGroupID, String.class, properties.getPriceGroupKeyForGroupSelectionForPrePaid());
            DailyAppLogger.DEBUG_LOGGER.debug("check getPgGroup for post paid " + getPgGroupUsingPriceGroupID + "price group id " + properties.getPriceGroupKeyForGroupSelectionForPostPaid());
            DailyAppLogger.DEBUG_LOGGER.debug("check getPgGroup for pre paid " + getPgGroupUsingPriceGroupID + "price group id " + properties.getPriceGroupKeyForGroupSelectionForPrePaid());
            if (postPaidPgGroupId == null) {
                DailyAppLogger.DEBUG_LOGGER.debug(" post paid for price group can not be null , set pg group for postpaid using pg group id  ");
                jdbcTemplate.update(updatePgGroupUsingPriceGroup, pgGroupKey, properties.getPriceGroupKeyForGroupSelectionForPostPaid());
                throw new LookupException(ErrorCodes.WARRNING.PG_GROUP_CAN_NOT_BE_NULL_IN_POST_PAID, Defines.SEVERITY.WARNING);
            }
            if (prePaidPgGroupId == null) {
                DailyAppLogger.DEBUG_LOGGER.debug(" pre paid for price group can not be null , set pg group for pre paid  using pg group id  ");
                jdbcTemplate.update(updatePgGroupUsingPriceGroup, pgGroupKey, properties.getPriceGroupKeyForGroupSelectionForPrePaid());
                throw new LookupException(ErrorCodes.WARRNING.PG_GROUP_CAN_NOT_BE_NULL_IN_PRE_PAID, Defines.SEVERITY.WARNING);
            }
        } catch (LookupException e) {
            DailyAppLogger.DEBUG_LOGGER.debug(" pre paid for price group can not be null , set pg group for pre paid  using pg group id  ");
            throw new LookupException(ErrorCodes.WARRNING.PG_GROUP_CAN_NOT_BE_NULL_IN_POST_PAID_OR_PRE_PAID, Defines.SEVERITY.WARNING);
        } catch (Exception exception) {
            DailyAppLogger.DEBUG_LOGGER.debug("error while execute deletePgGroup", exception);
            DailyAppLogger.ERROR_LOGGER.error("error while execute deletePgGroup  " + exception);
            exception.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.FATAL);
        }
    }
}
