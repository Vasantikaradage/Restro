package com.restrosmart.restrohotel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import com.restrosmart.restrohotel.Admin.ActivityLogin;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_captain, btn_admin, btnSuperAdmin;
    private Sessionmanager sessionmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*check for user login*/
        /*sessionmanager = new Sessionmanager(this);
        sessionmanager.CheckLogin();*/

      //  btn_captain = findViewById(R.id.btn_captain);
        btn_admin = findViewById(R.id.btn_admin);
        //btnSuperAdmin = findViewById(R.id.btnSuperAdmin);

    //    btnSuperAdmin.setOnClickListener(this);
        btn_admin.setOnClickListener(this);
     //   btn_captain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.btnSuperAdmin:
                Intent intentSuperAdmin = new Intent(MainActivity.this, ActivitySADashBoradDrawer.class);
                startActivity(intentSuperAdmin);
                break;*/

            case R.id.btn_admin:
                Intent intentAdmin = new Intent(MainActivity.this, ActivityLogin.class);
                startActivity(intentAdmin);
                break;

           /* case R.id.btn_captain:
                Intent intentCaptain = new Intent(MainActivity.this, ActivityCaptainDash.class);
                startActivity(intentCaptain);
                break;*/
        }
    }
}
