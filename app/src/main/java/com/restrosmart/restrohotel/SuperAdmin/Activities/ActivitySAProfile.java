package com.restrosmart.restrohotel.SuperAdmin.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

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

import static com.restrosmart.restrohotel.ConstantVariables.GET_SA_PROFILE;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.EMP_ID;

public class ActivitySAProfile extends AppCompatActivity {
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private Sessionmanager sessionmanager;
    private TextView tvUserSaName, tvUsenSAFulName, tvSaMob, tvSaEmail, tvSaAdhar, tvSaAdrress, tvSaEmpRole;
    private int mSaId;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sa_profile);

        init();
        setUpToolBar();
        HashMap<String, String> superAdminInfo = sessionmanager.getSuperAdminDetails();
        mSaId = Integer.parseInt(superAdminInfo.get(EMP_ID));

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        initRetrofitCall();
    }

    private void initRetrofitCall() {
        retrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivitySAProfile.this);
        mRetrofitService.retrofitData(GET_SA_PROFILE, (service.getSuperAdminProfile(mSaId)));

    }

    private void retrofitCallBack() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String jsonObjectInfo = jsonObject.toString();
                switch (requestId) {
                    case GET_SA_PROFILE:
                        try {
                            JSONObject object = new JSONObject(jsonObjectInfo);

                            int status = object.getInt("status");
                            if (status == 1) {
                                JSONObject superAdminObect = object.getJSONObject("superadmin");
                                tvSaMob.setText(superAdminObect.getString("SA_Mob"));
                              //  tvUsenSAFulName.setText(superAdminObect.getString("SA_Name"));
                                tvUserSaName.setText(superAdminObect.getString("SA_Username"));
                                tvSaEmpRole.setText("Super Admin");
                                tvSaEmail.setText(superAdminObect.getString("SA_Email"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;
                }

            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("", "RequestId" + requestId);
                Log.d("", "RetrofitError" + error);
            }
        };
    }

    private void setUpToolBar() {
        TextView toolBarTitle = (TextView) toolbar.findViewById(R.id.tx_title);
        toolBarTitle.setText("Profile");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    private void init() {
        sessionmanager = new Sessionmanager(ActivitySAProfile.this);
        tvUserSaName = findViewById(R.id.tv_username);
      //  tvUsenSAFulName = findViewById(R.id.txt_user_name);
        tvSaMob = findViewById(R.id.tv_emp_mobno);
        tvSaEmail = findViewById(R.id.tv_emp_email);
        tvSaAdhar = findViewById(R.id.tv_emp_aadhar_number);
        tvSaEmpRole = findViewById(R.id.tv_emp_role);
        toolbar = findViewById(R.id.toolbar);
    }
}
