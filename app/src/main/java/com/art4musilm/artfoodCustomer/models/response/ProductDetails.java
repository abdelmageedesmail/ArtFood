package com.art4musilm.artfoodCustomer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetails {
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
    private String price;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("descriptionen")
    @Expose
    private String descriptionen;
    @SerializedName("calories")
    @Expose
    private String calories;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("date_insert")
    @Expose
    private String dateInsert;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("expectedtime")
    @Expose
    private String expectedtime;
    @SerializedName("deliveryprice")
    @Expose
    private String deliveryprice;
    @SerializedName("prod_additions")
    @Expose
    private List<ProdAddition> prodAdditions = null;

    public String getDeliveryprice() {
        return deliveryprice;
    }

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

    public String getPrice() {
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

    public String getUserId() {
        return userId;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getActive() {
        return active;
    }

    public String getDateInsert() {
        return dateInsert;
    }

    public String getDistance() {
        return distance;
    }

    public String getExpectedtime() {
        return expectedtime;
    }

    public List<ProdAddition> getProdAdditions() {
        return prodAdditions;
    }
}
