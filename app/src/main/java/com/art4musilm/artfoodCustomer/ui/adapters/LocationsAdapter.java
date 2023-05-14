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
import com.art4musilm.artfoodCustomer.databinding.ItemLayoutLocationBinding;
import com.art4musilm.artfoodCustomer.models.response.Location;

import java.util.List;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.LocationItemVh> {

    Context context;
    LocationItemClickAction locationItemClickAction;
    List<Location> locations;

    public LocationsAdapter(Context context, LocationItemClickAction locationItemClickAction, List<Location> locations) {
        this.context = context;
        this.locationItemClickAction = locationItemClickAction;
        this.locations = locations;
    }

    @NonNull
    @Override
    public LocationItemVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutLocationBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_layout_location, parent, false);
        return new LocationItemVh(binding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull LocationItemVh holder, int position) {
        holder.binding.setLocation(locations.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationItemClickAction.onLocationSelected(locations.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public static class LocationItemVh extends RecyclerView.ViewHolder {
        public ItemLayoutLocationBinding binding;

        public LocationItemVh(@NonNull ItemLayoutLocationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface LocationItemClickAction{
        void onLocationSelected(Location location);
    }
}
