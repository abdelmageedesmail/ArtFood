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
import com.art4musilm.artfoodCustomer.databinding.ActivityForgetPasswordBinding;
import com.art4musilm.artfoodCustomer.models.Result;
import com.art4musilm.artfoodCustomer.models.response.ForgetPassStep1Res;
import com.art4musilm.artfoodCustomer.models.response.VerifyNewPasswordCode;
import com.art4musilm.artfoodCustomer.viewModels.AccountViewModel;

import static com.art4musilm.artfoodCustomer.commons.Validator.isPasswordValid;

public class ForgetPasswordActivity extends BaseActivity {
    ActivityForgetPasswordBinding binding;
    AccountViewModel accountViewModel;
    String userId="";
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

        //Step 1
        accountViewModel.observeForgetPassword().observe(this, new Observer<ForgetPassStep1Res>() {
            @Override
            public void onChanged(ForgetPassStep1Res forgetPassStep1Res) {
                if(forgetPassStep1Res.getStatus()){
                    showSuccessToast(forgetPassStep1Res.getMessage());
                    binding.passwordRecoveryS1.setVisibility(View.GONE);
                    binding.passwordRecoveryS2.setVisibility(View.VISIBLE);
                }else {
                    showErrorToast(forgetPassStep1Res.getMessage());
                }
            }
        });

        //Step 2
        accountViewModel.observeVerifyingPasswordCode().observe(this, new Observer<VerifyNewPasswordCode>() {
            @Override
            public void onChanged(VerifyNewPasswordCode verifyNewPasswordCode) {
                if(verifyNewPasswordCode.getStatus()){
                    showSuccessToast(verifyNewPasswordCode.getMessage());
                    userId = verifyNewPasswordCode.getId();
                    binding.passwordRecoveryS1.setVisibility(View.GONE);
                    binding.passwordRecoveryS2.setVisibility(View.GONE);
                    binding.passwordRecoveryS3.setVisibility(View.VISIBLE);
                }else {
                    showErrorToast(verifyNewPasswordCode.getMessage());
                }
            }
        });

        //Step 3
        accountViewModel.observeChangingPassword().observe(this, new Observer<Result>() {
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
        binding = DataBindingUtil.setContentView(this,R.layout.activity_forget_password);
        binding.toolbar.tittle.setText(getString(R.string.password_recovery));
        binding.toolbar.backBtn.setVisibility(View.VISIBLE);
        binding.toolbar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }



    public void sendCode(View view) {
        binding.phoneInput.setError(null);
        String phone = binding.phoneInput.getText().toString();
        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(phone)) {
            showErrorToast(getString(R.string.phoneisreq));
            binding.phoneInput.startAnimation(Validator.shakeError());
            focusView = binding.phoneInput;
            cancel = true;
        }else if(!Validator.isValidPhoneNumber(phone)){
            showErrorToast(getString(R.string.invalid_phone_num));
            binding.phoneInput.startAnimation(Validator.shakeError());
            focusView = binding.phoneInput;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        }else {
            accountViewModel.forgetPassword(sessionHelper.getUserLanguageCode(),phone);
        }
    }

    public void recoverPassword(View view) {
        binding.code.setError(null);
        String code = binding.code.getText().toString();
        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(code)) {
            showErrorToast(getString(R.string.pleaseinsertsentcode));
            binding.code.startAnimation(Validator.shakeError());
            focusView = binding.code;
            cancel = true;
        }
        if(cancel){
            focusView.requestFocus();
        }else {
            accountViewModel.verifyPasswordCode(sessionHelper.getUserLanguageCode(),code);
        }
    }

    public void changePassword(View view) {
        String password = binding.newPasswordInput.getText().toString();
        String rePassword = binding.verifyNewPasswordInput.getText().toString();
        boolean cancel = false;
        View focusView = null;
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
            accountViewModel.changePassword(sessionHelper.getUserLanguageCode(),userId,password);
        }
    }

    public void sendCodeAgain(View view) {
        binding.phoneInput.setError(null);
        String phone = binding.phoneInput.getText().toString();
        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(phone)) {
            showErrorToast(getString(R.string.phoneisreq));
            binding.phoneInput.startAnimation(Validator.shakeError());
            focusView = binding.phoneInput;
            cancel = true;
        }
        if(cancel){
            focusView.requestFocus();
        }else {
            accountViewModel.forgetPassword(sessionHelper.getUserLanguageCode(),phone);
        }
    }
}