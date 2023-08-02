package com.asset.loggingService.model.response;

import com.asset.loggingService.model.LogModel;
import com.asset.loggingService.model.TrxUserDto;

import java.util.List;

public class GetLogsResponse {

    List<TrxUserDto> logModelList;

    public GetLogsResponse(List<TrxUserDto> logModelList) {
        this.logModelList =logModelList;
    }

    public List<TrxUserDto> getLogModelList() {
        return logModelList;
    }

    public void setLogModelList(List<TrxUserDto> logModelList) {
        this.logModelList = logModelList;
    }
}

