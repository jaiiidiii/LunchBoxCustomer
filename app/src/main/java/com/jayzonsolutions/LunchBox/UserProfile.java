package com.jayzonsolutions.LunchBox;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jayzonsolutions.LunchBox.Service.CustomerService;
import com.jayzonsolutions.LunchBox.model.Address;
import com.jayzonsolutions.LunchBox.model.ApiResponse;
import com.jayzonsolutions.LunchBox.model.Customer;

import customfonts.MyEditText;
import customfonts.MyTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfile extends AppCompatActivity {

    MyEditText dispName;
    MyEditText dispEmail;
    MyEditText userPhone;
    MyTextView userRatting;
    MyEditText userAddress;
    MyEditText dispcnic;
    com.alexzh.circleimageview.CircleImageView circleview;
    com.rey.material.widget.Switch switcher;

    FloatingActionButton btnSave;
    FloatingActionButton btnCancel;

    CustomerService customerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        customerService = ApiUtils.getCustomerService();

        switcher =  findViewById(R.id.switcher);

        circleview = findViewById(R.id.circleview);
        /**
         * images
         * */
        //   String imagePath = ((movieList.get(position).getImagepath() != null)?movieList.get(position).getImagepath():"http://localhost:8080/images/biryani.jpg");

        if(Constant.customer.getCustomerImagePath().length() > 21){
            String imagePath = Constant.customer.getCustomerImagePath();


            Glide.with(this).load(ApiUtils.BASE_URL+(imagePath.substring(21))).
                    apply(RequestOptions.
                            centerCropTransform().fitCenter().
                            diskCacheStrategy(DiskCacheStrategy.ALL)).
                    into(circleview);
        }



        int foodmakerStatus = 0;
        /*switcher.setOnCheckedChangeListener(new com.rey.material.widget.Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(com.rey.material.widget.Switch view, boolean checked) {
                if(checked){
                    foodmakerStatus = 1;
                    Toast.makeText(getApplication(), "enabled", Toast.LENGTH_SHORT).show();
                } else {
                    foodmakerStatus = 2;
                    Toast.makeText(getApplication(), "disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });
        */

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                setAllFieldEnable();
            }
        });

        /***
         *
         * */
        dispName = findViewById(R.id.dispName);
        dispEmail  = findViewById(R.id.dispEmail);
        userPhone  = findViewById(R.id.userPhone);
        userRatting  = findViewById(R.id.userRatting);
        userAddress  = findViewById(R.id.userAddress);
        dispcnic =  findViewById(R.id.dispcnic);
        setAllFieldWithDefault();


        /***
         *
         * */
        btnSave = (FloatingActionButton) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Customer customer = Constant.customer;
                customer.setCustomerName(""+dispName.getText().toString());
                customer.setCustomerEmail(""+dispEmail.getText().toString());
                customer.setCustomerPhoneNumber(""+userPhone.getText().toString());
                customer.setCustomerNic(""+dispcnic.getText().toString());

                if(userAddress.getText().toString() == Constant.customer.getAddress().getAddress()){
                    Address address = new Address(""+userAddress.getText().toString(), "  Karachi");
                    customer.setAddress(address);
                }
                //foodmaker.setFoodmakerCreatedAt(null);
               // foodmaker.setFoodmakerLastUpdated(null);



                customerService.customerSignup(customer).enqueue(new Callback<ApiResponse>() {

                    @Override
                    public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                        Toast.makeText(UserProfile.this, "Successfully Update ", Toast.LENGTH_LONG).show();
                        setAllFieldDisable();
                    }

                    @Override
                    public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                        Toast.makeText(UserProfile.this, "Connection Problem ", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnCancel = (FloatingActionButton) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllFieldWithDefault();
            }
        });

        /***
         *
         */

/**
 * */






    }

    public void setAllFieldEnable(){
        dispName = findViewById(R.id.dispName);
        dispEmail  = findViewById(R.id.dispEmail);
        userPhone  = findViewById(R.id.userPhone);
        userRatting  = findViewById(R.id.userRatting);
        userAddress  = findViewById(R.id.userAddress);
        dispcnic =  findViewById(R.id.dispcnic);


        //btn

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        btnSave.setVisibility(View.VISIBLE);
        btnCancel.setVisibility(View.VISIBLE);

        dispEmail.setEnabled(true);
        dispName.setEnabled(true);
        dispEmail.setEnabled(true);
        userPhone.setEnabled(true);
        userRatting.setEnabled(true);
        userAddress.setEnabled(true);
        dispcnic.setEnabled(true);
    }
    public void setAllFieldDisable(){
        dispName = findViewById(R.id.dispName);
        dispEmail  = findViewById(R.id.dispEmail);
        userPhone  = findViewById(R.id.userPhone);
        userRatting  = findViewById(R.id.userRatting);
        userAddress  = findViewById(R.id.userAddress);
        dispcnic =  findViewById(R.id.dispcnic);
        //btn

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        btnSave.setVisibility(View.INVISIBLE);
        btnCancel.setVisibility(View.INVISIBLE);


        dispEmail.setEnabled(false);
        dispName.setEnabled(false);
        dispEmail.setEnabled(false);
        userPhone.setEnabled(false);
        userRatting.setEnabled(false);
        userAddress.setEnabled(false);
        dispcnic.setEnabled(false);
    }
    public void setAllFieldWithDefault(){
        dispName = findViewById(R.id.dispName);
        dispEmail  = findViewById(R.id.dispEmail);
        userPhone  = findViewById(R.id.userPhone);
        userRatting  = findViewById(R.id.userRatting);
        userAddress  = findViewById(R.id.userAddress);
        dispcnic =  findViewById(R.id.dispcnic);
        //btn

        dispName.setText(""+Constant.customer.getCustomerName());
        dispEmail.setText(""+Constant.customer.getCustomerEmail());
        userPhone.setText(""+Constant.customer.getCustomerPhoneNumber());
        userAddress.setText(""+Constant.customer.getCustomerAddressId().getAddress());
        dispcnic.setText(""+Constant.customer.getCustomerNic());
        setAllFieldDisable();
    }





}


