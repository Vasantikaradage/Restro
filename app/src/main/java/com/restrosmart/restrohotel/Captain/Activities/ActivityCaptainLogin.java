package com.restrosmart.restrohotel.Captain.Activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.restrosmart.restrohotel.Captain.Fragments.CaptainProfileFragment;
import com.restrosmart.restrohotel.Captain.Fragments.HomeFragment;
import com.restrosmart.restrohotel.Captain.Fragments.SwapTableFragment;
import com.restrosmart.restrohotel.R;

public class ActivityCaptainLogin extends AppCompatActivity {

    private Toolbar mToolbar;
    private MenuItem menuCapHome, menuSwapTable, menuCapProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captain_login);

        init();
        setupToolbar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFragment(0);
    }

    private void loadFragment(int fragmentId) {

        //getSupportFragmentManager().beginTransaction().add(R.id.flContainer,new HomeFragment()).commit();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        switch (fragmentId) {
            case 0:
                ft.replace(R.id.flContainer, new HomeFragment());
                ft.commit();
                mToolbar.setTitle("Home");
                break;

            case 1:
                ft.replace(R.id.flContainer, new SwapTableFragment());
                ft.commit();
                mToolbar.setTitle("Swap Tables");
                break;

            case 2:
                ft.replace(R.id.flContainer, new CaptainProfileFragment());
                ft.commit();
                mToolbar.setTitle("Profile");
                break;

            default:
                ft.replace(R.id.flContainer, new HomeFragment());
                ft.commit();
                mToolbar.setTitle("Home");
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.captain_menu, menu);
        menuCapHome = menu.findItem(R.id.captain_home);
        menuSwapTable = menu.findItem(R.id.swap_table);
        menuCapProfile = menu.findItem(R.id.captain_profile);

        menuCapHome.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.captain_home:
                menuCapHome.setVisible(false);
                menuSwapTable.setVisible(true);
                menuCapProfile.setVisible(true);
                loadFragment(0);
                break;

            case R.id.swap_table:
                menuCapHome.setVisible(true);
                menuSwapTable.setVisible(false);
                menuCapProfile.setVisible(true);
                loadFragment(1);
                break;

            case R.id.captain_profile:
                menuCapHome.setVisible(true);
                menuSwapTable.setVisible(true);
                menuCapProfile.setVisible(false);
                loadFragment(2);
                break;
        }

        return true;
    }

    private void setupToolbar() {
        mToolbar.setTitle("Home");
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void init() {
        mToolbar = findViewById(R.id.toolbar);
    }
}
