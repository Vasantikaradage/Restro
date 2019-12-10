package com.restrosmart.restrohotel.SuperAdmin.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.restrosmart.restrohotel.Model.EmployeeForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.SuperAdmin.Models.EmployeeSAForm;
import com.restrosmart.restrohotel.Utils.Sessionmanager;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class ActivitySaAdminProfile  extends AppCompatActivity {

    private int emp_id;
    private CircleImageView mImage;
    private String active,imageOldName;
    private TextView mName, mUsername, mRole, mStatus, mHotelName, mMobNo, mEmail, mAdhar, mAddress;
    private Sessionmanager sessionmanager;

    private ArrayList<EmployeeForm> arrayListEmployee;
    private Toolbar mTopToolbar;
    private  TextView toolBarTitle;
    private EmployeeSAForm employeeSAForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);
        init();
        setUpToolBar();

        sessionmanager = new Sessionmanager(this);
        HashMap<String, String> name_info = sessionmanager.getHotelDetails();


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            employeeSAForm = bundle.getParcelable("employeeForm");

            String status = String.valueOf(employeeSAForm.getActiveStatus());
            imageOldName= employeeSAForm.getEmpImg();
            Picasso.with(this).load(imageOldName).into(mImage);
            mName.setText(employeeSAForm.getEmpName());
            mUsername.setText(employeeSAForm.getUserName());
            mRole.setText(employeeSAForm.getRole());

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
            mHotelName.setText(employeeSAForm.getHotelName());
            mMobNo.setText(employeeSAForm.getEmpMob());
            mEmail.setText(employeeSAForm.getEmpEmail());
            mAdhar.setText(employeeSAForm.getEmpAdharId());
            mAddress.setText(employeeSAForm.getEmpAddress());

        }

          //  emp_id = intent.getIntExtra("empId", 0);
        //  retrofitCallBack();
    }

    private void setUpToolBar() {
        toolBarTitle.setText("Employee Profile");
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //retrofitCallBack();
    }

    private void init() {
        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolBarTitle = (TextView) mTopToolbar.findViewById(R.id.tx_title);
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
        arrayListEmployee=new ArrayList<>();
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
            Intent intent = new Intent(ActivitySaAdminProfile.this, ActivityAddAdmin.class);
            intent.putExtra("Emp_detail",  employeeSAForm);
          //  intent.putExtra("empId", emp_id);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
