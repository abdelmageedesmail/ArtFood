package com.art4musilm.artfoodCustomer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FamilyCategoriesResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("category_productivefamily")
    @Expose
    private List<Category> categoryProductivefamily = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public List<Category> getCategoryProductivefamily() {
        return categoryProductivefamily;
    }

    public String getMessage() {
        return message;
    }
}
