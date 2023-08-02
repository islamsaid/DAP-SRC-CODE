package com.asset.dailyapplookupservice.utils;

import com.asset.dailyapplookupservice.constants.ContractType;
import com.asset.dailyapplookupservice.constants.Type;
import com.asset.dailyapplookupservice.model.response.tariff.TariffModelResponse;
import com.asset.dailyapplookupservice.model.shared.TariffModel;

import java.util.ArrayList;
import java.util.List;

public class TariffModelUtil {

    public static List<TariffModelResponse> mapTariffModelsResponse(List<TariffModel> tariffModels) {
        List<TariffModelResponse> tariffModelResponses = new ArrayList<>();
        for (TariffModel tariffModel : tariffModels) {
            TariffModelResponse tariffModelResponse = mapTariffModelResponse(tariffModel);
            tariffModelResponses.add(tariffModelResponse);
        }
        return tariffModelResponses;
    }

    public static TariffModelResponse mapTariffModelResponse(TariffModel tariffModel) {
        TariffModelResponse tariffModelResponse = new TariffModelResponse();
        tariffModelResponse.setTariffModelCode(tariffModel.getTariffModelCode());
        tariffModelResponse.setTariffModelName(tariffModel.getTariffModel());
        if ((tariffModel.getConsumerFlag() != null && tariffModel.getConsumerFlag() == 1)
                && (tariffModel.getEnterpriseFlag() != null && tariffModel.getEnterpriseFlag() == 1)) {
            tariffModelResponse.setTariffModelType(Type.ALL.getValue());
        } else if (tariffModel.getConsumerFlag() != null && tariffModel.getConsumerFlag() == 1) {
            tariffModelResponse.setTariffModelType(Type.CONSUMER.getValue());
        } else if (tariffModel.getEnterpriseFlag() != null && tariffModel.getEnterpriseFlag() == 1) {
            tariffModelResponse.setTariffModelType(Type.ENTERPRISE.getValue());
        }
        if (tariffModel.getPreFlag() != null && tariffModel.getPreFlag() == 1) {
            tariffModelResponse.setContractType(ContractType.PRE.getValue());
        } else if (tariffModel.getPostFlag() != null && tariffModel.getPostFlag() == 1) {
            tariffModelResponse.setContractType(ContractType.POST.getValue());
        }
        tariffModelResponse.setHybird(tariffModel.getHybirdFlag());
        tariffModelResponse.setActivationSource(tariffModel.getActivationSourceFlag());
        tariffModelResponse.setBundleType(tariffModel.getBundleType());
        tariffModelResponse.setDeactivationSourceFlag(tariffModel.getDeacSourceFlag());

        return tariffModelResponse;
    }

    public static List<TariffModel> mapTariffModelList(List<TariffModelResponse> tariffModelResponses) {
        List<TariffModel> tariffModels  = new ArrayList<>();
        for (TariffModelResponse response : tariffModelResponses) {
            TariffModel tariffModel = mapTariffModel(response);
            tariffModels.add(tariffModel);
        }
        return tariffModels;
    }

    public static TariffModel mapTariffModel(TariffModelResponse tariffModelResponse) {
        TariffModel tariffModel = new TariffModel();
        tariffModel.setTariffModelCode(tariffModelResponse.getTariffModelCode());
        tariffModel.setTariffModel(tariffModelResponse.getTariffModelName());

        if (tariffModelResponse.getTariffModelType() != null && tariffModelResponse.getTariffModelType().equals(Type.CONSUMER.getValue())) {
            tariffModel.setConsumerFlag(1);
            tariffModel.setEnterpriseFlag(0);
        } else if (tariffModelResponse.getTariffModelType() != null && tariffModelResponse.getTariffModelType().equals(Type.ENTERPRISE.getValue())) {
            tariffModel.setEnterpriseFlag(1);
            tariffModel.setConsumerFlag(0);
        } else if (tariffModelResponse.getTariffModelType() != null && tariffModelResponse.getTariffModelType().equals(Type.ALL.getValue())) {
            tariffModel.setConsumerFlag(1);
            tariffModel.setEnterpriseFlag(1);
        }
        if (tariffModelResponse.getContractType() != null && tariffModelResponse.getContractType().equals(ContractType.PRE.getValue())) {
            tariffModel.setPreFlag(1);
            tariffModel.setPostFlag(0);
        } else if (tariffModelResponse.getContractType() != null && tariffModelResponse.getContractType().equals(ContractType.POST.getValue())) {
            tariffModel.setPreFlag(0);
            tariffModel.setPostFlag(1);
        }
        tariffModel.setHybirdFlag(tariffModelResponse.getHybird());
        tariffModel.setActivationSourceFlag(tariffModelResponse.getActivationSource());
        tariffModel.setBundleType(tariffModelResponse.getBundleType());
        tariffModel.setDeacSourceFlag(tariffModelResponse.getDeactivationSourceFlag());
        return tariffModel;
    }
}
