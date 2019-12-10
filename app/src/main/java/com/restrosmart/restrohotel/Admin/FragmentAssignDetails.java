package com.restrosmart.restrohotel.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.RVAssignTableDetails;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.CaptainTableForm;
import com.restrosmart.restrohotel.Model.TableForm;
import com.restrosmart.restrohotel.Model.TableFormId;
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

import static com.restrosmart.restrohotel.ConstantVariables.ALLOCATED_TABLE_DEATILS;
import static com.restrosmart.restrohotel.ConstantVariables.TABLE_DETAILS;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class FragmentAssignDetails extends Fragment {
    private View view;

    private Sessionmanager sessionmanager;
    private int hotelId, branchId;
    private FrameLayout flBtnTableAssign;
    private  RetrofitService mRetrofitService;
    private  IResult mResultCallBack;
    private ProgressDialog progressDialog;
    private LinearLayout linearLayoutNoData;
    private RecyclerView rvTableDetails;

    private ArrayList<CaptainTableForm> arrayListTable;
 
    private ArrayList<TableFormId> arrayListtTableId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assign_table, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

        HashMap<String, String> stringHashMap = sessionmanager.getHotelDetails();
        hotelId = Integer.parseInt(stringHashMap.get(HOTEL_ID));



        flBtnTableAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityAssignTable.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        retrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(ALLOCATED_TABLE_DEATILS, service.captainTableDisplay(hotelId));
        showProgressDialog();
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //Without this user can hide loader by tapping outside screen
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle(getContext().getResources().getString(R.string.app_name));
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    private void retrofitCallBack() {
        mResultCallBack=new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                switch (requestId) {
                    case ALLOCATED_TABLE_DEATILS:

                        JsonObject object = response.body();
                        String value = object.toString();

                        try {
                            JSONObject jsonObject = new JSONObject(value);
                            int status = jsonObject.getInt("status");
                            progressDialog.dismiss();
                            if (status == 1) {

                                linearLayoutNoData.setVisibility(View.GONE);
                                 rvTableDetails.setVisibility(View.VISIBLE);
                                JSONArray jsonArray = jsonObject.getJSONArray("assignedtable");
                                arrayListTable.clear();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    CaptainTableForm captainTableForm = new CaptainTableForm();
                                    captainTableForm.setCaptainName(jsonObject1.getString("Emp_Name"));
                                    captainTableForm.setArea_Name(jsonObject1.getString("Area_Name").toString());
                                    JSONArray array = jsonObject1.getJSONArray("table");

                                    arrayListtTableId = new ArrayList<>();
                                    for (int in = 0; in < array.length(); in++) {

                                        JSONObject jsonObject2 = array.getJSONObject(in);
                                        TableFormId tableFormId = new TableFormId();
                                        tableFormId.setTableId(jsonObject2.getInt("Table_Id"));
                                        tableFormId.setTableNo(jsonObject2.getInt("Table_No"));
                                      //  tableFormId.setTableStatus(jsonObject2.getInt("Table_Status"));
                                        arrayListtTableId.add(tableFormId);
                                    }
                                    captainTableForm.setArrayTableFormIds(arrayListtTableId);
                                    arrayListTable.add(captainTableForm);
                                }
                            } else {
                                linearLayoutNoData.setVisibility(View.VISIBLE);
                                rvTableDetails.setVisibility(View.GONE);
                            }
                            rvSetAdapter();


                        } catch (JSONException e) {

                            e.printStackTrace();
                        }


                        break;
                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("","requestId"+requestId);
                Log.d("","RetrofitError"+error);
            }
        };
    }

    private void rvSetAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvTableDetails.setLayoutManager(linearLayoutManager);
        rvTableDetails.setHasFixedSize(true);
        rvTableDetails.getLayoutManager().setMeasurementCacheEnabled(false);
        RVAssignTableDetails rvAssignTableDetails=new RVAssignTableDetails(getActivity(),arrayListTable);
        rvTableDetails.setAdapter(rvAssignTableDetails);
    }

    private void init() {
        sessionmanager = new Sessionmanager(getActivity());
        rvTableDetails = view.findViewById(R.id.rv_assign_table_deatils);
        flBtnTableAssign = getActivity().findViewById(R.id.ivAssignTable);
        linearLayoutNoData = view.findViewById(R.id.llNoTableData);
        arrayListTable = new ArrayList<>();
        arrayListtTableId = new ArrayList<>();
    }
}
