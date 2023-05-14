package com.art4musilm.artfoodCustomer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offer {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nameen")
    @Expose
    private String nameen;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("timeperiod")
    @Expose
    private String timeperiod;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("date")
    @Expose
    private String date;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNameen() {
        return nameen;
    }

    public String getImage() {
        return image;
    }

    public String getTimeperiod() {
        return timeperiod;
    }

    public String getActive() {
        return active;
    }

    public String getUserId() {
        return userId;
    }

    public String getDate() {
        return date;
    }
}
