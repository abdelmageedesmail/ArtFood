package com.art4musilm.artfoodCustomer.viewModels;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.art4musilm.artfoodCustomer.base.BaseViewModel;
import com.art4musilm.artfoodCustomer.data.CustomRxObserver;
import com.art4musilm.artfoodCustomer.models.Result;
import com.art4musilm.artfoodCustomer.models.response.InfoResponse;
import com.art4musilm.artfoodCustomer.repositories.AppInfRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AppInfoViewModel extends BaseViewModel {
    private AppInfRepository appInfRepository;
    MutableLiveData<InfoResponse> infoResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Result> addCommentLiveData = new MutableLiveData<>();

    public LiveData<InfoResponse> observeAppInfo() {
        return infoResponseMutableLiveData;
    }

    public LiveData<Result> observeAddingComment() {
        return addCommentLiveData;
    }

    @ViewModelInject
    public AppInfoViewModel(AppInfRepository appInfRepository) {
        this.appInfRepository = appInfRepository;
    }

    //Get app info
    public void getAppInfo(Boolean isArabic) {
        loading.postValue(true);
        appInfRepository.getInfo(isArabic)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        infoResponseMutableLiveData.postValue((InfoResponse) response);
                    }
                });
    }


    public void getPolicy(Boolean isArabic) {
        loading.postValue(true);
        appInfRepository.getPolices(isArabic)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        infoResponseMutableLiveData.postValue((InfoResponse) response);
                    }
                });
    }

    public void addComment(String name, String email, String msg, String lang, String type, String phone) {
        loading.postValue(true);
        appInfRepository.contact(name, email, msg, lang, phone, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        addCommentLiveData.postValue((Result) response);
                    }
                });
    }
}
