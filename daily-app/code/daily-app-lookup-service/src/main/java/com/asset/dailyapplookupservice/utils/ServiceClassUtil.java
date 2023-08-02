package com.asset.dailyapplookupservice.utils;

import com.asset.dailyapplookupservice.constants.ContractType;
import com.asset.dailyapplookupservice.constants.Type;
import com.asset.dailyapplookupservice.model.response.serviceClass.ServiceClassResponse;
import com.asset.dailyapplookupservice.model.shared.ServiceClassModel;

import java.util.ArrayList;
import java.util.List;

public class ServiceClassUtil {

    public static List<ServiceClassResponse> mapServiceClassesResponse(List<ServiceClassModel> serviceClasses) {
        List<ServiceClassResponse> serviceClassResponses = new ArrayList<>();
        for (ServiceClassModel serviceClass : serviceClasses) {
            ServiceClassResponse serviceClassResponse = mapServiceClassResponse(serviceClass);
            serviceClassResponses.add(serviceClassResponse);
        }
        return serviceClassResponses;
    }

    public static ServiceClassResponse mapServiceClassResponse(ServiceClassModel serviceClass) {
        ServiceClassResponse serviceClassResponse = new ServiceClassResponse();
        serviceClassResponse.setServiceClassCode(serviceClass.getServiceClassCode());
        serviceClassResponse.setServiceClassName(serviceClass.getServiceClassName());
        if ((serviceClass.getConsumerFlag() != null && serviceClass.getConsumerFlag() == 1)
                && (serviceClass.getEnterpriseFlag() != null && serviceClass.getEnterpriseFlag() == 1)) {
            serviceClassResponse.setServiceClassType(Type.ALL.getValue());
        } else if (serviceClass.getConsumerFlag() != null && serviceClass.getConsumerFlag() == 1) {
            serviceClassResponse.setServiceClassType(Type.CONSUMER.getValue());
        } else if (serviceClass.getEnterpriseFlag() != null && serviceClass.getEnterpriseFlag() == 1) {
            serviceClassResponse.setServiceClassType(Type.ENTERPRISE.getValue());
        }
        if (serviceClass.getPreFlag() != null && serviceClass.getPreFlag() == 1) {
            serviceClassResponse.setContractType(ContractType.PRE.getValue());
        } else if (serviceClass.getPostFlag() != null && serviceClass.getPostFlag() == 1) {
            serviceClassResponse.setContractType(ContractType.POST.getValue());
        } 
        serviceClassResponse.setHybird(serviceClass.getHybirdFlag());
        serviceClassResponse.setActivationSource(serviceClass.getActivationSourceFlag());
        serviceClassResponse.setBundleType(serviceClass.getBundleType());
        serviceClassResponse.setDeacSourceFlag(serviceClass.getDeacSourceFlag());

        return serviceClassResponse;
    }

    public static List<ServiceClassModel> mapServiceClassList(List<ServiceClassResponse> serviceClassResponse) {
        List<ServiceClassModel> serviceClassModels = new ArrayList<>();
        for (ServiceClassResponse response : serviceClassResponse) {
            ServiceClassModel serviceClassModel = mapServiceClassModel(response);
            serviceClassModels.add(serviceClassModel);
        }
        return serviceClassModels;
    }

    public static ServiceClassModel mapServiceClassModel(ServiceClassResponse serviceClassResponse) {
        ServiceClassModel serviceClassModel = new ServiceClassModel();
        serviceClassModel.setServiceClassCode(serviceClassResponse.getServiceClassCode());
        serviceClassModel.setServiceClassName(serviceClassResponse.getServiceClassName());

        if (serviceClassResponse.getServiceClassType() != null && serviceClassResponse.getServiceClassType().equals(Type.CONSUMER.getValue())) {
            serviceClassModel.setConsumerFlag(1);
            serviceClassModel.setEnterpriseFlag(0);
        } else if (serviceClassResponse.getServiceClassType() != null && serviceClassResponse.getServiceClassType().equals(Type.ENTERPRISE.getValue())) {
            serviceClassModel.setEnterpriseFlag(1);
            serviceClassModel.setConsumerFlag(0);
        } else if (serviceClassResponse.getServiceClassType() != null && serviceClassResponse.getServiceClassType().equals(Type.ALL.getValue())) {
            serviceClassModel.setConsumerFlag(1);
            serviceClassModel.setEnterpriseFlag(1);
        }
        if (serviceClassResponse.getContractType() != null && serviceClassResponse.getContractType().equals(ContractType.PRE.getValue())) {
            serviceClassModel.setPreFlag(1);
            serviceClassModel.setPostFlag(0);            
        } else if (serviceClassResponse.getContractType() != null && serviceClassResponse.getContractType().equals(ContractType.POST.getValue())) {
            serviceClassModel.setPreFlag(0);
            serviceClassModel.setPostFlag(1);            
        }
        serviceClassModel.setHybirdFlag(serviceClassResponse.getHybird());
        serviceClassModel.setActivationSourceFlag(serviceClassResponse.getActivationSource());
        serviceClassModel.setBundleType(serviceClassResponse.getBundleType());
        serviceClassModel.setDeacSourceFlag(serviceClassResponse.getDeacSourceFlag());
        return serviceClassModel;
    }
}
