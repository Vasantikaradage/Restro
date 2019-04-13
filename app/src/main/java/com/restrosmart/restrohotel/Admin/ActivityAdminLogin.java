package com.restrosmart.restrohotel.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.medialablk.easygifview.EasyGifView;
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

import static com.restrosmart.restrohotel.ConstantVariables.LOGIN_ADMIN;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.REMEMBER_PASSWORD;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.REMEMBER_USER_NAME;

public class ActivityAdminLogin extends AppCompatActivity {

    private EditText edtAdminUsername, edtAdminPassword;
    private CheckBox cbRememberMe;
    private TextView tvAdminForgotPassword;
    private Button btnAdminLogin, btnAdminRegister;

    private ProgressBar progressBar;
    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;
    private Sessionmanager sessionmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        init();
        EasyGifView easyGifView = (EasyGifView) findViewById(R.id.easyGifView);
        easyGifView.setGifFromResource(R.drawable.logo_gif); //Your own GIF file from Resources
        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if (isValid()){}*/
                btnAdminLogin.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                Login();
            }
        });

        btnAdminRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityAdminLogin.this, ActivityAdminRegister.class);
                startActivity(intent);
            }
        });

        if (sessionmanager.isRememberMe()) {
            HashMap<String, String> hashMap = sessionmanager.getRememberMe();

            edtAdminUsername.setText(hashMap.get(REMEMBER_USER_NAME));
            edtAdminPassword.setText(hashMap.get(REMEMBER_PASSWORD));
        }

        tvAdminForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityAdminLogin.this, ActivityAdminForgetPassword.class);
                startActivity(intent);
            }
        });
    }

    private void init() {

        sessionmanager = new Sessionmanager(this);
        edtAdminUsername = findViewById(R.id.edtAdminUsername);
        edtAdminPassword = findViewById(R.id.edtAdminPassword);
        cbRememberMe = findViewById(R.id.cbAdmin);
        tvAdminForgotPassword = findViewById(R.id.tvAdminForgotPassword);
        progressBar = findViewById(R.id.progressbar);
        btnAdminLogin = findViewById(R.id.btnAdminLogin);
        btnAdminRegister = findViewById(R.id.btnAdminRegister);
    }

    private boolean isValid() {
        return true;
    }

    public void Login() {
        initRetrofitCallback();

        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityAdminLogin.this);
        mRetrofitService.retrofitData(LOGIN_ADMIN, (service.getLogin(edtAdminUsername.getText().toString(), edtAdminPassword.getText().toString())));
    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();

                String value = jsonObject.toString();

                Log.v("reponse", value);
                try {
                    JSONObject jsonObject1 = new JSONObject(value);

                    int status = jsonObject1.getInt("status");
                    if (status == 1) {

                        JSONObject jsonObject2 = jsonObject1.getJSONObject("employee");

                        int hotelId = jsonObject2.getInt("Hotel_Id");
                        int branchId=jsonObject2.getInt("Branch_Id");
                        int roleId=jsonObject2.getInt("Role_Id");
                        String hotelName = jsonObject2.getString("Hotel_Name");
                        int empId=jsonObject2.getInt("Emp_Id");

                        sessionmanager.saveHotelDetails(hotelId, hotelName,roleId,branchId,empId);

                        //sessionmanager.createSession(user);
                        Intent intent = new Intent(ActivityAdminLogin.this, ActivityAdminDrawer.class);
                        startActivity(intent);

                        Toast.makeText(ActivityAdminLogin.this, "Login successful", Toast.LENGTH_SHORT).show();


                        progressBar.setVisibility(View.GONE);
                        btnAdminLogin.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(ActivityAdminLogin.this, "Employee not Active.. ", Toast.LENGTH_SHORT).show();
                    }

                    if (cbRememberMe.isChecked()) {
                        sessionmanager.setRememberMe(edtAdminUsername.getText().toString(), edtAdminPassword.getText().toString());
                    } else {
                        sessionmanager.clearRememberMe();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                progressBar.setVisibility(View.GONE);
                btnAdminLogin.setVisibility(View.VISIBLE);
                Toast.makeText(ActivityAdminLogin.this, "Something went wrong..! Please try again later.", Toast.LENGTH_LONG).show();
            }
        };
    }
}
