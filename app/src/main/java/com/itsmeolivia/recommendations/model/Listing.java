package  com.itsmeolivia.recommendations.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Listing implements Parcelable {

    public int listing_id;
    public String title;
    public String description;
    public String price;
    public String url;
    public Shop Shop;
    public Image[] Images;

    public int getListing_id() {
        return listing_id;
    }

    public void setListing_id(int listing_id) {
        this.listing_id = listing_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Shop getShop() {
        return Shop;
    }

    public void setShop(com.itsmeolivia.recommendations.model.Shop shop) {
        Shop = shop;
    }

    public Image[] getImages() {
        return Images;
    }

    public void setImages(Image[] images) {
        Images = images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(listing_id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(price);
        dest.writeString(url);
        dest.writeParcelable(Shop, 0);
        dest.writeParcelableArray(Images, 0);
    }

    public static final Creator<Listing> CREATOR = new Creator<Listing>() {
        @Override
        public Listing createFromParcel(Parcel source) {
            Listing listing = new Listing();
            listing.listing_id = source.readInt();
            listing.title = source.readString();
            listing.description = source.readString();
            listing.price = source.readString();
            listing.url = source.readString();
            listing.Shop = source.readParcelable(Shop.class.getClassLoader());
            listing.Images = (Image[]) source.readParcelableArray(Image.class.getClassLoader());
            return listing;
        }

        @Override
        public Listing[] newArray(int size) {
            return new Listing[size];
        }
    };
}
