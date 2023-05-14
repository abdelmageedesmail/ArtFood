package com.art4musilm.artfoodCustomer.ui.activites;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.base.BaseActivity;
import com.art4musilm.artfoodCustomer.databinding.ActivityIntroBinding;
import com.art4musilm.artfoodCustomer.session.SessionHelper;
import com.art4musilm.artfoodCustomer.ui.activites.account.LoginActivity;

public class introActivity extends BaseActivity {
    ActivityIntroBinding binding;

    @Override
    protected void onStart() {
        super.onStart();
        if(sessionHelper.isLogin()){
            startActivity(new Intent(introActivity.this,MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpUi();
    }

    private void setUpUi() {
        binding= DataBindingUtil.setContentView(this,R.layout.activity_intro);
    }

    public void goEnglish(View view) {
       if(sessionHelper.isEnglish(this)){
           startActivity(new Intent(introActivity.this, LoginActivity.class));
       }else {
           sessionHelper.setLanguageEnglish(new SessionHelper.OnSessionUpdate() {
               @Override
               public void refreshActivity() {
                   startActivity(new Intent(introActivity.this, LoginActivity.class));
               }
           });
       }
    }

    public void goArabic(View view) {
       if(sessionHelper.isArabic(this)){
           startActivity(new Intent(introActivity.this,LoginActivity.class));
       }else {
           sessionHelper.setLanguageArabic(new SessionHelper.OnSessionUpdate() {
               @Override
               public void refreshActivity() {
                   startActivity(new Intent(introActivity.this,LoginActivity.class));
               }
           });
       }
    }
}