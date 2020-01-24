package com.restrosmart.restrohotel.Admin;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;

import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.AdapterRVPromocode;

import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.ApplyPromoCode;
import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Interfaces.PositionListener;
import com.restrosmart.restrohotel.Model.PromoCodeForm;
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

import static com.restrosmart.restrohotel.ConstantVariables.ADD_PROMOCODE;

import static com.restrosmart.restrohotel.ConstantVariables.APPLY_PROMOCODE;
import static com.restrosmart.restrohotel.ConstantVariables.DELETE_PROMOCODE;
import static com.restrosmart.restrohotel.ConstantVariables.DISPLAY_PROMOCODE;
import static com.restrosmart.restrohotel.ConstantVariables.EDIT_PROMOCODE;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class ActivityDisplayPromocode extends AppCompatActivity {

    private RecyclerView rvPromocode;
    private TextView tvToolBarTitle;
    private Toolbar mToolbar;
    private ArrayList<PromoCodeForm> arrayListPromoCode;


    private Button btnSave, btnUpdate, btnCancel;
    private int editedItemPosition = 0, offerId = 0;
    private FrameLayout flAddPromoCode;

    private View dialoglayout;
    private BottomSheetDialog dialog;
    private EditText etPrice, etDescription, etPromoCode, etFromDate, etToDate, etOfferName;
    private TextView tvTitleAdd;

    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private AlertDialog alertDialog;
    private Sessionmanager mSessionManager;
    private int hotelId, hour, min, sec;
    private Calendar mcurrentDate;
    //private String fromDate, toDate, amOrpm;
    private int offerTypeId;
    String fromDateOut, toDateOut;
    private SpinKitView spinKitView;
    private LinearLayout llNodata;
    private Date dateFrom, dateTo;
    private String seletedStartDate, seletedEndDate, getStartDate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_promocode);

        init();
        setUpToolbar();

        Intent intent = getIntent();
        offerTypeId = (intent.getIntExtra("offerTypeId", 0));


        HashMap<String, String> stringHashMap = mSessionManager.getHotelDetails();
        hotelId = Integer.parseInt(stringHashMap.get(HOTEL_ID));

        getPromoCodeInfo();
        flAddPromoCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offerId = 0;
                AddEditOffer();
            }
        });

    }

    private void getPromoCodeInfo() {
        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getBaseContext());
        mRetrofitService.retrofitData(DISPLAY_PROMOCODE, (service.displayPromoCode(offerTypeId, hotelId)));

    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String objectInfo = jsonObject.toString();

                switch (requestId) {
                    case DISPLAY_PROMOCODE:
                        try {
                            JSONObject object = new JSONObject(objectInfo);
                            int status = object.getInt("status");
                            if (status == 1) {
                                llNodata.setVisibility(View.GONE);
                                spinKitView.setVisibility(View.GONE);
                                JSONArray jsonArray = object.getJSONArray("promodata");
                                arrayListPromoCode.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject promoCodeObject = jsonArray.getJSONObject(i);
                                    PromoCodeForm promoCodeForm = new PromoCodeForm();
                                    promoCodeForm.setOfferId(promoCodeObject.getInt("Offer_Id"));
                                    promoCodeForm.setOfferValue(promoCodeObject.getString("Coupon_Code"));
                                    promoCodeForm.setFromDate(promoCodeObject.getString("FromDate"));
                                    promoCodeForm.setToDate(promoCodeObject.getString("ToDate"));
                                    promoCodeForm.setOfferDescription(promoCodeObject.getString("Offer_Desp"));
                                    promoCodeForm.setOfferPrice(promoCodeObject.getString("Offer_Price"));
                                    promoCodeForm.setOfferFromTime(promoCodeObject.getString("FromTime"));
                                    promoCodeForm.setOfferToTime(promoCodeObject.getString("ToTime"));
                                    promoCodeForm.setOfferStatus(promoCodeObject.getInt("Status"));
                                    promoCodeForm.setOfferName(promoCodeObject.getString("Offer_Name"));
                                    arrayListPromoCode.add(promoCodeForm);
                                }

                                if (arrayListPromoCode != null && arrayListPromoCode.size() > 0) {
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityDisplayPromocode.this, LinearLayoutManager.VERTICAL, false);
                                    //StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(VERTICAL);
                                    rvPromocode.setHasFixedSize(true);
                                    rvPromocode.setLayoutManager(linearLayoutManager);
                                    AdapterRVPromocode adapterRVPromocode = new AdapterRVPromocode(ActivityDisplayPromocode.this, arrayListPromoCode, new ApplyPromoCode() {
                                        @Override
                                        public void applyPromoCode(int position) {
                                            alerDailog(position);
                                        }
                                    }, new EditListener() {
                                        @Override
                                        public void getEditListenerPosition(int position) {
                                            editedItemPosition = position;
                                            offerId = arrayListPromoCode.get(position).getOfferId();
                                            AddEditOffer();
                                        }
                                    }, new DeleteListener() {
                                        @Override
                                        public void getDeleteListenerPosition(int position) {
                                            initRetrofitCall();
                                            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                                            mRetrofitService = new RetrofitService(mResultCallBack, ActivityDisplayPromocode.this);
                                            mRetrofitService.retrofitData(DELETE_PROMOCODE, service.deleteOffer(hotelId, arrayListPromoCode.get(position).getOfferId()));
                                        }
                                    }, new PositionListener() {
                                        @Override
                                        public void positionListern(int position) {
                                            LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                            View dialoglayout = li.inflate(R.layout.dialog_promocode_view, null);
                                            final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDisplayPromocode.this);
                                            builder.setView(dialoglayout);
                                            alertDialog = builder.create();

                                            TextView promoCode = dialoglayout.findViewById(R.id.tv_promocode);
                                            TextView date = dialoglayout.findViewById(R.id.tv_Promodate);
                                            ImageButton btnCancel = dialoglayout.findViewById(R.id.btn_cancel);
                                            TextView tvOfferName = dialoglayout.findViewById(R.id.tv_offername);

                                            TextView promoCodeDesription = dialoglayout.findViewById(R.id.tv_description);
                                            tvOfferName.setText(arrayListPromoCode.get(position).getOfferName());
                                            String strPrice = arrayListPromoCode.get(position).getOfferPrice();
                                            promoCode.setText(arrayListPromoCode.get(position).getOfferValue() + " " + strPrice);
                                            date.setText(arrayListPromoCode.get(position).getFromDate() + " - " + arrayListPromoCode.get(position).getToDate());

                                            promoCodeDesription.setText(arrayListPromoCode.get(position).getOfferDescription());

                                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    alertDialog.dismiss();
                                                }
                                            });
                                            alertDialog.show();


                                        }
                                    });
                                    rvPromocode.setAdapter(adapterRVPromocode);

                                }
                            } else {
                                llNodata.setVisibility(View.VISIBLE);
                                spinKitView.setVisibility(View.GONE);
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
                spinKitView.setVisibility(View.GONE);


            }
        };
    }

    private void alerDailog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDisplayPromocode.this);
        builder
                .setTitle("Apply Offer")
                .setMessage("Are you sure you want to apply this Offer ?")
                /* .setIcon(R.drawable.ic_gr_promocode)*/
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        initRetrofitCall();
                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        mRetrofitService = new RetrofitService(mResultCallBack, ActivityDisplayPromocode.this);
                        mRetrofitService.retrofitData(APPLY_PROMOCODE, service.applyPromoCodeOffer(hotelId, arrayListPromoCode.get(position).getOfferId()));
                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }


    @SuppressLint("SetTextI18n")
    private void AddEditOffer() {

        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        dialoglayout = li.inflate(R.layout.bottomsheet_add_promocode, null);
        dialog = new BottomSheetDialog(ActivityDisplayPromocode.this);
        dialog.setContentView(dialoglayout);

        tvTitleAdd = dialoglayout.findViewById(R.id.tv_add_promocode);
        tvTitleAdd.setVisibility(View.VISIBLE);
        etPrice = dialoglayout.findViewById(R.id.et_price);
        etDescription = dialoglayout.findViewById(R.id.et_description);
        etPromoCode = dialoglayout.findViewById(R.id.et_promocode);
        etFromDate = dialoglayout.findViewById(R.id.et_from_date);
        etToDate = dialoglayout.findViewById(R.id.et_to_date);
        etOfferName = dialoglayout.findViewById(R.id.et_offer_name);
        btnSave = dialoglayout.findViewById(R.id.btnSave);
        btnCancel = dialoglayout.findViewById(R.id.btnCancel);
        btnUpdate = dialoglayout.findViewById(R.id.btnUpdate);

        tvTitleAdd.setText(R.string.add_promo_code);


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
                        String myFormat = "yyyy-MM-dd h:mm:ss a";

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
                    DatePickerDialog mDatePicker1 = new DatePickerDialog(ActivityDisplayPromocode.this, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                            Calendar myCalendar = Calendar.getInstance();
                            myCalendar.set(Calendar.YEAR, selectedyear);
                            myCalendar.set(Calendar.MONTH, selectedmonth);
                            myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                            String myFormat = "yyyy-MM-dd h:mm:ss a"; //Change as you need
                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

                            Date startDate = null, endDate = null;
                            try {
                                String getEndDate = sdf.format(myCalendar.getTime());
                                endDate = sdf.parse(getEndDate);
                                startDate = sdf.parse(seletedStartDate);
                                if (startDate.after(endDate)) {
                                    Toast.makeText(ActivityDisplayPromocode.this, R.string.select_end_greater_start_date, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ActivityDisplayPromocode.this, R.string.select_first_start_date, Toast.LENGTH_SHORT).show();
                }
                // mDatePicker.show();
            }
        });


        if (offerId != 0) {
            tvTitleAdd.setText((R.string.edit_promo_code));
            btnSave.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            etPrice.setText(arrayListPromoCode.get(editedItemPosition).getOfferPrice());
            etPromoCode.setText(arrayListPromoCode.get(editedItemPosition).getOfferValue());
            etDescription.setText(arrayListPromoCode.get(editedItemPosition).getOfferDescription());
            etOfferName.setText(arrayListPromoCode.get(editedItemPosition).getOfferName());

            DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
            DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            String mFromDate = arrayListPromoCode.get(editedItemPosition).getFromDate();
            String mToDate = arrayListPromoCode.get(editedItemPosition).getToDate();

            try {
                dateFrom = inputFormat.parse(mFromDate);
                dateTo = inputFormat.parse(mToDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            fromDateOut = outputFormat.format(dateFrom) + " " + arrayListPromoCode.get(editedItemPosition).getOfferFromTime();
            toDateOut = outputFormat.format(dateTo) + " " + arrayListPromoCode.get(editedItemPosition).getOfferToTime();

            etFromDate.setText(fromDateOut);
            etToDate.setText(toDateOut);


            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (validData()) {
                        spinKitView.setVisibility(View.VISIBLE);
                        initRetrofitCall();
                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        mRetrofitService = new RetrofitService(mResultCallBack, dialoglayout.getContext());
                        mRetrofitService.retrofitData(EDIT_PROMOCODE, service.editPromoCode(offerId,
                                etPromoCode.getText().toString(),
                                etOfferName.getText().toString(),
                                etFromDate.getText().toString(),
                                etToDate.getText().toString(),
                                etPrice.getText().toString(),
                                etDescription.getText().toString(),
                                hotelId));
                        editedItemPosition = 0;
                        offerId = 0;
                    }


                }
            });
            dialog.dismiss();
            //offerId=0;

        } else {
            offerId = 0;
            editedItemPosition = 0;
            btnSave.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);
            tvTitleAdd.setText("Add Promocode");

            etPrice.setText("");
            etDescription.setText("");
            etPromoCode.setText("");
            etFromDate.setText("");
            etToDate.setText("");

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (validData()) {

                        spinKitView.setVisibility(View.VISIBLE);
                        initRetrofitCall();
                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        mRetrofitService = new RetrofitService(mResultCallBack, dialoglayout.getContext());
                        mRetrofitService.retrofitData(ADD_PROMOCODE, service.addPromoCode(offerTypeId, etPrice.getText().toString(),
                                etOfferName.getText().toString(),
                                etPromoCode.getText().toString(),
                                etDescription.getText().toString(),
                                etFromDate.getText().toString(),
                                etToDate.getText().toString(),
                                hotelId, 0));
                    }
                }
            });
            dialog.dismiss();
            //  offerId=0;


        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                editedItemPosition = 0;
            }
        });
        dialog.show();

    }

    private boolean validData() {
        if (etOfferName.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter offer name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etPrice.getText().toString().equals("0")) {
            Toast.makeText(ActivityDisplayPromocode.this, "Please enter amount", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etPrice.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityDisplayPromocode.this, "Please enter amount", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etPromoCode.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter Promo Code", Toast.LENGTH_SHORT).show();
            return false;
        } else if ((etPromoCode.getText().toString().length()) != 4) {
            Toast.makeText(this, "Please enter Promo Code upto 4 character", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etDescription.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityDisplayPromocode.this, "Please enter Description..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etFromDate.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityDisplayPromocode.this, "Please select from date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etToDate.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityDisplayPromocode.this, "Please select to date", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void initRetrofitCall() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String jsonString = jsonObject.toString();

                switch (requestId) {
                    case ADD_PROMOCODE:
                        try {
                            JSONObject object = new JSONObject(jsonString);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityDisplayPromocode.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityDisplayPromocode.this, object.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                            spinKitView.setVisibility(View.GONE);
                            dialog.dismiss();
                            getPromoCodeInfo();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;

                    case DELETE_PROMOCODE:
                        try {
                            JSONObject object = new JSONObject(jsonString);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityDisplayPromocode.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityDisplayPromocode.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                            spinKitView.setVisibility(View.GONE);
                            getPromoCodeInfo();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;

                    case EDIT_PROMOCODE:
                        try {
                            JSONObject object = new JSONObject(jsonString);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityDisplayPromocode.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityDisplayPromocode.this, object.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                            spinKitView.setVisibility(View.GONE);
                            editedItemPosition = 0;
                            offerId = 0;
                            dialog.dismiss();
                            getPromoCodeInfo();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case APPLY_PROMOCODE:
                        try {
                            JSONObject object = new JSONObject(jsonString);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityDisplayPromocode.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityDisplayPromocode.this, object.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                            getPromoCodeInfo();
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
                spinKitView.setVisibility(View.GONE);
            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        offerId = 0;
        editedItemPosition = 0;
    }

    private void setUpToolbar() {
        tvToolBarTitle.setText("Promo Code");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    private void init() {
        rvPromocode = findViewById(R.id.rv_promocode);
        mToolbar = findViewById(R.id.toolbar);
        tvToolBarTitle = findViewById(R.id.tx_title);
        arrayListPromoCode = new ArrayList<>();
        btnSave = findViewById(R.id.btn_save);
        btnUpdate = findViewById(R.id.btn_update);
        flAddPromoCode = findViewById(R.id.ivAddPromoCode);
        mSessionManager = new Sessionmanager(this);
        spinKitView = findViewById(R.id.skLoading);
        llNodata = findViewById(R.id.llNoPromoData);

    }


}
