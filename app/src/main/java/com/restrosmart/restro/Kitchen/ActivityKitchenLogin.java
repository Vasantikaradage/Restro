package com.restrosmart.restro.Kitchen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.restrosmart.restro.Admin.ActivityAdminDrawer;
import com.restrosmart.restro.Admin.Admin_Forget_pass;
import com.restrosmart.restro.Model.UserForm;
import com.restrosmart.restro.R;

/**
 * Created by SHREE on 09/10/2018.
 */

public class ActivityKitchenLogin extends AppCompatActivity {

    String mobno1, pass1;
    EditText mobno, pass;
    TextView mForget;
    CheckBox savelogincheckbox;
    Button btn_login;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Boolean savelogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_kitchen_login);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mobno = findViewById(R.id.et_mob_no);
        pass = findViewById(R.id.et_pass);
        savelogincheckbox = (CheckBox) findViewById(R.id.checkbox_admin);
        mForget = (TextView) findViewById(R.id.tv_forget_pass);
        btn_login = findViewById(R.id.btn_login);

        sharedPreferences = getSharedPreferences("loginref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();


        savelogin = sharedPreferences.getBoolean("savelogin", true);
        if (savelogin == true) {
            mobno.setText(sharedPreferences.getString("Emp_Mob", null));
            pass.setText(sharedPreferences.getString("Password", null));
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityKitchenLogin.this, ActivityKitchenHome.class);
                startActivity(intent);
            }
        });

        mForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityKitchenLogin.this, Admin_Forget_pass.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void Login(View view) {

        mobno1 = mobno.getText().toString();
        pass1 = pass.getText().toString();
        String Active_Status = "1";
        String Role_Id = "2";



          /* //retrofit
           ApiService service= RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
           Call<UserForm> objectCall=service.getLogin(mobno1,pass1,Active_Status,Role_Id);
           objectCall.enqueue(new Callback<UserForm>() {
               @Override
               public void onResponse(Call<UserForm> call, retrofit2.Response<UserForm> response) {
                   UserForm user=response.body();
                   getLogin(user);

               }
               @Override
               public void onFailure(Call<UserForm> call, Throwable t) {

                   Toast.makeText(ActivityKitchenLogin.this, ""+t, Toast.LENGTH_SHORT).show();
               }
           });*/

    }

    private void getLogin(UserForm user) {

        String mob_number = user.getEmp_Mob();

        String role = user.getRole();

        if (mobno1.equals(mob_number)) {
            Intent intent = new Intent(ActivityKitchenLogin.this, ActivityAdminDrawer.class);
            intent.putExtra("user", user);
            startActivity(intent);

            Toast.makeText(ActivityKitchenLogin.this, "Login successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ActivityKitchenLogin.this, "Please check the username and password!", Toast.LENGTH_SHORT).show();

        }
        if (savelogincheckbox.isChecked()) {
            editor.putBoolean("savelogin", true);
            editor.putString("Emp_Mob", mobno1);
            editor.putString("Password", pass1);
            editor.commit();
        }
    }

}


