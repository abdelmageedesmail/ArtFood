package com.art4musilm.artfoodCustomer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProdAddition {
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
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("add_product")
    @Expose
    private List<NestedAddition> addProduct = null;

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

    public String getProduct() {
        return product;
    }

    public List<NestedAddition> getAddProduct() {
        return addProduct;
    }
}


