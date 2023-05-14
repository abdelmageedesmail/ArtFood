package com.art4musilm.artfoodCustomer.ui.activites;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.base.BaseActivity;
import com.art4musilm.artfoodCustomer.commons.Validator;
import com.art4musilm.artfoodCustomer.databinding.ActivityShoppingCartBinding;
import com.art4musilm.artfoodCustomer.models.CartModel;
import com.art4musilm.artfoodCustomer.models.Result;
import com.art4musilm.artfoodCustomer.models.requests.CheckoutRequest;
import com.art4musilm.artfoodCustomer.models.response.CalculatedDeliveryResponse;
import com.art4musilm.artfoodCustomer.models.response.Location;
import com.art4musilm.artfoodCustomer.models.response.LocationsResponse;
import com.art4musilm.artfoodCustomer.ui.adapters.CartAdapter;
import com.art4musilm.artfoodCustomer.ui.dialogs.ConfirmDialog;
import com.art4musilm.artfoodCustomer.ui.dialogs.DeliveryTimeDialog;
import com.art4musilm.artfoodCustomer.ui.dialogs.LocationsDialog;
import com.art4musilm.artfoodCustomer.ui.dialogs.MessageDialog;
import com.art4musilm.artfoodCustomer.viewModels.CheckoutViewModel;
import com.art4musilm.artfoodCustomer.viewModels.LocationsViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Field;


public class ShoppingCartActivity extends BaseActivity implements CartAdapter.CartItemClickAction {
    private static final int PAYMENT_METHOD = 1002;
    ActivityShoppingCartBinding binding;
    CartAdapter cartAdapter;
    List<CartModel> cartModelList = new ArrayList<>();
    LocationsViewModel locationsViewModel;
    CheckoutViewModel checkoutViewModel;
    int totalItems = 0;
    String paymentWay;
    double walletAmount = 0;
    int deliveryTimeStatus = 0;
    String selectedDate;
    String selectedTimeFrom;
    String selectedTimeTo;
    double totalPrice = 0;
    List<String> cartCollection;
    String addressId;
    private Double price;
    private double total = 0;
    private double deliveryPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpUi();
        setUpObserves();
    }

    private void setUpObserves() {
        shoppingCartViewModel.observeCartItems().observe(this, new Observer<List<CartModel>>() {
            @Override
            public void onChanged(List<CartModel> cartModels) {
                cartModelList.clear();
                cartModelList.addAll(cartModels);
                cartAdapter.notifyDataSetChanged();
                binding.cart.setNumber(cartModels.size());
                binding.itemsCount.setText(calcCartCount(cartModels) + "");
                if (cartModels.size() > 0) {
                    deliveryPrice = Double.parseDouble(cartModels.get(0).getDeliveryPrice());
                    String format = String.format("%.0f", deliveryPrice);
                    format = arabicToDecimal(format);
                    binding.deliveryPriceTv.setText(format + " " + getString(R.string.sr));
                }
                totalItems = cartModels.size();
                cartCollection = collectCartItemsToCheckout(cartModels);
            }
        });
        shoppingCartViewModel.observeTotalCartPrice().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                if (aDouble != null) {
                    price = aDouble;

                    total = price + deliveryPrice;
                    String format = String.format("%.0f", total);
                    format = arabicToDecimal(format);

                    binding.totaPrice.setText(format + " " + getString(R.string.sr));
                    binding.mealPriceTv.setText(aDouble + " " + getString(R.string.sr));
                    totalPrice = total;
                }
            }
        });
        locationsViewModel = new ViewModelProvider(this).get(LocationsViewModel.class);
        checkoutViewModel = new ViewModelProvider(this).get(CheckoutViewModel.class);
        binding.setViewModel(locationsViewModel);
        binding.setLifecycleOwner(this);
        locationsViewModel.observeLocations().observe(this, new Observer<LocationsResponse>() {
            @Override
            public void onChanged(LocationsResponse locationsResponse) {
                if (locationsResponse.getStatus()) {
                    if (locationsResponse.getData().isEmpty()) {
                        showErrorToast(getString(R.string.nolocatons));
                        startActivityForResult(new Intent(ShoppingCartActivity.this, LocationActivity.class)
                                .putExtra("target", "Checkout"), 1205);
                    } else {
                        showLocationsDialog(locationsResponse.getData());
                    }
                }
            }
        });
        checkoutViewModel.observeCheckout().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {

                if (result.getStatus()) {
                    shoppingCartViewModel.clearCart();
                    showConfirmMessage(result.getMessage());
                } else {
                    showErrorToast(result.getMessage());
                }
            }
        });

        locationsViewModel.observeCalculatedDelivery().observe(this, new Observer<CalculatedDeliveryResponse>() {
            @Override
            public void onChanged(CalculatedDeliveryResponse calculatedDeliveryResponse) {
                if (calculatedDeliveryResponse.getStatus()) {

                    deliveryPrice = Double.parseDouble(calculatedDeliveryResponse.getPrice());
                    String format = String.format("%.0f", deliveryPrice);
                    format = arabicToDecimal(format);

                    total = price + deliveryPrice;

                    String totalFormat = String.format("%.0f", total);
                    totalFormat = arabicToDecimal(totalFormat);

                    binding.totaPrice.setText(totalFormat + " " + getString(R.string.sr));
                    binding.deliveryPriceTv.setText(format + " " + getString(R.string.sr));
                }
            }
        });
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

    private void setUpUi() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_cart);
        binding.setCartViewModel(shoppingCartViewModel);
        binding.setLifecycleOwner(this);
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        cartAdapter = new CartAdapter(this, this, cartModelList);
        binding.cartList.setAdapter(cartAdapter);
        binding.cartList.setLayoutManager(new LinearLayoutManager(this));
        binding.cartList.setHasFixedSize(true);
    }


    @Override
    public void onCartItemChangeCount(int count, CartModel cartModel) {
        cartModel.setCount(count);
        shoppingCartViewModel.updateCartItem(cartModel);
    }

    @Override
    public void onCartItemDeleted(CartModel cartModel) {
        new ConfirmDialog(this, getString(R.string.delete_item), new ConfirmDialog.ConfirmClickActions() {
            @Override
            public void onConfirmed() {
                shoppingCartViewModel.deleteCartItem(cartModel);
            }
        }).show();
    }


    //Open Delivery time selector dialog
    public void openDateTimeDialog(View view) {
        if (addressId != null) {
            new DeliveryTimeDialog(this, new DeliveryTimeDialog.DialogActions() {
                @Override
                public void onSubmit(int status, String date, String timeFrom, String timeTo) {
                    deliveryTimeStatus = status;
                    selectedDate = date;
                    selectedTimeFrom = timeFrom;
                    selectedTimeTo = timeTo;

                    startActivityForResult(new Intent(ShoppingCartActivity.this, PaymentWaysActivity.class)
                            .putExtra("itemsCount", binding.itemsCount.getText() + "")
                            .putExtra("price", "" + price)
                            .putExtra("from", "cart")
                            .putExtra("mealPrice", binding.mealPriceTv.getText().toString())
                            .putExtra("deliveryPrice", "" + deliveryPrice)
                            .putExtra("totalPrice", binding.totaPrice.getText().toString() + ""), PAYMENT_METHOD);

                }
            }).show();
        } else {
            binding.selectaddress.startAnimation(Validator.shakeError());
            showErrorToast(getString(R.string.pleaseselectyouradress));
        }
    }

    public void selectLocation(View view) {
        locationsViewModel.getLocations(sessionHelper.userId(), sessionHelper.getUserLanguageCode());
    }

    void showLocationsDialog(List<Location> locations) {
        new LocationsDialog(this, locations, new LocationsDialog.LocationDialogActions() {
            @Override
            public void onLocationSelected(Location location) {
                if (location.getAddress() != null && !location.getAddress().isEmpty()) {
                    binding.addressTv.setVisibility(View.VISIBLE);
                    binding.addressTv.setText(location.getAddress());
                    addressId = location.getId();
                    //Here we call Api for get New Price offer of new address
                    if (cartModelList.get(0).getIsDelivery().equals("N")) {
                        locationsViewModel.calculateDeliveryPrice(cartModelList.get(0).getFamilyId(), addressId);
                    }

                }
            }

            @Override
            public void onAddNewLocationClicked() {
                startActivityForResult(new Intent(ShoppingCartActivity.this, LocationActivity.class).putExtra("target", "Checkout"), 1205);
            }
        }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYMENT_METHOD) {
            if (resultCode == RESULT_OK) {
                paymentWay = data.getStringExtra("paymentWay");
                if (paymentWay.equals("2")) {
                    walletAmount = Double.parseDouble(data.getStringExtra("walletAmount"));
                }
                doCheckout();
            }
        } else {
            if (resultCode == RESULT_OK) {
                locationsViewModel.getLocations(sessionHelper.userId(), sessionHelper.getUserLanguageCode());
            }
        }
    }


    void showConfirmMessage(String msg) {
        new MessageDialog(this, msg, getString(R.string.orderstatusknow))
                .show();
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }


    List<String> collectCartItemsToCheckout(List<CartModel> cartModels) {
        List<String> result = new ArrayList<>();
        for (CartModel cartModel : cartModels) {
            Log.e("addition", "" + cartModel.getAdditions_ids());
            String record = cartModel.getId() + "," + cartModel.getCount() + "," + cartModel.getPrice() + "," + cartModel.getAdditions_ids();
            result.add(record.replaceAll(",$", ""));
        }
        return result;
    }


    void doCheckout() {
        if (paymentWay.equals("2")) {
            if (totalPrice > walletAmount) {
                showErrorToast(getString(R.string.notenghf));
                return;
            }
        }
        Log.e("totalPrice", price + "..." + deliveryPrice);
        total = price + deliveryPrice;
        String format = String.format("%.0f", total);
        format = arabicToDecimal(format);

        CheckoutRequest checkoutRequest = new CheckoutRequest();
        checkoutRequest.setUserId(sessionHelper.userId());
        checkoutRequest.setTotalItems(totalItems + "");
        checkoutRequest.setTotlitemsprise(total + "");
        checkoutRequest.setShipping(binding.deliveryPriceTv.getText().toString());
        checkoutRequest.setNotes(binding.notesInput.getText().toString());
        checkoutRequest.setDeliveryMethod("0");
        checkoutRequest.setPaymentMethod(paymentWay);
        checkoutRequest.setDeliverytime(deliveryTimeStatus + "");
        checkoutRequest.setDeliverydate(selectedDate);
        checkoutRequest.setHourfrom(selectedTimeFrom);
        checkoutRequest.setHourto(selectedTimeTo);
        checkoutRequest.setUseraddress(addressId);
        checkoutRequest.setItems(cartCollection);
        checkoutRequest.setLang(sessionHelper.getUserLanguageCode());
        Log.e("formattt", sessionHelper.userId() + "..total" + total + "...shipping" + binding.deliveryPriceTv.getText().toString() + "...paymentWay" + paymentWay + "...deliverTimeStatus" + deliveryTimeStatus + "...AddressId" + addressId + "...cartCollection" + cartCollection.get(0));
        checkoutViewModel.checkout(checkoutRequest);
    }

    Integer calcCartCount(List<CartModel> cartModels) {
        int result = 0;
        for (CartModel cartModel : cartModels) {
            result += cartModel.getCount();
        }
        return result;
    }
}