package com.art4musilm.artfoodCustomer.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"id"})
public class CartModel {
    @ColumnInfo
    int id;
    @ColumnInfo
    String name;
    @ColumnInfo
    String desc;
    @ColumnInfo
    String imgPath;
    @ColumnInfo
    float price;
    @ColumnInfo
    float totalPrice;
    @ColumnInfo
    float additionsTotal;
    @ColumnInfo
    int count;
    @ColumnInfo
    String additions_ids;
    @ColumnInfo
    String deliveryPrice;
    @ColumnInfo
    String familyId;
    @ColumnInfo
    String isDelivery;

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getAdditionsTotal() {
        return additionsTotal;
    }

    public void setAdditionsTotal(float additionsTotal) {
        this.additionsTotal = additionsTotal;
    }

    public void setAdditions_ids(String additions_ids) {
        this.additions_ids = additions_ids;
    }

    public String getAdditions_ids() {
        return additions_ids;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setIsDelivery(String isDelivery) {
        this.isDelivery = isDelivery;
    }

    public String getIsDelivery() {
        return isDelivery;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }
}
