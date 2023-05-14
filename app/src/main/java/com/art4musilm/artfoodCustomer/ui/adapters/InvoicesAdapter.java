package com.art4musilm.artfoodCustomer.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.databinding.ItemLayoutInvoiceBinding;
import com.art4musilm.artfoodCustomer.models.response.Invoice;

import java.util.List;

public class InvoicesAdapter extends RecyclerView.Adapter<InvoicesAdapter.InvoiceItemVh> {

    Context context;
    List<Invoice> invoices;

    public InvoicesAdapter(Context context, List<Invoice> invoices) {
        this.context = context;
        this.invoices = invoices;
    }

    @NonNull
    @Override
    public InvoiceItemVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutInvoiceBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_layout_invoice, parent, false);
        return new InvoiceItemVh(binding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull InvoiceItemVh holder, int position) {
        holder.binding.setInvoice(invoices.get(position));
    }

    @Override
    public int getItemCount() {
        return invoices.size();
    }

    public static class InvoiceItemVh extends RecyclerView.ViewHolder {
        public ItemLayoutInvoiceBinding binding;

        public InvoiceItemVh(@NonNull ItemLayoutInvoiceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
