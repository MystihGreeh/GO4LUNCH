
package com.mystihgreeh.go4lunch.model.Restaurants;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Photo implements Serializable
{

    @SerializedName("height")
    @Expose
    private Long height;
    @SerializedName("html_attributions")
    @Expose
    private List<String> htmlAttributions = null;
    @SerializedName("photo_reference")
    @Expose
    private String photoReference;
    @SerializedName("width")
    @Expose
    private Long width;
    private final static long serialVersionUID = 3261085750954442446L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Photo() {
    }

    /**
     * 
     * @param htmlAttributions
     * @param photoReference
     * @param width
     * @param height
     */
    public Photo(Long height, List<String> htmlAttributions, String photoReference, Long width) {
        super();
        this.height = height;
        this.htmlAttributions = htmlAttributions;
        this.photoReference = photoReference;
        this.width = width;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Photo withHeight(Long height) {
        this.height = height;
        return this;
    }

    public List<String> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<String> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public Photo withHtmlAttributions(List<String> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
        return this;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public Photo withPhotoReference(String photoReference) {
        this.photoReference = photoReference;
        return this;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public Photo withWidth(Long width) {
        this.width = width;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Photo.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("height");
        sb.append('=');
        sb.append(((this.height == null)?"<null>":this.height));
        sb.append(',');
        sb.append("htmlAttributions");
        sb.append('=');
        sb.append(((this.htmlAttributions == null)?"<null>":this.htmlAttributions));
        sb.append(',');
        sb.append("photoReference");
        sb.append('=');
        sb.append(((this.photoReference == null)?"<null>":this.photoReference));
        sb.append(',');
        sb.append("width");
        sb.append('=');
        sb.append(((this.width == null)?"<null>":this.width));
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
        result = ((result* 31)+((this.width == null)? 0 :this.width.hashCode()));
        result = ((result* 31)+((this.htmlAttributions == null)? 0 :this.htmlAttributions.hashCode()));
        result = ((result* 31)+((this.photoReference == null)? 0 :this.photoReference.hashCode()));
        result = ((result* 31)+((this.height == null)? 0 :this.height.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Photo) == false) {
            return false;
        }
        Photo rhs = ((Photo) other);
        return (((((this.width == rhs.width)||((this.width!= null)&&this.width.equals(rhs.width)))&&((this.htmlAttributions == rhs.htmlAttributions)||((this.htmlAttributions!= null)&&this.htmlAttributions.equals(rhs.htmlAttributions))))&&((this.photoReference == rhs.photoReference)||((this.photoReference!= null)&&this.photoReference.equals(rhs.photoReference))))&&((this.height == rhs.height)||((this.height!= null)&&this.height.equals(rhs.height))));
    }

}
