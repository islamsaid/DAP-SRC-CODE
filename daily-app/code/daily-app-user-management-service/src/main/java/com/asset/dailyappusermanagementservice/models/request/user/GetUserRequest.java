package com.asset.dailyappusermanagementservice.models.request.user;

import com.asset.dailyappusermanagementservice.models.request.BaseRequest;
import lombok.Data;

@Data
public class GetUserRequest extends BaseRequest
{
    private Integer UserId;
}
