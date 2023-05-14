package com.art4musilm.artfoodCustomer.repositories;

import com.art4musilm.artfoodCustomer.data.ApiService;
import com.art4musilm.artfoodCustomer.models.Result;
import com.art4musilm.artfoodCustomer.models.requests.LoginRequest;
import com.art4musilm.artfoodCustomer.models.requests.SignUpRequest;
import com.art4musilm.artfoodCustomer.models.requests.UpdateProfileRequest;
import com.art4musilm.artfoodCustomer.models.response.ForgetPassStep1Res;

import com.art4musilm.artfoodCustomer.models.response.ProfileResponse;
import com.art4musilm.artfoodCustomer.models.response.UpdateProfileResponse;
import com.art4musilm.artfoodCustomer.models.response.VerifyNewPasswordCode;
import com.art4musilm.artfoodCustomer.session.UserSession;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AccountRepository {
    private ApiService apiService;

    @Inject
    public AccountRepository(ApiService apiService) {
        this.apiService = apiService;
    }


    //SignUp
    public Observable<Result> signUp(SignUpRequest signUpRequest) {
        return apiService.signUp(
                signUpRequest.getLang(),
                signUpRequest.getName(),
                signUpRequest.getEmailAddress(),
                signUpRequest.getMobile(),
                signUpRequest.getPassword());
    }

    //Login
    public Observable<UserSession> login(LoginRequest loginRequest) {
        return apiService.login(
                loginRequest.getLang(),
                loginRequest.getPhone(),
                loginRequest.getPassword(),
                loginRequest.getToken(),
                "2"
        );
    }

    //Forget password
    public Observable<ForgetPassStep1Res> forgetPassword(String lang, String mobile) {
        return apiService.forgetPassword(lang, mobile);
    }

    //Check password sent code
    public Observable<VerifyNewPasswordCode> checkPasswordSentCode(String lang, String code) {
        return apiService.verifyPasswordResetCode(lang, code);
    }

    //Change password
    public Observable<Result> changePassword(String lang, String id, String password) {
        return apiService.changePassword(password, id, lang);
    }

    //Change old password
    public Observable<Result> changeOldPassword(String lang, String id, String oldPassword, String newPassword) {
        return apiService.changeOldPassword(id, oldPassword, newPassword, lang);
    }

    //Get User Profile
    public Observable<ProfileResponse> getUserProfile(int id) {
        return apiService.getUserProfile(id);
    }

    //Update User Profile
    public Observable<UpdateProfileResponse> updateUserProfile(int id, String lang, MultipartBody.Part image, UpdateProfileRequest updateProfileRequest) {
        HashMap<String, RequestBody> req = new HashMap<String, RequestBody>();
        req.put("id", RequestBody.create(MediaType.parse("text/plain"), id + ""));
        req.put("lang", RequestBody.create(MediaType.parse("text/plain"), lang));
        req.put("name", RequestBody.create(MediaType.parse("text/plain"), updateProfileRequest.getName()));
        req.put("mobile", RequestBody.create(MediaType.parse("text/plain"), updateProfileRequest.getMobile()));
        req.put("email", RequestBody.create(MediaType.parse("text/plain"), updateProfileRequest.getEmail()));
        req.put("lat", RequestBody.create(MediaType.parse("text/plain"), updateProfileRequest.getLat() + ""));
        req.put("lng", RequestBody.create(MediaType.parse("text/plain"), updateProfileRequest.getLng() + ""));

        return apiService.updateUserProfile(image, req);
    }
}
