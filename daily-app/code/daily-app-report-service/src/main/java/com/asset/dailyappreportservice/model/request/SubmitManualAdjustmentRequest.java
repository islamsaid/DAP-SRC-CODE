package com.asset.dailyappreportservice.model.request;

import com.asset.dailyappreportservice.model.ManualAdjustment.DataKeysAggregationModel;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
public class SubmitManualAdjustmentRequest
{
    @Autowired
    private List<DataKeysAggregationModel> aggregationList;
}
