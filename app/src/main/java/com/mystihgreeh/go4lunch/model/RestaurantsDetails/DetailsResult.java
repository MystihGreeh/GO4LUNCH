
package com.mystihgreeh.go4lunch.model.RestaurantsDetails;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mystihgreeh.go4lunch.model.Workmates.Workmate;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class DetailsResult implements Serializable, Parcelable
{

    @SerializedName("formatted_address")
    @Expose
    private String formattedAddress;
    @SerializedName("formatted_phone_number")
    @Expose
    private String formattedPhoneNumber;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("photos")
    @Expose
    private List<Photo> photos = null;
    @SerializedName("place_id")
    @Expose
    private String placeId;
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("website")
    @Expose
    private String website;

    private List<Workmate> workmatesEatingHere;

    public final static Creator<DetailsResult> CREATOR = new Creator<DetailsResult>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DetailsResult createFromParcel(android.os.Parcel in) {
            return new DetailsResult(in);
        }

        public DetailsResult[] newArray(int size) {
            return (new DetailsResult[size]);
        }

    }
    ;
    private final static long serialVersionUID = 2898864357677052163L;

    protected DetailsResult(android.os.Parcel in) {
        this.formattedAddress = ((String) in.readValue((String.class.getClassLoader())));
        this.formattedPhoneNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.photos, (com.mystihgreeh.go4lunch.model.RestaurantsDetails.Photo.class.getClassLoader()));
        this.placeId = ((String) in.readValue((String.class.getClassLoader())));
        this.rating = ((Double) in.readValue((Double.class.getClassLoader())));
        this.website = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public DetailsResult() {
    }

    /**
     * 
     * @param formattedPhoneNumber
     * @param website
     * @param formattedAddress
     * @param name
     * @param placeId
     * @param rating
     * @param photos
     */
    public DetailsResult(String formattedAddress, String formattedPhoneNumber, String name, List<Photo> photos, String placeId, Double rating, String website) {
        super();
        this.formattedAddress = formattedAddress;
        this.formattedPhoneNumber = formattedPhoneNumber;
        this.name = name;
        this.photos = photos;
        this.placeId = placeId;
        this.rating = rating;
        this.website = website;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public DetailsResult withFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
        return this;
    }

    public String getFormattedPhoneNumber() {
        return formattedPhoneNumber;
    }

    public void setFormattedPhoneNumber(String formattedPhoneNumber) {
        this.formattedPhoneNumber = formattedPhoneNumber;
    }

    public DetailsResult withFormattedPhoneNumber(String formattedPhoneNumber) {
        this.formattedPhoneNumber = formattedPhoneNumber;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DetailsResult withName(String name) {
        this.name = name;
        return this;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public DetailsResult withPhotos(List<Photo> photos) {
        this.photos = photos;
        return this;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public DetailsResult withPlaceId(String placeId) {
        this.placeId = placeId;
        return this;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public DetailsResult withRating(Double rating) {
        this.rating = rating;
        return this;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public DetailsResult withWebsite(String website) {
        this.website = website;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DetailsResult.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("formattedAddress");
        sb.append('=');
        sb.append(((this.formattedAddress == null)?"<null>":this.formattedAddress));
        sb.append(',');
        sb.append("formattedPhoneNumber");
        sb.append('=');
        sb.append(((this.formattedPhoneNumber == null)?"<null>":this.formattedPhoneNumber));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("photos");
        sb.append('=');
        sb.append(((this.photos == null)?"<null>":this.photos));
        sb.append(',');
        sb.append("placeId");
        sb.append('=');
        sb.append(((this.placeId == null)?"<null>":this.placeId));
        sb.append(',');
        sb.append("rating");
        sb.append('=');
        sb.append(((this.rating == null)?"<null>":this.rating));
        sb.append(',');
        sb.append("website");
        sb.append('=');
        sb.append(((this.website == null)?"<null>":this.website));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(formattedAddress);
        dest.writeValue(formattedPhoneNumber);
        dest.writeValue(name);
        dest.writeList(photos);
        dest.writeValue(placeId);
        dest.writeValue(rating);
        dest.writeValue(website);
    }

    public int describeContents() {
        return  0;
    }


    public void setWorkmatesEatingHere(List<Workmate> users) { workmatesEatingHere = users; }

    public List<Workmate> getWorkmatesEatingHere() {
        return workmatesEatingHere;
    }
}
