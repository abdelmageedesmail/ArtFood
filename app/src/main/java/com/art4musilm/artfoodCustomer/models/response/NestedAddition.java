package com.art4musilm.artfoodCustomer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NestedAddition {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nameen")
    @Expose
    private String nameen;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("calories")
    @Expose
    private String calories;
    @SerializedName("additions")
    @Expose
    private String additions;
    @SerializedName("image")
    @Expose
    private String image;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNameen() {
        return nameen;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }

    public String getCalories() {
        return calories;
    }

    public String getAdditions() {
        return additions;
    }

    public String getImage() {
        return image;
    }
}
