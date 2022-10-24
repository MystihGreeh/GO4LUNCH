
package com.mystihgreeh.go4lunch.model.Restaurants;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class OpeningHours implements Serializable
{

    @SerializedName("open_now")
    @Expose
    private Boolean openNow;
    private final static long serialVersionUID = -4306701933477022177L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public OpeningHours() {
    }

    /**
     * 
     * @param openNow
     */
    public OpeningHours(Boolean openNow) {
        super();
        this.openNow = openNow;
    }

    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

    public OpeningHours withOpenNow(Boolean openNow) {
        this.openNow = openNow;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(OpeningHours.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("openNow");
        sb.append('=');
        sb.append(((this.openNow == null)?"<null>":this.openNow));
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
        result = ((result* 31)+((this.openNow == null)? 0 :this.openNow.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OpeningHours) == false) {
            return false;
        }
        OpeningHours rhs = ((OpeningHours) other);
        return ((this.openNow == rhs.openNow)||((this.openNow!= null)&&this.openNow.equals(rhs.openNow)));
    }

}
