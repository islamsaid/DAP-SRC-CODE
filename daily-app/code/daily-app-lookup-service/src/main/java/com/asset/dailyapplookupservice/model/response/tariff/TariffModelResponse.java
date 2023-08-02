package com.asset.dailyapplookupservice.model.response.tariff;

import java.io.Serializable;

public class TariffModelResponse implements Serializable {

    private Integer tariffModelCode;
    private String tariffModelName;
    private Integer tariffModelType;
    private Integer contractType;
    private Integer hybird;
    private Integer activationSource;
    private String bundleType;
    private Integer deactivationSourceFlag;

    public Integer getDeactivationSourceFlag() {
        return deactivationSourceFlag;
    }

    public void setDeactivationSourceFlag(Integer deactivationSourceFlag) {
        this.deactivationSourceFlag = deactivationSourceFlag;
    }

    public Integer getTariffModelCode() {
        return tariffModelCode;
    }

    public void setTariffModelCode(Integer tariffModelCode) {
        this.tariffModelCode = tariffModelCode;
    }

    public String getTariffModelName() {
        return tariffModelName;
    }

    public void setTariffModelName(String tariffModelName) {
        this.tariffModelName = tariffModelName;
    }

    public Integer getTariffModelType() {
        return tariffModelType;
    }

    public void setTariffModelType(Integer tariffModelType) {
        this.tariffModelType = tariffModelType;
    }

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }

    public Integer getHybird() {
        return hybird;
    }

    public void setHybird(Integer hybird) {
        this.hybird = hybird;
    }

    public Integer getActivationSource() {
        return activationSource;
    }

    public void setActivationSource(Integer activationSource) {
        this.activationSource = activationSource;
    }

    public String getBundleType() {
        return bundleType;
    }

    public void setBundleType(String bundleType) {
        this.bundleType = bundleType;
    }

    @Override
    public String toString() {
        return "TariffModelResponse{" +
                "tariffModelCode=" + tariffModelCode +
                ", tariffModelName='" + tariffModelName + '\'' +
                ", tariffModelType=" + tariffModelType +
                ", contractType=" + contractType +
                ", hybird=" + hybird +
                ", activationSource=" + activationSource +
                ", bundleType='" + bundleType + '\'' +
                ", deactivationSourceFlag=" + deactivationSourceFlag +
                '}';
    }
}
