package com.jayzonsolutions.LunchBox;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.SettingInjectorService;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.jayzonsolutions.LunchBox.Service.OrderService;
import com.jayzonsolutions.LunchBox.model.ApiResponse;
import com.jayzonsolutions.LunchBox.model.Cart;
import com.jayzonsolutions.LunchBox.model.CartItem;
import com.jayzonsolutions.LunchBox.model.Order;
import com.jayzonsolutions.LunchBox.model.OrderDish;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceOrderActivity extends AppCompatActivity implements
        View.OnClickListener,  GoogleApiClient.OnConnectionFailedListener  {

    TextView btnPlacePicker, btnDatePicker, btnTimePicker, txtDate, txtTime,btnPlaceOrder,placeDetails;;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private GoogleApiClient mGoogleApiClient;
    private int PLACE_PICKER_REQUEST = 1;
    private TextView tvPlaceDetails;
    private OrderService orderService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeorder);

        btnPlacePicker= findViewById(R.id.place_pick);
        btnDatePicker= findViewById(R.id.btn_date);
        btnTimePicker= findViewById(R.id.btn_time);
        placeDetails = findViewById(R.id.placeDetails);


        btnPlaceOrder = findViewById(R.id.btn_placeOrder);

        txtDate= findViewById(R.id.in_date);
        txtTime= findViewById(R.id.in_time);

        btnPlacePicker.setOnClickListener(this);
        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

        orderService = ApiUtils.getOrderService();


        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * only for testing
                 * start
                 */
                if(Cart.orderdishes.size() == 0){
                    Toast.makeText(PlaceOrderActivity.this,"Your Cart is Empty",Toast.LENGTH_LONG).show();
                    return;
                }

                Double totalPrice = 0.00;
                Set orderdishesKey1 = Cart.orderdishes.keySet();
                Set FoodmakerIdKey1 = Cart.foodmakerdishes.keySet();
                for (Iterator i = FoodmakerIdKey1.iterator(); i.hasNext(); ) {
                    int foodmakerId = (Integer)i.next();
                    HashMap<Integer,CartItem> foodmakerOrderDishes = (HashMap<Integer,CartItem>) Cart.foodmakerdishes.get(foodmakerId);//                    int foodmakerId =  i.next();

                    Log.v("foodmaker order" ,"foodakerid ==> "+ foodmakerId+"\norderdishes => "+foodmakerOrderDishes);
                    Order order = new Order(); //create order instant
                    order.setOrderCustomerId(10);
                    order.setOrderShipmentAddress(placeDetails.getText().toString());
                    order.setOrderDate(txtDate.getText().toString());

                    order.setOrderDeliverDate(txtDate.getText().toString());

                    order.setOrderStatus(1);
                    order.setFoodmakerId(foodmakerId);
                    List<OrderDish> orderDishes = new ArrayList<>();



                    Set foodmakerOrderDishesKey1 = foodmakerOrderDishes.keySet();
                    for (Iterator j = foodmakerOrderDishesKey1.iterator(); j.hasNext(); ) {
                        int orderDishesId = (Integer)j.next();
                        CartItem cartItem = (CartItem) Cart.orderdishes.get(orderDishesId);
                        totalPrice += cartItem.getQuantity()*cartItem.getFoodmakerDishes().getPrice();

                        OrderDish orderDish = new OrderDish();
                        orderDish.setDishId(orderDishesId);
                        orderDish.setQuantity(cartItem.getQuantity());
                        orderDishes.add(orderDish);

                        Log.v("cart item",cartItem.toString());
                    }
                    order.setOrderTotalAmount(totalPrice);
                    order.setOrderdishes(orderDishes);




                    Log.v("order detail ",order.getOrderdishes().toString());


                    orderService.placeOrder(order).enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                            Toast.makeText(PlaceOrderActivity.this,"true json",Toast.LENGTH_LONG).show();

                            //     System.out.println(response.body().toString());



                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {
                            Toast.makeText(PlaceOrderActivity.this,"Successfully placed",Toast.LENGTH_LONG).show();

                        }
                    });

                }

                /***
                 * end
                 */













  /*
                String date = txtDate.getText().toString();
                String time = txtTime.getText().toString();
                String dtStart = "2010-10-15T09:27:37Z";
                Date datenew = null;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                try {
                    datenew = format.parse(dtStart);
                    System.out.println("Dtae ===> "+datenew);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
        Order order = new Order();
        order.setOrderCustomerId(10);
        order.setOrderShipmentAddress("Ceaser Tower");
        order.setOrderDate(datenew);

        order.setOrderDeliverDate(datenew);
        order.setOrderTotalAmount(102);
        order.setOrderStatus(1);
        order.setFoodmakerId(8);
        List<OrderDish> orderDishes = new ArrayList<>();
        OrderDish orderDish = new OrderDish();
        orderDish.setDishId(27);
        orderDish.setQuantity(2.00);

        OrderDish orderDish1 = new OrderDish();
        orderDish.setDishId(23);
        orderDish.setQuantity(5.00);
        orderDishes.add(orderDish1);
        order.setOrderdishes(orderDishes);


        orderService.placeOrder(order).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                Toast.makeText(PlaceOrderActivity.this,"true json",Toast.LENGTH_LONG).show();

           //     System.out.println(response.body().toString());



            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(PlaceOrderActivity.this,"failed json ",Toast.LENGTH_LONG).show();

            }
        });


*/





            }
        });





        initViews();

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                      //      txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            txtDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        if (v == btnPlacePicker)
        {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            try {
                startActivityForResult(builder.build(PlaceOrderActivity.this), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                Toast.makeText(this, "error=" + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    private void initViews() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvPlaceDetails =  findViewById(R.id.placeDetails);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Snackbar.make(btnPlacePicker, connectionResult.getErrorMessage() + "", Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                StringBuilder stBuilder = new StringBuilder();
                String placename = String.format("%s", place.getName());
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);
                String address = String.format("%s", place.getAddress());
            /*    stBuilder.append("Name: ");
                stBuilder.append(placename);
                stBuilder.append("\n");
                stBuilder.append("Latitude: ");
                stBuilder.append(latitude);
                stBuilder.append("\n");
                stBuilder.append("Logitude: ");
                stBuilder.append(longitude);
                stBuilder.append("\n");
                stBuilder.append("Address: ");*/
                stBuilder.append(address);
                tvPlaceDetails.setText(stBuilder.toString());
            }
        }
    }

}
