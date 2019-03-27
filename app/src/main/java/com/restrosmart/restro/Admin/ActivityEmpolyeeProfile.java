package com.restrosmart.restro.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.restrosmart.restro.Model.GetEmployeeDetails;
import com.restrosmart.restro.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by SHREE on 10/9/2018.
 */

public class ActivityEmpolyeeProfile extends AppCompatActivity {

    ArrayList<GetEmployeeDetails> getEmployeeDetails;

    int emp_id;

    CircleImageView mImage;
    String active;
    TextView mName, mUsername, mRole, mStatus, mHotelName, mMobNo, mEmail, mAdhar, mAddress;

    String Name, Username, Role, Status, HotelName, MobNo, Email, Adhar, Address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        Intent intent = getIntent();

        /*passed complete arraylist*/
        getEmployeeDetails = intent.getParcelableArrayListExtra("Emp_detail");
        emp_id = intent.getIntExtra("empId", 0);

        Toolbar mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolBarTitle=(TextView)mTopToolbar.findViewById(R.id.tx_title);
        toolBarTitle.setText("Employee Profile");

        setSupportActionBar(mTopToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);



        mImage = (CircleImageView)findViewById(R.id.img_user_photo);

        mName = (TextView) findViewById(R.id.txt_user_name);
        mUsername = (TextView) findViewById(R.id.tv_username);
        mRole = (TextView) findViewById(R.id.tv_emp_role);
        mStatus = (TextView) findViewById(R.id.tv_emp_status);
        mHotelName = (TextView) findViewById(R.id.tv_hotel_name);
        mMobNo = (TextView) findViewById(R.id.tv_emp_mobno);
        mEmail = (TextView) findViewById(R.id.tv_emp_email);
        mAdhar = (TextView) findViewById(R.id.tv_emp_aadhar_number);
        mAddress = (TextView) findViewById(R.id.tv_emp_address);

       /* Name = mName.getText().toString();
        Username = mUsername.getText().toString();
        Role = mRole.getText().toString();
        Status = mStatus.getText().toString();
        HotelName = mHotelName.getText().toString();
        MobNo = mMobNo.getText().toString();
        Email = mEmail.getText().toString();
        Adhar = mAdhar.getText().toString();
        Address = mAddress.getText().toString();*/


        try {
            for (int i = 0; i < getEmployeeDetails.size(); i++) {

                int empId = getEmployeeDetails.get(i).getEmpId();

                if (emp_id == empId) {

                    String status = String.valueOf(getEmployeeDetails.get(i).getActiveStatus());
                    String imagePath = getEmployeeDetails.get(i).getEmpImg();

                    Picasso.with(this).load(imagePath).into(mImage);
                    mName.setText(getEmployeeDetails.get(i).getEmpName());
                    mUsername.setText(getEmployeeDetails.get(i).getUserName());
                    mRole.setText(getEmployeeDetails.get(i).getRole());


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
                    mHotelName.setText(getEmployeeDetails.get(i).getHotelName());
                    mMobNo.setText(getEmployeeDetails.get(i).getEmpMob());
                    mEmail.setText(getEmployeeDetails.get(i).getEmpEmail());
                    mAdhar.setText(getEmployeeDetails.get(i).getEmpAdharId());
                    mAddress.setText(getEmployeeDetails.get(i).getEmpAddress());


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
}
