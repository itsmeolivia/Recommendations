package com.itsmeolivia.recommendations.api;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by olivia on 8/5/15.
 */
public class Etsy {

    private static final String API_KEY = "m7bp7m88i1rcqcbhrr0fben6";

    private static RequestInterceptor getInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addEncodedQueryParam("api_key", API_KEY);
            }
        };
    }

    private static Api getApi() {
        return new RestAdapter.Builder()
                .setEndpoint("https://openapi.etsy.com/v2")
                .setRequestInterceptor(getInterceptor())
                .build()
                .create(Api.class);

    }
}
