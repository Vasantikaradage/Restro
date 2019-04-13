package com.restrosmart.restrohotel.Admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.PayModeModel;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.PAYMENT_METHODS;

/**
 * Created by SHREE on 27/11/2018.
 */

public  class FragmentPaymentMethods extends Fragment {

    private Toolbar mToolbar;
    private RecyclerView rvPaymentMode;
   // private RVPayModeAdapter rvPaymentModeAdapter;
    private ArrayList<PayModeModel> payModeModelArrayList;

    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;
    private Button btnAddPayment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_methods, null);
        return  view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
       // setupToolbar();
        getPaymentMethods();
    }



    public void getPaymentMethods() {
        initRetrofitCallback();

        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(PAYMENT_METHODS, (service.getPaymentMethods(1, 1)));
    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String value = jsonObject.toString();

                try {
                    JSONObject jsonObject1 = new JSONObject(value);
                    int status = jsonObject1.getInt("status");
                    if (status == 1) {

                        JSONArray jsonArray = jsonObject1.getJSONArray("Data");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                            PayModeModel payModeModel = new PayModeModel();
                            payModeModel.setPayModeId(jsonObject2.getInt("ID"));
                            payModeModel.setPayModeImage(jsonObject2.getString("Icon"));
                            payModeModel.setPayModeName(jsonObject2.getString("PaymentName"));
                            payModeModel.setPayAccountNo(jsonObject2.getString("Account_No"));
                            payModeModel.setPayACHolderName(jsonObject2.getString("AutorizedPerson"));
                            payModeModel.setPayBankName(jsonObject2.getString("Bank_Name"));
                            payModeModel.setPayBranchName(jsonObject2.getString("BranchName"));
                            payModeModel.setPayContact(jsonObject2.getString("PaymentMobile"));
                            payModeModel.setPayIFSCCode(jsonObject2.getString("IFSC_Code"));
                            payModeModel.setPayUpiCode(jsonObject2.getString("UPICode"));
                            payModeModel.setPayLink(jsonObject2.getString("Link"));
                            payModeModel.setPayQRimage(jsonObject2.getString("QR_Img"));
                            payModeModelArrayList.add(payModeModel);
                        }

                        rvPaymentMode.setLayoutManager(new LinearLayoutManager(getActivity()));
                        //rvPaymentModeAdapter = new RVPayModeAdapter(getActivity(), payModeModelArrayList);
                       // rvPaymentMode.setAdapter(rvPaymentModeAdapter);
                    }
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

    /*private void setupToolbar() {
       *//* setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setTitle("Payment Methods");*//*
    }
*/
    private void init() {

        payModeModelArrayList = new ArrayList<>();
        mToolbar = getView().findViewById(R.id.toolbar);
        rvPaymentMode = getView().findViewById(R.id.rvPaymentMode);
        btnAddPayment=getView().findViewById(R.id.btn_add_payment);

        btnAddPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent=new Intent(getActivity(),ActivityAddPayment.class);
               // startActivity(intent);

            }
        });
    }
}


