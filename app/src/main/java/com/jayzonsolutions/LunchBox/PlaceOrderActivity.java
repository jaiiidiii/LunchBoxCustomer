package com.jayzonsolutions.LunchBox;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.SettingInjectorService;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.jayzonsolutions.LunchBox.Fragments.DetailFragment;
import com.jayzonsolutions.LunchBox.Service.OrderService;
import com.jayzonsolutions.LunchBox.model.ApiResponse;
import com.jayzonsolutions.LunchBox.model.Cart;
import com.jayzonsolutions.LunchBox.model.CartItem;
import com.jayzonsolutions.LunchBox.model.Categories;
import com.jayzonsolutions.LunchBox.model.FoodmakerDishes;
import com.jayzonsolutions.LunchBox.model.Order;
import com.jayzonsolutions.LunchBox.model.OrderDish;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceOrderActivity extends AppCompatActivity implements
        View.OnClickListener,  GoogleApiClient.OnConnectionFailedListener  {

    TextView btnPlacePicker, btnDatePicker, btnTimePicker, txtDate, txtTime,btnPlaceOrder,placeDetails;;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private GoogleApiClient mGoogleApiClient;
    private int PLACE_PICKER_REQUEST = 1;
    private TextView tvPlaceDetails;
    private OrderService orderService;


    //
    private RecyclerView recyclerView;
    private RecycleAdapter_AddProduct mAdapter;
    private Categories categories;

    private Map<Integer,Double> orderdishes;
    private Map<Integer,CartItem> cartItemMap;
    Animation startAnimation;

    public String lang = "0.00";
    public String lat = "0.00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeorder);

        /**
         *
         */
        //create this screen orderDishes list
        orderdishes = new HashMap<>();
        cartItemMap = new HashMap<>();

        startAnimation = AnimationUtils.loadAnimation(PlaceOrderActivity.this, R.anim.bounce);
        Set<Map.Entry<Integer, CartItem>> entrySet = Cart.orderdishes.entrySet();
        ArrayList<CartItem> listOfEntry = new ArrayList<CartItem>(Cart.orderdishes.values());
        System.out.println(listOfEntry);


        //
        //foodmakerDishesList = new ArrayList<>();
        categories = new Categories();
        categories.productsArrayList = new ArrayList<>();
        mAdapter = new PlaceOrderActivity.RecycleAdapter_AddProduct(PlaceOrderActivity.this, listOfEntry);
        recyclerView = findViewById(R.id.cartitemrecyclerview);



        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PlaceOrderActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);









        /**
         *
         */








        if(Constant.customer == null){
            Intent intent = new Intent(PlaceOrderActivity.this,signin.class);
            startActivity(intent);
        }

        final Integer customerId = Integer.parseInt(Constant.customer.getCustomerId());

        btnPlacePicker= findViewById(R.id.place_pick);
        btnDatePicker= findViewById(R.id.btn_date);
        btnTimePicker= findViewById(R.id.btn_time);
        placeDetails = findViewById(R.id.placeDetails);


        btnPlaceOrder = findViewById(R.id.btn_placeOrder);

        txtDate= findViewById(R.id.in_date);
        txtTime= findViewById(R.id.in_time);

        btnPlacePicker.setOnClickListener(this);
        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

        orderService = ApiUtils.getOrderService();


        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * only for testing
                 * start
                 */
                if(Cart.orderdishes.size() == 0){
                    Toast.makeText(PlaceOrderActivity.this,"Your Cart is Empty",Toast.LENGTH_LONG).show();
                    return;
                }
                if(placeDetails.getText().toString().equals("")){
                    Toast.makeText(PlaceOrderActivity.this,"Please Insert Delivery Address ",Toast.LENGTH_LONG).show();
                    return;
                }

                if(txtDate.getText().toString().equals("")){
                    Toast.makeText(PlaceOrderActivity.this,"Please Select Date",Toast.LENGTH_LONG).show();
                    return;
                }

                Double totalPrice = 0.00;
                Set orderdishesKey1 = Cart.orderdishes.keySet();
                Set FoodmakerIdKey1 = Cart.foodmakerdishes.keySet();
                for (Iterator i = FoodmakerIdKey1.iterator(); i.hasNext(); ) {
                    int foodmakerId = (Integer)i.next();
                    HashMap<Integer,CartItem> foodmakerOrderDishes = (HashMap<Integer,CartItem>) Cart.foodmakerdishes.get(foodmakerId);//                    int foodmakerId =  i.next();

                    Log.v("foodmaker order" ,"foodakerid ==> "+ foodmakerId+"\norderdishes => "+foodmakerOrderDishes);
                    Order order = new Order(); //create order instant
                    order.setOrderCustomerId(customerId);
                    order.setOrderShipmentAddress(placeDetails.getText().toString());
                    order.setOrderDate(txtDate.getText().toString());

                    /**
                     * set lat lang
                     * */
                        Double latitude = Double.parseDouble(lat);
                        Double longitude = Double.parseDouble(lang);

                        order.setLatitude(latitude);
                        order.setLongitude(longitude);

                    /**
                     *set lat lang
                     * */

                    order.setOrderDeliverDate(txtDate.getText().toString());

                    order.setOrderStatus(1);
                    order.setFoodmakerId(foodmakerId);
                    List<OrderDish> orderDishes = new ArrayList<>();



                    Set foodmakerOrderDishesKey1 = foodmakerOrderDishes.keySet();
                    for (Iterator j = foodmakerOrderDishesKey1.iterator(); j.hasNext(); ) {
                        int orderDishesId = (Integer)j.next();
                        CartItem cartItem = (CartItem) Cart.orderdishes.get(orderDishesId);
                        totalPrice += cartItem.getQuantity()*cartItem.getFoodmakerDishes().getPrice();

                        OrderDish orderDish = new OrderDish();
                        orderDish.setDishId(orderDishesId);
                        orderDish.setQuantity(cartItem.getQuantity());
                        orderDishes.add(orderDish);

                        Log.v("cart item",cartItem.toString());
                    }
                    order.setOrderTotalAmount(totalPrice);
                    order.setOrderdishes(orderDishes);




                    Log.v("order detail ",order.getOrderdishes().toString());


                    orderService.placeOrder(order).enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                            Toast.makeText(PlaceOrderActivity.this,"Order Placed Successfully ",Toast.LENGTH_LONG).show();

                            //     System.out.println(response.body().toString());
                            Cart.orderdishes.clear();
                            Cart.foodmakerdishes.clear();
                            Intent intent = new Intent(PlaceOrderActivity.this, customerActivity.class);
                            startActivity(intent);


                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {
                            Toast.makeText(PlaceOrderActivity.this,"Network Error",Toast.LENGTH_LONG).show();
                         /*   Intent intent = new Intent(PlaceOrderActivity.this, customerActivity.class);
                            startActivity(intent);*/
                        }
                    });

                }

                /***
                 * end
                 */















            }
        });





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
                      //      txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            txtDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
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
            /*    stBuilder.append("Name: ");
                stBuilder.append(placename);
                stBuilder.append("\n");
                stBuilder.append("Latitude: ");
                stBuilder.append(latitude);
                stBuilder.append("\n");
                stBuilder.append("Logitude: ");
                stBuilder.append(longitude);
                stBuilder.append("\n");
                stBuilder.append("Address: ");*/
                stBuilder.append(address);
                tvPlaceDetails.setText(stBuilder.toString());
                lang = longitude;
                lat = latitude;
            }
        }
    }




    //
    public class RecycleAdapter_AddProduct extends RecyclerView.Adapter<PlaceOrderActivity.RecycleAdapter_AddProduct.MyViewHolder> {

        Context context;
        boolean showingFirst = true;
        int recentPos = -1;
        private List<FoodmakerDishes> foodmakerDishesList;
        private List<CartItem> cartItems;


        RecycleAdapter_AddProduct(Context context, List<CartItem> cartItems) {
            this.cartItems = cartItems;
            this.context = context;
        }

        void setFoodmakerDishesList(List<FoodmakerDishes> foodmakerDishesList) {
            this.foodmakerDishesList = foodmakerDishesList;
            notifyDataSetChanged();
        }
        void setCartItems(List<CartItem> cartItems) {
            this.cartItems = cartItems;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public PlaceOrderActivity.RecycleAdapter_AddProduct.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_product, parent, false);


            return new PlaceOrderActivity.RecycleAdapter_AddProduct.MyViewHolder(itemView);


        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(@NonNull final PlaceOrderActivity.RecycleAdapter_AddProduct.MyViewHolder holder, final int position) {
//            Products movie = productsList.get(position);

          //  holder.title.setText(""+cartItems.get(position).getFoodmakerDishes().getName());
          //  holder.price.setText(""+cartItems.get(position).getFoodmakerDishes().getPrice());

            holder.title.setText(""+cartItems.get(position).getFoodmakerDishes().getDish().getDishName());
            holder.price.setText(""+cartItems.get(position).getFoodmakerDishes().getDish().getDishQuantity());



            //  holder.price.setText(categories.getProductsArrayList().get(position).getPrice());
            Double d = cartItems.get(position).getQuantity();
            holder.quantityTxt.setText(""+d.intValue());
            //   holder.quantityTxt.setText(categories.getProductsArrayList().get(position).getQuantity() + "");


            holder.quantity = 1;

            holder.quantity = d.intValue();
            //   holder.quantity = categories.getProductsArrayList().get(position).getQuantity();
            Double totalPrice = holder.quantity * cartItems.get(position).getFoodmakerDishes().getPrice();


            String imagePath = ((cartItems.get(position).getFoodmakerDishes().getImagepath() != null)?cartItems.get(position).getFoodmakerDishes().getImagepath():"http://localhost:8080/images/user_na.jpg");


            Glide.with(context).load(ApiUtils.BASE_URL+(imagePath.substring(21))).
                    apply(RequestOptions.
                            centerCropTransform().fitCenter().
                            diskCacheStrategy(DiskCacheStrategy.ALL)).
                    into(holder.image);

/*
           Glide.with(context).load(ApiUtils.BASE_URL+"images/es2.jpg").
                    apply(RequestOptions.
                            centerCropTransform().fitCenter().
                            diskCacheStrategy(DiskCacheStrategy.ALL)).
                    into(holder.image);
*/

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
            cartItems.get(position).getFoodmakerDishes().getDish().setDishPriceAsPerQuantity(" "+totalPrice);

            holder.llPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (holder.quantity < 10) {
                        int foodmakerdishId = cartItems.get(position).getFoodmakerDishes().getFoodmakerDishesId();

                        recentPos = position;
                        holder.quantity = holder.quantity + 1;
                        cartItems.get(position).getFoodmakerDishes().getDish().setDishQuantity(holder.quantity);
                        //   categories.getProductsArrayList().get(position).setQuantity(holder.quantity);
                        cartItems.get(position).getFoodmakerDishes().getDish().setDishPriceAsPerQuantity("" + holder.quantity * cartItems.get(position).getFoodmakerDishes().getPrice());
                        //    categories.getProductsArrayList().get(position).setPriceAsPerQuantity("" + holder.quantity * Integer.parseInt(categories.getProductsArrayList().get(position).getPrice()));

                        holder.quantityTxt.setText("" + holder.quantity);

                        double quan = (double) holder.quantity;
                    //    orderdishes.put(foodmakerdishId,quan);
                    //    Constant.orderdishes.put(foodmakerdishId,quan);
                       int foodmakerId =  cartItems.get(position).getFoodmakerId();
                    //    Constant.foodmakerdishes.put(foodmakerId,orderdishes);


                        /**
                         * cart item
                         */


                        cartItemMap.put(foodmakerdishId,new CartItem(foodmakerdishId,cartItems.get(position).getFoodmakerDishes(),quan));
                        Cart.orderdishes.put(foodmakerdishId,new CartItem(foodmakerdishId,cartItems.get(position).getFoodmakerDishes(),quan));
                        Cart.foodmakerdishes.put(foodmakerId,cartItemMap);

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
                        cartItems.get(position).getFoodmakerDishes().getDish().setDishQuantity(holder.quantity);
                        //        categories.getProductsArrayList().get(position).setQuantity(holder.quantity);
                        cartItems.get(position).getFoodmakerDishes().getDish().setDishPriceAsPerQuantity("" + holder.quantity * cartItems.get(position).getFoodmakerDishes().getPrice());
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
            return cartItems.size();
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
