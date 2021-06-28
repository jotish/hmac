package com.subscriptions.network;


import com.subscriptions.network.dto.SubscriptionRequest;
import com.subscriptions.network.dto.SubscriptionResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SubscriptionService {


    @POST("/api/v1/partner/{partner_name}/subscriptions")
    Call<SubscriptionResponse> postSubscription(@Path("partner_name") String partnerName,
                                                @Body SubscriptionRequest body);

}
