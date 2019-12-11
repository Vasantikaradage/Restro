package com.restrosmart.restrohotel.Captain.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Captain.Adapters.CapViewPagerAdapter;
import com.restrosmart.restrohotel.Captain.Fragments.FragmentParcelOrders;
import com.restrosmart.restrohotel.Captain.Fragments.FragmentTableOrders;
import com.restrosmart.restrohotel.Captain.Models.AllOrderModel;
import com.restrosmart.restrohotel.Captain.Models.CapMenuModel;
import com.restrosmart.restrohotel.Captain.Models.CapOrderModel;
import com.restrosmart.restrohotel.Captain.Models.MenuModel;
import com.restrosmart.restrohotel.Captain.Models.OrderModel;
import com.restrosmart.restrohotel.Captain.Models.ToppingsModel;
import com.restrosmart.restrohotel.Captain.Models.UserCategory;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.FreeTables;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.DASHBOARD_CATEGORY;
import static com.restrosmart.restrohotel.ConstantVariables.GET_BOOKED_TABLE;
import static com.restrosmart.restrohotel.ConstantVariables.GET_UNBOOKED_TABLE;
import static com.restrosmart.restrohotel.ConstantVariables.ORDER_DETAILS;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.EMP_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class ActivityCapOrders extends AppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CapViewPagerAdapter capViewPagerAdapter;

    private LinearLayout llTabPager;
    private SpinKitView skLoading;

    private ArrayList<AllOrderModel> arrayListParcelAllOrder, arrayListTableAllOrder;
    private ArrayList<CapOrderModel> arrayListParcelOrder, arrayListTableOrder;
    private ArrayList<CapMenuModel> arrayListParcelMenu, arrayListTableMenu;
    private ArrayList<ToppingsModel> arrayListParcelToppings, arrayListTableToppings;

    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private Sessionmanager mSessionmanager;

    private HashMap<String, String> hotelDetails;
    private int hotelId;
    private DecimalFormat df2;



    private ArrayList<UserCategory> arrayListUserCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_orders);

        init();
        setupToolbar();

        hotelDetails = mSessionmanager.getHotelDetails();
        hotelId = Integer.parseInt(hotelDetails.get(HOTEL_ID));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mSessionmanager.setTabposition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMenuCategories();
        getOrderData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSessionmanager.setTabposition(0);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void getOrderData() {
        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, this);
        mRetrofitService.retrofitData(ORDER_DETAILS, (service.getHaveParcelOrders(hotelId)));
    }

    private void getMenuCategories() {
        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityCapOrders.this);
        mRetrofitService.retrofitData(DASHBOARD_CATEGORY, (service.getCategory(hotelId)));
    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject object = response.body();
                String responseString = object.toString();

                switch (requestId) {
                    case ORDER_DETAILS:
                        try {
                            JSONObject jsonObjectOrder = new JSONObject(responseString);
                            int status = jsonObjectOrder.getInt("status");
                            String msg = jsonObjectOrder.getString("message");

                            if (status == 1) {

                                // Parcel Orders
                                if (jsonObjectOrder.has("takeaway")) {
                                    JSONArray jsonArrayOrder = jsonObjectOrder.getJSONArray("takeaway");
                                    arrayListParcelAllOrder.clear();

                                    for (int i = 0; i < jsonArrayOrder.length(); i++) {

                                        JSONObject jsonObjectTakeAway = jsonArrayOrder.getJSONObject(i);
                                        AllOrderModel allOrderModel = new AllOrderModel();
                                        allOrderModel.setCustId(jsonObjectTakeAway.getString("CustId"));
                                        allOrderModel.setCustName(jsonObjectTakeAway.getString("CustName"));
                                        allOrderModel.setCustMob(jsonObjectTakeAway.getString("CustMob"));
                                        allOrderModel.setTableId(jsonObjectTakeAway.getInt("TableId"));
                                        allOrderModel.setTableNo(jsonObjectTakeAway.getInt("TableNo"));

                                        arrayListParcelOrder = new ArrayList<>();
                                        JSONArray jsonArrayODetails = jsonObjectTakeAway.getJSONArray("allorders");

                                        for (int j = 0; j < jsonArrayODetails.length(); j++) {
                                            JSONObject jsonObjectOders = jsonArrayODetails.getJSONObject(j);
                                            CapOrderModel capOrderModel = new CapOrderModel();
                                            capOrderModel.setOrderId(jsonObjectOders.getInt("Order_Id"));
                                            capOrderModel.setOrderDate(jsonObjectOders.getString("Order_Date"));
                                            capOrderModel.setOrderStatus(jsonObjectOders.getString("Order_Status_Name"));
                                            capOrderModel.setSubTotal(Float.parseFloat(df2.format(Float.parseFloat(jsonObjectOders.getString("subtotal")))));

                                            arrayListParcelMenu = new ArrayList<>();
                                            JSONArray jsonArrayMenu = jsonObjectOders.getJSONArray("order");

                                            for (int k = 0; k < jsonArrayMenu.length(); k++) {
                                                JSONObject jsonObjectMenu = jsonArrayMenu.getJSONObject(k);

                                                CapMenuModel capMenuModel = new CapMenuModel();
                                                capMenuModel.setOrderDetailId(jsonObjectMenu.getInt("Order_Detail_Id"));
                                                capMenuModel.setMenuId(jsonObjectMenu.getString("menuId"));
                                                capMenuModel.setMenuName(jsonObjectMenu.getString("menuName"));
                                                capMenuModel.setLiqMLQty(jsonObjectMenu.getString("liqMLQty"));
                                                capMenuModel.setMenuPrice(Float.parseFloat(df2.format(Float.parseFloat(jsonObjectMenu.getString("menuPrice")))));
                                                capMenuModel.setMenuQty(jsonObjectMenu.getInt("menuQty"));

                                                arrayListParcelToppings = new ArrayList<>();
                                                JSONArray jsonArrayToppings = jsonObjectMenu.getJSONArray("Topping");

                                                for (int l = 0; l < jsonArrayToppings.length(); l++) {
                                                    JSONObject jsonObjectToppings = jsonArrayToppings.getJSONObject(l);
                                                    ToppingsModel toppingsModel = new ToppingsModel();
                                                    toppingsModel.setToppingsId(jsonObjectToppings.getInt("topId"));
                                                    toppingsModel.setToppingsName(jsonObjectToppings.getString("topName"));
                                                    arrayListParcelToppings.add(toppingsModel);
                                                }

                                                capMenuModel.setToppingsModelArrayList(arrayListParcelToppings);
                                                arrayListParcelMenu.add(capMenuModel);
                                            }

                                            capOrderModel.setCapMenuModelArrayList(arrayListParcelMenu);
                                            arrayListParcelOrder.add(capOrderModel);
                                        }

                                        allOrderModel.setCapOrderModelArrayList(arrayListParcelOrder);
                                        arrayListParcelAllOrder.add(allOrderModel);
                                    }
                                }

                                // Table order
                                if (jsonObjectOrder.has("Havinghere")) {
                                    JSONArray jsonArrayOrder = jsonObjectOrder.getJSONArray("Havinghere");
                                    arrayListTableAllOrder.clear();

                                    for (int i = 0; i < jsonArrayOrder.length(); i++) {

                                        JSONObject jsonObjectTakeAway = jsonArrayOrder.getJSONObject(i);
                                        AllOrderModel allOrderModel = new AllOrderModel();
                                        allOrderModel.setCustId(jsonObjectTakeAway.getString("CustId"));
                                        allOrderModel.setCustName(jsonObjectTakeAway.getString("CustName"));
                                        allOrderModel.setCustMob(jsonObjectTakeAway.getString("CustMob"));
                                        allOrderModel.setTableId(jsonObjectTakeAway.getInt("TableId"));
                                        allOrderModel.setTableNo(jsonObjectTakeAway.getInt("TableNo"));

                                        arrayListTableOrder = new ArrayList<>();
                                        JSONArray jsonArrayODetails = jsonObjectTakeAway.getJSONArray("allorders");

                                        for (int j = 0; j < jsonArrayODetails.length(); j++) {
                                            JSONObject jsonObjectOders = jsonArrayODetails.getJSONObject(j);
                                            CapOrderModel capOrderModel = new CapOrderModel();
                                            capOrderModel.setOrderId(jsonObjectOders.getInt("Order_Id"));
                                            capOrderModel.setOrderDate(jsonObjectOders.getString("Order_Date"));
                                            capOrderModel.setOrderStatus(jsonObjectOders.getString("Order_Status_Name"));
                                            capOrderModel.setSubTotal(Float.parseFloat(df2.format(Float.parseFloat(jsonObjectOders.getString("subtotal")))));

                                            arrayListTableMenu = new ArrayList<>();
                                            JSONArray jsonArrayMenu = jsonObjectOders.getJSONArray("order");

                                            for (int k = 0; k < jsonArrayMenu.length(); k++) {
                                                JSONObject jsonObjectMenu = jsonArrayMenu.getJSONObject(k);

                                                CapMenuModel capMenuModel = new CapMenuModel();
                                                capMenuModel.setOrderDetailId(jsonObjectMenu.getInt("Order_Detail_Id"));
                                                capMenuModel.setMenuId(jsonObjectMenu.getString("menuId"));
                                                capMenuModel.setMenuName(jsonObjectMenu.getString("menuName"));
                                                capMenuModel.setLiqMLQty(jsonObjectMenu.getString("liqMLQty"));
                                                capMenuModel.setMenuPrice(Float.parseFloat(df2.format(Float.parseFloat(jsonObjectMenu.getString("menuPrice")))));
                                                capMenuModel.setMenuQty(jsonObjectMenu.getInt("menuQty"));

                                                arrayListTableToppings = new ArrayList<>();
                                                JSONArray jsonArrayToppings = jsonObjectMenu.getJSONArray("Topping");

                                                for (int l = 0; l < jsonArrayToppings.length(); l++) {
                                                    JSONObject jsonObjectToppings = jsonArrayToppings.getJSONObject(l);
                                                    ToppingsModel toppingsModel = new ToppingsModel();
                                                    toppingsModel.setToppingsId(jsonObjectToppings.getInt("topId"));
                                                    toppingsModel.setToppingsName(jsonObjectToppings.getString("topName"));
                                                    arrayListTableToppings.add(toppingsModel);
                                                }

                                                capMenuModel.setToppingsModelArrayList(arrayListTableToppings);
                                                arrayListTableMenu.add(capMenuModel);
                                            }

                                            capOrderModel.setCapMenuModelArrayList(arrayListTableMenu);
                                            arrayListTableOrder.add(capOrderModel);
                                        }

                                        allOrderModel.setCapOrderModelArrayList(arrayListTableOrder);
                                        arrayListTableAllOrder.add(allOrderModel);
                                    }
                                }

                                //llTabPager.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(ActivityCapOrders.this, msg, Toast.LENGTH_SHORT).show();
                                //llTabPager.setVisibility(View.GONE);
                            }

                            skLoading.setVisibility(View.GONE);
                            setViewPagerAdapter();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case DASHBOARD_CATEGORY:
                        try {
                            JSONObject jsonObject1 = new JSONObject(responseString);

                            int status = jsonObject1.getInt("status");
                            if (status == 1) {
                                arrayListUserCategory.clear();

                                JSONArray jsonArray = jsonObject1.getJSONArray("category");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                                    UserCategory userCategory = new UserCategory();
                                    userCategory.setCategoryId(jsonObject2.getInt("Pc_Id"));
                                    userCategory.setCategoryImage(jsonObject2.getString("Image_Name"));
                                    userCategory.setCategoryName(jsonObject2.getString("Name"));
                                    arrayListUserCategory.add(userCategory);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.v("Retrofit RequestId", String.valueOf(requestId));
                Log.v("Retrofit Error", String.valueOf(error));
            }
        };
    }

    private void setViewPagerAdapter() {
        tabLayout.removeAllTabs();

        capViewPagerAdapter = new CapViewPagerAdapter(getSupportFragmentManager(), arrayListParcelAllOrder, arrayListTableAllOrder, arrayListUserCategory);
        capViewPagerAdapter.addFragment(new FragmentParcelOrders(), "Parcel Orders");
        capViewPagerAdapter.addFragment(new FragmentTableOrders(), "Table Orders");

        viewPager.setAdapter(capViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(mSessionmanager.getTabposition());
    }

    private void setupToolbar() {
        mToolbar.setTitle("Orders");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void init() {
        mSessionmanager = new Sessionmanager(this);
        df2 = new DecimalFormat(".##");
        arrayListParcelAllOrder = new ArrayList<>();
        arrayListTableAllOrder = new ArrayList<>();
        arrayListUserCategory = new ArrayList<>();

        mToolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewPager);
        llTabPager = findViewById(R.id.llTabPager);
        skLoading = findViewById(R.id.skLoading);
    }
}
