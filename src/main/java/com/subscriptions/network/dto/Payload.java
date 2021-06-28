package com.subscriptions.network.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payload {

    @SerializedName("subscription")
    @Expose
    public Subscription subscription;

}
