package com.asset.dailyapplookupservice.service;

import com.asset.dailyapplookupservice.database.dao.imp.ServiceClassDAOImp;
import com.asset.dailyapplookupservice.defines.Defines;
import com.asset.dailyapplookupservice.defines.ErrorCodes;
import com.asset.dailyapplookupservice.exception.LookupException;
import com.asset.dailyapplookupservice.logger.DailyAppLogger;
import com.asset.dailyapplookupservice.model.response.serviceClass.ServiceClassesResponse;
import com.asset.dailyapplookupservice.model.response.serviceClass.ServiceClassResponse;
import com.asset.dailyapplookupservice.model.shared.ServiceClassModel;
import com.asset.dailyapplookupservice.utils.ServiceClassUtil;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ServiceClassService {

    @Autowired
    ServiceClassDAOImp serviceClassDaoImp;


    public ServiceClassesResponse getAllServiceClasses() throws LookupException {
        List<ServiceClassModel> serviceClasses = serviceClassDaoImp.getAllServiceClass();
        DailyAppLogger.DEBUG_LOGGER.debug("#Retrieved ServiceClasses = " + serviceClasses.size());
        if (serviceClasses == null || serviceClasses.isEmpty()) {
            DailyAppLogger.DEBUG_LOGGER.error("No service class were found");
            throw new LookupException(ErrorCodes.ERROR.NO_SERVICE_CLASSES_FOUND, Defines.SEVERITY.ERROR ,"serviceClass");
        }
        List<ServiceClassResponse> serviceClassesResponse = ServiceClassUtil.mapServiceClassesResponse(serviceClasses);
        return new ServiceClassesResponse(serviceClassesResponse);
    }


    public Integer updateServiceClass(ServiceClassResponse serviceClassResponse) throws LookupException {
        ServiceClassModel serviceClassModel = ServiceClassUtil.mapServiceClassModel(serviceClassResponse);
        serviceClassModel.setActivationSourceFlag(Optional.ofNullable(serviceClassModel.getActivationSourceFlag()).orElse(0));
        serviceClassModel.setDeacSourceFlag(Optional.ofNullable(serviceClassModel.getDeacSourceFlag()).orElse(0));

        Integer rowsAffected = serviceClassDaoImp.updateServiceClass(serviceClassModel);
        if (rowsAffected == 0 || rowsAffected == null) {
            DailyAppLogger.DEBUG_LOGGER.error("Service class not updated");
            throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR,"serviceClass");
        }
        DailyAppLogger.DEBUG_LOGGER.debug("Successfully updated, #rows affected = ["+rowsAffected+"}");
        return rowsAffected;
    }
    
    
     public void updateServiceClassBatch(List<ServiceClassResponse> serviceClassResponse) throws LookupException {
        List<ServiceClassModel> serviceClassModels = ServiceClassUtil.mapServiceClassList(serviceClassResponse);
         serviceClassModels.stream().filter(sc -> sc.getDeacSourceFlag() == null)
                 .forEach(sc -> sc.setDeacSourceFlag(0));
         serviceClassModels.stream().filter(sc -> sc.getActivationSourceFlag() == null)
                 .forEach(sc -> sc.setActivationSourceFlag(0));

         int[] rowsAffected = serviceClassDaoImp.UpdateServiceClassBatch(serviceClassModels);
        DailyAppLogger.DEBUG_LOGGER.debug("Updated service class batch successfully with row affected ["+Arrays.asList(rowsAffected)+"}");
    }
}
