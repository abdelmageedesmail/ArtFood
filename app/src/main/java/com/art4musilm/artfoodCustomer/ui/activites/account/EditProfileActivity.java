package com.art4musilm.artfoodCustomer.ui.activites.account;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.base.BaseActivity;
import com.art4musilm.artfoodCustomer.commons.Validator;
import com.art4musilm.artfoodCustomer.databinding.ActivityEditProfileBinding;
import com.art4musilm.artfoodCustomer.models.requests.UpdateProfileRequest;
import com.art4musilm.artfoodCustomer.models.response.UpdateProfileResponse;
import com.art4musilm.artfoodCustomer.viewModels.AccountViewModel;

public class EditProfileActivity extends BaseActivity {
    ActivityEditProfileBinding binding;
    AccountViewModel accountViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpUi();
        setUpObservers();
    }

    private void setUpObservers() {
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        binding.setViewModel(accountViewModel);
        binding.setLifecycleOwner(this);
        accountViewModel.getUserProfile(Integer.parseInt(sessionHelper.userId()));
        accountViewModel.observeUpdatingUserProfile().observe(this, new Observer<UpdateProfileResponse>() {
            @Override
            public void onChanged(UpdateProfileResponse updateProfileResponse) {
                if(updateProfileResponse.getStatus()){
                    showSuccessToast(updateProfileResponse.getMessage());
                    finish();
                }else {
                    showErrorToast(updateProfileResponse.getMessage());
                }
            }
        });
    }

    private void setUpUi() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_profile);
        binding.toolbar.tittle.setText(getString(R.string.edit));
        binding.toolbar.backBtn.setVisibility(View.VISIBLE);
        binding.toolbar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void save(View view) {
        String name = binding.nameInput.getText().toString();
        String email = binding.emailInput.getText().toString();
        String phone = binding.phoneInput.getText().toString();
        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(name)) {
            showErrorToast(getString(R.string.nameisreq));
            binding.nameInput.startAnimation(Validator.shakeError());
            focusView = binding.nameInput;
            cancel = true;
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            showErrorToast(getString(R.string.phoneisreq));
            binding.phoneInput.startAnimation(Validator.shakeError());
            focusView = binding.phoneInput;
            cancel = true;
            return;
        }

        if(!Validator.isValidPhoneNumber(phone)){
            showErrorToast(getString(R.string.invalid_phone_num));
            binding.phoneInput.startAnimation(Validator.shakeError());
            focusView = binding.phoneInput;
            cancel = true;
            return;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            showErrorToast(getString(R.string.invalid_email));
            binding.emailInput.startAnimation(Validator.shakeError());
            focusView = binding.emailInput;
            cancel = true;
            return;
        } else if (!Validator.isEmailValid(email)) {
            showErrorToast(getString(R.string.invalid_email));
            binding.emailInput.startAnimation(Validator.shakeError());
            focusView = binding.emailInput;
            cancel = true;
            return;
        }

        if(cancel){
            focusView.requestFocus();
        }else {
            UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
            updateProfileRequest.setEmail(email);
            updateProfileRequest.setId(sessionHelper.userId());
            updateProfileRequest.setMobile(phone);
            updateProfileRequest.setName(name);
            accountViewModel.updateUserProfile(Integer.parseInt(sessionHelper.userId()),sessionHelper.getUserLanguageCode(),null,updateProfileRequest);
        }
    }
}