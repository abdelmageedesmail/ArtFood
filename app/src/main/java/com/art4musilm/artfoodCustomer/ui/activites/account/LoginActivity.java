package com.art4musilm.artfoodCustomer.ui.activites.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.base.BaseActivity;
import com.art4musilm.artfoodCustomer.commons.Validator;
import com.art4musilm.artfoodCustomer.databinding.ActivityLocationBinding;
import com.art4musilm.artfoodCustomer.databinding.ActivityLoginBinding;
import com.art4musilm.artfoodCustomer.models.requests.LoginRequest;
import com.art4musilm.artfoodCustomer.session.UserSession;
import com.art4musilm.artfoodCustomer.ui.activites.MainActivity;
import com.art4musilm.artfoodCustomer.viewModels.AccountViewModel;

import static com.art4musilm.artfoodCustomer.commons.Validator.isPasswordValid;

public class LoginActivity extends BaseActivity {
    ActivityLoginBinding binding;
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
        accountViewModel.observeLogin().observe(this, new Observer<UserSession>() {
            @Override
            public void onChanged(UserSession userSession) {
                if (userSession.getStatus()) {
                    sessionHelper.setUserSession(userSession);
                    showSuccessToast(userSession.getMessage());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    ActivityCompat.finishAffinity(LoginActivity.this);
                } else {
                    showErrorToast(userSession.getMessage());
                }
            }
        });
    }

    private void setUpUi() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }

    public void goSignUp(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    public void goVisitor(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void goForgetPassword(View view) {
        startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
    }

    public void login(View view) {
        binding.phoneInput.setError(null);
        binding.passwordInput.setError(null);
        String phone = binding.phoneInput.getText().toString();
        String password = binding.passwordInput.getText().toString();
        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(phone)) {
            showErrorToast(getString(R.string.phoneisreq));
            binding.phoneInput.startAnimation(Validator.shakeError());
            focusView = binding.phoneInput;
            cancel = true;
            return;
        }

        if (!Validator.isValidPhoneNumber(phone)) {
            showErrorToast(getString(R.string.invalid_phone_num));
            binding.phoneInput.startAnimation(Validator.shakeError());
            focusView = binding.phoneInput;
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

        if (cancel) {
            focusView.requestFocus();
        } else {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setLang(sessionHelper.getUserLanguageCode());
            loginRequest.setPhone(phone);
            loginRequest.setPassword(password);
            loginRequest.setToken(sessionHelper.getPushNotificationToken());
            accountViewModel.login(loginRequest);
        }
    }
}