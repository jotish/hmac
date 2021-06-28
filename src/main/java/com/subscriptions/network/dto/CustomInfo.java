package com.subscriptions.network.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;

@Builder
public class CustomInfo {

    @SerializedName("life_assured_name")
    @Expose
    public String lifeAssuredName;

}
