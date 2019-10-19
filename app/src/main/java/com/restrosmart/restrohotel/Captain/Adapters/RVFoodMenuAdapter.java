package com.restrosmart.restrohotel.Captain.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Captain.Interfaces.ToppingsListener;
import com.restrosmart.restrohotel.Captain.Models.FoodMenuModel;
import com.restrosmart.restrohotel.Captain.Models.ToppingsModel;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.FOOD_ADD_TO_CART;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.TABLE_NO;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.USER_ID;

public class RVFoodMenuAdapter extends RecyclerView.Adapter<RVFoodMenuAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<FoodMenuModel> arrayList;
    private BottomSheetDialog dialog;
    private ProgressDialog progressDialog;

    private String toppingsList;

    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;
    private Sessionmanager mSessionmanager;

    private HashMap<String, String> hotelDetails, userDetails;
    private int selectedPos;
    private int mExpandedPosition = -1, previousExpandedPosition;

    private Animation slideUp, slideDown, leftToRight, rightToLeft;

    public RVFoodMenuAdapter(Context context, ArrayList<FoodMenuModel> arrayList) {
        this.mContext = context;
        this.arrayList = arrayList;
        mSessionmanager = new Sessionmanager(mContext);

        userDetails = mSessionmanager.getUserDetails();
        hotelDetails = mSessionmanager.getHotelDetails();

        slideDown = AnimationUtils.loadAnimation(mContext, R.anim.slide_down);
        slideUp = AnimationUtils.loadAnimation(mContext, R.anim.slide_up);
        leftToRight = AnimationUtils.loadAnimation(mContext, R.anim.left_to_right_anim);
        rightToLeft = AnimationUtils.loadAnimation(mContext, R.anim.right_to_left_anim);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_food_menu_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {

        holder.tvVegMenuName.setText(arrayList.get(position).getMenuName());
        holder.tvMenuDesc.setText("(" + arrayList.get(position).getMenuDesc() + ")");
        holder.tvMenuPrice.setText(mContext.getResources().getString(R.string.currency) + String.valueOf(arrayList.get(position).getMenuPrice()));

        if (arrayList.get(position).getMenuTaste() == 1) {
            holder.tvSweet.setVisibility(View.VISIBLE);
        } else if (arrayList.get(position).getMenuTaste() == 2) {
            holder.ivSpicy.setVisibility(View.VISIBLE);
        } else {
            holder.tvSweet.setVisibility(View.GONE);
            holder.ivSpicy.setVisibility(View.GONE);
        }

        Picasso.with(mContext)
                .load(arrayList.get(position).getMenuImage())
                .into(holder.ivMenuImg);

        final boolean isExpanded = position == mExpandedPosition;
        holder.llToppings.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);

        if (isExpanded)
            previousExpandedPosition = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mExpandedPosition = isExpanded ? -1 : position;
                notifyItemChanged(previousExpandedPosition);
                notifyItemChanged(position);

                //holder.btnAdd.setAnimation(rightToLeft);
                //holder.cvQty.setAnimation(leftToRight);

                /*selectedPos = position;
                notifyDataSetChanged();*/
            }
        });

        /*if (selectedPos == position) {
            holder.llToppings.setVisibility(View.VISIBLE);
            holder.llToppings.setAnimation(slideDown);
            holder.btnAdd.setAnimation(rightToLeft);
            holder.cvQty.setAnimation(leftToRight);
        } else {
            holder.llToppings.setVisibility(View.GONE);
            holder.llToppings.setAnimation(slideUp);
        }*/

        if (arrayList.get(position).getToppingsModelArrayList() != null && arrayList.get(position).getToppingsModelArrayList().size() > 0) {
            holder.rvMenuTopping.setLayoutManager(new GridLayoutManager(mContext, 1));
            RVToppingsAdapter rvToppingsAdapter = new RVToppingsAdapter(mContext, arrayList.get(position).getToppingsModelArrayList(), new ToppingsListener() {
                @Override
                public void AddRemoveToppings(ArrayList<ToppingsModel> arrayList) {
                    if (arrayList != null && arrayList.size() > 0) {
                        toppingsList = getMenuToppingList(arrayList);
                    } else {
                        toppingsList = null;
                    }
                }
            });
            holder.rvMenuTopping.setAdapter(rvToppingsAdapter);
            holder.rvMenuTopping.setVisibility(View.VISIBLE);
        } else {
            holder.rvMenuTopping.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvVegMenuName, tvMenuDesc, tvMenuPrice, tvSweet;
        private ImageView ivMenuImg, ivSpicy;
        private RecyclerView rvMenuTopping;

        private TextView tvMinusQty, tvAddQty, tvMenuQty;
        private Button btnAdd;
        private CardView cvQty;
        private LinearLayout llToppings;

        ItemViewHolder(View itemView) {
            super(itemView);

            tvVegMenuName = itemView.findViewById(R.id.tvVegMenuName);
            tvMenuDesc = itemView.findViewById(R.id.tvMenuDesc);
            tvMenuPrice = itemView.findViewById(R.id.tvMenuPrice);
            ivMenuImg = itemView.findViewById(R.id.ivMenuImg);
            ivSpicy = itemView.findViewById(R.id.ivSpicy);
            tvSweet = itemView.findViewById(R.id.tvSweet);

            llToppings = itemView.findViewById(R.id.llToppings);
            rvMenuTopping = itemView.findViewById(R.id.rvMenuTopping);
            cvQty = itemView.findViewById(R.id.cvQty);
            btnAdd = itemView.findViewById(R.id.btnAdd);

            tvMinusQty = itemView.findViewById(R.id.tvMinusQty);
            tvMenuQty = itemView.findViewById(R.id.tvMenuQty);
            tvAddQty = itemView.findViewById(R.id.tvAddQty);

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog = new ProgressDialog(mContext);
                    progressDialog.setMessage("Adding to cart...");
                    progressDialog.show();
                    MenuAddToCart();
                }
            });

            tvAddQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvMenuQty.setText(String.valueOf(Integer.parseInt(tvMenuQty.getText().toString()) + 1));
                }
            });

            tvMinusQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Integer.parseInt(tvMenuQty.getText().toString()) == 1) {
                        tvMenuQty.setText(String.valueOf(1));
                    } else {
                        tvMenuQty.setText(String.valueOf(Integer.parseInt(tvMenuQty.getText().toString()) - 1));
                    }
                }
            });
        }

        private void MenuAddToCart() {
            initRetrofitCallback();

            if (toppingsList == null || toppingsList.equalsIgnoreCase("")) {
                toppingsList = "";
            }

            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
            mRetrofitService = new RetrofitService(mResultCallBack, mContext);
            mRetrofitService.retrofitData(FOOD_ADD_TO_CART, (service.addToCart(mSessionmanager.getOrderID(), Integer.parseInt(userDetails.get(TABLE_NO)), Integer.parseInt(userDetails.get(USER_ID)), Integer.parseInt(hotelDetails.get(HOTEL_ID)), Integer.parseInt(hotelDetails.get(BRANCH_ID)), arrayList.get(getAdapterPosition()).getMenuId(),
                    Integer.parseInt(tvMenuQty.getText().toString()), toppingsList, 0, 0, 1, 0)));
        }

        private void initRetrofitCallback() {
            mResultCallBack = new IResult() {
                @Override
                public void notifySuccess(int requestId, Response<JsonObject> response) {
                    JsonObject jsonObjectCat = response.body();
                    String responseValue = jsonObjectCat.toString();

                    try {
                        JSONObject jsonObject = new JSONObject(responseValue);

                        int status = jsonObject.getInt("status");

                        progressDialog.dismiss();
                        if (status == 1) {
                            Toast.makeText(mContext, "Added in cart", Toast.LENGTH_SHORT).show();
                            toppingsList = null;
                            tvMenuQty.setText("1");
                            mSessionmanager.saveCartCount();
                            mSessionmanager.saveOrderID(jsonObject.getInt("Order_Id"));

                            Intent intent = new Intent("com.restrosmart.restro.addmenu");
                            //intent.putExtra("menuname", arrayList.get(getAdapterPosition()).getMenuName());
                            mContext.sendBroadcast(intent);
                        } else {
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void notifyError(int requestId, Throwable error) {
                    Log.v("Retrofit RequestId", String.valueOf(requestId));
                    Log.v("Retrofit Error", String.valueOf(error));
                }
            };
        }
    }

    private String getMenuToppingList(ArrayList<ToppingsModel> toppingsArrayList) {

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < toppingsArrayList.size(); i++) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Topping_Id", toppingsArrayList.get(i).getToppingsId());
                //jsonObject.put("Topping_Name", arrayListToppings.get.getStudentName());
                jsonArray.put(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return jsonArray.toString();
    }

    public void refreshList(ArrayList<FoodMenuModel> refreshList) {
        this.arrayList = refreshList;
        notifyDataSetChanged();
    }
}

/*btnAddMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View dialoglayout = li.inflate(R.layout.bottom_sheet_add_to_cart, null);
                    dialog = new BottomSheetDialog(mContext);
                    dialog.setContentView(dialoglayout);

                    tvToppingsLabel = dialog.findViewById(R.id.tvToppingsLabel);
                    rvToppings = dialog.findViewById(R.id.rvToppings);
                    tvMinusQty = dialog.findViewById(R.id.tvMinusQty);
                    tvMenuQty = dialog.findViewById(R.id.tvMenuQty);
                    tvAddQty = dialog.findViewById(R.id.tvAddQty);
                    btnAdd = dialog.findViewById(R.id.btnAdd);

                    *//*Toppings recyclerview*//*
                    if (arrayList.get(getAdapterPosition()).getToppingsModelArrayList() != null && arrayList.get(getAdapterPosition()).getToppingsModelArrayList().size() > 0) {

                        rvToppings.setLayoutManager(new LinearLayoutManager(mContext));
                        RVToppingsAdapter rvToppingsAdapter = new RVToppingsAdapter(mContext, arrayList.get(getAdapterPosition()).getToppingsModelArrayList(), new ToppingsListener() {
                            @Override
                            public void AddRemoveToppings(ArrayList<ToppingsModel> arrayList) {
                                if (arrayList != null && arrayList.size() > 0) {
                                    toppingsList = getToppingList(arrayList);
                                } else {
                                    toppingsList = null;
                                }
                            }
                        });
                        rvToppings.setAdapter(rvToppingsAdapter);
                        rvToppings.setVisibility(View.VISIBLE);
                        tvToppingsLabel.setVisibility(View.VISIBLE);
                    } else {
                        tvToppingsLabel.setVisibility(View.GONE);
                        rvToppings.setVisibility(View.GONE);
                    }

                    dialog.show();

                    btnAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            progressDialog = new ProgressDialog(mContext);
                            progressDialog.setMessage("Adding to cart...");
                            progressDialog.show();
                            MenuAddToCart();

                            *//*FoodMenuModel foodMenuModel = arrayList.get(getAdapterPosition());

                            FoodCartModel foodCartModel = new FoodCartModel();
                            foodCartModel.setMenuId(foodMenuModel.getMenuId());
                            foodCartModel.setMenuName(foodMenuModel.getMenuName());
                            foodCartModel.setMenuImage(foodMenuModel.getMenuImage());
                            foodCartModel.setMenuPrice(foodMenuModel.getMenuPrice());
                            foodCartModel.setMenuQtyPrice(foodMenuModel.getMenuPrice());
                            foodCartModel.setMenuQty(Integer.parseInt(tvMenuQty.getText().toString()));
                            sessionmanager.addToFoodCart(mContext, foodCartModel);

                            Intent intent = new Intent("com.restrosmart.restro.addmenu");
                            intent.putExtra("menuname", arrayList.get(getAdapterPosition()).getMenuName());
                            mContext.sendBroadcast(intent);
                            dialog.dismiss();*//*
                        }
                    });

                    tvAddQty.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            *//*FoodMenuModel foodMenuModel = arrayList.get(getAdapterPosition());
                            sessionmanager.updateQtyFoodCart(mContext, 1, foodMenuModel.getMenuId());*//*

                            tvMenuQty.setText(String.valueOf(Integer.parseInt(tvMenuQty.getText().toString()) + 1));
                        }
                    });

                    tvMinusQty.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (Integer.parseInt(tvMenuQty.getText().toString()) == 1) {
                                tvMenuQty.setText(String.valueOf(1));

                                *//*FoodMenuModel foodMenuModel1 = arrayList.get(getAdapterPosition());
                                sessionmanager.removeMenuFoodCart(mContext, foodMenuModel1.getMenuId());

                                Intent intent = new Intent("com.restrosmart.restro.addmenu");
                                intent.putExtra("menuname", arrayList.get(getAdapterPosition()).getMenuName());
                                mContext.sendBroadcast(intent);*//*
                            } else {
                                *//*FoodMenuModel foodMenuModel = arrayList.get(getAdapterPosition());
                                sessionmanager.updateQtyFoodCart(mContext, 0, foodMenuModel.getMenuId());*//*
                                tvMenuQty.setText(String.valueOf(Integer.parseInt(tvMenuQty.getText().toString()) - 1));
                            }
                        }
                    });
                }
            });*/
