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
import retrofit2.http.Query;

public interface OrderService {


    @GET("order/get-order-list")
    Call<List<Order>> getOrderList();

    @GET("order/get-order-list")
    Call<JsonArray> getOrderListTest();

    @POST("order/save-order")
    Call<ApiResponse> placeOrder(@Body Order order);

    @GET("customer/get-orderByCustomerId")
    Call<List<Order>> getOrdersBycustomerId(@Query("customerId") Integer customerId);

    @GET("customer/get-ack-orderByCustomerId")
    Call<List<Order>> getAckOrdersBycustomerId(@Query("customerId") Integer customerId);

    @GET("customer/get-done-orderByCustomerId")
    Call<List<Order>> getDoneOrdersBycustomerId(@Query("customerId") Integer customerId);

    @GET("order/update-order-status")
    Call<Void> updateOrderStatus(@Query("orderStatus") Integer orderStatus,@Query("orderId") Integer orderId);

    @GET("order/update-order-rating")
    Call<Void> updateOrderRating(@Query("orderRating") Integer orderRating,@Query("orderId") Integer orderId);

    @GET("order/get-order")
    Call<Order> getOrderByOrderId(@Query("id") Integer orderId);
}
