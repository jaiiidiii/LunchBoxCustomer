package com.jayzonsolutions.LunchBox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jayzonsolutions.LunchBox.Service.APIService;
import com.jayzonsolutions.LunchBox.Service.FoodmakerService;
import com.jayzonsolutions.LunchBox.app.Config;
import com.jayzonsolutions.LunchBox.model.Customer;

import customfonts.MyEditText;
import customfonts.MyTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class signin extends AppCompatActivity {

    ImageView sback;
    MyTextView login;
    MyTextView getFoodmakerList;
    String DeviceID = "";
    private APIService mAPIService;
    private FoodmakerService foodmakerService;


    //
    MyEditText useremail;
    MyEditText userpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        sback = findViewById(R.id.sinb);
        login = findViewById(R.id.sin);
        //   getFoodmakerList = findViewById(R.id.getFoodmakerList);
        useremail = findViewById(R.id.useremail);
        userpass = findViewById(R.id.userpass);

        mAPIService = ApiUtils.getAPIService();

        foodmakerService = ApiUtils.getFoodmakerService();
        displayFirebaseRegId();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  Toast.makeText(signin.this,"clicked",Toast.LENGTH_LONG).show();
                //api call
                if (validate()) {

/*
                if(useremail.getText().toString()=="" ||userpass.getText().toString() == ""){
                    Toast.makeText(signin.this, "Fields are required can't be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(signin.this, customerActivity.class);
*/

                    //mAPIService.savePost("sohail@gmail.com", "123456",DeviceID).enqueue(new Callback<Customer>() {

                    mAPIService.savePost(useremail.getText().toString(), userpass.getText().toString(),DeviceID).enqueue(new Callback<Customer>() {
                        @Override
                        public void onResponse(@NonNull Call<Customer> call, @NonNull Response<Customer> response) {
                            if(response.body() == null){
                                Toast.makeText(signin.this, "Email Address or Password is incorrect", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(signin.this, "Successfully Logged in", Toast.LENGTH_LONG).show();
                                Constant.customer = response.body();
                                Intent intent = new Intent(signin.this, customerActivity.class);
                                startActivity(intent);
                            }



                            //
                        }
                        @Override
                        public void onFailure(@NonNull Call<Customer> call, @NonNull Throwable t) {
                            Toast.makeText(signin.this, "Connection Problem ", Toast.LENGTH_LONG).show();
                        }
                    });


                }
                //api call end

               /* Intent intent = new Intent(signin.this, customerActivity.class);
                startActivity(intent);*/


            }
        });


        sback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(signin.this, main.class);
                startActivity(it);
            }
        });
    }

    public boolean validate() {
        MyEditText name = findViewById(R.id.useremail);
        MyEditText pass = findViewById(R.id.userpass);

        if (name.getText().toString().length() == 0 && pass.getText().toString().length() == 0) {
            name.setError("Email is required!");
            pass.setError("Password is required!");
            return false;
        } else if (name.getText().toString().length() == 0 && pass.getText().toString().length() != 0) {
            name.setError("Email is required!");
            return false;
        } else if (pass.getText().toString().length() == 0 && name.getText().toString().length() != 0) {
            pass.setError("Password is required!");
            return false;
        }
        return true;
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e("DeviceID", "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)) {
            //Toast.makeText(this, "Firebase Reg Id: " + regId, Toast.LENGTH_SHORT).show();
            DeviceID = regId;
        } else{}
        //Toast.makeText(this, "Firebase Reg Id is not received yet!", Toast.LENGTH_SHORT).show();
    }

}