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

/**
 * Created by olivia on 8/10/15.
 */
public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ListingHolder> {

    private LayoutInflater mInflater;
    private ActiveListings mActiveListings;

    public ListingAdapter(Context context) {

        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ListingHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ListingHolder(mInflater.inflate(R.layout.layout_listing, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ListingHolder listingHolder, int i) {
        final Listing listing = mActiveListings.results[i];
        

    }

    @Override
    public int getItemCount() {
        if (mActiveListings == null || mActiveListings.results == null)
            return 0;
        return mActiveListings.results.length;
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


