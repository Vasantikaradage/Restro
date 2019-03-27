package com.restrosmart.restro.User;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.gson.JsonObject;
import com.restrosmart.restro.Adapter.RVPayModeAdapter;
import com.restrosmart.restro.Interfaces.ApiService;
import com.restrosmart.restro.Interfaces.IResult;
import com.restrosmart.restro.Model.PayModeModel;
import com.restrosmart.restro.R;
import com.restrosmart.restro.RetrofitClientInstance;
import com.restrosmart.restro.RetrofitService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Response;

import static com.restrosmart.restro.ConstantVariables.PAYMENT_METHODS;

public class ActivityPaymentMethod extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView rvPaymentMode;
    private RVPayModeAdapter rvPaymentModeAdapter;
    private ArrayList<PayModeModel> payModeModelArrayList;

    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        init();
        setupToolbar();
        getPaymentMethods();
    }

       @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public void getPaymentMethods() {
        initRetrofitCallback();

        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityPaymentMethod.this);
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

                        rvPaymentMode.setLayoutManager(new LinearLayoutManager(ActivityPaymentMethod.this));
                        rvPaymentModeAdapter = new RVPayModeAdapter(ActivityPaymentMethod.this, payModeModelArrayList);
                        rvPaymentMode.setAdapter(rvPaymentModeAdapter);
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

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setTitle("Payment Methods");
    }

    private void init() {

        payModeModelArrayList = new ArrayList<>();
        mToolbar = findViewById(R.id.toolbar);
        rvPaymentMode = findViewById(R.id.rvPaymentMode);
    }
}
