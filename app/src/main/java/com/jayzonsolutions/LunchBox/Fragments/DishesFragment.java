package com.jayzonsolutions.LunchBox.Fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jayzonsolutions.LunchBox.Adaptor.RecyclerAdapter;
import com.jayzonsolutions.LunchBox.ApiUtils;
import com.jayzonsolutions.LunchBox.GlobalVariables;
import com.jayzonsolutions.LunchBox.R;
import com.jayzonsolutions.LunchBox.Service.APIService;
import com.jayzonsolutions.LunchBox.Service.ApiClient;
import com.jayzonsolutions.LunchBox.Service.ApiInterface;
import com.jayzonsolutions.LunchBox.Service.FoodmakerService;
import com.jayzonsolutions.LunchBox.Service.GPSTracker;
import com.jayzonsolutions.LunchBox.model.Foodmaker;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DishesFragment extends Fragment {
    final GlobalVariables g;
   // List<Movie> foodmakerList;
    List<Foodmaker> foodmakerList;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    Context context = getContext();
    GPSTracker gps;
    double latitude;
    double longitude;
    private FoodmakerService foodmakerService;
    private APIService mAPIService;
    //Permision code that will be checked in the method onRequestPermissionsResult
    private int permission_code = 23;


    public DishesFragment() {
        // Required empty public constructor
        g = GlobalVariables.GetInstance();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_dishes, container, false);


        //  final GlobalVariables g = GlobalVariables.GetInstance();
        g.ResetVariables();


        foodmakerList = new ArrayList<>();
        recyclerView = v.findViewById(R.id.recyclerview);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerAdapter = new RecyclerAdapter(getContext(), foodmakerList);
        recyclerView.setAdapter(recyclerAdapter);


        checkPermission();


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        mAPIService = ApiUtils.getAPIService();
        foodmakerService = ApiUtils.getFoodmakerService();


        return v;
    }

    @Override
    public void onResume() {

        super.onResume();
        context = getActivity();

        GPSTracker gpsTracker = new GPSTracker(context);
        foodmakerService.getFoodmakerListNearBy(gpsTracker.getLatitude(),gpsTracker.getLongitude()).enqueue(new Callback<List<Foodmaker>>() {
            @Override
            public void onResponse(@NonNull Call<List<Foodmaker>> call, @NonNull Response<List<Foodmaker>> response) {

                if(foodmakerList.size() <= 0)
                {
                    foodmakerList = response.body();
                    Log.d("TAG", "Response = " + foodmakerList);
                    recyclerAdapter.setMovieList(foodmakerList);
                }
                else
                {
                    Toast.makeText(context, "naaaaa", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Foodmaker>> call, @NonNull Throwable t) {
                Toast.makeText(context, "no foodmaker available in your area", Toast.LENGTH_LONG).show();
            }
        });





        /*foodmakerService.getFoodmakerList().enqueue(new Callback<List<Foodmaker>>() {
            @Override
            public void onResponse(@NonNull Call<List<Foodmaker>> call, @NonNull Response<List<Foodmaker>> response) {
              *//*  Toast.makeText(main.this, "success" + response.body().toString(), Toast.LENGTH_LONG).show();
                for (Foodmaker foodmaker : response.body()) {
                    System.out.println(foodmaker.getFoodmakerName());


                }
*//*
                foodmakerList = response.body();
                Log.d("TAG", "Response = " + foodmakerList);
                recyclerAdapter.setMovieList(foodmakerList);

            }

            @Override
            public void onFailure(@NonNull Call<List<Foodmaker>> call, @NonNull Throwable t) {
                Toast.makeText(context, "failed ", Toast.LENGTH_LONG).show();

            }
        });*/

        // super.onResume();

    }

    private void checkPermission() {
        if (isReadLocationAllowed()) {
            GetLocation();
            //If permission is already having then showing the toast
            //  Toast.makeText(Login.this, "You already have the permission", Toast.LENGTH_LONG).show();
            //Existing the method with return
            return;
        }

        //If the app has not the permission then asking for the permission
        requestPermission();


    }


    //We are calling this method to check the permission status
    private boolean isReadLocationAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    //Requesting permission
    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                //   Manifest.permission.READ_PHONE_STATE,
                //  Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION
        }, permission_code);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == permission_code) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                GetLocation();
                //Displaying a toast
                //   Toast.makeText(this, "Permission granted now you can read the phone state", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void GetLocation() {
        // ===============get location--------------------------
        gps = new GPSTracker(getActivity());

        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            g.setLatitude(latitude);
            g.setLongitude(longitude);

            if (latitude != 0.0 && longitude != 0.0) {

                Toast.makeText(getActivity(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                // Intent myIntent = new Intent(WorkDone.this,
                // CameraPhotoCapture.class);
                // myIntent.putExtra("workdone", Work.toString());
                // myIntent.putExtra("status", status.toString());
                // myIntent.putExtra("long",
                // Double.toString(longitude));
                // myIntent.putExtra("lat", Double.toString(latitude));
                //
                // startActivity(myIntent);
                //				Toast.makeText(getApplicationContext(),
                //						"STATUS " + stat.toString(), Toast.LENGTH_LONG)
                ///						.show();


            } else {
                Toast.makeText(getActivity(),
                        "Unable to get the Location\nPlease Wait...",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            gps.showSettingsAlert();

        }

        // ===============get location--------------------------


    }
    private int dpToPx() {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, r.getDisplayMetrics()));
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}
