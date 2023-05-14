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
import com.art4musilm.artfoodCustomer.models.response.Family;

import java.util.ArrayList;
import java.util.List;

public class ProductivityFamiliesAdapter extends
        RecyclerView.Adapter<ProductivityFamiliesAdapter.ImageVh> implements Filterable {

    Context context;
    StoresClickAction storesClickAction;
    List<Family> families;
    List<Family> familiesFiltered;
    String lang;

    public ProductivityFamiliesAdapter(Context context, StoresClickAction storesClickAction, List<Family> families, String lang) {
        this.context = context;
        this.storesClickAction = storesClickAction;
        this.families = families;
        this.familiesFiltered = families;
        this.lang = lang;
    }

    @NonNull
    @Override
    public ImageVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutProductiveFamilyBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_layout_productive_family, parent, false);
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
            String formatEquation = String.format("%.0f", Double.parseDouble(families.get(position).getPriceequation()));
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
                storesClickAction.onFamilySelected(families.get(position));
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


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    families = familiesFiltered;
                } else {
                    List<Family> filteredList = new ArrayList<>();
                    for (Family row : families) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    families = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = families;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                families = (ArrayList<Family>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ImageVh extends RecyclerView.ViewHolder {
        public ItemLayoutProductiveFamilyBinding binding;

        public ImageVh(@NonNull ItemLayoutProductiveFamilyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface StoresClickAction {
        void onFamilySelected(Family family);
    }
}
