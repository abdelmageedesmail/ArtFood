package com.art4musilm.artfoodCustomer.ui.activites;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.base.BaseActivity;
import com.art4musilm.artfoodCustomer.commons.Utils;
import com.art4musilm.artfoodCustomer.databinding.ActivityCurrentOrderDetailsBinding;
import com.art4musilm.artfoodCustomer.gps.GPSTracker;
import com.art4musilm.artfoodCustomer.models.DriverLocationModel;
import com.art4musilm.artfoodCustomer.models.Result;
import com.art4musilm.artfoodCustomer.models.response.Order;
import com.art4musilm.artfoodCustomer.ui.dialogs.ConfirmDialog;
import com.art4musilm.artfoodCustomer.viewModels.OrdersViewModel;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentOrderDetailsActivity extends BaseActivity {

    ActivityCurrentOrderDetailsBinding binding;
    OrdersViewModel ordersViewModel;
    Order order;
    GPSTracker mGps;
    long minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpUi();
        setUpObservers();
    }

    private void setUpObservers() {
        ordersViewModel = new ViewModelProvider(this).get(OrdersViewModel.class);
    }

    private void setUpUi() {
        mGps = new GPSTracker(this);
        Gson gson = new Gson();
        order = gson.fromJson(getIntent().getStringExtra("order"), Order.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_current_order_details);
        binding.setOrder(order);
        binding.setLang(sessionHelper.getUserLanguageCode());
        String format = String.format("%.2f", Double.parseDouble(order.getDistance()));
        format = arabicToDecimal(format).replace("٫", ".");
        binding.tvDistance.setText(format);
        int i = Integer.parseInt(order.getShipping()) + Integer.parseInt(order.getTotalprise());
        binding.tvTotalPrice.setText("" + i + getString(R.string.sr));
        binding.toolbar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.toolbar.tittle.setText(R.string.currentorderdetails);
        differenceDate(order.getDate());
        if (minutes > 10) {
            binding.btnCancelOrder.setVisibility(View.GONE);
        }
    }

    private static final String arabic = "\u06f0\u06f1\u06f2\u06f3\u06f4\u06f5\u06f6\u06f7\u06f8\u06f9";

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

    public void call(View view) {
        Utils.dialPhoneNumber(this, order.getDriver_mobile());
    }

    public void trackOrder(View view) {
        ordersViewModel.getDriverLocation(order.getId(), order.getDriver_id(), new OrdersViewModel.DriverData() {
            @Override
            public void onDriverReturnData(DriverLocationModel model) {
                if (model.getData().size() > 0) {
                    String lat = model.getData().get(0).getLat();
                    String lng = model.getData().get(0).getLng();
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr=" + mGps.getLatitude() + "," + mGps.getLongitude() + "&daddr=" + lat + "," + lng));
                    startActivity(intent);
                } else {
                    showErrorToast(getString(R.string.noavaliableLocation));
                }
            }
        });
    }

    private void differenceDate(String date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");

        try {

            Date oldDate = dateFormat.parse(date);
            System.out.println(oldDate);

            Date currentDate = new Date();

            long diff = currentDate.getTime() - oldDate.getTime();
            long seconds = diff / 1000;
            minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;

            if (oldDate.before(currentDate)) {

                Log.e("oldDate", "is previous date");
                Log.e("Difference: ", " seconds: " + seconds + " minutes: " + minutes
                        + " hours: " + hours + " days: " + days);

            }

            // Log.e("toyBornTime", "" + toyBornTime);

        } catch (ParseException e) {

            e.printStackTrace();
        }
//        return minutes;

    }


    public void contactWithMangement(View view) {
        startActivity(new Intent(CurrentOrderDetailsActivity.this, ContactUsActivity.class));
    }

    public void cancelOrder(View view) {
        new ConfirmDialog(this, getString(R.string.areyousure), new ConfirmDialog.ConfirmClickActions() {
            @Override
            public void onConfirmed() {
                ordersViewModel.cancelOrder(order.getId(), sessionHelper.getUserLanguageCode(), new OrdersViewModel.OrderActions() {
                    @Override
                    public void onOrderActionDone(Result result) {
                        if (result.getStatus()) {
                            showSuccessToast(result.getMessage());
                            finish();
                        } else {
                            showErrorToast(result.getMessage());
                        }
                    }
                });
            }
        }).show();
    }
}