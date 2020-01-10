package com.restrosmart.restrohotel.SuperAdmin.Activities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.SuperAdmin.Fragments.FragmentHotelDetails;
import com.restrosmart.restrohotel.SuperAdmin.Fragments.FragmentSAEmployee;
import com.restrosmart.restrohotel.SuperAdmin.Fragments.FragmentSADashboard;
import com.restrosmart.restrohotel.SuperAdmin.Fragments.FragmentSAReports;
import com.restrosmart.restrohotel.SuperAdmin.Fragments.FragmentSASettings;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.restrosmart.restrohotel.Utils.Sessionmanager.EMP_NAME;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.ROLE_ID;

public class ActivitySADashBoradDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private LinearLayout linearLayoutHeader;
    private ActionBarDrawerToggle toggle;
    private String emp_role, hotelId, branchId, empId;
    private CircleImageView circleImageView;
    private TextView name;
    private TextView tvEmail;
    private boolean isStartup = true;
    Fragment fragment = null;
    private String title = "";
    private Sessionmanager sessionmanager;
  //  private HashMap<String,String> superAdminInfo;
    Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_admin_drawer);

        init();


        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        HashMap<String,String> superAdminInfo =sessionmanager.getSuperAdminDetails();
        String string=superAdminInfo.get(EMP_NAME);

        navigationView = (NavigationView) findViewById(R.id.nav_sa_view);
       View  hView = navigationView.getHeaderView(0);
        linearLayoutHeader = (LinearLayout) hView.findViewById(R.id.linear_layout);
        circleImageView = (CircleImageView) hView.findViewById(R.id.imageView);
        name = (TextView) hView.findViewById(R.id.tx_name);
        tvEmail = (TextView) hView.findViewById(R.id.tx_email);


        name.setText(string);


        navigationView.setNavigationItemSelectedListener(ActivitySADashBoradDrawer.this);

        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        navigationView.setCheckedItem(R.id.screen_order_area);
        navigationView.getMenu().performIdentifierAction(R.id.screen_area, 0);



        intent=getIntent();
        tvEmail.setText(intent.getStringExtra("email"));

        linearLayoutHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentProfile = new Intent(ActivitySADashBoradDrawer.this, ActivitySAProfile.class);
               // intentProfile.putExtra("empId", Integer.parseInt(empId));
                startActivity(intentProfile);
            }
        });






    }

    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (isStartup) {
            ((FrameLayout) findViewById(R.id.screen_area)).removeAllViews();
            isStartup = false;
        }

        Bundle args = new Bundle();
        if (id == R.id.nav_sa_dashboard) {
            // itemShow.setVisible(false);
            fragment = new FragmentSADashboard();
            title = "DashBoard";

        } else if (id == R.id.nav_add_sa_employee) {
            //   itemShow.setVisible(false);
            fragment = new FragmentSAEmployee();
            fragment.setArguments(args);
            title = "Our Admin";
        }
        else if (id == R.id.nav_hotel_sa_details) {
            //  itemShow.setVisible(false);
            fragment = new FragmentHotelDetails();
            fragment.setArguments(args);
            title = "Hotel Details";

        } else if (id == R.id.nav_sa_reports) {
            //  itemShow.setVisible(false);
            fragment = new FragmentSAReports();
            fragment.setArguments(args);
            title = "Reports";

        } else if (id == R.id.nav_sa_settings) {
            // itemShow.setVisible(false);
            fragment = new FragmentSASettings();
            fragment.setArguments(args);
            title = "Settings";
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.screen_area, fragment);
            ft.detach(fragment);
            ft.attach(fragment);
            ft.commit();
            getSupportActionBar().setTitle(title);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        sessionmanager=new Sessionmanager(ActivitySADashBoradDrawer.this);
    }
}
