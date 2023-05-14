package com.art4musilm.artfoodCustomer.data;





import com.art4musilm.artfoodCustomer.base.BaseViewModel;
import com.art4musilm.artfoodCustomer.commons.Utils;
import com.art4musilm.artfoodCustomer.events.ErrorMessageEvent;
import com.art4musilm.artfoodCustomer.events.UnAuthorizedEvent;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.observers.DefaultObserver;
import retrofit2.HttpException;

/*
Created By Eng Ali Rabie
15 November 2020
*/

public abstract class CustomRxObserver extends DefaultObserver implements Observer {
    //Network errors
    final public static int UNAUTHORIZED = 401;
    final public static int INTERNAL_SERVER_ERROR = 500;
    //Error Message event
    public ErrorMessageEvent errorMessageEvent;

    //Un Authorized Event
    public UnAuthorizedEvent unAuthorizedEvent;

   BaseViewModel baseViewModel;
    public CustomRxObserver(BaseViewModel baseViewModel) {
        this.baseViewModel = baseViewModel;
        baseViewModel.loading.postValue(true);
    }

    @Override
    public void onNext(@NonNull Object o) {
        onResponse(o);
        baseViewModel.loading.postValue(false);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        if(e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            if(httpException.code() == UNAUTHORIZED) {
                if(unAuthorizedEvent==null){
                    unAuthorizedEvent = new UnAuthorizedEvent();
                    unAuthorizedEvent.setMsg(Utils.parseResponse(((HttpException) e).response()));
                }
                EventBus.getDefault().post(unAuthorizedEvent);
            }else if(httpException.code() == INTERNAL_SERVER_ERROR){
                if(errorMessageEvent==null){
                    errorMessageEvent = new ErrorMessageEvent();
                }
                errorMessageEvent.setMsg(Utils.parseResponse(((HttpException) e).response()));
                EventBus.getDefault().post(errorMessageEvent);
            }
        }else {
            e.printStackTrace();
            baseViewModel.noConnection.postValue(true);
        }
        baseViewModel.loading.postValue(false);
    }

    @Override
    public void onComplete() {

    }
    public abstract void onResponse(Object response);
}
