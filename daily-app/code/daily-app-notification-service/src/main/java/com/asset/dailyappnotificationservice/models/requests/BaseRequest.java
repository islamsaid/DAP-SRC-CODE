package com.asset.dailyappnotificationservice.models.requests;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseRequest implements Serializable
{
    private String token;
    private String requestId;
    private String sessionId;
}
