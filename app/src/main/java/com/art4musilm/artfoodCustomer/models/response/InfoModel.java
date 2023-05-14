package com.art4musilm.artfoodCustomer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InfoModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("sortorder")
    @Expose
    private String sortorder;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("slug")
    @Expose
    private String slug;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public String getSortorder() {
        return sortorder;
    }

    public String getLang() {
        return lang;
    }

    public String getSlug() {
        return slug;
    }
}
