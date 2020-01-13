package com.restrosmart.restrohotel.Captain.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Captain.Adapters.AllOrdersRVAdapter;
import com.restrosmart.restrohotel.Captain.Models.OrderStatusOrderList;
import com.restrosmart.restrohotel.Captain.Models.OrderStatusOrders;
import com.restrosmart.restrohotel.Captain.Models.ToppingsModel;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.GET_TABLE_ORDERS;
import static com.restrosmart.restrohotel.ConstantVariables.UNIQUE_KEY;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class ActivityTableOrders extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView rvTableOrders;
    private SpinKitView skLoading;
    private LinearLayout llNoOrders;

    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private Sessionmanager mSessionmanager;

    private HashMap<String, String> hotelDetails;

    private ArrayList<OrderStatusOrderList> mOrderStatusOrderListArrayList;
    private ArrayList<OrderStatusOrders> mOrderStatusOrdersArrayList;
    private ArrayList<ToppingsModel> mToppingsModelArrayList;
    private ArrayList<String> mOrderIdArrayList;

    private int mOrderId, mTableId, hotelId;
    private String mCustId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_orders);

        init();
        setupToolBar();

        hotelDetails = mSessionmanager.getHotelDetails();
        hotelId = Integer.parseInt(hotelDetails.get(HOTEL_ID));

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mOrderId = bundle.getInt("orderId");
            mTableId = bundle.getInt("tableId");
            mCustId = bundle.getString("custId");
        }

        getTableOrders();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setupToolBar() {
        mToolbar.setTitle("Table Orders");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void getTableOrders() {
        initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, this);
        mRetrofitService.retrofitData(GET_TABLE_ORDERS, (service.getTableOrders(hotelId, mCustId, UNIQUE_KEY)));
    }

    private void initRetrofitCallBack() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObjectCat = response.body();
                String responseValue = jsonObjectCat.toString();

                try {
                    JSONObject jsonObject = new JSONObject(responseValue);

                    int status = jsonObject.getInt("status");
                    String msg = jsonObject.getString("message");

                    if (status == 1) {
                        mOrderStatusOrderListArrayList.clear();

                        JSONArray jsonArray = jsonObject.getJSONArray("orderlist");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            mOrderStatusOrdersArrayList = new ArrayList<>();

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            mOrderIdArrayList.add("Order " + (i + 1));

                            OrderStatusOrderList orderStatusOrderList = new OrderStatusOrderList();
                            orderStatusOrderList.setoId(jsonObject1.getInt("Order_Id"));
                            orderStatusOrderList.setOrderId("Order " + jsonObject1.getString("Order_Id"));
                            orderStatusOrderList.setOrderStatus(jsonObject1.getString("Order_Status"));

                            JSONArray jsonArrayOrder = jsonObject1.getJSONArray("order");

                            for (int j = 0; j < jsonArrayOrder.length(); j++) {
                                mToppingsModelArrayList = new ArrayList<>();
                                JSONObject jsonObjectOrders = jsonArrayOrder.getJSONObject(j);

                                OrderStatusOrders orderStatusOrders = new OrderStatusOrders();
                                orderStatusOrders.setOrderDetailId(jsonObjectOrders.getString("Order_Detail_Id"));
                                orderStatusOrders.setOrderMenuId(jsonObjectOrders.getString("menuId"));
                                orderStatusOrders.setOrderMenuName(jsonObjectOrders.getString("menuName"));
                                orderStatusOrders.setOrderMenuPrice(Float.parseFloat(jsonObjectOrders.getString("menuPrice")));
                                orderStatusOrders.setTotalMenuPrice(Float.parseFloat(jsonObjectOrders.getString("menuCost")));
                                orderStatusOrders.setOrderMenuQty(jsonObjectOrders.getInt("menuQty"));

                                JSONArray jsonArrayTopping = jsonObjectOrders.getJSONArray("Topping");

                                if (jsonArrayTopping != null && jsonArrayTopping.length() > 0) {
                                    for (int k = 0; k < jsonArrayTopping.length(); k++) {
                                        JSONObject jsonObjectToppings = jsonArrayTopping.getJSONObject(k);

                                        ToppingsModel toppingsModel = new ToppingsModel();
                                        toppingsModel.setToppingsId(jsonObjectToppings.getInt("topId"));
                                        toppingsModel.setToppingsName(jsonObjectToppings.getString("topName"));
                                        toppingsModel.setToppingsPrice(Float.parseFloat(jsonObjectToppings.getString("topPrice")));

                                        mToppingsModelArrayList.add(toppingsModel);
                                    }
                                }

                                orderStatusOrders.setToppingsModelArrayList(mToppingsModelArrayList);
                                mOrderStatusOrdersArrayList.add(orderStatusOrders);
                            }

                            orderStatusOrderList.setOrderStatusOrders(mOrderStatusOrdersArrayList);
                            mOrderStatusOrderListArrayList.add(orderStatusOrderList);
                        }

                        AllOrdersRVAdapter allOrdersRVAdapter = new AllOrdersRVAdapter(ActivityTableOrders.this, hotelId, mOrderIdArrayList, mOrderStatusOrderListArrayList);
                        rvTableOrders.setHasFixedSize(true);
                        rvTableOrders.setNestedScrollingEnabled(false);
                        rvTableOrders.setLayoutManager(new GridLayoutManager(ActivityTableOrders.this, 1));
                        rvTableOrders.setItemAnimator(new DefaultItemAnimator());
                        rvTableOrders.setAdapter(allOrdersRVAdapter);

                        rvTableOrders.setVisibility(View.VISIBLE);
                        llNoOrders.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(ActivityTableOrders.this, msg, Toast.LENGTH_SHORT).show();
                        rvTableOrders.setVisibility(View.GONE);
                        llNoOrders.setVisibility(View.VISIBLE);
                    }

                    skLoading.setVisibility(View.GONE);
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

    private void init() {
        mSessionmanager = new Sessionmanager(this);
        mOrderIdArrayList = new ArrayList<>();
        mOrderStatusOrderListArrayList = new ArrayList<>();

        mToolbar = findViewById(R.id.toolbar);
        rvTableOrders = findViewById(R.id.rvTableOrders);
        skLoading = findViewById(R.id.skLoading);
        llNoOrders = findViewById(R.id.llNoOrders);
    }
}
