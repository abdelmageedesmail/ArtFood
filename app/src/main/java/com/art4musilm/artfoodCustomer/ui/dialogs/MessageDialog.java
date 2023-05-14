package com.art4musilm.artfoodCustomer.ui.dialogs;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.databinding.MessageDialogLayoutBinding;

import libs.mjn.prettydialog.PrettyDialog;


public class MessageDialog extends PrettyDialog {
    MessageDialogLayoutBinding binding;
   public MessageDialog(Context context,String msg,String desc) {
        super(context);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.message_dialog_layout, null, false);
        setContentView(binding.getRoot());
        binding.setMsg(msg);
        binding.setDesc(desc);
    }
}
