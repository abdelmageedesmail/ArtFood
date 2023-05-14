package com.art4musilm.artfoodCustomer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("cat_id")
    @Expose
    private String cat_id;
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

    public String getCat_id() {
        return cat_id;
    }
}
