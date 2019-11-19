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

    private ArrayList<OrderModel> arrayListParcelOrder, arrayListTableOrder;
    private ArrayList<MenuModel> arrayListParcelMenu, arrayListTableMenu;
    private ArrayList<ToppingsModel> arrayListParcelToppings, arrayListTableToppings;

    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private Sessionmanager mSessionmanager;

    private HashMap<String, String> hotelDetails;
    private int hotelId;

    private ArrayList<UserCategory> arrayListUserCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_orders);

        init();
        setupToolbar();

        hotelDetails = mSessionmanager.getHotelDetails();
        hotelId = Integer.parseInt(hotelDetails.get(HOTEL_ID));

        getMenuCategories();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void getOrderData() {
        llTabPager.setVisibility(View.VISIBLE);
        skLoading.setVisibility(View.GONE);
        setViewPagerAdapter();

        /*initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, this);
        mRetrofitService.retrofitData(ORDER_DETAILS, (service.getParcelTakeOrders(hotelId)));*/
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

                            if (status == 1) {

                                // Parcel Orders
                                if (jsonObjectOrder.has("NewOngoingOrder")) {
                                    JSONArray jsonArrayOrder = jsonObjectOrder.getJSONArray("NewOngoingOrder");
                                    arrayListParcelOrder.clear();

                                    for (int i = 0; i < jsonArrayOrder.length(); i++) {
                                        JSONObject jsonObjectCustD = jsonArrayOrder.getJSONObject(i);
                                        OrderModel orderModel = new OrderModel();
                                        orderModel.setOrder_id(jsonObjectCustD.getInt("Order_Id"));
                                        orderModel.setCustName(jsonObjectCustD.getString("Cust_Name"));
                                        orderModel.setCustMobNo(jsonObjectCustD.getString("Cust_Mob"));
                                        orderModel.setTime(jsonObjectCustD.getString("Order_Date"));
                                        orderModel.setTableId(jsonObjectCustD.getInt("Table_Id"));

                                        arrayListParcelMenu = new ArrayList<>();
                                        JSONArray jsonArrayMenu = jsonObjectCustD.getJSONArray("order");

                                        for (int j = 0; j < jsonArrayMenu.length(); j++) {
                                            JSONObject jsonObjectMenu = jsonArrayMenu.getJSONObject(j);
                                            MenuModel menuModel = new MenuModel();
                                            menuModel.setMenuName(jsonObjectMenu.getString("menuName"));
                                            menuModel.setLiqMLQty(jsonObjectMenu.getString("liqMLQty"));
                                            menuModel.setMenuDisp(jsonObjectMenu.getString("orderMsg"));
                                            menuModel.setMenuPrice(jsonObjectMenu.getInt("menuPrice"));
                                            menuModel.setMenuQty(jsonObjectMenu.getInt("menuQty"));

                                            arrayListParcelToppings = new ArrayList<>();
                                            JSONArray jsonArrayToppings = jsonObjectMenu.getJSONArray("Topping");

                                            for (int k = 0; k < jsonArrayToppings.length(); k++) {
                                                JSONObject jsonObjectToppings = jsonArrayToppings.getJSONObject(k);
                                                ToppingsModel toppingsModel = new ToppingsModel();
                                                toppingsModel.setToppingsId(jsonObjectToppings.getInt("topId"));
                                                toppingsModel.setToppingsName(jsonObjectToppings.getString("topName"));
                                                arrayListParcelToppings.add(toppingsModel);
                                            }

                                            menuModel.setArrayListToppings(arrayListParcelToppings);
                                            arrayListParcelMenu.add(menuModel);
                                        }

                                        orderModel.setArrayList(arrayListParcelMenu);
                                        arrayListParcelOrder.add(orderModel);
                                    }
                                }

                                // Table order
                                if (jsonObjectOrder.has("CompletCancelorder")) {
                                    JSONArray jsonArrayAccepeted = jsonObjectOrder.getJSONArray("CompletCancelorder");
                                    arrayListTableOrder.clear();

                                    for (int i = 0; i < jsonArrayAccepeted.length(); i++) {
                                        JSONObject jsonObjectCustD = jsonArrayAccepeted.getJSONObject(i);
                                        OrderModel orderModel = new OrderModel();
                                        orderModel.setOrder_id(jsonObjectCustD.getInt("Order_Id"));
                                        orderModel.setCustName(jsonObjectCustD.getString("Cust_Name"));
                                        orderModel.setCustMobNo(jsonObjectCustD.getString("Cust_Mob"));
                                        orderModel.setTime(jsonObjectCustD.getString("Order_Date"));
                                        orderModel.setTableId(jsonObjectCustD.getInt("Table_Id"));

                                        arrayListTableMenu = new ArrayList<>();
                                        JSONArray jsonArrayMenu = jsonObjectCustD.getJSONArray("order");

                                        for (int j = 0; j < jsonArrayMenu.length(); j++) {
                                            JSONObject jsonObjectMenu = jsonArrayMenu.getJSONObject(j);
                                            MenuModel menuModel = new MenuModel();
                                            menuModel.setMenuName(jsonObjectMenu.getString("menuName"));
                                            menuModel.setLiqMLQty(jsonObjectMenu.getString("liqMLQty"));
                                            menuModel.setMenuDisp(jsonObjectMenu.getString("orderMsg"));
                                            menuModel.setMenuPrice(jsonObjectMenu.getInt("menuPrice"));
                                            menuModel.setMenuQty(jsonObjectMenu.getInt("menuQty"));

                                            arrayListTableToppings = new ArrayList<>();
                                            JSONArray jsonArrayToppings = jsonObjectMenu.getJSONArray("Topping");

                                            for (int k = 0; k < jsonArrayToppings.length(); k++) {
                                                JSONObject jsonObjectToppings = jsonArrayToppings.getJSONObject(k);
                                                ToppingsModel toppingsModel = new ToppingsModel();
                                                toppingsModel.setToppingsId(jsonObjectToppings.getInt("topId"));
                                                toppingsModel.setToppingsName(jsonObjectToppings.getString("topName"));
                                                arrayListTableToppings.add(toppingsModel);
                                            }

                                            menuModel.setArrayListToppings(arrayListTableToppings);
                                            arrayListTableMenu.add(menuModel);

                                        }
                                        orderModel.setArrayList(arrayListTableMenu);
                                        arrayListTableOrder.add(orderModel);
                                    }
                                }

                                llTabPager.setVisibility(View.VISIBLE);
                            } else {
                                llTabPager.setVisibility(View.GONE);
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

                                getOrderData();
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
        tabLayout.addTab(tabLayout.newTab().setText("Parcel Orders"));
        tabLayout.addTab(tabLayout.newTab().setText("Table Orders"));

        capViewPagerAdapter = new CapViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), arrayListParcelOrder, arrayListTableOrder, arrayListUserCategory);
        viewPager.setAdapter(capViewPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupToolbar() {
        mToolbar.setTitle("Orders");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void init() {
        mSessionmanager = new Sessionmanager(this);
        arrayListParcelOrder = new ArrayList<>();
        arrayListTableOrder = new ArrayList<>();
        arrayListUserCategory = new ArrayList<>();

        mToolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewPager);
        llTabPager = findViewById(R.id.llTabPager);
        skLoading = findViewById(R.id.skLoading);
    }
}
