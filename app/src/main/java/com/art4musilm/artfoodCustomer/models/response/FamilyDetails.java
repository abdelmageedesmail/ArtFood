package com.art4musilm.artfoodCustomer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FamilyDetails {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("familystatus")
    @Expose
    private String familystatus;
    @SerializedName("stars")
    @Expose
    private float stars;
    @SerializedName("familystatus_name")
    @Expose
    private String familystatusName;
    @SerializedName("familystatus_nameen")
    @Expose
    private String familystatusNameen = null;
    @SerializedName("commercialrecord")
    @Expose
    private String commercialrecord;
    @SerializedName("deliveryprice")
    @Expose
    private String deliveryprice;
    @SerializedName("expectedtime")
    @Expose
    private String expectedtime;
    @SerializedName("distance")
    @Expose
    private String distance;

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

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getMobile() {
        return mobile;
    }

    public String getActive() {
        return active;
    }

    public String getFamilystatus() {
        return familystatus;
    }

    public float getStars() {
        return stars;
    }

    public String getFamilystatusName() {
        return familystatusName;
    }

    public String getFamilystatusNameen() {
        return familystatusNameen;
    }

    public String getCommercialrecord() {
        return commercialrecord;
    }

    public String getDeliveryprice() {
        return deliveryprice;
    }

    public String getExpectedtime() {
        return expectedtime;
    }

    public String getDistance() {
        return distance;
    }
}
