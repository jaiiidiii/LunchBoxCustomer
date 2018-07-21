package com.jayzonsolutions.LunchBox.Service;

import com.jayzonsolutions.LunchBox.model.ApiResponse;
import com.jayzonsolutions.LunchBox.model.Customer;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    @POST("customer/login")
    @FormUrlEncoded
    Call<Customer> savePost(@Field("customerEmail") String customerEmail, @Field("customerPassword") String customerPassword
    , @Field("token") String token);
}
