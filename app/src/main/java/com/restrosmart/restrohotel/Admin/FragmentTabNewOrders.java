package com.restrosmart.restrohotel.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.AdapterNewOrder;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.MenuDisplayForm;
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


public class FragmentTabNewOrders extends Fragment {

    private ArrayList<OrderModel> arrayListOder;

    private ArrayList<MenuForm> arrayListMenu;
    private  ArrayList<ToppingsForm>  arrayListToppings;

    private String userType;
    private  Sessionmanager sessionmanager;
    private  int hotelId,branchId;
    private  RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private RecyclerView recyclerView;
    OrderModel orderModel;
    ArrayList<OrderModel> arraylistOrder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // userType = getArguments().getString("userType");
        arraylistOrder  = getArguments().getParcelableArrayList("orderArrayList");

        View view = inflater.inflate(R.layout.tab_orders, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();




        callViewAdapter();
        sessionmanager = new Sessionmanager(getActivity());
        HashMap<String, String> name_info = sessionmanager.getHotelDetails();
        hotelId = Integer.parseInt(name_info.get(HOTEL_ID));





       /* for (int i = 0; i < 4; i++) {

            MenuDisplayForm menu = new MenuDisplayForm();
            menu.setMenu_Name("Dosa");
            //   menu.setMenu_qty("2");
            menu.setBranch_Id(50);
            arrayListMenu.add(menu);
        }


        for (int i = 0; i < arrayListMenu.size(); i++) {

            String qty = "Qty - " + "2";

            OrderModel orderModel = new OrderModel();
            orderModel.setCust_mob_no("9845246171");
            orderModel.setOrder_id("2");
            orderModel.setMenu_name("Dosa");
            orderModel.setMenu_qty(qty);
            orderModel.setMenu_price("40");
            orderModel.setTot_bill("80/-");
            orderModel.setTime("11:00 AM");
            orderModel.setMsg("Sweet");
            arrayListOder.add(orderModel);
        }
*/



        /*} else {

            KitchenAdapter_New_Order kitchenAdapterNewOrder = new KitchenAdapter_New_Order(getActivity(), arrayListOder);
            recyclerView.setAdapter(kitchenAdapterNewOrder);

        }*/

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
                    arrayListOder.clear();
                    if(status==1)
                    {
                        JSONArray jsonArrayOrder=jsonObjectOrder.getJSONArray("New Order");
                         arrayListOder.clear();
                        arrayListOder=new ArrayList<>();

                        for(int i=0;i<jsonArrayOrder.length(); i++)
                        {
                            JSONObject jsonObjectCustD=jsonArrayOrder.getJSONObject(i);
                            orderModel=new OrderModel();
                            orderModel.setOrder_id(jsonObjectCustD.getInt("Order_Id"));
                            orderModel.setCust_mob_no(jsonObjectCustD.getInt("Cust_Mob"));
                            orderModel.setTableId(jsonObjectCustD.getInt("Table_Id"));
                            // orderModel.setArrayList();


                            JSONArray jsonArrayMenu=jsonObjectCustD.getJSONArray("order");
                            arrayListMenu=new ArrayList<>();
                            for(int in=0;in<jsonArrayMenu.length(); in++)
                            {
                                JSONObject jsonObjectMenu=jsonArrayMenu.getJSONObject(in);
                                MenuForm menuForm=new MenuForm();
                                menuForm.setMenuName(jsonObjectMenu.getString("menuName"));
                                menuForm.setMenuDisp(jsonObjectMenu.getString("orderMsg"));
                                menuForm.setMenuPrice(jsonObjectMenu.getInt("menuPrice"));
                                menuForm.setMenuQty(jsonObjectMenu.getInt("menuQty"));


                                JSONArray jsonArrayToppings=jsonObjectMenu.getJSONArray("toppings");
                                arrayListToppings=new ArrayList<>();
                                for(int it=0;it<jsonArrayToppings.length();it++)
                                {
                                    JSONObject jsonObjectToppings=jsonArrayToppings.getJSONObject(it);
                                    ToppingsForm toppingsForm=new ToppingsForm();
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

                        callViewAdapter();


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }

            @Override
            public void notifyError(int requestId, Throwable error) {

            }
        };
    }

    private void callViewAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        // recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);

        // if (userType.equals("Admin")) {
        AdapterNewOrder adapterNewOrder = new AdapterNewOrder(getActivity(), arraylistOrder);
        recyclerView.setAdapter(adapterNewOrder);
    }

    private void init() {
      //  arrayListOder = new ArrayList<OrderModel>();
        arrayListMenu = new ArrayList<>();
        arrayListToppings=new ArrayList<>();
     recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_new_order);

    }
}
