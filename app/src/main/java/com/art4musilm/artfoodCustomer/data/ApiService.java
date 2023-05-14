package com.art4musilm.artfoodCustomer.data;

import com.art4musilm.artfoodCustomer.models.DriverLocationModel;
import com.art4musilm.artfoodCustomer.models.Result;
import com.art4musilm.artfoodCustomer.models.response.CalculatedDeliveryResponse;
import com.art4musilm.artfoodCustomer.models.response.CategoriesResponse;
import com.art4musilm.artfoodCustomer.models.response.FamiliesResponse;
import com.art4musilm.artfoodCustomer.models.response.FamilyCategoriesResponse;
import com.art4musilm.artfoodCustomer.models.response.FamilyDetailsResponse;
import com.art4musilm.artfoodCustomer.models.response.FamilySearchResponse;
import com.art4musilm.artfoodCustomer.models.response.ForgetPassStep1Res;

import com.art4musilm.artfoodCustomer.models.response.InfoResponse;
import com.art4musilm.artfoodCustomer.models.response.InvoiceResponse;
import com.art4musilm.artfoodCustomer.models.response.LocationsResponse;
import com.art4musilm.artfoodCustomer.models.response.OffersResponse;
import com.art4musilm.artfoodCustomer.models.response.OrderResponse;
import com.art4musilm.artfoodCustomer.models.response.ProductDetailsResponse;
import com.art4musilm.artfoodCustomer.models.response.ProductsResponse;
import com.art4musilm.artfoodCustomer.models.response.ProfileResponse;
import com.art4musilm.artfoodCustomer.models.response.SliderResponse;
import com.art4musilm.artfoodCustomer.models.response.UpdateProfileResponse;
import com.art4musilm.artfoodCustomer.models.response.VerifyNewPasswordCode;
import com.art4musilm.artfoodCustomer.session.UserSession;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ApiService {

    //Sign up
    @FormUrlEncoded
    @POST("registeruserapps_client")
    Observable<Result> signUp(
            @Field("lang") String lang,
            @Field("name") String name,
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("password") String password);


    //Login
    @FormUrlEncoded
    @POST("loginuserapps")
    Observable<UserSession> login(
            @Field("lang") String lang,
            @Field("mobile") String name,
            @Field("password") String email,
            @Field("token") String mobile,
            @Field("type") String type);

    //Forget password
    @FormUrlEncoded
    @POST("appsbackpassword")
    Observable<ForgetPassStep1Res> forgetPassword(
            @Field("lang") String lang,
            @Field("mobile") String mobile);

    //Verify Forget password sent code
    @FormUrlEncoded
    @POST("checkpasswordcode")
    Observable<VerifyNewPasswordCode> verifyPasswordResetCode(
            @Field("lang") String lang,
            @Field("code") String code);

    //Change password
    @FormUrlEncoded
    @POST("newchangenewpassword")
    Observable<Result> changePassword(
            @Field("password") String password,
            @Field("id") String id,
            @Field("lang") String lang);

    //Change old password
    @FormUrlEncoded
    @POST("update_userapppassword")
    Observable<Result> changeOldPassword(
            @Field("id") String id,
            @Field("oldpassword") String oldPassword,
            @Field("newpassword") String newPassword,
            @Field("lang") String lang);


    //Get User Profile
    @FormUrlEncoded
    @POST("getuserdata_client")
    Observable<ProfileResponse> getUserProfile(@Field("id") int id);

    //Update User Profile
    @Multipart
    @POST("update_client")
    Observable<UpdateProfileResponse> updateUserProfile(
            @Part MultipartBody.Part img,
            @PartMap Map<String, RequestBody> partMap);


    //Get Categories
    @GET("getSections")
    Observable<CategoriesResponse> getCategories();

    //Get Slider
    @GET("getSlideshow")
    Observable<SliderResponse> getSlider();

    //Get Families
    @FormUrlEncoded
    @POST("getallfamily")
    Observable<FamiliesResponse> getFamilies(@Field("lat") double lat,
                                             @Field("lng") double lng);

    //Get Families By id
    @FormUrlEncoded
    @POST("getallfamilybysection")
    Observable<FamiliesResponse> getFamiliesById(@Field("cat") String catId,
                                                 @Field("lat") double lat,
                                                 @Field("lng") double lng);

    //Get Family Details
    @FormUrlEncoded
    @POST("getuserdata_productivefamily")
    Observable<FamilyDetailsResponse> getFamilyDetails(@Field("id") int id,
                                                       @Field("lat") double lat,
                                                       @Field("lng") double lng);

    //Get Family Categories
    @FormUrlEncoded
    @POST("category_productivefamily")
    Observable<FamilyCategoriesResponse> getFamilyCategories(@Field("id") int id);

    //Get Families categories meals
    @FormUrlEncoded
    @POST("getmeal_bycategory")
    Observable<ProductsResponse> getFamiliesCatsMeals(
            @Field("category") int id,
            @Field("user_id") int userId,
            @Field("lang") String lang);

    //Get Meal details
    @FormUrlEncoded
    @POST("getmeal_details")
    Observable<ProductDetailsResponse> getMealDetails(
            @Field("id_meal") int id,
            @Field("lang") String lang);

    @FormUrlEncoded
    @POST("searchfamily")
    Observable<FamilySearchResponse> getFamilySearch(
            @Field("lang") String lang,
            @Field("searchbyname") String searchbyname);

    //Get Offers
    @GET("getalloffers")
    Observable<OffersResponse> getOffers();

    //Get invoices
    @FormUrlEncoded
    @POST("gettransferbank")
    Observable<InvoiceResponse> getInvoices(@Field("user_id") int id,
                                            @Field("lang") String lang);

    //Add Credit
    @FormUrlEncoded
    @POST("Addcredit")
    Observable<Result> addCredit(
            @Field("user_id") int id,
            @Field("price") String lang);

    //Get Locations
    @FormUrlEncoded
    @POST("getclientaddress")
    Observable<LocationsResponse> getLocations(@Field("user_id") String id,
                                               @Field("lang") String lang);


    //Add location
    @FormUrlEncoded
    @POST("addaddress_client")
    Observable<Result> addLocation(@Field("id") String id,
                                   @Field("lang") String lang,
                                   @Field("address") String address,
                                   @Field("lat") double lat,
                                   @Field("lng") double lng);

    @GET("getaboutappar")
    Observable<InfoResponse> getAboutAr();

    @GET("getaboutappen")
    Observable<InfoResponse> getAboutEn();

    @GET("gettermsconditions")
    Observable<InfoResponse> getPoliceAr();

    @GET("gettermsconditionsen")
    Observable<InfoResponse> getPoliceEn();


    //Add comment
    @FormUrlEncoded
    @POST("submitFeedback")
    Observable<Result> addComment(@Field("name") String name,
                                  @Field("comment") String comment,
                                  @Field("lang") String lang,
                                  @Field("email") String email,
                                  @Field("type") String type,
                                  @Field("mobile") String mobile);


    @FormUrlEncoded
    @POST("submitOrder")
    Observable<Result> checkout(
            @Field("user_id") String userId,
            @Field("totlitems") String totalItems,
            @Field("totlitemsprise") String totlitemsprise,
            @Field("shipping") String shipping,
            @Field("note") String notes,
            @Field("deliverymethod") String deliveryMethod,
            @Field("paymentmethod") String paymentMethod,
            @Field("deliverytime") String deliverytime,
            @Field("deliverydate") String deliverydate,
            @Field("hourfrom") String hourfrom,
            @Field("hourto") String hourto,
            @Field("transaction_id") String transaction_id,
            @Field("useraddress") String useraddress,
            @Field("items[]") List<String> items,
            @Field("lang") String lang);


    //Get Current Orders
    @FormUrlEncoded
    @POST("getcurrentorderuser")
    Observable<OrderResponse> getCurrentOrders(@Field("user_id") String userId);

    //Get Previous Orders
    @FormUrlEncoded
    @POST("getpreviousorderuser")
    Observable<OrderResponse> getPreviousOrders(@Field("user_id") String userId);

    //Cancel Order
    @FormUrlEncoded
    @POST("cancelorder")
    Observable<Result> cancelOrder(@Field("order_id") String orderId,
                                   @Field("lang") String lang);

    //get driver location
    @FormUrlEncoded
    @POST("getclientdriverlocation")
    Observable<DriverLocationModel> getDriverLocation(@Field("order_id") String orderId,
                                                      @Field("driver_id") String driver_id);


    //Set Order delivered
    @FormUrlEncoded
    @POST("connectorderbyuser")
    Observable<Result> setOrderDelivered(@Field("order_id") String orderId,
                                         @Field("lang") String lang);

    //Review order
    @FormUrlEncoded
    @POST("voteorder")
    Observable<Result> reviewOrder(@Field("order_id") String orderId,
                                   @Field("lang") String lang,
                                   @Field("stars") float stars,
                                   @Field("votedetails") String msg);

    @FormUrlEncoded
    @POST("ReSendOrder")
    Observable<Result> resendOrder(@Field("order_id") String orderId);


    @FormUrlEncoded
    @POST("clientcalcatedelivery")
    Observable<CalculatedDeliveryResponse> clientCalcaulatedDeliver(
            @Field("useraddress") String useraddress,
            @Field("family_id") String family_id);
}
