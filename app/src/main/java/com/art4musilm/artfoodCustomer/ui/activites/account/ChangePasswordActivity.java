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
import com.art4musilm.artfoodCustomer.databinding.ActivityChangePasswordBinding;
import com.art4musilm.artfoodCustomer.models.Result;
import com.art4musilm.artfoodCustomer.viewModels.AccountViewModel;

import static com.art4musilm.artfoodCustomer.commons.Validator.isPasswordValid;

public class ChangePasswordActivity extends BaseActivity {
    ActivityChangePasswordBinding binding;
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
        accountViewModel.observeChangingOldPassword().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if(result.getStatus()){
                    showSuccessToast(result.getMessage());
                    finish();
                }else {
                    showErrorToast(result.getMessage());
                }
            }
        });
    }

    private void setUpUi() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_change_password);
        binding.toolbar.tittle.setText(getString(R.string.change_password));
        binding.toolbar.backBtn.setVisibility(View.VISIBLE);
        binding.toolbar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void changePassword(View view) {
        String oldPassword = binding.oldPasswordInput.getText().toString();
        String password = binding.newPasswordInput.getText().toString();
        String rePassword = binding.verifyNewPasswordInput.getText().toString();
        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(oldPassword)) {
            showErrorToast(getString(R.string.invalidpassword));
            binding.oldPasswordInput.startAnimation(Validator.shakeError());
            focusView = binding.oldPasswordInput;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            showErrorToast(getString(R.string.invalidpassword));
            binding.newPasswordInput.startAnimation(Validator.shakeError());
            focusView = binding.newPasswordInput;
            cancel = true;
        }

        if (TextUtils.isEmpty(rePassword)) {
            showErrorToast(getString(R.string.invalidpassword));
            binding.verifyNewPasswordInput.startAnimation(Validator.shakeError());
            focusView = binding.verifyNewPasswordInput;
            cancel = true;
        }
        if(!password.equals(rePassword)){
            binding.newPasswordInput.startAnimation(Validator.shakeError());
            binding.verifyNewPasswordInput.startAnimation(Validator.shakeError());
            showErrorToast(getString(R.string.passwordsnotmatches));
            focusView = binding.newPasswordInput;
            cancel = true;
        }
        if(cancel){
            focusView.requestFocus();
        }else {
            accountViewModel.changeOldPassword(sessionHelper.getUserLanguageCode(),sessionHelper.userId(),oldPassword,password);
        }
    }
}