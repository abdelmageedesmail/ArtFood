package com.art4musilm.artfoodCustomer.viewModels;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.art4musilm.artfoodCustomer.base.BaseViewModel;
import com.art4musilm.artfoodCustomer.data.CustomRxObserver;
import com.art4musilm.artfoodCustomer.models.Result;
import com.art4musilm.artfoodCustomer.models.requests.CheckoutRequest;
import com.art4musilm.artfoodCustomer.repositories.CheckoutRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CheckoutViewModel extends BaseViewModel {
    private CheckoutRepository checkoutRepository;
    MutableLiveData<Result> resultMutableLiveData = new MutableLiveData<>();

    public LiveData<Result> observeCheckout() {
        return resultMutableLiveData;
    }

    @ViewModelInject
    public CheckoutViewModel(CheckoutRepository checkoutRepository) {
        this.checkoutRepository = checkoutRepository;
    }

    public void checkout(CheckoutRequest checkoutRequest) {
        loading.postValue(true);
        checkoutRepository.checkout(checkoutRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(o -> resultMutableLiveData.postValue(o), e -> Log.e("errorSentCart", "" + e.getMessage()));
    }

}
