package com.art4musilm.artfoodCustomer.ui.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.commons.Utils;
import com.art4musilm.artfoodCustomer.commons.Validator;
import com.art4musilm.artfoodCustomer.databinding.DeliveryTimeDialogLayoutBinding;
import com.art4musilm.artfoodCustomer.databinding.RechargeDialogLayoutBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class RechargeDialog extends BottomSheetDialog {
   RechargeDialogLayoutBinding binding;
    public interface DialogActions{
        void onSubmit(String amount);
    }
    public RechargeDialog(@NonNull Context context, DialogActions dialogActions) {
        super(context);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.recharge_dialog_layout, null, false);
        setContentView(binding.getRoot());
        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.amountInput.getText().toString().isEmpty()){
                    binding.amountInput.startAnimation(Validator.shakeError());
                    return;
                }
                dialogActions.onSubmit(binding.amountInput.getText().toString());
                dismiss();
            }
        });
    }
}


