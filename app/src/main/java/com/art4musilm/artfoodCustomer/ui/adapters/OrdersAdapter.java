package com.art4musilm.artfoodCustomer.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.databinding.ItemLayoutOrderBinding;
import com.art4musilm.artfoodCustomer.databinding.ItemLayoutProductiveFamilyBinding;
import com.art4musilm.artfoodCustomer.models.response.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderVh> {

    Context context;
    OrdersClickAction ordersClickAction;
    List<Order> orders;
    boolean isCurrent;
    String lang;
    long minutes;

    public OrdersAdapter(Context context, List<Order> orders, OrdersClickAction ordersClickAction, boolean isCurrentOrder, String lang) {
        this.context = context;
        this.ordersClickAction = ordersClickAction;
        this.isCurrent = isCurrentOrder;
        this.lang = lang;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutOrderBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_layout_order, parent, false);
        return new OrderVh(binding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull OrderVh holder, int position) {
        holder.binding.setLang(lang);
        holder.binding.setOrder(orders.get(position));
        holder.binding.setIsCurrent(isCurrent);
        int i = Integer.parseInt(orders.get(position).getTotalprise()) + Integer.parseInt(orders.get(position).getShipping());
        holder.binding.tvTotalPrice.setText("" + i);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ordersClickAction.onOrderSelected(orders.get(position), isCurrent);
            }
        });
        holder.binding.orderDeliveredBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ordersClickAction.onOrderDelivered(orders.get(position));
            }
        });

        holder.binding.cancelOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ordersClickAction.onOrderCanceled(orders.get(position));
            }
        });
        differenceDate(orders.get(position).getDate());
        Log.e("datee", "" + minutes);
        if (minutes > 10) {
            holder.binding.cancelOrderBtn.setVisibility(View.GONE);
        } else {
            holder.binding.cancelOrderBtn.setVisibility(View.VISIBLE);
        }
    }


    private void differenceDate(String date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");

        try {

            Date oldDate = dateFormat.parse(date);
            System.out.println(oldDate);

            Date currentDate = new Date();

            long diff = currentDate.getTime() - oldDate.getTime();
            long seconds = diff / 1000;
            minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;

            if (oldDate.before(currentDate)) {

                Log.e("oldDate", "is previous date");
                Log.e("Difference: ", " seconds: " + seconds + " minutes: " + minutes
                        + " hours: " + hours + " days: " + days);

            }

            // Log.e("toyBornTime", "" + toyBornTime);

        } catch (ParseException e) {

            e.printStackTrace();
        }
//        return minutes;

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class OrderVh extends RecyclerView.ViewHolder {
        public ItemLayoutOrderBinding binding;

        public OrderVh(@NonNull ItemLayoutOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OrdersClickAction {
        void onOrderSelected(Order order, boolean isCurrent);

        void onOrderCanceled(Order order);

        void onOrderDelivered(Order order);
    }
}
