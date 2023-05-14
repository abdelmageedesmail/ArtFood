package com.art4musilm.artfoodCustomer.repositories;

import com.art4musilm.artfoodCustomer.data.ApiService;
import com.art4musilm.artfoodCustomer.models.DriverLocationModel;
import com.art4musilm.artfoodCustomer.models.Result;
import com.art4musilm.artfoodCustomer.models.response.OrderResponse;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class OrdersRepository {
    ApiService apiService;

    @Inject
    public OrdersRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<OrderResponse> getOrders(String userId, boolean isCurrent) {
        if (isCurrent)
            return apiService.getCurrentOrders(userId);
        else
            return apiService.getPreviousOrders(userId);
    }

    public Observable<Result> cancelOrder(String id, String lang) {
        return apiService.cancelOrder(id, lang);
    }

    public Observable<DriverLocationModel> getDriverLocation(String id, String driverId) {
        return apiService.getDriverLocation(id, driverId);
    }


    public Observable<Result> setOrderDelivered(String id, String lang) {
        return apiService.setOrderDelivered(id, lang);
    }

    public Observable<Result> reviewOrder(String orderId, String lang, float stars, String message) {
        return apiService.reviewOrder(orderId, lang, stars, message);
    }

    public Observable<Result> resendOrder(String orderId) {
        return apiService.resendOrder(orderId);
    }
}
