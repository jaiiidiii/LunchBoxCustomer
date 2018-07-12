package com.jayzonsolutions.LunchBox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class main extends AppCompatActivity {
    TextView sin;
    LinearLayout circle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circle = findViewById(R.id.circle);
        sin = findViewById(R.id.sin);


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


    }
}
