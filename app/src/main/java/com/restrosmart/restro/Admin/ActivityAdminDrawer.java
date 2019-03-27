package com.restrosmart.restro.Admin;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.restrosmart.restro.Interfaces.ApiService;
import com.restrosmart.restro.Interfaces.IResult;
import com.restrosmart.restro.Model.GetEmployeeDetails;
import com.restrosmart.restro.R;
import com.restrosmart.restro.RetrofitClientInstance;
import com.restrosmart.restro.RetrofitService;
import com.restrosmart.restro.Utils.AlertUtils;
import com.restrosmart.restro.Utils.Sessionmanager;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.restrosmart.restro.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restro.Utils.Sessionmanager.HOTEL_ID;
import static com.restrosmart.restro.Utils.Sessionmanager.ROLE_ID;

public class ActivityAdminDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    private String emp_name, emp_email, emp_mob, emp_pass, emp_uname, emp_status, emp_add, emp_id, emp_adhar, emp_img, emp_role,hotelId,branchId;

    private CircleImageView circleImageView;
    private TextView name;
    private TextView email;

    RetrofitService mRetrofitService;
    IResult mResultCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_drawer);

        Sessionmanager sharedPreferanceManage = new Sessionmanager(ActivityAdminDrawer.this);
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();
        emp_role = name_info.get(ROLE_ID);
       hotelId=name_info.get(HOTEL_ID);
       branchId=name_info.get(BRANCH_ID);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        // navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setCheckedItem(R.id.screen_order_area);
        navigationView.getMenu().performIdentifierAction(R.id.screen_area, 0);

        View hView = navigationView.getHeaderView(0);
        circleImageView = (CircleImageView) hView.findViewById(R.id.imageView);
        name = (TextView) hView.findViewById(R.id.tx_name);
        email = (TextView) hView.findViewById(R.id.tx_email);

        getEmployeeDetail();





        //  getEmp(user);

        Menu menu = navigationView.getMenu();
        MenuItem allorder = menu.findItem(R.id.nav_all_orders);
        MenuItem nav_menu = menu.findItem(R.id.nav_menu);
        MenuItem nav_daily_offers = menu.findItem(R.id.nav_daily_offers);
        MenuItem nav_add_employee = menu.findItem(R.id.nav_add_employee);
        MenuItem nav_settings = menu.findItem(R.id.nav_settings);
        //navigationView.setItemBackgroundResource(R.color.colorAccent);

        if (emp_role.equals("2")) {
            allorder.setVisible(true);
            nav_menu.setVisible(true);
            nav_daily_offers.setVisible(true);
            nav_add_employee.setVisible(true);
            nav_settings.setVisible(true);

        } else {
            allorder.setVisible(true);
            nav_menu.setVisible(false);
            nav_daily_offers.setVisible(false);
            nav_add_employee.setVisible(false);
            nav_settings.setVisible(false);

        }



    }

    private void getEmployeeDetail() {


        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        Call<List<GetEmployeeDetails>> call= service.getallEmployees("1","1");

        call.enqueue(new Callback<List<GetEmployeeDetails>>() {
            @Override
            public void onResponse(Call<List<GetEmployeeDetails>> call, Response<List<GetEmployeeDetails>> response) {

                List<GetEmployeeDetails> getEmployee =  response.body();

                getData(getEmployee);

            }

            private void getData(List<GetEmployeeDetails> getEmployee) {

                for(int i=0;i<getEmployee.size();i++)
                {
                    int roleId=getEmployee.get(i).getEmpId();
                    if(roleId==Integer.parseInt(emp_role))
                    {

                        name.setText(getEmployee.get(i).getEmpName());
                        email.setText(getEmployee.get(i).getEmpEmail());
                        Picasso.with(ActivityAdminDrawer.this).load(getEmployee.get(i).getEmpImg()).resize(500, 500).into(circleImageView);


                    }
                }
            }

            @Override
            public void onFailure(Call<List<GetEmployeeDetails>> call, Throwable t) {

                Toast.makeText(ActivityAdminDrawer.this, ""+t, Toast.LENGTH_SHORT).show();
            }
        });



    }




    private void setFirstItemNavigationView() {
        navigationView.setCheckedItem(R.id.tablayout);
        navigationView.getMenu().performIdentifierAction(R.id.tablayout, 0);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        AlertUtils.AlertDialoge(this, android.R.drawable.ic_dialog_alert, "Logout", "Are you sure You want to logout?");
    }


    Fragment fragment = null;
    String title = "";

    @SuppressWarnings("StatementWithEmptyBody")
    private boolean isStartup = true;

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (isStartup) {
            ((FrameLayout) findViewById(R.id.screen_area)).removeAllViews();
            isStartup = false;
        }

        Bundle args = new Bundle();

        if (id == R.id.nav_all_orders) {
            Bundle bundle = new Bundle();
            bundle.putString("role", emp_role);
            fragment = new AllOrders();
            fragment.setArguments(args);
            fragment.setArguments(bundle);
            title = "All Orders";

        } else if (id == R.id.nav_menu) {
            fragment = new MenuItems();
            fragment.setArguments(args);
            title = "Menu";

        } else if (id == R.id.nav_daily_offers) {

            fragment = new DailyOffers();
            fragment.setArguments(args);
            title = "Daily Offers";


        } else if (id == R.id.nav_add_employee) {

            fragment = new FragmentEmployeeList();
            fragment.setArguments(args);
            title = "Our Employees";
        }

        else if (id == R.id.nav_add_payment_methods) {

                fragment = new FragPaymentMethods();
                fragment.setArguments(args);
                title = "Payment Methods";

        } else if (id == R.id.nav_settings) {

            fragment = new AdminSettings();
            fragment.setArguments(args);
            title = "Settings";

        }


        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.screen_area, fragment);
           // ft.detach(fragment);
           // ft.attach(fragment);
            ft.commit();

            getSupportActionBar().setTitle(title);
          /*  if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack();
            }*/
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
