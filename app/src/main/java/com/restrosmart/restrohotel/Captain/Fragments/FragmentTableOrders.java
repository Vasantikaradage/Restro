package com.restrosmart.restrohotel.Captain.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Captain.Activities.ActivityHotelMenu;
import com.restrosmart.restrohotel.Captain.Adapters.RVTableOrderAdapter;
import com.restrosmart.restrohotel.Captain.Models.OrderModel;
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

import static com.restrosmart.restrohotel.ConstantVariables.GET_UNBOOKED_TABLE;
import static com.restrosmart.restrohotel.ConstantVariables.REGISTER_CUSTOMER;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.EMP_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTableOrders extends Fragment implements View.OnClickListener {

    private View view;
    private RecyclerView rvTableOrders;
    private LinearLayout llNoData;
    private FloatingActionButton fabAddMenu;

    private ArrayList<OrderModel> orderModelArrayList;
    private ArrayList<UserCategory> userCategoryArrayList;
    private ArrayList<FreeTables> freeTablesArrayList;

    private AlertDialog alertDialog;
    private EditText edtName, edtMobileNo;
    private int selectedTable = 0;

    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;
    private Sessionmanager mSessionmanager;

    private ProgressDialog progressDialog;

    private HashMap<String, String> hotelDetails, capDetails;
    private int hotelId, capId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_table_orders, container, false);

        init();

        hotelDetails = mSessionmanager.getHotelDetails();
        hotelId = Integer.parseInt(hotelDetails.get(HOTEL_ID));
        capDetails = mSessionmanager.getCaptainDetails();
        capId = Integer.parseInt(capDetails.get(EMP_ID));

        orderModelArrayList = getArguments().getParcelableArrayList("arrayListTableOrder");
        userCategoryArrayList = getArguments().getParcelableArrayList("arrayListUserCategory");

        if (orderModelArrayList != null && orderModelArrayList.size() > 0) {
            RVTableOrderAdapter rvTableOrderAdapter = new RVTableOrderAdapter(getContext(), orderModelArrayList);
            rvTableOrders.setHasFixedSize(true);
            rvTableOrders.setNestedScrollingEnabled(false);
            rvTableOrders.setLayoutManager(new GridLayoutManager(getContext(), 1));
            rvTableOrders.setItemAnimator(new DefaultItemAnimator());
            rvTableOrders.setAdapter(rvTableOrderAdapter);

            rvTableOrders.setVisibility(View.VISIBLE);
            llNoData.setVisibility(View.GONE);
        } else {
            rvTableOrders.setVisibility(View.GONE);
            llNoData.setVisibility(View.VISIBLE);
        }

        fabAddMenu.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getFreeTable();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabAddMenu) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = null;
            if (layoutInflater != null) {
                dialogView = layoutInflater.inflate(R.layout.user_register_dialog, null);
            }
            dialogBuilder.setView(dialogView);

            alertDialog = dialogBuilder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            alertDialog.show();

            edtName = dialogView.findViewById(R.id.edtName);
            edtMobileNo = dialogView.findViewById(R.id.edtMobileNo);
            TextView tvTableNoLabel = dialogView.findViewById(R.id.tvTableNoLabel);
            Spinner spnTableNo = dialogView.findViewById(R.id.spnTableNo);
            Button btnCancelOrder = dialogView.findViewById(R.id.btnCancelOrder);
            Button btnConfirmOrder = dialogView.findViewById(R.id.btnConfirmOrder);

            if (freeTablesArrayList.size() > 0) {
                ArrayAdapter<FreeTables> arrayAdapter = new ArrayAdapter<FreeTables>(getContext(), android.R.layout.simple_list_item_1, freeTablesArrayList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnTableNo.setAdapter(arrayAdapter);
                spnTableNo.setSelection(0);

                tvTableNoLabel.setVisibility(View.VISIBLE);
                spnTableNo.setVisibility(View.VISIBLE);
            } else {
                tvTableNoLabel.setVisibility(View.GONE);
                spnTableNo.setVisibility(View.GONE);
                Toast.makeText(getContext(), "No tables available", Toast.LENGTH_SHORT).show();
            }

            spnTableNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedTable = Integer.parseInt(parent.getItemAtPosition(position).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isValid()) {
                        showProgrssDailog();
                        registerCustomer();
                    }
                }
            });

            btnCancelOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
        }
    }

    private void registerCustomer() {
        initRetrofitCallback();

        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getContext());
        mRetrofitService.retrofitData(REGISTER_CUSTOMER, (service.registerCustomer(hotelId, selectedTable, edtName.getText().toString(),
                edtMobileNo.getText().toString())));
    }

    private void getFreeTable() {
        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getContext());
        mRetrofitService.retrofitData(GET_UNBOOKED_TABLE, (service.getFreeTables(hotelId, capId)));
    }

    private boolean isValid() {
        String mobilePattern = "(0/91)?[7-9][0-9]{9}";

        if (edtName.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Please enter customer name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (edtMobileNo.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Please enter mobile no", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!edtMobileNo.getText().toString().matches(mobilePattern)) {
            Toast.makeText(getContext(), "Please enter valid mobile no", Toast.LENGTH_SHORT).show();
            return false;
        } else if (selectedTable == 0) {
            Toast.makeText(getContext(), "No tables available", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void showProgrssDailog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //Without this user can hide loader by tapping outside screen
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle(this.getResources().getString(R.string.app_name));
        progressDialog.setMessage("Registering & assigning table to customer...");
        progressDialog.show();
    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObjectResponse = response.body();
                String responseValue = jsonObjectResponse.toString();

                switch (requestId) {
                    case REGISTER_CUSTOMER:
                        try {
                            JSONObject jsonObject = new JSONObject(responseValue);

                            int status = jsonObject.getInt("status");
                            String msg = jsonObject.getString("message");

                            if (status == 1) {
                                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                alertDialog.dismiss();
                                JSONObject jsonObject1 = jsonObject.getJSONObject("Customer");

                                String id = jsonObject1.getString("Cap_Cust_Id");
                                String name = jsonObject1.getString("Ccust_Name");
                                String mob = jsonObject1.getString("Ccust_Mob");

                                mSessionmanager.saveCustDetails(id, name, mob);

                                Intent intent = new Intent(getContext(), ActivityHotelMenu.class);
                                intent.putExtra("categoryPos", 0);
                                intent.putExtra("categoryId", userCategoryArrayList.get(0).getCategoryId());
                                intent.putExtra("categoryName", userCategoryArrayList.get(0).getCategoryName());
                                intent.putParcelableArrayListExtra("arrayList", userCategoryArrayList);
                                getContext().startActivity(intent);

                            } else {
                                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case GET_UNBOOKED_TABLE:
                        try {
                            JSONObject jsonObject1 = new JSONObject(responseValue);

                            int status = jsonObject1.getInt("status");
                            if (status == 1) {
                                freeTablesArrayList.clear();

                                if (jsonObject1.getJSONArray("captable") != null && jsonObject1.getJSONArray("captable").length() > 0) {
                                    JSONArray jsonArray = jsonObject1.getJSONArray("captable");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                                        FreeTables freeTables = new FreeTables();

                                        freeTables.setTableNo(jsonObject2.getInt("Table_Id"));
                                        freeTablesArrayList.add(freeTables);
                                    }
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

    private void init() {
        mSessionmanager = new Sessionmanager(getContext());
        freeTablesArrayList = new ArrayList<>();

        rvTableOrders = view.findViewById(R.id.rvTableOrders);
        llNoData = view.findViewById(R.id.llNoData);
        fabAddMenu = view.findViewById(R.id.fabAddMenu);
    }
}
