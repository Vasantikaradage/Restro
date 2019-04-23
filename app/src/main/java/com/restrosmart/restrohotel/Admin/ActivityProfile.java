package com.restrosmart.restrohotel.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.EmployeeForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.BRANCH_DETAILS;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class ActivityProfile extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView tvTitle, tvUserName,tvUser, tvRole, tvActiveStatus, tvMobileNo, tvEmail, tvAdharNo, tvCurrentAddress,
            tvHotelname, tvHotelMob, tvHotelAddress, tvHotelEmail,
            tvGSTNNo, tvNoOfTables, tvHotelTiming;
    private  int mHotelId,mBranchId;
    private List<EmployeeForm> ArrayListEmployee;
    private  RetrofitService mRetrofitService;
    private IResult mResultCallBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        init();
        setUpToolBar();

        Intent intent=getIntent();
        int employeeId=intent.getIntExtra("empId",0);
        ArrayListEmployee=intent.getParcelableArrayListExtra("employeeList");

        Sessionmanager sharedPreferanceManage = new Sessionmanager(ActivityProfile.this);
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();
        mHotelId = Integer.parseInt(name_info.get(HOTEL_ID));
        mBranchId = Integer.parseInt(name_info.get(BRANCH_ID));

        for(int i=0;i<ArrayListEmployee.size();i++)
        {
           int empId= ArrayListEmployee.get(i).getEmpId();
           if(employeeId == empId)
            {
                tvUserName.setText(ArrayListEmployee.get(i).getEmpName());
                tvUser.setText(ArrayListEmployee.get(i).getEmpName());
                tvRole.setText(ArrayListEmployee.get(i).getRole());
                String status= String.valueOf(ArrayListEmployee.get(i).getActiveStatus());
               tvActiveStatus.setText(status);
               String mobNo=ArrayListEmployee.get(i).getEmpMob();
               tvMobileNo.setText(mobNo);
                tvEmail.setText(ArrayListEmployee.get(i).getEmpEmail());
                String AdharNo=ArrayListEmployee.get(i).getEmpAdharId();
                tvAdharNo.setText(AdharNo);
                tvCurrentAddress.setText(ArrayListEmployee.get(i).getEmpAddress());
            }

            initRetrofitCallback();
            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
            mRetrofitService = new RetrofitService(mResultCallBack, ActivityProfile.this);
            mRetrofitService.retrofitData(BRANCH_DETAILS, (service.getBranchDetail(mHotelId,
                   mBranchId)));
        }



    }

    private void initRetrofitCallback() {
        mResultCallBack=new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject object=response.body();
                String objectInfo=object.toString();

                try {
                    JSONObject jsonObject=new JSONObject(objectInfo);
                    int status=jsonObject.getInt("status");

                    if(status==1)
                    {
                        JSONObject object1=jsonObject.getJSONObject("Bdetail");
                        tvHotelname.setText(object1.getString("Hotel_Name").toString());
                      //  mBranchName=object1.getString("Branch_Name").toString();
                        tvHotelAddress.setText(object1.getString("Branch_Address").toString());
                        String email=object1.getString("Branch_Email").toString();
                        tvHotelEmail.setText(email);
                        String hotelMob=object1.getString("Branch_Mob").toString();
                        tvHotelMob.setText(hotelMob);
                        tvGSTNNo.setText(object1.getString("Branch_Gstnno").toString());
                        tvNoOfTables.setText(object1.getString("Branch_Table_Count").toString());
                       tvHotelTiming.setText(object1.getString("Branch_Timing").toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void notifyError(int requestId, Throwable error) {

            }
        };
    }

    private void init() {
        mToolbar=findViewById(R.id.toolbar);
        tvTitle = (TextView) mToolbar.findViewById(R.id.tx_title);
        tvUser=findViewById(R.id.tx_user_name);
        tvUserName = findViewById(R.id.tv_user_name);
        tvRole = findViewById(R.id.tv_role);
        tvActiveStatus = findViewById(R.id.tv_Active_status);
        tvMobileNo = findViewById(R.id.tv_emp_mobno);
        tvEmail = findViewById(R.id.tv_emp_email);
        tvAdharNo = findViewById(R.id.tv_emp_aadhar_number);
        tvCurrentAddress = findViewById(R.id.tv_emp_address);

        tvHotelname = findViewById(R.id.tv_hotel_name);
        tvHotelEmail = findViewById(R.id.tv_hotel_email);
        tvHotelAddress = findViewById(R.id.tv_hotel_address);
        tvHotelMob = findViewById(R.id.tv_hotel_mobno);

        tvGSTNNo = findViewById(R.id.tv_gstn_no);
        tvHotelTiming = findViewById(R.id.tv_no_of_tables);
        tvNoOfTables = findViewById(R.id.tv_hotel_timing);
    }

    private void setUpToolBar() {


        tvTitle.setText("Admin Profile");
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
