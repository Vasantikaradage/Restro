package com.restrosmart.restrohotel.Admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.BranchForm;
import com.restrosmart.restrohotel.Model.EmployeeForm;
import com.restrosmart.restrohotel.Model.RoleForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.ADD_NEW_EMPLOYEE;
import static com.restrosmart.restrohotel.ConstantVariables.BRANCH_INFO;
import static com.restrosmart.restrohotel.ConstantVariables.EMP_EDIT_DETAILS;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.ROLE_ID;

public class ActivityNewAddEmployee extends AppCompatActivity {
    private EditText etvName, etMob, etEmail, etUsername, etAdhar, etPass, etConPass, etAddress;

    private CircleImageView imageView;
    private Spinner spDesignation, spBranch;
    private String password;
    private Button register;
    private FrameLayout select_image;
    private int roleId, hotelId, branchId, adminId, branchInfo;
    private ArrayList<EmployeeForm> employeeDetails;
    private int emp_id;
    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private ArrayList<BranchForm> arrayListBranch;
    List<RoleForm> employeeRoles;
    ArrayList<RoleForm> arrayListRole;
    private int desginagtionSelId, branchSelId, position;
    private TextInputLayout txPass, txConPass;
    private LinearLayout llPassword, llConPassword, llBranch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_employee);

        init();

        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbar mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolBarTitle = (TextView) mTopToolbar.findViewById(R.id.tx_title);
        toolBarTitle.setText("Add New Employee");

        setSupportActionBar(mTopToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);


        Sessionmanager sharedPreferanceManage = new Sessionmanager(ActivityNewAddEmployee.this);
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();
        adminId = Integer.parseInt(name_info.get(ROLE_ID));
        hotelId = Integer.parseInt(name_info.get(HOTEL_ID));


        Intent intent = getIntent();
        employeeDetails = intent.getParcelableArrayListExtra("Emp_detail");
        emp_id = intent.getIntExtra("empId", 0);

        if (emp_id != 0) {


            for (int i = 0; i < employeeDetails.size(); i++) {
                llBranch.setVisibility(View.GONE);
                llConPassword.setVisibility(View.GONE);
                llPassword.setVisibility(View.GONE);

                // txPass.setVisibility(View.GONE);
                // txConPass.setVisibility(View.GONE);
                if (emp_id == employeeDetails.get(i).getEmpId()) {
                    etvName.setText(employeeDetails.get(i).getEmpName());
                    etUsername.setText(employeeDetails.get(i).getUserName());
                    etAddress.setText(employeeDetails.get(i).getEmpAddress());
                    etAdhar.setText(employeeDetails.get(i).getEmpAdharId());
                    etEmail.setText(employeeDetails.get(i).getEmpEmail());
                    etMob.setText(employeeDetails.get(i).getEmpMob());

                    desginagtionSelId = employeeDetails.get(i).getRole_Id();
                    branchSelId = employeeDetails.get(i).getBranch_Id();
                    password = employeeDetails.get(i).getPassword();

                    branchInfo = employeeDetails.get(i).getBranch_Id();


                    retrofitArrayDesignationCall();
                    // retrofitArrayBranchCall();

                    register.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            initRetrofitCallback();
                            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                            mRetrofitService = new RetrofitService(mResultCallBack, ActivityNewAddEmployee.this);
                            mRetrofitService.retrofitData(EMP_EDIT_DETAILS, (service.
                                    editEmployeeDetail(emp_id,
                                            etvName.getText().toString(),
                                            etMob.getText().toString(),
                                            etEmail.getText().toString(),
                                            etAddress.getText().toString(),
                                            etUsername.getText().toString(),
                                            hotelId,
                                            branchInfo,
                                            roleId, password

                                    )));
                        }
                    });


                }
            }
        } else {
            // txPass.setVisibility(View.VISIBLE);
            // txConPass.setVisibility(View.VISIBLE);

            llBranch.setVisibility(View.VISIBLE);
            llConPassword.setVisibility(View.VISIBLE);
            llPassword.setVisibility(View.VISIBLE);


            retrofitArrayDesignationCall();
            retrofitArrayBranchCall();


       /* String[] desig = {"Select Designation", "Admin", "Employee"};
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(ActivityAddNewEmployee.this, android.R.layout.simple_list_item_1, desig);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDesignation.setAdapter(stringArrayAdapter);*/


          /*  select_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    selectImage();

                }
            });
*/

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Picasso.with(getApplicationContext()).load("https://images.pexels.com/photos/67636/rose-blue-flower-rose-blooms-67636.jpeg?auto=compress&cs=tinysrgb&h=650&w=940").into(imageView);

                    Map<String, String> signup = new HashMap<>();
                    signup.put("Emp_Name", etvName.getText().toString());
                    signup.put("Emp_Mob", etMob.getText().toString().trim());
                    signup.put("Emp_Email", etEmail.getText().toString().trim());
                    signup.put("User_Name", etUsername.getText().toString().trim());
                    signup.put("Emp_Adhar_Id", etAdhar.getText().toString().trim());
                    signup.put("Password", etPass.getText().toString().trim());
                    signup.put("Con_Pass", etConPass.getText().toString().trim());
                    signup.put("Role_Id", String.valueOf(roleId));
                    signup.put("Active_Status", "1");
                    signup.put("Hotel_Id", String.valueOf(hotelId));
                    signup.put("Branch_Id", String.valueOf(branchId));
                    signup.put("Emp_Address", etAddress.getText().toString().trim());
                    signup.put("Admin_Id", String.valueOf(adminId));

                    initRetrofitCallback();

                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                    mRetrofitService = new RetrofitService(mResultCallBack, ActivityNewAddEmployee.this);
                    mRetrofitService.retrofitData(ADD_NEW_EMPLOYEE, (service.AddEmployee(signup)));
                }
            });


        }
    }

    private void init() {
        arrayListBranch = new ArrayList<BranchForm>();
        arrayListRole = new ArrayList<>();


        //imageView = (CircleImageView) findViewById(R.id.iv_profile);

        // tvName = (TextView) findViewById(R.id.et_name);
        etvName = (EditText) findViewById(R.id.et_name);
        etMob = (EditText) findViewById(R.id.et_mob_no);
        etEmail = (EditText) findViewById(R.id.et_email);
        etUsername = (EditText) findViewById(R.id.et_username);
        etAdhar = (EditText) findViewById(R.id.et_adharno);
        etAddress = (EditText) findViewById(R.id.et_address);
        etPass = (EditText) findViewById(R.id.et_pass);
        etConPass = (EditText) findViewById(R.id.et_cpass);
        register = (Button) findViewById(R.id.btn_register);


        spDesignation = (Spinner) findViewById(R.id.sp_designation);
        spBranch = (Spinner) findViewById(R.id.sp_branch);

        select_image = (FrameLayout) findViewById(R.id.iv_select_image);
        llPassword = findViewById(R.id.llPassword);
        llConPassword = findViewById(R.id.llConPass);
        llBranch = findViewById(R.id.llBranch);

    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {

                switch (requestId) {
                    case ADD_NEW_EMPLOYEE:
                        JsonObject resp = response.body();
                        String valueInfo = resp.toString();
                        try {
                            JSONObject jsonObject = new JSONObject(valueInfo);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityNewAddEmployee.this, "Registered Successfully..!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ActivityNewAddEmployee.this, "Try again..!", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;

                    case BRANCH_INFO:
                        JsonObject object = response.body();
                        String value = object.toString();
                        try {
                            JSONObject object1 = new JSONObject(value);
                            int status = object1.getInt("status");

                            arrayListBranch.clear();
                            if (status == 1) {
                                JSONArray jsonArray = object1.getJSONArray("Hotel_Branch");
                                BranchForm branchForm = new BranchForm();
                                branchForm.setBranchId(0);
                                branchForm.setBranchName("Select Branch");
                                arrayListBranch.add(branchForm);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object2 = jsonArray.getJSONObject(i);
                                    BranchForm branchForm1 = new BranchForm();

                                    branchForm1.setBranchId(object2.getInt("Branch_Id"));
                                    branchForm1.setBranchName(object2.getString("Branch_Name"));
                                    arrayListBranch.add(branchForm1);
                                }


                                ArrayAdapter<BranchForm> stringArrayAdapter = new ArrayAdapter<BranchForm>(ActivityNewAddEmployee.this, android.R.layout.simple_list_item_1, arrayListBranch);
                                stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spBranch.setAdapter(stringArrayAdapter);
                                getSelectedBranch(arrayListBranch);
                                spBranch.setSelection(0);


                                if (branchSelId != 0) {


                                    for (position = 0; position < arrayListBranch.size(); position++)
                                        if (arrayListBranch.get(position).getBranchId() == branchSelId) {

                                            break;
                                        }


                                    spBranch.setSelection(position);
                                    // Toast.makeText(ActivitynewAddEmpDummy.this, "You did not edit the Branch details", Toast.LENGTH_LONG).show();

                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case EMP_EDIT_DETAILS:
                        JsonObject object1 = response.body();
                        String objectInfo = object1.toString();
                        try {
                            JSONObject jsonObject = new JSONObject(objectInfo);

                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityNewAddEmployee.this, "Updated Successfully", Toast.LENGTH_LONG).show();
                                finish();

                            } else {
                                Toast.makeText(ActivityNewAddEmployee.this, "Try Again..", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;


                }

            }

            private void getSelectedBranch(final ArrayList<BranchForm> arrayListBranch) {

                spBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        String branchName = spBranch.getSelectedItem().toString();

                        branchId = (arrayListBranch.get(i).getBranchId());


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                        Toast.makeText(getApplicationContext(), "Please selct the role..", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void notifyError(int requestId, Throwable error) {

            }
        };
    }

    private void retrofitArrayBranchCall() {

        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityNewAddEmployee.this);
        mRetrofitService.retrofitData(BRANCH_INFO, (service.getBranch((hotelId))));


        //  ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        //  Call<List<BranchForm>> call= service.getBranch(Integer.parseInt(hotelId));
       /* call.enqueue(new Callback<List<BranchForm>>() {

            @Override
            public void onResponse(Call<List<BranchForm>> call, Response<List<BranchForm>> response) {
                List<BranchForm> arrayListBranch = response.body();

                ArrayAdapter<BranchForm> stringArrayAdapter = new ArrayAdapter<BranchForm>(ActivityAddNewEmployee.this, android.R.layout.simple_list_item_1, arrayListBranch);
                stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spDesignation.setAdapter(stringArrayAdapter);
                getSelectedBranch(arrayListBranch);
            }

            private void getSelectedBranch(final List<BranchForm> arrayListBranch) {

                spDesignation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        String spBranch = spDesignation.getSelectedItem().toString();

                        branchId = String.valueOf(arrayListBranch.get(i).getBranchId());

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                        Toast.makeText(getApplicationContext(),"Please selct the role..",Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onFailure(Call<List<BranchForm>> call, Throwable t) {

            }
        });*/


    }

    private void retrofitArrayDesignationCall() {

        RoleForm roleForm = new RoleForm();
        roleForm.setRole_Id(0);
        roleForm.setRole("Select Designation");
        arrayListRole.add(roleForm);


        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        Call<List<RoleForm>> call = service.getEmpRole(hotelId);
        call.enqueue(new Callback<List<RoleForm>>() {

            @Override
            public void onResponse(Call<List<RoleForm>> call, Response<List<RoleForm>> response) {


                employeeRoles = response.body();


                for (int i = 0; i < employeeRoles.size(); i++) {
                    RoleForm roleForm = new RoleForm();
                    roleForm.setRole_Id(employeeRoles.get(i).getRole_Id());
                    roleForm.setRole(employeeRoles.get(i).getRole());
                    arrayListRole.add(roleForm);


                }


                ArrayAdapter<RoleForm> stringArrayAdapter = new ArrayAdapter<RoleForm>(ActivityNewAddEmployee.this, android.R.layout.simple_list_item_1, arrayListRole);
                stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spDesignation.setAdapter(stringArrayAdapter);
                getSelectedRole(arrayListRole);
                spDesignation.setSelection(0);


                if (desginagtionSelId != 0) {
                    for (position = 0; position < arrayListRole.size(); position++)
                        if (arrayListRole.get(position).getRole_Id() == desginagtionSelId)
                            break;
                    spDesignation.setSelection(position);
                }

            }

            private void getSelectedRole(final List<RoleForm> arrayListRole) {

                spDesignation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        String role = spDesignation.getSelectedItem().toString();

                        roleId = arrayListRole.get(i).getRole_Id();


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                        Toast.makeText(getApplicationContext(), "Please selct the role..", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onFailure(Call<List<RoleForm>> call, Throwable t) {

            }
        });


    }


    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityNewAddEmployee.this);

        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));

                    startActivityForResult(intent, 1);

                } else if (options[item].equals("Choose from Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, 2);


                } else if (options[item].equals("Cancel")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();

    }


    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {

                File f = new File(Environment.getExternalStorageDirectory().toString());

                for (File temp : f.listFiles()) {

                    if (temp.getName().equals("temp.jpg")) {

                        f = temp;

                        break;

                    }

                }

                try {

                    Bitmap bitmap;

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();


                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),

                            bitmapOptions);


                    imageView.setImageBitmap(bitmap);


                    String path = Environment

                            .getExternalStorageDirectory()

                            + File.separator

                            + "Phoenix" + File.separator + "default";

                    f.delete();

                    OutputStream outFile = null;

                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");

                    try {

                        outFile = new FileOutputStream(file);

                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);

                        outFile.flush();

                        outFile.close();

                    } catch (FileNotFoundException e) {

                        e.printStackTrace();

                    } catch (IOException e) {

                        e.printStackTrace();

                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }

            } else if (requestCode == 2) {


                Uri selectedImage = data.getData();

                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);

                c.moveToFirst();

                int columnIndex = c.getColumnIndex(filePath[0]);

                String picturePath = c.getString(columnIndex);

                c.close();

                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

                // Log.w("path of image from gallery......******************.........", picturePath+"");

                imageView.setImageBitmap(thumbnail);

            }

        }

    }


/*
    public void takephoto(View view){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(),"pic.png");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, TAKE_PICTURE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage, null);
                     imageView = (CircleImageView) findViewById(R.id.iv_profile);


                  *//*  ContentResolver cr = getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);

                        imageView.setImageBitmap(bitmap);*//*


                    try {
                        Picasso.with(getApplicationContext()).load(selectedImage).into(imageView);

                        Toast.makeText(this, selectedImage.toString(),
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
        }
    }*/

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
