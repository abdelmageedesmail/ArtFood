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
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class DeliveryTimeDialog extends BottomSheetDialog {
    DeliveryTimeDialogLayoutBinding binding;
    ArrayAdapter arrayAdapter;
    int status=0;
    public interface DialogActions{
        void onSubmit(int status,String date,String timeFrom,String timeTo);
    }
    public DeliveryTimeDialog(@NonNull Context context,DialogActions dialogActions) {
        super(context);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.delivery_time_dialog_layout, null, false);
        arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item,
                context.getResources().getTextArray(R.array.delivery_time_options));
        binding.specificTimeSp.setAdapter(arrayAdapter);
        setContentView(binding.getRoot());
        binding.specificTimeSp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){
                    case 0 :
                        status = 0;
                        binding.timeLayout.setVisibility(View.GONE);
                        break;
                    case 1 :
                        status = 1;
                        binding.timeLayout.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        binding.toTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openTimePicker(context, new Utils.TimeAction() {
                    @Override
                    public void onGetTime(String date) {
                        binding.toTimeInput.setText(date);
                    }
                });
            }
        });
        binding.fromTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openTimePicker(context, new Utils.TimeAction() {
                    @Override
                    public void onGetTime(String date) {
                        binding.fromTimeInput.setText(date);
                    }
                });
            }
        });
        binding.dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openDatePicker(context, new Utils.DateAction() {
                    @Override
                    public void onGetDate(String date) {
                        binding.dateInput.setText(date);
                    }
                });
            }
        });
        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = binding.dateInput.getText().toString();
                String timeFrom = binding.fromTimeInput.getText().toString();
                String timeTo = binding.toTimeInput.getText().toString();
                if(binding.specificTimeSp.getText().toString().isEmpty()){
                    binding.specificTimeSp.startAnimation(Validator.shakeError());
                    return;
                }
                if(status==0){
                    dialogActions.onSubmit(status,date,timeFrom,timeTo);
                    dismiss();
                }else {
                    if(date.isEmpty()){
                        binding.dateInput.startAnimation(Validator.shakeError());
                        return;
                    }
                    if(timeTo.isEmpty()){
                        binding.toTimeInput.startAnimation(Validator.shakeError());
                        return;
                    }
                    if(timeFrom.isEmpty()){
                        binding.fromTimeInput.startAnimation(Validator.shakeError());
                        return;
                    }
                    dialogActions.onSubmit(status,date,timeFrom,timeTo);
                    dismiss();
                }

            }
        });
    }
}


