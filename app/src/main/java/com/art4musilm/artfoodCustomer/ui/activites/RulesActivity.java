package com.art4musilm.artfoodCustomer.ui.activites;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.base.BaseActivity;
import com.art4musilm.artfoodCustomer.databinding.ActivityRulesBinding;
import com.art4musilm.artfoodCustomer.models.response.InfoResponse;
import com.art4musilm.artfoodCustomer.viewModels.AppInfoViewModel;

public class RulesActivity extends BaseActivity {
    ActivityRulesBinding binding;
    AppInfoViewModel appInfoViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpUi();
        setUpObservers();
    }

    private void setUpObservers() {
        appInfoViewModel = new ViewModelProvider(this).get(AppInfoViewModel.class);
        binding.setViewModel(appInfoViewModel);
        binding.setLifecycleOwner(this);
        appInfoViewModel.observeAppInfo().observe(this, new Observer<InfoResponse>() {
            @Override
            public void onChanged(InfoResponse infoResponse) {
                if(infoResponse.getStatus()) {
                    if(infoResponse.getData()!=null)
                    binding.toolbar.tittle.setText(infoResponse.getData().getName());
                }else {
                    showSuccessToast(infoResponse.getMessage());
                }
            }
        });
        if(getIntent().getStringExtra("target").equals("about")){
            appInfoViewModel.getAppInfo(sessionHelper.isArabic(this));
        }else {
            appInfoViewModel.getPolicy(sessionHelper.isArabic(this));
        }

    }

    private void setUpUi() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_rules);
        binding.toolbar.backBtn.setVisibility(View.VISIBLE);
        binding.toolbar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}