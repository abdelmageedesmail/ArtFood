package com.art4musilm.artfoodCustomer.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.databinding.ItemLayoutMealMasterAdditionBinding;
import com.art4musilm.artfoodCustomer.models.response.ProdAddition;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.List;

public class MasterMealsAdditionsAdapter extends RecyclerView.Adapter<MasterMealsAdditionsAdapter.MealVh> {

    Context context;
    MealsPropertiesAdapter.MealPropertyClickAction mealsClickAction;
    List<ProdAddition> prodAdditions;
    String lang;
    public MasterMealsAdditionsAdapter(Context context, MealsPropertiesAdapter.MealPropertyClickAction mealsClickAction, List<ProdAddition> prodAdditions, String lang) {
        this.context = context;
        this.mealsClickAction = mealsClickAction;
        this.prodAdditions = prodAdditions;
        this.lang = lang;
    }

    @NonNull
    @Override
    public MealVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutMealMasterAdditionBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_layout_meal_master_addition, parent, false);
        return new MealVh(binding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MealVh holder, int position) {
        holder.binding.setProductMasterAddition(prodAdditions.get(position));
        holder.binding.setLang(lang);
        if(prodAdditions.get(position).getAddProduct()!=null){
            if(!prodAdditions.get(position).getAddProduct().isEmpty()){
                MealsPropertiesAdapter propertiesAdapter = new MealsPropertiesAdapter(context,Integer.parseInt(prodAdditions.get(position).getId()),mealsClickAction,prodAdditions.get(position).getAddProduct(),lang);
                GravitySnapHelper gravitySnapHelperSlider = new GravitySnapHelper(Gravity.CENTER);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                layoutManager.setOrientation(RecyclerView.HORIZONTAL);
                holder.binding.mealPropertyList.setLayoutManager(layoutManager);
                holder.binding.mealPropertyList.setAdapter(propertiesAdapter);
                gravitySnapHelperSlider.attachToRecyclerView(holder.binding.mealPropertyList);
                holder.binding.mealPropertyList.setOnFlingListener(gravitySnapHelperSlider);
            }
        }
    }

    @Override
    public int getItemCount() {
        return prodAdditions.size();
    }

    public static class MealVh extends RecyclerView.ViewHolder {
        public ItemLayoutMealMasterAdditionBinding binding;

        public MealVh(@NonNull ItemLayoutMealMasterAdditionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
