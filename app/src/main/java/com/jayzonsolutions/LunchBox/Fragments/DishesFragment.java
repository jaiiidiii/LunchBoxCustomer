package com.jayzonsolutions.LunchBox.Fragments;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import com.jayzonsolutions.LunchBox.R;
import com.jayzonsolutions.LunchBox.Service.APIService;
import com.jayzonsolutions.LunchBox.Service.ApiClient;
import com.jayzonsolutions.LunchBox.Service.ApiInterface;
import com.jayzonsolutions.LunchBox.Service.FoodmakerService;
import com.jayzonsolutions.LunchBox.main;
import com.jayzonsolutions.LunchBox.model.Foodmaker;
import com.jayzonsolutions.LunchBox.model.Movie;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DishesFragment extends Fragment {
   // List<Movie> movieList;
    List<Foodmaker> movieList;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    Context context = getContext();
    private FoodmakerService foodmakerService;
    private APIService mAPIService;



    public DishesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dishes, container, false);

        movieList = new ArrayList<>();
        recyclerView = v.findViewById(R.id.recyclerview);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerAdapter = new RecyclerAdapter(getContext(), movieList);
        recyclerView.setAdapter(recyclerAdapter);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        /*Call<List<Movie>> call = apiService.getMovies();

        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(@NonNull Call<List<Movie>> call, @NonNull Response<List<Movie>> response) {
                movieList = response.body();
                Log.d("TAG", "Response = " + movieList);
                recyclerAdapter.setMovieList(movieList);
            }

            @Override
            public void onFailure(@NonNull Call<List<Movie>> call, @NonNull Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
            }
        });*/



        mAPIService = ApiUtils.getAPIService();
        foodmakerService = ApiUtils.getFoodmakerService();

        foodmakerService.getFoodmakerList().enqueue(new Callback<List<Foodmaker>>() {
            @Override
            public void onResponse(@NonNull Call<List<Foodmaker>> call, @NonNull Response<List<Foodmaker>> response) {
              /*  Toast.makeText(main.this, "success" + response.body().toString(), Toast.LENGTH_LONG).show();
                for (Foodmaker foodmaker : response.body()) {
                    System.out.println(foodmaker.getFoodmakerName());


                }
*/
                movieList = response.body();
                Log.d("TAG", "Response = " + movieList);
                recyclerAdapter.setMovieList(movieList);

            }

            @Override
            public void onFailure(@NonNull Call<List<Foodmaker>> call, @NonNull Throwable t) {
                Toast.makeText(context, "failed ", Toast.LENGTH_LONG).show();

            }
        });





        return v;
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
