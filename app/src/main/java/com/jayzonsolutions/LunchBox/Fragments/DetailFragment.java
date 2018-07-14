package com.jayzonsolutions.LunchBox.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jayzonsolutions.LunchBox.ApiUtils;
import com.jayzonsolutions.LunchBox.Constant;
import com.jayzonsolutions.LunchBox.PlaceOrderActivity;
import com.jayzonsolutions.LunchBox.R;
import com.jayzonsolutions.LunchBox.Service.FoodmakerService;
import com.jayzonsolutions.LunchBox.model.Categories;
import com.jayzonsolutions.LunchBox.model.FoodmakerDishes;
import com.jayzonsolutions.LunchBox.model.Products;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailFragment extends Fragment {
    public static Integer id;
    Context context = getContext();

    Animation startAnimation;
    private LinearLayout linear_progressbar;

    private Toolbar toolbar;
    private TextView toolBarTxt;

    private RecyclerView recyclerView;
    private RecycleAdapter_AddProduct mAdapter;
    private int status_code;
    private String token, totalPriceOfProducts;
    private FoodmakerService foodmakerService;
    List<FoodmakerDishes> foodmakerDishesList;

    private Map<Integer,Double> orderdishes;

//    private ProductArrayList productsArrayList;

    private TextView quantityOfTotalProduct, priceOfTotalProduct, next;
    private Categories categories;
    private ImageView btn;
    private int[] IMAGES = {R.drawable.biryani, R.drawable.koorma, R.drawable.pulao, R.drawable.chicken_karahi, R.drawable.salad};
    private String[] NamES = {"Biryani", "koorma", "Pulao", "Chicken_karahi", "Salad"};
    private String[] PRICE = {"150", "132", "101", "93", "85"};


    private View view;


    public DetailFragment() {
        // Required empty public constructor
    }

    public void setId(Integer id) {
        DetailFragment.id = id;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //create this screen orderDishes list
        orderdishes = new HashMap<>();


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail, container, false);

        startAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);

        //    initComponent(view);

        foodmakerDishesList = new ArrayList<>();
        categories = new Categories();
        categories.productsArrayList = new ArrayList<>();
        mAdapter = new RecycleAdapter_AddProduct(getActivity(), foodmakerDishesList);
        recyclerView = view.findViewById(R.id.recyclerview);



        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
      //  Toast.makeText(getActivity().getApplicationContext(), "id =" + id, Toast.LENGTH_SHORT).show();


/**
 *start
 ** call to get foodmaker dishes **/

        foodmakerService = ApiUtils.getFoodmakerService();

        foodmakerService.getDishesByFoodmakerId(id).enqueue(new Callback<List<FoodmakerDishes>>() {
            @Override
            public void onResponse(@NonNull Call<List<FoodmakerDishes>> call, @NonNull Response<List<FoodmakerDishes>> response) {
                Toast.makeText(getContext(), "success" , Toast.LENGTH_LONG).show();

              foodmakerDishesList = response.body();
              mAdapter.setFoodmakerDishesList(foodmakerDishesList);
 /*               for (FoodmakerDishes foodmakerDishes : response.body()) {
                    Log.d("TAG", "Response = " + foodmakerDishes.getDish().getDishName());

                    Toast.makeText(getContext(), "success" + foodmakerDishes.getDish().getDishName(), Toast.LENGTH_SHORT).show();



                }
 */


            }

            @Override
            public void onFailure(@NonNull Call<List<FoodmakerDishes>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Response Failed", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "failed" );
            }
        });



        /**
         *End
         ** call to get foodmaker dishes**/


        btn = view.findViewById(R.id.btnDetail);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  showTimePickerDialog(v);
                 Intent intent = new Intent(getActivity(), PlaceOrderActivity.class);
                 startActivity(intent);
                getActivity().overridePendingTransition(0,0);

            }
        });





        return view;


    }


    public class RecycleAdapter_AddProduct extends RecyclerView.Adapter<RecycleAdapter_AddProduct.MyViewHolder> {

        Context context;
        boolean showingFirst = true;
        int recentPos = -1;
        private List<FoodmakerDishes> foodmakerDishesList;


        RecycleAdapter_AddProduct(Context context, List<FoodmakerDishes> foodmakerDishesList) {
            this.foodmakerDishesList = foodmakerDishesList;
            this.context = context;
        }

        void setFoodmakerDishesList(List<FoodmakerDishes> foodmakerDishesList) {
            this.foodmakerDishesList = foodmakerDishesList;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public RecycleAdapter_AddProduct.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_product, parent, false);


            return new RecycleAdapter_AddProduct.MyViewHolder(itemView);


        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(@NonNull final RecycleAdapter_AddProduct.MyViewHolder holder, final int position) {
//            Products movie = productsList.get(position);

            holder.title.setText(foodmakerDishesList.get(position).getDish().getDishName());
            holder.price.setText(foodmakerDishesList.get(position).getDish().getDishSellingPrice().toString());
          //  holder.price.setText(categories.getProductsArrayList().get(position).getPrice());
            holder.quantityTxt.setText(foodmakerDishesList.get(position).getDish().getDishQuantity().toString());
         //   holder.quantityTxt.setText(categories.getProductsArrayList().get(position).getQuantity() + "");


            holder.quantity = 1;
            holder.quantity = foodmakerDishesList.get(position).getDish().getDishQuantity();
         //   holder.quantity = categories.getProductsArrayList().get(position).getQuantity();
            int totalPrice = holder.quantity * foodmakerDishesList.get(position).getDish().getDishSellingPrice();


         /*   Glide.with(context).load(ApiUtils.BASE_URL+"images/es2.jpg").
                    apply(RequestOptions.
                            centerCropTransform().fitCenter().
                            diskCacheStrategy(DiskCacheStrategy.ALL)).
                    into(holder.image);*/

            if (position == recentPos) {
                Log.e("pos", "" + recentPos);
                // start animation
                holder.quantityTxt.startAnimation(startAnimation);

            } else {
                holder.quantityTxt.clearAnimation();

            }


            if (holder.quantity > 0) {
                holder.quantityTxt.setVisibility(View.VISIBLE);
                holder.llMinus.setVisibility(View.VISIBLE);
            } else {
                holder.quantityTxt.setVisibility(View.GONE);
                holder.llMinus.setVisibility(View.GONE);
            }



     //       categories.getProductsArrayList().get(position).setPriceAsPerQuantity("" + totalPrice);
foodmakerDishesList.get(position).getDish().setDishPriceAsPerQuantity(" "+totalPrice);

            holder.llPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
            Log.e("dish"," => "+foodmakerDishesList.get(position).getDish().getDishId());
                    Log.e("dish"," => "+foodmakerDishesList.get(position).getFoodmakerDishesId());

                    if (holder.quantity < 10) {
                        int foodmakerdishId = foodmakerDishesList.get(position).getFoodmakerDishesId();

                        recentPos = position;
                        holder.quantity = holder.quantity + 1;
                        foodmakerDishesList.get(position).getDish().setDishQuantity(holder.quantity);
                     //   categories.getProductsArrayList().get(position).setQuantity(holder.quantity);
                        foodmakerDishesList.get(position).getDish().setDishPriceAsPerQuantity("" + holder.quantity * foodmakerDishesList.get(position).getDish().getDishSellingPrice());
                    //    categories.getProductsArrayList().get(position).setPriceAsPerQuantity("" + holder.quantity * Integer.parseInt(categories.getProductsArrayList().get(position).getPrice()));

                        holder.quantityTxt.setText("" + holder.quantity);

                            double quan = (double) holder.quantity;
                          orderdishes.put(foodmakerdishId,quan);
                          Constant.orderdishes.put(foodmakerdishId,quan);
                          int foodmakerId = foodmakerDishesList.get(position).getFoodmakerid();
                          Constant.foodmakerdishes.put(foodmakerId,orderdishes);

                    }


                    notifyDataSetChanged();

                }
            });


            holder.llMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (holder.quantity > 0 && holder.quantity <= 10) {

                        recentPos = position;

                        holder.quantity = holder.quantity - 1;
                        foodmakerDishesList.get(position).getDish().setDishQuantity(holder.quantity);
                //        categories.getProductsArrayList().get(position).setQuantity(holder.quantity);
                        foodmakerDishesList.get(position).getDish().setDishPriceAsPerQuantity("" + holder.quantity * foodmakerDishesList.get(position).getDish().getDishSellingPrice());
              //          categories.getProductsArrayList().get(position).setPriceAsPerQuantity("" + holder.quantity *
                  //              Integer.parseInt(categories.getProductsArrayList().get(position).getPrice()));

                        holder.quantityTxt.setText("" + holder.quantity);


                    }

                    notifyDataSetChanged();

                }
            });


        }

        @Override
        public int getItemCount() {
            return foodmakerDishesList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {


            ImageView image;
            TextView title;
            TextView price;
            TextView quantityTxt;
            int quantity;
            private LinearLayout llMinus, llPlus;


            MyViewHolder(View view) {
                super(view);

                image = view.findViewById(R.id.imageProduct);
                title = view.findViewById(R.id.titleProduct);
                price = view.findViewById(R.id.price);
                quantityTxt = view.findViewById(R.id.quantityTxt);
                llPlus = view.findViewById(R.id.llPlus);
                llMinus = view.findViewById(R.id.llMinus);
            }

        }

    }

}

