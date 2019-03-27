package com.restrosmart.restro.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.restrosmart.restro.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

/**
 * Created by SHREE on 09/10/2018.
 */

public class ActivityUserLogin extends AppCompatActivity {

    private EditText edtUserMob;
    private SearchableSpinner spTableNo;
    private ImageView ivNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        init();

        // Initializing a String Array
        String[] plants = new String[]{"1", "2", "3", "4", "5"};

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, plants);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spTableNo.setAdapter(spinnerArrayAdapter);

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityUserLogin.this, ActivityOrderChooser.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        edtUserMob = findViewById(R.id.edtUserMob);
        spTableNo = findViewById(R.id.spTableNo);
        ivNext = findViewById(R.id.ivNext);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void Login(View view) {

        Intent i = new Intent(ActivityUserLogin.this, ActivityUserHome.class);
        startActivity(i);
    }
}
