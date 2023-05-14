package com.art4musilm.artfoodCustomer.ui.activites;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.base.BaseActivity;
import com.art4musilm.artfoodCustomer.databinding.ActivityFamilyDetailsBinding;
import com.art4musilm.artfoodCustomer.databinding.ActivityStoreBinding;
import com.art4musilm.artfoodCustomer.gps.GPSTracker;
import com.art4musilm.artfoodCustomer.models.response.Category;
import com.art4musilm.artfoodCustomer.models.response.FamilyCategoriesResponse;
import com.art4musilm.artfoodCustomer.models.response.FamilyDetailsResponse;
import com.art4musilm.artfoodCustomer.models.response.Product;
import com.art4musilm.artfoodCustomer.models.response.ProductsResponse;
import com.art4musilm.artfoodCustomer.ui.adapters.CategoriesAdapter;
import com.art4musilm.artfoodCustomer.ui.adapters.MealsAdapter;
import com.art4musilm.artfoodCustomer.viewModels.StoresViewModel;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.ArrayList;
import java.util.List;

public class FamilyDetailsActivity extends BaseActivity implements
        CategoriesAdapter.CategoryClickAction,
        MealsAdapter.MealsClickAction {
    ActivityFamilyDetailsBinding binding;
    CategoriesAdapter categoriesAdapter;
    MealsAdapter mealsAdapter;
    List<Category> categoryList = new ArrayList<>();
    List<Product> productList = new ArrayList<>();
    StoresViewModel storesViewModel;
    String familyUserId = "0";
    String price, format1, isdelivery, deliveryprice, familystatus, distance;
    GPSTracker mGps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpUi();
        setUpObservers();
    }

    private void setUpObservers() {
        storesViewModel = new ViewModelProvider(this).get(StoresViewModel.class);
        binding.setViewModel(storesViewModel);
        binding.setLifecycleOwner(this);
        storesViewModel.observeFamilyCategories().observe(this, new Observer<FamilyCategoriesResponse>() {
            @Override
            public void onChanged(FamilyCategoriesResponse familyCategoriesResponse) {
                if (familyCategoriesResponse.getStatus()) {
                    if (familyCategoriesResponse.getCategoryProductivefamily() != null) {
                        categoryList.clear();
                        categoryList.addAll(familyCategoriesResponse.getCategoryProductivefamily());
                        categoriesAdapter.notifyDataSetChanged();
                        if (!familyCategoriesResponse.getCategoryProductivefamily().isEmpty()) {
                            storesViewModel.getFamilyProducts(Integer.parseInt(familyCategoriesResponse.getCategoryProductivefamily().get(0).getCat_id()), Integer.parseInt(familyUserId), sessionHelper.getUserLanguageCode());
                        }
                    }
                }
            }
        });
        if (getIntent().getExtras().getString("price") != null) {
            price = getIntent().getExtras().getString("price");
            Log.e("eqPrice", "" + price);
            String format = String.format("%.0f", Double.parseDouble(price));
            binding.tvDeliveryPrice.setText(price + " " + getString(R.string.sr));
        }


        storesViewModel.observeFamilyDetails().observe(this, new Observer<FamilyDetailsResponse>() {
            @Override
            public void onChanged(FamilyDetailsResponse familyDetailsResponse) {
                if (familyDetailsResponse.getStatus()) {
//                    if (familyDetailsResponse.getFamilyDetails() != null) {
                    isdelivery = familyDetailsResponse.getFamilyDetails().getIsdelivery();

                    familystatus = familyDetailsResponse.getFamilyDetails().getFamilystatusNameen();
                    distance = familyDetailsResponse.getFamilyDetails().getDistance();
                    deliveryprice = familyDetailsResponse.getFamilyDetails().getDeliveryprice();
                    String format = String.format("%.0f", Double.parseDouble(deliveryprice));
                    binding.tvDeliveryPrice.setText(format + " " + getString(R.string.sr));
                    if (isdelivery.equals("N")) {
                        price = deliveryprice;
                    }
                    Log.e("detailssss", ".." + isdelivery + "..." + deliveryprice + "..." + familystatus + "..." + distance);
                    format1 = String.format("%.0f", Double.parseDouble(distance));
                    binding.tvDistance.setText(format1);
                    Log.e("expectedTime", "" + familyDetailsResponse.getFamilyDetails().getExpectedtime());
                    storesViewModel.getFamilyCategories(Integer.parseInt(familyDetailsResponse.getFamilyDetails().getId()));
                    familyUserId = familyDetailsResponse.getFamilyDetails().getId();
//                    }
                    setUpMealsList();
                } else {
                    showErrorToast(familyDetailsResponse.getMessage());
                }
            }
        });
        getFamilyDetails();
        storesViewModel.observeFamilyProducts().observe(this, new Observer<ProductsResponse>() {
            @Override
            public void onChanged(ProductsResponse productsResponse) {
                if (productsResponse.getStatus()) {
                    if (productsResponse.getProducts() != null) {
                        productList.clear();
                        productList.addAll(productsResponse.getProducts());
                        mealsAdapter.notifyDataSetChanged();
                    }
                } else {
                    showErrorToast(productsResponse.getMessage());
                }
            }
        });
    }

    private void getFamilyDetails() {
        if (sessionHelper.getLocation() != null) {
            storesViewModel.getFamiliesDetails(Integer.parseInt(getIntent().getStringExtra("id")), sessionHelper.getLocation().getLat(), sessionHelper.getLocation().getLng());
        } else {
            storesViewModel.getFamiliesDetails(Integer.parseInt(getIntent().getStringExtra("id")), mGps.getLatitude(), mGps.getLongitude());
        }
    }

    private void setUpUi() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_family_details);
        binding.toolbar.tittle.setText(getString(R.string.storedetails));
        binding.toolbar.backBtn.setVisibility(View.VISIBLE);
        binding.setLang(sessionHelper.getUserLanguageCode());
        mGps = new GPSTracker(this);
        binding.toolbar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        setUpCategoriesList();

    }

    private void setUpCategoriesList() {
        GravitySnapHelper gravitySnapHelperSlider = new GravitySnapHelper(Gravity.START);
        categoriesAdapter = new CategoriesAdapter(this, this, sessionHelper.getUserLanguageCode(), categoryList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.categoriesList.setLayoutManager(layoutManager);
        binding.categoriesList.setAdapter(categoriesAdapter);
        gravitySnapHelperSlider.attachToRecyclerView(binding.categoriesList);
        binding.categoriesList.setOnFlingListener(gravitySnapHelperSlider);
    }

    private void setUpMealsList() {
        GravitySnapHelper gravitySnapHelperSlider = new GravitySnapHelper(Gravity.START);
        mealsAdapter = new MealsAdapter(this, this, productList, sessionHelper.getUserLanguageCode(), format1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.mealsList.setLayoutManager(layoutManager);
        binding.mealsList.setAdapter(mealsAdapter);
        gravitySnapHelperSlider.attachToRecyclerView(binding.mealsList);
        binding.mealsList.setOnFlingListener(gravitySnapHelperSlider);
        binding.searchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mealsAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onCategorySelected(Category category) {
        storesViewModel.getFamilyProducts(Integer.parseInt(category.getCat_id()), Integer.parseInt(familyUserId), sessionHelper.getUserLanguageCode());
    }

    @Override
    public void onMealSelected(Product product) {
        startActivity(new Intent(this, MealActivity.class)
                .putExtra("id", product.getId())
                .putExtra("familyId", getIntent().getStringExtra("id"))
//                .putExtra("isDelivery", getIntent().getStringExtra("isDelivery"))
                .putExtra("isDelivery", isdelivery)
//                .putExtra("status", getIntent().getStringExtra("status"))
                .putExtra("status", familystatus)
                .putExtra("deliveryPrice", price));
    }
}