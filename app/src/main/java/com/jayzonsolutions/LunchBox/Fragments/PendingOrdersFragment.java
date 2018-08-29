package com.jayzonsolutions.LunchBox.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jayzonsolutions.LunchBox.ApiUtils;
import com.jayzonsolutions.LunchBox.BillingDetails;
import com.jayzonsolutions.LunchBox.Constant;
import com.jayzonsolutions.LunchBox.GlobalVariables;
import com.jayzonsolutions.LunchBox.R;
import com.jayzonsolutions.LunchBox.Service.APIService;

import com.jayzonsolutions.LunchBox.Service.ItemClickListener;
import com.jayzonsolutions.LunchBox.Service.OrderService;

import com.jayzonsolutions.LunchBox.model.Order;
import com.jayzonsolutions.LunchBox.model.OrderDish;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingOrdersFragment extends Fragment {

    final GlobalVariables g;

    List<Order> orderList;
    RecyclerView recyclerView;
    RecycleAdapter_AddProduct recyclerAdapter;
    Context context = getContext();

    private OrderService orderService;
    private APIService mAPIService;


    public PendingOrdersFragment() {
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
        recyclerView.addItemDecoration(new PendingOrdersFragment.GridSpacingItemDecoration(1, dpToPx(), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerAdapter = new RecycleAdapter_AddProduct(getContext(), orderList);
        recyclerView.setAdapter(recyclerAdapter);


        mAPIService = ApiUtils.getAPIService();
        orderService = ApiUtils.getOrderService();


        return v;
    }

    @Override
    public void onResume() {

        super.onResume();
        context = getActivity();

        String customerIdStr =  Constant.customer.getCustomerId();
        Integer customerId = Integer.parseInt(customerIdStr);


        orderService.getOrdersBycustomerId(customerId).enqueue(new Callback<List<Order>>() {

            @Override
            public void onResponse(@NonNull Call<List<Order>> call, @NonNull Response<List<Order>> response) {

                if(orderList.size() <= 0)
                {
                    orderList = response.body();
                    Log.d("TAG", "Response = " + orderList);
                    recyclerAdapter.setCustomerOrderList(orderList);
                }
                else
                {
                    Toast.makeText(context, "no pending orders", Toast.LENGTH_LONG).show();
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

        void setCustomerOrderList(List<Order> customerOrderList) {
            this.customerOrderList = customerOrderList;
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

              //  holder.title.setText(customerOrderList.get(position).getCustomer().getCustomerName());
                holder.title.setText(customerOrderList.get(position).getFoodmaker().getFoodmakerName());
                holder.price.setText(customerOrderList.get(position).getOrderTotalAmount().toString());
                holder.quantity = customerOrderList.get(position).getOrderdishes().size();


            } else {

                holder.title.setText("new order!");
            }


//            holder.price.setText(foodmakerOrderList.get(position).getOrderTotalAmount().toString());



            //  holder.quantity = 1;

            //   holder.quantity = categories.getProductsArrayList().get(position).getQuantity();
            //  int totalPrice = holder.quantity * foodmakerOrderList.get(position).getDish().getDishSellingPrice();




            //       categories.getProductsArrayList().get(position).setPriceAsPerQuantity("" + totalPrice);


            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(View v, final int pos) {

                    showDialog(pos);

                    /*
                    Log.d("pos", String.valueOf(pos));
               //     Toast.makeText(context, "Clicked Position =" + pos, Toast.LENGTH_SHORT).show();
                    final ScrollView s_view = new ScrollView(context);
                    final TextView t_view = new TextView(context);

                    SpannableString DishName = new SpannableString("> Dish: \n");
                    DishName.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, DishName.length(), 0);

                    SpannableString DishDescription = new SpannableString("> Dishes:\n");
                    DishDescription.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, DishDescription.length(), 0);
                    t_view.append(DishDescription);
for(int i = 0; i < customerOrderList.get(pos).getOrderdishes().size(); i++ )
{
    
 

  //  t_view.append(DishName);
  //  t_view.append(customerOrderList.get(pos).getOrderdishes().get(i).getDishes().getName()+ "\n");

    t_view.append(""+customerOrderList.get(pos).getOrderdishes().get(i).getDishes().getName()+ "\n");



   
}
                    t_view.setTextSize(15);
                    //  t_view.setTextColor(primary_text_material_dark);
                    s_view.addView(t_view);

//                    Toast.makeText(context, "Pressed Order Item", Toast.LENGTH_SHORT).show();
                    final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Done Order");
                   // alert.setMessage(customerOrderList.get(pos).getOrderdishes().get(pos).getDishes().getDescription());
                    alert.setView(s_view);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Toast.makeText(context, "Clicked Yes", Toast.LENGTH_SHORT).show();

                            //     setOrderStatus(foodmakerOrderList.get(pos).getOrderId());


                        }
                    });
alert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
      //  Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder alert2 = new AlertDialog.Builder(context);
        alert2.setTitle("Are You sure to cancel the order");
        alert2.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

             //   Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();

                //     setOrderStatus(foodmakerOrderList.get(pos).getOrderId());
                orderService.updateOrderStatus(4, customerOrderList.get(pos).getOrderId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        Toast.makeText(context, "Order status changed to 4", Toast.LENGTH_LONG).show();
                        removeAt(pos);

                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Toast.makeText(context, "Response Failed", Toast.LENGTH_SHORT).show();
                      //  Log.d("TAG", "failed");
                    }
                });

            }
        });
        alert2.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Clicked No", Toast.LENGTH_SHORT).show();

            }
        });
        alert2.show();
    }
});
                 */
/*   alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context, "Clicked No", Toast.LENGTH_SHORT).show();

                        }
                    });*//*


                    alert.show();

*/

                }
            });
        }

        @Override
        public int getItemCount() {
            return customerOrderList.size();
        }


        public void removeAt(int position) {
            customerOrderList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, customerOrderList.size());
        }



        void showDialog(final int pos) {

            List<OrderDish> orderDishList = new ArrayList<>(orderList.get(pos).getOrderdishes());
            String dishes[] = new String[orderDishList.size()];

            for(int i=0;i<orderDishList.size();i++)
            {
                dishes[i] = " " +i+ " : " +orderDishList.get(i).getDishes().getName() + " ^ " + orderDishList.get(i).getQuantity()
                        + " ^ " + orderDishList.get(i).getDishes().getPrice();
            }

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            LayoutInflater inflater = getLayoutInflater();
            View convertView = (View) inflater.inflate(R.layout.custom, null);
            alertDialog.setView(convertView);
            alertDialog.setTitle("Order detail");
            ListView lv = (ListView) convertView.findViewById(R.id.listView1);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(context,R.layout.list_item,R.id.text1,dishes);
            lv.setAdapter(adapter);

            alertDialog.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setNeutralButton("Review Order", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent in = new Intent(getActivity(), BillingDetails.class);
                    startActivity(in);
                }
            });
            alertDialog.setNegativeButton("canel order", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final AlertDialog.Builder alert_Dialog = new AlertDialog.Builder(context);
                    alert_Dialog.setTitle("Cancel order");
                    alert_Dialog.setMessage("are you sure you want to cancel the order?");
                    alert_Dialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context, "your order is canceled", Toast.LENGTH_SHORT).show();
                            OrderService orderService = ApiUtils.getOrderService();
                            orderService.updateOrderStatus(4, orderList.get(pos).getOrderId()).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                                    removeAt(pos);
                                }

                                @Override
                                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                                    Toast.makeText(context, "connection problem", Toast.LENGTH_SHORT).show();
                                    Log.d("TAG", "failed");
                                }
                            });
                        }
                    });
                    alert_Dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        }
                    });


                    alert_Dialog.show();
                }
            });


            alertDialog.show();

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
