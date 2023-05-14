package com.art4musilm.artfoodCustomer.viewModels;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.art4musilm.artfoodCustomer.base.BaseViewModel;
import com.art4musilm.artfoodCustomer.data.CustomRxObserver;
import com.art4musilm.artfoodCustomer.models.Result;
import com.art4musilm.artfoodCustomer.models.response.InvoiceResponse;
import com.art4musilm.artfoodCustomer.repositories.InvoicesRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class InvoicesViewModel extends BaseViewModel {
    private InvoicesRepository invoicesRepository;
    private MutableLiveData<InvoiceResponse> invoiceResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Result> addCreditLiveData = new MutableLiveData<>();

    public LiveData<Result> rechargingObserver() {
        return addCreditLiveData;
    }

    @ViewModelInject
    public InvoicesViewModel(InvoicesRepository invoicesRepository) {
        this.invoicesRepository = invoicesRepository;
    }

    public LiveData<InvoiceResponse> observeInvoices() {
        return invoiceResponseMutableLiveData;
    }

    public void getInvoices(int id, String lang) {
        loading.postValue(true);
        invoicesRepository.getInvoices(id, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        invoiceResponseMutableLiveData.postValue((InvoiceResponse) response);
                    }
                });
    }


    public void addCredit(int id, String amount) {
        loading.postValue(true);
        invoicesRepository.addCredit(id, amount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        addCreditLiveData.postValue((Result) response);
                    }
                });
    }
}
