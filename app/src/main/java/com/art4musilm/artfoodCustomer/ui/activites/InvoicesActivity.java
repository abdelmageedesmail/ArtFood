package com.art4musilm.artfoodCustomer.ui.activites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.base.BaseActivity;
import com.art4musilm.artfoodCustomer.databinding.ActivityInvoicesBinding;
import com.art4musilm.artfoodCustomer.payment.PaymentRefrences;
import com.art4musilm.artfoodCustomer.models.Result;
import com.art4musilm.artfoodCustomer.models.response.Invoice;
import com.art4musilm.artfoodCustomer.models.response.InvoiceResponse;
import com.art4musilm.artfoodCustomer.payment.SettingsManager;
import com.art4musilm.artfoodCustomer.ui.adapters.CardsAdapter;
import com.art4musilm.artfoodCustomer.ui.adapters.InvoicesAdapter;
import com.art4musilm.artfoodCustomer.ui.dialogs.RechargeDialog;
import com.art4musilm.artfoodCustomer.viewModels.InvoicesViewModel;

import java.math.BigDecimal;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import company.tap.gosellapi.GoSellSDK;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.models.Authorize;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.PhoneNumber;
import company.tap.gosellapi.internal.api.models.SavedCard;
import company.tap.gosellapi.internal.api.models.Token;
import company.tap.gosellapi.open.buttons.PayButtonView;
import company.tap.gosellapi.open.controllers.SDKSession;
import company.tap.gosellapi.open.controllers.ThemeObject;
import company.tap.gosellapi.open.delegate.SessionDelegate;
import company.tap.gosellapi.open.enums.AppearanceMode;
import company.tap.gosellapi.open.enums.CardType;
import company.tap.gosellapi.open.enums.TransactionMode;
import company.tap.gosellapi.open.models.CardsList;
import company.tap.gosellapi.open.models.Customer;
import company.tap.gosellapi.open.models.PaymentItem;
import company.tap.gosellapi.open.models.TapCurrency;
import company.tap.gosellapi.open.models.Tax;
import company.tap.gosellapi.GoSellSDK;


public class InvoicesActivity extends BaseActivity {
    ActivityInvoicesBinding binding;
    InvoicesAdapter invoicesAdapter;
    List<Invoice> invoiceList = new ArrayList<>();
    InvoicesViewModel invoicesViewModel;
    SDKSession sdkSession;
    private PayButtonView payButtonView;
    private SettingsManager settingsManager;
    private final int SDK_REQUEST_CODE = 1001;
    private ProgressDialog progress;
    RecyclerView rvCards;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpUi();
        setUpObservers();
        settingsManager = SettingsManager.getInstance();
        settingsManager.setPref(this);

        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getString("from").equals("payment")) {
                invoicesViewModel.addCredit(Integer.parseInt(sessionHelper.userId()), getIntent().getExtras().getString("priceValue"));
            }
        }
    }

    private void setUpObservers() {
        invoicesViewModel = new ViewModelProvider(this).get(InvoicesViewModel.class);
        binding.setViewModel(invoicesViewModel);
        binding.setLifecycleOwner(this);
        invoicesViewModel.observeInvoices().observe(this, new Observer<InvoiceResponse>() {
            @Override
            public void onChanged(InvoiceResponse invoiceResponse) {
                if (invoiceResponse.getStatus()) {
                    invoiceList.clear();
                    invoiceList.addAll(invoiceResponse.getData());
                    invoicesAdapter.notifyDataSetChanged();
                } else {
                    showErrorToast(invoiceResponse.getMessage());
                }
            }
        });
        invoicesViewModel.getInvoices(Integer.parseInt(sessionHelper.userId()), sessionHelper.getUserLanguageCode());
        invoicesViewModel.rechargingObserver().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result.getStatus()) {
                    invoicesViewModel.getInvoices(Integer.parseInt(sessionHelper.userId()), sessionHelper.getUserLanguageCode());
                } else {
                    showErrorToast(result.getMessage());
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (settingsManager == null) {
            settingsManager = SettingsManager.getInstance();
            settingsManager.setPref(this);
        }
    }

    private void setUpUi() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_invoices);
        binding.toolbar.tittle.setText(getString(R.string.invoices));
        binding.toolbar.backBtn.setVisibility(View.VISIBLE);
        binding.toolbar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        invoicesAdapter = new InvoicesAdapter(this, invoiceList);
        binding.invoicesList.setAdapter(invoicesAdapter);
        binding.invoicesList.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addBalance(View view) {
        new RechargeDialog(this, new RechargeDialog.DialogActions() {
            @Override
            public void onSubmit(String amount) {
//                invoicesViewModel.addCredit(Integer.parseInt(sessionHelper.userId()), amount);
                Intent intent = new Intent(InvoicesActivity.this, PayActivity.class);
                intent.putExtra("priceValue", amount);
                intent.putExtra("from", "invoice");
                startActivity(intent);

            }
        }).show();


    }


}