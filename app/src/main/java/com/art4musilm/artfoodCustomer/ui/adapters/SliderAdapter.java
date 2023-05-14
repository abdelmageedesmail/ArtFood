package com.art4musilm.artfoodCustomer.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.databinding.ItemSliderLayoutBinding;
import com.art4musilm.artfoodCustomer.models.response.Slider;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.ImageVh> {

    Context context;
    List<Slider> sliders;

    public SliderAdapter(Context context, List<Slider> sliders) {
        this.context = context;
        this.sliders = sliders;
    }

    @NonNull
    @Override
    public ImageVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSliderLayoutBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_slider_layout, parent, false);
        return new ImageVh(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageVh holder, int position) {
        holder.binding.setSlider(sliders.get(position));
    }

    @Override
    public int getItemCount() {
        return sliders.size();
    }

    public static class ImageVh extends RecyclerView.ViewHolder {
        public ItemSliderLayoutBinding binding;

        public ImageVh(@NonNull ItemSliderLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
