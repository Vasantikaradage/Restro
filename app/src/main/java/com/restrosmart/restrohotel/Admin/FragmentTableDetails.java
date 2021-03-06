package com.restrosmart.restrohotel.Admin;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.RVTableDetailsAdapter;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Interfaces.PositionListener;
import com.restrosmart.restrohotel.Interfaces.StatusListener;
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

import static com.restrosmart.restrohotel.ConstantVariables.ADD_TABLE_DETAILS;
import static com.restrosmart.restrohotel.ConstantVariables.TABLE_DETAILS;
import static com.restrosmart.restrohotel.ConstantVariables.UPDATE_AREA;
import static com.restrosmart.restrohotel.ConstantVariables.UPDATE_AREA_STATUS;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class FragmentTableDetails extends Fragment {

    private RecyclerView rvTableDetails;
    private View view;
    private ArrayList<TableForm> arrayListTable;
    private ArrayList<TableFormId> freeTableSwapArrayList;
    private ArrayList<TableForm> freeAreaSwapArrayList;
    private ArrayList<TableFormId> arrayListtTableId;
    private FrameLayout frameLayout;
    private View dialoglayout;
    private BottomSheetDialog dialog;
    private EditText etvAreaName, etvTableCount;
    private TextView tvTitle, tvTitleEdit;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private Sessionmanager sessionmanager;
    int hotelId, branchId;
    private RVTableDetailsAdapter rvTableDetailsAdapter;
    private int mAreaId;
    private LinearLayout linearLayoutNoData;
    private ProgressDialog progressDialog;
    private MoveTableReceiver moveReceiver;
    private  String count;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_table_details, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

        sessionmanager = new Sessionmanager(getActivity());
        HashMap<String, String> name_info = sessionmanager.getHotelDetails();
        hotelId = Integer.parseInt(name_info.get(HOTEL_ID));


        IntentFilter filter = new IntentFilter();
        filter.addAction("com.restrohotel.admin.move.table");
        moveReceiver = new MoveTableReceiver();
        getActivity().registerReceiver(moveReceiver, filter);
        tableDetails();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(moveReceiver);
    }

    private void tableDetails() {
        initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(TABLE_DETAILS, service.tableDisplay(hotelId));
        showProgressDialog();

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                dialoglayout = li.inflate(R.layout.dialog_add_table_details, null);
                dialog = new BottomSheetDialog(getActivity());
                dialog.setContentView(dialoglayout);
                etvAreaName = dialoglayout.findViewById(R.id.etv_area_name);
                etvTableCount = dialoglayout.findViewById(R.id.etv_table_count);
                Button saveTable = dialoglayout.findViewById(R.id.btnSave);
                tvTitle = dialoglayout.findViewById(R.id.tv_table_title);
                tvTitle.setVisibility(View.VISIBLE);
                Button cancelTable = dialoglayout.findViewById(R.id.btnCancel);
                dialog.show();

                cancelTable.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });



                saveTable.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        count= (etvTableCount.getText().toString());
                        if (isValid()) {
                            initRetrofitCallBack();
                            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                            mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                            mRetrofitService.retrofitData(ADD_TABLE_DETAILS, service.addtables(etvAreaName.getText().toString(),
                                    Integer.parseInt(count),
                                    hotelId));
                        }

                    }
                });
            }
        });

    }

    private boolean isValid() {
        if (etvAreaName.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter Area Name..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (count.length()==0) {
            Toast.makeText(getActivity(), "Please enter Table Count..", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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

    @Override
    public void onResume() {
        super.onResume();
        initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(TABLE_DETAILS, service.tableDisplay(hotelId));
    }

    private void initRetrofitCallBack() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {

                switch (requestId) {
                    case ADD_TABLE_DETAILS:
                        try {
                            JsonObject object = response.body();
                            String objectInfo = object.toString();
                            JSONObject jsonObject = new JSONObject(objectInfo);
                            int status = jsonObject.getInt("status");
                            String msg = jsonObject.getString("message");
                            if (status == 1) {
                                String statusInfo = jsonObject.getString("message");
                                Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                                tableDetails();

                            } else {
                                Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();

                            }
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;


                    case TABLE_DETAILS:
                        JsonObject object = response.body();
                        String value = object.toString();

                        try {
                            JSONObject jsonObject = new JSONObject(value);
                            int status = jsonObject.getInt("status");
                            progressDialog.dismiss();
                            if (status == 1) {

                                linearLayoutNoData.setVisibility(View.GONE);
                                rvTableDetails.setVisibility(View.VISIBLE);
                                JSONArray jsonArray = jsonObject.getJSONArray("table");
                                arrayListTable.clear();
                                // freeAreaSwapArrayList.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    TableForm tableForm = new TableForm();
                                    tableForm.setAreaId(jsonObject1.getInt("Area_Id"));
                                    tableForm.setAreaName(jsonObject1.getString("Area_Name").toString());
                                    tableForm.setTableCount(jsonObject1.getInt("Table_Count"));
                                    tableForm.setArea_Status(jsonObject1.getInt("Area_Status"));
                                    JSONArray array = jsonObject1.getJSONArray("tableList");

                                    arrayListtTableId = new ArrayList<>();
                                    for (int in = 0; in < array.length(); in++) {
                                        if (jsonObject1.getInt("Area_Status") != 0) {
                                            JSONObject jsonObject2 = array.getJSONObject(in);
                                            TableFormId tableFormId = new TableFormId();
                                            tableFormId.setTableId(jsonObject2.getInt("Table_Id"));
                                            tableFormId.setTableStatus(jsonObject2.getInt("Table_Status"));
                                           tableFormId.setTableNo(jsonObject2.getInt("Table_No"));

                                            arrayListtTableId.add(tableFormId);
                                        }
                                    }
                                    tableForm.setArrayTableFormIds(arrayListtTableId);
                                    arrayListTable.add(tableForm);
                                }

                            } else {
                                linearLayoutNoData.setVisibility(View.VISIBLE);
                                rvTableDetails.setVisibility(View.GONE);
                            }
                            callAdapterInformation();


                        } catch (JSONException e) {

                            e.printStackTrace();
                        }


                        break;
                    case UPDATE_AREA:
                        JsonObject updateObject = response.body();
                        String valueUpdate = updateObject.toString();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(valueUpdate);

                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                Toast.makeText(getActivity(), "Area Updated Successfully", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                tableDetails();
                            } else {
                                Toast.makeText(getActivity(), "Try Again..", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case UPDATE_AREA_STATUS:
                        JsonObject object1 = response.body();
                        String updateStatus = object1.toString();

                        try {
                            JSONObject jsonObject1 = new JSONObject(updateStatus);
                            int status = jsonObject1.getInt("status");
                            if (status == 1) {
                                Toast.makeText(getActivity(), "Status Updated Successfully", Toast.LENGTH_LONG).show();
                                tableDetails();

                            } else {
                                Toast.makeText(getActivity(), "Try Again..", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {

            }
        };
    }

    private void callAdapterInformation() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvTableDetails.setLayoutManager(linearLayoutManager);
        rvTableDetails.setHasFixedSize(true);
        rvTableDetails.getLayoutManager().setMeasurementCacheEnabled(false);
        rvTableDetailsAdapter = new RVTableDetailsAdapter(getActivity(), arrayListTable, new PositionListener() {
            @Override
            public void positionListern(int position) {
                tableDetails();
            }
        }, new StatusListener() {
            @Override
            public void statusListern(int position, int status) {
                initRetrofitCallBack();
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                mRetrofitService.retrofitData(UPDATE_AREA_STATUS, service.AreaStatus(status, arrayListTable.get(position).getAreaId(), hotelId));

            }
        }, new EditListener() {
            @Override
            public void getEditListenerPosition(final int position) {
                mAreaId = position;
                LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                dialoglayout = li.inflate(R.layout.dialog_add_table_details, null);
                dialog = new BottomSheetDialog(getActivity());
                dialog.setContentView(dialoglayout);
                etvAreaName = dialoglayout.findViewById(R.id.etv_area_name);
                etvTableCount = dialoglayout.findViewById(R.id.etv_table_count);
                Button saveTable = dialoglayout.findViewById(R.id.btnSave);
                Button updateTable = dialoglayout.findViewById(R.id.btnUpdate);
                TextView tvTableCount = dialoglayout.findViewById(R.id.tv_table_count);
                tvTitle = dialoglayout.findViewById(R.id.tv_table_title);
                tvTitle.setVisibility(View.GONE);
                saveTable.setVisibility(View.GONE);
                tvTableCount.setVisibility(View.GONE);
                etvTableCount.setVisibility(View.GONE);
                updateTable.setVisibility(View.VISIBLE);

                etvAreaName.setText(arrayListTable.get(position).getAreaName());


                tvTitleEdit = dialoglayout.findViewById(R.id.etv_edit_tableTitle);
                tvTitleEdit.setVisibility(View.VISIBLE);


                Button cancelTable = dialoglayout.findViewById(R.id.btnCancel);
                dialog.show();

                cancelTable.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                updateTable.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        initRetrofitCallBack();
                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                        mRetrofitService.retrofitData(UPDATE_AREA, service.UpdateArea(etvAreaName.getText().toString(),
                                arrayListTable.get(position).getAreaId(),
                                hotelId));

                    }
                });


            }
        });
        rvTableDetails.setAdapter(rvTableDetailsAdapter);
        //  rvTableDetailsAdapter.notifyDataSetChanged();

    }

    private void init() {
        rvTableDetails = view.findViewById(R.id.rv_table_details);
        arrayListTable = new ArrayList<>();
        arrayListtTableId = new ArrayList<>();
        frameLayout = view.findViewById(R.id.fl_add_area);
        linearLayoutNoData = view.findViewById(R.id.llNoTableData);
        freeTableSwapArrayList = new ArrayList<>();
        freeAreaSwapArrayList = new ArrayList<>();
    }


    private class MoveTableReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            initRetrofitCallBack();
            tableDetails();
        }
    }
}
