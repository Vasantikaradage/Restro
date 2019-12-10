package com.restrosmart.restrohotel.Admin;


import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
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
import com.restrosmart.restrohotel.Utils.FilePath;
import com.restrosmart.restrohotel.Utils.ImageFilePath;
import com.restrosmart.restrohotel.Utils.Sessionmanager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.restrosmart.restrohotel.ConstantVariables.HOTEL_DETAILS;
import static com.restrosmart.restrohotel.ConstantVariables.EDIT_BRANCH_DETAILS;
import static com.restrosmart.restrohotel.ConstantVariables.EMP_EDIT_DETAILS;
import static com.restrosmart.restrohotel.ConstantVariables.GET_ALL_EMPLOYEE;
import static com.restrosmart.restrohotel.ConstantVariables.PICK_GALLERY_IMAGE;
import static com.restrosmart.restrohotel.ConstantVariables.REQUEST_PERMISSION;
import static com.restrosmart.restrohotel.ConstantVariables.UPDATE_EMP_IMAGE;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.ROLE_ID;

public class ActivityProfile extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView tvTitle, tvUserFullName, tvUser, tvRole, tvActiveStatus, tvMobileNo, tvEmail, tvAdharNo, tvCurrentAddress,
            tvHotelname, tvBranchMob, tvBranchAddress, tvBranchEmail,
            tvGSTNNo, tvNoOfTables, tvBranchStartTime, tvBranchEndTime, tvBranchName, tvBranchPhone;

    private EditText etvTitle, etvUserFullName, etvUser, etvRole, etvActiveStatus, etvMobileNo, etvEmail, etvAdharNo, etvCurrentAddress,
            etvHotelname, etvBranchName, etvBranchMob, etvBranchAddress, etvBranchEmail,
            etvGSTNNo, etvNoOfTables, etvBranchphone;

    private int mHotelId, mBranchId;
    //private List<EmployeeForm> ArrayListEmployee;

    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private int roleId;
    private String Password;
    private ImageButton btneEditEmp, btnUpdateEmp, btnCancelEmp;
    private int mTblNo, employeeId;
    private String mGstnNo, empOldImage;
    private CircleImageView mPhoto;
    private FrameLayout frameLayoutCamera;
    private ImageButton updatePhoto;

    private String selectedFilePath, extension, selectedImage;
    private String selectedData = "null";
    private File selectedFile;

    private Bitmap bitmapImage;
    private ApiService apiService;
    private ArrayList<EmployeeForm> arrayListEmployee;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        init();
        setUpToolBar();

        Sessionmanager sharedPreferanceManage = new Sessionmanager(ActivityProfile.this);
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();
        mHotelId = Integer.parseInt(name_info.get(HOTEL_ID));
        roleId = Integer.parseInt((name_info.get(ROLE_ID)));


        Intent intent = getIntent();
        employeeId = intent.getIntExtra("empId", 0);
        getEmployeeList();
        //ArrayListEmployee = intent.getParcelableArrayListExtra("employeeList");


        frameLayoutCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePhoto.setVisibility(View.VISIBLE);
                if (checkPermission()) {
                    Intent imageIntent = new Intent();
                    imageIntent.setType("image/*");
                    imageIntent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(imageIntent, "Select Image"), PICK_GALLERY_IMAGE);
                } else {
                    requestPermission();
                }


            }
        });

        updatePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String image = empOldImage.substring(empOldImage.lastIndexOf("/") + 1);
                if ((selectedData == "null")) {
                    if (image.equals("def_user.png")) {
                        selectedImage = "";
                        extension = "";
                    } else {
                        extension = "";
                        selectedImage = "";
                    }
                    Picasso.with(ActivityProfile.this)
                            .load(empOldImage)
                            .resize(500, 500)
                            .into(mPhoto);

                } else {
                    selectedImage = selectedData;
                }

                initRetrofitCallback();
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack, ActivityProfile.this);
                mRetrofitService.retrofitData(UPDATE_EMP_IMAGE, (service.employeeImageUpdate(selectedImage, extension, image, employeeId, mHotelId)));
            }
        });

        tvVisible();
        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityProfile.this);
        mRetrofitService.retrofitData(HOTEL_DETAILS, (service.getHotelDetail(mHotelId)));

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
                        btneEditEmp.setVisibility(View.VISIBLE);
                        //  updateEmployee();
                    }
                });
            }


        });


        /*btnEditBranch.setOnClickListener(new View.OnClickListener() {
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
                        if (isValidBranch()) {

                            initRetrofitCallback();
                            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                            mRetrofitService = new RetrofitService(mResultCallBack, ActivityProfile.this);
                            mRetrofitService.retrofitData(EDIT_BRANCH_DETAILS, (service.
                                    editBranchDetails(tvBranchName.getText().toString(),
                                            tvBranchAddress.getText().toString(),
                                            tvBranchEmail.getText().toString(),
                                            (tvBranchMob.getText().toString()),
                                           (tvBranchPhone.getText().toString()),
                                            (etvGSTNNo.getText().toString()),
                                            Integer.parseInt(etvNoOfTables.getText().toString()),
                                            tvBranchStartTime.getText().toString(),
                                            tvBranchEndTime.getText().toString(),
                                            mHotelId, mBranchId)));
                        }
                    }
                });


                btnCancelBranchAccount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        refreshEditedBranchDetails();

                    }
                });

            }
        });*/


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
        retrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityProfile.this);
        mRetrofitService.retrofitData(GET_ALL_EMPLOYEE, (service.getallEmployees(mHotelId)));
    }

    private void retrofitCallBack() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {

                JsonObject jsonObject = response.body();
                String empInfo = jsonObject.toString();

                try {
                    JSONObject object = new JSONObject(empInfo);
                    int status = object.getInt("status");
                    if (status == 1) {

                        JSONArray jsonArray = object.getJSONArray("allemployee");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObjectEmp = jsonArray.getJSONObject(i);
                            EmployeeForm employeeForm = new EmployeeForm();
                            employeeForm.setEmpId(jsonObjectEmp.getInt("Emp_Id"));
                            employeeForm.setEmpName(jsonObjectEmp.getString("Emp_Name"));
                            employeeForm.setEmpImg(jsonObjectEmp.getString("Emp_Img"));
                            employeeForm.setEmpEmail(jsonObjectEmp.getString("Emp_Email"));
                            employeeForm.setEmpAddress(jsonObjectEmp.getString("Emp_Address"));
                            employeeForm.setUserName(jsonObjectEmp.getString("User_Name"));
                            employeeForm.setHotelName(jsonObjectEmp.getString("Hotel_Name"));
                            employeeForm.setRole(jsonObjectEmp.getString("Role"));
                            employeeForm.setEmpMob(jsonObjectEmp.getString("Emp_Mob"));
                            employeeForm.setEmpAdharId(jsonObjectEmp.getString("Emp_Adhar_Id"));
                            employeeForm.setActiveStatus(jsonObjectEmp.getInt("Active_Status"));
                            employeeForm.setRole_Id(jsonObjectEmp.getInt("Role_Id"));
                            arrayListEmployee.add(employeeForm);
                            getAdapter(arrayListEmployee);

                        }

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

    private void getAdapter(ArrayList<EmployeeForm> ArrayListEmployee) {
        for (int i = 0; i < ArrayListEmployee.size(); i++) {
            int empId = ArrayListEmployee.get(i).getEmpId();
            if (employeeId == empId) {

                btneEditEmp.setVisibility(View.VISIBLE);
                btnUpdateEmp.setVisibility(View.GONE);
                tvUserFullName.setText(ArrayListEmployee.get(i).getEmpName());
                tvUser.setText(ArrayListEmployee.get(i).getUserName());
                tvRole.setText(ArrayListEmployee.get(i).getRole());
                String status = String.valueOf(ArrayListEmployee.get(i).getActiveStatus());
                Password = ArrayListEmployee.get(i).getPassword();
                empOldImage = arrayListEmployee.get(i).getEmpImg();
                if (status == "1") {
                    tvActiveStatus.setText("Active");
                } else {
                    tvActiveStatus.setText("InActive");
                }

                // String image=ArrayListEmployee.get(i).getEmpImg();
                Picasso.with(ActivityProfile.this).load(empOldImage).resize(500, 500).into(mPhoto);


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
        if (isValidBranch()) {

            int table = Integer.parseInt(tvNoOfTables.getText().toString());


            initRetrofitCallback();
             String branchPhone=  etvBranchphone.getText().toString();
            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
            mRetrofitService = new RetrofitService(mResultCallBack, ActivityProfile.this);
            mRetrofitService.retrofitData(EDIT_BRANCH_DETAILS, (service.
                    editBranchDetails(etvBranchName.getText().toString(),
                            etvBranchAddress.getText().toString(),
                            etvBranchEmail.getText().toString(),
                            etvBranchMob.getText().toString(),
                            branchPhone,
                            tvGSTNNo.getText().toString(),
                            table, tvBranchStartTime.getText().toString(),
                            tvBranchEndTime.getText().toString(),
                            mHotelId, mBranchId)));
        }
    }

    private boolean isValidBranch() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (etvBranchName.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter Barnch name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etvBranchAddress.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter Barnch address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etvBranchMob.getText().toString().length()==0) {
            Toast.makeText(this, "Please enter Barnch mobile no", Toast.LENGTH_SHORT).show();
            return false;
        } else if ((etvBranchphone.getText().toString().length())==0)
        {
            Toast.makeText(this, "Please enter Barnch phone no", Toast.LENGTH_SHORT).show();
            return false;
        } else if (tvGSTNNo.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter GSTN  no", Toast.LENGTH_SHORT).show();
            return false;
        } else if (tvBranchStartTime.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please select start time", Toast.LENGTH_SHORT).show();
            return false;
        } else if (tvBranchEndTime.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please select end time", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etvBranchMob.getText().toString().length() < 10 ) {
            Toast.makeText(this, "Please enter valid  mobile no", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(etvBranchphone.getText().toString().length() < 12){
            Toast.makeText(this, "Please enter valid  phone no", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etvBranchEmail.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter  email id", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!etvBranchEmail.getText().toString().matches(emailPattern)) {
            Toast.makeText(this, "Please enter valid email id", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject object = response.body();
                String objectInfo = object.toString();

                switch (requestId) {
                    case HOTEL_DETAILS:

                        try {
                            JSONObject jsonObject = new JSONObject(objectInfo);
                            int status = jsonObject.getInt("status");

                            if (status == 1) {
                                JSONObject object1 = jsonObject.getJSONObject("hoteldetail");
                                tvHotelname.setText(object1.getString("Hotel_Name").toString());
                            //    tvBranchName.setText(object1.getString("Branch_Name").toString());
                                //  mBranchName=object1.getString("Branch_Name").toString();
                                tvBranchStartTime.setText(object1.getString("Start_Time").toString());
                                tvBranchEndTime.setText(object1.getString("End_Time").toString());

                                tvBranchAddress.setText(object1.getString("Hotel_Address").toString());
                                String email = object1.getString("Hotel_Email").toString();
                                tvBranchEmail.setText(email);
                                String hotelMob = object1.getString("Hotel_Mob").toString();
                                tvBranchMob.setText(hotelMob);

                                String branchPhone = object1.getString("Hotel_Phone").toString();
                                tvBranchPhone.setText(branchPhone);

                                mGstnNo = object1.getString("Hotel_Gstinno");
                                mTblNo = object1.getInt("Hotel_Table_Count");

                                tvGSTNNo.setText(mGstnNo);
                                tvNoOfTables.setText(object1.getString("Hotel_Table_Count").toString());
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

                    case UPDATE_EMP_IMAGE:
                        try {
                            JSONObject jsonObject = new JSONObject(objectInfo);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityProfile.this, "Photo Updated Successfully", Toast.LENGTH_LONG).show();
                                updatePhoto.setVisibility(View.GONE);
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
                Log.d("","requestId"+requestId);
                Log.d("","retrofitError"+error);
               // Toast.makeText(ActivityProfile.this, "" + error, Toast.LENGTH_LONG).show();


            }
        };
    }

    private void refreshEditedBranchDetails() {


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
       // btnEditBranch.setVisibility(View.VISIBLE);

        tvBranchStartTime.setTextColor(Color.parseColor("#7A1B5C"));
        tvBranchEndTime.setTextColor(Color.parseColor("#7A1B5C"));

        etvGSTNNo.setVisibility(View.GONE);
        etvNoOfTables.setVisibility(View.GONE);
      //  btnUpdateBranch.setVisibility(View.GONE);
      //  btnCancelBranch.setVisibility(View.GONE);
        etvBranchName.setVisibility(View.GONE);
        etvBranchMob.setVisibility(View.GONE);
        etvBranchphone.setVisibility(View.GONE);
        etvBranchEmail.setVisibility(View.GONE);
        etvBranchAddress.setVisibility(View.GONE);


        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityProfile.this);
        mRetrofitService.retrofitData(HOTEL_DETAILS, (service.getHotelDetail(mHotelId)));

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
        String image = empOldImage.substring(empOldImage.lastIndexOf("/") + 1);
        if ((selectedData == "null")) {
            if (image.equals("def_user.png")) {
                selectedImage = "";
                extension = "";
            } else {
                extension = "";
                selectedImage = "";
            }
            Picasso.with(ActivityProfile.this)
                    .load(empOldImage)
                    .resize(500, 500)
                    .into(mPhoto);

        } else {
            selectedImage = selectedData;
        }
        if (isValidEmployee()) {
            initRetrofitCallback();
            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
            mRetrofitService = new RetrofitService(mResultCallBack, ActivityProfile.this);
            mRetrofitService.retrofitData(
                    EMP_EDIT_DETAILS, (service.
                            editEmployeeDetail(employeeId,
                                    etvUserFullName.getText().toString(),
                                    selectedImage, image, extension,
                                    etvMobileNo.getText().toString(),
                                    etvEmail.getText().toString(),
                                    etvCurrentAddress.getText().toString(),
                                    tvUser.getText().toString(),
                                    (etvAdharNo.getText().toString()),
                                    mHotelId)));
        }
    }

    private boolean isValidEmployee() {

        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        String mobilePattern = "(0/91)?[7-9][0-9]{9}";
        if (etvUserFullName.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter full name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etvCurrentAddress.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter Address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (tvUser.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!etvEmail.getText().toString().matches(emailPattern)) {
            Toast.makeText(this, "Please enter valid email id", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etvMobileNo.getText().toString().length()==0) {
            Toast.makeText(this, "Please enter  mobile no", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etvMobileNo.getText().toString().length() < 10) {
            Toast.makeText(this, "Please enter valid mobile no", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etvAdharNo.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter aadhar no", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etvAdharNo.getText().toString().length() < 12) {
            Toast.makeText(this, "Please enter  valid aadhar no", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
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
        arrayListEmployee = new ArrayList<>();

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
        tvBranchPhone = findViewById(R.id.tv_branch_phone);


        etvHotelname = findViewById(R.id.etv_hotel_name);
        etvBranchName = findViewById(R.id.etv_branch_name);
        etvBranchEmail = findViewById(R.id.etv_branch_email);
        etvBranchAddress = findViewById(R.id.etv_branch_address);
        etvBranchMob = findViewById(R.id.etv_branch_mobno);
        etvBranchphone = findViewById(R.id.etv_branch_phone);
        tvGSTNNo = findViewById(R.id.tv_gstn_no);
        tvNoOfTables = findViewById(R.id.tv_no_of_tables);
        tvBranchStartTime = findViewById(R.id.tv_branch_start_time);
        tvBranchEndTime = findViewById(R.id.tv_branch_end_time);
        etvGSTNNo = findViewById(R.id.etv_gstn_no);
        etvNoOfTables = findViewById(R.id.etv_no_of_tables);
        btneEditEmp = findViewById(R.id.btn_edit_emp);
        btnUpdateEmp = findViewById(R.id.btn_update_emp);
        btnCancelEmp = findViewById(R.id.btn_cancel_emp);

        mPhoto = findViewById(R.id.img_user_photo);
        frameLayoutCamera = findViewById(R.id.iv_select_image);
        updatePhoto = findViewById(R.id.btn_update_photo);

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

    private void requestPermission() {
        ActivityCompat.requestPermissions((Activity) this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);

    }

    private boolean checkPermission() {

        int result = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub


        if (resultCode == RESULT_OK) {
            if (data == null) {
                //no data present
                return;
            }

            Uri selectedFileUri = data.getData();
            selectedFilePath = FilePath.getPath(this, selectedFileUri);

            Bitmap categoryBitmap = null;
            try {
                categoryBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedFileUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String picturePath = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                picturePath = ImageFilePath.getPath(this, selectedFileUri);
            }

            selectedFile = new File(selectedFilePath);
            int file_size = Integer.parseInt(String.valueOf(selectedFile.length() / 1024));     //calculate size of image in KB
            if (file_size <= 1024) {

                extension = getFileExtension(selectedFile);


                bitmapImage = exifInterface(picturePath, categoryBitmap);
                selectedData = getImageString(bitmapImage);

                byte[] decodedString = Base64.decode(selectedData, Base64.NO_WRAP);
                InputStream inputStream = new ByteArrayInputStream(decodedString);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                mPhoto.setImageBitmap(bitmap);

                /*if (selectedFilePath != null && !selectedFilePath.equals("")) {



                    //tvStudyFileName.setText(selectedFilePath);
                   // alertDialog.dismiss();
                } else {
                    Toast.makeText(ActivityNewAddEmployee.this, "cannot upload image", Toast.LENGTH_SHORT).show();
                }*/
            } else {
                Toast.makeText(ActivityProfile.this, "upload image should be 1mb", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getImageString(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private Bitmap exifInterface(String filePath, Bitmap bitmap) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert exif != null;
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        return rotateBitmap(bitmap, orientation);
    }

    private Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getFileExtension(File selectedFile) {
        String fileName = selectedFile.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";

    }
}
