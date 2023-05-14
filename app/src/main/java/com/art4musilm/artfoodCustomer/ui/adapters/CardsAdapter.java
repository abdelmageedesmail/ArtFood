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
import com.art4musilm.artfoodCustomer.databinding.ItemCardBinding;
import com.art4musilm.artfoodCustomer.databinding.ItemLayoutCartBinding;
import com.art4musilm.artfoodCustomer.models.CartModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import company.tap.gosellapi.internal.api.models.SavedCard;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardItemVh> {

    Context context;
    CartItemClickAction cartItemClickAction;
    List<SavedCard> list;

    public CardsAdapter(Context context, List<SavedCard> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CardItemVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCardBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_card, parent, false);
        return new CardItemVh(binding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull CardItemVh holder, int position) {
        holder.binding.tvCardName.setText(list.get(position).getBrandName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CardItemVh extends RecyclerView.ViewHolder {
        public ItemCardBinding binding;

        public CardItemVh(@NonNull ItemCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    //    public class MyHolder extends RecyclerView.ViewHolder{
//
//    }
    public interface CartItemClickAction {
        void onCartItemChangeCount(int count, CartModel cartModel);

        void onCartItemDeleted(CartModel cartModel);
    }
}
