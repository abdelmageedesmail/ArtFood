package com.art4musilm.artfoodCustomer.base;

import android.app.Application;

import com.art4musilm.artfoodCustomer.session.SessionHelper;

import javax.inject.Inject;

import company.tap.gosellapi.GoSellSDK;
import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class BaseApp extends Application {
    @Inject
    SessionHelper sessionHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        GoSellSDK.init(this, "sk_live_fGBrPdweNVyaH9bXA0sUIgOZ", "com.art4musilm.artfoodCustomer");  // to be replaced by merchant, you can contact tap support team to get you credentials

    }
}
