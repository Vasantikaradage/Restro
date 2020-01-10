package com.restrosmart.restrohotel.Admin;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.JsonObject;

import com.restrosmart.restrohotel.Adapter.AdapterRVScratchCardMenu;
import com.restrosmart.restrohotel.Adapter.AdapterRVScratchcard;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Interfaces.OfferListerner;
import com.restrosmart.restrohotel.Model.OfferMenuForm;
import com.restrosmart.restrohotel.Model.ParentCategoryForm;
import com.restrosmart.restrohotel.Model.ScratchCardForm;
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

import static com.restrosmart.restrohotel.ConstantVariables.ADD_SCRATCHCARD;
import static com.restrosmart.restrohotel.ConstantVariables.DELETE_PROMOCODE;
import static com.restrosmart.restrohotel.ConstantVariables.DISPLAY_SCRATCHCARD;
import static com.restrosmart.restrohotel.ConstantVariables.EDIT_SCRATCHCARD;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

public class ActivityDisplayScratchCard extends AppCompatActivity {

    private RecyclerView rvScratchCard;
    private ArrayList<ScratchCardForm> scratchCardFormArrayList;
    private TextView tvToolBarTitle;
    private Toolbar mToolbar;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private int mHotelId;
    private SpinKitView spinKitView;
    private LinearLayout llNodata;
    private Sessionmanager sharedPreferanceManage;
    private ArrayList<ParentCategoryForm> parentCategoryFormArrayList;
    private FrameLayout flAddScratchCard;
    private View dialoglayout;
    private BottomSheetDialog dialog;
    private TextView tvTitleAdd;
    private EditText etFromDate, etToDate, etDescription, etOfferName, etTotPplCount, etTotWinnerCnt;
    private Button btnSave, btnUpdate, btnCancel;
    private int offerTypeId, editedItemPosition, itemPosition, offerId;
    private Calendar mcurrentDate;
    private ArrayList<OfferMenuForm> offerMenuFormArrayList;
    private String fromDateOut, toDateOut,getStartDate,seletedStartDate,seletedEndDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_scratchcard);

        init();
        setUpToolBar();
        Intent intent = getIntent();
        offerTypeId = intent.getIntExtra("offerTypeId", 0);

        HashMap<String, String> name_info = sharedPreferanceManage.getHotelDetails();
        mHotelId = Integer.parseInt(name_info.get(HOTEL_ID));

        flAddScratchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offerId = 0;
                AddEditScratchCard();
            }
        });
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        displayScratcCard();
    }

    private void displayScratcCard() {
        initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, ActivityDisplayScratchCard.this);
        mRetrofitService.retrofitData(DISPLAY_SCRATCHCARD, service.displayScratchcard(offerTypeId, mHotelId));
        spinKitView.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    private void AddEditScratchCard() {

        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        dialoglayout = li.inflate(R.layout.bottomsheet_add_scratchcard, null);
        dialog = new BottomSheetDialog(ActivityDisplayScratchCard.this);
        dialog.setContentView(dialoglayout);

        tvTitleAdd = dialoglayout.findViewById(R.id.tv_add_scratchcard);
        tvTitleAdd.setVisibility(View.VISIBLE);

        etDescription = dialoglayout.findViewById(R.id.et_description);
        etFromDate = dialoglayout.findViewById(R.id.et_from_date);
        etToDate = dialoglayout.findViewById(R.id.et_to_date);
        etOfferName = dialoglayout.findViewById(R.id.et_offer_name);
        etTotPplCount = dialoglayout.findViewById(R.id.et_total_people_count);
        etTotWinnerCnt = dialoglayout.findViewById(R.id.et_winner_people_count);


        btnSave = dialoglayout.findViewById(R.id.btnSave);
        btnCancel = dialoglayout.findViewById(R.id.btnCancel);
        btnUpdate = dialoglayout.findViewById(R.id.btnUpdate);

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
                        String myFormat = "yyyy-MM-dd hh:mm:ss a";

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
                    DatePickerDialog mDatePicker1 = new DatePickerDialog(ActivityDisplayScratchCard.this, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                            Calendar myCalendar = Calendar.getInstance();
                            myCalendar.set(Calendar.YEAR, selectedyear);
                            myCalendar.set(Calendar.MONTH, selectedmonth);
                            myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                            String myFormat = "yyyy-MM-dd hh:mm:ss a"; //Change as you need
                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

                            Date startDate = null, endDate = null;
                            try {
                                String getEndDate = sdf.format(myCalendar.getTime());
                                endDate = sdf.parse(getEndDate);
                                startDate = sdf.parse(seletedStartDate);
                                if (startDate.after(endDate)) {
                                    Toast.makeText(ActivityDisplayScratchCard.this, R.string.select_end_greater_start_date, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ActivityDisplayScratchCard.this, R.string.select_first_start_date, Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (offerId != 0) {
            tvTitleAdd.setText(R.string.edit_scratch_card);
            btnSave.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            etFromDate.setText("");
            etToDate.setText("");

            etOfferName.setText(scratchCardFormArrayList.get(editedItemPosition).getOfferName());
            etDescription.setText(scratchCardFormArrayList.get(editedItemPosition).getDiscription());
            etTotPplCount.setText(String.valueOf(scratchCardFormArrayList.get(editedItemPosition).getTotalPplCnt()));
            etTotWinnerCnt.setText(String.valueOf(scratchCardFormArrayList.get(editedItemPosition).getWinnerPplCnt()));

            DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
            DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            String mFromDate = scratchCardFormArrayList.get(editedItemPosition).getFromDate();
            String mToDate = scratchCardFormArrayList.get(editedItemPosition).getToDate();
            Date dateFrom = null;
            Date dateTo = null;
            try {
                dateFrom = inputFormat.parse(mFromDate);
                dateTo = inputFormat.parse(mToDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            fromDateOut = outputFormat.format(dateFrom) + " " + scratchCardFormArrayList.get(editedItemPosition).getFromTime();
            toDateOut = outputFormat.format(dateTo) + " " + scratchCardFormArrayList.get(editedItemPosition).getToTime();

            etFromDate.setText(fromDateOut);
            etToDate.setText(toDateOut);


            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isValidData()) {
                        initRetrofitCallBack();
                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        mRetrofitService = new RetrofitService(mResultCallBack, dialoglayout.getContext());
                        mRetrofitService.retrofitData(EDIT_SCRATCHCARD, service.editScratchcard(offerId,
                                etDescription.getText().toString(),
                                etOfferName.getText().toString(),
                                etFromDate.getText().toString(),
                                etToDate.getText().toString(),
                                etTotPplCount.getText().toString(),
                                etTotWinnerCnt.getText().toString(), mHotelId));
                    }
                    offerId = 0;
                    editedItemPosition = 0;
                    spinKitView.setVisibility(View.VISIBLE);
                }
            });
        } else {
            btnSave.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);
            tvTitleAdd.setText((R.string.add_scratch_card));


            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isValidData()) {
                        if (etTotPplCount.getText().toString().length() >
                                etTotWinnerCnt.getText().toString().length()) {

                            spinKitView.setVisibility(View.VISIBLE);
                            initRetrofitCallBack();
                            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                            mRetrofitService = new RetrofitService(mResultCallBack, dialoglayout.getContext());
                            mRetrofitService.retrofitData(ADD_SCRATCHCARD, service.addScratchcard(offerTypeId,
                                    etOfferName.getText().toString(),
                                    etDescription.getText().toString(),
                                    etFromDate.getText().toString(),
                                    etToDate.getText().toString(),
                                    etTotPplCount.getText().toString(),
                                    etTotWinnerCnt.getText().toString(),
                                    mHotelId));


                        } else {
                            Toast.makeText(ActivityDisplayScratchCard.this, "Winner Count is less than Total People Count", Toast.LENGTH_SHORT).show();
                        }
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
            Toast.makeText(ActivityDisplayScratchCard.this, "Please enter  offer name", Toast.LENGTH_SHORT).show();
            return false;

        } else if (etDescription.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityDisplayScratchCard.this, "Please enter Description..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etFromDate.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityDisplayScratchCard.this, "Please select from date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etToDate.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityDisplayScratchCard.this, "Please select to date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etTotPplCount.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityDisplayScratchCard.this, "Please enter people count", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etTotWinnerCnt.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityDisplayScratchCard.this, "Please enter winner count", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initRetrofitCallBack() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String objectInfo = jsonObject.toString();

                switch (requestId) {
                    case ADD_SCRATCHCARD:
                        try {
                            JSONObject object = new JSONObject(objectInfo);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityDisplayScratchCard.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityDisplayScratchCard.this, object.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                            spinKitView.setVisibility(View.GONE);
                            offerId = 0;
                            editedItemPosition = 0;
                            displayScratcCard();
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;

                    case EDIT_SCRATCHCARD:
                        try {
                            JSONObject object = new JSONObject(objectInfo);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityDisplayScratchCard.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityDisplayScratchCard.this, object.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                            spinKitView.setVisibility(View.GONE);
                            offerId = 0;
                            editedItemPosition = 0;
                            dialog.dismiss();
                            displayScratcCard();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;

                    case DELETE_PROMOCODE:
                        try {
                            JSONObject object = new JSONObject(objectInfo);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityDisplayScratchCard.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityDisplayScratchCard.this, object.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                            spinKitView.setVisibility(View.GONE);
                            displayScratcCard();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;

                    case DISPLAY_SCRATCHCARD:
                        JSONObject object = null;
                        try {
                            object = new JSONObject(objectInfo);
                            int status = object.getInt("status");
                            if (status == 1) {
                                llNodata.setVisibility(View.GONE);
                                JSONArray jsonArray = object.getJSONArray("scratchcards");
                                scratchCardFormArrayList.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject ScratchObject = jsonArray.getJSONObject(i);
                                    ScratchCardForm scratchCardForm = new ScratchCardForm();
                                    scratchCardForm.setOfferName(ScratchObject.getString("Offer_Name"));
                                    scratchCardForm.setFromDate(ScratchObject.getString("FromDate"));
                                    scratchCardForm.setToDate(ScratchObject.getString("ToDate"));
                                    scratchCardForm.setFromTime(ScratchObject.getString("FromTime"));
                                    scratchCardForm.setToTime(ScratchObject.getString("ToTime"));
                                    scratchCardForm.setDiscription(ScratchObject.getString("Offer_Desp"));
                                    scratchCardForm.setStatus(ScratchObject.getInt("Status"));
                                    scratchCardForm.setOfferId(ScratchObject.getInt("Offer_Id"));
                                    scratchCardForm.setWinnerPplCnt(ScratchObject.getInt("PplWinnerCount"));
                                    scratchCardForm.setTotalPplCnt(ScratchObject.getInt("PplTotalCount"));

                                    JSONArray array = ScratchObject.getJSONArray("offermenu");
                                    offerMenuFormArrayList = new ArrayList<>();
                                    for (int j = 0; j < array.length(); j++) {
                                        JSONObject scratchCardMenu = array.getJSONObject(j);
                                        OfferMenuForm offerMenuForm = new OfferMenuForm();
                                        offerMenuForm.setOffer_maintain_Id(scratchCardMenu.getInt("Offer_maintain_Id"));
                                        offerMenuForm.setMenu_Name(scratchCardMenu.getString("Menu_Name"));
                                        offerMenuForm.setMenu_Offer_Price(scratchCardMenu.getString("Offer_Qty_Count"));
                                        offerMenuForm.setMenuId(scratchCardMenu.getString("Menu_Id"));
                                        offerMenuForm.setMenu_Image_Name(scratchCardMenu.getString("Menu_Img"));
                                        offerMenuFormArrayList.add(offerMenuForm);
                                    }

                                    scratchCardForm.setOfferMenuFormArrayList(offerMenuFormArrayList);
                                    scratchCardFormArrayList.add(scratchCardForm);
                                }

                                if (scratchCardFormArrayList.size() > 0 && scratchCardFormArrayList != null) {

                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityDisplayScratchCard.this, LinearLayoutManager.VERTICAL, false);

                                    rvScratchCard.setHasFixedSize(true);
                                    rvScratchCard.setLayoutManager(linearLayoutManager);
                                    AdapterRVScratchcard adapterRVScratchcard = new AdapterRVScratchcard(ActivityDisplayScratchCard.this, scratchCardFormArrayList, offerTypeId, new EditListener() {
                                        @Override
                                        public void getEditListenerPosition(int position) {
                                            offerId = scratchCardFormArrayList.get(position).getOfferId();
                                            editedItemPosition = position;
                                            AddEditScratchCard();

                                        }
                                    }, new OfferListerner() {
                                        @Override
                                        public void offerDisplay(ArrayList<OfferMenuForm> offerMenuForms, int pos) {

                                            itemPosition = pos;

                                            LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                            dialoglayout = li.inflate(R.layout.bottom_scrtchcard_view, null);
                                            dialog = new BottomSheetDialog(ActivityDisplayScratchCard.this);
                                            dialog.setContentView(dialoglayout);

                                            TextView tvMenu = dialoglayout.findViewById(R.id.tv_offer_name);
                                            TextView tvDate = dialoglayout.findViewById(R.id.tv_offer_date);
                                            TextView tvTime = dialoglayout.findViewById(R.id.tv_offer_time);
                                            TextView tvDesc = dialoglayout.findViewById(R.id.tv_offer_desc);
                                            RecyclerView rvMenuItem = dialoglayout.findViewById(R.id.rv_menu_item);
                                            TextView tvTitle = dialoglayout.findViewById(R.id.tv_menu_title);
                                            TextView tvWinnerPplCnt = dialoglayout.findViewById(R.id.tv_ppl_winner_cnt);
                                            LinearLayout linearLayout = dialoglayout.findViewById(R.id.linear_layout_menu);
                                            ImageView btnCancel = dialoglayout.findViewById(R.id.btn_cancel);
                                            View viewline = dialoglayout.findViewById(R.id.view_line);

                                            tvMenu.setText(scratchCardFormArrayList.get(pos).getOfferName());
                                            tvDate.setText(scratchCardFormArrayList.get(pos).getFromDate() + " - " + scratchCardFormArrayList.get(pos).getToDate());
                                            tvTime.setText(scratchCardFormArrayList.get(pos).getFromTime() + " - " + scratchCardFormArrayList.get(pos).getToTime());
                                            tvDesc.setText(scratchCardFormArrayList.get(pos).getDiscription());
                                            //String cnt= String.valueOf(scratchCardFormArrayList.get(pos).getWinnerPplCnt());
                                            tvWinnerPplCnt.setText(String.valueOf(scratchCardFormArrayList.get(pos).getWinnerPplCnt()));

                                            if (offerMenuForms.size() > 0 && offerMenuForms != null) {
                                                tvTitle.setVisibility(View.VISIBLE);
                                                linearLayout.setVisibility(View.VISIBLE);
                                                viewline.setVisibility(View.VISIBLE);
                                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityDisplayScratchCard.this);

                                                rvMenuItem.setHasFixedSize(true);
                                                rvMenuItem.setLayoutManager(linearLayoutManager);
                                                AdapterRVScratchCardMenu adapterRVScratchCardMenu = new AdapterRVScratchCardMenu(getApplicationContext(), offerMenuForms);
                                                rvMenuItem.setAdapter(adapterRVScratchCardMenu);
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
                                    }, new DeleteListener() {
                                        @Override
                                        public void getDeleteListenerPosition(int position) {

                                            initRetrofitCallBack();
                                            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                                            mRetrofitService = new RetrofitService(mResultCallBack, ActivityDisplayScratchCard.this);
                                            mRetrofitService.retrofitData(DELETE_PROMOCODE, service.deleteOffer(mHotelId, scratchCardFormArrayList.get(position).getOfferId()));

                                        }
                                    });
                                    rvScratchCard.setAdapter(adapterRVScratchcard);

                                } else {
                                    llNodata.setVisibility(View.VISIBLE);
                                    spinKitView.setVisibility(View.GONE);
                                }
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

    private void setUpToolBar() {
        tvToolBarTitle.setText("Scratch Card");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void init() {
        rvScratchCard = findViewById(R.id.rv_scratch_card);
        scratchCardFormArrayList = new ArrayList<>();
        mToolbar = findViewById(R.id.toolbar);
        tvToolBarTitle = findViewById(R.id.tx_title);
        sharedPreferanceManage = new Sessionmanager(ActivityDisplayScratchCard.this);
        parentCategoryFormArrayList = new ArrayList<>();
        flAddScratchCard = findViewById(R.id.ivAddScratchcard);
        offerMenuFormArrayList = new ArrayList<>();

        spinKitView = findViewById(R.id.skLoading);
        llNodata = findViewById(R.id.llNoScratchData);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
