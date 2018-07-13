package com.jayzonsolutions.LunchBox.Service;

import com.google.gson.JsonArray;
import com.jayzonsolutions.LunchBox.model.ApiResponse;
import com.jayzonsolutions.LunchBox.model.Foodmaker;
import com.jayzonsolutions.LunchBox.model.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface OrderService {


    @GET("order/get-order-list")
    Call<List<Order>> getOrderList();

    @GET("order/get-order-list")
    Call<JsonArray> getOrderListTest();

    @POST("order/save-order")
    Call<ApiResponse> placeOrder(@Body Order order);
}
