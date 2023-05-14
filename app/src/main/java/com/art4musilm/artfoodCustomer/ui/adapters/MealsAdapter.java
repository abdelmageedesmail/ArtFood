package com.art4musilm.artfoodCustomer.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.databinding.ItemLayoutMealBinding;
import com.art4musilm.artfoodCustomer.models.response.Product;

import java.util.ArrayList;
import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealVh> implements Filterable {

    Context context;
    MealsClickAction mealsClickAction;
    List<Product> productList;
    List<Product> productFiltered;
    String lang;
    String distance;

    public MealsAdapter(Context context, MealsClickAction mealsClickAction, List<Product> productList, String lang, String distance) {
        this.context = context;
        this.mealsClickAction = mealsClickAction;
        this.productList = productList;
        this.productFiltered = productList;
        this.lang = lang;
        this.distance = distance;
    }

    @NonNull
    @Override
    public MealVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutMealBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_layout_meal, parent, false);
        return new MealVh(binding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MealVh holder, int position) {
        holder.binding.setLang(lang);
        holder.binding.setProduct(productList.get(position));

        holder.binding.tvDistance.setText("" + distance + " KM");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mealsClickAction.onMealSelected(productList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    productList = productFiltered;
                } else {
                    List<Product> filteredList = new ArrayList<>();
                    for (Product row : productList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    productList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = productList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productList = (ArrayList<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class MealVh extends RecyclerView.ViewHolder {
        public ItemLayoutMealBinding binding;

        public MealVh(@NonNull ItemLayoutMealBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface MealsClickAction {
        void onMealSelected(Product product);
    }
}
