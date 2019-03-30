package com.restrosmart.restro.Admin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restro.Interfaces.ApiService;
import com.restrosmart.restro.Interfaces.IResult;
import com.restrosmart.restro.Model.GetEmployeeDetails;
import com.restrosmart.restro.R;
import com.restrosmart.restro.RetrofitClientInstance;
import com.restrosmart.restro.RetrofitService;
import com.restrosmart.restro.Utils.AlertUtils;
import com.restrosmart.restro.Utils.Sessionmanager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.restrosmart.restro.ConstantVariables.DISPLAY_WATER_BOTTLE;
import static com.restrosmart.restro.ConstantVariables.EDIT_WATER_BOTTLE;
import static com.restrosmart.restro.ConstantVariables.SAVE_WATER_BOTTLE;
import static com.restrosmart.restro.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restro.Utils.Sessionmanager.HOTEL_ID;
import static com.restrosmart.restro.Utils.Sessionmanager.ROLE_ID;

public class ActivityAdminDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private View dialoglayout;
    private IntentFilter intentFilter;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private Fragment fragment = null;
    private String title = "";
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private String emp_role, hotelId, branchId;
    private CircleImageView circleImageView;
    private TextView name;
    private TextView tvEmail, tvBottlePrice, textInfo, textTitlePrice;
    private EditText editTextPrice;
    private int menuId, menuPrice;
    private ImageView imageBtnEdit;
    private Button btnCancel, btnSave, btnEdit;
    private LinearLayout linearLayout;
    @SuppressWarnings("StatementWithEmptyBody")
    private boolean isStartup = true;
    private AlertDialog dialog;
    private Toolbar toolbar;
   MyReceiver myReceiver;
    private  String  refresh_categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_drawer);
        init();

        Sessionmanager sharedPreferanceManage = new Sessionmanager(ActivityAdminDrawer.this);
        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();
        emp_role = name_info.get(ROLE_ID);
        hotelId = name_info.get(HOTEL_ID);
        branchId = name_info.get(BRANCH_ID);

        Intent intent=getIntent();
        refresh_categoryList=intent.getStringExtra("Refresh_CategoryList");

        intentFilter = new IntentFilter("Refresh_CategoryList");
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        myReceiver = new MyReceiver();
        registerReceiver(myReceiver, intentFilter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        navigationView.setCheckedItem(R.id.screen_order_area);
        navigationView.getMenu().performIdentifierAction(R.id.screen_area, 0);

        View hView = navigationView.getHeaderView(0);
        circleImageView = (CircleImageView) hView.findViewById(R.id.imageView);
        name = (TextView) hView.findViewById(R.id.tx_name);
        tvEmail = (TextView) hView.findViewById(R.id.tx_email);

        if(refresh_categoryList!=null)
        {
            fragment = new MenuItems();
            //fragment.setArguments(args);
            title = "Menu";
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.screen_area, fragment);
            ft.commit();
            getSupportActionBar().setTitle(title);
        }

        Menu menu = navigationView.getMenu();
        MenuItem allorder = menu.findItem(R.id.nav_all_orders);
        MenuItem nav_menu = menu.findItem(R.id.nav_menu);
        MenuItem nav_daily_offers = menu.findItem(R.id.nav_daily_offers);
        MenuItem nav_add_employee = menu.findItem(R.id.nav_add_employee);
        MenuItem nav_settings = menu.findItem(R.id.nav_settings);
        MenuItem nav_report = menu.findItem(R.id.nav_reports);

        getEmployeeDetail();
        if (emp_role.equals("2")) {
            allorder.setVisible(true);
            nav_menu.setVisible(true);
            nav_daily_offers.setVisible(true);
            nav_add_employee.setVisible(true);
            nav_settings.setVisible(true);
            nav_report.setVisible(true);

        } else if (emp_role.equals("1")) {

            allorder.setVisible(false);
            nav_menu.setVisible(false);
            nav_daily_offers.setVisible(false);
            nav_add_employee.setVisible(true);
            nav_settings.setVisible(true);
            nav_report.setVisible(true);
        } else {

        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    private void init() {
    }

    private void getEmployeeDetail() {
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        Call<List<GetEmployeeDetails>> call = service.getallEmployees("1", "1");
        call.enqueue(new Callback<List<GetEmployeeDetails>>() {
            @Override
            public void onResponse(Call<List<GetEmployeeDetails>> call, Response<List<GetEmployeeDetails>> response) {
                List<GetEmployeeDetails> getEmployee = response.body();
                getData(getEmployee);

            }

            private void getData(List<GetEmployeeDetails> getEmployee) {
                for (int i = 0; i < getEmployee.size(); i++) {
                    int roleId = getEmployee.get(i).getEmpId();
                    if (roleId == Integer.parseInt(emp_role)) {
                        name.setText(getEmployee.get(i).getEmpName());
                        tvEmail.setText(getEmployee.get(i).getEmpEmail());
                        Picasso.with(ActivityAdminDrawer.this).load(getEmployee.get(i).getEmpImg()).resize(500, 500).into(circleImageView);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GetEmployeeDetails>> call, Throwable t) {
                Toast.makeText(ActivityAdminDrawer.this, "" + t, Toast.LENGTH_SHORT).show();
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
        MenuItem item = menu.findItem(R.id.action_water_bottel);
        if (emp_role.equals("1")) {
            item.setVisible(false);
        } else
            item.setVisible(true);

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
        } else if (id == R.id.action_water_bottel) {
            initRetrofitCallBack();

            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
            mRetrofitService = new RetrofitService(mResultCallBack, ActivityAdminDrawer.this);
            mRetrofitService.retrofitData(DISPLAY_WATER_BOTTLE, service.DisplayWaterBottle(
                    Integer.parseInt(branchId),
                    Integer.parseInt(hotelId)
            ));


        }

        return super.onOptionsItemSelected(item);
    }

    private void initRetrofitCallBack() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                switch (requestId) {
                    case SAVE_WATER_BOTTLE:
                        JsonObject jsonObject = response.body();
                        Toast.makeText(ActivityAdminDrawer.this, "Save the water Bottle Successfully..", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        break;

                    case DISPLAY_WATER_BOTTLE:
                        JsonObject jsonObject1 = response.body();
                        String value = jsonObject1.toString();
                        try {
                            JSONObject object = new JSONObject(value);
                            int status = object.getInt("status");
                            if (status == 1) {
                                menuId = object.getInt("Menu_Id");
                                menuPrice = object.getInt("Non_Ac_Rate");
                                if (menuId == 1) {
                                    dailogWaterBottle();
                                }
                            } else {
                                dailogWaterBottle();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case EDIT_WATER_BOTTLE:
                        JsonObject jsonObject2 = response.body();
                        Toast.makeText(ActivityAdminDrawer.this, "Edit the water Bottle Successfully..", Toast.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("", "requestId" + requestId);
                Log.d("", "retrofitError" + error);
            }
        };
    }

    private void dailogWaterBottle() {
        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialoglayout = li.inflate(R.layout.dailog_water_bottle, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialoglayout);
        dialog = builder.create();

        btnCancel = dialoglayout.findViewById(R.id.btnCancelBottle);
        btnSave = dialoglayout.findViewById(R.id.btnConfirmBottle);
        btnEdit = dialoglayout.findViewById(R.id.btnEditBottle);
        editTextPrice = dialoglayout.findViewById(R.id.etx_water_bottle_price);
        linearLayout = dialoglayout.findViewById(R.id.llwaterBottleDisplay);
        textInfo = (TextView) dialoglayout.findViewById(R.id.tx_info);
        tvBottlePrice = dialoglayout.findViewById(R.id.txPrice);
        textTitlePrice = dialoglayout.findViewById(R.id.txTitlePrice);
        imageBtnEdit = (ImageView) dialoglayout.findViewById(R.id.imageBtn_edit);

        if (menuId == 1) {
            linearLayout.setVisibility(View.VISIBLE);
            imageBtnEdit.setVisibility(View.VISIBLE);
            tvBottlePrice.setVisibility(View.VISIBLE);

            textTitlePrice.setVisibility(View.GONE);
            editTextPrice.setVisibility(View.GONE);
            textInfo.setVisibility(View.GONE);
            btnEdit.setVisibility(View.GONE);

            String price = String.valueOf(menuPrice);
            tvBottlePrice.setText(price);

            imageBtnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editTextPrice.setVisibility(View.VISIBLE);
                    btnEdit.setVisibility(View.VISIBLE);
                    btnCancel.setVisibility(View.VISIBLE);
                    textTitlePrice.setVisibility(View.VISIBLE);

                    tvBottlePrice.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.GONE);
                    imageBtnEdit.setVisibility(View.GONE);

                    String price = String.valueOf(menuPrice);
                    editTextPrice.setText(price);

                    btnEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            initRetrofitCallBack();
                            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                            mRetrofitService = new RetrofitService(mResultCallBack, ActivityAdminDrawer.this);

                            mRetrofitService.retrofitData(EDIT_WATER_BOTTLE, service.editWaterBottle(
                                    "Water Bottle",
                                    Integer.parseInt(editTextPrice.getText().toString()),
                                    Integer.parseInt(branchId),
                                    Integer.parseInt(hotelId)
                            ));

                        }
                    });
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            });
        } else {
            btnSave.setVisibility(View.VISIBLE);
            editTextPrice.setVisibility(View.VISIBLE);
            textInfo.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);
            textTitlePrice.setVisibility(View.GONE);

            btnEdit.setVisibility(View.GONE);
            imageBtnEdit.setVisibility(View.GONE);

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initRetrofitCallBack();
                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                    mRetrofitService = new RetrofitService(mResultCallBack, ActivityAdminDrawer.this);
                    mRetrofitService.retrofitData(SAVE_WATER_BOTTLE, service.AddWaterBottle(
                            1,
                            "Water Bottle",
                            "water.png",
                            0,
                            Integer.parseInt(editTextPrice.getText().toString()),
                            Integer.parseInt(branchId),
                            Integer.parseInt(hotelId)
                    ));
                }


            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    private void logout() {
        AlertUtils.AlertDialoge(this, android.R.drawable.ic_dialog_alert, "Logout", "Are you sure You want to logout?");
    }

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

            fragment = new ViewEmployee();
            fragment.setArguments(args);
            title = "Our Employees";
        } else if (id == R.id.nav_hotel_details) {

            fragment = new FragmentHotelDetails();
            fragment.setArguments(args);
            title = "Hotel Details";
        } else if (id == R.id.nav_add_payment_methods) {

            fragment = new FragPaymentMethods();
            fragment.setArguments(args);
            title = "Payment Methods";
        } else if (id == R.id.nav_reports) {

            fragment = new FragReports();
            fragment.setArguments(args);
            title = "Reports";

        } else if (id == R.id.nav_settings) {

            fragment = new AdminSettings();
            fragment.setArguments(args);
            title = "Settings";

        }
        else if( refresh_categoryList.equals("Add_Category"))
        {
            fragment = new MenuItems();
            fragment.setArguments(args);
            title = "Menu";
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.screen_area, fragment);
            // ft.detach(fragment);
            // ft.attach(fragment);
            ft.commit();
            getSupportActionBar().setTitle(title);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}
