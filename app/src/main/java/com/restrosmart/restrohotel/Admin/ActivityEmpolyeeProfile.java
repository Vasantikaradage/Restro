package com.restrosmart.restrohotel.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.GET_ALL_EMPLOYEE;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

/**
 * Created by SHREE on 10/9/2018.
 */

public class ActivityEmpolyeeProfile extends AppCompatActivity {
    private int emp_id;
    private CircleImageView mImage;
    private String active;
    private TextView mName, mUsername, mRole, mStatus, mHotelName, mMobNo, mEmail, mAdhar, mAddress;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private  Sessionmanager sessionmanager;
    private  int hotelId,branchId;
    private  ArrayList<EmployeeForm> arrayListEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        arrayListEmployee=new ArrayList<>();

        sessionmanager = new Sessionmanager(this);
        HashMap<String, String> name_info = sessionmanager.getHotelDetails();
        hotelId = Integer.parseInt(name_info.get(HOTEL_ID));
        branchId = Integer.parseInt(name_info.get(BRANCH_ID));

        Intent intent = getIntent();
        emp_id = intent.getIntExtra("empId", 0);

        Toolbar mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolBarTitle = (TextView) mTopToolbar.findViewById(R.id.tx_title);
        toolBarTitle.setText("Employee Profile");
        setSupportActionBar(mTopToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        init();
        retrofitCallBack();
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrofitCallBack();
    }

    private void init() {
        mImage = (CircleImageView) findViewById(R.id.img_user_photo);
        mName = (TextView) findViewById(R.id.txt_user_name);
        mUsername = (TextView) findViewById(R.id.tv_username);
        mRole = (TextView) findViewById(R.id.tv_emp_role);
        mStatus = (TextView) findViewById(R.id.tv_emp_status);
        mHotelName = (TextView) findViewById(R.id.tv_hotel_name);
        mMobNo = (TextView) findViewById(R.id.tv_emp_mobno);
        mEmail = (TextView) findViewById(R.id.tv_emp_email);
        mAdhar = (TextView) findViewById(R.id.tv_emp_aadhar_number);
        mAddress = (TextView) findViewById(R.id.tv_emp_address);
    }

    private void retrofitCallBack() {
        retrofitCallBackEmployee();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityEmpolyeeProfile.this);
        mRetrofitService.retrofitData(GET_ALL_EMPLOYEE, (service.getallEmployees(hotelId,
              branchId)));
    }

    private void retrofitCallBackEmployee() {
        mResultCallBack=new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {

                JsonObject jsonObject=response.body();
                String empInfo=jsonObject.toString();

                try {
                    JSONObject object=new JSONObject(empInfo);
                    int status=object.getInt("status");
                    if(status==1){

                        JSONArray jsonArray=object.getJSONArray("allemployee");
                        for(int i=0; i<jsonArray.length(); i++) {

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
                            employeeForm.setBranch_Id(jsonObjectEmp.getInt("Branch_Id"));
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

    private void getAdapter(ArrayList<EmployeeForm> employeeDetails) {

        try {
            for (int i = 0; i < employeeDetails.size(); i++) {

                int empId = employeeDetails.get(i).getEmpId();

                if (emp_id == empId) {

                    String status = String.valueOf(employeeDetails.get(i).getActiveStatus());
                    String imagePath = employeeDetails.get(i).getEmpImg();

                    Picasso.with(this).load(imagePath).into(mImage);
                    mName.setText(employeeDetails.get(i).getEmpName());
                    mUsername.setText(employeeDetails.get(i).getUserName());
                    mRole.setText(employeeDetails.get(i).getRole());

                    switch (status) {
                        case "1":
                            active = "Active";
                            break;

                        case "2":
                            active = "Inactive";
                            break;

                        default:
                            active = "Active";
                    }

                    mStatus.setText(active);
                    mHotelName.setText(employeeDetails.get(i).getHotelName());
                    mMobNo.setText(employeeDetails.get(i).getEmpMob());
                    mEmail.setText(employeeDetails.get(i).getEmpEmail());
                    mAdhar.setText(employeeDetails.get(i).getEmpAdharId());
                    mAddress.setText(employeeDetails.get(i).getEmpAddress());

                } else {

                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show();
            e.getMessage();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_employee, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit_employee) {
            Intent intent = new Intent(ActivityEmpolyeeProfile.this, ActivityNewAddEmployee.class);
            intent.putParcelableArrayListExtra("Emp_detail", (ArrayList<? extends Parcelable>) arrayListEmployee);
            intent.putExtra("empId", emp_id);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
