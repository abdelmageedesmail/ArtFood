package com.art4musilm.artfoodCustomer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateProfileResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("user_details")
    @Expose
    private Profile userDetails;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public Profile getUserDetails() {
        return userDetails;
    }

    public String getMessage() {
        return message;
    }
}
