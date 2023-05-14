package com.art4musilm.artfoodCustomer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {
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
    @SerializedName("price")
    @Expose
    private float price;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("descriptionen")
    @Expose
    private String descriptionen;
    @SerializedName("calories")
    @Expose
    private String calories;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("expectedtime")
    @Expose
    private String expectedtime;

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

    public float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionen() {
        return descriptionen;
    }

    public String getCalories() {
        return calories;
    }

    public String getCategory() {
        return category;
    }

    public String getDistance() {
        return distance;
    }

    public String getExpectedtime() {
        return expectedtime;
    }
}
