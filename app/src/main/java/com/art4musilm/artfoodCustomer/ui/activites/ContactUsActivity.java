package com.art4musilm.artfoodCustomer.ui.activites;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.base.BaseActivity;
import com.art4musilm.artfoodCustomer.commons.Validator;
import com.art4musilm.artfoodCustomer.databinding.ActivityContactUsBinding;
import com.art4musilm.artfoodCustomer.models.Result;
import com.art4musilm.artfoodCustomer.viewModels.AppInfoViewModel;

public class ContactUsActivity extends BaseActivity {
    ActivityContactUsBinding binding;
    AppInfoViewModel appInfoViewModel;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_us);
        binding.toolbar.tittle.setText(getString(R.string.contact_us));
        binding.toolbar.backBtn.setVisibility(View.VISIBLE);
        binding.toolbar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    type = "2";
                } else if (i == 2) {
                    type = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        setUpObservers();
    }

    private void setUpObservers() {
        appInfoViewModel = new ViewModelProvider(this).get(AppInfoViewModel.class);
        binding.setViewModel(appInfoViewModel);
        binding.setLifecycleOwner(this);
        appInfoViewModel.observeAddingComment().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result s) {
                if (s.getStatus()) {
                    showSuccessToast(s.getMessage());
                    finish();
                } else {
                    showErrorToast(s.getMessage());
                }

            }
        });
    }

    public void send(View view) {
        String name = binding.nameInput.getText().toString();
        String email = binding.emailInput.getText().toString();
        String msg = binding.msgInput.getText().toString();

        if (TextUtils.isEmpty(name)) {
            binding.nameInput.requestFocus();
            binding.nameInput.startAnimation(Validator.shakeError());
            return;
        }

        if (TextUtils.isEmpty(email) || !Validator.isEmailValid(email)) {
            binding.emailInput.requestFocus();
            binding.emailInput.startAnimation(Validator.shakeError());
            return;
        }

        if (TextUtils.isEmpty(msg)) {
            binding.msgInput.requestFocus();
            binding.msgInput.startAnimation(Validator.shakeError());
            return;
        }

        if (type == null) {
            showErrorToast(getString(R.string.pleaseSelectContactType));
            return;
        }
        appInfoViewModel.addComment(name, email, msg, sessionHelper.getUserLanguageCode(), type, binding.phoneInput.getText().toString());

    }
}