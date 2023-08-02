package com.asset.dailyappnotificationservice.models.responses;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

@Data
public class BaseResponse<T> {
    private Integer statusCode;
    private String statusMessage;
    private Integer severity;
    private String traceId;
    private T payload;

    public BaseResponse() {
    }

    public BaseResponse(Integer statusCode, String statusMessage, Integer severity, String traceId, T payload) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.severity = severity;
        this.traceId = traceId;
        this.payload = payload;
    }
}
