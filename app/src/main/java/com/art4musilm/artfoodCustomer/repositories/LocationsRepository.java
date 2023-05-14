package com.art4musilm.artfoodCustomer.repositories;

import com.art4musilm.artfoodCustomer.data.ApiService;
import com.art4musilm.artfoodCustomer.models.Result;
import com.art4musilm.artfoodCustomer.models.response.CalculatedDeliveryResponse;
import com.art4musilm.artfoodCustomer.models.response.LocationsResponse;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class LocationsRepository {
    private ApiService apiService;

    @Inject
    public LocationsRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    //Get locations
    public Observable<LocationsResponse> getLocations(String id, String lang) {
        return apiService.getLocations(id, lang);
    }

    //Add location
    public Observable<Result> addLocation(String lang, String id, String address, double lat, double lng) {
        return apiService.addLocation(id, lang, address, lat, lng);
    }

    public Observable<CalculatedDeliveryResponse> getCalculatedValue(String familyId, String addressId) {
        return apiService.clientCalcaulatedDeliver(addressId, familyId);
    }
}
