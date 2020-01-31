package com.restrosmart.restrohotel.SuperAdmin.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
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
import com.restrosmart.restrohotel.Admin.ActivityNewAddEmployee;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.BranchForm;
import com.restrosmart.restrohotel.Model.EmployeeForm;
import com.restrosmart.restrohotel.Model.RoleForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.SuperAdmin.Models.EmployeeSAForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.HotelForm;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.restrosmart.restrohotel.ConstantVariables.ADD_NEW_EMPLOYEE;
import static com.restrosmart.restrohotel.ConstantVariables.ADMIN_EMP_EDIT;
import static com.restrosmart.restrohotel.ConstantVariables.EMP_EDIT_DETAILS;
import static com.restrosmart.restrohotel.ConstantVariables.GET_SA_ALL_HOTEL;
import static com.restrosmart.restrohotel.ConstantVariables.PICK_GALLERY_IMAGE;
import static com.restrosmart.restrohotel.ConstantVariables.REQUEST_PERMISSION;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.EMP_ID;


public class ActivityAddAdmin extends AppCompatActivity {

    private EditText etvName, etMob, etEmail, etUsername, etAdhar, etPass, etConPass, etAddress;
    private TextView tvEmpRole;

    private CircleImageView imageView;
    private Spinner spHotel;
    private String password;
    private Button btnSave, btnUpdate;
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
    private LinearLayout llPassword, llConPassword, llHotel;
    private FrameLayout flImage;
    private String selectedFilePath, extension, selectedData, selectedImage, subString;
    private File selectedFile;
    private Bitmap bitmapImage;
    private String imageOldName;
    private EmployeeSAForm employeeSAForm;

    private LinearLayout llDesignation;
    private View viewline;
    private HashMap<String, String> superAdminInfo;
    private ArrayList<HotelForm> hotelFormArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_admin);

        init();
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbar mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolBarTitle = (TextView) mTopToolbar.findViewById(R.id.tx_title);
        toolBarTitle.setText("Add New Admin");

        setSupportActionBar(mTopToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);


        Sessionmanager sharedPreferanceManage = new Sessionmanager(ActivityAddAdmin.this);
        superAdminInfo = sharedPreferanceManage.getSuperAdminDetails();

        getHotelDetails();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {


            employeeSAForm = bundle.getParcelable("Emp_detail");

            //  llHotel.setVisibility(View.GONE);
            llConPassword.setVisibility(View.GONE);
            llPassword.setVisibility(View.GONE);

            btnSave.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);


            //  if (emp_id == employeeSAForm.getEmpId()) {
            toolBarTitle.setText("Edit Employee");
            emp_id = employeeSAForm.getEmpId();
            etvName.setText(employeeSAForm.getEmpName());
            etUsername.setText(employeeSAForm.getUserName());
            etAddress.setText(employeeSAForm.getEmpAddress());
            etAdhar.setText(employeeSAForm.getEmpAdharId());
            etEmail.setText(employeeSAForm.getEmpEmail());
            etMob.setText(employeeSAForm.getEmpMob());
            etPass.setText(employeeSAForm.getPassword());
            etConPass.setText(employeeSAForm.getPassword());
            etUsername.setFocusable(false);

            desginagtionSelId = employeeSAForm.getRole_Id();
            //    branchSelId = employeeDetails.get(i).getBranch_Id();
            password = employeeSAForm.getPassword();
            hotelId = employeeSAForm.getHotelId();
            imageOldName = employeeSAForm.getEmpImg();
           // getHotelDetails();
          //  spHotel.setSelection(2);


            //branchInfo = employeeDetails.get(i).getBranch_Id();
            Picasso.with(ActivityAddAdmin.this)
                    .load(imageOldName)
                    .resize(500, 500)
                    .into(imageView);

            flImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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


            // retrofitArrayDesignationCall();
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String image = imageOldName.substring(imageOldName.lastIndexOf("/") + 1);
                    String imageExtention=image.substring((image.lastIndexOf(".")+1));
                    if ((selectedData == null)) {
                        if (image.equals("def_user.png")) {
                            selectedImage = "";
                            extension = "";
                            image = "";

                        } else {
                            selectedImage = "";
                            extension = "";
                        }
                        Picasso.with(ActivityAddAdmin.this)
                                .load(imageOldName)
                                .resize(500, 500)
                                .into(imageView);

                    } else {
                        selectedImage = selectedData;
                    }

                    if (isValidEmpUpdate()) {

                        String name = etvName.getText().toString();
                        String mob = etMob.getText().toString();
                        String username = etUsername.getText().toString();
                        String address = etAddress.getText().toString();
                        String email = etEmail.getText().toString();
                        String adhar = etAdhar.getText().toString();

                      //  spHotel.setSelection(position);
                        initRetrofitCallback();
                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        mRetrofitService = new RetrofitService(mResultCallBack, ActivityAddAdmin.this);
                        mRetrofitService.retrofitData(ADMIN_EMP_EDIT, (service.
                                adminEditEmployeeDetail(emp_id,
                                        name,
                                        selectedImage,
                                        image,
                                        extension,
                                        mob,
                                        email,
                                        address,
                                        username,
                                        adhar,
                                        1,
                                        hotelId)));
                        // showProgrssDailog();
                    }
                }
            });
            // }


        } else {
            // llHotel.setVisibility(View.VISIBLE);
            llConPassword.setVisibility(View.VISIBLE);
            llPassword.setVisibility(View.VISIBLE);

            btnSave.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);

        }

        spHotel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hotelId = hotelFormArrayList.get(position).getHotelId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        flImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isValid()) {
                    Map<String, String> signup = new HashMap<>();
                    signup.put("Emp_Name", etvName.getText().toString());
                    signup.put("Emp_Mob", etMob.getText().toString().trim());

                    if (selectedData == null) {
                        signup.put("Emp_Img", "");
                        signup.put("Img_Type", "");
                    } else {
                        signup.put("Emp_Img", selectedData);
                        signup.put("Img_Type", extension);
                    }


                    signup.put("Emp_Email", etEmail.getText().toString().trim());
                    signup.put("User_Name", etUsername.getText().toString().trim());
                    signup.put("Emp_Adhar_Id", etAdhar.getText().toString().trim());
                    signup.put("Password", etPass.getText().toString().trim());
                    signup.put("Con_Pass", etConPass.getText().toString().trim());
                    signup.put("Role_Id", "1");
                    signup.put("Active_Status", "1");
                    signup.put("Hotel_Id", String.valueOf(hotelId));
                    signup.put("Emp_Address", etAddress.getText().toString().trim());
                    signup.put("Admin_Id", (superAdminInfo.get(EMP_ID)));

                    initRetrofitCallback();
                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                    mRetrofitService = new RetrofitService(mResultCallBack, ActivityAddAdmin.this);
                    mRetrofitService.retrofitData(ADD_NEW_EMPLOYEE, (service.AddEmployee(signup)));
                }


            }
        });
    }

    private boolean isValidEmpUpdate() {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (etvName.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityAddAdmin.this, "Please enter Full Name..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etUsername.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityAddAdmin.this, "Please enter User Name..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etMob.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityAddAdmin.this, "Please enter Mobile No..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etMob.getText().toString().length() < 10) {
            Toast.makeText(this, "Please enter valid mobile no", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etEmail.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityAddAdmin.this, "Please enter Email Id..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!etEmail.getText().toString().matches(emailPattern)) {
            Toast.makeText(this, "Please enter valid email id", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etAdhar.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityAddAdmin.this, "Please enter adhar no", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etAdhar.getText().toString().length() < 12) {
            Toast.makeText(this, "Please enter valid adhar no", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etAddress.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityAddAdmin.this, "Please enter Address..", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void getHotelDetails() {
        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityAddAdmin.this);
        mRetrofitService.retrofitData(GET_SA_ALL_HOTEL, (service.getSAHotelDetails(Integer.parseInt(superAdminInfo.get(EMP_ID)))));
    }

    private boolean isValid() {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        String mobilePattern = "(0/91)?[7-9][0-9]{9}";
        if (etvName.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityAddAdmin.this, "Please enter full name..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etUsername.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityAddAdmin.this, "Please enter user name..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etMob.getText().toString().length() == 0) {
            Toast.makeText(ActivityAddAdmin.this, "Please enter mobile no..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etMob.getText().toString().length() < 10) {
            Toast.makeText(this, "Please enter valid mobile no", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etEmail.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityAddAdmin.this, "Please enter email id..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!(etEmail.getText().toString().trim()).matches(emailPattern)) {
            Toast.makeText(this, "Please enter valid email id", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etAdhar.getText().toString().length() == 0) {
            Toast.makeText(ActivityAddAdmin.this, "Please enter adhar no", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etAdhar.getText().toString().length() < 12) {
            Toast.makeText(this, "Please enter valid adhar no", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etAddress.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityAddAdmin.this, "Please enter address..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etPass.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityAddAdmin.this, "Please enter password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etConPass.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityAddAdmin.this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!(etPass.getText().toString()).equals(etConPass.getText().toString())) {
            Toast.makeText(ActivityAddAdmin.this, "Password does not match..", Toast.LENGTH_SHORT).show();
            return false;
        }  else if (hotelId == 0) {
            Toast.makeText(ActivityAddAdmin.this, "Please Select hotel", Toast.LENGTH_SHORT).show();
            return false;
        }


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

    private void init() {

        arrayListBranch = new ArrayList<BranchForm>();
        arrayListRole = new ArrayList<>();
        imageView = (CircleImageView) findViewById(R.id.iv_profile);
        flImage = findViewById(R.id.iv_select_image);
        etvName = (EditText) findViewById(R.id.et_name);
        etMob = (EditText) findViewById(R.id.et_mob_no);
        etEmail = (EditText) findViewById(R.id.et_email);
        etUsername = (EditText) findViewById(R.id.et_username);
        etAdhar = (EditText) findViewById(R.id.et_adharno);
        etAddress = (EditText) findViewById(R.id.et_address);
        etPass = (EditText) findViewById(R.id.et_pass);
        etConPass = (EditText) findViewById(R.id.et_cpass);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnUpdate = findViewById(R.id.btn_update);

        tvEmpRole = findViewById(R.id.tv_emp_role);
        spHotel = (Spinner) findViewById(R.id.sp_hotel);

        select_image = (FrameLayout) findViewById(R.id.iv_select_image);
        llPassword = findViewById(R.id.llPassword);
        llConPassword = findViewById(R.id.llConPass);
        llHotel = findViewById(R.id.llHotel);
        llDesignation = findViewById(R.id.llHotel);
        viewline = findViewById(R.id.view_line);
        hotelFormArrayList = new ArrayList<>();
    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject resp = response.body();
                String valueInfo = resp.toString();

                switch (requestId) {
                    case ADD_NEW_EMPLOYEE:

                        try {
                            JSONObject jsonObject = new JSONObject(valueInfo);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityAddAdmin.this, "Registered Successfully..!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ActivityAddAdmin.this, "Try again..!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;

                    case GET_SA_ALL_HOTEL:
                        try {

                            JSONObject object = new JSONObject(valueInfo);
                            int status = object.getInt("status");
                            if (status == 1) {
                                hotelFormArrayList.clear();
                                HotelForm hotelForm1 = new HotelForm();
                                hotelForm1.setHotelId(0);
                                hotelForm1.setHotelName("Select Hotel");
                                hotelFormArrayList.add(hotelForm1);
                                JSONArray jsonArray = object.getJSONArray("Hotellist");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject hotelObject = jsonArray.getJSONObject(i);
                                    HotelForm hotelForm = new HotelForm();
                                    hotelForm.setHotelId(hotelObject.getInt("Hotel_Id"));
                                    hotelForm.setHotelName(hotelObject.getString("Hotel_Name"));
                                    hotelFormArrayList.add(hotelForm);
                                }

                                // getSelectedHotel(hotelFormArrayList);
                                // spHotel.setSelection(0);

                                if (hotelId != 0) {
                                    for (position = 0; position < hotelFormArrayList.size(); position++)
                                        if (hotelFormArrayList.get(position).getHotelId() == hotelId) {
                                            break;
                                        }
                                }


                            }
                            ArrayAdapter<HotelForm> stringArrayAdapter = new ArrayAdapter<HotelForm>(ActivityAddAdmin.this, android.R.layout.simple_spinner_item, hotelFormArrayList);
                            stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spHotel.setAdapter(stringArrayAdapter);
                            spHotel.setSelection(position);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;


                    case ADMIN_EMP_EDIT:
                        JsonObject object1 = response.body();
                        String objectInfo = object1.toString();
                        try {
                            JSONObject jsonObject = new JSONObject(objectInfo);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityAddAdmin.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                               finish();
                              //  ActivityCompat.finishAffinity(ActivityAddAdmin.this);
                            } else {
                                Toast.makeText(ActivityAddAdmin.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("", "VolleyError" + error);
                Log.d("", "requestId" + requestId);
            }
        };
    }

    // private void getSelectedHotel(final ArrayList<HotelForm> hotelFormArrayList1) {


    // }

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
                imageView.setImageBitmap(bitmap);

                /*if (selectedFilePath != null && !selectedFilePath.equals("")) {
 //tvStudyFileName.setText(selectedFilePath);
                   // alertDialog.dismiss();
                } else {
                    Toast.makeText(ActivityNewAddEmployee.this, "cannot upload image", Toast.LENGTH_SHORT).show();
                }*/
            } else {
                Toast.makeText(ActivityAddAdmin.this, "upload image up to 1 mb", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}



