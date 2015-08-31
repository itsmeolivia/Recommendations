package com.itsmeolivia.recommendations.google;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by olivia on 8/31/15.
 */
public class GoogleServicesHelper implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{


    private Activity mActivity;
    private GoogleServicesListener mListener;
    private GoogleApiClient mApiClient;

    private static final int REQUEST_CODE_RESOLUTION = -100;
    private static final int REQUEST_CODE_AVAILABLITY = -101;

    public GoogleServicesHelper(Activity activity, GoogleServicesListener listener) {
        this.mActivity = activity;
        this.mListener = listener;

        this.mApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }

    public void connect() {
        if (isGooglePlayServicesAvailable())
            mApiClient.connect();
        else {
            mListener.onDisconnected();
        }
    }

    public void disconnect() {
        if (isGooglePlayServicesAvailable())
            mApiClient.disconnect();
        else {
            mListener.onDisconnected();
        }
    }

    private boolean isGooglePlayServicesAvailable() {
        int availability = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mActivity);
        switch(availability) {
            case ConnectionResult.SUCCESS:
                return true;
            case ConnectionResult.SERVICE_DISABLED:
            case ConnectionResult.SERVICE_INVALID:
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                GooglePlayServicesUtil.getErrorDialog(availability, mActivity, REQUEST_CODE_AVAILABLITY).show();
                return false;
            default:
                return false;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mListener.onConnected();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mListener.onDisconnected();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(mActivity, REQUEST_CODE_RESOLUTION);
            } catch (IntentSender.SendIntentException e) {
                connect();
            }
        }
        else {
            mListener.onDisconnected();
        }
    }

    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_RESOLUTION || requestCode == REQUEST_CODE_AVAILABLITY) {
            if (resultCode == Activity.RESULT_OK) {
                connect();
            }
            else {
                mListener.onDisconnected();
            }
        }
    }

    public interface GoogleServicesListener {
        public void onConnected();
        public void onDisconnected();
    }
}
