package com.restrosmart.restrohotel.Captain.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.GET_TABLE_ORDERS;

public class ActivityTableOrders extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView rvTableOrders;

    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;

    private int mOrderId, mTableId, mCustId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_orders);

        init();
        setupToolBar();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mOrderId = bundle.getInt("orderId");
            mTableId = bundle.getInt("tableId");
            mCustId = bundle.getInt("custId");
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
        mRetrofitService.retrofitData(GET_TABLE_ORDERS, (service.getTableOrders(1, mTableId, mCustId)));
    }

    private void initRetrofitCallBack() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String mResponseString = jsonObject.toString();

                try {
                    JSONObject object = new JSONObject(mResponseString);
                    int status = object.getInt("status");
                    String msg = object.getString("message");

                    if (status == 1) {

                        JSONArray jsonArrayOrderDetails = object.getJSONArray("Orderdetail");

                        for (int i = 0; i < jsonArrayOrderDetails.length(); i++) {

                            JSONObject jsonObject1 = jsonArrayOrderDetails.getJSONObject(i);

                            int orderId = object.getInt("Order_Id");
                            String orderDate = object.getString("Order_Date");

                            JSONArray jsonArrayOrder = jsonObject1.getJSONArray("order");

                            for (int j = 0; j < jsonArrayOrder.length(); j++) {
                                JSONObject jsonObjectOrder = jsonArrayOrder.getJSONObject(j);

                                jsonObjectOrder.getInt("Order_Detail_Id");
                                jsonObjectOrder.getInt("menuId");
                                jsonObjectOrder.getString("menuName");
                                jsonObjectOrder.getInt("liqMLQty");
                                jsonObjectOrder.getString("menuPrice");
                                jsonObjectOrder.getInt("menuQty");

                                JSONArray jsonArrayTopping = jsonObjectOrder.getJSONArray("Topping");
                                for (int k = 0; k < jsonArrayTopping.length(); k++) {
                                    JSONObject jsonObjectTopping = jsonArrayTopping.getJSONObject(k);

                                    jsonObjectTopping.getInt("menuId");
                                    jsonObjectTopping.getString("menuName");
                                    jsonObjectTopping.getString("menuPrice");
                                    jsonObjectTopping.getInt("menuQty");
                                }
                            }
                        }

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

    private void init() {
        mToolbar = findViewById(R.id.toolbar);
        rvTableOrders = findViewById(R.id.rvTableOrders);
    }
}
