package com.jayzonsolutions.LunchBox;

import com.jayzonsolutions.LunchBox.Service.APIService;
import com.jayzonsolutions.LunchBox.Service.CustomerService;
import com.jayzonsolutions.LunchBox.Service.FoodmakerService;
import com.jayzonsolutions.LunchBox.Service.OrderService;

public class ApiUtils {

    public static final String BASE_URL = "http://192.168.0.110:8080/";
   // public static final String BASE_URL = "https://3fe11e17.ngrok.io/";


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
    public static OrderService getOrderService(){
        return  RetrofitClient.getClient(BASE_URL).create(OrderService.class);
    }
}