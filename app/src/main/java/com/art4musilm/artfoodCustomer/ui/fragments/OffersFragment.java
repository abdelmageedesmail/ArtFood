package com.art4musilm.artfoodCustomer.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.base.BaseFragment;
import com.art4musilm.artfoodCustomer.databinding.FragmentOffersBinding;
import com.art4musilm.artfoodCustomer.models.response.Offer;
import com.art4musilm.artfoodCustomer.models.response.OffersResponse;
import com.art4musilm.artfoodCustomer.models.response.Slider;
import com.art4musilm.artfoodCustomer.models.response.SliderResponse;
import com.art4musilm.artfoodCustomer.ui.activites.FamilyDetailsActivity;
import com.art4musilm.artfoodCustomer.ui.adapters.OffersAdapter;
import com.art4musilm.artfoodCustomer.ui.adapters.SliderAdapter;
import com.art4musilm.artfoodCustomer.viewModels.StoresViewModel;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.labo.kaji.fragmentanimations.FlipAnimation;

import java.util.ArrayList;
import java.util.List;

import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout;

import static com.art4musilm.artfoodCustomer.commons.Utils.autoScrollRecycler;

public class OffersFragment extends BaseFragment implements OffersAdapter.OffersClickAction {
    FragmentOffersBinding binding;
    SliderAdapter sliderAdapter;
    OffersAdapter offersAdapter;
    List<Slider> sliders = new ArrayList<>();
    List<Offer> offers = new ArrayList<>();
    StoresViewModel storesViewModel;

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return FlipAnimation.create(FlipAnimation.LEFT, enter, 1000);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_offers,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.toolbar.tittle.setText(getString(R.string.offers));
        setUpAdsSlider();
        setUpOffersList();
        setUpObservers();
    }

    private void setUpOffersList() {
        offersAdapter = new OffersAdapter(getContext(),sessionHelper.getUserLanguageCode(),offers,this);
        binding.offersList.setAdapter(offersAdapter);
        binding.offersList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.offersList.setHasFixedSize(true);
    }
    private void setUpObservers() {
        //Get top slider
        storesViewModel = new ViewModelProvider(this).get(StoresViewModel.class);
        binding.setViewModel(storesViewModel);
        binding.setLifecycleOwner(this);
        storesViewModel.observeSlider().observe(getViewLifecycleOwner(), new Observer<SliderResponse>() {
            @Override
            public void onChanged(SliderResponse sliderResponse) {
                if(sliderResponse.getStatus()){
                    if(sliderResponse.getData()!=null){
                        sliders.clear();
                        sliders.addAll(sliderResponse.getData());
                        sliderAdapter.notifyDataSetChanged();
                        binding.pageIndicator.setCount(sliderResponse.getData().size());
                    }
                }
            }
        });
        storesViewModel.getSlider();

        //Get offers
        storesViewModel.observeOffers().observe(getViewLifecycleOwner(), new Observer<OffersResponse>() {
            @Override
            public void onChanged(OffersResponse offersResponse) {
                if(offersResponse.getStatus()){
                    if(offersResponse.getData()!=null){
                        offers.clear();
                        offers.addAll(offersResponse.getData());
                        offersAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        storesViewModel.getOffers();
    }
    private void setUpAdsSlider() {
        GravitySnapHelper gravitySnapHelperSlider = new GravitySnapHelper(Gravity.CENTER);
        sliderAdapter = new SliderAdapter(getContext(),sliders);
        LinearLayoutManager layoutManager = new ZoomRecyclerLayout(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.adsSliderLayout.setLayoutManager(layoutManager);
        binding.adsSliderLayout.setAdapter(sliderAdapter);
        gravitySnapHelperSlider.attachToRecyclerView(binding.adsSliderLayout);
        binding.adsSliderLayout.setOnFlingListener(gravitySnapHelperSlider);
        binding.pageIndicator.setCount(sliderAdapter.getItemCount());
        autoScrollRecycler(binding.adsSliderLayout,binding.pageIndicator, sliderAdapter.getItemCount());
    }
    @Override
    public void onOfferSelected(Offer offer) {
        startActivity(new Intent(getContext(), FamilyDetailsActivity.class)
                .putExtra("id",offer.getUserId()+""));
    }
}