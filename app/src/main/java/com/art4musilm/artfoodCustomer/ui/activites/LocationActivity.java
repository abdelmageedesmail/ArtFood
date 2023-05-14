package com.art4musilm.artfoodCustomer.ui.activites;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.base.BaseActivity;
import com.art4musilm.artfoodCustomer.databinding.ActivityLocationBinding;
import com.art4musilm.artfoodCustomer.events.LocationEvent;
import com.art4musilm.artfoodCustomer.gps.GPSTracker;
import com.art4musilm.artfoodCustomer.models.Result;
import com.art4musilm.artfoodCustomer.models.response.CurrentLocation;
import com.art4musilm.artfoodCustomer.ui.activites.account.PersonalInfoActivity;
import com.art4musilm.artfoodCustomer.viewModels.LocationsViewModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;

public class LocationActivity extends BaseActivity implements OnMapReadyCallback {
    public static GoogleMap map;
    public static final int LOCATION_REQUEST = 10366;
    LatLng currentLatLng;
    GPSTracker gpsTracker;
    ActivityLocationBinding binding;
    LocationsViewModel locationsViewModel;
    public static double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_location);
        gpsTracker = new GPSTracker(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        binding.toolbar.tittle.setText(getString(R.string.location));
        checkLocationPermission();
        setObservers();
    }

    private void setObservers() {
        locationsViewModel = new ViewModelProvider(this).get(LocationsViewModel.class);
        binding.setViewModel(locationsViewModel);
        binding.setLifecycleOwner(this);
        locationsViewModel.observeAddingLocation().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result.getStatus()) {
                    showSuccessToast(result.getMessage());
                    setResult(RESULT_OK);
                    finish();
                } else {
                    showErrorToast(result.getMessage());
                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        currentLatLng = new LatLng(gpsTracker.getLatitude(),
                gpsTracker.getLongitude());
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(currentLatLng, 15);
        binding.addressTv.setText(gpsTracker.getCompleteAddressString(LocationActivity.this, currentLatLng.latitude, currentLatLng.longitude));
        map.animateCamera(update);
        map.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                currentLatLng = map.getCameraPosition().target;
                binding.addressTv.setText(gpsTracker.getCompleteAddressString(LocationActivity.this, currentLatLng.latitude, currentLatLng.longitude));
                latitude = currentLatLng.latitude;
                longitude = currentLatLng.longitude;
            }
        });
    }

    void checkLocationPermission() {
        if (!IsLocationPermissionGranted()) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                    LOCATION_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                currentLatLng = new LatLng(gpsTracker.getLatitude(),
                        gpsTracker.getLongitude());
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(currentLatLng, 15);
                map.animateCamera(update);
            }
        }
    }

    public boolean IsLocationPermissionGranted() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            if (checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        if (ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return false;
        if (ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return false;
        return true;
    }

    public void saveLocation(View view) {
        if (currentLatLng != null) {
            if (getIntent().getStringExtra("target").equals("Home")) {
                CurrentLocation currentLocation = new CurrentLocation();
                currentLocation.setAddress(binding.addressTv.getText().toString());
                currentLocation.setLat(currentLatLng.latitude);
                currentLocation.setLng(currentLatLng.longitude);
                sessionHelper.setLocation(currentLocation);
                EventBus.getDefault().postSticky(new LocationEvent(currentLocation));
                finish();
            } else if (getIntent().getStringExtra("target").equals("Checkout")) {
                locationsViewModel.addLocation(sessionHelper.getUserLanguageCode(), sessionHelper.userId(), binding.addressTv.getText().toString() + "", currentLatLng.latitude, currentLatLng.longitude);
            } else if (getIntent().getStringExtra("target").equals("personalInfo")) {
                onBackPressed();
            }
        }
    }
}