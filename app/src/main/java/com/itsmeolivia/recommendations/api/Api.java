package com.itsmeolivia.recommendations.api;

import com.itsmeolivia.recommendations.model.ActiveListings;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by olivia on 8/10/15.
 */
public interface Api {

    @GET("/listings/active")
    void activeListings(@Query("includes") String includes,
                        Callback<ActiveListings> callback);
}
