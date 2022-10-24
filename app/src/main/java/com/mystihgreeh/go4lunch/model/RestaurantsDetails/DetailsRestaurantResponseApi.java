
package com.mystihgreeh.go4lunch.model.RestaurantsDetails;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class DetailsRestaurantResponseApi implements Serializable, Parcelable
{

    @SerializedName("html_attributions")
    @Expose
    private List<Object> htmlAttributions = null;
    @SerializedName("result")
    @Expose
    private DetailsResult detailsResult;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Creator<DetailsRestaurantResponseApi> CREATOR = new Creator<DetailsRestaurantResponseApi>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DetailsRestaurantResponseApi createFromParcel(android.os.Parcel in) {
            return new DetailsRestaurantResponseApi(in);
        }

        public DetailsRestaurantResponseApi[] newArray(int size) {
            return (new DetailsRestaurantResponseApi[size]);
        }

    }
    ;
    private final static long serialVersionUID = 2681943364614233208L;

    protected DetailsRestaurantResponseApi(android.os.Parcel in) {
        in.readList(this.htmlAttributions, (java.lang.Object.class.getClassLoader()));
        this.detailsResult = ((DetailsResult) in.readValue((DetailsResult.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public DetailsRestaurantResponseApi() {
    }

    /**
     * 
     * @param detailsResult
     * @param htmlAttributions
     * @param status
     */
    public DetailsRestaurantResponseApi(List<Object> htmlAttributions, DetailsResult detailsResult, String status) {
        super();
        this.htmlAttributions = htmlAttributions;
        this.detailsResult = detailsResult;
        this.status = status;
    }

    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public DetailsRestaurantResponseApi withHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
        return this;
    }

    public DetailsResult getResult() {
        return detailsResult;
    }

    public void setResult(DetailsResult detailsResult) {
        this.detailsResult = detailsResult;
    }

    public DetailsRestaurantResponseApi withResult(DetailsResult detailsResult) {
        this.detailsResult = detailsResult;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DetailsRestaurantResponseApi withStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DetailsRestaurantResponseApi.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("htmlAttributions");
        sb.append('=');
        sb.append(((this.htmlAttributions == null)?"<null>":this.htmlAttributions));
        sb.append(',');
        sb.append("result");
        sb.append('=');
        sb.append(((this.detailsResult == null)?"<null>":this.detailsResult));
        sb.append(',');
        sb.append("status");
        sb.append('=');
        sb.append(((this.status == null)?"<null>":this.status));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeList(htmlAttributions);
        dest.writeValue(detailsResult);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
