package com.restrosmart.restrohotel.Admin;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.CategoryForm;
import com.restrosmart.restrohotel.Model.MenuDisplayForm;
import com.restrosmart.restrohotel.Model.ParentCategoryForm;
import com.restrosmart.restrohotel.R;

import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Response;


import static com.restrosmart.restrohotel.ConstantVariables.ADD_NEW_OFFER;
import static com.restrosmart.restrohotel.ConstantVariables.EDIT_OFFER;
import static com.restrosmart.restrohotel.ConstantVariables.MENU_DISPLAY;
import static com.restrosmart.restrohotel.ConstantVariables.PARENT_CATEGORY;
import static com.restrosmart.restrohotel.ConstantVariables.SUB_CATEGORY_DISPLAY;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

/**
 * Created by SHREE on 30/10/2018.
 */
public class ActivityAddNewOffer extends AppCompatActivity {
    private ArrayList<ParentCategoryForm> mParentCategoryArraylist;
    private ArrayList<CategoryForm> mSubCategoryArrylist;
    private ArrayList<MenuDisplayForm> mMenuArraylist;
    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;
    private EditText etxOfferName, etxOfferValue;
    private RadioGroup radioGroupValue, radioGroupOfferBy;
    private RadioButton radioButtonRs, radioButtonDis, radioButtonByMenu, radioButtonByCoupon, radioButtonByOther;
    private TextView txFromDate, txToDate, txFromTime, txToTime, txTitleOffer;
    private LinearLayout linearLayoutMenu, linearLayoutCoupon, linerOfferBy, linearOfferTitle;
    private Sessionmanager sessionmanager;
    private String mOfferPrice, hotelId, branchId, mFromTime, mToTime, mFromDate, mToDate, mOffername, mOfferFromDate, mOfferToDate, mOfferValue, mOfferBy;
    private Spinner spParentCategory, spSubCategory, spMenu;
    private int mOfferStatus, mOfferId, parentCategorySelectedId, subCategorySelectedId, menuSelectedId, mSwitchSatus;
    private Switch swStatus;
    private Button btnOfferSave, btnOfferUpdate;
    private String timeSet = "";
    private Toolbar mTopToolbar;
    private TextView txTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_offer);
        init();
        setUpToolBar();


        sessionmanager = new Sessionmanager(this);
        HashMap<String, String> name_info = sessionmanager.getHotelDetails();
        hotelId = name_info.get(HOTEL_ID);
        branchId = name_info.get(BRANCH_ID);
        btnOfferSave.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        //Edit offer
        if (intent.getExtras() != null) {
            mOfferId = intent.getIntExtra("offerId", 0);
            mOffername = intent.getStringExtra("offerName");
            mOfferFromDate = intent.getStringExtra("fromDate");
            mOfferToDate = intent.getStringExtra("toDate");
            mOfferValue = intent.getStringExtra("offerValue");
            mOfferStatus = intent.getIntExtra("offerStatus", 0);
            mOfferBy = intent.getStringExtra("offerBy");
            etxOfferValue.setVisibility(View.VISIBLE);
            btnOfferUpdate.setVisibility(View.VISIBLE);
            btnOfferSave.setVisibility(View.GONE);
            linerOfferBy.setVisibility(View.GONE);
            txTitleOffer.setVisibility(View.GONE);
            linearOfferTitle.setVisibility(View.GONE);

            Date fromdate = null;
            Date todate = null;
            try {
                fromdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(mOfferFromDate);
                todate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(mOfferFromDate);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            String fromTime = new SimpleDateFormat("HH:mm:ss").format(fromdate);
            String toTime = new SimpleDateFormat("HH:mm:ss").format(todate);
            String fromDate = new SimpleDateFormat("yyyy-MM-dd").format(fromdate);
            String toDate = new SimpleDateFormat("yyyy-MM-dd").format(todate);

            etxOfferName.setText(mOffername);
            txFromDate.setText(fromDate);
            txToDate.setText(toDate);
            txFromTime.setText(fromTime);
            txToTime.setText(toTime);
            int status = 1;
            if (status == mOfferStatus) {
                swStatus.setChecked(true);
                mSwitchSatus = 1;
            } else {
                swStatus.setChecked(false);
                mSwitchSatus = 0;
            }
            if (mOfferValue.contains("%")) {
                radioButtonDis.setChecked(true);

            } else {
                radioButtonRs.setChecked(true);

            }
            etxOfferValue.setText(mOfferValue);
            //update offer
            btnOfferUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateOfferCall();
                }

                private void updateOfferCall() {
                    initRetrofitCallBack();
                    String fDate = txFromDate.getText().toString();
                    String fTime = txFromTime.getText().toString();
                    String tDate = txToDate.getText().toString();
                    String ttime = txToTime.getText().toString();

                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                    mRetrofitService = new RetrofitService(mResultCallBack, ActivityAddNewOffer.this);

                    mRetrofitService.retrofitData(EDIT_OFFER, service.editOffer(
                            mOfferId,
                            mOffername,
                            fDate + " " + fTime,
                            tDate + " " + ttime,
                            etxOfferValue.getText().toString(),
                            Integer.parseInt(hotelId),
                            Integer.parseInt(branchId)
                            )
                    );
                }
            });


        } else {
            etxOfferValue.setVisibility(View.GONE);
            btnOfferUpdate.setVisibility(View.GONE);
            btnOfferSave.setVisibility(View.VISIBLE);
            //save offer
            btnOfferSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddNewOfferCall();
                }
            });
        }
        /// radio button for offer value
        radioGroupValue.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.btn_radio_rs) {
                    etxOfferValue.setVisibility(View.VISIBLE);
                    mOfferPrice = etxOfferValue.getText().toString();
                } else {
                    etxOfferValue.setVisibility(View.VISIBLE);
                    mOfferPrice = etxOfferValue.getText().toString();
                }
            }
        });
        ////radio button for offer by
        radioGroupOfferBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn_radio_menu:

                        linearLayoutMenu.setVisibility(View.VISIBLE);
                        linearLayoutCoupon.setVisibility(View.GONE);
                        radioButtonByMenu.setTextColor(R.color.white);
                        radioButtonByCoupon.setTextColor(R.color.Black);
                        radioButtonByOther.setTextColor(R.color.Black);
                        selectParentCategory();
                        break;

                    case R.id.btn_radio_coupon:
                        linearLayoutCoupon.setVisibility(View.VISIBLE);
                        linearLayoutMenu.setVisibility(View.GONE);
                        radioButtonByCoupon.setTextColor(R.color.white);
                        radioButtonByOther.setTextColor(R.color.Black);
                        radioButtonByMenu.setTextColor(R.color.Black);
                        break;

                    case R.id.btn_radio_other:
                        linearLayoutMenu.setVisibility(View.GONE);
                        linearLayoutCoupon.setVisibility(View.GONE);
                        radioButtonByOther.setTextColor(R.color.white);
                        radioButtonByCoupon.setTextColor(R.color.Black);
                        radioButtonByMenu.setTextColor(R.color.Black);
                        break;
                }
            }
        });


        //switch status
        swStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mSwitchSatus = 1;
                } else {
                    mSwitchSatus = 0;
                }
            }
        });


        //parentCategory Selected id from spinner
        spParentCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String parentCategorySeletedName = spParentCategory.getSelectedItem().toString();
                parentCategorySelectedId = mParentCategoryArraylist.get(position).getPc_id();
                subCategoryCall();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //sub category selected id from spinner
        spSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String subCategorySeletedName = spSubCategory.getSelectedItem().toString();
                subCategorySelectedId = mSubCategoryArrylist.get(position).getCategory_id();
                menuCall();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //menu selected id from spinner
        spMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String menuSeletedName = spMenu.getSelectedItem().toString();
                menuSelectedId = mMenuArraylist.get(position).getMenu_Id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setUpToolBar() {
        txTitle.setText("Add New Offer");
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void init() {
        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        txTitle = (TextView) mTopToolbar.findViewById(R.id.tx_title);

        etxOfferName = (EditText) findViewById(R.id.etx_offer_name);
        radioGroupValue = (RadioGroup) findViewById(R.id.radiogroup);
        radioButtonRs = (RadioButton) findViewById(R.id.btn_radio_rs);
        radioButtonDis = (RadioButton) findViewById(R.id.btn_radio_disc);
        etxOfferValue = (EditText) findViewById(R.id.etx_offer_value);

        radioGroupOfferBy = (RadioGroup) findViewById(R.id.radioGroupOfferBy);
        radioButtonByMenu = (RadioButton) findViewById(R.id.btn_radio_menu);
        radioButtonByCoupon = (RadioButton) findViewById(R.id.btn_radio_coupon);
        radioButtonByOther = (RadioButton) findViewById(R.id.btn_radio_other);

        txFromDate = findViewById(R.id.btn_from_date);
        txToDate = findViewById(R.id.btn_to_date);
        txFromTime = findViewById(R.id.btn_from_time);
        txToTime = findViewById(R.id.btn_to_time);

        linearLayoutMenu = (LinearLayout) findViewById(R.id.llayout_menu);
        linearLayoutCoupon = (LinearLayout) findViewById(R.id.llinear_coupon);
        linerOfferBy = (LinearLayout) findViewById(R.id.ll_offer_by);
        linearOfferTitle = (LinearLayout) findViewById(R.id.linear_layout);
        txTitleOffer = (TextView) findViewById(R.id.tx_title_offer);

        spParentCategory = (Spinner) findViewById(R.id.sp_parent_category);
        spSubCategory = (Spinner) findViewById(R.id.sp_sub_category);
        spMenu = (Spinner) findViewById(R.id.sp_menu);

        swStatus = (Switch) findViewById(R.id.statusSwitch);
        btnOfferSave = (Button) findViewById(R.id.btn_save);
        btnOfferUpdate = (Button) findViewById(R.id.btn_update);

        mParentCategoryArraylist = new ArrayList<>();
        mSubCategoryArrylist = new ArrayList<>();
        mMenuArraylist = new ArrayList<>();
    }

    private void AddNewOfferCall() {

        if (etxOfferName.getText().toString().length() == 0 || mFromTime == null || mToTime == null || mFromDate == null || mToDate == null ||
                etxOfferValue.getText().toString().length() == 0 || mSwitchSatus == 0) {
            Alert();

        } else {
            initRetrofitCallBack();
            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
            mRetrofitService = new RetrofitService(mResultCallBack, ActivityAddNewOffer.this);
            mRetrofitService.retrofitData(ADD_NEW_OFFER, service.AddNewOffer(
                    etxOfferName.getText().toString(),
                    mFromDate + " " + mFromTime,
                    mToDate + " " + mToTime,
                    etxOfferValue.getText().toString(),
                    menuSelectedId,
                    "",
                    Integer.parseInt(hotelId),
                    Integer.parseInt(branchId),
                    mSwitchSatus)
            );
        }

    }

    private void Alert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(ActivityAddNewOffer.this);
        alert.setTitle("Alert");
        alert.setMessage("Please select and enter remaining fields.");
        alert.setIcon(R.drawable.ic_cal_daily_offer);
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }

    //call retrofit for menu
    private void menuCall() {
        initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityAddNewOffer.this);
        mRetrofitService.retrofitData(MENU_DISPLAY, service.GetMenuDisplay(
                Integer.parseInt(hotelId),
                Integer.parseInt(branchId),
                subCategorySelectedId)
        );


    }

    //call retrofit for sub category
    private void subCategoryCall() {
        initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityAddNewOffer.this);
        mRetrofitService.retrofitData(SUB_CATEGORY_DISPLAY, service.GetSubCategory(Integer.parseInt(hotelId),
                Integer.parseInt(branchId),
                parentCategorySelectedId
        ));
    }

    // call retrofit for parent category
    private void selectParentCategory() {

        initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityAddNewOffer.this);
        mRetrofitService.retrofitData(PARENT_CATEGORY, service.GetAllCategory(Integer.parseInt(hotelId)));
    }

    private void initRetrofitCallBack() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                switch (requestId) {
                    case PARENT_CATEGORY:
                        JsonObject jsonObject = response.body();
                        String value = jsonObject.toString();
                        try {
                            JSONObject object = new JSONObject(value);
                            int status = object.getInt("status");
                            if (status == 1) {
                                JSONObject jsonObject1 = object.getJSONObject("AllMenu");
                                mParentCategoryArraylist.clear();
                                JSONArray jsonArray = jsonObject1.getJSONArray("MenuList");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    JSONArray jsonArray1 = jsonObject2.getJSONArray("Menu");
                                    //  String pcName = jsonObject2.getString("Name").toString();
                                    ParentCategoryForm parentCategoryForm = new ParentCategoryForm();
                                    parentCategoryForm.setPc_id(jsonObject2.getInt("Pc_Id"));
                                    parentCategoryForm.setName(jsonObject2.getString("Name").toString());
                                    mParentCategoryArraylist.add(parentCategoryForm);
                                }
                                ArrayAdapter<ParentCategoryForm> arrayAdapter = new ArrayAdapter<ParentCategoryForm>(ActivityAddNewOffer.this, android.R.layout.simple_spinner_item, mParentCategoryArraylist);
                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spParentCategory.setAdapter(arrayAdapter);
                            } else {

                            }
                        } catch (Exception e) {

                        }
                        break;

                    case SUB_CATEGORY_DISPLAY:
                        JsonObject jsonObject1 = response.body();
                        String value1 = jsonObject1.toString();
                        try {
                            JSONObject object = new JSONObject(value1);
                            int status = object.getInt("status");
                            if (status == 1) {
                                mSubCategoryArrylist.clear();
                                JSONArray jsonArray = object.getJSONArray("subcategory");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object1 = jsonArray.getJSONObject(i);

                                    CategoryForm categoryForm = new CategoryForm();
                                    categoryForm.setCategory_id(object1.getInt("Category_Id"));
                                    categoryForm.setCategory_Name(object1.getString("Category_Name"));
                                    mSubCategoryArrylist.add(categoryForm);
                                }
                            }
                            ArrayAdapter<CategoryForm> arrayAdapter = new ArrayAdapter<CategoryForm>(ActivityAddNewOffer.this, android.R.layout.simple_spinner_item, mSubCategoryArrylist);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spSubCategory.setAdapter(arrayAdapter);
                        } catch (Exception e) {

                        }
                        break;

                    case MENU_DISPLAY:
                        JsonObject jsonObjectMenu = response.body();
                        String value2 = jsonObjectMenu.toString();
                        try {
                            JSONObject object = new JSONObject(value2);
                            int status = object.getInt("status");
                            if (status == 1) {
                                mMenuArraylist.clear();
                                JSONArray jsonArray = object.getJSONArray("menu");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object1 = jsonArray.getJSONObject(i);

                                    MenuDisplayForm menuDisplayForm = new MenuDisplayForm();
                                    menuDisplayForm.setMenu_Id(object1.getInt("Menu_Id"));
                                    menuDisplayForm.setMenu_Name(object1.getString("Menu_Name"));
                                    mMenuArraylist.add(menuDisplayForm);
                                }
                            }
                            ArrayAdapter<MenuDisplayForm> arrayAdapter = new ArrayAdapter<MenuDisplayForm>(ActivityAddNewOffer.this, android.R.layout.simple_spinner_item, mMenuArraylist);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spMenu.setAdapter(arrayAdapter);
                        } catch (Exception e) {
                        }
                        break;

                    case ADD_NEW_OFFER:
                        JsonObject jsonObjectAdd = response.body();
                        String valueAdd = jsonObjectAdd.toString();
                        try {
                            JSONObject object = new JSONObject(valueAdd);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityAddNewOffer.this, "Offer Added Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        finish();
                        break;

                    case EDIT_OFFER:
                        JsonObject jsonObjecEdt = response.body();
                        String valueEdt = jsonObjecEdt.toString();
                        try {
                            JSONObject object = new JSONObject(valueEdt);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityAddNewOffer.this, "Offer Upadted Successfully", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        finish();
                        break;
                }

            }

            @Override
            public void notifyError(int requestId, Throwable error) {

            }
        };
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void BtnDateTime(View v) {
        switch (v.getId()) {
            case R.id.btn_from_date:
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(ActivityAddNewOffer.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mFromDate = String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
                                + "-" + String.valueOf(year);
                        txFromDate.setText(mFromDate);
                    }
                }, yy, mm, dd);
                datePicker.show();
                break;

            case R.id.btn_to_date:
                final Calendar calendar1 = Calendar.getInstance();
                int yy1 = calendar1.get(Calendar.YEAR);
                int mm1 = calendar1.get(Calendar.MONTH);
                int dd1 = calendar1.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker1 = new DatePickerDialog(ActivityAddNewOffer.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mToDate = String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
                                + "-" + String.valueOf(year);
                        txToDate.setText(mToDate);
                    }
                }, yy1, mm1, dd1);
                datePicker1.show();
                break;

            case R.id.btn_from_time:
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ActivityAddNewOffer.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedHour > 12) {
                            selectedHour -= 12;
                            timeSet = "PM";
                        } else if (selectedHour == 0) {
                            selectedHour += 12;
                            timeSet = "AM";
                        } else if (selectedHour == 12)
                            timeSet = "PM";
                        else
                            timeSet = "AM";

                        String min = "";
                        if (selectedMinute < 10)
                            min = "0" + selectedMinute;
                        else
                            min = String.valueOf(selectedMinute);

                        String new_hour = "";
                        if (selectedHour < 10)
                            new_hour = "0" + selectedHour;
                        else


                            new_hour = String.valueOf(selectedHour);


                        mFromTime = new_hour + ":" + min + ":" + timeSet;
                        txFromTime.setText(mFromTime);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                break;

            case R.id.btn_to_time:
                Calendar mcurrentTime1 = Calendar.getInstance();
                int hour1 = mcurrentTime1.get(Calendar.HOUR_OF_DAY);
                int minute1 = mcurrentTime1.get(Calendar.MINUTE);
                mTimePicker = new TimePickerDialog(ActivityAddNewOffer.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedHour > 12) {
                            selectedHour -= 12;
                            timeSet = "PM";
                        } else if (selectedHour == 0) {
                            selectedHour += 12;
                            timeSet = "AM";
                        } else if (selectedHour == 12)
                            timeSet = "PM";
                        else
                            timeSet = "AM";

                        String min = "";
                        if (selectedMinute < 10)
                            min = "0" + selectedMinute;
                        else
                            min = String.valueOf(selectedMinute);

                        String new_hour = "";
                        if (selectedHour < 10)
                            new_hour = "0" + selectedHour;
                        else
                            new_hour = String.valueOf(selectedHour);

                        mToTime = new_hour + ":" + min + ":" + timeSet;
                        txToTime.setText(mToTime);
                    }

                }, hour1, minute1, true);//Yes 24 hour time

                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                break;
        }
    }
}
