package com.jayzonsolutions.LunchBox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jayzonsolutions.LunchBox.Service.APIService;
import com.jayzonsolutions.LunchBox.Service.FoodmakerService;
import com.jayzonsolutions.LunchBox.app.Config;

import customfonts.MyEditText;
import customfonts.MyTextView;


public class signin extends AppCompatActivity {

    ImageView sback;
    MyTextView login;
    MyTextView getFoodmakerList;
    String DeviceID;
    private APIService mAPIService;
    private FoodmakerService foodmakerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        sback = findViewById(R.id.sinb);
        login = findViewById(R.id.sin);
     //   getFoodmakerList = findViewById(R.id.getFoodmakerList);

        mAPIService = ApiUtils.getAPIService();

        foodmakerService = ApiUtils.getFoodmakerService();
        displayFirebaseRegId();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            /*    //  Toast.makeText(signin.this,"clicked",Toast.LENGTH_LONG).show();
                //api call
                if (validate()) {
                    mAPIService.savePost("sohail@gmail.com", "123456").enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {

                            Intent intent = new Intent(signin.this, customerActivity.class);
                            startActivity(intent);
                            Toast.makeText(signin.this, "success" + response.body().getStatus(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                            Toast.makeText(signin.this, "failed ", Toast.LENGTH_LONG).show();

                        }
                    });
                }
                //api call end*/

                Intent intent = new Intent(signin.this, customerActivity.class);
                startActivity(intent);


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
        MyEditText name = findViewById(R.id.usrusr);
        MyEditText pass = findViewById(R.id.pswrd);

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
            Toast.makeText(this, "Firebase Reg Id: " + regId, Toast.LENGTH_SHORT).show();
            DeviceID = regId;
        } else
            Toast.makeText(this, "Firebase Reg Id is not received yet!", Toast.LENGTH_SHORT).show();
    }

}
