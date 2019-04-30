package com.restrosmart.restrohotel.Admin;


import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.BRANCH_DETAILS;
import static com.restrosmart.restrohotel.ConstantVariables.EDIT_BRANCH_DETAILS;
import static com.restrosmart.restrohotel.ConstantVariables.EMP_EDIT_DETAILS;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.ROLE_ID;

public class ActivityProfile extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView tvTitle, tvUserFullName, tvUser, tvRole, tvActiveStatus, tvMobileNo, tvEmail, tvAdharNo, tvCurrentAddress,
            tvHotelname, tvBranchMob, tvBranchAddress, tvBranchEmail,
            tvGSTNNo, tvNoOfTables, tvBranchStartTime, tvBranchEndTime, tvBranchName,tvBranchPhone;

    private EditText etvTitle, etvUserFullName, etvUser, etvRole, etvActiveStatus, etvMobileNo, etvEmail, etvAdharNo, etvCurrentAddress,
            etvHotelname, etvBranchName, etvBranchMob, etvBranchAddress, etvBranchEmail,
            etvGSTNNo, etvNoOfTables,etvBranchphone;

    private int mHotelId, mBranchId;
    private List<EmployeeForm> ArrayListEmployee;

    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private int roleId;
    private String Password;
    private ImageButton btneEditEmp, btnUpdateEmp, btnCancelEmp,
            btnEditBranch, btnUpdateBranch, btnCancelBranch,
            btnEditBranchAccount, btnUpdateBranchAccount, btnCancelBranchAccount;
    private int mTblNo, employeeId;
    private String mGstnNo;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        init();
        setUpToolBar();
        getEmployeeList();

        Intent intent = getIntent();
        employeeId = intent.getIntExtra("empId", 0);
        //ArrayListEmployee = intent.getParcelableArrayListExtra("employeeList");

        Sessionmanager sharedPreferanceManage = new Sessionmanager(ActivityProfile.this);
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();
        mHotelId = Integer.parseInt(name_info.get(HOTEL_ID));
        mBranchId = Integer.parseInt(name_info.get(BRANCH_ID));
        roleId = Integer.parseInt((name_info.get(ROLE_ID)));

        tvVisible();
        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityProfile.this);
        mRetrofitService.retrofitData(BRANCH_DETAILS, (service.getBranchDetail(mHotelId,
                mBranchId)));

        btneEditEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               tvInVisible();
               etvVisible();

                etvUserFullName.requestFocus();
                etvEmail.setText(tvEmail.getText().toString());
                etvUserFullName.setText(tvUserFullName.getText().toString());
                etvAdharNo.setText(tvAdharNo.getText().toString());
                etvCurrentAddress.setText(tvCurrentAddress.getText().toString());
                etvMobileNo.setText(tvMobileNo.getText().toString());


                btnUpdateEmp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateEmployee();
                    }
                });
                btnCancelEmp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        tvVisible();
                        etvInVisible();
                        getEmployeeList();
                        //  updateEmployee();
                    }
                });
            }


        });


        btnEditBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBranchName.setVisibility(View.GONE);
                tvBranchMob.setVisibility(View.GONE);
                tvBranchPhone.setVisibility(View.GONE);
                tvBranchEmail.setVisibility(View.GONE);
                tvBranchAddress.setVisibility(View.GONE);
                btnEditBranch.setVisibility(View.GONE);
                btnUpdateBranch.setVisibility(View.VISIBLE);
                btnCancelBranch.setVisibility(View.VISIBLE);


                etvBranchName.setVisibility(View.VISIBLE);
                etvBranchMob.setVisibility(View.VISIBLE);
                etvBranchphone.setVisibility(View.VISIBLE);
                etvBranchEmail.setVisibility(View.VISIBLE);
                etvBranchAddress.setVisibility(View.VISIBLE);
                etvBranchName.requestFocus();

                etvBranchName.setText(tvBranchName.getText().toString());
                etvBranchMob.setText(tvBranchMob.getText().toString());
                etvBranchEmail.setText(tvBranchEmail.getText().toString());
                etvBranchphone.setText(tvBranchPhone.getText().toString());
                etvBranchAddress.setText(tvBranchAddress.getText().toString());
                btnUpdateBranch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateBranch();
                    }
                });

                btnCancelBranch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        refreshEditedBranchDetails();
                    }
                });

            }
        });

        btnEditBranchAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnCancelBranchAccount.setVisibility(View.VISIBLE);
                btnUpdateBranchAccount.setVisibility(View.VISIBLE);

                tvGSTNNo.setVisibility(View.GONE);
                tvNoOfTables.setVisibility(View.GONE);
                tvBranchEndTime.setVisibility(View.GONE);
                tvBranchStartTime.setVisibility(View.GONE);

                etvGSTNNo.setVisibility(View.VISIBLE);
                etvNoOfTables.setVisibility(View.VISIBLE);
                tvBranchEndTime.setVisibility(View.VISIBLE);
                tvBranchStartTime.setVisibility(View.VISIBLE);
                tvBranchStartTime.setTextColor(getResources().getColor(R.color.gray));
                tvBranchEndTime.setTextColor(getResources().getColor(R.color.gray));

                etvGSTNNo.setText(mGstnNo);
                etvNoOfTables.setText(tvNoOfTables.getText().toString());

                tvBranchStartTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar mCurrentTime = Calendar.getInstance();
                        int mHour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                        int mMinute = mCurrentTime.get(Calendar.MINUTE);

                        TimePickerDialog mTimePickerDialog = new TimePickerDialog(ActivityProfile.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                Calendar myCalendar = Calendar.getInstance();
                                myCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                                myCalendar.set(Calendar.MINUTE, selectedMinute);
                                String myFormat = "hh:mm a"; //Change as you need
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
                                tvBranchStartTime.setText(sdf.format(myCalendar.getTime()));
                                //  selectedTime = sdf.format(myCalendar.getTime());
                            }
                        }, mHour, mMinute, false);
                        mTimePickerDialog.show();

                    }
                });

                tvBranchEndTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar mCurrentTime = Calendar.getInstance();
                        int mHour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                        int mMinute = mCurrentTime.get(Calendar.MINUTE);

                        TimePickerDialog mTimePickerDialog = new TimePickerDialog(ActivityProfile.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                Calendar myCalendar = Calendar.getInstance();
                                myCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                                myCalendar.set(Calendar.MINUTE, selectedMinute);
                                String myFormat = "hh:mm a"; //Change as you need
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
                                tvBranchEndTime.setText(sdf.format(myCalendar.getTime()));
                                //  selectedTime = sdf.format(myCalendar.getTime());
                            }
                        }, mHour, mMinute, false);

                        mTimePickerDialog.show();
                    }
                });


                btnUpdateBranchAccount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        initRetrofitCallback();
                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        mRetrofitService = new RetrofitService(mResultCallBack, ActivityProfile.this);
                        mRetrofitService.retrofitData(EDIT_BRANCH_DETAILS, (service.
                                editBranchDetails(tvBranchName.getText().toString(),
                                        tvBranchAddress.getText().toString(),
                                        tvBranchEmail.getText().toString(),
                                        Integer.parseInt(tvBranchMob.getText().toString()),
                                        Integer.parseInt(tvBranchPhone.getText().toString()),

                                        (etvGSTNNo.getText().toString()),
                                        Integer.parseInt(etvNoOfTables.getText().toString()),
                                        tvBranchStartTime.getText().toString(),
                                        tvBranchEndTime.getText().toString(),
                                        mHotelId, mBranchId)));
                    }
                });


                btnCancelBranchAccount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        refreshEditedBranchDetails();

                    }
                });

            }
        });


    }

    private void etvVisible() {
        btnUpdateEmp.setVisibility(View.VISIBLE);
        btnCancelEmp.setVisibility(View.VISIBLE);
        etvUserFullName.setVisibility(View.VISIBLE);
        etvEmail.setVisibility(View.VISIBLE);
        etvAdharNo.setVisibility(View.VISIBLE);
        etvMobileNo.setVisibility(View.VISIBLE);
        etvCurrentAddress.setVisibility(View.VISIBLE);
    }

    private void tvInVisible() {
        tvUserFullName.setVisibility(View.GONE);
        tvEmail.setVisibility(View.GONE);
        tvAdharNo.setVisibility(View.GONE);
        tvMobileNo.setVisibility(View.GONE);
        btneEditEmp.setVisibility(View.GONE);
        tvCurrentAddress.setVisibility(View.GONE);
    }

    private void tvVisible() {
        tvUserFullName.setVisibility(View.VISIBLE);
        tvRole.setVisibility(View.VISIBLE);
        tvActiveStatus.setVisibility(View.VISIBLE);
        tvEmail.setVisibility(View.VISIBLE);
        tvAdharNo.setVisibility(View.VISIBLE);
        tvMobileNo.setVisibility(View.VISIBLE);
        tvCurrentAddress.setVisibility(View.VISIBLE);
        tvGSTNNo.setVisibility(View.VISIBLE);
        tvNoOfTables.setVisibility(View.VISIBLE);
    }

    private void getEmployeeList() {
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        Call<List<EmployeeForm>> call = service.getallEmployees("1", "1");
        call.enqueue(new Callback<List<EmployeeForm>>() {
            @Override
            public void onResponse(Call<List<EmployeeForm>> call, Response<List<EmployeeForm>> response) {
                ArrayListEmployee = response.body();
                getData(ArrayListEmployee);

            }

            @Override
            public void onFailure(Call<List<EmployeeForm>> call, Throwable t) {

            }
        });
    }

    private void getData(List<EmployeeForm> ArrayListEmployee) {
        for (int i = 0; i < ArrayListEmployee.size(); i++) {
            int empId = ArrayListEmployee.get(i).getEmpId();
            if (employeeId == empId) {
                tvUserFullName.setText(ArrayListEmployee.get(i).getEmpName());
                tvUser.setText(ArrayListEmployee.get(i).getUserName());
                tvRole.setText(ArrayListEmployee.get(i).getRole());
                String status = String.valueOf(ArrayListEmployee.get(i).getActiveStatus());
                Password = ArrayListEmployee.get(i).getPassword();
                if (status == "1") {
                    tvActiveStatus.setText("Active");
                } else {
                    tvActiveStatus.setText("InActive");
                }

                String mobNo = ArrayListEmployee.get(i).getEmpMob();
                tvMobileNo.setText(mobNo);
                tvEmail.setText(ArrayListEmployee.get(i).getEmpEmail());
                String AdharNo = ArrayListEmployee.get(i).getEmpAdharId();
                tvAdharNo.setText(AdharNo);
                tvCurrentAddress.setText(ArrayListEmployee.get(i).getEmpAddress());
            }
        }

    }


    private void updateBranch() {
        int mob = Integer.parseInt(etvBranchMob.getText().toString());
        int table = Integer.parseInt(tvNoOfTables.getText().toString());
        int phone= Integer.parseInt(etvBranchphone.getText().toString());

        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityProfile.this);
        mRetrofitService.retrofitData(EDIT_BRANCH_DETAILS, (service.
                editBranchDetails(etvBranchName.getText().toString(),
                        etvBranchAddress.getText().toString(),
                        etvBranchEmail.getText().toString(),
                        mob,
                        phone,
                        tvGSTNNo.getText().toString(),
                        table, tvBranchStartTime.getText().toString(),
                        tvBranchEndTime.getText().toString(),
                        mHotelId, mBranchId)));
    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject object = response.body();
                String objectInfo = object.toString();

                switch (requestId) {
                    case BRANCH_DETAILS:

                        try {
                            JSONObject jsonObject = new JSONObject(objectInfo);
                            int status = jsonObject.getInt("status");

                            if (status == 1) {
                                JSONObject object1 = jsonObject.getJSONObject("Bdetail");
                                tvHotelname.setText(object1.getString("Hotel_Name").toString());
                                tvBranchName.setText(object1.getString("Branch_Name").toString());
                                //  mBranchName=object1.getString("Branch_Name").toString();
                                tvBranchStartTime.setText(object1.getString("Start_Time").toString());
                                tvBranchEndTime.setText(object1.getString("End_Time").toString());

                                tvBranchAddress.setText(object1.getString("Branch_Address").toString());
                                String email = object1.getString("Branch_Email").toString();
                                tvBranchEmail.setText(email);
                                String hotelMob = object1.getString("Branch_Mob").toString();
                                tvBranchMob.setText(hotelMob);

                                String branchPhone = object1.getString("Branch_Phone").toString();
                                tvBranchPhone.setText(branchPhone);

                                mGstnNo = object1.getString("Branch_Gstnno");
                                mTblNo = object1.getInt("Branch_Table_Count");

                                tvGSTNNo.setText(mGstnNo);
                                tvNoOfTables.setText(object1.getString("Branch_Table_Count").toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;
                    case EMP_EDIT_DETAILS:

                        try {
                            JSONObject jsonObject = new JSONObject(objectInfo);

                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityProfile.this, "Updated Successfully", Toast.LENGTH_LONG).show();
                               tvVisible();
                               etvInVisible();
                               getEmployeeList();

                            } else {
                                Toast.makeText(ActivityProfile.this, "Try Again..", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case EDIT_BRANCH_DETAILS:

                        try {
                            JSONObject jsonObject = new JSONObject(objectInfo);
                            int status = jsonObject.getInt("status");

                            if (status == 1) {
                                Toast.makeText(ActivityProfile.this, "Updated Successfully", Toast.LENGTH_LONG).show();
                                refreshEditedBranchDetails();

                            } else {
                                Toast.makeText(ActivityProfile.this, "Try Again..", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;


                }

            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Toast.makeText(ActivityProfile.this, "" + error, Toast.LENGTH_LONG).show();


            }
        };
    }

    private void refreshEditedBranchDetails() {
        btnCancelBranchAccount.setVisibility(View.GONE);
        btnUpdateBranchAccount.setVisibility(View.GONE);

        tvGSTNNo.setVisibility(View.VISIBLE);
        tvNoOfTables.setVisibility(View.VISIBLE);
        tvBranchEndTime.setVisibility(View.VISIBLE);
        tvBranchStartTime.setVisibility(View.VISIBLE);
        tvBranchEndTime.setVisibility(View.VISIBLE);
        tvBranchStartTime.setVisibility(View.VISIBLE);
        tvBranchName.setVisibility(View.VISIBLE);
        tvBranchMob.setVisibility(View.VISIBLE);
        tvBranchPhone.setVisibility(View.VISIBLE);
        tvBranchEmail.setVisibility(View.VISIBLE);
        tvBranchAddress.setVisibility(View.VISIBLE);
        btnEditBranch.setVisibility(View.VISIBLE);

        tvBranchStartTime.setTextColor(Color.parseColor("#7A1B5C"));
        tvBranchEndTime.setTextColor(Color.parseColor("#7A1B5C"));

        etvGSTNNo.setVisibility(View.GONE);
        etvNoOfTables.setVisibility(View.GONE);
        btnUpdateBranch.setVisibility(View.GONE);
        btnCancelBranch.setVisibility(View.GONE);
        etvBranchName.setVisibility(View.GONE);
        etvBranchMob.setVisibility(View.GONE);
        etvBranchphone.setVisibility(View.GONE);
        etvBranchEmail.setVisibility(View.GONE);
        etvBranchAddress.setVisibility(View.GONE);


        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityProfile.this);
        mRetrofitService.retrofitData(BRANCH_DETAILS, (service.getBranchDetail(mHotelId,
                mBranchId)));

    }

    private void etvInVisible() {
        btnUpdateEmp.setVisibility(View.GONE);
        btnCancelEmp.setVisibility(View.GONE);
        etvUserFullName.setVisibility(View.GONE);
        etvEmail.setVisibility(View.GONE);
        etvAdharNo.setVisibility(View.GONE);
        etvMobileNo.setVisibility(View.GONE);
        etvCurrentAddress.setVisibility(View.GONE);
    }

    private void updateEmployee() {
        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityProfile.this);
        mRetrofitService.retrofitData(EMP_EDIT_DETAILS, (service.
                editEmployeeDetail(employeeId,
                        etvUserFullName.getText().toString(),
                        etvMobileNo.getText().toString(),
                        etvEmail.getText().toString(),
                        etvCurrentAddress.getText().toString(),
                        tvUser.getText().toString(),
                        mHotelId,
                        mBranchId,
                        roleId,
                        Password
                )));
    }

    private void init() {
        mToolbar = findViewById(R.id.toolbar);
        tvTitle = (TextView) mToolbar.findViewById(R.id.tx_title);
        tvUser = findViewById(R.id.tx_user_name);
        tvUserFullName = findViewById(R.id.tv_user_name);
        tvRole = findViewById(R.id.tv_role);
        tvActiveStatus = findViewById(R.id.tv_Active_status);
        tvMobileNo = findViewById(R.id.tv_emp_mobno);
        tvEmail = findViewById(R.id.tv_emp_email);
        tvAdharNo = findViewById(R.id.tv_emp_aadhar_number);
        tvCurrentAddress = findViewById(R.id.tv_emp_address);

        etvUserFullName = findViewById(R.id.etv_user_name);
        etvMobileNo = findViewById(R.id.etv_emp_mobno);
        etvEmail = findViewById(R.id.etv_emp_email);
        etvAdharNo = findViewById(R.id.etv_emp_aadhar_number);
        etvCurrentAddress = findViewById(R.id.etv_emp_address);


        tvHotelname = findViewById(R.id.tv_hotel_name);
        tvBranchName = findViewById(R.id.tv_branch_name);
        tvBranchEmail = findViewById(R.id.tv_branch_email);
        tvBranchAddress = findViewById(R.id.tv_branch_address);
        tvBranchMob = findViewById(R.id.tv_branch_mobno);
        tvBranchPhone=findViewById(R.id.tv_branch_phone);


        etvHotelname = findViewById(R.id.etv_hotel_name);
        etvBranchName = findViewById(R.id.etv_branch_name);
        etvBranchEmail = findViewById(R.id.etv_branch_email);
        etvBranchAddress = findViewById(R.id.etv_branch_address);
        etvBranchMob = findViewById(R.id.etv_branch_mobno);
        etvBranchphone=findViewById(R.id.etv_branch_phone);

        tvGSTNNo = findViewById(R.id.tv_gstn_no);
        tvNoOfTables = findViewById(R.id.tv_no_of_tables);
        tvBranchStartTime = findViewById(R.id.tv_branch_start_time);
        tvBranchEndTime = findViewById(R.id.tv_branch_end_time);

        etvGSTNNo = findViewById(R.id.etv_gstn_no);
        etvNoOfTables = findViewById(R.id.etv_no_of_tables);

        btneEditEmp = findViewById(R.id.btn_edit_emp);
        btnEditBranch = findViewById(R.id.btn_edit_branch);
        btnUpdateBranch = findViewById(R.id.btn_update_branch);
        btnCancelBranch = findViewById(R.id.btn_cancel_branch);
        btnEditBranchAccount = findViewById(R.id.btn_edit_branch_account);

        btnUpdateEmp = findViewById(R.id.btn_update_emp);
        btnCancelEmp = findViewById(R.id.btn_cancel_emp);

        btnUpdateBranchAccount = findViewById(R.id.btn_update_branch_account);
        btnCancelBranchAccount = findViewById(R.id.btn_cancel_branch_account);
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