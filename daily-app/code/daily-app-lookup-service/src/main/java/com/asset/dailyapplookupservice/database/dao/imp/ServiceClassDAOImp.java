package com.asset.dailyapplookupservice.database.dao.imp;

import com.asset.dailyapplookupservice.constants.Queries;
import com.asset.dailyapplookupservice.defines.Defines;
import com.asset.dailyapplookupservice.defines.ErrorCodes;
import com.asset.dailyapplookupservice.exception.LookupException;
import com.asset.dailyapplookupservice.logger.DailyAppLogger;
import com.asset.dailyapplookupservice.manager.QueriesCache;
import com.asset.dailyapplookupservice.model.shared.ServiceClassModel;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

@Component
public class ServiceClassDAOImp {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final BeanPropertyRowMapper<ServiceClassModel> serviceClassMapper = new BeanPropertyRowMapper<>(ServiceClassModel.class);

    public List<ServiceClassModel> getAllServiceClass() throws LookupException {
        String getAllServiceClassQuery = null;
        try {
            getAllServiceClassQuery = QueriesCache.allQueries.get(Queries.GET_ALL_SERVICE_CLASS.id);
            DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.GET_ALL_SERVICE_CLASS.id, getAllServiceClassQuery);
            return jdbcTemplate.query(getAllServiceClassQuery, serviceClassMapper);
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error while execute " + getAllServiceClassQuery);
            DailyAppLogger.ERROR_LOGGER.error("error while execute " + getAllServiceClassQuery, ex);
            ex.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public int updateServiceClass(ServiceClassModel serviceClassModel) throws LookupException {
        String updateServiceClassQuery = null;
        try {
            updateServiceClassQuery = QueriesCache.allQueries.get(Queries.UPDATE_SERVICE_CLASS.id);
            DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.UPDATE_SERVICE_CLASS.id, updateServiceClassQuery);
            return jdbcTemplate.update(updateServiceClassQuery, serviceClassModel.getConsumerFlag(),
                    serviceClassModel.getEnterpriseFlag(), serviceClassModel.getPreFlag(),
                    serviceClassModel.getPostFlag(), serviceClassModel.getActivationSourceFlag(),
                    serviceClassModel.getDeacSourceFlag(), serviceClassModel.getHybirdFlag(),
                    serviceClassModel.getBundleType(), serviceClassModel.getServiceClassCode());
        } catch (Exception ex) {
            DailyAppLogger.DEBUG_LOGGER.error("error while execute " + updateServiceClassQuery);
            DailyAppLogger.ERROR_LOGGER.error("error while execute " + updateServiceClassQuery, ex);
            ex.printStackTrace();
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public int[] UpdateServiceClassBatch(List<ServiceClassModel> serviceClassList) {
        String updatePriceGroup = QueriesCache.allQueries.get(Queries.UPDATE_SERVICE_CLASS.id);
        DailyAppLogger.DEBUG_LOGGER.debug("Sql Query#{} = {}", Queries.UPDATE_SERVICE_CLASS.id, updatePriceGroup);

        return jdbcTemplate.batchUpdate(updatePriceGroup, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) {
                try {
                    ServiceClassModel serviceClass = serviceClassList.get(i);
                    if (serviceClass.getConsumerFlag() == null) {
                        ps.setString(1, "");
                    } else {
                        ps.setInt(1, serviceClass.getConsumerFlag());
                    }
                    if (serviceClass.getEnterpriseFlag() == null) {
                        ps.setString(2, "");
                    } else {
                        ps.setInt(2, serviceClass.getEnterpriseFlag());
                    }
                    if (serviceClass.getPreFlag() == null) {
                        ps.setString(3, "");
                    } else {
                        ps.setInt(3, serviceClass.getPreFlag());
                    }
                    if (serviceClass.getPostFlag() == null) {
                        ps.setString(4, "");
                    } else {
                        ps.setInt(4, serviceClass.getPostFlag());
                    }
                    if (serviceClass.getActivationSourceFlag() == null) {
                        ps.setString(5, "");
                    } else {
                        ps.setInt(5, serviceClass.getActivationSourceFlag());
                    }
                    if (serviceClass.getDeacSourceFlag() == null) {
                        ps.setString(6, "");
                    } else {
                        ps.setInt(6, serviceClass.getDeacSourceFlag());
                    }
                    if (serviceClass.getHybirdFlag() == null) {
                        ps.setString(7, "");
                    } else {
                        ps.setInt(7, serviceClass.getHybirdFlag());
                    }
                    ps.setString(8, serviceClass.getBundleType());
                    ps.setInt(9, serviceClass.getServiceClassCode());

                } catch (SQLException ex) {
                    DailyAppLogger.DEBUG_LOGGER.error("error while execute " + updatePriceGroup);
                    DailyAppLogger.ERROR_LOGGER.error("error while execute " + updatePriceGroup, ex);
                    ex.printStackTrace();
                    throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
                }
            }

            @Override
            public int getBatchSize() {
                return serviceClassList.size();
            }
        });
    }

}

