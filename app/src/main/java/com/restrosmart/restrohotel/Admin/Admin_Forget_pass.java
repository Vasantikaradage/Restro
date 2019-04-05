package com.restrosmart.restrohotel.Admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.restrosmart.restrohotel.R;

import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Admin_Forget_pass extends AppCompatActivity {

    EditText mEmail;
    Button mForget;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__forget_pass);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEmail = findViewById(R.id.et_forget_email);
        mForget = findViewById(R.id.btn_forget);

        mForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isValidEmaillId(mEmail.getText().toString().trim())) {

                    new SweetAlertDialog(Admin_Forget_pass.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Forget Password")
                            .setContentText("Your new password has been sent to your email!")
                            .show();

                } else {

                    Toast.makeText(getApplicationContext(), "InValid Email Address.", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private boolean isValidEmaillId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}

