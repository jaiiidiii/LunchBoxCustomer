package com.jayzonsolutions.LunchBox.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.jayzonsolutions.LunchBox.ApiUtils;
import com.jayzonsolutions.LunchBox.Constant;
import com.jayzonsolutions.LunchBox.GlobalVariables;
import com.jayzonsolutions.LunchBox.R;
import com.jayzonsolutions.LunchBox.Service.APIService;
import com.jayzonsolutions.LunchBox.Service.FoodmakerService;
import com.jayzonsolutions.LunchBox.Service.ItemClickListener;
import com.jayzonsolutions.LunchBox.Service.OrderService;
import com.jayzonsolutions.LunchBox.model.ApiResponse;
import com.jayzonsolutions.LunchBox.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoneOrdersFragments extends Fragment{


    final GlobalVariables g;

    List<Order> orderList;
    RecyclerView recyclerView;
    RecycleAdapter_AddProduct recyclerAdapter;
    Context context = getContext();

    private OrderService orderService;
    private FoodmakerService foodmakerService;
    private APIService mAPIService;
    RatingBar ratingBar;


    public DoneOrdersFragments() {
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


        orderList = new ArrayList<>();
        recyclerView = v.findViewById(R.id.recyclerview);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DoneOrdersFragments.GridSpacingItemDecoration(1, dpToPx(), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerAdapter = new DoneOrdersFragments.RecycleAdapter_AddProduct(getContext(), orderList);
        recyclerView.setAdapter(recyclerAdapter);


        mAPIService = ApiUtils.getAPIService();
        orderService = ApiUtils.getOrderService();


        return v;
    }

    @Override
    public void onResume() {

        super.onResume();
        context = getActivity();

        getOrderList();

    }

    public void getOrderList() {
        String customerIdStr =  Constant.customer.getCustomerId();
        Integer customerId = Integer.parseInt(customerIdStr);

        orderService.getDoneOrdersBycustomerId(customerId).enqueue(new Callback<List<Order>>() {

            @Override
            public void onResponse(@NonNull Call<List<Order>> call, @NonNull Response<List<Order>> response) {

                if(orderList.size() <= 0)
                {
                    orderList = response.body();
                    if(orderList == null){
                        orderList = new ArrayList<Order>();
                    }
                    Log.d("TAG", "Response = " + orderList);
                    recyclerAdapter.setCustomerOrderList(orderList);
                }
                else
                {
                    Toast.makeText(context, "no current orders", Toast.LENGTH_LONG).show();
                }




            }

            @Override
            public void onFailure(@NonNull Call<List<Order>> call, @NonNull Throwable t) {
                Toast.makeText(context, "connection problem", Toast.LENGTH_LONG).show();
            }
        });
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



    public class RecycleAdapter_AddProduct extends RecyclerView.Adapter<RecycleAdapter_AddProduct.MyViewHolder> {

        Context context;
        boolean showingFirst = true;
        int recentPos = -1;
        private List<Order> customerOrderList;


        RecycleAdapter_AddProduct(Context context, List<Order> customerOrderList) {
            this.customerOrderList = customerOrderList;
            this.context = context;
        }

        void setCustomerOrderList(List<Order> foodmakerOrderList) {
            this.customerOrderList = foodmakerOrderList;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public RecycleAdapter_AddProduct.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item, parent, false);


            return new RecycleAdapter_AddProduct.MyViewHolder(itemView);


        }

        @SuppressLint("SetTextI18n")
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(@NonNull final RecycleAdapter_AddProduct.MyViewHolder holder, final int position) {

            //junaid commit
//            Products movie = productsList.get(position);
            if (customerOrderList.get(position).getCustomer() != null) {

/*                String imagePath = ((customerOrderList.get(position).getCustomer().getCustomerImagePath() != null)?customerOrderList.get(position).getCustomer().getCustomerImagePath():"http://localhost:8080/images/user_na.jpg");

                Glide.with(context).load(ApiUtils.BASE_URL+(imagePath.substring(21))).
                        apply(RequestOptions.
                                centerCropTransform().fitCenter().
                                diskCacheStrategy(DiskCacheStrategy.ALL)).
                        into(holder.image);*/

                //holder.title.setText(customerOrderList.get(position).getCustomer().getCustomerName());
                holder.title.setText(customerOrderList.get(position).getFoodmaker().getFoodmakerName());
                holder.price.setText(customerOrderList.get(position).getOrderTotalAmount().toString());
                holder.quantity = customerOrderList.get(position).getOrderdishes().size();

/*        Glide.with(context).load(ApiUtils.BASE_URL+(foodmakerOrderList.get(position).getFoodmakerImagePath().substring(21))).
                apply(RequestOptions.
                        centerCropTransform().fitCenter().
                        diskCacheStrategy(DiskCacheStrategy.ALL)).
                into(holder.FoodMakerImage);*/
            } else {
                holder.title.setText("new order");
            }


//            holder.price.setText(foodmakerOrderList.get(position).getOrderTotalAmount().toString());



            //  holder.quantity = 1;

            //   holder.quantity = categories.getProductsArrayList().get(position).getQuantity();
            //  int totalPrice = holder.quantity * foodmakerOrderList.get(position).getDish().getDishSellingPrice();




            //       categories.getProductsArrayList().get(position).setPriceAsPerQuantity("" + totalPrice);


            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(View v, int pos) {
                    showDialog(pos);

                }
            });


/*            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(View v, final int pos) {
                    Log.d("pos", String.valueOf(pos));
                    Toast.makeText(context, "Clicked Position =" + pos, Toast.LENGTH_SHORT).show();

//                    Toast.makeText(context, "Pressed Order Item", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Done Order");
                    alert.setMessage("the meal is prepared ?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Toast.makeText(context, "Clicked Yes", Toast.LENGTH_SHORT).show();

                            OrderService orderService = ApiUtils.getOrderService();
                            orderService.updateOrderStatus(3, foodmakerOrderList.get(pos).getOrderId()).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                                    Toast.makeText(context, "Order status changed to 2", Toast.LENGTH_LONG).show();
                                    removeAt(pos);

                                }

                                @Override
                                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                                    Toast.makeText(context, "Response Failed", Toast.LENGTH_SHORT).show();
                                    Log.d("TAG", "failed");
                                }
                            });

                            //     setOrderStatus(foodmakerOrderList.get(pos).getOrderId());
                *//*Intent myIntent = new Intent(
                        CameraPhotoCapture.this,
                        LoginActivity.class);
                startActivity(myIntent);
                finish();*//*

                        }
                    });

                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context, "Clicked No", Toast.LENGTH_SHORT).show();

                        }
                    });

                    alert.show();


                }
            });*/
        }

        @Override
        public int getItemCount() {
            return customerOrderList.size();
        }


        void showDialog(final int pos)
        {
            foodmakerService = ApiUtils.getFoodmakerService();

            LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
            View view = inflater.inflate(R.layout.fragment_two,null);
            view.findViewById(R.id.dialog_ratingbar);

            ratingBar = view.findViewById(R.id.dialog_ratingbar);

            if(orderList.get(pos).getOrderRating() == 0 )
            {
                ratingBar.setRating(0);

                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Rating");
                alert.setMessage("how do you rate this app");
                alert.setView(view);

                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {

                        foodmakerService.setRatings(orderList.get(pos).getOrderCustomerId(),orderList.get(pos).getFoodmakerId(),
                                (int) ratingBar.getRating()).enqueue(new Callback<ApiResponse>() {
                            @Override
                            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                                Toast.makeText(context, "Thankyou for your feedback", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<ApiResponse> call, Throwable t) {
                                Toast.makeText(context, "connection problem", Toast.LENGTH_SHORT).show();
                            }
                        });

                        orderService.updateOrderRating((int) ratingBar.getRating(),orderList.get(pos).getOrderId()).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                    getOrderList();
                                    dialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                getOrderList();
                                dialog.dismiss();
                            }
                        });

                    }
                });

                alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }

            else {

                ratingBar.setRating(orderList.get(pos).getOrderRating().floatValue());

                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Rating");
                alert.setMessage("you rated the foodmaker");
                alert.setView(view);

                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {

                        foodmakerService.setRatings(orderList.get(pos).getOrderCustomerId(),orderList.get(pos).getFoodmakerId(),
                                (int) ratingBar.getRating()).enqueue(new Callback<ApiResponse>() {
                            @Override
                            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                                Toast.makeText(context, "Thankyou for your feedback", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<ApiResponse> call, Throwable t) {
                                Toast.makeText(context, "connection problem", Toast.LENGTH_SHORT).show();
                            }
                        });

                        orderService.updateOrderRating((int) ratingBar.getRating(),orderList.get(pos).getOrderId()).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                    getOrderList();
                                    dialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                getOrderList();
                                dialog.dismiss();
                            }
                        });

                    }
                });

                alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }


        }

        public void removeAt(int position) {
            customerOrderList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, customerOrderList.size());
        }

        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


            ImageView image;
            TextView title;
            TextView price;

            int quantity;


            private ItemClickListener itemClickListener;

            MyViewHolder(View view) {
                super(view);

                image = view.findViewById(R.id.imageProduct1);
                title = view.findViewById(R.id.titleProduct1);
                price = view.findViewById(R.id.price1);
                view.setOnClickListener(this);
            }
            @Override
            public void onClick(View v) {
                this.itemClickListener.onItemClick(v, getLayoutPosition());
            }

            void setItemClickListener(ItemClickListener ic) {
                this.itemClickListener = ic;

            }
        }

    }

}



