package com.asset.dailyapplookupservice.model.response.serviceClass;

import java.util.List;

public class ServiceClassesResponse {

    private List<ServiceClassResponse> serviceClasseList;

    public ServiceClassesResponse() {
    }

    
    public ServiceClassesResponse(List<ServiceClassResponse> serviceClasseList) {
        this.serviceClasseList = serviceClasseList;
    }

    public List<ServiceClassResponse> getServiceClasseList() {
        return serviceClasseList;
    }

    public void setServiceClasseList(List<ServiceClassResponse> serviceClasseList) {
        this.serviceClasseList = serviceClasseList;
    }
}
