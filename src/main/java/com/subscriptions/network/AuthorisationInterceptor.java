package com.subscriptions.network;


import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class AuthorisationInterceptor implements Interceptor {

    private final String signingKey;
    private final String keyId;

    public AuthorisationInterceptor(String signingKey, String keyId) {
        this.signingKey = signingKey;
        this.keyId = keyId;
    }

    private String bodyToString(final RequestBody request) throws IOException {

        final Buffer buffer = new Buffer();
        if (request != null)
            request.writeTo(buffer);
        else
            return "";
        return buffer.readUtf8();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        final String method = request.method();
        final String requestUri = request.url().uri().toString();
        String requestBody = bodyToString(request.body());


        Request newRequest = request.newBuilder()
                .header("Authorization", getAuthorisation(method.toUpperCase(), requestUri, requestBody))
                .build();

        return chain.proceed(newRequest);
    }

    private String getAuthorisation(String requestMethod, String requestUri, String requestBody) {
        String nonce = getNonce();
        String signature = getSignature(requestMethod, requestUri, requestBody, nonce);
        return "PRACTO-HMAC-SHA256 KeyId=" + this.keyId + ", Nonce=" + nonce + ", Signature=" + signature;
    }

    private String getSignature(String requestMethod, String requestUri, String requestBody, String nonce) {
        String signedData = requestMethod + requestUri + requestBody + nonce;
        SecretKeySpec signingKey = new SecretKeySpec(this.signingKey.getBytes(), "HmacSHA256");
        Mac mac;
        try {
            mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            return "";
        }
        byte[] rawHmac = mac.doFinal(signedData.getBytes());
        return DatatypeConverter.printHexBinary(rawHmac).toLowerCase();
    }


    private String getNonce() {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        return UUID.randomUUID().toString() + "|" + timestamp;
    }
}
