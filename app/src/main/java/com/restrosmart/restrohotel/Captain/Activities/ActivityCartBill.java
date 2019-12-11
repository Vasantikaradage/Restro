package com.restrosmart.restrohotel.Captain.Activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Captain.Adapters.FoodCartRVAdapter;
import com.restrosmart.restrohotel.Captain.Adapters.LiquorCartRVAdapter;
import com.restrosmart.restrohotel.Captain.Models.FoodCartModel;
import com.restrosmart.restrohotel.Captain.Models.LiquorCartModel;
import com.restrosmart.restrohotel.Captain.Models.ToppingsModel;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.GET_CART_MENU;
import static com.restrosmart.restrohotel.ConstantVariables.PLACE_ORDER;
import static com.restrosmart.restrohotel.ConstantVariables.UNIQUE_KEY;
import static com.restrosmart.restrohotel.ConstantVariables.WATER_ADD_TO_CART;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.CUST_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.TABLE_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.TABLE_NO;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.WATER_BOTTLE_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.WATER_BOTTLE_IMAGE;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.WATER_BOTTLE_NAME;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.WATER_BOTTLE_PRICE;

public class ActivityCartBill extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView tvSubTotal, tvTotalAmount;
    private RecyclerView rvFoodCart, rvLiquorCart;
    private FoodCartRVAdapter foodCartRVAdapter;
    private LiquorCartRVAdapter liquorCartRVAdapter;
    private LinearLayout llPlaceOrder, llCartEmpty;
    private RelativeLayout rlTotalPayment;
    private NestedScrollView nestedCart;
    private Button btnAddWaterBottle;
    private EditText edtNote;

    private DecimalFormat df2;

    private ProgressDialog progressDialog;

    private ArrayList<FoodCartModel> foodCartModelArrayList;
    private ArrayList<LiquorCartModel> liquorCartModelArrayList;
    private ArrayList<ToppingsModel> toppingsArraylist, allToppingsArraylist, liqToppingsArrayList, allLiqToppingsArraylist;

    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;
    private Sessionmanager mSessionmanager;

    private HashMap<String, String> hotelDetails, userDetails;

    private String waterBottleId, waterBottleName, waterBottleImage, waterBottlePrice;
    private float subTotalAmount, totalAmount = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_bill);

        init();
        setupToolbar();

        userDetails = mSessionmanager.getCustDetails();
        hotelDetails = mSessionmanager.getHotelDetails();

        HashMap<String, String> hashMap = mSessionmanager.getWaterBottleDetail();
        waterBottleId = hashMap.get(WATER_BOTTLE_ID);
        waterBottleName = hashMap.get(WATER_BOTTLE_NAME);
        waterBottleImage = hashMap.get(WATER_BOTTLE_IMAGE);
        waterBottlePrice = hashMap.get(WATER_BOTTLE_PRICE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Calculating total amount...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        getCartMenu();
        //tvSubTotal.setText(String.valueOf(mSessionmanager.getFoodSubTotal(getContext())));

        llPlaceOrder.setOnClickListener(this);
        btnAddWaterBottle.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mRefreshCart, new IntentFilter("com.restrosmart.restro.refreshcart"));
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mRefreshCart);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llPlaceOrder:
                /*AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = null;
                if (layoutInflater != null) {
                    dialogView = layoutInflater.inflate(R.layout.place_order_dialog, null);
                }
                dialogBuilder.setView(dialogView);

                alertDialog = dialogBuilder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();

                Button btnCancelOrder = dialogView.findViewById(R.id.btnCancelOrder);
                Button btnConfirmOrder = dialogView.findViewById(R.id.btnConfirmOrder);

                btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        placeOrder();
                    }
                });

                btnCancelOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });*/

                AlertDialog.Builder alert = new AlertDialog.Builder(ActivityCartBill.this);
                alert.setTitle("Confirmation");
                alert.setMessage("Are you sure? You want to confirm the order?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                        placeOrder();
                    }
                });

                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                alert.show();
                break;

            case R.id.btnAddWaterBottle:

                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Adding to cart...");
                progressDialog.show();
                WaterAddToCart();
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void placeOrder() {
        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, this);
        mRetrofitService.retrofitData(PLACE_ORDER, (service.placeOrder(Integer.parseInt(hotelDetails.get(HOTEL_ID)),
                Integer.parseInt(userDetails.get(TABLE_ID)),
                Integer.parseInt(userDetails.get(TABLE_NO)),
                userDetails.get(CUST_ID),
                mSessionmanager.getOrderID(),
                edtNote.getText().toString(),
                subTotalAmount,
                UNIQUE_KEY)));
    }

    private void getCartMenu() {
        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, this);
        mRetrofitService.retrofitData(GET_CART_MENU, (service.getCartDisplay(Integer.parseInt(hotelDetails.get(HOTEL_ID)),
                mSessionmanager.getOrderID(),
                userDetails.get(CUST_ID),
                Integer.parseInt(userDetails.get(TABLE_ID)),
                UNIQUE_KEY)));
    }

    private void WaterAddToCart() {
        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, this);
        mRetrofitService.retrofitData(WATER_ADD_TO_CART, (service.addToCart(mSessionmanager.getOrderID(),
                Integer.parseInt(userDetails.get(TABLE_ID)),
                Integer.parseInt(userDetails.get(TABLE_NO)),
                userDetails.get(CUST_ID),
                Integer.parseInt(hotelDetails.get(HOTEL_ID)),
                waterBottleId,
                waterBottleName,
                waterBottlePrice, 1,
                "", 0, "", "", "", 0, 0, 1, 7, UNIQUE_KEY)));
    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObjectCat = response.body();
                String responseValue = jsonObjectCat.toString();

                switch (requestId) {

                    case GET_CART_MENU:
                        try {
                            JSONObject jsonObject = new JSONObject(responseValue);

                            int status = jsonObject.getInt("status");
                            String msg = jsonObject.getString("message");

                            if (status == 1) {
                                if (jsonObject.has("Food")) {
                                    JSONArray jsonArrayFood = jsonObject.getJSONArray("Food");
                                    if (jsonArrayFood != null) {
                                        foodCartModelArrayList.clear();

                                        for (int i = 0; i < jsonArrayFood.length(); i++) {
                                            toppingsArraylist = new ArrayList<>();
                                            allToppingsArraylist = new ArrayList<>();

                                            JSONObject jsonObjectFood = jsonArrayFood.getJSONObject(i);

                                            FoodCartModel foodCartModel = new FoodCartModel();

                                            foodCartModel.setOrderDetailId(jsonObjectFood.getInt("Order_Detail_Id"));
                                            foodCartModel.setMenuId(jsonObjectFood.getString("menuId"));
                                            foodCartModel.setMenuName(jsonObjectFood.getString("menuName"));
                                            foodCartModel.setMenuPrice(Float.parseFloat(df2.format(Float.parseFloat(jsonObjectFood.getString("menuPrice")))));
                                            foodCartModel.setMenuQty(jsonObjectFood.getInt("menuQty"));
                                            //foodCartModel.setMenuOrderMsg(jsonObjectFood.getString("orderMsg"));
                                            foodCartModel.setMenuQtyPrice(Float.parseFloat(df2.format(Float.parseFloat(jsonObjectFood.getString("menuQtyPrice")))));

                                            JSONArray jsonArray = jsonObjectFood.getJSONArray("Topping");
                                            if (jsonArray != null) {
                                                for (int j = 0; j < jsonArray.length(); j++) {
                                                    JSONObject jsonObject1 = jsonArray.getJSONObject(j);

                                                    ToppingsModel toppingsModel = new ToppingsModel();
                                                    toppingsModel.setToppingsId(jsonObject1.getInt("topId"));
                                                    toppingsModel.setToppingsName(jsonObject1.getString("topName"));
                                                    toppingsModel.setToppingsPrice(Float.parseFloat(df2.format(Float.parseFloat(jsonObject1.getString("topPrice")))));

                                                    toppingsArraylist.add(toppingsModel);
                                                }
                                            }

                                            JSONArray jsonArrayAll = jsonObjectFood.getJSONArray("allToppingList");
                                            if (jsonArray != null) {
                                                for (int j = 0; j < jsonArrayAll.length(); j++) {
                                                    JSONObject jsonObject1 = jsonArrayAll.getJSONObject(j);

                                                    ToppingsModel toppingsModel = new ToppingsModel();
                                                    toppingsModel.setToppingsId(jsonObject1.getInt("Topping_Id"));
                                                    toppingsModel.setToppingsName(jsonObject1.getString("Topping_Name"));
                                                    toppingsModel.setToppingsPrice(Float.parseFloat(df2.format(Float.parseFloat(jsonObject1.getString("Topping_Price")))));

                                                    allToppingsArraylist.add(toppingsModel);
                                                }
                                            }
                                            foodCartModel.setToppingsModelArrayList(toppingsArraylist);
                                            foodCartModel.setAllToppingsModelArrayList(allToppingsArraylist);
                                            foodCartModelArrayList.add(foodCartModel);
                                        }

                                       /* if (foodCartModelArrayList != null && foodCartModelArrayList.size() > 0) {

                                        }*/
                                    } else {
                                        foodCartModelArrayList.clear();
                                    }
                                } else {
                                    foodCartModelArrayList.clear();
                                }

                                foodCartRVAdapter = new FoodCartRVAdapter(ActivityCartBill.this, foodCartModelArrayList);

                                rvFoodCart.setHasFixedSize(true);
                                rvFoodCart.setNestedScrollingEnabled(false);
                                rvFoodCart.setLayoutManager(new GridLayoutManager(ActivityCartBill.this, 1));
                                rvFoodCart.setItemAnimator(new DefaultItemAnimator());
                                rvFoodCart.setAdapter(foodCartRVAdapter);

                                if (jsonObject.has("Liquor")) {
                                    JSONArray jsonArrayLiquor = jsonObject.getJSONArray("Liquor");

                                    if (jsonArrayLiquor != null) {
                                        liquorCartModelArrayList.clear();

                                        for (int i = 0; i < jsonArrayLiquor.length(); i++) {
                                            liqToppingsArrayList = new ArrayList<>();
                                            allLiqToppingsArraylist = new ArrayList<>();

                                            JSONObject jsonObjectLiquor = jsonArrayLiquor.getJSONObject(i);

                                            LiquorCartModel liquorCartModel = new LiquorCartModel();

                                            liquorCartModel.setOrderDetailId(jsonObjectLiquor.getInt("Order_Detail_Id"));
                                            liquorCartModel.setLiqId(jsonObjectLiquor.getInt("liqId"));
                                            liquorCartModel.setLiqName(jsonObjectLiquor.getString("liqName"));
                                            //liquorCartModel.setLiqOrderMsg(jsonObjectLiquor.getString("orderMsg"));
                                            liquorCartModel.setLiqMLQty(jsonObjectLiquor.getString("liqMLQty"));
                                            liquorCartModel.setLiqPrice(Float.parseFloat(df2.format(Float.parseFloat(jsonObjectLiquor.getString("liqPrice")))));
                                            liquorCartModel.setLiqQty(jsonObjectLiquor.getInt("liqQty"));
                                            liquorCartModel.setLiqQtyPrice(Float.parseFloat(df2.format(Float.parseFloat(jsonObjectLiquor.getString("liqQtyPrice")))));

                                            JSONArray jsonArray = jsonObjectLiquor.getJSONArray("Topping");
                                            if (jsonArray != null) {
                                                for (int j = 0; j < jsonArray.length(); j++) {
                                                    JSONObject jsonObject1 = jsonArray.getJSONObject(j);

                                                    ToppingsModel toppingsModel = new ToppingsModel();
                                                    toppingsModel.setToppingsId(jsonObject1.getInt("topId"));
                                                    toppingsModel.setToppingsName(jsonObject1.getString("topName"));
                                                    toppingsModel.setToppingsPrice(Float.parseFloat(df2.format(Float.parseFloat(jsonObject1.getString("topPrice")))));

                                                    liqToppingsArrayList.add(toppingsModel);
                                                }
                                            }

                                            JSONArray jsonArrayAll = jsonObjectLiquor.getJSONArray("allToppingList");
                                            if (jsonArray != null) {
                                                for (int j = 0; j < jsonArrayAll.length(); j++) {
                                                    JSONObject jsonObject1 = jsonArrayAll.getJSONObject(j);

                                                    ToppingsModel toppingsModel = new ToppingsModel();
                                                    toppingsModel.setToppingsId(jsonObject1.getInt("Topping_Id"));
                                                    toppingsModel.setToppingsName(jsonObject1.getString("Topping_Name"));
                                                    toppingsModel.setToppingsPrice(Float.parseFloat(df2.format(Float.parseFloat(jsonObject1.getString("Topping_Price")))));

                                                    allLiqToppingsArraylist.add(toppingsModel);
                                                }
                                            }
                                            liquorCartModel.setToppingsModelArrayList(liqToppingsArrayList);
                                            liquorCartModel.setAllToppingsModelArrayList(allLiqToppingsArraylist);
                                            liquorCartModelArrayList.add(liquorCartModel);
                                        }

                                        /*if (liquorCartModelArrayList != null && liquorCartModelArrayList.size() > 0) {

                                        }*/
                                    } else {
                                        liquorCartModelArrayList.clear();
                                    }
                                } else {
                                    liquorCartModelArrayList.clear();
                                }

                                liquorCartRVAdapter = new LiquorCartRVAdapter(ActivityCartBill.this, liquorCartModelArrayList);

                                rvLiquorCart.setHasFixedSize(true);
                                rvLiquorCart.setNestedScrollingEnabled(false);
                                rvLiquorCart.setLayoutManager(new GridLayoutManager(ActivityCartBill.this, 1));
                                rvLiquorCart.setItemAnimator(new DefaultItemAnimator());
                                rvLiquorCart.setAdapter(liquorCartRVAdapter);

                                subTotalAmount = Float.parseFloat(df2.format(Float.parseFloat(jsonObject.getString("totalamt"))));
                                tvSubTotal.setText(String.valueOf(subTotalAmount));

                                nestedCart.setVisibility(View.VISIBLE);
                                llPlaceOrder.setVisibility(View.VISIBLE);
                                llCartEmpty.setVisibility(View.GONE);
                            } else {
                                foodCartModelArrayList.clear();
                                liquorCartModelArrayList.clear();
                                nestedCart.setVisibility(View.GONE);
                                llPlaceOrder.setVisibility(View.GONE);
                                llCartEmpty.setVisibility(View.VISIBLE);
                            }

                            if (foodCartModelArrayList.size() == 0 && liquorCartModelArrayList.size() == 0) {
                                mSessionmanager.resetCartCount();
                                mSessionmanager.deleteOrderID();
                            }

                            totalAmount = Float.parseFloat(df2.format(Float.parseFloat(jsonObject.getString("maintotal"))));
                            tvTotalAmount.setText(getResources().getString(R.string.currency) + " " + String.valueOf(totalAmount));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;

                    case WATER_ADD_TO_CART:
                        try {
                            JSONObject jsonObject1 = new JSONObject(responseValue);

                            int status = jsonObject1.getInt("status");
                            String msg = jsonObject1.getString("message");

                            if (status == 1) {
                                getCartMenu();

                                Toast.makeText(ActivityCartBill.this, msg, Toast.LENGTH_SHORT).show();
                                mSessionmanager.saveCartCount();
                                mSessionmanager.saveOrderID(jsonObject1.getInt("Order_Id"));
                                Intent intent = new Intent("com.restrosmart.restro.addmenu");
                                sendBroadcast(intent);
                            } else {
                                Toast.makeText(ActivityCartBill.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case PLACE_ORDER:
                        try {
                            JSONObject jsonObject1 = new JSONObject(responseValue);

                            int status = jsonObject1.getInt("status");
                            String msg = jsonObject1.getString("message");

                            if (status == 1) {
                                Toast.makeText(ActivityCartBill.this, msg, Toast.LENGTH_SHORT).show();
                                mSessionmanager.resetCartCount();
                                mSessionmanager.deleteOrderID();
                                mSessionmanager.deleteCustDetails();
                                finish();
                                ActivityHotelMenu.handler.sendEmptyMessage(0);

                            } else {
                                Toast.makeText(ActivityCartBill.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }

                progressDialog.dismiss();

            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                progressDialog.dismiss();

                Log.v("Retrofit RequestId", String.valueOf(requestId));
                Log.v("Retrofit Error", String.valueOf(error));
            }
        };
    }

    BroadcastReceiver mRefreshCart = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            progressDialog = new ProgressDialog(ActivityCartBill.this);
            progressDialog.setMessage("Calculating total amount...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
            getCartMenu();
        }
    };

    private void setupToolbar() {
        mToolbar.setTitle("Cart");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void init() {
        df2 = new DecimalFormat(".##");
        mSessionmanager = new Sessionmanager(this);
        foodCartModelArrayList = new ArrayList<>();
        liquorCartModelArrayList = new ArrayList<>();

        mToolbar = findViewById(R.id.toolbar);
        tvSubTotal = findViewById(R.id.tvSubTotal);
        rvFoodCart = findViewById(R.id.rvFoodCart);
        rvLiquorCart = findViewById(R.id.rvLiquorCart);
        nestedCart = findViewById(R.id.nestedCart);
        rlTotalPayment = findViewById(R.id.rlTotalPayment);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        llPlaceOrder = findViewById(R.id.llPlaceOrder);
        llCartEmpty = findViewById(R.id.llCartEmpty);
        btnAddWaterBottle = findViewById(R.id.btnAddWaterBottle);
        edtNote = findViewById(R.id.edtNote);
    }
}
