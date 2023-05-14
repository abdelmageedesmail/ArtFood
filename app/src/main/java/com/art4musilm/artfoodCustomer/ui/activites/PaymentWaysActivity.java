package com.art4musilm.artfoodCustomer.ui.activites;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.base.BaseActivity;
import com.art4musilm.artfoodCustomer.databinding.ActivityPaymentWaysBinding;
import com.art4musilm.artfoodCustomer.models.Result;
import com.art4musilm.artfoodCustomer.models.response.InvoiceResponse;
import com.art4musilm.artfoodCustomer.viewModels.InvoicesViewModel;

public class PaymentWaysActivity extends BaseActivity {

    ActivityPaymentWaysBinding binding;
    InvoicesViewModel invoicesViewModel;
    int paymentWay = 0;
    String walletAmount = "0";
    double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpUi();
        setUpObservers();

        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getString("from") != null) {
                if (getIntent().getExtras().getString("from").equals("payment")) {
                    Intent intent = new Intent();
                    intent.putExtra("paymentWay", 1 + "");
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

        }
    }

    private void setUpObservers() {
        invoicesViewModel = new ViewModelProvider(this).get(InvoicesViewModel.class);
        binding.setViewModel(invoicesViewModel);
        invoicesViewModel.observeInvoices().observe(this, new Observer<InvoiceResponse>() {
            @Override
            public void onChanged(InvoiceResponse invoiceResponse) {
                walletAmount = invoiceResponse.getBalance();
                binding.walletLayout.setVisibility(View.VISIBLE);
            }
        });
        binding.setLifecycleOwner(this);
    }

    private void setUpUi() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_ways);
        binding.toolbar.tittle.setText(getString(R.string.payment_ways));
        binding.toolbar.backBtn.setVisibility(View.VISIBLE);
        if (getIntent().getStringExtra("from").equals("cart")) {
            binding.relativeLayout.setVisibility(View.VISIBLE);
            binding.cardPrices.setVisibility(View.VISIBLE);
            binding.mealsCount.setText(getIntent().getStringExtra("itemsCount"));
            binding.mealPriceTv.setText(getIntent().getStringExtra("mealPrice"));
            total = Double.parseDouble(binding.mealPriceTv.getText().toString().replace(" " + getString(R.string.sr), "").replace("٫", "")) + Double.parseDouble(getIntent().getStringExtra("deliveryPrice").replace(" " + getString(R.string.sr), "").replace("٫", ""));
            binding.totaPrice.setText("" + total + " " + getString(R.string.sr));
            binding.deliveryPriceTv.setText(getIntent().getStringExtra("deliveryPrice") + " " + getString(R.string.sr));
        } else {
            binding.relativeLayout.setVisibility(View.GONE);
            binding.cardPrices.setVisibility(View.GONE);
        }

        binding.toolbar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        setUpPaymentWaysSelector();
    }

    private void setUpPaymentWaysSelector() {
        selectCashPay(true);
        binding.onlineCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isPressed()) {
                    selectOnlinePay(b);
                }
            }
        });

        binding.cashCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isPressed()) {
                    selectCashPay(b);
                }
            }
        });

        binding.walletCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isPressed()) {
                    selectWalletPay(b);
                }
            }
        });

        binding.payOnlineLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.onlineCheck.setChecked(true);
                selectOnlinePay(true);
            }
        });

        binding.payWalletLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.walletCheck.setChecked(true);
                selectWalletPay(true);
            }
        });

        binding.payCashLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.cashCheck.setChecked(true);
                selectCashPay(true);
            }
        });
    }

    private void selectWalletPay(boolean b) {
        binding.payCashLayout.setSelected(false);
        binding.payOnlineLayout.setSelected(false);
        binding.payWalletLayout.setSelected(true);
        binding.onlieIc.setSelected(!b);
        binding.cashIc.setSelected(!b);
        binding.walletIc.setSelected(b);
        binding.cashCheck.setChecked(!b);
        binding.walletCheck.setChecked(b);
        binding.onlineCheck.setChecked(!b);
        invoicesViewModel.getInvoices(Integer.parseInt(sessionHelper.userId()), sessionHelper.getUserLanguageCode());
        paymentWay = 2;
    }

    private void selectCashPay(boolean b) {
        binding.payCashLayout.setSelected(true);
        binding.payOnlineLayout.setSelected(false);
        binding.payWalletLayout.setSelected(false);
        binding.onlieIc.setSelected(!b);
        binding.cashIc.setSelected(b);
        binding.walletIc.setSelected(!b);
        binding.cashCheck.setChecked(b);
        binding.walletCheck.setChecked(!b);
        binding.onlineCheck.setChecked(!b);
        binding.walletLayout.setVisibility(View.GONE);
        paymentWay = 0;
    }

    private void selectOnlinePay(boolean b) {
        binding.payCashLayout.setSelected(false);
        binding.payOnlineLayout.setSelected(true);
        binding.payWalletLayout.setSelected(false);
        binding.onlieIc.setSelected(b);
        binding.cashIc.setSelected(!b);
        binding.walletIc.setSelected(!b);
        binding.cashCheck.setChecked(!b);
        binding.walletCheck.setChecked(!b);
        binding.onlineCheck.setChecked(b);
        binding.walletLayout.setVisibility(View.GONE);
        paymentWay = 1;

    }

    public void confirmOrder(View view) {
        if (paymentWay == 1) {
            String[] split = getIntent().getStringExtra("totalPrice").split(" ");
            Intent i = new Intent(this, PayActivity.class);
            i.putExtra("priceValue", "" + total);
            i.putExtra("from", "cart");
            startActivity(i);
            finish();
        } else {
            Intent intent = new Intent();
            intent.putExtra("paymentWay", paymentWay + "");
            if (paymentWay == 2) {
                intent.putExtra("walletAmount", walletAmount + "");
            }
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}