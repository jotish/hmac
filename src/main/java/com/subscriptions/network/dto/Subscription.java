package com.subscriptions.network.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subscription {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("plan_id")
    @Expose
    public String planId;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("start_date")
    @Expose
    public String startDate;
    @SerializedName("end_date")
    @Expose
    public String endDate;
    @SerializedName("reference_id")
    @Expose
    public String referenceId;

}
