package com.art4musilm.artfoodCustomer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("orders")
    @Expose
    private List<Order> orders = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public String getMessage() {
        return message;
    }
}
