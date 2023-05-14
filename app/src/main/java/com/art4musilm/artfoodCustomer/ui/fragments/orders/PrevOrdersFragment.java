package com.art4musilm.artfoodCustomer.ui.fragments.orders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.base.BaseFragment;
import com.art4musilm.artfoodCustomer.databinding.FragmentCurrentOrdersBinding;
import com.art4musilm.artfoodCustomer.databinding.FragmentPrevOrdersBinding;
import com.art4musilm.artfoodCustomer.models.response.Order;
import com.art4musilm.artfoodCustomer.models.response.OrderResponse;
import com.art4musilm.artfoodCustomer.ui.activites.PreviousOrderDetailsActivity;
import com.art4musilm.artfoodCustomer.ui.adapters.OrdersAdapter;
import com.art4musilm.artfoodCustomer.viewModels.OrdersViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PrevOrdersFragment extends BaseFragment implements OrdersAdapter.OrdersClickAction {
    FragmentPrevOrdersBinding binding;
    OrdersAdapter ordersAdapter;
    List<Order> orders = new ArrayList<>();
    OrdersViewModel ordersViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_prev_orders,container,false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpOrdersList();
        setUpObservers();
    }

    private void setUpOrdersList() {
        ordersAdapter = new OrdersAdapter(getContext(),orders,this,false,sessionHelper.getUserLanguageCode());
        binding.cOrdersList.setAdapter(ordersAdapter);
        binding.cOrdersList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.cOrdersList.setHasFixedSize(true);
    }
    private void setUpObservers() {
        ordersViewModel = new ViewModelProvider(this).get(OrdersViewModel.class);
        binding.setViewModel(ordersViewModel);
        binding.setLifecycleOwner(this);
        ordersViewModel.observeMyOrders().observe(getViewLifecycleOwner(), new Observer<OrderResponse>() {
            @Override
            public void onChanged(OrderResponse orderResponse) {
                if(orderResponse.getStatus()){
                    orders.clear();
                    orders.addAll(orderResponse.getOrders());
                    ordersAdapter.notifyDataSetChanged();
                }else {
                    showErrorToast(orderResponse.getMessage());
                }
            }
        });
        ordersViewModel.getOrders(sessionHelper.userId(),false);
    }

    @Override
    public void onOrderSelected(Order order,boolean isCurrent) {
        Gson gson = new Gson();
        startActivity(new Intent(getContext(), PreviousOrderDetailsActivity.class)
        .putExtra("order",gson.toJson(order)));
    }



    @Override
    public void onOrderCanceled(Order order) {

    }

    @Override
    public void onOrderDelivered(Order order) {

    }
}