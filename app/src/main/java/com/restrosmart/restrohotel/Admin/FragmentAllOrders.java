package com.restrosmart.restrohotel.Admin;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.ViewPagerAdapter;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.MenuForm;
import com.restrosmart.restrohotel.Model.OrderModel;
import com.restrosmart.restrohotel.Model.ToppingsForm;
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
import static com.restrosmart.restrohotel.ConstantVariables.ORDER_DETAILS;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;


public class FragmentAllOrders extends Fragment {

    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private String user_type;

    private ArrayList<OrderModel> arrayListOder;
    private ArrayList<MenuForm> arrayListMenu;
    private  ArrayList<ToppingsForm>  arrayListToppings;

    private ArrayList<OrderModel> arrayListOderAccepetd;
    private ArrayList<MenuForm> arrayListMenuAccepetd;
    private  ArrayList<ToppingsForm>  arrayListToppingsAccepetd;

    private ArrayList<OrderModel> arrayListOderCompleted;
    private ArrayList<MenuForm> arrayListMenuCompleted;
    private  ArrayList<ToppingsForm>  arrayListToppingsCompleted;

    private ArrayList<OrderModel> arrayListOderCancel;
    private ArrayList<MenuForm> arrayListMenuCancel;
    private  ArrayList<ToppingsForm>  arrayListToppingsCancel;

    private  RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private  Sessionmanager sessionmanager;
    private  int hotelId,branchId;
    private  OrderModel orderModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        user_type = getArguments().getString("role");
        View view = inflater.inflate(R.layout.fragment_all_orders, null);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
        sessionmanager = new Sessionmanager(getActivity());
        HashMap<String, String> name_info = sessionmanager.getHotelDetails();
        hotelId = Integer.parseInt(name_info.get(HOTEL_ID));

        //callViewAdapter();

        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(ORDER_DETAILS, (service.Order(hotelId)));

    }

    private void initRetrofitCallback() {
        mResultCallBack=new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject object=response.body();
                String orderInfo=object.toString();

                try {

                    JSONObject jsonObjectOrder=new JSONObject(orderInfo);
                    int status=jsonObjectOrder.getInt("status");

                    if(status==1) {
                        //  JSONArray jsonArray = jsonObjectOrder.getJSONArray("Orders");
                        // Oder for new Order
                        arrayListOder.clear();
                        //  for (int order = 0; order < jsonArray.length(); order++) {
                        //  JSONObject jsonObjectOrderinfo = jsonArray.getJSONObject(order);
                        if (jsonObjectOrder.has("NewOngoingOrder")) {
                            JSONArray jsonArrayOrder = jsonObjectOrder.getJSONArray("NewOngoingOrder");

                            arrayListOder.clear();
                            arrayListOder = new ArrayList<>();

                            for (int i = 0; i < jsonArrayOrder.length(); i++) {
                                JSONObject jsonObjectCustD = jsonArrayOrder.getJSONObject(i);
                                orderModel = new OrderModel();
                                orderModel.setOrder_id(jsonObjectCustD.getInt("Order_Id"));
                                orderModel.setCust_mob_no(jsonObjectCustD.getInt("Cust_Mob"));
                                orderModel.setTableId(jsonObjectCustD.getInt("Table_Id"));
                                orderModel.setOrder_Status_Name(jsonObjectCustD.getString("Order_Status_Name"));
                                // orderModel.setArrayList();


                                JSONArray jsonArrayMenu = jsonObjectCustD.getJSONArray("order");
                                arrayListMenu = new ArrayList<>();
                                for (int in = 0; in < jsonArrayMenu.length(); in++) {
                                    JSONObject jsonObjectMenu = jsonArrayMenu.getJSONObject(in);
                                    MenuForm menuForm = new MenuForm();
                                    menuForm.setMenuName(jsonObjectMenu.getString("menuName"));
                                    menuForm.setLiqMLQty(jsonObjectMenu.getString("liqMLQty"));
                                    menuForm.setMenuDisp(jsonObjectMenu.getString("orderMsg"));
                                    menuForm.setMenuPrice(jsonObjectMenu.getInt("menuPrice"));
                                    menuForm.setMenuQty(jsonObjectMenu.getInt("menuQty"));


                                    JSONArray jsonArrayToppings = jsonObjectMenu.getJSONArray("Topping");
                                    arrayListToppings = new ArrayList<>();
                                    for (int it = 0; it < jsonArrayToppings.length(); it++) {
                                        JSONObject jsonObjectToppings = jsonArrayToppings.getJSONObject(it);
                                        ToppingsForm toppingsForm = new ToppingsForm();
                                        toppingsForm.setToppingId(jsonObjectToppings.getInt("topId"));
                                        toppingsForm.setToppingsName(jsonObjectToppings.getString("topName"));
                                        arrayListToppings.add(toppingsForm);
                                    }

                                    menuForm.setArrayListToppings(arrayListToppings);
                                    arrayListMenu.add(menuForm);

                                }


                                orderModel.setArrayList(arrayListMenu);
                                arrayListOder.add(orderModel);
                            }
                        }

                           /* JSONArray jsonArrayOrderAccepted = jsonObjectOrderinfo.getJSONArray("Accepted");
                            JSONArray jsonArrayOrderCompleted = jsonObjectOrderinfo.getJSONArray("Completed");
                            JSONArray jsonArrayOrderCancel = jsonObjectOrderinfo.getJSONArray("Cancel");
*/
                        //  arrayListOderAccepetd.clear();

                        // order for  Accepted
                        arrayListOderAccepetd.clear();
                        if (jsonObjectOrder.has("CompletCancelorder")) {
                            JSONArray jsonArrayAccepeted = jsonObjectOrder.getJSONArray("CompletCancelorder");
                            arrayListOderAccepetd.clear();
                            arrayListOderAccepetd = new ArrayList<>();

                            for (int i = 0; i < jsonArrayAccepeted.length(); i++) {
                                JSONObject jsonObjectCustD = jsonArrayAccepeted.getJSONObject(i);
                                orderModel = new OrderModel();
                                orderModel.setOrder_id(jsonObjectCustD.getInt("Order_Id"));
                                orderModel.setCust_mob_no(jsonObjectCustD.getInt("Cust_Mob"));
                                orderModel.setTableId(jsonObjectCustD.getInt("Table_Id"));
                                orderModel.setOrder_Status_Name(jsonObjectCustD.getString("Order_Status_Name"));

                                // orderModel.setArrayList();


                                JSONArray jsonArrayMenu = jsonObjectCustD.getJSONArray("order");
                                arrayListMenuAccepetd = new ArrayList<>();
                                for (int in = 0; in < jsonArrayMenu.length(); in++) {
                                    JSONObject jsonObjectMenu = jsonArrayMenu.getJSONObject(in);
                                    MenuForm menuForm = new MenuForm();
                                    menuForm.setMenuName(jsonObjectMenu.getString("menuName"));
                                    menuForm.setLiqMLQty(jsonObjectMenu.getString("liqMLQty"));
                                    menuForm.setMenuDisp(jsonObjectMenu.getString("orderMsg"));
                                    menuForm.setMenuPrice(jsonObjectMenu.getInt("menuPrice"));
                                    menuForm.setMenuQty(jsonObjectMenu.getInt("menuQty"));


                                    JSONArray jsonArrayToppings = jsonObjectMenu.getJSONArray("Topping");
                                    arrayListToppingsAccepetd = new ArrayList<>();
                                    for (int it = 0; it < jsonArrayToppings.length(); it++) {
                                        JSONObject jsonObjectToppings = jsonArrayToppings.getJSONObject(it);
                                        ToppingsForm toppingsForm = new ToppingsForm();
                                        toppingsForm.setToppingId(jsonObjectToppings.getInt("topId"));
                                        toppingsForm.setToppingsName(jsonObjectToppings.getString("topName"));
                                        arrayListToppingsAccepetd.add(toppingsForm);
                                    }

                                    menuForm.setArrayListToppings(arrayListToppingsAccepetd);
                                    arrayListMenuAccepetd.add(menuForm);

                                }
                                orderModel.setArrayList(arrayListMenuAccepetd);
                                arrayListOderAccepetd.add(orderModel);
                            }
                        }else {

                        }
                        callViewAdapter();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("","requestId"+requestId);
                Log.d("","retrofitError"+error);
            }
        };
    }

    private void callViewAdapter() {
        tabLayout.addTab(tabLayout.newTab().setText("New Orders").setIcon(R.drawable.ic_order_accepted));
        tabLayout.addTab(tabLayout.newTab().setText("Past Orders").setIcon(R.drawable.ic_order_served));

        tabLayout.setTabGravity(TabLayout.MODE_SCROLLABLE);
        tabLayout.setBackground(getContext().getResources().getDrawable(R.drawable.login_bg));
        viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
        viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount(), user_type,arrayListOder,arrayListOderAccepetd);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }

    private void init() {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        tabLayout = (TabLayout) getActivity().findViewById(R.id.tablayout);
        arrayListOder=new ArrayList<>();
        arrayListMenu=new ArrayList<>();
        arrayListToppings=new ArrayList<>();

        arrayListOderAccepetd=new ArrayList<>();
        arrayListMenuAccepetd=new ArrayList<>();
        arrayListToppingsAccepetd=new ArrayList<>();

        arrayListOderCompleted=new ArrayList<>();
        arrayListMenuCompleted=new ArrayList<>();
        arrayListToppingsCompleted=new ArrayList<>();

        arrayListOderCancel=new ArrayList<>();
        arrayListMenuCancel=new ArrayList<>();
        arrayListToppingsCancel=new ArrayList<>();

    }


}
