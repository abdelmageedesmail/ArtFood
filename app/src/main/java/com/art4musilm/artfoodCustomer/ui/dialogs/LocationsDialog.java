package com.art4musilm.artfoodCustomer.ui.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.databinding.ItemLocationsDialogBinding;
import com.art4musilm.artfoodCustomer.models.response.Location;
import com.art4musilm.artfoodCustomer.ui.adapters.LocationsAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class LocationsDialog extends BottomSheetDialog implements LocationsAdapter.LocationItemClickAction {
    ItemLocationsDialogBinding binding;
    LocationsAdapter locationsAdapter;
    LocationDialogActions locationDialogActions;
    public LocationsDialog(@NonNull Context context, List<Location> locations, LocationDialogActions locationDialogActions) {
        super(context);
        this.locationDialogActions = locationDialogActions;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_locations_dialog, null, false);
        setContentView(binding.getRoot());
        locationsAdapter = new LocationsAdapter(context,this,locations);
        binding.locationsList.setAdapter(locationsAdapter);
        binding.locationsList.setLayoutManager(new LinearLayoutManager(context));
        binding.addNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationDialogActions.onAddNewLocationClicked();
                dismiss();
            }
        });
    }

    @Override
    public void onLocationSelected(Location location) {
        locationDialogActions.onLocationSelected(location);
        dismiss();
    }

    public interface LocationDialogActions{
        void onLocationSelected(Location location);
        void onAddNewLocationClicked();
    }
}

