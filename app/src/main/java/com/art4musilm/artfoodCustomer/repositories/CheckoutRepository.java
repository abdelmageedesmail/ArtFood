package com.art4musilm.artfoodCustomer.repositories;

import com.art4musilm.artfoodCustomer.data.ApiService;
import com.art4musilm.artfoodCustomer.models.Result;
import com.art4musilm.artfoodCustomer.models.requests.CheckoutRequest;



import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class CheckoutRepository {
    private ApiService apiService;

    @Inject
    public CheckoutRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<Result> checkout(CheckoutRequest checkoutRequest){
        return apiService.checkout(
                checkoutRequest.getUserId(),
                checkoutRequest.getTotalItems(),
                checkoutRequest.getTotlitemsprise(),
                checkoutRequest.getShipping(),
                checkoutRequest.getNotes(),
                checkoutRequest.getDeliveryMethod(),
                checkoutRequest.getPaymentMethod(),checkoutRequest.getDeliverytime(),
                checkoutRequest.getDeliverydate(),
                checkoutRequest.getHourfrom(),
                checkoutRequest.getHourto(),
                checkoutRequest.getTransaction_id(),
                checkoutRequest.getUseraddress(),
                checkoutRequest.getItems(),
                checkoutRequest.getLang());
    }
}
