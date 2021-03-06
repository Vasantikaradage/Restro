package com.restrosmart.restrohotel.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.medialablk.easygifview.EasyGifView;
import com.restrosmart.restrohotel.Captain.Activities.ActivityCaptainDash;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.SuperAdmin.Activities.ActivitySADashBoradDrawer;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.LOGIN_ADMIN;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.REMEMBER_PASSWORD;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.REMEMBER_USER_NAME;

public class ActivityLogin extends AppCompatActivity {

    private EditText edtAdminUsername, edtAdminPassword;
    private CheckBox cbRememberMe;
    private TextView tvAdminForgotPassword;
    private Button btnAdminLogin;

    private ProgressBar progressBar;
    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;
    private Sessionmanager sessionmanager;
    private boolean oldFlag = false;

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

      /*  btnAdminRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLogin.this, ActivityAdminRegister.class);
                startActivity(intent);
            }
        });*/

        if (sessionmanager.isRememberMe()) {
            HashMap<String, String> hashMap = sessionmanager.getRememberMe();

            edtAdminUsername.setText(hashMap.get(REMEMBER_USER_NAME));
            edtAdminPassword.setText(hashMap.get(REMEMBER_PASSWORD));
        }

        edtAdminPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edtAdminPassword.getRight() - edtAdminPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        if (oldFlag) {
                            oldFlag = false;
                            edtAdminPassword.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(ActivityLogin.this, R.drawable.ic_password), null,
                                    ContextCompat.getDrawable(ActivityLogin.this, R.drawable.ic_pass_hide), null);
                            edtAdminPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        } else {
                            oldFlag = true;
                            edtAdminPassword.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(ActivityLogin.this, R.drawable.ic_password), null,
                                    ContextCompat.getDrawable(ActivityLogin.this, R.drawable.ic_pass_show), null);
                            edtAdminPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        tvAdminForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLogin.this, ActivityAdminForgetPassword.class);
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
        //   btnAdminRegister = findViewById(R.id.btnAdminRegister);
    }

    private boolean isValid() {
        return true;
    }

    public void Login() {
        initRetrofitCallback();

        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityLogin.this);
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
                    String message = jsonObject1.getString("message");
                    if (status == 1) {

                        JSONObject jsonObject2 = jsonObject1.getJSONObject("user");

                        int roleId = jsonObject2.getInt("Role_Id");
                        int empId = jsonObject2.getInt("Emp_Id");
                        String empName = jsonObject2.getString("Emp_Name");

                        if (roleId == 1) {
                            int hotelId = jsonObject2.getInt("Hotel_Id");
                            String hotelName = jsonObject2.getString("Hotel_Name");
                            sessionmanager.saveHotelDetails(hotelId, hotelName, roleId, empId);
                            Intent intent = new Intent(ActivityLogin.this, ActivityAdminDrawer.class);
                            startActivity(intent);
                            Toast.makeText(ActivityLogin.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        } else if (roleId == 4) {
                            int hotelId = jsonObject2.getInt("Hotel_Id");
                            String hotelName = jsonObject2.getString("Hotel_Name");

                            sessionmanager.saveHotelDetails(hotelId, hotelName, roleId, empId);
                            sessionmanager.saveCaptainDetails(empId, empName, roleId);
                            Intent intent = new Intent(ActivityLogin.this, ActivityCaptainDash.class);
                            startActivity(intent);
                            Toast.makeText(ActivityLogin.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        } else {

                            String email=jsonObject2.getString("Emp_Email");
                            sessionmanager.saveSuperAdminDetails(empId, empName, roleId);
                            Intent intent = new Intent(ActivityLogin.this, ActivitySADashBoradDrawer.class);
                            intent.putExtra("email",email);
                            startActivity(intent);
                            Toast.makeText(ActivityLogin.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                        btnAdminLogin.setVisibility(View.VISIBLE);

                    } else {
                        Toast.makeText(ActivityLogin.this, message, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        btnAdminLogin.setVisibility(View.VISIBLE);
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
                Toast.makeText(ActivityLogin.this, "Something went wrong..! Please try again later.", Toast.LENGTH_LONG).show();
            }
        };
    }
}
