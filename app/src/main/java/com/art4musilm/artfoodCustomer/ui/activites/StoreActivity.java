package com.art4musilm.artfoodCustomer.ui.activites;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.base.BaseActivity;
import com.art4musilm.artfoodCustomer.databinding.ActivityStoreBinding;
import com.art4musilm.artfoodCustomer.models.response.Category;
import com.art4musilm.artfoodCustomer.ui.adapters.CategoriesAdapter;
import com.art4musilm.artfoodCustomer.ui.adapters.MealsAdapter;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends BaseActivity implements
        CategoriesAdapter.CategoryClickAction{
    ActivityStoreBinding binding;
    CategoriesAdapter categoriesAdapter;
    MealsAdapter mealsAdapter;
    List<Category> categoryList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpUi();
    }

    private void setUpUi() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_store);
        binding.toolbar.tittle.setText(getString(R.string.storedetails));
        binding.toolbar.backBtn.setVisibility(View.VISIBLE);
        binding.toolbar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        setUpCategoriesList();
        setUpMealsList();

    }
    private void setUpCategoriesList() {
        GravitySnapHelper gravitySnapHelperSlider = new GravitySnapHelper(Gravity.START);
        categoriesAdapter = new CategoriesAdapter(this,this,sessionHelper.getUserLanguageCode(),categoryList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.categoriesList.setLayoutManager(layoutManager);
        binding.categoriesList.setAdapter(categoriesAdapter);
        gravitySnapHelperSlider.attachToRecyclerView(binding.categoriesList);
        binding.categoriesList.setOnFlingListener(gravitySnapHelperSlider);
    }

    private void setUpMealsList() {
//        GravitySnapHelper gravitySnapHelperSlider = new GravitySnapHelper(Gravity.START);
//        mealsAdapter = new MealsAdapter(this,this);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
//        binding.mealsList.setLayoutManager(layoutManager);
//        binding.mealsList.setAdapter(mealsAdapter);
//        gravitySnapHelperSlider.attachToRecyclerView(binding.mealsList);
//        binding.mealsList.setOnFlingListener(gravitySnapHelperSlider);
    }

    @Override
    public void onCategorySelected(Category category) {

    }

//    @Override
//    public void onMealSelected() {
//        startActivity(new Intent(this,MealActivity.class));
//    }
}