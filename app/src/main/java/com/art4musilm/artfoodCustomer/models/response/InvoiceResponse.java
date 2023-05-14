package com.art4musilm.artfoodCustomer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InvoiceResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<Invoice> data = null;
    @SerializedName("balance")
    @Expose
    private String balance="";
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public List<Invoice> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getBalance() {
        return balance;
    }
}
