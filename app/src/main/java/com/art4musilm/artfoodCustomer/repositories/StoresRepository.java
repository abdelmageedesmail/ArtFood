package com.art4musilm.artfoodCustomer.repositories;

import com.art4musilm.artfoodCustomer.data.ApiService;
import com.art4musilm.artfoodCustomer.models.response.CategoriesResponse;
import com.art4musilm.artfoodCustomer.models.response.FamiliesResponse;
import com.art4musilm.artfoodCustomer.models.response.FamilyCategoriesResponse;
import com.art4musilm.artfoodCustomer.models.response.FamilyDetailsResponse;
import com.art4musilm.artfoodCustomer.models.response.FamilySearchResponse;
import com.art4musilm.artfoodCustomer.models.response.OffersResponse;
import com.art4musilm.artfoodCustomer.models.response.ProductDetailsResponse;
import com.art4musilm.artfoodCustomer.models.response.ProductsResponse;
import com.art4musilm.artfoodCustomer.models.response.SliderResponse;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class StoresRepository {
    private ApiService apiService;

    @Inject
    public StoresRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    //Get Categories
    public Observable<CategoriesResponse> getCategories() {
        return apiService.getCategories();
    }

    //Get slider
    public Observable<SliderResponse> getSlider() {
        return apiService.getSlider();
    }

    //Get Families
    public Observable<FamiliesResponse> getFamilies(double lat, double lng) {
        return apiService.getFamilies(lat, lng);
    }

    //Get Families by id
    public Observable<FamiliesResponse> getFamiliesById(String id, double lat, double lng) {
        return apiService.getFamiliesById(id, lat, lng);
    }

    //Get family details
    public Observable<FamilyDetailsResponse> getFamilyDetails(int id, double lat, double lng) {
        return apiService.getFamilyDetails(id, lat, lng);
    }

    //Get Family categories
    public Observable<FamilyCategoriesResponse> getFamilyCategories(int id) {
        return apiService.getFamilyCategories(id);
    }

    //Get Family categories products
    public Observable<ProductsResponse> getFamilyProducts(int id, int userId, String lang) {
        return apiService.getFamiliesCatsMeals(id, userId, lang);
    }

    //Get product details
    public Observable<ProductDetailsResponse> getMealDetails(int id, String lang) {
        return apiService.getMealDetails(id, lang);
    }

    //Get Offers
    public Observable<OffersResponse> getOffers() {
        return apiService.getOffers();
    }

    public Observable<FamilySearchResponse> getFamilySearchResponse(String lang,String name) {
        return apiService.getFamilySearch(lang,name);
    }
}
