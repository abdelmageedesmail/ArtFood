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
import com.art4musilm.artfoodCustomer.databinding.ItemLayoutProductiveFamilyBinding;
import com.art4musilm.artfoodCustomer.databinding.ItemLayoutStoreBinding;

public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.ImageVh> {

    Context context;
    StoresClickAction storesClickAction;

    public StoresAdapter(Context context, StoresClickAction storesClickAction) {
        this.context = context;
        this.storesClickAction = storesClickAction;
    }

    @NonNull
    @Override
    public ImageVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutStoreBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_layout_store, parent, false);
        return new ImageVh(binding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ImageVh holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storesClickAction.onStoreSelected();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class ImageVh extends RecyclerView.ViewHolder {
        public ItemLayoutStoreBinding binding;

        public ImageVh(@NonNull ItemLayoutStoreBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface StoresClickAction{
        void onStoreSelected();
    }
}
