package com.art4musilm.artfoodCustomer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("status_name")
    @Expose
    private String statusName;
    @SerializedName("status_nameen")
    @Expose
    private String statusNameen;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("driver_id")
    @Expose
    private String driver_id;
    @SerializedName("totalprise")
    @Expose
    private String totalprise;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("stars")
    @Expose
    private float stars;
    @SerializedName("totlitemsprise")
    @Expose
    private String totlitemsprise;

    @SerializedName("youropinion")
    @Expose
    private String youropinion;
    @SerializedName("tradename")
    @Expose
    private String tradename;
    @SerializedName("shipping")
    @Expose
    private String shipping;

    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("driver_mobile")
    @Expose
    private String driver_mobile;
    @SerializedName("user_address")
    @Expose
    private UserAddress userAddress;


    public String getTotlitemsprise() {
        return totlitemsprise;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getStatusNameen() {
        return statusNameen;
    }

    public String getId() {
        return id;
    }

    public String getTotalprise() {
        return totalprise;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public float getStars() {
        return stars;
    }

    public String getYouropinion() {
        return youropinion;
    }

    public String getTradename() {
        return tradename;
    }

    public String getShipping() {
        return shipping;
    }

    public String getDistance() {
        return distance;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getDriver_mobile() {
        return driver_mobile;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public static class UserAddress {
        @SerializedName("address")
        @Expose
        private String address;

        public String getAddress() {
            return address;
        }
    }
}
