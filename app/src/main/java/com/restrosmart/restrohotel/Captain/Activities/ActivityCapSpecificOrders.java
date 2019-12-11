package com.restrosmart.restrohotel.Captain.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Captain.Adapters.CapSpecificOrderRVAdapter;
import com.restrosmart.restrohotel.Captain.Models.CapOrderModel;
import com.restrosmart.restrohotel.Captain.Models.UserCategory;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.CAP_COMPLETE_ORDER;
import static com.restrosmart.restrohotel.ConstantVariables.UNIQUE_KEY;

public class ActivityCapSpecificOrders extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private RecyclerView rvOrders;
    private SpinKitView skLoading;
    private LinearLayout llNoOrders;
    private FloatingActionButton fabAddMenu;

    private ArrayList<CapOrderModel> capOrderModelArrayList;
    private ArrayList<UserCategory> userCategoryArrayList;
    private ArrayList<String> orderIdsArrayList;

    private String title;

    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;
    private Sessionmanager mSessionmanager;

    private int hotelId, tableId, tableNo;
    private String custId, custName, custMob;
    private String orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_specific_orders);

        init();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            title = bundle.getString("title");
            hotelId = bundle.getInt("hotelId");
            custId = bundle.getString("custId");
            custName = bundle.getString("custName");
            custMob = bundle.getString("custMob");
            tableId = bundle.getInt("tableId");
            tableNo = bundle.getInt("tableNo");
            capOrderModelArrayList = bundle.getParcelableArrayList("arraylist");
            userCategoryArrayList = bundle.getParcelableArrayList("categoryArraylist");
        }
        setupToolBar();

        if (capOrderModelArrayList != null && capOrderModelArrayList.size() > 0) {
            for (int i = 0; i < capOrderModelArrayList.size(); i++) {
                orderIdsArrayList.add("Order " + (i + 1));
            }

            CapSpecificOrderRVAdapter capSpecificOrderRVAdapter = new CapSpecificOrderRVAdapter(this, hotelId, custId, tableId, tableNo, orderIdsArrayList, capOrderModelArrayList);
            rvOrders.setHasFixedSize(true);
            rvOrders.setNestedScrollingEnabled(false);
            rvOrders.setLayoutManager(new GridLayoutManager(this, 1));
            rvOrders.setItemAnimator(new DefaultItemAnimator());
            rvOrders.setAdapter(capSpecificOrderRVAdapter);

            rvOrders.setVisibility(View.VISIBLE);
            llNoOrders.setVisibility(View.GONE);
        } else {
            rvOrders.setVisibility(View.GONE);
            llNoOrders.setVisibility(View.VISIBLE);
        }

        skLoading.setVisibility(View.GONE);
        fabAddMenu.setOnClickListener(this);

        rvOrders.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fabAddMenu.getVisibility() == View.VISIBLE) {
                    fabAddMenu.hide();
                } else if (dy < 0 && fabAddMenu.getVisibility() != View.VISIBLE) {
                    fabAddMenu.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cap_order_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.complete_order) {
            if (capOrderModelArrayList.size() > 0) {
                showAlertDialog();
            } else {
                Toast.makeText(this, "No order placed yet", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabAddMenu) {
            //Todo remove below code
            mSessionmanager.deleteCustDetails();
            mSessionmanager.resetCartCount();
            mSessionmanager.deleteOrderID();

            mSessionmanager.saveCustDetails(custId, custName, custMob, tableId, tableNo);

            Intent intent = new Intent(ActivityCapSpecificOrders.this, ActivityHotelMenu.class);
            intent.putExtra("categoryPos", 0);
            intent.putExtra("categoryId", userCategoryArrayList.get(0).getCategoryId());
            intent.putExtra("categoryName", userCategoryArrayList.get(0).getCategoryName());
            intent.putParcelableArrayListExtra("arrayList", userCategoryArrayList);
            startActivity(intent);
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setMessage("Are you sure you want to complete the order and make a bill?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        completeOrder();
                        dialog.dismiss();
                    }
                });

        builder.setNegativeButton(
                android.R.string.no,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void completeOrder() {
        orderList = getOrderIdList();

        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, this);
        mRetrofitService.retrofitData(CAP_COMPLETE_ORDER, (service.capCompleteOrder(hotelId, tableId, custId, orderList, UNIQUE_KEY)));
    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObjectResponse = response.body();
                String responseValue = jsonObjectResponse.toString();

                if (requestId == CAP_COMPLETE_ORDER) {
                    try {
                        JSONObject jsonObject = new JSONObject(responseValue);

                        int status = jsonObject.getInt("status");
                        String msg = jsonObject.getString("message");

                        if (status == 1) {
                            finish();
                            Toast.makeText(ActivityCapSpecificOrders.this, msg, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ActivityCapSpecificOrders.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.v("Retrofit RequestId", String.valueOf(requestId));
                Log.v("Retrofit Error", String.valueOf(error));
            }
        };
    }

    private String getOrderIdList() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < capOrderModelArrayList.size(); i++) {
            try {
                CapOrderModel capOrderModel = capOrderModelArrayList.get(i);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Order_Id", capOrderModel.getOrderId());
                jsonArray.put(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jsonArray.toString();
    }

    private void setupToolBar() {
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        mSessionmanager = new Sessionmanager(this);
        orderIdsArrayList = new ArrayList<>();

        mToolbar = findViewById(R.id.toolbar);
        rvOrders = findViewById(R.id.rvOrders);
        skLoading = findViewById(R.id.skLoading);
        llNoOrders = findViewById(R.id.llNoOrders);
        fabAddMenu = findViewById(R.id.fabAddMenu);
    }
}
