package com.art4musilm.artfoodCustomer.di;


import com.art4musilm.artfoodCustomer.BuildConfig;
import com.art4musilm.artfoodCustomer.data.ApiService;
import com.art4musilm.artfoodCustomer.data.GenericInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.art4musilm.artfoodCustomer.commons.keys.API_URL;

@Module
@InstallIn(ApplicationComponent.class)
public class RetrofitModule {


    @Provides
    @Singleton
    public static ApiService provideApiService(
            HttpLoggingInterceptor logging,
            GenericInterceptor genericInterceptor) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(100, TimeUnit.SECONDS);
        httpClient.readTimeout(100, TimeUnit.SECONDS);
        httpClient.addInterceptor(genericInterceptor);
        // Show Network logging
        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(logging);
        }
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient client = httpClient.build();
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(ApiService.class);
    }


    @Provides
    HttpLoggingInterceptor providesLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }


}
