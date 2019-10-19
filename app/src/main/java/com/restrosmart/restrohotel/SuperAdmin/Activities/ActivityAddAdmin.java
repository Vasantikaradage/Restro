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
import com.restrosmart.restrohotel.Utils.FilePath;
import com.restrosmart.restrohotel.Utils.ImageFilePath;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

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
import static com.restrosmart.restrohotel.ConstantVariables.BRANCH_INFO;
import static com.restrosmart.restrohotel.ConstantVariables.EMP_EDIT_DETAILS;
import static com.restrosmart.restrohotel.ConstantVariables.PICK_GALLERY_IMAGE;
import static com.restrosmart.restrohotel.ConstantVariables.REQUEST_PERMISSION;


public class ActivityAddAdmin  extends AppCompatActivity {

    private EditText etvName, etMob, etEmail, etUsername, etAdhar, etPass, etConPass, etAddress;

    private CircleImageView imageView;
    private Spinner spDesignation, spBranch;
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
    private LinearLayout llPassword, llConPassword, llBranch;
    private FrameLayout flImage;
    private String selectedFilePath, extension, selectedData, selectedImage,subString;
    private File selectedFile;
    private Bitmap bitmapImage;
    private String imageOldName;

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


        Sessionmanager sharedPreferanceManage = new Sessionmanager(ActivityAddAdmin.this);
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();
        // adminId = Integer.parseInt(name_info.get(ROLE_ID));
        // hotelId = Integer.parseInt(name_info.get(HOTEL_ID));


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

                    if (selectedData==null) {
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
                    signup.put("Role_Id", String.valueOf(roleId));
                    signup.put("Active_Status", "1");
                    signup.put("Hotel_Id", String.valueOf(hotelId));
                    signup.put("Emp_Address", etAddress.getText().toString().trim());
                    signup.put("Admin_Id", String.valueOf(adminId));

                    initRetrofitCallback();
                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                    mRetrofitService = new RetrofitService(mResultCallBack, ActivityAddAdmin.this);
                    mRetrofitService.retrofitData(ADD_NEW_EMPLOYEE, (service.AddEmployee(signup)));
                }
            }
        });

    }

    private boolean isValid() {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        String mobilePattern = "(0/91)?[7-9][0-9]{9}";
        if (etvName.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityAddAdmin.this, "Please enter Full Name..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etUsername.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityAddAdmin.this, "Please enter User Name..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etMob.getText().toString().length()==0) {
            Toast.makeText(ActivityAddAdmin.this, "Please enter Mobile No..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etMob.getText().toString().length() < 10) {
            Toast.makeText(this, "Please enter valid mobile no", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etEmail.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityAddAdmin.this, "Please enter Email Id..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!(etEmail.getText().toString().trim()).matches(emailPattern)) {
            Toast.makeText(this, "Please enter valid email id", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (etAdhar.getText().toString().length()==0) {
            Toast.makeText(ActivityAddAdmin.this, "Please enter adhar no", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etAdhar.getText().toString().length() < 12) {
            Toast.makeText(this, "Please enter valid adhar no", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etAddress.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityAddAdmin.this, "Please enter Address..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etPass.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityAddAdmin.this, "Please enter password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etConPass.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityAddAdmin.this, "Please enter conform password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!(etPass.getText().toString()).equals(etConPass.getText().toString())) {
            Toast.makeText(ActivityAddAdmin.this, "Password Does not match..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (roleId == 0) {
            Toast.makeText(ActivityAddAdmin.this, "Please Select Designation", Toast.LENGTH_SHORT).show();
            return false;
        } /*else if (branchInfo == 0) {
            Toast.makeText(ActivityNewAddEmployee.this, "Please Select Branch", Toast.LENGTH_SHORT).show();
            return false;
        }*/


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
                                Toast.makeText(ActivityAddAdmin.this, "Registered Successfully..!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ActivityAddAdmin.this, "Try again..!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;

               /*     case BRANCH_INFO:
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
                        branchInfo = (arrayListBranch.get(i).getBranchId());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        Toast.makeText(getApplicationContext(), "Please selct the role..", Toast.LENGTH_SHORT).show();
                    }
                });*/
                    // }
                }
            }

                    @Override
                    public void notifyError ( int requestId, Throwable error){
                        Log.d("", "VolleyError" + error);
                        Log.d("", "requestId" + requestId);
                    }
                } ;
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



