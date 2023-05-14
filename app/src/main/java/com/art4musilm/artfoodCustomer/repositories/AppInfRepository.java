package com.art4musilm.artfoodCustomer.repositories;

import com.art4musilm.artfoodCustomer.data.ApiService;
import com.art4musilm.artfoodCustomer.models.Result;
import com.art4musilm.artfoodCustomer.models.response.InfoResponse;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class AppInfRepository {
    private ApiService apiService;

    @Inject
    public AppInfRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    //Get about
    public Observable<InfoResponse> getInfo(boolean isArabic) {
        if (isArabic)
            return apiService.getAboutAr();
        return apiService.getAboutEn();
    }

    //Get polices
    public Observable<InfoResponse> getPolices(boolean isArabic) {
        if (isArabic)
            return apiService.getPoliceAr();
        return apiService.getPoliceEn();
    }

    public Observable<Result> contact(String name, String email, String msg, String lang, String phone, String type) {
        return apiService.addComment(name, msg, lang, email, type, phone);
    }
}
