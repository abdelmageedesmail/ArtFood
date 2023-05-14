package com.art4musilm.artfoodCustomer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Invoice {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("operation_number")
    @Expose
    private String operationNumber;
    @SerializedName("operation_date")
    @Expose
    private String operationDate;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("operation")
    @Expose
    private String operation;
    public String getId() {
        return id;
    }

    public String getOperationNumber() {
        return operationNumber;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public String getPrice() {
        return price;
    }

    public String getUserId() {
        return userId;
    }

    public String getDate() {
        return date;
    }

    public String getOperation() {
        return operation;
    }
}
