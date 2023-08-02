package com.asset.dailyapplookupservice.model.shared;

public class TariffModel {

    private Integer tariffModelCode;
    private String tariffModel;

    private Integer consumerFlag;

    private Integer enterpriseFlag;

    private Integer preFlag;

    private Integer postFlag;

    private Integer activationSourceFlag;
    private Integer deacSourceFlag;
    private Integer hybirdFlag;
    private String bundleType;

    public Integer getTariffModelCode() {
        return tariffModelCode;
    }

    public void setTariffModelCode(Integer tariffModelCode) {
        this.tariffModelCode = tariffModelCode;
    }

    public String getTariffModel() {
        return tariffModel;
    }

    public void setTariffModel(String tariffModel) {
        this.tariffModel = tariffModel;
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
