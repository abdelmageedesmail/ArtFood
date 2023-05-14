package com.art4musilm.artfoodCustomer.models.requests;

import java.util.List;

public class CheckoutRequest {
    String userId;
    String totalItems;
    String totlitemsprise;
    String shipping;
    String notes;
    String deliveryMethod;
    String paymentMethod;
    String deliverytime;
    String deliverydate;
    String hourfrom;
    String hourto;
    String transaction_id;
    String useraddress;
    List<String> items;
    String lang;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setDeliverytime(String deliverytime) {
        this.deliverytime = deliverytime;
    }

    public void setDeliverydate(String deliverydate) {
        this.deliverydate = deliverydate;
    }

    public void setHourfrom(String hourfrom) {
        this.hourfrom = hourfrom;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public void setUseraddress(String useraddress) {
        this.useraddress = useraddress;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getUserId() {
        return userId;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public String getShipping() {
        return shipping;
    }

    public String getNotes() {
        return notes;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getDeliverytime() {
        return deliverytime;
    }

    public String getDeliverydate() {
        return deliverydate;
    }

    public String getHourfrom() {
        return hourfrom;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public String getUseraddress() {
        return useraddress;
    }

    public List<String> getItems() {
        return items;
    }

    public String getLang() {
        return lang;
    }

    public String getHourto() {
        return hourto;
    }

    public void setHourto(String hourto) {
        this.hourto = hourto;
    }

    public String getTotlitemsprise() {
        return totlitemsprise;
    }

    public void setTotlitemsprise(String totlitemsprise) {
        this.totlitemsprise = totlitemsprise;
    }

}
