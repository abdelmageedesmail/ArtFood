package com.art4musilm.artfoodCustomer.ui.activites;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.base.BaseActivity;
import com.art4musilm.artfoodCustomer.databinding.ActivityMealBinding;
import com.art4musilm.artfoodCustomer.models.CartModel;
import com.art4musilm.artfoodCustomer.models.response.NestedAddition;
import com.art4musilm.artfoodCustomer.models.response.ProdAddition;
import com.art4musilm.artfoodCustomer.models.response.ProductDetails;
import com.art4musilm.artfoodCustomer.models.response.ProductDetailsResponse;
import com.art4musilm.artfoodCustomer.ui.adapters.MasterMealsAdditionsAdapter;
import com.art4musilm.artfoodCustomer.ui.adapters.MealsPropertiesAdapter;
import com.art4musilm.artfoodCustomer.viewModels.ShoppingCartViewModel;
import com.art4musilm.artfoodCustomer.viewModels.StoresViewModel;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class MealActivity extends BaseActivity implements MealsPropertiesAdapter.MealPropertyClickAction {
    ActivityMealBinding binding;
    MasterMealsAdditionsAdapter masterMealsAdditionsAdapter;
    StoresViewModel storesViewModel;
    List<ProdAddition> prodAdditionList = new ArrayList<>();
    HashMap selectedAdditions = new HashMap();
    ProductDetails productDetails;
    ShoppingCartViewModel shoppingCartViewModel;
    List<CartModel> cartModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpUi();
        setUpObservers();
    }

    private void setUpObservers() {
        storesViewModel = new ViewModelProvider(this).get(StoresViewModel.class);
        shoppingCartViewModel = new ViewModelProvider(this).get(ShoppingCartViewModel.class);
        binding.setViewModel(storesViewModel);
        binding.setLang(sessionHelper.getUserLanguageCode());
        binding.setLifecycleOwner(this);

        shoppingCartViewModel.observeCartItems().observe(this, new Observer<List<CartModel>>() {
            @Override
            public void onChanged(List<CartModel> cartModels) {
                cartModelList.clear();
                cartModelList.addAll(cartModels);
            }
        });


        storesViewModel.observeProductDetails().observe(this, new Observer<ProductDetailsResponse>() {
            @Override
            public void onChanged(ProductDetailsResponse productDetailsResponse) {
                if (productDetailsResponse.getStatus()) {
                    if (productDetailsResponse.getData() != null) {
                        if (!productDetailsResponse.getData().isEmpty()) {
                            prodAdditionList.clear();
                            prodAdditionList.addAll(productDetailsResponse.getData().get(0).getProdAdditions());
                            productDetails = productDetailsResponse.getData().get(0);
                            masterMealsAdditionsAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    showErrorToast(productDetailsResponse.getMessage());
                }
            }
        });
        Log.e("mealId", "" + getIntent().getStringExtra("id"));
        storesViewModel.getMealDetails(Integer.parseInt(getIntent().getStringExtra("id")), "ar");

        shoppingCartViewModel.observeCartItems().observe(this, new Observer<List<CartModel>>() {
            @Override
            public void onChanged(List<CartModel> cartModels) {
                binding.cart.setNumber(cartModels.size());
            }
        });

    }

    private void setUpUi() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meal);
        binding.tittle.setText(R.string.meal_details);
        binding.backBtn.setVisibility(View.VISIBLE);
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MealActivity.this, ShoppingCartActivity.class));
            }
        });
        setUpMealPropertiesList();

    }

    private void setUpMealPropertiesList() {
        GravitySnapHelper gravitySnapHelperSlider = new GravitySnapHelper(Gravity.CENTER);
        masterMealsAdditionsAdapter = new MasterMealsAdditionsAdapter(this, this, prodAdditionList, sessionHelper.getUserLanguageCode());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.additionsList.setLayoutManager(layoutManager);
        binding.additionsList.setAdapter(masterMealsAdditionsAdapter);
        gravitySnapHelperSlider.attachToRecyclerView(binding.additionsList);
        binding.additionsList.setOnFlingListener(gravitySnapHelperSlider);
    }

    @Override
    public void onMealPropertySelected(int masterAdditionId, NestedAddition prodAddition) {
        selectedAdditions.put(masterAdditionId, prodAddition);
        binding.total.setText(Float.parseFloat(storesViewModel.observeProductDetails().getValue().getData().get(0).getPrice()) + calculateTotalsWithAdditions() + " " + getString(R.string.sr));
    }

    public void goShoppingCart(View view) {
        if (sessionHelper.isLogin()) {
//            Log.e("statusss", "" + getIntent().getExtras().getString("status"));
            if (getIntent().getExtras().getString("status").equals("Open")) {
                if (productDetails != null) {
                    if (cartModelList.size() > 0) {
                        if (cartModelList.get(0).getFamilyId().equals(getIntent().getExtras().getString("familyId"))) {
                            CartModel cartModel = new CartModel();
                            cartModel.setId(Integer.parseInt(productDetails.getId()));
                            cartModel.setName(productDetails.getName());
                            cartModel.setImgPath(productDetails.getImage());
                            cartModel.setDesc(productDetails.getDescription());
                            cartModel.setCount(binding.counter.getValue());
                            cartModel.setPrice(Float.parseFloat(productDetails.getPrice()));

                            cartModel.setAdditionsTotal(calculateTotalsWithAdditions());
                            if (getAdditionsIds().isEmpty()) {
                                cartModel.setAdditions_ids("0,0");
                            } else {
                                cartModel.setAdditions_ids(getAdditionsIds());

                                Log.e("additionsIds", "" + getAdditionsIds());
                            }

                            cartModel.setDeliveryPrice(getIntent().getExtras().getString("deliveryPrice"));
                            cartModel.setFamilyId(getIntent().getExtras().getString("familyId"));
                            cartModel.setIsDelivery(getIntent().getExtras().getString("isDelivery"));
                            shoppingCartViewModel.insertCartItem(cartModel);
                            startActivity(new Intent(MealActivity.this, ShoppingCartActivity.class));
                        } else {
                            Toast.makeText(this, getString(R.string.cannotAddOrder), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        CartModel cartModel = new CartModel();
                        cartModel.setId(Integer.parseInt(productDetails.getId()));
                        cartModel.setName(productDetails.getName());
                        cartModel.setImgPath(productDetails.getImage());
                        cartModel.setDesc(productDetails.getDescription());
                        cartModel.setCount(binding.counter.getValue());
                        cartModel.setPrice(Float.parseFloat(productDetails.getPrice()));

                        cartModel.setAdditionsTotal(calculateTotalsWithAdditions());
                        if (getAdditionsIds().isEmpty()) {
                            cartModel.setAdditions_ids("0,0");
                        } else {
                            cartModel.setAdditions_ids(getAdditionsIds());
                        }
                        cartModel.setDeliveryPrice(getIntent().getExtras().getString("deliveryPrice"));
                        cartModel.setFamilyId(getIntent().getExtras().getString("familyId"));
                        cartModel.setIsDelivery(getIntent().getExtras().getString("isDelivery"));
                        shoppingCartViewModel.insertCartItem(cartModel);
                        startActivity(new Intent(MealActivity.this, ShoppingCartActivity.class));
                    }

                }
            } else {
                showErrorToast(getString(R.string.familyIsNotAvaliableNow));
            }

        } else {
            showErrorToast(getString(R.string.pleaseLoginFirst));
        }
    }

    float calculateTotalsWithAdditions() {
        float result = 0;
        for (Object value : selectedAdditions.values()) {
            result += Float.parseFloat(((NestedAddition) value).getPrice());
        }
        return result;
    }

    String getAdditionsIds() {
        StringBuilder result = new StringBuilder();
        for (Object value : selectedAdditions.keySet()) {
            result.append(value.toString() + "," + ((NestedAddition) selectedAdditions.get(value)).getId() + ",");
        }
        return result.toString().replaceAll(",$", "");
    }

//    String getParentAdditionsIds() {
//        StringBuilder result = new StringBuilder();
//        for (Object value : selectedAdditions.keySet()) {
//            result.append(value.toString() + "," + ((NestedAddition) selectedAdditions.get(value)).getId() + ",");
//        }
//        return result.toString().replaceAll(",$", "");
//    }
}