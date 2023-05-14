package com.art4musilm.artfoodCustomer.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.databinding.ItemLayoutMealBinding;
import com.art4musilm.artfoodCustomer.databinding.ItemLayoutMealPropertyBinding;
import com.art4musilm.artfoodCustomer.models.response.NestedAddition;

import java.util.List;

public class MealsPropertiesAdapter extends RecyclerView.Adapter<MealsPropertiesAdapter.MealVh> {

    Context context;
    MealPropertyClickAction mealsClickAction;
    List<NestedAddition> prodAdditions;
    int masterId;
    String lang;
    public int mSelectedItem = -1;

    public MealsPropertiesAdapter(Context context,int masterId, MealPropertyClickAction mealsClickAction, List<NestedAddition> prodAdditions, String lang) {
        this.context = context;
        this.mealsClickAction = mealsClickAction;
        this.prodAdditions = prodAdditions;
        this.lang = lang;
        this.masterId = masterId;
    }

    @NonNull
    @Override
    public MealVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutMealPropertyBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_layout_meal_property, parent, false);
        return new MealVh(binding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MealVh holder, int position) {
        holder.binding.item.setSelected(mSelectedItem==position);
        holder.binding.setLang(lang);
        holder.binding.setAddition(prodAdditions.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedItem = position;
                notifyDataSetChanged();
                mealsClickAction.onMealPropertySelected(masterId,prodAdditions.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return prodAdditions.size();
    }

    public static class MealVh extends RecyclerView.ViewHolder {
        public ItemLayoutMealPropertyBinding binding;

        public MealVh(@NonNull ItemLayoutMealPropertyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface MealPropertyClickAction{
        void onMealPropertySelected(int masterAdditionId,NestedAddition nestedAddition);
    }
}
