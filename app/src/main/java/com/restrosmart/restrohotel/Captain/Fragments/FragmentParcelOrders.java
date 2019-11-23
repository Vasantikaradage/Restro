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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Captain.Activities.ActivityHotelMenu;
import com.restrosmart.restrohotel.Captain.Adapters.RVParcelOrderAdapter;
import com.restrosmart.restrohotel.Captain.Interfaces.CapOrderDeleteListener;
import com.restrosmart.restrohotel.Captain.Models.AllOrderModel;
import com.restrosmart.restrohotel.Captain.Models.OrderModel;
import com.restrosmart.restrohotel.Captain.Models.UserCategory;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.REGISTER_CUSTOMER;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentParcelOrders extends Fragment implements View.OnClickListener {

    private View view;
    private RecyclerView rvParcelOrders;
    private LinearLayout llNoData;
    private FloatingActionButton fabAddMenu;

    private ArrayList<AllOrderModel> orderModelArrayList;
    private ArrayList<UserCategory> userCategoryArrayList;

    private EditText edtName, edtMobileNo;

    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;

    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;
    private Sessionmanager mSessionmanager;

    private HashMap<String, String> hotelDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_parcel_orders, container, false);

        init();

        hotelDetails = mSessionmanager.getHotelDetails();

        orderModelArrayList = getArguments().getParcelableArrayList("arrayListParcelOrder");
        userCategoryArrayList = getArguments().getParcelableArrayList("arrayListUserCategory");

        if (orderModelArrayList != null && orderModelArrayList.size() > 0) {
            RVParcelOrderAdapter rvParcelOrderAdapter = new RVParcelOrderAdapter(getContext(), Integer.parseInt(hotelDetails.get(HOTEL_ID)), orderModelArrayList, new CapOrderDeleteListener() {
                @Override
                public void deleteOrder(int arraylistSize) {
                    if (arraylistSize > 0) {
                        rvParcelOrders.setVisibility(View.VISIBLE);
                        llNoData.setVisibility(View.GONE);
                    } else {
                        rvParcelOrders.setVisibility(View.GONE);
                        llNoData.setVisibility(View.VISIBLE);
                    }
                }
            });
            rvParcelOrders.setHasFixedSize(true);
            rvParcelOrders.setNestedScrollingEnabled(false);
            rvParcelOrders.setLayoutManager(new GridLayoutManager(getContext(), 1));
            rvParcelOrders.setItemAnimator(new DefaultItemAnimator());
            rvParcelOrders.setAdapter(rvParcelOrderAdapter);

            rvParcelOrders.setVisibility(View.VISIBLE);
            llNoData.setVisibility(View.GONE);
        } else {
            rvParcelOrders.setVisibility(View.GONE);
            llNoData.setVisibility(View.VISIBLE);
        }

        fabAddMenu.setOnClickListener(this);
        return view;
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
            Button btnCancelOrder = dialogView.findViewById(R.id.btnCancelOrder);
            Button btnConfirmOrder = dialogView.findViewById(R.id.btnConfirmOrder);

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
        mRetrofitService.retrofitData(REGISTER_CUSTOMER, (service.registerCustomer(Integer.parseInt(hotelDetails.get(HOTEL_ID)), 0, edtName.getText().toString(),
                edtMobileNo.getText().toString())));
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
        }
        return true;
    }

    private void showProgrssDailog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle(this.getResources().getString(R.string.app_name));
        progressDialog.setMessage("Registering customer...");
        progressDialog.show();
    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObjectResponse = response.body();
                String responseValue = jsonObjectResponse.toString();

                if (requestId == REGISTER_CUSTOMER) {
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
                            int tableNo = jsonObject1.getInt("Ctable_Id");

                            //Todo remove below code
                            mSessionmanager.deleteCustDetails();
                            mSessionmanager.resetCartCount();
                            mSessionmanager.deleteOrderID();

                            mSessionmanager.saveCustDetails(id, name, mob, tableNo);

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

        rvParcelOrders = view.findViewById(R.id.rvParcelOrders);
        llNoData = view.findViewById(R.id.llNoData);
        fabAddMenu = view.findViewById(R.id.fabAddMenu);
    }
}
