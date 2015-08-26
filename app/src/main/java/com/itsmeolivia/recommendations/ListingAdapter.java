package com.itsmeolivia.recommendations;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itsmeolivia.recommendations.model.ActiveListings;
import com.itsmeolivia.recommendations.model.Listing;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Callable;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by olivia on 8/10/15.
 */
public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ListingHolder>
implements Callback<ActiveListings> {

    private MainActivity mActivity;
    private LayoutInflater mInflater;
    private ActiveListings mActiveListings;

    public ListingAdapter(MainActivity activity) {

        this.mActivity = activity;
        mInflater = LayoutInflater.from(activity);
    }

    @Override
    public ListingHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ListingHolder(mInflater.inflate(R.layout.layout_listing, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ListingHolder listingHolder, int i) {
        final Listing listing = mActiveListings.results[i];
        listingHolder.mTitleView.setText(listing.title);
        listingHolder.mPriceView.setText(listing.price);
        listingHolder.mShopNameView.setText(listing.Shop.shop_name);

        Picasso.with(listingHolder.mImageView.getContext())
                .load(listing.Images[0].url_570xN)
                .into(listingHolder.mImageView);

    }

    @Override
    public int getItemCount() {
        if (mActiveListings == null || mActiveListings.results == null)
            return 0;
        return mActiveListings.results.length;
    }

    @Override
    public void success(ActiveListings activeListings, Response response) {
        this.mActiveListings = activeListings;
        notifyDataSetChanged();
        this.mActivity.showList();
    }

    @Override
    public void failure(RetrofitError error) {
        this.mActivity.showError();
    }

    public ActiveListings getActiveListings() {
        return mActiveListings;
    }

    public class ListingHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTitleView;
        public TextView mShopNameView;
        public TextView mPriceView;

        public ListingHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.listing_image);
            mTitleView = (TextView) itemView.findViewById(R.id.listing_title);
            mShopNameView = (TextView) itemView.findViewById(R.id.shop_name);
            mPriceView = (TextView) itemView.findViewById(R.id.price);

        }
    }
}


