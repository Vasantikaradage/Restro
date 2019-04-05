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
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.BranchForm;
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
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.ROLE_ID;

public class AddNewEmployee extends AppCompatActivity {

    AppCompatEditText mob, email, uname, adhar, pass, cpass, address;
    TextInputEditText name;

    CircleImageView imageView;

    Spinner designation,branch;
    Button register;
    ImageButton select_image;
    String roleId,hotelId,branchId,adminId;

    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;

    RetrofitService mRetrofitService;
    IResult mResultCallBack;

    ArrayList<BranchForm> arrayListBranch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_employee);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbar mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolBarTitle=(TextView)mTopToolbar.findViewById(R.id.tx_title);
        toolBarTitle.setText("Add New Employee");

        setSupportActionBar(mTopToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        arrayListBranch=new ArrayList<BranchForm>();





        //imageView = (CircleImageView) findViewById(R.id.iv_profile);

        name = (TextInputEditText) findViewById(R.id.et_name);
        mob = (AppCompatEditText) findViewById(R.id.et_mob_no);
        email = (AppCompatEditText) findViewById(R.id.et_email);
        uname = (AppCompatEditText) findViewById(R.id.et_username);
        adhar = (AppCompatEditText) findViewById(R.id.et_adharno);
        address = (AppCompatEditText) findViewById(R.id.et_address);
        pass = (AppCompatEditText) findViewById(R.id.et_pass);
        cpass = (AppCompatEditText) findViewById(R.id.et_cpass);
        register = (Button) findViewById(R.id.btn_register);

        designation = (Spinner) findViewById(R.id.sp_designation);
        branch=(Spinner)findViewById(R.id.sp_branch);

        select_image= (ImageButton)findViewById(R.id.iv_select_image);

        Sessionmanager sharedPreferanceManage = new Sessionmanager(AddNewEmployee.this);
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();
        adminId = name_info.get(ROLE_ID);
        hotelId=name_info.get(HOTEL_ID);

        retrofitArrayDesignationCall();
        retrofitArrayBranchCall();


       /* String[] desig = {"Select Designation", "Admin", "Employee"};
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(AddNewEmployee.this, android.R.layout.simple_list_item_1, desig);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        designation.setAdapter(stringArrayAdapter);*/


       select_image.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               selectImage();

           }
       });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Picasso.with(getApplicationContext()).load("https://images.pexels.com/photos/67636/rose-blue-flower-rose-blooms-67636.jpeg?auto=compress&cs=tinysrgb&h=650&w=940").into(imageView);

                Map<String, String> signup = new HashMap<>();
                signup.put("Emp_Name", name.getText().toString());
                signup.put("Emp_Mob",  mob.getText().toString().trim());
                signup.put("Emp_Email", email.getText().toString().trim());
                signup.put("User_Name", uname.getText().toString().trim());
                signup.put("Emp_Adhar_Id", adhar.getText().toString().trim());
                signup.put("Password", pass.getText().toString().trim());
                signup.put("Con_Pass", cpass.getText().toString().trim());
                signup.put("Role_Id", roleId);
                signup.put("Active_Status", "1");
                signup.put("Hotel_Id", hotelId);
                signup.put("Branch_Id", branchId);
                signup.put("Emp_Address", address.getText().toString().trim());
                signup.put("Admin_Id",adminId);

                initRetrofitCallback();

                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack, AddNewEmployee.this);
                mRetrofitService.retrofitData(ADD_NEW_EMPLOYEE, (service.AddEmployee(signup)));
            }
        });


    }

    private void initRetrofitCallback() {
        mResultCallBack=new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {

                switch (requestId)
                {
                    case ADD_NEW_EMPLOYEE:
                        JsonObject resp = response.body();
                        Toast.makeText(AddNewEmployee.this, "Registered Successfully..!", Toast.LENGTH_SHORT).show();
                        break;

                    case BRANCH_INFO:
                       JsonObject object=response.body();
                       String value=object.toString();
                        try {
                            JSONObject object1=new JSONObject(value);
                            int status=object1.getInt("status");


                            if(status==1)
                            {
                                JSONArray jsonArray=object1.getJSONArray("Hotel_Branch");
                                for(int i=0;i<jsonArray.length();i++) {
                                    JSONObject object2 = jsonArray.getJSONObject(i);
                                    BranchForm branchForm=new BranchForm();
                                    branchForm.setBranchId(object2.getInt("Branch_Id"));
                                    branchForm.setBranchName(object2.getString("Branch_Name"));
                                    arrayListBranch.add(branchForm);
                                }


                                ArrayAdapter<BranchForm> stringArrayAdapter = new ArrayAdapter<BranchForm>(AddNewEmployee.this, android.R.layout.simple_list_item_1, arrayListBranch);
                                stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                branch.setAdapter(stringArrayAdapter);
                                getSelectedBranch(arrayListBranch);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;


                }

            }

            private void getSelectedBranch(final ArrayList<BranchForm> arrayListBranch) {

                branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        String branchName = branch.getSelectedItem().toString();

                        branchId = String.valueOf(arrayListBranch.get(i).getBranchId());

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                        Toast.makeText(getApplicationContext(),"Please selct the role..",Toast.LENGTH_SHORT).show();

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
        mRetrofitService = new RetrofitService(mResultCallBack, AddNewEmployee.this);
        mRetrofitService.retrofitData(BRANCH_INFO, (service.getBranch(Integer.parseInt(hotelId))));


      //  ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
      //  Call<List<BranchForm>> call= service.getBranch(Integer.parseInt(hotelId));
       /* call.enqueue(new Callback<List<BranchForm>>() {

            @Override
            public void onResponse(Call<List<BranchForm>> call, Response<List<BranchForm>> response) {
                List<BranchForm> arrayListBranch = response.body();

                ArrayAdapter<BranchForm> stringArrayAdapter = new ArrayAdapter<BranchForm>(AddNewEmployee.this, android.R.layout.simple_list_item_1, arrayListBranch);
                stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                designation.setAdapter(stringArrayAdapter);
                getSelectedBranch(arrayListBranch);
            }

            private void getSelectedBranch(final List<BranchForm> arrayListBranch) {

                designation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        String branch = designation.getSelectedItem().toString();

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

        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        Call<List<RoleForm>> call= service.getEmpRole(Integer.parseInt(hotelId));
        call.enqueue(new Callback<List<RoleForm>>() {

            @Override
            public void onResponse(Call<List<RoleForm>> call, Response<List<RoleForm>> response) {
                List<RoleForm> employeeRoles = response.body();

                ArrayAdapter<RoleForm> stringArrayAdapter = new ArrayAdapter<RoleForm>(AddNewEmployee.this, android.R.layout.simple_list_item_1, employeeRoles);
                stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                designation.setAdapter(stringArrayAdapter);
                getSelectedRole(employeeRoles);
            }

            private void getSelectedRole(final List<RoleForm> employeeRoles) {

                designation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        String role = designation.getSelectedItem().toString();

                        roleId = String.valueOf(employeeRoles.get(i).getRole_Id());

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                        Toast.makeText(getApplicationContext(),"Please selct the role..",Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onFailure(Call<List<RoleForm>> call, Throwable t) {

            }
        });







    }


    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(AddNewEmployee.this);

        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo"))

                {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));

                    startActivityForResult(intent, 1);

                }

                else if (options[item].equals("Choose from Gallery"))

                {

                    Intent intent = new   Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, 2);



                }

                else if (options[item].equals("Cancel")) {

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

                String[] filePath = { MediaStore.Images.Media.DATA };

                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);

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
