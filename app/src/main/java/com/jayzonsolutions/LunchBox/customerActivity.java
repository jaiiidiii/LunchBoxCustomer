package com.jayzonsolutions.LunchBox;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jayzonsolutions.LunchBox.Fragments.ChatFragment;
import com.jayzonsolutions.LunchBox.Fragments.MessageFragment;
import com.jayzonsolutions.LunchBox.Fragments.ProfileFragment;

public class customerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    ToggleButton toggleButton;
    public static FragmentManager fragmentManager;
    ImageView selectImage;
    TextView userName;
    ImageView btnCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        drawer = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        //To Show First fragment on creation
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MessageFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_message);
        }

        View headerLayout = navigationView.getHeaderView(0);
        /* navigationView.inflateHeaderView(R.layout.nav_header);*/
        selectImage = headerLayout.findViewById(R.id.select_img);
        userName = headerLayout.findViewById(R.id.customer_name);
        userName.setText(""+Constant.customer.getCustomerName());

        /***
         * image working
         */
        if(Constant.customer.getCustomerImagePath().length() > 21){
            String imagePath = Constant.customer.getCustomerImagePath();


            Glide.with(this).load(ApiUtils.BASE_URL+(imagePath.substring(21))).
                    apply(RequestOptions.
                            centerCropTransform().fitCenter().
                            diskCacheStrategy(DiskCacheStrategy.ALL)).
                    into(selectImage);
        }

        btnCart = findViewById(R.id.btnCart);
        btnCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(customerActivity.this,PlaceOrderActivity.class);
                startActivity(i);
            }
        });

/*
        selectImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });*/

        /***
         * image working end
         */


        /**
         * toggle button working
         * start
        toggleButton = (ToggleButton) findViewById(R.id.update_user_status);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Toast.makeText(customerActivity.this, "enabled", Toast.LENGTH_SHORT).show();
                } else {
                    // The toggle is disabled
                    Toast.makeText(customerActivity.this, "disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        /**
         * toggle button working
         * end*/
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_message:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MessageFragment()).commit();
                break;
            case R.id.nav_chat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ChatFragment()).commit();
                /*Intent in = new Intent(customerActivity.this,ChatFragment.class);
                startActivity(in);*/
                break;
            case R.id.nav_profile:
                Intent in1 = new Intent(customerActivity.this,UserProfile.class);
                startActivity(in1);
                break;
            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
