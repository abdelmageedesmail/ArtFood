package com.art4musilm.artfoodCustomer.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.databinding.ItemLayoutProductiveFamilyBinding;
import com.art4musilm.artfoodCustomer.databinding.ItemLayoutProductiveSearchFamilyBinding;
import com.art4musilm.artfoodCustomer.models.response.Family;
import com.art4musilm.artfoodCustomer.models.response.FamilySearchResponse;

import java.util.ArrayList;
import java.util.List;

public class SearchProductivityFamiliesAdapter extends
        RecyclerView.Adapter<SearchProductivityFamiliesAdapter.ImageVh> {

    Context context;
    StoresSerchClickAction storesClickAction;
    List<FamilySearchResponse.DataEntity> families;
    String lang;

    public SearchProductivityFamiliesAdapter(Context context, StoresSerchClickAction storesClickAction, List<FamilySearchResponse.DataEntity> families, String lang) {
        this.context = context;
        this.storesClickAction = storesClickAction;
        this.families = families;
        this.lang = lang;
    }

    @NonNull
    @Override
    public ImageVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutProductiveSearchFamilyBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_layout_productive_search_family, parent, false);
        return new ImageVh(binding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ImageVh holder, int position) {
        holder.binding.setFamily(families.get(position));
        holder.binding.setLang(lang);
        if (families.get(position).getIsdelivery().equals("Y")) {
            String formatPrice = String.format("%.0f", Double.parseDouble(families.get(position).getDeliveryprice()));
            formatPrice = arabicToDecimal(formatPrice).replace("٫", ".");
            Log.e("formatPrice", "" + formatPrice);
            if (formatPrice.equals("0.00")) {
                holder.binding.tvDeliveryPrice.setText("" + 0 + context.getString(R.string.sr));
            } else {
                holder.binding.tvDeliveryPrice.setText("" + Integer.parseInt(formatPrice) + context.getString(R.string.sr));
            }

        } else {
//            String formatEquation = String.format("%.0f", Double.parseDouble(families.get(position).getPriceequation()));
            String formatEquation = String.format("%.0f", Double.parseDouble(families.get(position).getDeliveryprice()));
            formatEquation = arabicToDecimal(formatEquation).replace("٫", ".");
            if (formatEquation.equals("0.00")) {
                holder.binding.tvDeliveryPrice.setText("" + 0 + context.getString(R.string.sr));
            } else {
                holder.binding.tvDeliveryPrice.setText("" + Integer.parseInt(formatEquation) + context.getString(R.string.sr));
            }

        }
//        String format = String.format("%.2f", Double.parseDouble(families.get(position).getDistance()));
//        format = arabicToDecimal(format).replace("٫", ".");
//        if (format.equals("0.00")) {
//            holder.binding.tvDistance.setText("" + 0 + " KM");
//        } else {
//            holder.binding.tvDistance.setText("" + Integer.parseInt(format) + " KM");
//        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storesClickAction.onFamilySearchSelected(families.get(position));
            }
        });
    }

    private static final String arabic = "\u06f0\u06f1\u06f2\u06f3\u06f4\u06f5\u06f6\u06f7\u06f8\u06f9";

    private static String arabicToDecimal(String number) {
        char[] chars = new char[number.length()];
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);
    }

    @Override
    public int getItemCount() {
        return families.size();
    }


    public static class ImageVh extends RecyclerView.ViewHolder {
        public ItemLayoutProductiveSearchFamilyBinding binding;

        public ImageVh(@NonNull ItemLayoutProductiveSearchFamilyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface StoresSerchClickAction {
        void onFamilySearchSelected(FamilySearchResponse.DataEntity family);
    }
}
