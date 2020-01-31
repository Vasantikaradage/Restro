package com.restrosmart.restrohotel.Admin;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.AdapterRVDailyOfferMenu;
import com.restrosmart.restrohotel.Adapter.RVDailyOfferAdapter;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.DailyOfferDisplayListerner;
import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Interfaces.PositionListener;
import com.restrosmart.restrohotel.Model.DailyOfferForm;
import com.restrosmart.restrohotel.Model.OfferMenuForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;
import com.squareup.picasso.Picasso;

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

import static com.restrosmart.restrohotel.ConstantVariables.ADD_DAILY_OFFER;
import static com.restrosmart.restrohotel.ConstantVariables.DELETE_PROMOCODE;
import static com.restrosmart.restrohotel.ConstantVariables.DISPLAY_DAILY_OFFER;
import static com.restrosmart.restrohotel.ConstantVariables.EDIT_DAILY_OFFER;
import static com.restrosmart.restrohotel.ConstantVariables.IMAGE_RESULT_OK;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;


public class ActivityDisplayDailyOffer extends AppCompatActivity {
    private int offerTypeId, hotelId;
    private Sessionmanager mSessionManager;
    private ArrayList<DailyOfferForm> dailyOfferFormArrayList;
    private ArrayList<OfferMenuForm> offerMenuFormArrayList, offerSubMenuArrayList;

    private RecyclerView rvDailyOffer;
    private FrameLayout flAddDailyOffer;

    private View dialoglayout;
    private BottomSheetDialog dialog;
    private EditText etPrice, etDescription, etOfferName, etFromDate, etToDate, etBuyCnt, etGetCnt;
    private TextView tvTitleAdd, tvToolBarTitle;
    private Button btnSave, btnUpdate, btnCancel;
    private ImageView flImage;
    private Toolbar mToolbar;
    private Calendar mcurrentDate;
    private String fromDate, toDate, imageName, mFinalImageName, getStartDate, seletedStartDate, seletedEndDate;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private RadioGroup radioGroup;
    private RadioButton radioBtnRs, radioBtnPerc, radioBtnBuyGet;
    private int statusType, itemPosition;
    private LinearLayout llBuy, llGet;
    private TextView tvAmt;
    private SpinKitView skLoading;
    private LinearLayout llNoData;
    private int offerId, editItemPosition;
    private String fromDateOut, toDateOut;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_daily_offer);

        init();
        setUpToolbar();

        Intent intent = getIntent();
        offerTypeId = (intent.getIntExtra("offerTypeId", 0));


        HashMap<String, String> stringHashMap = mSessionManager.getHotelDetails();
        hotelId = Integer.parseInt(stringHashMap.get(HOTEL_ID));

        getDailyOfferInfo();
        flAddDailyOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offerId = 0;
                AddEditOffer();

            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void AddEditOffer() {
        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        dialoglayout = li.inflate(R.layout.bottomsheet_add_dailyoffer, null);
        dialog = new BottomSheetDialog(ActivityDisplayDailyOffer.this);
        dialog.setContentView(dialoglayout);

        tvTitleAdd = dialoglayout.findViewById(R.id.tv_add_dailyoffer);
        tvTitleAdd.setVisibility(View.VISIBLE);
        etPrice = dialoglayout.findViewById(R.id.et_price);
        etDescription = dialoglayout.findViewById(R.id.et_description);
        etOfferName = dialoglayout.findViewById(R.id.et_offer_name);
        etFromDate = dialoglayout.findViewById(R.id.et_from_date);
        etToDate = dialoglayout.findViewById(R.id.et_to_date);
        flImage = dialoglayout.findViewById(R.id.image1);
        etBuyCnt = dialoglayout.findViewById(R.id.et_buy_cnt);
        etGetCnt = dialoglayout.findViewById(R.id.et_get_cnt);
        radioGroup = dialoglayout.findViewById(R.id.radio_group);
        radioBtnRs = dialoglayout.findViewById(R.id.btn_radio_rupe);
        radioBtnPerc = dialoglayout.findViewById(R.id.btn_radio_percent);
        radioBtnBuyGet = dialoglayout.findViewById(R.id.btn_radio_str);
        tvAmt = dialoglayout.findViewById(R.id.tv_amt);

        btnSave = dialoglayout.findViewById(R.id.btnSave);
        btnCancel = dialoglayout.findViewById(R.id.btnCancel);
        btnUpdate = dialoglayout.findViewById(R.id.btnUpdate);
        llBuy = dialoglayout.findViewById(R.id.linear_layout_buy);
        llGet = dialoglayout.findViewById(R.id.linear_layout_get);


        statusType = 1;

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn_radio_rupe:
                        statusType = 1;
                        etPrice.setVisibility(View.VISIBLE);
                        llBuy.setVisibility(View.GONE);
                        llGet.setVisibility(View.GONE);
                        tvAmt.setVisibility(View.VISIBLE);
                        tvAmt.setVisibility(View.VISIBLE);
                        break;

                    case R.id.btn_radio_percent:
                        statusType = 2;
                        etPrice.setVisibility(View.VISIBLE);
                        llBuy.setVisibility(View.GONE);
                        llGet.setVisibility(View.GONE);
                        tvAmt.setVisibility(View.VISIBLE);
                        tvAmt.setVisibility(View.VISIBLE);
                        break;

                    case R.id.btn_radio_str:
                        statusType = 3;
                        etPrice.setVisibility(View.GONE);
                        tvAmt.setVisibility(View.GONE);
                        llBuy.setVisibility(View.VISIBLE);
                        tvAmt.setVisibility(View.GONE);
                        llGet.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });


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
                    DatePickerDialog mDatePicker1 = new DatePickerDialog(ActivityDisplayDailyOffer.this, new DatePickerDialog.OnDateSetListener() {
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
                                    Toast.makeText(ActivityDisplayDailyOffer.this, R.string.select_end_greater_start_date, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ActivityDisplayDailyOffer.this, R.string.select_first_start_date, Toast.LENGTH_SHORT).show();
                }
                // mDatePicker.show();
            }
        });
        flImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityDisplayDailyOffer.this, ActivityBannerImage.class);
                startActivityForResult(intent, IMAGE_RESULT_OK);
            }
        });

        if (offerId != 0) {
            btnSave.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            tvTitleAdd.setText((R.string.edit_daily_offer));

            etOfferName.setText(dailyOfferFormArrayList.get(editItemPosition).getOfferName());
            etDescription.setText(dailyOfferFormArrayList.get(editItemPosition).getDescription());
            etPrice.setText(dailyOfferFormArrayList.get(editItemPosition).getOfferPrice());
            etBuyCnt.setText(String.valueOf(dailyOfferFormArrayList.get(editItemPosition).getBuyCnt()));
            etGetCnt.setText(String.valueOf(dailyOfferFormArrayList.get(editItemPosition).getGetCnt()));

            int dailyState = dailyOfferFormArrayList.get(editItemPosition).getOfferPriceStatus();
            if (dailyState == 1) {
                statusType = 1;
                radioBtnRs.setChecked(true);
                etPrice.setVisibility(View.VISIBLE);
                llBuy.setVisibility(View.GONE);
                llGet.setVisibility(View.GONE);

            } else if (dailyState == 2) {
                statusType = 2;
                radioBtnPerc.setChecked(true);
                etPrice.setVisibility(View.VISIBLE);
                llBuy.setVisibility(View.GONE);
                llGet.setVisibility(View.GONE);

            } else {
                statusType = 3;
                radioBtnBuyGet.setChecked(true);
                etPrice.setVisibility(View.GONE);
                llGet.setVisibility(View.VISIBLE);
                llBuy.setVisibility(View.VISIBLE);
            }

            DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
            DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            String mFromDate = dailyOfferFormArrayList.get(editItemPosition).getFromDate();
            String mToDate = dailyOfferFormArrayList.get(editItemPosition).getToDate();
            Date dateFrom = null;
            Date dateTo = null;
            try {
                dateFrom = inputFormat.parse(mFromDate);
                dateTo = inputFormat.parse(mToDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            fromDateOut = outputFormat.format(dateFrom) + " " + dailyOfferFormArrayList.get(editItemPosition).getFromTime();
            toDateOut = outputFormat.format(dateTo) + " " + dailyOfferFormArrayList.get(editItemPosition).getToTime();

            etFromDate.setText(fromDateOut);
            etToDate.setText(toDateOut);

            Picasso.with(dialoglayout.getContext())
                    .load(dailyOfferFormArrayList.get(editItemPosition).getOfferImg())
                    .resize(500, 500)
                    .into(flImage);

            mFinalImageName = dailyOfferFormArrayList.get(editItemPosition).getOfferImg().substring(dailyOfferFormArrayList.get(editItemPosition).getOfferImg().lastIndexOf("/") + 1);
            ;
            flImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ActivityDisplayDailyOffer.this, ActivityBannerImage.class);
                    startActivityForResult(intent, IMAGE_RESULT_OK);

                }
            });


            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imageName != null) {
                        mFinalImageName = imageName.substring(imageName.lastIndexOf("/") + 1);
                    }

                    if (isValidData()) {
                        skLoading.setVisibility(View.VISIBLE);
                        initRetrofitCallback();
                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        mRetrofitService = new RetrofitService(mResultCallBack, dialoglayout.getContext());
                        mRetrofitService.retrofitData(EDIT_DAILY_OFFER, service.editDailyOffer(offerId,
                                etPrice.getText().toString(),
                                etOfferName.getText().toString(),
                                mFinalImageName,
                                etDescription.getText().toString(),
                                etFromDate.getText().toString(),
                                etToDate.getText().toString(),
                                hotelId,
                                statusType,
                                (etBuyCnt.getText().toString()),
                                (etGetCnt.getText().toString())));

                        editItemPosition = 0;
                        offerId = 0;
                    }

                }
            });


        } else {
            tvTitleAdd.setText(R.string.add_daily_offer);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imageName != null) {
                        mFinalImageName = imageName.substring(imageName.lastIndexOf("/") + 1);
                    } else {
                        Toast.makeText(ActivityDisplayDailyOffer.this, "please select image", Toast.LENGTH_SHORT).show();
                    }

                    if (isValidData()) {
                        skLoading.setVisibility(View.VISIBLE);
                        initRetrofitCallback();
                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        mRetrofitService = new RetrofitService(mResultCallBack, dialoglayout.getContext());
                        mRetrofitService.retrofitData(ADD_DAILY_OFFER, service.addDailyOffer(offerTypeId, etPrice.getText().toString(),
                                etOfferName.getText().toString(),
                                mFinalImageName,
                                etDescription.getText().toString(),
                                etFromDate.getText().toString(),
                                etToDate.getText().toString(), hotelId, statusType,
                                (etBuyCnt.getText().toString()),
                                (etGetCnt.getText().toString())));

                        editItemPosition = 0;
                        offerId = 0;
                    }

                }
            });
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editItemPosition = 0;
                offerId = 0;
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private boolean isValidData() {
        if (etOfferName.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter  offer name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etDescription.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityDisplayDailyOffer.this, "Please enter Description..", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etFromDate.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityDisplayDailyOffer.this, "Please select from date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etToDate.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(ActivityDisplayDailyOffer.this, "Please select to date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mFinalImageName == null) {
            Toast.makeText(ActivityDisplayDailyOffer.this, "Please select image", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (statusType == 1 || statusType == 2) {
            if (etPrice.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(ActivityDisplayDailyOffer.this, "Please enter amount", Toast.LENGTH_SHORT).show();
                return false;
            } else if (etPrice.getText().toString().equals("0")) {
                Toast.makeText(ActivityDisplayDailyOffer.this, "Please enter amount", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            if ((etBuyCnt.getText().toString().equalsIgnoreCase("") || (etBuyCnt.getText().toString().equals("0")))) {
                Toast.makeText(ActivityDisplayDailyOffer.this, "Please enter buy count", Toast.LENGTH_SHORT).show();
                return false;
            } else if ((etGetCnt.getText().toString().equalsIgnoreCase("") || (etGetCnt.getText().toString().equals("0")))) {
                Toast.makeText(ActivityDisplayDailyOffer.this, "Please enter get count", Toast.LENGTH_SHORT).show();
                return false;
            }

        }
        return true;
    }


    private void getDailyOfferInfo() {
        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getBaseContext());
        mRetrofitService.retrofitData(DISPLAY_DAILY_OFFER, (service.displayDailyOffer(offerTypeId, hotelId)));
    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String strObject = jsonObject.toString();
                skLoading.setVisibility(View.GONE);

                switch (requestId) {
                    case DISPLAY_DAILY_OFFER:
                        try {
                            JSONObject object = new JSONObject(strObject);
                            int status = object.getInt("status");
                            if (status == 1) {
                                llNoData.setVisibility(View.GONE);
                                JSONArray jsonArray = object.getJSONArray("Data");
                                dailyOfferFormArrayList.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject dailyOfferObject = jsonArray.getJSONObject(i);
                                    DailyOfferForm dailyOfferForm = new DailyOfferForm();
                                    dailyOfferForm.setOfferName(dailyOfferObject.getString("Offer_Name"));
                                    //dailyOfferForm.setPrice(dailyOfferObject.getString("totalprice"));
                                    dailyOfferForm.setOfferPrice(dailyOfferObject.getString("Offer_Price"));
                                    dailyOfferForm.setDescription(dailyOfferObject.getString("Offer_Desp"));
                                    dailyOfferForm.setFromDate(dailyOfferObject.getString("FromDate"));
                                    dailyOfferForm.setToDate(dailyOfferObject.getString("ToDate"));
                                    dailyOfferForm.setFromTime(dailyOfferObject.getString("FromTime"));
                                    dailyOfferForm.setToTime(dailyOfferObject.getString("ToTime"));
                                    dailyOfferForm.setBuyCnt(dailyOfferObject.getInt("Buy_Count"));
                                    dailyOfferForm.setGetCnt(dailyOfferObject.getInt("Get_Count"));
                                    dailyOfferForm.setOfferId(dailyOfferObject.getInt("Offer_Id"));
                                    dailyOfferForm.setStatus(dailyOfferObject.getInt("Status"));
                                    dailyOfferForm.setOfferImg(dailyOfferObject.getString("Offer_Img"));
                                    dailyOfferForm.setOfferPriceStatus(dailyOfferObject.getInt("OfferPriceStatus"));

                                    //main Menu Arraylist
                                    if (dailyOfferObject.has("MainMenuList")) {
                                        JSONArray dailyOfferMainMenuArray = dailyOfferObject.getJSONArray("MainMenuList");
                                        offerMenuFormArrayList = new ArrayList<>();
                                        for (int j = 0; j < dailyOfferMainMenuArray.length(); j++) {
                                            JSONObject dailyOfferMenuObj = dailyOfferMainMenuArray.getJSONObject(j);
                                            OfferMenuForm offerMenuForm = new OfferMenuForm();
                                            offerMenuForm.setOffer_maintain_Id(dailyOfferMenuObj.getInt("Offer_maintain_Id"));
                                            offerMenuForm.setMenuId(dailyOfferMenuObj.getString("MenuId"));
                                            offerMenuForm.setMenu_Name(dailyOfferMenuObj.getString("MenuName"));
                                            // offerMenuForm.setMenu_Image_Name(dailyOfferMenuObj.getString("Menu_Img"));
                                            //offerMenuForm.setBuyGetId(dailyOfferMenuObj.getInt("Buy_Get"));
                                            offerMenuForm.setMenu_Ori_Price(dailyOfferMenuObj.getString("MenuPrice"));
                                            offerMenuFormArrayList.add(offerMenuForm);
                                        }
                                        dailyOfferForm.setOfferMenuFormArrayList(offerMenuFormArrayList);
                                    }

                                    //sub menu array list
                                    if (dailyOfferObject.has("SubMainMenuList")) {
                                        JSONArray dailyOfferSubMenuArray = dailyOfferObject.getJSONArray("SubMainMenuList");
                                        offerSubMenuArrayList = new ArrayList<>();
                                        for (int j = 0; j < dailyOfferSubMenuArray.length(); j++) {
                                            JSONObject dailyOfferMenuObj = dailyOfferSubMenuArray.getJSONObject(j);
                                            OfferMenuForm offerMenuForm = new OfferMenuForm();
                                            offerMenuForm.setOffer_maintain_Id(dailyOfferMenuObj.getInt("Offer_maintain_Id"));
                                            offerMenuForm.setMenuId(dailyOfferMenuObj.getString("MenuId"));
                                            offerMenuForm.setMenu_Name(dailyOfferMenuObj.getString("MenuName"));
                                            // offerMenuForm.setMenu_Image_Name(dailyOfferMenuObj.getString("Menu_Img"));
                                            //offerMenuForm.setBuyGetId(dailyOfferMenuObj.getInt("Buy_Get"));
                                            offerMenuForm.setMenu_Ori_Price(dailyOfferMenuObj.getString("MenuPrice"));
                                            offerSubMenuArrayList.add(offerMenuForm);
                                        }
                                        dailyOfferForm.setOfferSubMenuArrayList(offerSubMenuArrayList);
                                    }
                                    dailyOfferFormArrayList.add(dailyOfferForm);
                                }

                                if (dailyOfferFormArrayList.size() >= 0 && dailyOfferFormArrayList != null) {
                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(ActivityDisplayDailyOffer.this, 2);
                                    RVDailyOfferAdapter rvDailyOfferAdapter = new RVDailyOfferAdapter(ActivityDisplayDailyOffer.this, new EditListener() {
                                        @Override
                                        public void getEditListenerPosition(int position) {

                                            offerId = dailyOfferFormArrayList.get(position).getOfferId();
                                            editItemPosition = position;
                                            AddEditOffer();

                                        }
                                    }, dailyOfferFormArrayList, new PositionListener() {
                                        @Override
                                        public void positionListern(int position) {

                                            if ((dailyOfferFormArrayList.get(position).getBuyCnt() == 0) && (dailyOfferFormArrayList.get(position).getGetCnt() == 0)) {
                                                Intent intent = new Intent(ActivityDisplayDailyOffer.this, ActivitySelectMenu.class);
                                                intent.putExtra("offerTypeId", offerTypeId);
                                                intent.putExtra("offerId", dailyOfferFormArrayList.get(itemPosition).getOfferId());
                                                startActivity(intent);
                                            } else {
                                                itemPosition = position;

                                                Intent intent = new Intent(ActivityDisplayDailyOffer.this, ActivityDaliyBuyGetoffer.class);
                                                intent.putExtra("offerTypeId", offerTypeId);
                                                intent.putExtra("buyCnt", dailyOfferFormArrayList.get(itemPosition).getBuyCnt());
                                                intent.putExtra("getCnt", dailyOfferFormArrayList.get(itemPosition).getGetCnt());
                                                intent.putExtra("offerId", dailyOfferFormArrayList.get(itemPosition).getOfferId());
                                                startActivity(intent);
                                            }
                                        }
                                    }, new DeleteListener() {
                                        @Override
                                        public void getDeleteListenerPosition(int position) {
                                            initRetrofitCallback();
                                            ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                                            mRetrofitService = new RetrofitService(mResultCallBack, ActivityDisplayDailyOffer.this);
                                            mRetrofitService.retrofitData(DELETE_PROMOCODE, service.deleteOffer(hotelId, dailyOfferFormArrayList.get(position).getOfferId()));

                                        }
                                    }, new DailyOfferDisplayListerner() {
                                        @Override
                                        public void dailyOfferDisplay(ArrayList<OfferMenuForm> offerMenuForms, ArrayList<OfferMenuForm> offerSubMenuForms, int pos) {
                                            itemPosition = pos;
                                            LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                            dialoglayout = li.inflate(R.layout.bottom_dailyoffer_view, null);
                                            dialog = new BottomSheetDialog(ActivityDisplayDailyOffer.this);
                                            dialog.setContentView(dialoglayout);

                                            TextView tvofferName = dialoglayout.findViewById(R.id.tv_offer_name);
                                            TextView tvDate = dialoglayout.findViewById(R.id.tv_offer_date);
                                            TextView tvTime = dialoglayout.findViewById(R.id.tv_offer_time);
                                            TextView tvDesc = dialoglayout.findViewById(R.id.tv_offer_desc);
                                            RecyclerView rvMenuItemBuy = dialoglayout.findViewById(R.id.rv_menu_item_buy);
                                            TextView tvOfferPrice = dialoglayout.findViewById(R.id.tv_offer_price);
                                            TextView tvMenuPrice = dialoglayout.findViewById(R.id.tv_menu_price);
                                            LinearLayout linearLayoutMenu = dialoglayout.findViewById(R.id.menu_llayout);
                                            TextView tvMenuName = dialoglayout.findViewById(R.id.tv_menu);
                                            TextView tvMenuPriceSingle = dialoglayout.findViewById(R.id.tv_menu_amt);

                                            ImageButton btnCancel = dialoglayout.findViewById(R.id.btn_cancel);

                                            TextView tvTitle = dialoglayout.findViewById(R.id.tv_menu_title);
                                            LinearLayout linearLayout = dialoglayout.findViewById(R.id.linear_layout_menu);
                                            View viewline = dialoglayout.findViewById(R.id.view_line);
                                            ImageView bannerImageView = dialoglayout.findViewById(R.id.banner_image);
                                            RecyclerView rvMenuItemGet = dialoglayout.findViewById(R.id.rv_menu_item_get);

                                            TextView tvBuy = dialoglayout.findViewById(R.id.tv_buy);
                                            TextView tvGet = dialoglayout.findViewById(R.id.tv_get);

                                            tvofferName.setText(dailyOfferFormArrayList.get(itemPosition).getOfferName());
                                            tvDate.setText(dailyOfferFormArrayList.get(pos).getFromDate() + "-" + dailyOfferFormArrayList.get(pos).getToDate());
                                            tvTime.setText(dailyOfferFormArrayList.get(pos).getFromTime() + "-" + dailyOfferFormArrayList.get(pos).getToTime());
                                            tvDesc.setText(dailyOfferFormArrayList.get(pos).getDescription());

                                            Picasso.with(dialoglayout.getContext()).load(dailyOfferFormArrayList.get(itemPosition).getOfferImg()).resize(500, 500).into(bannerImageView);

                                            if (dailyOfferFormArrayList.get(pos).getStatus() == 0) {
                                                linearLayoutMenu.setVisibility(View.GONE);
                                                rvMenuItemBuy.setVisibility(View.GONE);
                                                tvBuy.setVisibility(View.GONE);
                                                tvGet.setVisibility(View.GONE);
                                                rvMenuItemGet.setVisibility(View.GONE);
                                                tvTitle.setVisibility(View.GONE);
                                                viewline.setVisibility(View.GONE);
                                            } else {
                                                linearLayoutMenu.setVisibility(View.VISIBLE);
                                                if ((dailyOfferFormArrayList.get(itemPosition).getBuyCnt() == 0) && (dailyOfferFormArrayList.get(itemPosition).getGetCnt() == 0)) {

                                                    tvMenuPrice.setText(dailyOfferFormArrayList.get(itemPosition).getPrice());
                                                    tvOfferPrice.setText(dailyOfferFormArrayList.get(pos).getOfferPrice());
                                                    linearLayoutMenu.setVisibility(View.VISIBLE);

                                                    for (int i = 0; i < offerMenuForms.size(); i++) {
                                                        tvMenuName.setText(offerMenuForms.get(i).getMenu_Name());
                                                        tvMenuPriceSingle.setText("\u20B9 " + (offerMenuForms.get(i).getMenu_Ori_Price()));
                                                        //Toast.makeText(ActivityDisplayDailyOffer.this, offerMenuForms.get(i).getMenu_Name(), Toast.LENGTH_SHORT).show();
                                                    }

                                                } else {
                                                    tvTitle.setVisibility(View.VISIBLE);
                                                    viewline.setVisibility(View.VISIBLE);
                                                    tvGet.setVisibility(View.VISIBLE);
                                                    rvMenuItemGet.setVisibility(View.VISIBLE);

                                                }
                                                    if (offerMenuForms.size() > 0 && offerMenuForms != null && offerSubMenuForms.size() > 0 && offerSubMenuForms != null) {

                                                        linearLayoutMenu.setVisibility(View.GONE);
                                                        if (offerMenuForms.size() > 0 && offerMenuForms != null) {
                                                            tvTitle.setVisibility(View.VISIBLE);
                                                            viewline.setVisibility(View.VISIBLE);
                                                            if (dailyOfferFormArrayList.get(pos).getBuyCnt() != 0) {
                                                                tvBuy.setVisibility(View.VISIBLE);
                                                            } else {
                                                                tvBuy.setVisibility(View.GONE);
                                                            }

                                                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityDisplayDailyOffer.this);
                                                            rvMenuItemBuy.setHasFixedSize(true);
                                                            rvMenuItemBuy.setLayoutManager(linearLayoutManager);
                                                            AdapterRVDailyOfferMenu adapterRVDailyOfferMenu = new AdapterRVDailyOfferMenu(getApplicationContext(), offerMenuForms);
                                                            rvMenuItemBuy.setAdapter(adapterRVDailyOfferMenu);
                                                        } else {
                                                            //tvTitle.setVisibility(View.GONE);
                                                            // viewline.setVisibility(View.GONE);
                                                            rvMenuItemBuy.setVisibility(View.GONE);
                                                            tvBuy.setVisibility(View.GONE);
                                                            tvGet.setVisibility(View.GONE);
                                                            rvMenuItemGet.setVisibility(View.GONE);
                                                        }

                                                        if (offerSubMenuForms.size() > 0 && offerSubMenuForms != null) {
                                                            tvTitle.setVisibility(View.VISIBLE);
                                                            viewline.setVisibility(View.VISIBLE);
                                                            tvGet.setVisibility(View.VISIBLE);
                                                            rvMenuItemGet.setVisibility(View.VISIBLE);

                                                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityDisplayDailyOffer.this);
                                                            rvMenuItemGet.setLayoutManager(linearLayoutManager);
                                                            AdapterRVDailyOfferMenu adapterRVDailyOfferMenu = new AdapterRVDailyOfferMenu(getApplicationContext(), offerSubMenuForms);
                                                            rvMenuItemGet.setAdapter(adapterRVDailyOfferMenu);

                                                        } else {
                                                            tvGet.setVisibility(View.GONE);
                                                            rvMenuItemGet.setVisibility(View.GONE);
                                                        }
                                                    } else {
                                                        rvMenuItemBuy.setVisibility(View.GONE);
                                                        tvBuy.setVisibility(View.GONE);
                                                        tvGet.setVisibility(View.GONE);
                                                        rvMenuItemGet.setVisibility(View.GONE);
                                                    }
                                                }


                                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialog.dismiss();
                                                }
                                            });
                                            dialog.show();
                                        }
                                    });
                                    rvDailyOffer.setLayoutManager(gridLayoutManager);
                                    rvDailyOffer.setHasFixedSize(true);
                                    rvDailyOffer.setAdapter(rvDailyOfferAdapter);

                                    if (dailyOfferFormArrayList.size() >= 6) {
                                        flAddDailyOffer.setVisibility(View.GONE);
                                    } else {
                                        flAddDailyOffer.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                llNoData.setVisibility(View.VISIBLE);
                                skLoading.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case DELETE_PROMOCODE:
                        try {
                            JSONObject object = new JSONObject(strObject);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityDisplayDailyOffer.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityDisplayDailyOffer.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                            skLoading.setVisibility(View.GONE);
                            getDailyOfferInfo();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case ADD_DAILY_OFFER:
                        try {
                            JSONObject object = new JSONObject(strObject);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityDisplayDailyOffer.this, object.getString("message"), Toast.LENGTH_LONG).show();
                                // finish();
                            } else {
                                Toast.makeText(ActivityDisplayDailyOffer.this, object.getString("message"), Toast.LENGTH_LONG).show();
                            }
                            skLoading.setVisibility(View.GONE);

                            dialog.dismiss();
                            imageName = null;
                            mFinalImageName = null;
                            getDailyOfferInfo();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case EDIT_DAILY_OFFER:
                        try {
                            JSONObject object = new JSONObject(strObject);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(ActivityDisplayDailyOffer.this, object.getString("message"), Toast.LENGTH_LONG).show();
                                // finish();
                            } else {
                                Toast.makeText(ActivityDisplayDailyOffer.this, object.getString("message"), Toast.LENGTH_LONG).show();
                            }
                            skLoading.setVisibility(View.GONE);
                            dialog.dismiss();
                            editItemPosition = 0;
                            offerId = 0;
                            getDailyOfferInfo();
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
                skLoading.setVisibility(View.GONE);
            }
        };
    }

    private void setUpToolbar() {
        tvToolBarTitle.setText("Daily Offer");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getDailyOfferInfo();
    }

    private void init() {
        mSessionManager = new Sessionmanager(ActivityDisplayDailyOffer.this);
        dailyOfferFormArrayList = new ArrayList<>();
        rvDailyOffer = findViewById(R.id.recycler_offer_item);
        flAddDailyOffer = findViewById(R.id.ivAddOffer);
        mToolbar = findViewById(R.id.toolbar);
        tvToolBarTitle = findViewById(R.id.tx_title);
        offerMenuFormArrayList = new ArrayList<>();
        skLoading = findViewById(R.id.skLoading);
        llNoData = findViewById(R.id.llNoDailyData);
        offerSubMenuArrayList = new ArrayList<>();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == IMAGE_RESULT_OK /*&& requestCode==IMAGE_RESULT_OK*/) {
            imageName = data.getStringExtra("image_name");
            Picasso.with(dialoglayout.getContext())
                    .load(imageName)
                    .resize(500, 500)
                    .into(flImage);
        }
    }

}
