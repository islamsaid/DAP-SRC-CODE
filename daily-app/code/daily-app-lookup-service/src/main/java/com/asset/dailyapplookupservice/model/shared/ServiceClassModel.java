package com.asset.dailyapplookupservice.model.shared;

public class ServiceClassModel {

    private Integer serviceClassCode;
    private String serviceClassName;

    private Integer consumerFlag;

    private Integer enterpriseFlag;

    private Integer preFlag;

    private Integer postFlag;

    private Integer activationSourceFlag = 0;
    private Integer deacSourceFlag = 0;
    private Integer hybirdFlag;
    private String bundleType;

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

    public Integer getConsumerFlag() {
        return consumerFlag;
    }

    public void setConsumerFlag(Integer consumerFlag) {
        this.consumerFlag = consumerFlag;
    }

    public Integer getEnterpriseFlag() {
        return enterpriseFlag;
    }

    public void setEnterpriseFlag(Integer enterpriseFlag) {
        this.enterpriseFlag = enterpriseFlag;
    }

    public Integer getPreFlag() {
        return preFlag;
    }

    public void setPreFlag(Integer preFlag) {
        this.preFlag = preFlag;
    }

    public Integer getPostFlag() {
        return postFlag;
    }

    public void setPostFlag(Integer postFlag) {
        this.postFlag = postFlag;
    }

    public Integer getActivationSourceFlag() {
        return activationSourceFlag;
    }

    public void setActivationSourceFlag(Integer activationSourceFlag) {
        this.activationSourceFlag = activationSourceFlag;
    }

    public Integer getDeacSourceFlag() {
        return deacSourceFlag;
    }

    public void setDeacSourceFlag(Integer deacSourceFlag) {
        this.deacSourceFlag = deacSourceFlag;
    }

    public Integer getHybirdFlag() {
        return hybirdFlag;
    }

    public void setHybirdFlag(Integer hybirdFlag) {
        this.hybirdFlag = hybirdFlag;
    }

    public String getBundleType() {
        return bundleType;
    }

    public void setBundleType(String bundleType) {
        this.bundleType = bundleType;
    }
}
