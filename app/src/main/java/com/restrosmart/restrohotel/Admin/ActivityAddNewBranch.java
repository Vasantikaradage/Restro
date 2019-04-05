package com.restrosmart.restrohotel.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;

import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.EDIT_BRANCH_DETAILS;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

/**
 * Created by SHREE on 05/01/2019.
 */

public class ActivityAddNewBranch extends AppCompatActivity {

    private EditText etvBranchName, etvAddress, etvEmail, etvMob, etvGstnNo, etvTblNo, etvHotelTiming;
    private String mHotelId, mBranchId;
    private TextView tvHotelName;

    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private Button btnSave, btnUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_branch);
        init();

        Intent intent = getIntent();
        Sessionmanager sharedPreferanceManage = new Sessionmanager(ActivityAddNewBranch.this);
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();
        mHotelId = name_info.get(HOTEL_ID);
        mBranchId = name_info.get(BRANCH_ID);

        if (intent.getExtras() != null) {
            btnUpdate.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.GONE);

            tvHotelName.setText(intent.getStringExtra("hotelName"));
            etvBranchName.setText(intent.getStringExtra("branchName"));
            etvAddress.setText(intent.getStringExtra("branchAddress"));
            etvEmail.setText(intent.getStringExtra("branchEmail"));
            etvMob.setText(intent.getStringExtra("branchMob"));
            etvGstnNo.setText(intent.getStringExtra("gstnNo"));
            etvTblNo.setText(intent.getStringExtra("tableNo"));
            etvHotelTiming.setText(intent.getStringExtra("timing"));

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initRetrofitCallback();
                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                    mRetrofitService = new RetrofitService(mResultCallBack, ActivityAddNewBranch.this);
                    mRetrofitService.retrofitData(EDIT_BRANCH_DETAILS, (service.editHotelDetail(
                            etvBranchName.getText().toString(),
                            etvAddress.getText().toString(),
                            etvEmail.getText().toString(),
                            Integer.parseInt(etvMob.getText().toString()),
                            Integer.parseInt(etvGstnNo.getText().toString()),
                            Integer.parseInt(etvTblNo.getText().toString()),
                            etvHotelTiming.getText().toString(),
                            Integer.parseInt(mHotelId),
                            Integer.parseInt(mBranchId))));
                }
            });
        } else {
            // tvHotelName.setText(intent.getStringExtra("hotelName"));
            btnUpdate.setVisibility(View.GONE);
            btnSave.setVisibility(View.VISIBLE);
        }
    }

    private void init() {
        tvHotelName = (TextView) findViewById(R.id.tv_hotel_name);
        etvBranchName = (EditText) findViewById(R.id.etv_branch_name);
        etvAddress = (EditText) findViewById(R.id.etv_address);
        etvEmail = (EditText) findViewById(R.id.etv_email);
        etvMob = (EditText) findViewById(R.id.etv_mob);
        etvGstnNo = (EditText) findViewById(R.id.etv_gstn_no);
        etvTblNo = (EditText) findViewById(R.id.etv_no_of_table);
        etvHotelTiming = (EditText) findViewById(R.id.etv_hotel_time);
        btnUpdate = (Button) findViewById(R.id.btn_edit_branch);
        btnSave = (Button) findViewById(R.id.btn_save_branch);
    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                switch (requestId) {
                    case EDIT_BRANCH_DETAILS:
                        JsonObject jsonObject1 = response.body();
                        String editHotelDetailValue = jsonObject1.toString();
                        try {
                            JSONObject object = new JSONObject(editHotelDetailValue);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityAddNewBranch.this, "Hotel Detail Updated successfully", Toast.LENGTH_SHORT).show();
                                finish();
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
}
