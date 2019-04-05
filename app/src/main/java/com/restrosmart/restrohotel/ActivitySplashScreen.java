package com.restrosmart.restrohotel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.restrosmart.restrohotel.Utils.Sessionmanager;

public class ActivitySplashScreen extends AppCompatActivity {

    private ImageView ivLogo;
    private TextView tvAppname;

    // Animation
    private Animation animLogo, animAppName;

    // Session Manager
    private Sessionmanager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        init();

        //After loading animatio set animation to particuler view(Button, Image, etc.)
        ivLogo.setAnimation(animLogo);
        tvAppname.setAnimation(animAppName);

        Thread t = new Thread() {
            public void run() {
                try {
                    sleep(4000);
                    session.CheckLogin();
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    private void init() {
        session = new Sessionmanager(getApplicationContext());

        //Load animations
        animLogo = AnimationUtils.loadAnimation(this, R.anim.logo_anim);
        animAppName = AnimationUtils.loadAnimation(this, R.anim.appname_anim);

        ivLogo = findViewById(R.id.ivLogo);
        tvAppname = findViewById(R.id.tvAppname);
    }
}
