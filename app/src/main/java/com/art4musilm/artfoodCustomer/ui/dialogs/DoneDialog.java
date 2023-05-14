package com.art4musilm.artfoodCustomer.ui.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.databinding.DoneDialogLayoutBinding;

import libs.mjn.prettydialog.PrettyDialog;

public class DoneDialog extends PrettyDialog {
    DoneDialogLayoutBinding binding;
    public DoneDialog(Context context,String msg) {
        super(context);
        setAnimationEnabled(true);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.done_dialog_layout, null, false);
        setContentView(binding.getRoot());
        binding.setMsg(msg);
        binding.dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
