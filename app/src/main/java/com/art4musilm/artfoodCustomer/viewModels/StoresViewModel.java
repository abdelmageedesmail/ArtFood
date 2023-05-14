package com.art4musilm.artfoodCustomer.viewModels;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.art4musilm.artfoodCustomer.base.BaseViewModel;
import com.art4musilm.artfoodCustomer.data.CustomRxObserver;
import com.art4musilm.artfoodCustomer.models.response.CategoriesResponse;
import com.art4musilm.artfoodCustomer.models.response.FamiliesResponse;
import com.art4musilm.artfoodCustomer.models.response.FamilyCategoriesResponse;
import com.art4musilm.artfoodCustomer.models.response.FamilyDetailsResponse;
import com.art4musilm.artfoodCustomer.models.response.FamilySearchResponse;
import com.art4musilm.artfoodCustomer.models.response.OffersResponse;
import com.art4musilm.artfoodCustomer.models.response.ProductDetailsResponse;
import com.art4musilm.artfoodCustomer.models.response.ProductsResponse;
import com.art4musilm.artfoodCustomer.models.response.SliderResponse;
import com.art4musilm.artfoodCustomer.repositories.StoresRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class StoresViewModel extends BaseViewModel {
    private StoresRepository storesRepository;
    MutableLiveData<FamiliesResponse> familiesResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<FamiliesResponse> searchFamiliesResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<SliderResponse> sliderResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<FamilyDetailsResponse> familyDetailsResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<CategoriesResponse> categoriesResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<FamilyCategoriesResponse> familyCategoriesResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<ProductsResponse> productsResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<ProductDetailsResponse> productDetailsResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<OffersResponse> offersResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<FamilySearchResponse> familySearchResponseMutableLiveData = new MutableLiveData<>();

    public LiveData<OffersResponse> observeOffers() {
        return offersResponseMutableLiveData;
    }

    public LiveData<ProductDetailsResponse> observeProductDetails() {
        return productDetailsResponseMutableLiveData;
    }

    public LiveData<CategoriesResponse> observeCategories() {
        return categoriesResponseMutableLiveData;
    }

    public LiveData<FamilyDetailsResponse> observeFamilyDetails() {
        return familyDetailsResponseMutableLiveData;
    }

    public LiveData<SliderResponse> observeSlider() {
        return sliderResponseMutableLiveData;
    }

    public LiveData<FamiliesResponse> observeFamilies() {
        return familiesResponseMutableLiveData;
    }

    public LiveData<FamilyCategoriesResponse> observeFamilyCategories() {
        return familyCategoriesResponseMutableLiveData;
    }

    public LiveData<ProductsResponse> observeFamilyProducts() {
        return productsResponseMutableLiveData;
    }

    public LiveData<FamiliesResponse> observeFamilySearchResponse() {
        return searchFamiliesResponseMutableLiveData;
    }

    @ViewModelInject
    public StoresViewModel(StoresRepository storesRepository) {
        this.storesRepository = storesRepository;
    }

    //Get Categories
    public void getCategories() {
        loading.postValue(true);
        storesRepository.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        categoriesResponseMutableLiveData.postValue((CategoriesResponse) response);
                    }
                });
    }

    //Get Slider
    public void getSlider() {
        loading.postValue(true);
        storesRepository.getSlider()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        sliderResponseMutableLiveData.postValue((SliderResponse) response);
                    }
                });
    }

    //Get Families
    public void getFamilies(String id, double lat, double lng) {
        loading.postValue(true);
        switch (Integer.parseInt(id)) {
            case 0: {
                storesRepository.getFamilies(lat, lng)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .subscribe(new CustomRxObserver(this) {
                            @Override
                            public void onResponse(Object response) {
                                loading.postValue(false);
                                familiesResponseMutableLiveData.postValue((FamiliesResponse) response);
                            }
                        });
                break;
            }
            default: {
                storesRepository.getFamiliesById(id, lat, lng)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .subscribe(new CustomRxObserver(this) {
                            @Override
                            public void onResponse(Object response) {
                                loading.postValue(false);
                                familiesResponseMutableLiveData.postValue((FamiliesResponse) response);
                            }
                        });
                break;
            }
        }

    }

    //Get Family Details
    public void getFamiliesDetails(int id, double lat, double lng) {
        loading.postValue(true);
        storesRepository.getFamilyDetails(id, lat, lng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())

                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        familyDetailsResponseMutableLiveData.postValue((FamilyDetailsResponse) response);
                    }
                });
    }

    //Get Family Details
    public void getFamilyCategories(int id) {
        loading.postValue(true);
        storesRepository.getFamilyCategories(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        familyCategoriesResponseMutableLiveData.postValue((FamilyCategoriesResponse) response);
                    }
                });
    }

    //Get Family products
    public void getFamilyProducts(int id, int userId, String lang) {
        loading.postValue(true);
        storesRepository.getFamilyProducts(id, userId, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        productsResponseMutableLiveData.postValue((ProductsResponse) response);
                    }
                });
    }


    //Get Family products
    public void getMealDetails(int id, String lang) {
        loading.postValue(true);
        storesRepository.getMealDetails(id, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        productDetailsResponseMutableLiveData.postValue((ProductDetailsResponse) response);
                    }
                });
    }

    //Get Offers
    public void getOffers() {
        loading.postValue(true);
        storesRepository.getOffers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        offersResponseMutableLiveData.postValue((OffersResponse) response);
                    }
                });
    }

    public void getFamilySearch(String id, double lat, double lng) {
        storesRepository.getFamiliesById(id, lat, lng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        loading.postValue(false);
                        searchFamiliesResponseMutableLiveData.postValue((FamiliesResponse) response);
                    }
                });
    }
}
