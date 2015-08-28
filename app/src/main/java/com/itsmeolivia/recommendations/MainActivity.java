package com.itsmeolivia.recommendations;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.itsmeolivia.recommendations.api.Etsy;
import com.itsmeolivia.recommendations.model.ActiveListings;
import com.itsmeolivia.recommendations.model.Listing;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends ActionBarActivity {

    private static final String STATE_ACTIVE_LISTINGS = "StateActiveListings";
    public static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private View mProgressBar;
    private TextView mErrorView;
    private ListingAdapter mAdapter;

    private Listing mListing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mProgressBar = findViewById(R.id.progressbar);
        mErrorView = (TextView) findViewById(R.id.error_view);

        //setup recycler view
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new ListingAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        System.out.println("on create");

        if (savedInstanceState == null) {
            System.out.println("if loop");
            showLoading();
            Etsy.getActiveListings(mAdapter);
        }

        else {
            if (savedInstanceState.containsKey(STATE_ACTIVE_LISTINGS)) {
                mAdapter.success((ActiveListings) savedInstanceState.getParcelable(STATE_ACTIVE_LISTINGS), null);
                showList();
            }
            else {
                showLoading();
                Etsy.getActiveListings(mAdapter);
            }
        }


    }

    private void getData() {
        String api_key = "m7bp7m88i1rcqcbhrr0fben6";
        String etsyUrl = "https://openapi.etsy.com/v2/listings/active?includes=Shop,Images&api_key=" + api_key;

        if (isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(etsyUrl)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showLoading();
                        }
                    });
                    showError();

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showLoading();
                        }
                    });

                    try{
                        String jsonData = response.body().string();

                        if (response.isSuccessful()) {

                            mListing = getListingDetails(jsonData);

                        }

                        else {

                        }
                    }
                    catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                    catch (JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;

        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }

    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
    }

    public void showList() {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
    }

    public void showError() {

        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
    }

    private Listing getListingDetails(String jsonData) throws JSONException{
        Listing listing = new Listing();
        listing.setDescription(jsonData);
        listing.setImages(jsonData);
        listing.setListing_id(jsonData);
        listing.setPrice(jsonData);
        listing.setUrl(jsonData);
        listing.setTitle(jsonData);
        listing.setShop(jsonData);

        return listing;
    }

    private String getDescription(String jsonData) throws JSONException{
        JSONObject list_detail = new JSONObject(jsonData);

    }
}
