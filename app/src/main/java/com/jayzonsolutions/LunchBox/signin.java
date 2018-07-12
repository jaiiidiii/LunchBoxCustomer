package com.jayzonsolutions.LunchBox;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jayzonsolutions.LunchBox.Service.APIService;
import com.jayzonsolutions.LunchBox.Service.FoodmakerService;
import com.jayzonsolutions.LunchBox.model.ApiResponse;

import customfonts.MyEditText;
import customfonts.MyTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class signin extends AppCompatActivity {

    ImageView sback;
    MyTextView login;
    MyTextView getFoodmakerList;
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


}
