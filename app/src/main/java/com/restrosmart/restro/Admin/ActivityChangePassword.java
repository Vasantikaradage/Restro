package com.restrosmart.restro.Admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restro.Interfaces.ApiService;
import com.restrosmart.restro.Interfaces.IResult;
import com.restrosmart.restro.R;

import com.restrosmart.restro.RetrofitClientInstance;
import com.restrosmart.restro.RetrofitService;
import com.restrosmart.restro.Utils.Sessionmanager;
import com.restrosmart.restro.customfonts.EditText_Roboto_Meidum;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Response;


import static com.restrosmart.restro.ConstantVariables.CHANGE_PASSWORD;
import static com.restrosmart.restro.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restro.Utils.Sessionmanager.HOTEL_ID;
import static com.restrosmart.restro.Utils.Sessionmanager.ROLE_ID;

/**
 * Created by SHREE on 03/10/2018.
 */

public class ActivityChangePassword extends AppCompatActivity {
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private Sessionmanager sessionmanager;
    private String Emp_Id, branchId, hotelId;
    private EditText mOld_pass, mNew_pass, mCon_pass;
    private Button btnChangePassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
    }

    private void init() {
        mOld_pass = findViewById(R.id.et_old_pass);
        mNew_pass = findViewById(R.id.et_pass);
        mCon_pass = findViewById(R.id.et_cpass);
        btnChangePassword = (Button) findViewById(R.id.save_change_password);

        sessionmanager = new Sessionmanager(ActivityChangePassword.this);
        HashMap<String, String> name_info = sessionmanager.getHotelDetails();
        Emp_Id = name_info.get(ROLE_ID);
        hotelId = name_info.get(HOTEL_ID);
        branchId = name_info.get(BRANCH_ID);

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
                        Integer.parseInt(hotelId),
                        Integer.parseInt(branchId)));
            }
        });
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
