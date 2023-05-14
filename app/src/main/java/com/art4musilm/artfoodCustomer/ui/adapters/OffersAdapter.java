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
import com.art4musilm.artfoodCustomer.databinding.ItemLayoutOfferBinding;
import com.art4musilm.artfoodCustomer.databinding.ItemLayoutOrderBinding;
import com.art4musilm.artfoodCustomer.models.response.Offer;

import java.util.List;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OfferVh> {

    Context context;
    OffersClickAction offersClickAction;
    List<Offer> offers;
    String lang;

    public OffersAdapter(Context context,String lang,List<Offer>offers, OffersClickAction offersClickAction) {
        this.context = context;
        this.offersClickAction = offersClickAction;
        this.offers=offers;
        this.lang=lang;
    }

    @NonNull
    @Override
    public OfferVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutOfferBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_layout_offer, parent, false);
        return new OfferVh(binding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull OfferVh holder, int position) {
        holder.binding.setOffer(offers.get(position));
        holder.binding.setLang(lang);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offersClickAction.onOfferSelected(offers.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public static class OfferVh extends RecyclerView.ViewHolder {
        public ItemLayoutOfferBinding binding;

        public OfferVh(@NonNull ItemLayoutOfferBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OffersClickAction{
        void onOfferSelected(Offer offer);
    }
}
