package com.art4musilm.artfoodCustomer.ui.activites.account;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.base.BaseActivity;
import com.art4musilm.artfoodCustomer.commons.Validator;
import com.art4musilm.artfoodCustomer.databinding.ActivitySignUpBinding;
import com.art4musilm.artfoodCustomer.models.Result;
import com.art4musilm.artfoodCustomer.models.requests.SignUpRequest;
import com.art4musilm.artfoodCustomer.ui.dialogs.MessageDialog;
import com.art4musilm.artfoodCustomer.viewModels.AccountViewModel;

import static com.art4musilm.artfoodCustomer.commons.Validator.isPasswordValid;

public class SignUpActivity extends BaseActivity {
    ActivitySignUpBinding binding;
    ArrayAdapter customerTypeArrayAdapter;
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
        accountViewModel.observeSignUp().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if(result.getStatus()){
                    new MessageDialog(SignUpActivity.this,result.getMessage(),getString(R.string.youwillredirect)).show();
                    finish();
                }else {
                    showErrorToast(result.getMessage());
                }
            }
        });
    }

    private void setUpUi() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up);
        binding.toolbar.tittle.setText(R.string.signup);
        binding.toolbar.backBtn.setVisibility(View.VISIBLE);
        binding.toolbar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        customerTypeArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        binding.customerTypeSp.setAdapter(customerTypeArrayAdapter);

    }

    public void signUp(View view) {
        binding.nameInput.setError(null);
        binding.emailInput.setError(null);
        binding.phoneInput.setError(null);
        binding.passwordInput.setError(null);
        binding.repasswordInput.setError(null);

       String name = binding.nameInput.getText().toString();
       String email = binding.emailInput.getText().toString();
       String phone = binding.phoneInput.getText().toString();
       String password = binding.passwordInput.getText().toString();
       String rePassword = binding.repasswordInput.getText().toString();
       boolean acceptTerms = binding.termsCheck.isChecked();

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
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            showErrorToast(getString(R.string.invalidpassword));
            binding.passwordInput.startAnimation(Validator.shakeError());
            focusView = binding.passwordInput;
            cancel = true;
            return;
        }

        if (TextUtils.isEmpty(rePassword)) {
            showErrorToast(getString(R.string.invalidpassword));
            binding.repasswordInput.startAnimation(Validator.shakeError());
            focusView = binding.repasswordInput;
            cancel = true;
            return;
        }

        if(!password.equals(rePassword)){
            binding.passwordInput.startAnimation(Validator.shakeError());
            binding.repasswordInput.startAnimation(Validator.shakeError());
            showErrorToast(getString(R.string.passwordsnotmatches));
            focusView = binding.passwordInput;
            cancel = true;
            return;
        }

        if(!acceptTerms){
            binding.termsCheck.startAnimation(Validator.shakeError());
            showErrorToast(getString(R.string.accettermesreq));
            return;
        }

        if(cancel){
            focusView.requestFocus();
        }else {
            SignUpRequest signUpRequest = new SignUpRequest();
            signUpRequest.setLang(sessionHelper.getUserLanguageCode());
            signUpRequest.setName(name);
            signUpRequest.setMobile(phone);
            signUpRequest.setEmailAddress(email);
            signUpRequest.setPassword(password);
            accountViewModel.signUp(signUpRequest);
        }
    }

    public void login(View view) {
        finish();
    }
}