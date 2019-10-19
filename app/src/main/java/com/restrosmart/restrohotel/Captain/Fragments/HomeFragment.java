package com.restrosmart.restrohotel.Captain.Fragments;


import android.app.ProgressDialog;
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
import com.restrosmart.restrohotel.Captain.Adapters.ScanTableRVAdapter;
import com.restrosmart.restrohotel.Captain.Models.AreaTableModel;
import com.restrosmart.restrohotel.Captain.Models.ScanTableModel;
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

import static com.restrosmart.restrohotel.ConstantVariables.SCAN_TABLE;
import static com.restrosmart.restrohotel.ConstantVariables.TABLE_CONF_STATUS;

public class HomeFragment extends Fragment {

    private View view;

    private RecyclerView rvScanTable;
    private LinearLayout llNoTables;
    private SpinKitView skLoading;

    private Sessionmanager mSessionmanager;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;

    private ArrayList<AreaTableModel> areaTableModelArrayList;
    private ArrayList<ScanTableModel> scanTableModelArrayList;

    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        init();
        getScanTable();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getContext().registerReceiver(mTableConfReceiver, new IntentFilter("com.restrohotel.captain.accept.decline.table"));
    }

    @Override
    public void onPause() {
        super.onPause();

        getContext().unregisterReceiver(mTableConfReceiver);
    }

    BroadcastReceiver mTableConfReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();

            if (bundle != null) {
                int mAreaId = bundle.getInt("areaId");
                int mTableId = bundle.getInt("tableId");
                int mTableConfStatus = bundle.getInt("tableConfStatus");
                String mProgressMsg = bundle.getString("progressMsg");

                showProgressDialog(mProgressMsg);
                tableConfStatus(mAreaId, mTableId, mTableConfStatus);
            }
        }
    };

    private void showProgressDialog(String msg) {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    private void tableConfStatus(int areaId, int tableId, int tableConfStatus) {
        initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getContext());
        mRetrofitService.retrofitData(TABLE_CONF_STATUS, (service.scanConfirmTable(1, tableId, areaId, tableConfStatus)));
    }

    private void getScanTable() {
        initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getContext());
        mRetrofitService.retrofitData(SCAN_TABLE, (service.getScanTable(1)));
    }

    private void initRetrofitCallBack() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String mParentSubcategory = jsonObject.toString();

                switch (requestId) {
                    case SCAN_TABLE:

                        try {
                            JSONObject object = new JSONObject(mParentSubcategory);
                            int status = object.getInt("status");
                            String msg = object.getString("message");

                            if (status == 1) {
                                areaTableModelArrayList.clear();
                                JSONArray jsonArray = object.getJSONArray("scantbl");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    scanTableModelArrayList = new ArrayList<>();
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    JSONArray jsonArray1 = jsonObject2.getJSONArray("tables");

                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                        JSONObject jsonObject3 = jsonArray1.getJSONObject(j);

                                        ScanTableModel scanTableModel = new ScanTableModel();
                                        scanTableModel.setTableId(jsonObject3.getInt("Table_Id"));
                                        scanTableModel.setCustName(jsonObject3.getString("Cust_Name"));
                                        scanTableModel.setCustMob(jsonObject3.getString("Cust_Mob"));

                                        scanTableModelArrayList.add(scanTableModel);
                                    }

                                    AreaTableModel areaTableModel = new AreaTableModel();
                                    areaTableModel.setAreaId(jsonObject2.getInt("Area_Id"));
                                    areaTableModel.setAreaName(jsonObject2.getString("Area_Name"));
                                    areaTableModel.setScanTableModelArrayList(scanTableModelArrayList);

                                    areaTableModelArrayList.add(areaTableModel);
                                }

                                if (areaTableModelArrayList != null && areaTableModelArrayList.size() > 0) {
                                    ScanTableRVAdapter scanTableRVAdapter = new ScanTableRVAdapter(getContext(), areaTableModelArrayList);

                                    rvScanTable.setHasFixedSize(true);
                                    rvScanTable.setNestedScrollingEnabled(false);
                                    rvScanTable.setLayoutManager(new GridLayoutManager(getContext(), 1));
                                    rvScanTable.setItemAnimator(new DefaultItemAnimator());
                                    rvScanTable.setAdapter(scanTableRVAdapter);

                                    rvScanTable.setVisibility(View.VISIBLE);
                                    llNoTables.setVisibility(View.GONE);
                                    skLoading.setVisibility(View.GONE);
                                } else {
                                    rvScanTable.setVisibility(View.GONE);
                                    llNoTables.setVisibility(View.VISIBLE);
                                    skLoading.setVisibility(View.GONE);
                                }
                            } else {
                                rvScanTable.setVisibility(View.GONE);
                                llNoTables.setVisibility(View.VISIBLE);
                                skLoading.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case TABLE_CONF_STATUS:

                        try {
                            JSONObject object = new JSONObject(mParentSubcategory);
                            int status = object.getInt("status");
                            progressDialog.dismiss();
                            if (status == 1) {
                                getScanTable();
                            } else {
                                Toast.makeText(getContext(), "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
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

    private void init() {
        areaTableModelArrayList = new ArrayList<>();
        mSessionmanager = new Sessionmanager(getContext());

        rvScanTable = view.findViewById(R.id.rvScanTable);
        llNoTables = view.findViewById(R.id.llNoTables);
        skLoading = view.findViewById(R.id.skLoading);
    }
}
