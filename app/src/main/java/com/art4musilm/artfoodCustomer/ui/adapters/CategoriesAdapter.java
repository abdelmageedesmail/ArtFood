package com.art4musilm.artfoodCustomer.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.databinding.ItemLayoutCategoryBinding;
import com.art4musilm.artfoodCustomer.databinding.ItemSliderLayoutBinding;
import com.art4musilm.artfoodCustomer.models.response.Category;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ImageVh> {

    Context context;
    CategoryClickAction categoryClickAction;
    public int mSelectedItem = 0;
    private String lang;
    private List<Category> categories;
    private int[] colors = {R.color.colorAccent,R.color.colorPrimaryDark,R.color.green};

    public CategoriesAdapter(Context context, CategoryClickAction categoryClickAction, String lang, List<Category> categories) {
        this.context = context;
        this.categoryClickAction = categoryClickAction;
        this.lang = lang;
        this.categories = categories;
    }

    @NonNull
    @Override
    public ImageVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutCategoryBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_layout_category, parent, false);
        return new ImageVh(binding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ImageVh holder, int position) {
        if(mSelectedItem==position){
            holder.binding.selection.setVisibility(View.VISIBLE);
        }else {
            holder.binding.selection.setVisibility(View.INVISIBLE);
        }
        holder.binding.setLang(lang);
        holder.binding.setCategory(categories.get(position));
        holder.binding.categoryCard.getBackground().setTint(context.getColor(colors[position % 3]));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedItem = position;
                notifyDataSetChanged();
                categoryClickAction.onCategorySelected(categories.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ImageVh extends RecyclerView.ViewHolder {
        public ItemLayoutCategoryBinding binding;

        public ImageVh(@NonNull ItemLayoutCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface CategoryClickAction{
        void onCategorySelected(Category category);
    }
}
