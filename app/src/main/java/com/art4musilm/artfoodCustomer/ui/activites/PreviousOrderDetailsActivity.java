package com.art4musilm.artfoodCustomer.ui.activites;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.base.BaseActivity;
import com.art4musilm.artfoodCustomer.databinding.ActivityPrevuiosOrderDetailsBinding;
import com.art4musilm.artfoodCustomer.models.Result;
import com.art4musilm.artfoodCustomer.models.response.Order;
import com.art4musilm.artfoodCustomer.models.response.OrderResponse;
import com.art4musilm.artfoodCustomer.ui.dialogs.ReviewDialog;
import com.art4musilm.artfoodCustomer.viewModels.OrdersViewModel;
import com.google.gson.Gson;

public class PreviousOrderDetailsActivity extends BaseActivity {
    ActivityPrevuiosOrderDetailsBinding binding;
    Order order;
    OrdersViewModel ordersViewModel;
    float review = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpUi();
        setUpViewModel();
    }

    private void setUpViewModel() {
        ordersViewModel = new ViewModelProvider(this).get(OrdersViewModel.class);
        binding.setViewModel(ordersViewModel);
        binding.setLifecycleOwner(this);
        ordersViewModel.observeOrderReview().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result.getStatus()) {
                    showSuccessToast(result.getMessage());
                    binding.rate.setRating(review);
                } else {
                    showErrorToast(result.getMessage());
                }
            }
        });
        ordersViewModel.observeResendOrderLiveData().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result orderResponse) {
                if (orderResponse.getStatus()) {
                    showSuccessToast(orderResponse.getMessage());
                    finish();
                } else {
                    showErrorToast(orderResponse.getMessage());
                }
            }
        });
    }

    private void setUpUi() {
        Gson gson = new Gson();
        order = gson.fromJson(getIntent().getStringExtra("order"), Order.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_prevuios_order_details);
        binding.setOrder(order);
        int i = Integer.parseInt(order.getShipping()) + Integer.parseInt(order.getTotalprise());
        binding.tvTotalPrice.setText("" + i + getString(R.string.sr));
        String format = String.format("%.1f", Double.parseDouble(order.getDistance()));
        format = arabicToDecimal(format).replace("٫", ".");
        binding.tvDistance.setText(format);
        binding.setLang(sessionHelper.getUserLanguageCode());
        binding.toolbar.tittle.setText(R.string.order_details);
        binding.toolbar.backBtn.setVisibility(View.VISIBLE);
        binding.toolbar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private static String arabicToDecimal(String number) {
        char[] chars = new char[number.length()];
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);
    }

    public void reviewOrder(View view) {
        new ReviewDialog(this, new ReviewDialog.ReviewDialogActions() {
            @Override
            public void onReviewSubmitted(float rate, String msg) {
                review = rate;
                ordersViewModel.reviewOrder(order.getId(), sessionHelper.getUserLanguageCode(), rate, msg);
            }
        }).show();
    }

    public void resendOrder(View view) {
        ordersViewModel.resendOrder(order.getId());
    }
}