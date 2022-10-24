
package com.mystihgreeh.go4lunch.model.Restaurants;

import android.location.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mystihgreeh.go4lunch.model.Workmates.Workmate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Result implements Serializable
{

    @SerializedName("business_status")
    @Expose
    private String businessStatus;
    @SerializedName("geometry")
    @Expose
    private Geometry geometry;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("icon_background_color")
    @Expose
    private String iconBackgroundColor;
    @SerializedName("icon_mask_base_uri")
    @Expose
    private String iconMaskBaseUri;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("opening_hours")
    @Expose
    private OpeningHours openingHours;
    @SerializedName("photos")
    @Expose
    private List<Photo> photos = null;
    @SerializedName("place_id")
    @Expose
    private String placeId;
    @SerializedName("plus_code")
    @Expose
    private PlusCode plusCode;
    @SerializedName("price_level")
    @Expose
    private Long priceLevel;
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("reference")
    @Expose
    private String reference;
    @SerializedName("scope")
    @Expose
    private String scope;
    @SerializedName("types")
    @Expose
    private List<String> types = null;
    @SerializedName("user_ratings_total")
    @Expose
    private Long userRatingsTotal;
    @SerializedName("vicinity")
    @Expose
    private String vicinity;
    @SerializedName("permanently_closed")
    @Expose
    private Boolean permanentlyClosed;
    private final static long serialVersionUID = -9005568200588316010L;

    private int distance;
    private List<Workmate> workmatesEatingHere;

 

    /**
     * No args constructor for use in serialization
     * 
     */
    public Result() {
    }

    /**
     * 
     * @param types
     * @param plusCode
     * @param icon
     * @param placeId
     * @param rating
     * @param userRatingsTotal
     * @param businessStatus
     * @param priceLevel
     * @param photos
     * @param iconMaskBaseUri
     * @param reference
     * @param permanentlyClosed
     * @param iconBackgroundColor
     * @param scope
     * @param name
     * @param geometry
     * @param openingHours
     * @param vicinity
     */
    public Result(String businessStatus, Geometry geometry, String icon, String iconBackgroundColor, String iconMaskBaseUri, String name, OpeningHours openingHours, List<Photo> photos, String placeId, PlusCode plusCode, Long priceLevel, Double rating, String reference, String scope, List<String> types, Long userRatingsTotal, String vicinity, Boolean permanentlyClosed) {
        super();
        this.businessStatus = businessStatus;
        this.geometry = geometry;
        this.icon = icon;
        this.iconBackgroundColor = iconBackgroundColor;
        this.iconMaskBaseUri = iconMaskBaseUri;
        this.name = name;
        this.openingHours = openingHours;
        this.photos = photos;
        this.placeId = placeId;
        this.plusCode = plusCode;
        this.priceLevel = priceLevel;
        this.rating = rating;
        this.reference = reference;
        this.scope = scope;
        this.types = types;
        this.userRatingsTotal = userRatingsTotal;
        this.vicinity = vicinity;
        this.permanentlyClosed = permanentlyClosed;
        this.workmatesEatingHere = new ArrayList<>();
    }

    public String getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
    }

    public Result withBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
        return this;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Result withGeometry(Geometry geometry) {
        this.geometry = geometry;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Result withIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getIconBackgroundColor() {
        return iconBackgroundColor;
    }

    public void setIconBackgroundColor(String iconBackgroundColor) {
        this.iconBackgroundColor = iconBackgroundColor;
    }

    public Result withIconBackgroundColor(String iconBackgroundColor) {
        this.iconBackgroundColor = iconBackgroundColor;
        return this;
    }

    public String getIconMaskBaseUri() {
        return iconMaskBaseUri;
    }

    public void setIconMaskBaseUri(String iconMaskBaseUri) {
        this.iconMaskBaseUri = iconMaskBaseUri;
    }

    public Result withIconMaskBaseUri(String iconMaskBaseUri) {
        this.iconMaskBaseUri = iconMaskBaseUri;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Result withName(String name) {
        this.name = name;
        return this;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    public Result withOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
        return this;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Result withPhotos(List<Photo> photos) {
        this.photos = photos;
        return this;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public Result withPlaceId(String placeId) {
        this.placeId = placeId;
        return this;
    }

    public PlusCode getPlusCode() {
        return plusCode;
    }

    public void setPlusCode(PlusCode plusCode) {
        this.plusCode = plusCode;
    }

    public Result withPlusCode(PlusCode plusCode) {
        this.plusCode = plusCode;
        return this;
    }

    public Long getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(Long priceLevel) {
        this.priceLevel = priceLevel;
    }

    public Result withPriceLevel(Long priceLevel) {
        this.priceLevel = priceLevel;
        return this;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Result withRating(Double rating) {
        this.rating = rating;
        return this;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Result withReference(String reference) {
        this.reference = reference;
        return this;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Result withScope(String scope) {
        this.scope = scope;
        return this;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public Result withTypes(List<String> types) {
        this.types = types;
        return this;
    }

    public Long getUserRatingsTotal() {
        return userRatingsTotal;
    }

    public void setUserRatingsTotal(Long userRatingsTotal) {
        this.userRatingsTotal = userRatingsTotal;
    }

    public Result withUserRatingsTotal(Long userRatingsTotal) {
        this.userRatingsTotal = userRatingsTotal;
        return this;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public Result withVicinity(String vicinity) {
        this.vicinity = vicinity;
        return this;
    }

    public Boolean getPermanentlyClosed() {
        return permanentlyClosed;
    }

    public void setPermanentlyClosed(Boolean permanentlyClosed) {
        this.permanentlyClosed = permanentlyClosed;
    }

    public Result withPermanentlyClosed(Boolean permanentlyClosed) {
        this.permanentlyClosed = permanentlyClosed;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Result.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("businessStatus");
        sb.append('=');
        sb.append(((this.businessStatus == null)?"<null>":this.businessStatus));
        sb.append(',');
        sb.append("geometry");
        sb.append('=');
        sb.append(((this.geometry == null)?"<null>":this.geometry));
        sb.append(',');
        sb.append("icon");
        sb.append('=');
        sb.append(((this.icon == null)?"<null>":this.icon));
        sb.append(',');
        sb.append("iconBackgroundColor");
        sb.append('=');
        sb.append(((this.iconBackgroundColor == null)?"<null>":this.iconBackgroundColor));
        sb.append(',');
        sb.append("iconMaskBaseUri");
        sb.append('=');
        sb.append(((this.iconMaskBaseUri == null)?"<null>":this.iconMaskBaseUri));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("openingHours");
        sb.append('=');
        sb.append(((this.openingHours == null)?"<null>":this.openingHours));
        sb.append(',');
        sb.append("photos");
        sb.append('=');
        sb.append(((this.photos == null)?"<null>":this.photos));
        sb.append(',');
        sb.append("placeId");
        sb.append('=');
        sb.append(((this.placeId == null)?"<null>":this.placeId));
        sb.append(',');
        sb.append("plusCode");
        sb.append('=');
        sb.append(((this.plusCode == null)?"<null>":this.plusCode));
        sb.append(',');
        sb.append("priceLevel");
        sb.append('=');
        sb.append(((this.priceLevel == null)?"<null>":this.priceLevel));
        sb.append(',');
        sb.append("rating");
        sb.append('=');
        sb.append(((this.rating == null)?"<null>":this.rating));
        sb.append(',');
        sb.append("reference");
        sb.append('=');
        sb.append(((this.reference == null)?"<null>":this.reference));
        sb.append(',');
        sb.append("scope");
        sb.append('=');
        sb.append(((this.scope == null)?"<null>":this.scope));
        sb.append(',');
        sb.append("types");
        sb.append('=');
        sb.append(((this.types == null)?"<null>":this.types));
        sb.append(',');
        sb.append("userRatingsTotal");
        sb.append('=');
        sb.append(((this.userRatingsTotal == null)?"<null>":this.userRatingsTotal));
        sb.append(',');
        sb.append("vicinity");
        sb.append('=');
        sb.append(((this.vicinity == null)?"<null>":this.vicinity));
        sb.append(',');
        sb.append("permanentlyClosed");
        sb.append('=');
        sb.append(((this.permanentlyClosed == null)?"<null>":this.permanentlyClosed));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.types == null)? 0 :this.types.hashCode()));
        result = ((result* 31)+((this.plusCode == null)? 0 :this.plusCode.hashCode()));
        result = ((result* 31)+((this.icon == null)? 0 :this.icon.hashCode()));
        result = ((result* 31)+((this.placeId == null)? 0 :this.placeId.hashCode()));
        result = ((result* 31)+((this.rating == null)? 0 :this.rating.hashCode()));
        result = ((result* 31)+((this.userRatingsTotal == null)? 0 :this.userRatingsTotal.hashCode()));
        result = ((result* 31)+((this.businessStatus == null)? 0 :this.businessStatus.hashCode()));
        result = ((result* 31)+((this.priceLevel == null)? 0 :this.priceLevel.hashCode()));
        result = ((result* 31)+((this.photos == null)? 0 :this.photos.hashCode()));
        result = ((result* 31)+((this.iconMaskBaseUri == null)? 0 :this.iconMaskBaseUri.hashCode()));
        result = ((result* 31)+((this.reference == null)? 0 :this.reference.hashCode()));
        result = ((result* 31)+((this.permanentlyClosed == null)? 0 :this.permanentlyClosed.hashCode()));
        result = ((result* 31)+((this.iconBackgroundColor == null)? 0 :this.iconBackgroundColor.hashCode()));
        result = ((result* 31)+((this.scope == null)? 0 :this.scope.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.geometry == null)? 0 :this.geometry.hashCode()));
        result = ((result* 31)+((this.openingHours == null)? 0 :this.openingHours.hashCode()));
        result = ((result* 31)+((this.vicinity == null)? 0 :this.vicinity.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Result) == false) {
            return false;
        }
        Result rhs = ((Result) other);
        return (((((((((((((((((((this.types == rhs.types)||((this.types!= null)&&this.types.equals(rhs.types)))&&((this.plusCode == rhs.plusCode)||((this.plusCode!= null)&&this.plusCode.equals(rhs.plusCode))))&&((this.icon == rhs.icon)||((this.icon!= null)&&this.icon.equals(rhs.icon))))&&((this.placeId == rhs.placeId)||((this.placeId!= null)&&this.placeId.equals(rhs.placeId))))&&((this.rating == rhs.rating)||((this.rating!= null)&&this.rating.equals(rhs.rating))))&&((this.userRatingsTotal == rhs.userRatingsTotal)||((this.userRatingsTotal!= null)&&this.userRatingsTotal.equals(rhs.userRatingsTotal))))&&((this.businessStatus == rhs.businessStatus)||((this.businessStatus!= null)&&this.businessStatus.equals(rhs.businessStatus))))&&((this.priceLevel == rhs.priceLevel)||((this.priceLevel!= null)&&this.priceLevel.equals(rhs.priceLevel))))&&((this.photos == rhs.photos)||((this.photos!= null)&&this.photos.equals(rhs.photos))))&&((this.iconMaskBaseUri == rhs.iconMaskBaseUri)||((this.iconMaskBaseUri!= null)&&this.iconMaskBaseUri.equals(rhs.iconMaskBaseUri))))&&((this.reference == rhs.reference)||((this.reference!= null)&&this.reference.equals(rhs.reference))))&&((this.permanentlyClosed == rhs.permanentlyClosed)||((this.permanentlyClosed!= null)&&this.permanentlyClosed.equals(rhs.permanentlyClosed))))&&((this.iconBackgroundColor == rhs.iconBackgroundColor)||((this.iconBackgroundColor!= null)&&this.iconBackgroundColor.equals(rhs.iconBackgroundColor))))&&((this.scope == rhs.scope)||((this.scope!= null)&&this.scope.equals(rhs.scope))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.geometry == rhs.geometry)||((this.geometry!= null)&&this.geometry.equals(rhs.geometry))))&&((this.openingHours == rhs.openingHours)||((this.openingHours!= null)&&this.openingHours.equals(rhs.openingHours))))&&((this.vicinity == rhs.vicinity)||((this.vicinity!= null)&&this.vicinity.equals(rhs.vicinity))));
    }

    public int getDistance() {
        return distance;
    }

    /**
     * Calculate the distance
     * @return
     */
    public void calculateDistance(double userLatitude, double userLongitude) {

        float[] distance = new float[1];
        Location.distanceBetween(userLatitude, userLongitude, this.getGeometry().getLocation().getLat(), this.getGeometry().getLocation().getLng(), distance);
        this.distance =  (int) distance[0];
    }

    public void setWorkmatesEatingHere(List<Workmate> users) { workmatesEatingHere = users; }

    public List<Workmate> getWorkmatesEatingHere() {
        return workmatesEatingHere;
    }

}
