package com.restrosmart.restrohotel.Admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.JsonObject;
import com.mcsoft.timerangepickerdialog.RangeTimePickerDialog;
import com.restrosmart.restrohotel.Adapter.AdapterRVRushHours;
import com.restrosmart.restrohotel.Adapter.AdapterRVRushHoursMenu;
import com.restrosmart.restrohotel.Captain.Models.UserCategory;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Interfaces.OfferListerner;
import com.restrosmart.restrohotel.Model.OfferMenuForm;
import com.restrosmart.restrohotel.Model.RushHourForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.ADD_RUSHHOURS;
import static com.restrosmart.restrohotel.ConstantVariables.DELETE_PROMOCODE;
import static com.restrosmart.restrohotel.ConstantVariables.DISPLAY_RUSHHOURS;
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
    private int itemPosition, offerId;
    private SpinKitView spinKitView;
    private LinearLayout llNodata;

    private View dialoglayout;
    private BottomSheetDialog dialog;
    private AlertDialog alertDialog;
    private static String format;
    private static boolean status;
    private int hotelId, parentCategoryId;
    private HashMap<String, String> hotelDetails;
    private int editedItemPosition;
    private int offerTypeId;
    private String SelectedFromDate, SelectedToDate, SelectedFromTime, SelectedToTime = null,getStartDate,seletedEndDate,seletedStartDate;
    String timeSet = "";
    private ArrayList<OfferMenuForm> offerMenuFormsArryList;

    private String date[] = {"17 Jun", "18 Jun", "19 Jun", "20 Jun"};
    private String startTime[] = {"4pm-8pm", "1pm-5pm", "9am-11pm", "2pm-10pm"};

    private String messgae[] = {"Reserve to get buy 1, get 1 free on speciality medium/large pizza", "Reserve to get buy 1, get 1 free on speciality medium/large pizza",
            "Reserve to get buy 1, get 1 free on speciality medium/large pizza", "Reserve to get buy 1, get 1 free on speciality medium/large pizza"};

    private Button reset;
    private FrameLayout flAddRushHours;
    private EditText etFromDate, etToDate, etDescription, etFromTime, etToTime, etOfferName;
    private Spinner spMenu;
    private TextView tvTitleAdd, tvTitleUpdate;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private Sessionmanager mSessionmanager;
    private ArrayList<UserCategory> arrayListUserCategory;
    private  Calendar mcurrentDate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rush_hours);

        init();
        setUpToolbar();
        Intent intent = getIntent();
        offerTypeId = intent.getIntExtra("offerTypeId", 0);

        hotelDetails = mSessionmanager.getHotelDetails();
        hotelId = Integer.parseInt(hotelDetails.get(HOTEL_ID));


        flAddRushHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offerId = 0;
                AddEditRushHours();

            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        displayRushHours();
    }

    private void displayRushHours() {
        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityDisplayRushHours.this);
        mRetrofitService.retrofitData(DISPLAY_RUSHHOURS, service.displayRushHours(hotelId, offerTypeId));
        spinKitView.setVisibility(View.GONE);
    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String strJsonObject = jsonObject.toString();

                switch (requestId) {
                    case DISPLAY_RUSHHOURS:
                        try {
                            JSONObject object = new JSONObject(strJsonObject);
                            int status = object.getInt("status");
                            if (status == 1) {
                                rushHourFormArrayList.clear();
                                llNodata.setVisibility(View.GONE);
                                JSONArray jsonArray = object.getJSONArray("rushhours");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject rushHourObj = jsonArray.getJSONObject(i);
                                    RushHourForm rushHourForm = new RushHourForm();
                                    rushHourForm.setFromDate(rushHourObj.getString("FromDate"));
                                    rushHourForm.setToDate(rushHourObj.getString("ToDate"));
                                    rushHourForm.setFromTime(rushHourObj.getString("FromTime"));
                                    rushHourForm.setToTime(rushHourObj.getString("ToTime"));
                                    rushHourForm.setRushHourId(rushHourObj.getInt("Offer_Id"));
                                    rushHourForm.setOffer_Type_Id(rushHourObj.getInt("Offer_Type_Id"));
                                    rushHourForm.setMessage(rushHourObj.getString("Offer_Desp"));
                                    rushHourForm.setOfferName(rushHourObj.getString("Offer_Name"));
                                    rushHourForm.setActiveStatus(rushHourObj.getInt("Status"));

                                    JSONArray menuArray = rushHourObj.getJSONArray("offermenu");
                                    offerMenuFormsArryList = new ArrayList<>();
                                    for (int j = 0; j < menuArray.length(); j++) {

                                        JSONObject menuObject = menuArray.getJSONObject(j);
                                        OfferMenuForm offerMenuForm = new OfferMenuForm();
                                        offerMenuForm.setOffer_maintain_Id(menuObject.getInt("Offer_maintain_Id"));
                                        offerMenuForm.setMenuId(menuObject.getString("Menu_Id"));
                                        offerMenuForm.setMenu_Name(menuObject.getString("Menu_Name"));
                                        offerMenuForm.setMenu_Ori_Price(menuObject.getString("Menu_Ori_Price"));
                                        offerMenuForm.setMenu_Offer_Price(menuObject.getString("Menu_Offer_Price"));
                                        offerMenuFormsArryList.add(offerMenuForm);
                                    }

                                    rushHourForm.setArrayListMenu(offerMenuFormsArryList);
                                    rushHourFormArrayList.add(rushHourForm);
                                }
                                if (rushHourFormArrayList.size() > 0 && rushHourFormArrayList != null) {
                                    LinearLayoutManager staggeredGridLayoutManager = new LinearLayoutManager(ActivityDisplayRushHours.this);

                                    rvRushHours.setHasFixedSize(true);
                                    rvRushHours.setLayoutManager(staggeredGridLayoutManager);
                                    AdapterRVRushHours adapterRVRushHours = new AdapterRVRushHours(ActivityDisplayRushHours.this, rushHourFormArrayList, offerTypeId, new DeleteListener() {
                                        @Override
                                        public void getDeleteListenerPosition(int position) {
                                            initRetrofitCallback();
                                            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                                            mRetrofitService = new RetrofitService(mResultCallBack, ActivityDisplayRushHours.this);
                                            mRetrofitService.retrofitData(DELETE_PROMOCODE, service.deleteOffer(hotelId, rushHourFormArrayList.get(position).getRushHourId()));
                                        }
                                    }, new OfferListerner() {

                                        @Override
                                        public void offerDisplay(ArrayList<OfferMenuForm> offerMenuForms, int pos) {
                                            itemPosition = pos;

                                            LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                            dialoglayout = li.inflate(R.layout.bottom_rushhour_view, null);
                                            dialog = new BottomSheetDialog(ActivityDisplayRushHours.this);
                                            dialog.setContentView(dialoglayout);

                                            TextView tvMenu = dialoglayout.findViewById(R.id.tv_offer_name);
                                            TextView tvDate = dialoglayout.findViewById(R.id.tv_offer_date);
                                            TextView tvTime = dialoglayout.findViewById(R.id.tv_offer_time);
                                            TextView tvDesc = dialoglayout.findViewById(R.id.tv_offer_desc);
                                            RecyclerView rvMenuItem = dialoglayout.findViewById(R.id.rv_menu_item);
                                            TextView tvTitle = dialoglayout.findViewById(R.id.tv_menu_title);
                                            LinearLayout linearLayout = dialoglayout.findViewById(R.id.linear_layout_menu);
                                            View viewline = dialoglayout.findViewById(R.id.view_line);
                                            ImageButton btnCancel=dialoglayout.findViewById(R.id.btn_cancel);

                                            tvMenu.setText(rushHourFormArrayList.get(pos).getOfferName());
                                            tvDate.setText(rushHourFormArrayList.get(pos).getFromDate() + "-" + rushHourFormArrayList.get(pos).getToDate());
                                            tvTime.setText(rushHourFormArrayList.get(pos).getFromTime() + "-" + rushHourFormArrayList.get(pos).getToTime());
                                            tvDesc.setText(rushHourFormArrayList.get(pos).getMessage());

                                            if (offerMenuForms.size() > 0 && offerMenuForms != null) {
                                                tvTitle.setVisibility(View.VISIBLE);
                                                linearLayout.setVisibility(View.VISIBLE);
                                                viewline.setVisibility(View.VISIBLE);
                                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityDisplayRushHours.this);

                                                rvMenuItem.setHasFixedSize(true);
                                                rvMenuItem.setLayoutManager(linearLayoutManager);
                                                AdapterRVRushHoursMenu adapterRVRushHoursMenu = new AdapterRVRushHoursMenu(getApplicationContext(), offerMenuForms);
                                                rvMenuItem.setAdapter(adapterRVRushHoursMenu);
                                            } else {
                                                tvTitle.setVisibility(View.GONE);
                                                linearLayout.setVisibility(View.GONE);
                                                viewline.setVisibility(View.GONE);

                                            }

                                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialog.dismiss();
                                                }
                                            });
                                            dialog.show();
                                        }
                                    }, new EditListener() {
                                        @Override
                                        public void getEditListenerPosition(int position) {
                                            editedItemPosition = position;
                                            offerId = rushHourFormArrayList.get(position).getRushHourId();
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


                                } else {

                                }
                            } else {
                                llNodata.setVisibility(View.VISIBLE);
                                spinKitView.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;
                    case ADD_RUSHHOURS:
                        try {
                            JSONObject object = new JSONObject(strJsonObject);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityDisplayRushHours.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityDisplayRushHours.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                            displayRushHours();
                            spinKitView.setVisibility(View.GONE);
                            dialog.dismiss();
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
                            offerId = 0;
                            editedItemPosition = 0;
                            spinKitView.setVisibility(View.GONE);
                            dialog.dismiss();
                            displayRushHours();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case DELETE_PROMOCODE:
                        try {
                            JSONObject object = new JSONObject(strJsonObject);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityDisplayRushHours.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityDisplayRushHours.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                            spinKitView.setVisibility(View.GONE);
                            displayRushHours();

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

    @SuppressLint("SetTextI18n")
    private void AddEditRushHours() {
        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        dialoglayout = li.inflate(R.layout.bottomsheet_add_rushhours, null);
        dialog = new BottomSheetDialog(ActivityDisplayRushHours.this);
        dialog.setContentView(dialoglayout);

        tvTitleAdd = dialoglayout.findViewById(R.id.tv_add_promocode);
        tvTitleAdd.setVisibility(View.VISIBLE);
        etFromDate = dialoglayout.findViewById(R.id.et_from_date);
        etToDate = dialoglayout.findViewById(R.id.et_to_date);
        etDescription = dialoglayout.findViewById(R.id.et_description);
        etFromTime = dialoglayout.findViewById(R.id.et_from_time);
        etToTime = dialoglayout.findViewById(R.id.et_to_time);
        etOfferName = dialoglayout.findViewById(R.id.et_offer_name);
        // spMenu = dialoglayout.findViewById(R.id.sp_parent_category);


        btnSave = dialoglayout.findViewById(R.id.btnSave);
        btnCancel = dialoglayout.findViewById(R.id.btnCancel);
        btnUpdate = dialoglayout.findViewById(R.id.btnUpdate);

    /*    initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getApplicationContext());
        mRetrofitService.retrofitData(DASHBOARD_CATEGORY, (service.getCategory(hotelId)));
*/
        etFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(dialoglayout.getContext(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                        String myFormat = "yyyy-MM-dd";

                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
                        getStartDate = sdf.format(myCalendar.getTime());
                        etFromDate.setText(getStartDate);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                String formattedDate = df.format(c);
                try {
                    Date d = df.parse(formattedDate);
                    mDatePicker.getDatePicker().setMinDate(d.getTime());

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mDatePicker.show();
            }
        });


        etToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                seletedStartDate = getStartDate;


                if (seletedStartDate != null) {
                    DatePickerDialog mDatePicker1 = new DatePickerDialog(ActivityDisplayRushHours.this, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                            Calendar myCalendar = Calendar.getInstance();
                            myCalendar.set(Calendar.YEAR, selectedyear);
                            myCalendar.set(Calendar.MONTH, selectedmonth);
                            myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                            String myFormat = "yyyy-MM-dd"; //Change as you need
                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

                            Date startDate = null, endDate = null;
                            try {
                                String getEndDate = sdf.format(myCalendar.getTime());
                                endDate = sdf.parse(getEndDate);
                                startDate = sdf.parse(seletedStartDate);
                                if (startDate.after(endDate)) {
                                    Toast.makeText(ActivityDisplayRushHours.this, R.string.select_end_greater_start_date, Toast.LENGTH_SHORT).show();
                                } else {
                                    etToDate.setText(sdf.format(myCalendar.getTime()));
                                    seletedEndDate = sdf.format(myCalendar.getTime());
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }, mYear, mMonth, mDay);

                    Date date = Calendar.getInstance().getTime();
                    SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    String formattedDate1 = sdformat.format(date);
                    try {
                        Date d = sdformat.parse(formattedDate1);
                        mDatePicker1.getDatePicker().setMinDate(d.getTime());

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    mDatePicker1.show();
                } else {
                    Toast.makeText(ActivityDisplayRushHours.this, R.string.select_first_start_date, Toast.LENGTH_SHORT).show();
                }
                // mDatePicker.show();
            }

        });

        etFromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                int second = mcurrentTime.get(Calendar.SECOND);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(dialoglayout.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        myCalendar.set(Calendar.MINUTE, selectedMinute);
                        String myFormat = "hh:mm:ss a"; //Change as you need
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
                        etFromTime.setText(sdf.format(myCalendar.getTime()));

                        // Toast.makeText(ActivityDisplayRushHours.this, sdf.format(myCalendar.getTime()), Toast.LENGTH_SHORT).show();
                    }
                }, hour, minute, false);//Yes 24 hour time
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
                int second = mcurrentTime.get(Calendar.SECOND);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(dialoglayout.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        myCalendar.set(Calendar.MINUTE, selectedMinute);
                        String myFormat = "hh:mm:ss a"; //Change as you need
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
                        etToTime.setText(sdf.format(myCalendar.getTime()));
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        if (offerId != 0) {
            tvTitleAdd.setText((R.string.edit_rush_hour));
            btnSave.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);

            etDescription.setText(rushHourFormArrayList.get(editedItemPosition).getMessage());
            etOfferName.setText(rushHourFormArrayList.get(editedItemPosition).getOfferName());


            DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
            DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            String inputDateFrom = rushHourFormArrayList.get(editedItemPosition).getFromDate();
            String inputDateTo = rushHourFormArrayList.get(editedItemPosition).getToDate();
            Date dateFrom = null;
            Date dateTo = null;
            try {
                dateFrom = inputFormat.parse(inputDateFrom);
                dateTo = inputFormat.parse(inputDateTo);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String outputDateFromStr = outputFormat.format(dateFrom);
            String outputDateToStr = outputFormat.format(dateTo);

            etFromDate.setText(outputDateFromStr);
            etToDate.setText(outputDateToStr);

            etFromTime.setText(rushHourFormArrayList.get(editedItemPosition).getFromTime());
            etToTime.setText(rushHourFormArrayList.get(editedItemPosition).getToTime());


            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isValidData()) {
                        spinKitView.setVisibility(View.VISIBLE);
                        initRetrofitCallback();
                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        mRetrofitService = new RetrofitService(mResultCallBack, dialoglayout.getContext());
                        mRetrofitService.retrofitData(EDIT_RUSHHOURS, service.editRushHours(offerId,
                                String.valueOf(hotelId),
                                etFromDate.getText().toString() + " " + etFromTime.getText().toString(),
                                etToDate.getText().toString() + " " + etToTime.getText().toString(),
                                etDescription.getText().toString(),
                                etOfferName.getText().toString()
                        ));

                        offerId = 0;
                        editedItemPosition = 0;
                    }

                }
            });


        } else {
            btnSave.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);
            tvTitleAdd.setText("Add Rush Hours");

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isValidData()) {

                        spinKitView.setVisibility(View.VISIBLE);
                        initRetrofitCallback();
                        //Toast.makeText(ActivityDisplayRushHours.this, etFromTime.getText().toString(), Toast.LENGTH_SHORT).show();
                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        mRetrofitService = new RetrofitService(mResultCallBack, dialoglayout.getContext());
                        mRetrofitService.retrofitData(ADD_RUSHHOURS, service.addRushHours(
                                etFromDate.getText().toString() + " " + etFromTime.getText().toString(),
                                etToDate.getText().toString() + " " + etToTime.getText().toString(), hotelId,
                                etDescription.getText().toString(),
                                0,
                                etOfferName.getText().toString(),
                                offerTypeId));


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
        dialog.show();

    }

    private boolean isValidData() {
        if (etOfferName.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityDisplayRushHours.this, "Please enter  offer name", Toast.LENGTH_SHORT).show();
            return false;

        } else if (etDescription.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityDisplayRushHours.this, "Please enter Description..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etFromDate.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityDisplayRushHours.this, "Please select from date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etToDate.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityDisplayRushHours.this, "Please select to date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etFromTime.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityDisplayRushHours.this, "Please select from time", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etToTime.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityDisplayRushHours.this, "Please select to time", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
        tvSelectDate = findViewById(R.id.tv_date);
        tvSelectTime = findViewById(R.id.tv_time);
        btnSave = findViewById(R.id.btn_save);
        btnUpdate = findViewById(R.id.btn_update);
        flAddRushHours = findViewById(R.id.ivAddRushHours);
        arrayListUserCategory = new ArrayList<>();
        offerMenuFormsArryList = new ArrayList<>();

        spinKitView = findViewById(R.id.skLoading);
        llNodata = findViewById(R.id.llNoRushData);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        offerId = 0;
        editedItemPosition = 0;
    }

    @Override
    public void onSelectedTime(int hourStart, int minuteStart, int hourEnd, int minuteEnd) {
        Toast.makeText(this, "Start: " + hourStart + ":" + minuteStart + "\nEnd: " + hourEnd + ":" + minuteEnd, Toast.LENGTH_SHORT).show();
        tvSelectTime.setText(hourStart + ":" + minuteStart + " - " + hourEnd + ":" + minuteEnd);
    }
}
