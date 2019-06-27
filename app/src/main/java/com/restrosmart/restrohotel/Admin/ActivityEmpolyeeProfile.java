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

import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Model.EmployeeForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SHREE on 10/9/2018.
 */

public class ActivityEmpolyeeProfile extends AppCompatActivity {

    private List<EmployeeForm> employeeDetails;

    private int emp_id;
    private  ApiService apiService;

    private CircleImageView mImage;
    private String active;
    private TextView mName, mUsername, mRole, mStatus, mHotelName, mMobNo, mEmail, mAdhar, mAddress;

    private String Name, Username, Role, Status, HotelName, MobNo, Email, Adhar, Address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        Intent intent = getIntent();

        /*passed complete arraylist*/
        // employeeDetails = intent.getParcelableArrayListExtra("Emp_detail");
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
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        Call<List<EmployeeForm>> call = service.getallEmployees("1", "1");

        call.enqueue(new Callback<List<EmployeeForm>>() {
            @Override
            public void onResponse(Call<List<EmployeeForm>> call, Response<List<EmployeeForm>> response) {
                employeeDetails = response.body();
                getData(employeeDetails);
            }

            @Override
            public void onFailure(Call<List<EmployeeForm>> call, Throwable t) {
                Toast.makeText(ActivityEmpolyeeProfile.this, "" + t, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getData(List<EmployeeForm> employeeDetails) {

        try {
            for (int i = 0; i < employeeDetails.size(); i++) {

                int empId = employeeDetails.get(i).getEmpId();

                if (emp_id == empId) {

                    String status = String.valueOf(employeeDetails.get(i).getActiveStatus());
                    String imagePath = employeeDetails.get(i).getEmpImg();

                    Picasso.with(this).load(apiService.BASE_URL+imagePath).into(mImage);
                    mName.setText(employeeDetails.get(i).getEmpName());
                    mUsername.setText(employeeDetails.get(i).getUserName());
                    mRole.setText(employeeDetails.get(i).getRole());


                    //if (status.contentEquals("1"))


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
            intent.putParcelableArrayListExtra("Emp_detail", (ArrayList<? extends Parcelable>) employeeDetails);
            intent.putExtra("empId", emp_id);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
