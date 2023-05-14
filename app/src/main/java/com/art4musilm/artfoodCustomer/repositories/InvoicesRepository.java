package com.art4musilm.artfoodCustomer.repositories;
import com.art4musilm.artfoodCustomer.data.ApiService;
import com.art4musilm.artfoodCustomer.models.Result;
import com.art4musilm.artfoodCustomer.models.response.InvoiceResponse;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class InvoicesRepository {
    private ApiService apiService;

    @Inject
    public InvoicesRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Observable<InvoiceResponse> getInvoices(int id, String lang){
        return apiService.getInvoices(id,lang);
    }

    public Observable<Result> addCredit(int id, String amount){
        return apiService.addCredit(id,amount);
    }
}
