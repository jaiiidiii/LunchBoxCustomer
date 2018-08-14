package com.jayzonsolutions.LunchBox.Service;

import com.jayzonsolutions.LunchBox.model.ApiResponse;
import com.jayzonsolutions.LunchBox.model.Customer;
import com.jayzonsolutions.LunchBox.model.Order;

import java.util.List;
import java.util.Observable;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface CustomerService {

    @POST("customer/login")
    @FormUrlEncoded
    Call<Customer> customerLogin(@Field("customerEmail") String customerEmail, @Field("customerPassword") String customerPassword);

    @POST("customer/signup")
    Call<ApiResponse> customerSignup(@Body Customer customer);

    @Multipart
    @POST("customer/upload-img")
    Call<ResponseBody> uploadUserImage(@Query("id") Integer id,
                                             @Part MultipartBody.Part file);
    @Multipart

    @POST("customer/upload-img-test")
    Call<ResponseBody> uploadUserImageTest(@Part MultipartBody.Part file);


}
