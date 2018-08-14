package com.jayzonsolutions.LunchBox;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.jayzonsolutions.LunchBox.Fragments.ChatFragment;
import com.jayzonsolutions.LunchBox.Fragments.MessageFragment;
import com.jayzonsolutions.LunchBox.Fragments.ProfileFragment;
import com.jayzonsolutions.LunchBox.Service.CustomerService;
import com.jayzonsolutions.LunchBox.model.Customer;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class customerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    ToggleButton toggleButton;
    private static int RESULT_LOAD_IMAGE = 1;
    ImageView selectImage;
    CustomerService customerService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        drawer = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
     //  selectImage = (ImageView)findViewById(R.id.select_img);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
       View  headerLayout = navigationView.getHeaderView(0);
               /* navigationView.inflateHeaderView(R.layout.nav_header);*/
        selectImage = headerLayout.findViewById(R.id.select_img);






       /* String[] permissions = {"android.permission.READ_EXTERNAL_STORAGE"};
        ActivityCompat.requestPermissions(this, permissions, 1); // without sdk version check
*/

        //   selectImage.setImageBitmap(BitmapFactory.decodeFile("/storage/emulated/0/DCIM/Camera/IMG_20180812_172935709.jpg"));
        /*File imgFile = new  File("/storage/emulated/0/DCIM/Camera/IMG_20180812_172935709.jpg");

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());



            selectImage.setImageBitmap(myBitmap);

        }
*/

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

        /***
         * image working
         */

        selectImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        /***
         * image working end
         */


        /**
         * toggle button working
         * start*/
       /*toggleButton = (ToggleButton) findViewById(R.id.update_user_status);
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
                /*Intent in = new Intent(customerActivity.this,PlaceOrderActivity.class);
                startActivity(in);*/
                break;
            case R.id.nav_profile:
                /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();*/
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            String[] permissions = {"android.permission.READ_EXTERNAL_STORAGE"};
            ActivityCompat.requestPermissions(this, permissions, 1); // without sdk version check
            if(isPermissionGranted()){
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = this.getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                String picturePath = null;
                if( cursor == null){
                    picturePath =  selectedImage.getPath();
                }else{
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();
                }


                ImageView imageView = (ImageView) findViewById(R.id.select_img);
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                customerService = ApiUtils.getCustomerService();
                File file = new File(picturePath);
               /* RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file);
*/
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file);

                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", file.getName(), requestFile);


                if(file.exists()){
                    customerService.uploadUserImage(1,body).enqueue(new Callback<ResponseBody>() {
                        // mAPIService.savePost(useremail.getText().toString(), userpass.getText().toString(),DeviceID).enqueue(new Callback<Customer>() {
                        @Override
                        public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                            if(response.body() == null){
                            }else{
                            }



                            //
                        }
                        @Override
                        public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

                        }
                    });
                }



            }



        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // save file

                /*File imgFile = new  File("/storage/emulated/0/DCIM/Camera/IMG_20180812_172935709.jpg");

                if(imgFile.exists()){

                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                   selectImage.setImageBitmap(myBitmap);

                }*/

            } else {
                Toast.makeText(getApplicationContext(), "PERMISSION_DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean isPermissionGranted() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE")  == PackageManager.PERMISSION_GRANTED){
                Log.v("permission", "Permission is granted");
                return true;
            }


        }
        return false;
    }
}
