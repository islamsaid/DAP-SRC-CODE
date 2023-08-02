package com.asset.dailyappreportservice.model.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class EpochDateRequest {
    @NotNull(message = "date is mandatory")
    @Min(0)
    private long date;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
