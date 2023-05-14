package com.art4musilm.artfoodCustomer.session;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class UserSession {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("iduser")
    @Expose
    private String iduser;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("nameuser")
    @Expose
    private String nameuser;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("error_id")
    @Expose
    private Integer errorId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public String getIduser() {
        return iduser;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getNameuser() {
        return nameuser;
    }

    public String getType() {
        return type;
    }

    public Integer getErrorId() {
        return errorId;
    }

    public String getImage() {
        return image;
    }

    public String getMessage() {
        return message;
    }
}
