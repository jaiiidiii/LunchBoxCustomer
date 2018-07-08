package com.jayzonsolutions.LunchBox;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jayzonsolutions.LunchBox.Service.APIService;
import com.jayzonsolutions.LunchBox.Service.FoodmakerService;
import com.jayzonsolutions.LunchBox.model.Foodmaker;

import java.util.List;

import customfonts.MyTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class main extends AppCompatActivity {
    TextView sin;
    LinearLayout circle;

    MyTextView getFoodmakerList;
    private FoodmakerService foodmakerService;
    private APIService mAPIService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circle = findViewById(R.id.circle);
        sin = findViewById(R.id.sin);


        mAPIService = ApiUtils.getAPIService();

        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(main.this, signup.class);
                startActivity(it);

            }
        });
        sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it = new Intent(main.this, signin.class);
                startActivity(it);


//                Intent it = new Intent(main.this,customerActivity.class);
//                startActivity(it);
            }
        });

        getFoodmakerList = findViewById(R.id.getFoodmakerList);
        foodmakerService = ApiUtils.getFoodmakerService();
        getFoodmakerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(main.this, "process ", Toast.LENGTH_LONG).show();
                foodmakerService.getFoodmakerList().enqueue(new Callback<List<Foodmaker>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Foodmaker>> call, @NonNull Response<List<Foodmaker>> response) {
                        Toast.makeText(main.this, "success" + response.body().toString(), Toast.LENGTH_LONG).show();
                        for (Foodmaker foodmaker : response.body()) {
                            System.out.println(foodmaker.getFoodmakerName());
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Foodmaker>> call, @NonNull Throwable t) {
                        Toast.makeText(main.this, "failed ", Toast.LENGTH_LONG).show();

                    }
                });

            }

        });


    }
}
