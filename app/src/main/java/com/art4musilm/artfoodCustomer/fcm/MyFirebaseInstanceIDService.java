package com.art4musilm.artfoodCustomer.fcm;

import android.util.Log;

import com.art4musilm.artfoodCustomer.session.SessionHelper;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Inject
    public SessionHelper sessionHelper;
    public static String refreshedToken;
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);
    }

    private void storeRegIdInPref(String token) {
        Log.e(TAG, "sendRegistrationToServer: " + token);
        sessionHelper.savePushNotificationToken(token);
    }
}