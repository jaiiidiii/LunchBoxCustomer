package com.jayzonsolutions.LunchBox;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import java.util.Calendar;

public class PlaceOrderActivity extends AppCompatActivity implements
        View.OnClickListener,  GoogleApiClient.OnConnectionFailedListener  {

    TextView btnPlacePicker, btnDatePicker, btnTimePicker, txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private GoogleApiClient mGoogleApiClient;
    private int PLACE_PICKER_REQUEST = 1;
    private TextView tvPlaceDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeorder);

        btnPlacePicker= findViewById(R.id.place_pick);
        btnDatePicker= findViewById(R.id.btn_date);
        btnTimePicker= findViewById(R.id.btn_time);
        txtDate= findViewById(R.id.in_date);
        txtTime= findViewById(R.id.in_time);

        btnPlacePicker.setOnClickListener(this);
        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

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
                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
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
                stBuilder.append("Name: ");
                stBuilder.append(placename);
                stBuilder.append("\n");
                stBuilder.append("Latitude: ");
                stBuilder.append(latitude);
                stBuilder.append("\n");
                stBuilder.append("Logitude: ");
                stBuilder.append(longitude);
                stBuilder.append("\n");
                stBuilder.append("Address: ");
                stBuilder.append(address);
                tvPlaceDetails.setText(stBuilder.toString());
            }
        }
    }

}
