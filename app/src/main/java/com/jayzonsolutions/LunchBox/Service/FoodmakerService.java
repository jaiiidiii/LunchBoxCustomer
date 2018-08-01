package com.jayzonsolutions.LunchBox.Service;

import com.jayzonsolutions.LunchBox.model.Foodmaker;
import com.jayzonsolutions.LunchBox.model.FoodmakerDishes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodmakerService {

    @GET("foodmaker/foodmakers-list")
    Call<List<Foodmaker>> getFoodmakerList();

    @GET("foodmaker/foodmakers-nearBy-list")
    Call<List<Foodmaker>> getFoodmakerListNearBy1(@Query("miles") Integer miles, @Query("lat") Double lat, @Query("longt") Double longt);

    @GET("foodmaker_dishes/foodmakersdishes-list-byfoodmakerid")
    Call<List<FoodmakerDishes>> getDishesByFoodmakerId(@Query("foodmakerId") Integer foodmakerId);

    @GET("foodmaker/foodmakers-nearBy-list")
    Call<List<Foodmaker>> getFoodmakerListNearBy(@Query("lat") Double lat, @Query("longt") Double longt);

}
