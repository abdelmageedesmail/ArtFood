package com.art4musilm.artfoodCustomer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Family {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lname")
    @Expose
    private String lname;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("categors")
    @Expose
    private String categors;
    @SerializedName("familystatus")
    @Expose
    private String familystatus;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("expectedtime")
    @Expose
    private String expectedtime;
    @SerializedName("familystatus_name")
    @Expose
    private String familystatusName;
    @SerializedName("familystatus_nameen")
    @Expose
    private String familystatusNameen;
    @SerializedName("deliveryprice")
    @Expose
    private String deliveryprice;
    @SerializedName("isdelivery")
    @Expose
    private String isdelivery;
    @SerializedName("priceequation")
    @Expose
    private String priceequation;


    public String getIsdelivery() {
        return isdelivery;
    }

    public String getPriceequation() {
        return priceequation;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getLname() {
        return lname;
    }

    public String getActive() {
        return active;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getImage() {
        return image;
    }

    public String getCategors() {
        return categors;
    }

    public String getFamilystatus() {
        return familystatus;
    }

    public String getDistance() {
        return distance;
    }

    public String getExpectedtime() {
        return expectedtime;
    }

    public String getFamilystatusName() {
        return familystatusName;
    }

    public String getFamilystatusNameen() {
        return familystatusNameen;
    }

    public String getDeliveryprice() {
        return deliveryprice;
    }
}
