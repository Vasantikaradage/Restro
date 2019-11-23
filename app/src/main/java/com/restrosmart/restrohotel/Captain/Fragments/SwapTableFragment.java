package com.restrosmart.restrohotel.Captain.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Captain.Adapters.RVSwapBookedAreaAdapter;
import com.restrosmart.restrohotel.Captain.Adapters.RVSwapBookedTablesAdapter;
import com.restrosmart.restrohotel.Captain.Models.AreaSwapModel;
import com.restrosmart.restrohotel.Captain.Models.TableSwapModel;
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

import static com.restrosmart.restrohotel.ConstantVariables.GET_BOOKED_TABLE;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.EMP_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class SwapTableFragment extends Fragment {

    private View view;
    private RecyclerView rvTables;
    private SpinKitView skLoading;
    private LinearLayout llNoTables;
    private RVSwapBookedAreaAdapter rvOrderTablesAdapter;

    private Sessionmanager mSessionmanager;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;

    private ArrayList<AreaSwapModel> areaSwapModelArrayList, freeAreaSwapArrayList;
    private ArrayList<TableSwapModel> tableSwapModelArrayList, freeTableSwapArrayList;
    private HashMap<String, String> capDetails, hotelDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_swap_table, container, false);

        init();
        capDetails = mSessionmanager.getCaptainDetails();
        hotelDetails = mSessionmanager.getHotelDetails();
        getBookedTable();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getContext().registerReceiver(mSwapTableReceiver, new IntentFilter("com.restrohotel.captain.swap.table"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        getContext().unregisterReceiver(mSwapTableReceiver);
        //rvOrderTablesAdapter.onDestroyFragment();
    }

    BroadcastReceiver mSwapTableReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Tables swapped successfully", Toast.LENGTH_SHORT).show();
            getBookedTable();
        }
    };

    private void getBookedTable() {
        initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getContext());
        mRetrofitService.retrofitData(GET_BOOKED_TABLE, (service.getBookedTable(Integer.parseInt(hotelDetails.get(HOTEL_ID)), Integer.parseInt(capDetails.get(EMP_ID)))));
    }

    private void initRetrofitCallBack() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String mParentSubcategory = jsonObject.toString();

                try {
                    JSONObject object = new JSONObject(mParentSubcategory);
                    int status = object.getInt("status");
                    //String msg = object.getString("message");

                    if (status == 1) {
                        areaSwapModelArrayList.clear();
                        freeAreaSwapArrayList.clear();

                        if (object.has("bookedtable")) {
                            JSONArray jsonArray = object.getJSONArray("bookedtable");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                tableSwapModelArrayList = new ArrayList<>();
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                JSONArray jsonArray1 = jsonObject2.getJSONArray("tables");

                                for (int j = 0; j < jsonArray1.length(); j++) {
                                    JSONObject jsonObject3 = jsonArray1.getJSONObject(j);

                                    TableSwapModel tableSwapModel = new TableSwapModel();
                                    tableSwapModel.setTableId(jsonObject3.getInt("Table_Id"));
                                    tableSwapModel.setCustId(jsonObject3.getString("Cust_Id"));

                                    if (jsonObject3.has("Order_Id") && !jsonObject3.isNull("Order_Id")) {
                                        tableSwapModel.setOrderId(jsonObject3.getInt("Order_Id"));
                                    }

                                    tableSwapModelArrayList.add(tableSwapModel);
                                }

                                AreaSwapModel areaSwapModel = new AreaSwapModel();
                                areaSwapModel.setAreaName(jsonObject2.getString("Area_Name"));
                                areaSwapModel.setTableSwapModelArrayList(tableSwapModelArrayList);

                                areaSwapModelArrayList.add(areaSwapModel);
                            }
                        }

                        if (object.has("freetable")) {
                            JSONArray jsonArray1 = object.getJSONArray("freetable");

                            for (int i = 0; i < jsonArray1.length(); i++) {
                                freeTableSwapArrayList = new ArrayList<>();
                                JSONObject jsonObject2 = jsonArray1.getJSONObject(i);
                                JSONArray jsonArray2 = jsonObject2.getJSONArray("tables");

                                for (int j = 0; j < jsonArray2.length(); j++) {
                                    JSONObject jsonObject3 = jsonArray2.getJSONObject(j);

                                    TableSwapModel tableSwapModel = new TableSwapModel();
                                    tableSwapModel.setTableId(jsonObject3.getInt("Table_Id"));

                                    freeTableSwapArrayList.add(tableSwapModel);
                                }

                                AreaSwapModel areaSwapModel = new AreaSwapModel();
                                areaSwapModel.setAreaName(jsonObject2.getString("Area_Name"));
                                areaSwapModel.setTableSwapModelArrayList(freeTableSwapArrayList);

                                freeAreaSwapArrayList.add(areaSwapModel);
                            }
                        }
                        if (areaSwapModelArrayList.size() != 0 && freeAreaSwapArrayList.size() != 0) {
                            rvOrderTablesAdapter = new RVSwapBookedAreaAdapter(getContext(), areaSwapModelArrayList, freeAreaSwapArrayList);
                            rvTables.setHasFixedSize(true);
                            rvTables.setNestedScrollingEnabled(false);
                            rvTables.setLayoutManager(new GridLayoutManager(getContext(), 1));
                            rvTables.setItemAnimator(new DefaultItemAnimator());
                            rvTables.setAdapter(rvOrderTablesAdapter);

                            rvTables.setVisibility(View.VISIBLE);
                            llNoTables.setVisibility(View.GONE);
                        } else {
                            rvTables.setVisibility(View.GONE);
                            llNoTables.setVisibility(View.VISIBLE);
                        }
                    } else {
                        rvTables.setVisibility(View.GONE);
                        llNoTables.setVisibility(View.VISIBLE);
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
        areaSwapModelArrayList = new ArrayList<>();
        freeAreaSwapArrayList = new ArrayList<>();
        mSessionmanager = new Sessionmanager(getContext());

        rvTables = view.findViewById(R.id.rvTables);
        llNoTables = view.findViewById(R.id.llNoTables);
        skLoading = view.findViewById(R.id.skLoading);
    }
}
