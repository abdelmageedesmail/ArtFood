package com.art4musilm.artfoodCustomer.ui.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.commons.Validator;
import com.art4musilm.artfoodCustomer.databinding.ReviewDialogBinding;

import libs.mjn.prettydialog.PrettyDialog;

public class ReviewDialog extends PrettyDialog {
    ReviewDialogBinding binding;
    public ReviewDialog(Context context,ReviewDialogActions reviewDialogActions) {
        super(context);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.review_dialog, null, false);
        setContentView(binding.getRoot());
        binding.addReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.reviewTxtInput.getText().toString().isEmpty()){
                    binding.reviewTxtInput.startAnimation(Validator.shakeError());
                    return;
                }
                reviewDialogActions.onReviewSubmitted(binding.rateBar.getRating()==0?1:binding.rateBar.getRating(),binding.reviewTxtInput.getText().toString());
                dismiss();
            }
        });
    }

    public interface ReviewDialogActions{
        void onReviewSubmitted(float rate,String msg);
    }
}
