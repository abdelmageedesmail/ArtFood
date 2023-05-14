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
import com.art4musilm.artfoodCustomer.commons.numberPicker.Enums.ActionEnum;
import com.art4musilm.artfoodCustomer.commons.numberPicker.Interface.ValueChangedListener;
import com.art4musilm.artfoodCustomer.databinding.ItemLayoutCartBinding;
import com.art4musilm.artfoodCustomer.databinding.ItemLayoutMealBinding;
import com.art4musilm.artfoodCustomer.models.CartModel;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartItemVh> {

    Context context;
    CartItemClickAction cartItemClickAction;
    List<CartModel> cartModelList;

    public CartAdapter(Context context, CartItemClickAction cartItemClickAction, List<CartModel> cartModelList) {
        this.context = context;
        this.cartItemClickAction = cartItemClickAction;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public CartItemVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutCartBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_layout_cart, parent, false);
        return new CartItemVh(binding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull CartItemVh holder, int position) {
        holder.binding.setCartModel(cartModelList.get(position));
        holder.binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartItemClickAction.onCartItemDeleted(cartModelList.get(position));
            }
        });
        holder.binding.counter.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                cartItemClickAction.onCartItemChangeCount(value,cartModelList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public static class CartItemVh extends RecyclerView.ViewHolder {
        public ItemLayoutCartBinding binding;

        public CartItemVh(@NonNull ItemLayoutCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface CartItemClickAction{
        void onCartItemChangeCount(int count,CartModel cartModel);
        void onCartItemDeleted(CartModel cartModel);
    }
}
