package com.restrosmart.restrohotel.Admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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


import static com.restrosmart.restrohotel.ConstantVariables.CHANGE_PASSWORD;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.ROLE_ID;

/**
 * Created by SHREE on 03/10/2018.
 */

public class ActivityChangePassword extends AppCompatActivity {
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private Sessionmanager sessionmanager;
    private String Emp_Id, branchId, hotelId;
    private TextInputEditText mOld_pass, mNew_pass, mCon_pass;
    private Button btnChangePassword;
    private TextView tvTitle;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
        setUpToolBar();

        sessionmanager = new Sessionmanager(ActivityChangePassword.this);
        HashMap<String, String> name_info = sessionmanager.getHotelDetails();
        Emp_Id = name_info.get(ROLE_ID);
        hotelId = name_info.get(HOTEL_ID);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRetrofitCallBack();
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack, ActivityChangePassword.this);
                mRetrofitService.retrofitData(CHANGE_PASSWORD, service.ChangePassword(
                        Integer.parseInt(Emp_Id),
                        mNew_pass.getText().toString(),
                        mCon_pass.getText().toString(),
                        Integer.parseInt(hotelId)));
            }
        });
    }

    private void setUpToolBar() {
        tvTitle.setText("Change Password");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void init() {
        mOld_pass = findViewById(R.id.et_old_pass);
        mNew_pass = findViewById(R.id.et_pass);
        mCon_pass = findViewById(R.id.et_cpass);
        btnChangePassword = (Button) findViewById(R.id.save_change_password);
        mToolbar = findViewById(R.id.toolbar);
        tvTitle = mToolbar.findViewById(R.id.tx_title);
    }

    private void initRetrofitCallBack() {

        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {

                switch (requestId) {
                    case CHANGE_PASSWORD:
                        JsonObject jsonObject = response.body();
                        String value = jsonObject.toString();
                        try {
                            JSONObject object = new JSONObject(value);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityChangePassword.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ActivityChangePassword.this, "please check  the new and conform password", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("", "requestId" + requestId);
                Log.d("", "retrofitError" + error);
            }
        };
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
