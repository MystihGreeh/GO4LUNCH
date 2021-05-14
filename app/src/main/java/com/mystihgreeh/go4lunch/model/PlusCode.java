
package com.mystihgreeh.go4lunch.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "compound_code",
    "global_code"
})
@Generated("jsonschema2pojo")
public class PlusCode implements Serializable
{

    @JsonProperty("compound_code")
    private String compoundCode;
    @JsonProperty("global_code")
    private String globalCode;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -1286682522500802979L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PlusCode() {
    }

    /**
     * 
     * @param globalCode
     * @param compoundCode
     */
    public PlusCode(String compoundCode, String globalCode) {
        super();
        this.compoundCode = compoundCode;
        this.globalCode = globalCode;
    }

    @JsonProperty("compound_code")
    public String getCompoundCode() {
        return compoundCode;
    }

    @JsonProperty("compound_code")
    public void setCompoundCode(String compoundCode) {
        this.compoundCode = compoundCode;
    }

    @JsonProperty("global_code")
    public String getGlobalCode() {
        return globalCode;
    }

    @JsonProperty("global_code")
    public void setGlobalCode(String globalCode) {
        this.globalCode = globalCode;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(PlusCode.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("compoundCode");
        sb.append('=');
        sb.append(((this.compoundCode == null)?"<null>":this.compoundCode));
        sb.append(',');
        sb.append("globalCode");
        sb.append('=');
        sb.append(((this.globalCode == null)?"<null>":this.globalCode));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
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
        result = ((result* 31)+((this.globalCode == null)? 0 :this.globalCode.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.compoundCode == null)? 0 :this.compoundCode.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PlusCode) == false) {
            return false;
        }
        PlusCode rhs = ((PlusCode) other);
        return ((((this.globalCode == rhs.globalCode)||((this.globalCode!= null)&&this.globalCode.equals(rhs.globalCode)))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.compoundCode == rhs.compoundCode)||((this.compoundCode!= null)&&this.compoundCode.equals(rhs.compoundCode))));
    }

}
