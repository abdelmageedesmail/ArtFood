package com.art4musilm.artfoodCustomer.viewModels;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.art4musilm.artfoodCustomer.base.BaseViewModel;
import com.art4musilm.artfoodCustomer.data.CustomRxObserver;
import com.art4musilm.artfoodCustomer.models.DriverLocationModel;
import com.art4musilm.artfoodCustomer.models.Result;
import com.art4musilm.artfoodCustomer.models.response.OrderResponse;
import com.art4musilm.artfoodCustomer.repositories.OrdersRepository;

import java.util.concurrent.PriorityBlockingQueue;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrdersViewModel extends BaseViewModel {
    OrdersRepository ordersRepository;
    MutableLiveData<OrderResponse> orderResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Result> reviewOrderLiveData = new MutableLiveData<>();
    MutableLiveData<Result> resendOrderLiveData = new MutableLiveData<>();

    public LiveData<Result> observeOrderReview() {
        return reviewOrderLiveData;
    }

    public LiveData<OrderResponse> observeMyOrders() {
        return orderResponseMutableLiveData;
    }

    public LiveData<Result> observeResendOrderLiveData() {
        return resendOrderLiveData;
    }

    public interface OrderActions {
        void onOrderActionDone(Result result);
    }

    public interface DriverData {
        void onDriverReturnData(DriverLocationModel model);
    }

    @ViewModelInject
    public OrdersViewModel(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public void getOrders(String id, boolean isCurrent) {
        loading.postValue(true);
        ordersRepository.getOrders(id, isCurrent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        orderResponseMutableLiveData.postValue((OrderResponse) response);
                    }
                });
    }


    public void getDriverLocation(String id, String driverId, DriverData driverData) {
        loading.postValue(true);
        ordersRepository.getDriverLocation(id, driverId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        driverData.onDriverReturnData((DriverLocationModel) response);
                    }
                });
    }

    public void cancelOrder(String id, String lang, OrderActions orderActions) {
        loading.postValue(true);
        ordersRepository.cancelOrder(id, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        orderActions.onOrderActionDone((Result) response);
                    }
                });
    }

    public void setOrderAsDelivered(String id, String lang, OrderActions orderActions) {
        loading.postValue(true);
        ordersRepository.setOrderDelivered(id, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        orderActions.onOrderActionDone((Result) response);
                    }
                });
    }

    public void reviewOrder(String orderId, String lang, float stars, String message) {
        loading.postValue(true);
        ordersRepository.reviewOrder(orderId, lang, stars, message)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        reviewOrderLiveData.postValue((Result) response);
                    }
                });
    }

    public void resendOrder(String orderId) {
        loading.postValue(true);
        ordersRepository.resendOrder(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        resendOrderLiveData.postValue((Result) response);
                    }
                });
    }
}
