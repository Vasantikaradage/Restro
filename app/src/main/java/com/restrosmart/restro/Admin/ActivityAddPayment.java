package com.restrosmart.restro.Admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.JsonObject;
import com.restrosmart.restro.Interfaces.ApiService;
import com.restrosmart.restro.Interfaces.IResult;
import com.restrosmart.restro.R;
import com.restrosmart.restro.RetrofitClientInstance;
import com.restrosmart.restro.RetrofitService;
import com.restrosmart.restro.Utils.Sessionmanager;

import java.util.HashMap;

import retrofit2.Response;

import static com.restrosmart.restro.ConstantVariables.ADD_PAYMENT_MODE;
import static com.restrosmart.restro.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restro.Utils.Sessionmanager.HOTEL_ID;

/**
 * Created by SHREE on 28/11/2018.
 */

public  class ActivityAddPayment extends AppCompatActivity {
    private EditText  etxPaymentName,etxPersonName,etxAccountNo,etxIfscCode,etxPhoneNo,etxBranchName,etxUpiCode,etxBankName;
    private ImageView imagIcon,ImageQrCode;
    private  RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private  String imageIcon,QRIcon,hotelId,branchId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_payment_add);
        init();
        initRetrofitCall();


    }

    private void initRetrofitCall() {

        initRetrofitCallback();

        ApiService apiService= RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);

        mRetrofitService = new RetrofitService(mResultCallBack,ActivityAddPayment.this);
        mRetrofitService.retrofitData(ADD_PAYMENT_MODE,(apiService.AddPaymentMode(etxPaymentName.getText().toString(),
                etxPersonName.getText().toString(),
                etxBankName.getText().toString(),
                etxIfscCode.getText().toString(),
                etxBranchName.getText().toString(),
                etxUpiCode.getText().toString(),
                Integer.parseInt(etxPhoneNo.getText().toString()),
                imageIcon,
                QRIcon,
                Integer.parseInt(branchId),
                Integer.parseInt(hotelId)

        )));


    }

    private void initRetrofitCallback() {

        mResultCallBack=new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {

            }

            @Override
            public void notifyError(int requestId, Throwable error) {

            }
        };
    }

    private void init() {
        etxAccountNo=findViewById(R.id.etx_account_no);
        etxBranchName=findViewById(R.id.etx_bank_branck);

        etxPaymentName=findViewById(R.id.etx_payment_type);
        etxPhoneNo=findViewById(R.id.etx_mob_no);
        etxUpiCode=findViewById(R.id.etx_upi_code);
        etxBankName=findViewById(R.id.etx_bank);
        ImageQrCode=findViewById(R.id.img_qr_code);

        Sessionmanager sharedPreferanceManage = new Sessionmanager(ActivityAddPayment.this);
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();

        hotelId = name_info.get(HOTEL_ID);
        branchId = name_info.get(BRANCH_ID);

    }
}
