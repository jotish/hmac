package com.subscriptions;

import com.subscriptions.network.AuthorisationInterceptor;
import com.subscriptions.network.SubscriptionService;
import com.subscriptions.network.dto.CustomInfo;
import com.subscriptions.network.dto.SubscriptionRequest;
import com.subscriptions.network.dto.SubscriptionResponse;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {

        String partnerName = "tataaia";
        String keyId = "1";
        String secretKey = "xxxx";
        String planId = "f57f5429-8771-4681-bd76-0251a6ee3285";
        String baseUrl =  "https://subscriptions-uat.practodev.com";
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder().addInterceptor(new AuthorisationInterceptor(secretKey, keyId))
                .build();
        Retrofit practo = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SubscriptionService service = practo.create(SubscriptionService.class);

        SubscriptionRequest request = SubscriptionRequest.builder()
                .planId(planId)
                .userEmail("test@test.com")
                .userName("Test User")
                .userMobile("+919000000000")
                .referenceId("U000001234")
                .customInfo(CustomInfo.builder().lifeAssuredName("Test Life Assured").build()).build();

        Response<SubscriptionResponse> response = service.postSubscription(partnerName, request).execute();
        if (response.isSuccessful()) {
            System.out.println("Successfully created subscription id =  " + response.body().payload.subscription.id);
        } else {
            System.out.println("Failed response code " + response.code() + " error " + response.errorBody().string());
        }
    }

}
