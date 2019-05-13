package com.restrosmart.restrohotel.Captain.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Captain.Adapters.RVOrderTablesAdapter;
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

public class SwapTableFragment extends Fragment {

    private View view;
    private RecyclerView rvTables;

    private Sessionmanager mSessionmanager;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;

    private ArrayList<AreaTableModel> areaTableModelArrayList;
    private ArrayList<ScanTableModel> scanTableModelArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_swap_table, container, false);

        init();

        //Get table list this is demo list
        getScanTable();

        return view;
    }

    private void getScanTable() {
        initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getContext());
        mRetrofitService.retrofitData(SCAN_TABLE, (service.getScanTable(1, 1)));
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

                                RVOrderTablesAdapter rvOrderTablesAdapter = new RVOrderTablesAdapter(getContext(), areaTableModelArrayList);
                                rvTables.setHasFixedSize(true);
                                rvTables.setNestedScrollingEnabled(false);
                                rvTables.setLayoutManager(new GridLayoutManager(getContext(), 1));
                                rvTables.setItemAnimator(new DefaultItemAnimator());
                                rvTables.setAdapter(rvOrderTablesAdapter);
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

        rvTables = view.findViewById(R.id.rvTables);
    }
}
