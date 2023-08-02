package com.asset.dailyapplookupservice.database.dao.imp;

import com.asset.dailyapplookupservice.constants.Queries;
import com.asset.dailyapplookupservice.defines.Defines;
import com.asset.dailyapplookupservice.defines.ErrorCodes;
import com.asset.dailyapplookupservice.exception.LookupException;
import com.asset.dailyapplookupservice.logger.DailyAppLogger;
import com.asset.dailyapplookupservice.manager.QueriesCache;
import com.asset.dailyapplookupservice.model.shared.TariffModel;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

@Component
public class TariffModelDAOImp {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final BeanPropertyRowMapper<TariffModel> tariffModelMapper = new BeanPropertyRowMapper<>(TariffModel.class);

    public List<TariffModel> getAllTariffModel() throws LookupException {
        String getAllTariffQuery = null;
        try {
            getAllTariffQuery = QueriesCache.allQueries.get(Queries.GET_ALL_TARIFF_MODEL.id);
            DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.GET_ALL_TARIFF_MODEL.id, getAllTariffQuery);
            return jdbcTemplate.query(getAllTariffQuery, tariffModelMapper);
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error while execute " + getAllTariffQuery);
            DailyAppLogger.ERROR_LOGGER.error("error while execute " + getAllTariffQuery, ex);
            ex.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public int updateTariffModel(TariffModel tariffModel) throws LookupException {
        String updateTariffQuery = null;
        try {
            updateTariffQuery = QueriesCache.allQueries.get(Queries.UPDATE_TARIFF_MODEL.id);
            DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.UPDATE_TARIFF_MODEL.id, updateTariffQuery);
            return jdbcTemplate.update(updateTariffQuery, tariffModel.getConsumerFlag(),
                    tariffModel.getEnterpriseFlag(), tariffModel.getPreFlag(),
                    tariffModel.getPostFlag(), tariffModel.getActivationSourceFlag(),
                    tariffModel.getDeacSourceFlag(), tariffModel.getHybirdFlag(),
                    tariffModel.getBundleType(), tariffModel.getTariffModelCode());
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error while execute " + updateTariffQuery);
            DailyAppLogger.ERROR_LOGGER.error("error while execute " + updateTariffQuery, ex);
            ex.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public int[] UpdateTariffModelBatch(List<TariffModel> tariffModelList) {
        String updatePriceGroup = QueriesCache.allQueries.get(Queries.UPDATE_TARIFF_MODEL.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.UPDATE_TARIFF_MODEL.id, updatePriceGroup);

        return jdbcTemplate.batchUpdate(updatePriceGroup, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) {
                try {
                    TariffModel tariffModel = tariffModelList.get(i);
                    if (tariffModel.getConsumerFlag() == null) {
                        ps.setString(1, "");
                    } else {
                        ps.setInt(1, tariffModel.getConsumerFlag());
                    }

                    if (tariffModel.getEnterpriseFlag() == null) {
                        ps.setString(2, "");
                    } else {
                        ps.setInt(2, tariffModel.getEnterpriseFlag());
                    }

                    if (tariffModel.getPreFlag() == null) {
                        ps.setString(3, "");
                    } else {
                        ps.setInt(3, tariffModel.getPreFlag());
                    }

                    if (tariffModel.getPostFlag() == null) {
                        ps.setString(4, "");
                    } else {
                        ps.setInt(4, tariffModel.getPostFlag());
                    }

                    if (tariffModel.getActivationSourceFlag() == null) {
                        ps.setString(5, "");
                    } else {
                        ps.setInt(5, tariffModel.getActivationSourceFlag());
                    }
                    if (tariffModel.getDeacSourceFlag() == null) {
                        ps.setString(6, "");
                    } else {
                        ps.setInt(6, tariffModel.getDeacSourceFlag());
                    }
                    if (tariffModel.getHybirdFlag() == null) {
                        ps.setString(7, "");
                    } else {
                        ps.setInt(7, tariffModel.getHybirdFlag());
                    }

                    ps.setString(8, tariffModel.getBundleType());
                    ps.setInt(9, tariffModel.getTariffModelCode());
                } catch (SQLException ex) {
                    DailyAppLogger.DEBUG_LOGGER.error("error while execute " + updatePriceGroup);
                    DailyAppLogger.ERROR_LOGGER.error("error while execute " + updatePriceGroup, ex);
                    ex.printStackTrace();
                    throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
                }
            }

            @Override
            public int getBatchSize() {
                return tariffModelList.size();
            }
        });
    }

}

