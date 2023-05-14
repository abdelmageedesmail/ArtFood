package com.art4musilm.artfoodCustomer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FamilyCategory {
    @SerializedName("cat_id")
    @Expose
    private String catId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nameen")
    @Expose
    private String nameen;

    public String getCatId() {
        return catId;
    }

    public String getName() {
        return name;
    }

    public String getNameen() {
        return nameen;
    }
}
