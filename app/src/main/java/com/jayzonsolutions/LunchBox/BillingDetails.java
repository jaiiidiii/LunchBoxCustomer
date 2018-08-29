package com.jayzonsolutions.LunchBox;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jayzonsolutions.LunchBox.Service.OrderService;
import com.jayzonsolutions.LunchBox.model.Order;
import com.jayzonsolutions.LunchBox.model.OrderDish;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillingDetails extends AppCompatActivity {


    OrderService orderService;
    customfonts.Medium_Textview totalPriceTxt;
    customfonts.Regular_Textview billingAddress;
    customfonts.Regular_Textview orderDateAndTime;
    customfonts.Regular_Textview customerName;
    customfonts.Regular_Textview orderStatusTxt;
    customfonts.Regular_Textview foodmakerNameTxt;
    customfonts.Regular_Textview finalPrice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        orderService = ApiUtils.getOrderService();
        orderService.getOrderByOrderId(1).enqueue(new Callback<Order>() {

            @Override
            public void onResponse(@NonNull Call<Order> call, @NonNull Response<Order> response) {
                if(response.body() == null){
                    return;
                }
                Order order = response.body();
                totalPriceTxt = findViewById(R.id.totalPrice);
                billingAddress = findViewById(R.id.billingAddress);
                orderDateAndTime = findViewById(R.id.orderDateAndTime);
                customerName = findViewById(R.id.customerName);
                orderStatusTxt= findViewById(R.id.orderStatusTxt);
                foodmakerNameTxt = findViewById(R.id.foodmakerName);
                finalPrice = findViewById(R.id.finalPrice);

                ListView lv = findViewById(R.id.listView1);

                Double totalPrice = order.getOrderTotalAmount();
                String deliveryAddress = order.getOrderShipmentAddress();
                String dateTime = order.getOrderDate();
                String customername = order.getCustomer().getCustomerName();

                String orderStatusDis = "Not Aviable";
                if(order.getOrderStatus() == 1){
                     orderStatusDis = "Order Request Pending";
                }else if(order.getOrderStatus() == 2){
                     orderStatusDis = "Order Request Accepted";
                }else if(order.getOrderStatus() == 3){
                     orderStatusDis = "Order Deliver";
                }else if(order.getOrderStatus() == 4){
                     orderStatusDis = "Order Canceled";
                }else if(order.getOrderStatus() == 5){
                     orderStatusDis = "Order is no the way";
                }

                String foodmakerName = order.getFoodmaker().getFoodmakerName();

                List<OrderDish> orderDishList = new ArrayList<>(order.getOrderdishes());
                String dishes[] = new String[orderDishList.size()];

                for(int i=0;i<orderDishList.size();i++)
                {
                    dishes[i] = "item " +i+ " : " +orderDishList.get(i).getDishes().getName() + "  (" + orderDishList.get(i).getQuantity()
                            + " * " + orderDishList.get(i).getDishes().getPrice()+")";
                }

                totalPriceTxt.setText(""+totalPrice);
                billingAddress.setText(""+deliveryAddress);
                orderDateAndTime.setText(""+dateTime);
                customerName.setText(""+customername);
                orderStatusTxt.setText(""+orderStatusDis);
                foodmakerNameTxt.setText(""+foodmakerName);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(BillingDetails.this,R.layout.list_item,R.id.text1,dishes);
                lv.setAdapter(adapter);
                finalPrice.setText("Final Price : "+totalPrice);
            }

            @Override
            public void onFailure(@NonNull Call<Order> call, @NonNull Throwable t) {

            }
        });






    }

}
