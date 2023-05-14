package com.art4musilm.artfoodCustomer.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.databinding.FragmentOrdersBinding;
import com.art4musilm.artfoodCustomer.ui.adapters.TabsAdapter;
import com.art4musilm.artfoodCustomer.ui.fragments.orders.CurrentOrdersFragment;
import com.art4musilm.artfoodCustomer.ui.fragments.orders.PrevOrdersFragment;
import com.labo.kaji.fragmentanimations.FlipAnimation;

public class OrdersFragment extends Fragment {
    FragmentOrdersBinding binding;
    TabsAdapter tabsAdapter;

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return FlipAnimation.create(FlipAnimation.LEFT, enter, 1000);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.toolbar.tittle.setText(getString(R.string.myorders));
        setUpTabs();
    }

    private void setUpTabs() {
        tabsAdapter = new TabsAdapter(getChildFragmentManager());
        tabsAdapter.addFragment(new CurrentOrdersFragment(), getString(R.string.corders));
        tabsAdapter.addFragment(new PrevOrdersFragment(), getString(R.string.porders));
        binding.pager.setAdapter(tabsAdapter);
        binding.tabs.setupWithViewPager(binding.pager);
    }
}