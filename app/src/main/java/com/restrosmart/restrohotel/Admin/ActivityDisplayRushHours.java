package com.restrosmart.restrohotel.Admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.gson.JsonObject;
import com.mcsoft.timerangepickerdialog.RangeTimePickerDialog;
import com.restrosmart.restrohotel.Adapter.AdapterRVRushHours;
import com.restrosmart.restrohotel.Captain.Activities.ActivityCapOrders;
import com.restrosmart.restrohotel.Captain.Models.UserCategory;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.ButtonListerner;
import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Interfaces.PositionListener;
import com.restrosmart.restrohotel.Model.RushHourForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.ADD_PROMOCODE;
import static com.restrosmart.restrohotel.ConstantVariables.ADD_RUSHHOURS;
import static com.restrosmart.restrohotel.ConstantVariables.DASHBOARD_CATEGORY;
import static com.restrosmart.restrohotel.ConstantVariables.DELETE_RUSHHOURS;
import static com.restrosmart.restrohotel.ConstantVariables.EDIT_PROMOCODE;
import static com.restrosmart.restrohotel.ConstantVariables.EDIT_RUSHHOURS;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;


public class ActivityDisplayRushHours extends AppCompatActivity implements RangeTimePickerDialog.ISelectedTime {

    private RecyclerView rvRushHours;
    private TextView tvToolBarTitle;
    private Toolbar mToolbar;
    private ArrayList<RushHourForm> rushHourFormArrayList;
    private LinearLayout linearLayout;
    private EditText etAmount, etMessage;
    private static TextView tvSelectDate;
    private static TextView tvSelectTime;
    private Button btnSave, btnUpdate, btnCancel;
    private ImageView ivBtnEdit, ivBtnCancel;
    private int itemPosition;

    private View dialoglayout;
    private BottomSheetDialog dialog;
    private AlertDialog alertDialog;
    private static String format;
    private static boolean status;
    private int hotelId, parentCategoryId;
    private HashMap<String, String> hotelDetails;
    private int editedItemPosition;

    private String date[] = {"17 Jun", "18 Jun", "19 Jun", "20 Jun"};
    private String startTime[] = {"4pm-8pm", "1pm-5pm", "9am-11pm", "2pm-10pm"};

    private String messgae[] = {"Reserve to get buy 1, get 1 free on speciality medium/large pizza", "Reserve to get buy 1, get 1 free on speciality medium/large pizza",
            "Reserve to get buy 1, get 1 free on speciality medium/large pizza", "Reserve to get buy 1, get 1 free on speciality medium/large pizza"};

    private Button reset;
    private FrameLayout flAddRushHours;
    private EditText etSelectDate, etDescription, etFromTime, etToTime;
    private Spinner spMenu;
    private TextView tvTitleAdd, tvTitleUpdate;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private Sessionmanager mSessionmanager;
    private ArrayList<UserCategory> arrayListUserCategory;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rush_hours);

        init();
        setUpToolbar();

        hotelDetails = mSessionmanager.getHotelDetails();
        hotelId = Integer.parseInt(hotelDetails.get(HOTEL_ID));


        flAddRushHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEditRushHours();

            }
        });


        for (int i = 0; i < date.length; i++) {
            RushHourForm rushHourForm = new RushHourForm();
            rushHourForm.setDate(date[i]);
            rushHourForm.setTime(startTime[i]);

            rushHourForm.setMessage(messgae[i]);
            rushHourFormArrayList.add(rushHourForm);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rvRushHours.setHasFixedSize(true);
        rvRushHours.setLayoutManager(linearLayoutManager);
        AdapterRVRushHours adapterRVRushHours = new AdapterRVRushHours(this, rushHourFormArrayList, new DeleteListener() {
            @Override
            public void getDeleteListenerPosition(int position) {
                initRetrofitCallback();
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack, dialoglayout.getContext());
                mRetrofitService.retrofitData(DELETE_RUSHHOURS, service.deletePromoCode("", ""));
            }
        }, new PositionListener() {
            @Override
            public void positionListern(int position) {
                itemPosition = position;

                LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialoglayout = li.inflate(R.layout.dialog_promocode_view, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDisplayRushHours.this);
                builder.setView(dialoglayout);
                alertDialog = builder.create();

                TextView price = dialoglayout.findViewById(R.id.tv_price);

                TextView date = dialoglayout.findViewById(R.id.tv_date);
                TextView promoCodeName = dialoglayout.findViewById(R.id.tv_promocode_name);
                TextView promoCodeDesription = dialoglayout.findViewById(R.id.tv_promo_description);
                //  ImageView btnClose=dialoglayout.findViewById(R.id.ivCloseDialog);


                //  String strPrice = rushHourFormArrayList.get(position).getOfferValue();

                //  price.setText(strPrice);
                date.setText(rushHourFormArrayList.get(position).getDate());
                //  promoCodeName.setText(arrayListPromoCode.get(position).getOffer());
                promoCodeDesription.setText("Customer need to apply FLAT  code to avail 30% off. offer is valid till JUN 15 2019");


                alertDialog.show();

            }
        }, new EditListener() {
            @Override
            public void getEditListenerPosition(int position) {
                editedItemPosition = position;
                AddEditRushHours();
            }
        });

        rvRushHours.scrollToPosition(rushHourFormArrayList.size() - 1);
        rvRushHours.setAdapter(adapterRVRushHours);

        for (int i = 0; i < rushHourFormArrayList.size(); i++) {
            //   etAmount.setFocusable(false);
            // etAmount.setText(rushHourFormArrayList.get(i).getOffer());
            //tvOffer.setText(rushHourFormArrayList.get(i).getOffer());
            //  tvDate.setText(rushHourFormArrayList.get(i).getDate());
            //   btnSave.setVisibility(View.GONE);

        }

       /* ivBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             *//*   etMessage.setFocusable(false);
                //  etAmount.setText(rushHourFormArrayList.get(position).getOffer());
                // tvOffer.setText(rushHourFormArrayList.get(position).getOffer());
                tvSelectDate.setText(rushHourFormArrayList.get(itemPosition).getDate());
                tvSelectTime.setText(rushHourFormArrayList.get(itemPosition).getTime());
                etMessage.setText(rushHourFormArrayList.get(itemPosition).getMessage());

                btnSave.setVisibility(View.GONE);
                ivBtnCancel.setVisibility(View.GONE);
                ivBtnEdit.setVisibility(View.VISIBLE);
                btnUpdate.setVisibility(View.GONE);
*//*
            }
        });
*/
        /*ivBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < rushHourFormArrayList.size(); i++) {

                    //   etAmount.setFocusableInTouchMode(true);
                    // etAmount.setFocusable(true);

                   *//* InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(etAmount, InputMethodManager.SHOW_IMPLICIT);
*//*
                    //  etAmount.setText(rushHourFormArrayList.get(i).getOffer());
                    //  tvOffer.setText(rushHourFormArrayList.get(i).getOffer());
                    tvSelectDate.setText(rushHourFormArrayList.get(i).getDate());
                    tvSelectTime.setText(rushHourFormArrayList.get(i).getTime());
                    btnSave.setVisibility(View.GONE);
                    ivBtnEdit.setVisibility(View.GONE);
                    ivBtnCancel.setVisibility(View.VISIBLE);
                    btnUpdate.setVisibility(View.VISIBLE);


                }
            }
        });*/

       /* btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivBtnEdit.setVisibility(View.VISIBLE);
                ivBtnCancel.setVisibility(View.GONE);
                btnUpdate.setVisibility(View.GONE
                );
            }
        });*/

    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String strJsonObject = jsonObject.toString();

                switch (requestId) {
                    case ADD_RUSHHOURS:
                        try {
                            JSONObject object = new JSONObject(strJsonObject);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityDisplayRushHours.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityDisplayRushHours.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case EDIT_RUSHHOURS:
                        try {
                            JSONObject object = new JSONObject(strJsonObject);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityDisplayRushHours.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityDisplayRushHours.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case DELETE_RUSHHOURS:
                        try {
                            JSONObject object = new JSONObject(strJsonObject);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityDisplayRushHours.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityDisplayRushHours.this, object.getString("message"), Toast.LENGTH_SHORT).show();
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
                Log.d("", "RetrofitError" + error);


            }
        };
    }

    private void AddEditRushHours() {
        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        dialoglayout = li.inflate(R.layout.bottomsheet_add_rushhours, null);
        dialog = new BottomSheetDialog(ActivityDisplayRushHours.this);
        dialog.setContentView(dialoglayout);

        tvTitleAdd = dialoglayout.findViewById(R.id.tv_add_promocode);
        tvTitleAdd.setVisibility(View.VISIBLE);
        etSelectDate = dialoglayout.findViewById(R.id.et_select_date);
        etDescription = dialoglayout.findViewById(R.id.et_description);
        etFromTime = dialoglayout.findViewById(R.id.et_from_time);
        etToTime = dialoglayout.findViewById(R.id.et_to_time);
        // spMenu = dialoglayout.findViewById(R.id.sp_parent_category);


        btnSave = dialoglayout.findViewById(R.id.btnSave);
        btnCancel = dialoglayout.findViewById(R.id.btnCancel);
        btnUpdate = dialoglayout.findViewById(R.id.btnUpdate);

    /*    initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getApplicationContext());
        mRetrofitService.retrofitData(DASHBOARD_CATEGORY, (service.getCategory(hotelId)));
*/
        etSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(dialoglayout.getContext(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        String year = String.valueOf(selectedyear);
                        String mon = String.valueOf(selectedmonth);
                        String day = String.valueOf(selectedday);
                        etSelectDate.setText(day + "/" + mon + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

        etFromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(dialoglayout.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        etFromTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        etToTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(dialoglayout.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        etToTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        if (editedItemPosition != 0) {
            tvTitleAdd.setText("Edit RushHours");
            btnSave.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            etSelectDate.setText(rushHourFormArrayList.get(editedItemPosition).getDate());
            etFromTime.setText(rushHourFormArrayList.get(editedItemPosition).getTime());
            etDescription.setText(rushHourFormArrayList.get(editedItemPosition).getMessage());

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initRetrofitCallback();
                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                    mRetrofitService = new RetrofitService(mResultCallBack, dialoglayout.getContext());
                    mRetrofitService.retrofitData(EDIT_RUSHHOURS, service.editRushHours("", etSelectDate.getText().toString(),
                            etFromTime.getText().toString(),
                            etDescription.getText().toString(),
                            etToTime.getText().toString()));

                }
            });


        } else {
            btnSave.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);
            tvTitleAdd.setText("Add RushHours");

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initRetrofitCallback();
                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                    mRetrofitService = new RetrofitService(mResultCallBack, dialoglayout.getContext());
                    mRetrofitService.retrofitData(ADD_RUSHHOURS, service.addRushHours(etSelectDate.getText().toString(),
                            etFromTime.getText().toString(),
                            etDescription.getText().toString(),
                            etToTime.getText().toString()));


                }
            });
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
//                Intent intent=new Intent(ActivityDisplayPromocode.this,ActivityAddPromoCode.class);
//                startActivity(intent);
//
    }


    private void setUpToolbar() {
        tvToolBarTitle.setText("Rush Hours");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void init() {

        mSessionmanager = new Sessionmanager(this);
        rvRushHours = findViewById(R.id.rv_rushhours);
        mToolbar = findViewById(R.id.toolbar);
        tvToolBarTitle = findViewById(R.id.tx_title);
        rushHourFormArrayList = new ArrayList<>();
        linearLayout = findViewById(R.id.linear);

        //  etAmount = findViewById(R.id.et_amount);
        tvSelectDate = findViewById(R.id.tv_date);
        tvSelectTime = findViewById(R.id.tv_time);
        btnSave = findViewById(R.id.btn_save);
        //  ivBtnEdit = findViewById(R.id.iv_btn_edit);
        //  ivBtnCancel = findViewById(R.id.iv_btn_cancel);
        btnUpdate = findViewById(R.id.btn_update);
        flAddRushHours = findViewById(R.id.ivAddRushHours);
        arrayListUserCategory = new ArrayList<>();
        // etMessage = findViewById(R.id.etv_enter_message);
        // LayoutCalender=findViewById(R.id.relative_layout);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onSelectedTime(int hourStart, int minuteStart, int hourEnd, int minuteEnd) {
        Toast.makeText(this, "Start: " + hourStart + ":" + minuteStart + "\nEnd: " + hourEnd + ":" + minuteEnd, Toast.LENGTH_SHORT).show();

        tvSelectTime.setText(hourStart + ":" + minuteStart + " - " + hourEnd + ":" + minuteEnd);
    }
}
