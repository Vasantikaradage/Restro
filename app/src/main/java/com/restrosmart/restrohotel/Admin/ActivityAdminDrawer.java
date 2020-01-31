package com.restrosmart.restrohotel.Admin;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
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
import android.view.MenuInflater;
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
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.EmployeeForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.AlertUtils;
import com.restrosmart.restrohotel.Utils.Sessionmanager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.DISPLAY_WATER_BOTTLE;
import static com.restrosmart.restrohotel.ConstantVariables.EDIT_WATER_BOTTLE;
import static com.restrosmart.restrohotel.ConstantVariables.GET_ALL_EMPLOYEE;
import static com.restrosmart.restrohotel.ConstantVariables.SAVE_WATER_BOTTLE;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.EMP_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.ROLE_ID;

public class ActivityAdminDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private View dialoglayout;
    private IntentFilter intentFilter, intentFilterToppings;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;


    private String title = "";
    private NavigationView navigationView;
    private LinearLayout linearLayoutHeader;
    private ActionBarDrawerToggle toggle;
    private String emp_role, hotelId, branchId, empId,waterBottleId="",waterBottleName;
    private CircleImageView circleImageView;
    private TextView name;
    private TextView tvEmail, tvBottlePrice, textInfo, textTitlePrice,tvBottleTitleName;
    private EditText editTextPrice,etWaterBottleName;
    private int waterBottlePrice;
    private ImageView imageBtnEdit;
    private Button btnCancel, btnEdit,btnSave;
    private LinearLayout linearLayout;
    @SuppressWarnings("StatementWithEmptyBody")
    private boolean isStartup = true;
    private AlertDialog dialog;
    private Toolbar toolbar;
    MyReceiver myReceiver;
    MyReceiverTopping myReceiverTopping;

    private String refresh_categoryList;
    private String refresh_toppingList;
    private  int waterBottleStatus, waterPrice;

    Fragment fragment = null;

    HashMap<String, String> name_info;
    private ApiService apiService;
    private Sessionmanager sharedPreferanceManage;

    private ArrayList<EmployeeForm> arrayListEmployee;
    private MenuItem itemShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_drawer);
        init();

        //get information from session
        name_info = sharedPreferanceManage.getHotelDetails();
        emp_role = name_info.get(ROLE_ID);
        hotelId = name_info.get(HOTEL_ID);
     //   branchId = name_info.get(BRANCH_ID);
        empId = name_info.get(EMP_ID);

        final Intent intent = getIntent();
        refresh_categoryList = intent.getStringExtra("Refresh_CategoryList");
        refresh_toppingList = intent.getStringExtra("Refresh_ToppingList");

        intentFilter = new IntentFilter("Refresh_CategoryList");
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        myReceiver = new MyReceiver();
        registerReceiver(myReceiver, intentFilter);

        intentFilterToppings = new IntentFilter("Refresh_ToppingList");
        intentFilterToppings.addCategory(Intent.CATEGORY_DEFAULT);
        myReceiverTopping = new MyReceiverTopping();
        registerReceiver(myReceiverTopping, intentFilterToppings);

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
        linearLayoutHeader = (LinearLayout) hView.findViewById(R.id.linear_layout);
        circleImageView = (CircleImageView) hView.findViewById(R.id.imageView);
        name = (TextView) hView.findViewById(R.id.tx_name);
        tvEmail = (TextView) hView.findViewById(R.id.tx_email);

        if (refresh_categoryList != null) {
           // itemShow.setVisible(true);
            fragment = new FragmentMenuItems();
            title = "Menu Card";
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.screen_area, fragment);
            ft.commit();
            getSupportActionBar().setTitle(title);

        } else if (refresh_toppingList != null) {
            //itemShow.setVisible(false);
            fragment = new FragmentToppings();
            title = "Toppings";
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
        MenuItem nav_topins = menu.findItem(R.id.nav_toppings);
        MenuItem nav_dashborad = menu.findItem(R.id.nav_dashboard);
        MenuItem nav_tables = menu.findItem(R.id.nav_table);
        MenuItem nav_assisgn_table=menu.findItem(R.id.nav_assign_table);


        if (emp_role.equals("1")) {
            allorder.setVisible(true);
            nav_menu.setVisible(true);
            nav_daily_offers.setVisible(true);
            nav_add_employee.setVisible(true);
            nav_settings.setVisible(true);
            nav_report.setVisible(true);
            nav_topins.setVisible(true);
            nav_dashborad.setVisible(true);
            nav_tables.setVisible(true);
            nav_assisgn_table.setVisible(true);

        } else
            {

            allorder.setVisible(false);
            nav_menu.setVisible(false);
            nav_daily_offers.setVisible(false);
            nav_add_employee.setVisible(true);
            nav_settings.setVisible(true);
            nav_report.setVisible(true);
            nav_topins.setVisible(false);
            nav_dashborad.setVisible(true);
            nav_tables.setVisible(true);
            nav_assisgn_table.setVisible(false);

        }
        getEmployeeDetail();



        linearLayoutHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentProfile = new Intent(ActivityAdminDrawer.this, ActivityAdminProfile.class);
                intentProfile.putParcelableArrayListExtra("employeeList", (ArrayList<? extends Parcelable>) arrayListEmployee);
                intentProfile.putExtra("empId", Integer.parseInt(empId));
                startActivity(intentProfile);
            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
        unregisterReceiver(myReceiverTopping);
        finish();
    }

    private void init() {
        arrayListEmployee = new ArrayList<>();
        sharedPreferanceManage = new Sessionmanager(ActivityAdminDrawer.this);
    }

    private void getEmployeeDetail() {
        retrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityAdminDrawer.this);
        mRetrofitService.retrofitData(GET_ALL_EMPLOYEE, (service.getallEmployees(Integer.parseInt(hotelId)
               )));

    }

    private void retrofitCallBack() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {

                JsonObject jsonObject = response.body();
                String empInfo = jsonObject.toString();

                try {
                    JSONObject object = new JSONObject(empInfo);
                    int status = object.getInt("status");
                    if (status == 1) {

                        JSONArray jsonArray = object.getJSONArray("allemployee");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObjectEmp = jsonArray.getJSONObject(i);
                            EmployeeForm employeeForm = new EmployeeForm();
                            employeeForm.setEmpId(jsonObjectEmp.getInt("Emp_Id"));
                            employeeForm.setEmpName(jsonObjectEmp.getString("Emp_Name"));
                            employeeForm.setEmpImg(jsonObjectEmp.getString("Emp_Img"));
                            employeeForm.setEmpEmail(jsonObjectEmp.getString("Emp_Email"));
                            employeeForm.setEmpAddress(jsonObjectEmp.getString("Emp_Address"));
                            employeeForm.setUserName(jsonObjectEmp.getString("User_Name"));
                            employeeForm.setHotelName(jsonObjectEmp.getString("Hotel_Name"));
                            employeeForm.setRole(jsonObjectEmp.getString("Role"));

                            employeeForm.setEmpMob(jsonObjectEmp.getString("Emp_Mob"));
                            employeeForm.setEmpAdharId(jsonObjectEmp.getString("Emp_Adhar_Id"));

                            employeeForm.setActiveStatus(jsonObjectEmp.getInt("Active_Status"));
                            employeeForm.setRole_Id(jsonObjectEmp.getInt("Role_Id"));
                            arrayListEmployee.add(employeeForm);
                            getAdapter(arrayListEmployee);

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("", "requestId" + requestId);
                Log.d("", "RetrofitError" + error);
            }
        };
    }


    private void getAdapter(ArrayList<EmployeeForm> getEmployee) {
        for (int i = 0; i < getEmployee.size(); i++) {
            int id = getEmployee.get(i).getEmpId();
            if (Integer.parseInt(empId) == id) {
                name.setText(getEmployee.get(i).getEmpName());
                tvEmail.setText(getEmployee.get(i).getEmpEmail());
                Picasso.with(ActivityAdminDrawer.this).load( getEmployee.get(i).getEmpImg()).resize(500, 500).into(circleImageView);
            }
        }
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
    protected void onPostResume() {
        super.onPostResume();
        getEmployeeDetail();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_drawer, menu);
        itemShow= menu.findItem(R.id.action_water_bottel);

     /*   if (emp_role.equals("1")) {
            itemShow.setVisible(false);
        } else*/
            itemShow.setVisible(true);

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
            mRetrofitService.retrofitData(DISPLAY_WATER_BOTTLE, service.DisplayWaterBottle(Integer.parseInt(hotelId)));


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
                        String objectInfo=jsonObject.toString();
                        try {
                            JSONObject object=new JSONObject(objectInfo);
                            int status=object.getInt("status");
                            if(status==1) {
                                Toast.makeText(ActivityAdminDrawer.this, "Save the water Bottle Successfully..", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(ActivityAdminDrawer.this, "Something went wrong ..", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                        break;

                    case DISPLAY_WATER_BOTTLE:
                        JsonObject jsonObject1 = response.body();
                        String value = jsonObject1.toString();
                        try {
                            JSONObject object = new JSONObject(value);
                            waterBottleStatus = object.getInt("status");
                            if (waterBottleStatus == 1) {

                                JSONObject object1=object.getJSONObject("waterbottle");
                                waterBottleId = object1.getString("Water_Id");
                                waterBottlePrice = object1.getInt("Water_Price");
                                waterBottleName=object1.getString("Water_Name");
                                dailogWaterBottle();
                            } else {
                                dailogWaterBottle();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case EDIT_WATER_BOTTLE:
                        JsonObject jsonObject2 = response.body();
                        String valueinfo = jsonObject2.toString();
                        try {
                            JSONObject object = new JSONObject(valueinfo);

                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityAdminDrawer.this, "Edit the water Bottle Successfully..", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(ActivityAdminDrawer.this, "Try Again..", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


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
        btnSave = dialoglayout.findViewById(R.id.btnSaveBottle);
        btnEdit = dialoglayout.findViewById(R.id.btnEditBottle);

        editTextPrice = dialoglayout.findViewById(R.id.etx_water_bottle_price);
        etWaterBottleName=dialoglayout.findViewById(R.id.etx_water_bottle_name);
        linearLayout = dialoglayout.findViewById(R.id.llwaterBottleDisplay);
        textInfo = (TextView) dialoglayout.findViewById(R.id.tx_info);
        tvBottlePrice = dialoglayout.findViewById(R.id.txPrice);
        textTitlePrice = dialoglayout.findViewById(R.id.txTitlePrice);
        tvBottleTitleName=dialoglayout.findViewById(R.id.txTitleName);
        imageBtnEdit = (ImageView) dialoglayout.findViewById(R.id.imageBtn_edit);


        if (!waterBottleId.equals("")) {
            linearLayout.setVisibility(View.VISIBLE);
            imageBtnEdit.setVisibility(View.VISIBLE);
            tvBottlePrice.setVisibility(View.VISIBLE);

            textTitlePrice.setVisibility(View.GONE);
            tvBottleTitleName.setVisibility(View.GONE);
            editTextPrice.setVisibility(View.GONE);
            etWaterBottleName.setVisibility(View.GONE);
            textInfo.setVisibility(View.GONE);
            btnEdit.setVisibility(View.GONE);

            String price = String.valueOf(waterBottlePrice);
            tvBottlePrice.setText(price);
            etWaterBottleName.setText(waterBottleName);
            dialog.show();

            imageBtnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editTextPrice.setVisibility(View.VISIBLE);
                    etWaterBottleName.setVisibility(View.VISIBLE);
                    btnEdit.setVisibility(View.VISIBLE);
                    btnCancel.setVisibility(View.VISIBLE);
                    textTitlePrice.setVisibility(View.VISIBLE);
                    tvBottleTitleName.setVisibility(View.VISIBLE);

                    tvBottlePrice.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.GONE);
                    imageBtnEdit.setVisibility(View.GONE);

                    String price = String.valueOf(waterBottlePrice);
                    editTextPrice.setText(price);

                    btnEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(isValid()) {
                                initRetrofitCallBack();
                                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                                mRetrofitService = new RetrofitService(mResultCallBack, ActivityAdminDrawer.this);

                                mRetrofitService.retrofitData(EDIT_WATER_BOTTLE, service.editWaterBottle(waterBottleId,
                                        etWaterBottleName.getText().toString(),
                                        Integer.parseInt(editTextPrice.getText().toString()),
                                        Integer.parseInt(hotelId)
                                ));
                            }

                        }
                    });
                    dialog.show();

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }

            });

        }else
        {
            btnSave.setVisibility(View.VISIBLE);
            editTextPrice.setVisibility(View.VISIBLE);
            etWaterBottleName.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);
            textTitlePrice.setVisibility(View.GONE);
            tvBottleTitleName.setVisibility(View.GONE);

            btnEdit.setVisibility(View.GONE);
            imageBtnEdit.setVisibility(View.GONE);

           // waterPrice = Integer.parseInt();
            dialog.show();

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isValid()) {
                        initRetrofitCallBack();
                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        mRetrofitService = new RetrofitService(mResultCallBack, ActivityAdminDrawer.this);
                        mRetrofitService.retrofitData(SAVE_WATER_BOTTLE, service.AddWaterBottle(
                                etWaterBottleName.getText().toString(),
                                Integer.parseInt(editTextPrice.getText().toString()),
                                Integer.parseInt(hotelId)
                        ));

                    }
                }

            });
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
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
        if (id == R.id.nav_dashboard) {
           // itemShow.setVisible(false);
            fragment = new fragmentDashboard();
            title = "DashBoard";

        } else if (id == R.id.nav_all_orders) {
            Bundle bundle = new Bundle();
           // itemShow.setVisible(false);
            bundle.putString("role", emp_role);
            fragment = new FragmentAllOrders();
            fragment.setArguments(args);
            fragment.setArguments(bundle);
            title = "All Orders";

        }  else if (id == R.id.nav_toppings) {
           // itemShow.setVisible(false);
            fragment = new FragmentToppings();
            // fragment.setArguments(args);
            title = "Toppings";

        } else if (id == R.id.nav_menu) {
           itemShow.setVisible(true);
            fragment = new FragmentMenuItems();
            // fragment.setArguments(args);
            title = "Menu Card";

        } else if (id == R.id.nav_daily_offers) {
           // itemShow.setVisible(false);
            fragment = new FragmentDailyOffers();
            fragment.setArguments(args);
            title = "Daily Offers";


        } else if (id == R.id.nav_add_employee) {
         //   itemShow.setVisible(false);
            fragment = new FragmentViewEmployee();
            fragment.setArguments(args);
            title = "Our Employees";

        } else if (id == R.id.nav_table) {
            // itemShow.setVisible(false);
            fragment = new FragmentTableDetails();
            title = "Table Details";

        }
        else if (id == R.id.nav_assign_table) {
            // itemShow.setVisible(false);
            fragment = new FragmentAssignDetails();
            title = "Assign Table";

        }
        else if (id == R.id.nav_add_payment_methods) {
          //  itemShow.setVisible(false);
            fragment = new FragmentPaymentMethods();
            fragment.setArguments(args);
            title = "Payment Methods";

        } else if (id == R.id.nav_reports) {
          //  itemShow.setVisible(false);
            fragment = new FragmentReports();
            fragment.setArguments(args);
            title = "Reports";

        } else if (id == R.id.nav_settings) {
           // itemShow.setVisible(false);
            fragment = new FragmentAdminSettings();
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


    private boolean isValid() {
        if (etWaterBottleName.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityAdminDrawer.this, "Please enter water bottle name..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (editTextPrice.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityAdminDrawer.this, "Please enter water bottle price", Toast.LENGTH_SHORT).show();
            return false;
        } /*else if (branchInfo == 0) {
            Toast.makeText(ActivityNewAddEmployee.this, "Please Select Branch", Toast.LENGTH_SHORT).show();
            return false;
        }*/


        return true;
    }
    }
