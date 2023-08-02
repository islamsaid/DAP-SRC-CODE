package com.asset.dailyapplookupservice.model.response.tariff;

import java.util.List;

public class TariffModelsResponse {

    private List<TariffModelResponse> tariffModelList;

    public TariffModelsResponse() {
    }

    public TariffModelsResponse(List<TariffModelResponse> tariffModelList) {
        this.tariffModelList = tariffModelList;
    }

    public List<TariffModelResponse> getTariffModelList() {
        return tariffModelList;
    }

    public void setTariffModelList(List<TariffModelResponse> tariffModelList) {
        this.tariffModelList = tariffModelList;
    }

}
