package com.jayzonsolutions.LunchBox;

import com.jayzonsolutions.LunchBox.Service.APIService;
import com.jayzonsolutions.LunchBox.Service.CustomerService;
import com.jayzonsolutions.LunchBox.Service.FoodmakerService;

public class ApiUtils {

    // public static final String BASE_URL = "http://192.168.0.106:8080/";
    public static final String BASE_URL = "http://192.168.0.103:8080/";

    private ApiUtils() {
    }

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

     static CustomerService getCustomerService() {
        return RetrofitClient.getClient(BASE_URL).create(CustomerService.class);
    }

     public static FoodmakerService getFoodmakerService() {
        return RetrofitClient.getClient(BASE_URL).create(FoodmakerService.class);
    }
}