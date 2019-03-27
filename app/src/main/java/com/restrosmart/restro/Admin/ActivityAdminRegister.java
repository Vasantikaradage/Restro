package com.restrosmart.restro.Admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restro.Interfaces.ApiService;
import com.restrosmart.restro.Interfaces.IResult;
import com.restrosmart.restro.R;
import com.restrosmart.restro.RetrofitClientInstance;
import com.restrosmart.restro.RetrofitService;

import org.json.JSONObject;

import retrofit2.Response;

import static com.restrosmart.restro.ConstantVariables.REGISTER_ADMIN;

public class ActivityAdminRegister extends AppCompatActivity {

    private EditText edtHotelName, edtHotelAddress, edtHotelMob, edtHotelPhone, edtHotelEmail, edtHotelGstinNo, edtHotelPassword, edtHotleConfirmPassword;
    private ProgressBar pbRegister;
    private Button btnHotelRegister;

    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        init();

        btnHotelRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (isValid()) {
                }*/
                pbRegister.setVisibility(View.VISIBLE);
                btnHotelRegister.setVisibility(View.GONE);
                register();
            }
        });
    }

    private boolean isValid() {
        return true;
    }

    private void register() {
        initRetrofitCallback();

        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityAdminRegister.this);
        mRetrofitService.retrofitData(REGISTER_ADMIN, (service.hotelRegister(
                edtHotelName.getText().toString(), edtHotelAddress.getText().toString(), edtHotelMob.getText().toString(),
                edtHotelPhone.getText().toString(), edtHotelEmail.getText().toString(), edtHotelGstinNo.getText().toString(),
                edtHotelPassword.getText().toString(), edtHotleConfirmPassword.getText().toString())));
    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {

                JsonObject jsonObject = response.body();
                String value = jsonObject.toString();

                //Log.v("response", value);

                try {
                    JSONObject jsonObject1 = new JSONObject(value);

                    int status = jsonObject1.getInt("status");

                    if (status == 1) {
                        btnHotelRegister.setVisibility(View.VISIBLE);
                        pbRegister.setVisibility(View.GONE);
                        Toast.makeText(ActivityAdminRegister.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ActivityAdminRegister.this, "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
                        btnHotelRegister.setVisibility(View.VISIBLE);
                        pbRegister.setVisibility(View.GONE);
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

    private void init() {
        edtHotelName = findViewById(R.id.edtHotelName);
        edtHotelAddress = findViewById(R.id.edtHotelAddress);
        edtHotelMob = findViewById(R.id.edtHotelMob);
        edtHotelPhone = findViewById(R.id.edtHotelPhone);
        edtHotelEmail = findViewById(R.id.edtHotelEmail);
        edtHotelGstinNo = findViewById(R.id.edtHotelGstinNo);
        edtHotelPassword = findViewById(R.id.edtHotelPassword);
        edtHotleConfirmPassword = findViewById(R.id.edtHotleConfirmPassword);
        pbRegister = findViewById(R.id.pbRegister);
        btnHotelRegister = findViewById(R.id.btnHotelRegister);
    }
}
