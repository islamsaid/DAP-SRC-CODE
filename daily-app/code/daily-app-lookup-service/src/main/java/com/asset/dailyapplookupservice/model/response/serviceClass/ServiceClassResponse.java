package com.asset.dailyapplookupservice.model.response.serviceClass;

import java.io.Serializable;

public class ServiceClassResponse implements Serializable {

    private Integer serviceClassCode;
    private String serviceClassName;
    private Integer serviceClassType;
    private Integer contractType;
    private Integer hybird;
    private Integer activationSource;
    private String bundleType;
    private Integer deacSourceFlag;

    public Integer getDeacSourceFlag() {
        return deacSourceFlag;
    }

    public void setDeacSourceFlag(Integer deacSourceFlag) {
        this.deacSourceFlag = deacSourceFlag;
    }

    public Integer getServiceClassCode() {
        return serviceClassCode;
    }

    public void setServiceClassCode(Integer serviceClassCode) {
        this.serviceClassCode = serviceClassCode;
    }

    public String getServiceClassName() {
        return serviceClassName;
    }

    public void setServiceClassName(String serviceClassName) {
        this.serviceClassName = serviceClassName;
    }

    public Integer getServiceClassType() {
        return serviceClassType;
    }

    public void setServiceClassType(Integer serviceClassType) {
        this.serviceClassType = serviceClassType;
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
        return "ServiceClassResponse{" +
                "serviceClassCode=" + serviceClassCode +
                ", serviceClassName='" + serviceClassName + '\'' +
                ", serviceClassType=" + serviceClassType +
                ", contractType=" + contractType +
                ", hybird=" + hybird +
                ", activationSource=" + activationSource +
                ", bundleType='" + bundleType + '\'' +
                ", deacSourceFlag=" + deacSourceFlag +
                '}';
    }
}
