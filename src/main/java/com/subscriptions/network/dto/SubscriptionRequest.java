package com.subscriptions.network.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;

@Builder
public class SubscriptionRequest {

    @SerializedName("plan_id")
    @Expose
    public String planId;
    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("user_mobile")
    @Expose
    public String userMobile;
    @SerializedName("user_email")
    @Expose
    public String userEmail;
    @SerializedName("reference_id")
    @Expose
    public String referenceId;
    @SerializedName("custom_info")

    @Expose
    public CustomInfo customInfo;

}
