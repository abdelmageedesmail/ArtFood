package com.art4musilm.artfoodCustomer.data;
import android.os.SystemClock;

import com.art4musilm.artfoodCustomer.BuildConfig;
import com.art4musilm.artfoodCustomer.commons.Connectivity;
import com.art4musilm.artfoodCustomer.session.SessionHelper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class GenericInterceptor implements Interceptor {
    public static final String ANDROID = "0";
    SessionHelper sessionHelper;
    Connectivity connectivity;
    enum ErrorType {
        Unauthorized,
        ServiceUnavailable,
        ServiceError
    }
    @Inject
    public GenericInterceptor(SessionHelper sessionHelper,Connectivity connectivity) {
        this.sessionHelper=sessionHelper;
        this.connectivity=connectivity;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        // Delay Requests with development
        SystemClock.sleep(BuildConfig.DELAY);
        return addRequestHeaders(chain);
    }

    private Response addRequestHeaders(Chain chain) throws IOException {
        Request request=null;
        try {
            if (sessionHelper.isLogin()) {
                request = chain.request().newBuilder()
//                        .addHeader("LanguageCode", sessionHelper.getUserLanguageCode())
//                        .addHeader("RegistrationToken", sessionHelper.getPushNotificationToken())
//                        .addHeader("OSType", ANDROID)
//                        .addHeader("OSVersion", String.valueOf(Build.VERSION.RELEASE))
//                        .addHeader("ConnectionType",connectivity.getNetworkConnectionType())
//                        .addHeader("AppVersion", BuildConfig.VERSION_NAME)
//                        .addHeader("DeviceType",Build.BRAND+" "+ Build.MODEL)
                        .addHeader("Accept", "*/*")
                        .build();
            } else {
                request = chain.request().newBuilder()
//                        .addHeader("LanguageCode", sessionHelper.getUserLanguageCode())
//                        .addHeader("RegistrationToken",  sessionHelper.getPushNotificationToken())
//                        .addHeader("OSType", ANDROID)
//                        .addHeader("OSVersion", String.valueOf(Build.VERSION.RELEASE))
//                        .addHeader("ConnectionType", connectivity.getNetworkConnectionType())
//                        .addHeader("AppVersion", BuildConfig.VERSION_NAME)
//                        .addHeader("DeviceType",Build.BRAND+" "+Build.MODEL)
                        .addHeader("Accept", "*/*")
                        .build();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return chain.proceed(request);
    }

}
