package com.art4musilm.artfoodCustomer.viewModels;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.art4musilm.artfoodCustomer.base.BaseViewModel;
import com.art4musilm.artfoodCustomer.data.CustomRxObserver;
import com.art4musilm.artfoodCustomer.models.Result;
import com.art4musilm.artfoodCustomer.models.response.CalculatedDeliveryResponse;
import com.art4musilm.artfoodCustomer.models.response.LocationsResponse;
import com.art4musilm.artfoodCustomer.repositories.LocationsRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LocationsViewModel extends BaseViewModel {
    private LocationsRepository repository;
    MutableLiveData<LocationsResponse> locationsResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Result> resultMutableLiveData = new MutableLiveData<>();
    MutableLiveData<CalculatedDeliveryResponse> calculatedDeliveryResponseMutableLiveData = new MutableLiveData<>();

    public LiveData<Result> observeAddingLocation() {
        return resultMutableLiveData;
    }

    public LiveData<CalculatedDeliveryResponse> observeCalculatedDelivery() {
        return calculatedDeliveryResponseMutableLiveData;
    }

    public LiveData<LocationsResponse> observeLocations() {
        return locationsResponseMutableLiveData;
    }

    @ViewModelInject
    public LocationsViewModel(LocationsRepository repository) {
        this.repository = repository;
    }

    public void getLocations(String id, String lang) {
        loading.postValue(true);
        repository.getLocations(id, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        locationsResponseMutableLiveData.postValue((LocationsResponse) response);
                    }
                });
    }

    public void addLocation(String lang, String id, String address, double lat, double lng) {
        loading.postValue(true);
        repository.addLocation(lang, id, address, lat, lng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        resultMutableLiveData.postValue((Result) response);
                    }
                });
    }

    public void calculateDeliveryPrice(String familyId, String addressId) {
        loading.postValue(true);

        repository.getCalculatedValue(familyId, addressId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        calculatedDeliveryResponseMutableLiveData.postValue((CalculatedDeliveryResponse) response);
                    }
                });
    }
}
