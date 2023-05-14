package com.art4musilm.artfoodCustomer.viewModels;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.art4musilm.artfoodCustomer.base.BaseViewModel;
import com.art4musilm.artfoodCustomer.data.CustomRxObserver;
import com.art4musilm.artfoodCustomer.models.Result;
import com.art4musilm.artfoodCustomer.models.requests.LoginRequest;
import com.art4musilm.artfoodCustomer.models.requests.SignUpRequest;


import com.art4musilm.artfoodCustomer.models.requests.UpdateProfileRequest;
import com.art4musilm.artfoodCustomer.models.response.ForgetPassStep1Res;

import com.art4musilm.artfoodCustomer.models.response.ProfileResponse;
import com.art4musilm.artfoodCustomer.models.response.UpdateProfileResponse;
import com.art4musilm.artfoodCustomer.models.response.VerifyNewPasswordCode;
import com.art4musilm.artfoodCustomer.repositories.AccountRepository;
import com.art4musilm.artfoodCustomer.session.UserSession;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class AccountViewModel extends BaseViewModel {
    private AccountRepository accountRepository;
    private MutableLiveData<Result> signUpResLiveData = new MutableLiveData<>();
    private MutableLiveData<UserSession> loginLiveData = new MutableLiveData<>();
    private MutableLiveData<ProfileResponse> userProfileLiveData = new MutableLiveData<>();
    private MutableLiveData<UpdateProfileResponse> updateProfileLiveData = new MutableLiveData<>();
    private MutableLiveData<ForgetPassStep1Res> forgetPasswordLiveData = new MutableLiveData<>();
    private MutableLiveData<VerifyNewPasswordCode> verifyPasswordLiveData = new MutableLiveData<>();

    private MutableLiveData<Result> changingPasswordLiveData = new MutableLiveData<>();

    private MutableLiveData<Result> changeOldPasswordLiveData = new MutableLiveData<>();

    public LiveData<Result> observeSignUp(){
        return signUpResLiveData;
    }

    public LiveData<UserSession> observeLogin(){
        return loginLiveData;
    }

    public LiveData<ForgetPassStep1Res> observeForgetPassword(){return forgetPasswordLiveData;}

    public LiveData<VerifyNewPasswordCode> observeVerifyingPasswordCode (){
        return verifyPasswordLiveData;
    }

    public LiveData<Result> observeChangingPassword (){
        return changingPasswordLiveData;
    }

    public LiveData<Result> observeChangingOldPassword(){return changeOldPasswordLiveData;}

    public LiveData<ProfileResponse> observeUserProfile(){
        return userProfileLiveData;
    }

    public LiveData<UpdateProfileResponse> observeUpdatingUserProfile(){
        return updateProfileLiveData;
    }

    @ViewModelInject
    public AccountViewModel(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    //Sign Up
    public void signUp(SignUpRequest signUpRequest){
        loading.postValue(true);
        accountRepository.signUp(signUpRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        signUpResLiveData.postValue((Result) response);
                    }
                });
    }


    //Login
    public void login(LoginRequest loginRequest){
        loading.postValue(true);
        accountRepository.login(loginRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        loginLiveData.postValue((UserSession) response);
                    }
                });
    }

    //Forget Password
    public void forgetPassword(String lang,String mobile){
        loading.postValue(true);
        accountRepository.forgetPassword(lang,mobile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        forgetPasswordLiveData.postValue((ForgetPassStep1Res) response);
                    }
                });
    }


    //Verify Password code
    public void verifyPasswordCode(String lang,String code){
        loading.postValue(true);
        accountRepository.checkPasswordSentCode(lang,code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        verifyPasswordLiveData.postValue((VerifyNewPasswordCode) response);
                    }
                });
    }


    //Change password
    public void changePassword(String lang,String id,String password){
        loading.postValue(true);
        accountRepository.changePassword(lang,id,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        changingPasswordLiveData.postValue((Result) response);
                    }
                });
    }


    //Change old password
    public void changeOldPassword(String lang,String id,String oldPass,String newPass){
        loading.postValue(true);
        accountRepository.changeOldPassword(lang,id,oldPass,newPass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        changeOldPasswordLiveData.postValue((Result) response);
                    }
                });
    }


    //Get user profile
    public void getUserProfile(int id){
        loading.postValue(true);
        accountRepository.getUserProfile(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        userProfileLiveData.postValue((ProfileResponse) response);
                    }
                });
    }

    //Update user profile
    public void updateUserProfile(int id, String lang, MultipartBody.Part img, UpdateProfileRequest updateProfileRequest){
        loading.postValue(true);
        accountRepository.updateUserProfile(id,lang,img,updateProfileRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new CustomRxObserver(this) {
                    @Override
                    public void onResponse(Object response) {
                        updateProfileLiveData.postValue((UpdateProfileResponse) response);
                    }
                });
    }


}
