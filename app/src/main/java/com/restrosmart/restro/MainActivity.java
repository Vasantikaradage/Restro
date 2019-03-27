package com.restrosmart.restro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.restrosmart.restro.Admin.AdminLogin;
import com.restrosmart.restro.Bar.ActivityBarHome;
import com.restrosmart.restro.Kitchen.ActivityKitchenLogin;
import com.restrosmart.restro.User.ActivityUserLogin;
import com.restrosmart.restro.Utils.Sessionmanager;

public class MainActivity extends AppCompatActivity {

    private Button btn_user, btn_admin, btn_kitchen, btnBarCounter;
    private Sessionmanager sessionmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*check for user login*/
        /*sessionmanager = new Sessionmanager(this);
        sessionmanager.CheckLogin();*/


        btn_user = findViewById(R.id.btn_user);
        btn_admin = findViewById(R.id.btn_admin);
        btn_kitchen = findViewById(R.id.btn_kitchen);
        btnBarCounter = findViewById(R.id.btnBarCounter);

        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ActivityUserLogin.class);
                startActivity(i);
            }
        });


        btn_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AdminLogin.class);
                startActivity(i);
            }
        });

        btn_kitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ActivityKitchenLogin.class);
                startActivity(i);
            }
        });

        btnBarCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ActivityBarHome.class);
                startActivity(i);
            }
        });
    }
}
