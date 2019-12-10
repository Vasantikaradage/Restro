package com.restrosmart.restrohotel.Admin;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Adapter.AdapterRVPromocode;

import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.DeleteListener;
import com.restrosmart.restrohotel.Interfaces.EditListener;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Interfaces.PositionListener;
import com.restrosmart.restrohotel.Model.PromoCodeForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Response;

import static android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL;
import static com.restrosmart.restrohotel.ConstantVariables.ADD_PROMOCODE;
import static com.restrosmart.restrohotel.ConstantVariables.DELETE_PROMOCODE;
import static com.restrosmart.restrohotel.ConstantVariables.EDIT_PROMOCODE;
import static com.restrosmart.restrohotel.ConstantVariables.SAVE_CATEGORY;

public class ActivityDisplayPromocode extends AppCompatActivity {

    private RecyclerView rvPromocode;
    private TextView tvToolBarTitle, tvDetails;
    private Toolbar mToolbar;
    private ArrayList<PromoCodeForm> arrayListPromoCode;
    private EditText etAmount, etOffer;
    private TextView tvDate;
    private Button btnSave, btnUpdate, btnCancel;
    private ImageView ivBtnEdit, ivBtnancel;
    private String date[] = {"17-19 Jun", "18-25 Jun", "19-21 Jun", "20-28 Jun"};
    private String offer[] = {"FLAT", "FLAT", "FLAT", "FLAT"};
    private String offerValue[] = {"10", "20", "30", "40"};
    private int itemPosition, editedItemPosition;
    private FrameLayout flAddPromoCode;

    private View dialoglayout;
    private BottomSheetDialog dialog;
    private EditText etPrice, etDescription, etPromoCode, etFromDate, etToDate;
    private TextView tvTitleAdd, tvTitleUpdate;
    final Calendar myCalendar = Calendar.getInstance();
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private AlertDialog alertDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_promocode);

        init();
        setUpToolbar();

        flAddPromoCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEditOffer();

            }
        });
      /*  tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SlyCalendarDialog()
                        .setSingle(false)
                        .setFirstMonday(false)
                        .setCallback(ActivityDisplayPromocode.this)
                        .show(getSupportFragmentManager(), "TAG_SLYCALENDAR");
                Calendar startSelectionDate = Calendar.getInstance();
                startSelectionDate.add(Calendar.MONTH, -1);
                Calendar endSelectionDate = (Calendar) startSelectionDate.clone();
                endSelectionDate.add(Calendar.DATE, 40);
            }
        });*/


        for (int i = 0; i < date.length; i++) {
            PromoCodeForm promoCodeForm = new PromoCodeForm();
            promoCodeForm.setDate(date[i]);
            promoCodeForm.setOffer(offer[i]);
            promoCodeForm.setOfferValue(offerValue[i]);
            arrayListPromoCode.add(promoCodeForm);
        }

        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, VERTICAL);
        rvPromocode.setHasFixedSize(true);
        rvPromocode.setLayoutManager(staggeredGridLayoutManager);
        AdapterRVPromocode adapterRVPromocode = new AdapterRVPromocode(this, arrayListPromoCode, new EditListener() {
            @Override
            public void getEditListenerPosition(int position) {
                editedItemPosition = position;
                AddEditOffer();

            }
        }, new DeleteListener() {
            @Override
            public void getDeleteListenerPosition(int position) {
                initRetrofitCall();
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack, dialoglayout.getContext());
                mRetrofitService.retrofitData(DELETE_PROMOCODE, service.deletePromoCode(etPrice.getText().toString(),
                        etPromoCode.getText().toString()));

            }
        }, new PositionListener() {
            @Override
            public void positionListern(int position) {
                LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialoglayout = li.inflate(R.layout.dialog_promocode_view, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDisplayPromocode.this);
                builder.setView(dialoglayout);
                alertDialog = builder.create();

                TextView price = dialoglayout.findViewById(R.id.tv_price);

                TextView date = dialoglayout.findViewById(R.id.tv_date);
                TextView promoCodeName = dialoglayout.findViewById(R.id.tv_promocode_name);
                TextView promoCodeDesription = dialoglayout.findViewById(R.id.tv_promo_description);
                //  ImageView btnClose=dialoglayout.findViewById(R.id.ivCloseDialog);


                String strPrice = arrayListPromoCode.get(position).getOfferValue();

                price.setText(strPrice);
                date.setText(arrayListPromoCode.get(position).getDate());
                promoCodeName.setText(arrayListPromoCode.get(position).getOffer());
                promoCodeDesription.setText("Customer need to apply FLAT  code to avail 30% off. offer is valid till JUN 15 2019");


                alertDialog.show();


            }
        }); /*new PositionListener() {
            @Override
            public void positionListern(int position) {
               *//* itemPosition = position;
                etAmount.setFocusable(false);
                etOffer.setFocusable(false);
                etAmount.setText(arrayListPromoCode.get(position).getOfferValue());
                etOffer.setText(arrayListPromoCode.get(position).getOffer());
                tvDate.setText(arrayListPromoCode.get(position).getDate());

                ivBtnEdit.setVisibility(View.VISIBLE);
                tvDetails.setVisibility(View.VISIBLE);

                btnUpdate.setVisibility(View.GONE);
                ivBtnancel.setVisibility(View.GONE);
                btnSave.setVisibility(View.GONE);
*//*
            }
        }, new ButtonListerner() {
            @Override
            public void getEditListenerPosition(int position) {
              *//*  etAmount.setText("");
                etAmount.setFocusableInTouchMode(true);
                etAmount.setFocusable(true);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etAmount, InputMethodManager.SHOW_IMPLICIT);

                etOffer.setText("");
                etOffer.setFocusableInTouchMode(true);
                etOffer.setFocusable(true);
                InputMethodManager imm1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm1.showSoftInput(etOffer, InputMethodManager.SHOW_IMPLICIT);
                tvDate.setText("select Date");
                btnSave.setVisibility(View.VISIBLE);
                btnUpdate.setVisibility(View.GONE);

                ivBtnancel.setVisibility(View.VISIBLE);
                ivBtnEdit.setVisibility(View.GONE);
                tvDetails.setVisibility(View.GONE);*//*
         */
        //}
        // });
        rvPromocode.setAdapter(adapterRVPromocode);

       /* rvPromocode.scrollToPosition(arrayListPromoCode.size() - 1);
        rvPromocode.setAdapter(adapterRVPromocode);
        for (int i = 0; i < arrayListPromoCode.size(); i++) {
          //  etAmount.setFocusable(false);
           // etOffer.setFocusable(false);

            etAmount.setText(arrayListPromoCode.get(i).getOfferValue());
            etOffer.setText(arrayListPromoCode.get(i).getOffer());
            tvDate.setText(arrayListPromoCode.get(i).getDate());
            btnSave.setVisibility(View.GONE);
            tvDetails.setVisibility(View.VISIBLE);

        }*/

      /*  ivBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < arrayListPromoCode.size(); i++) {

                    etAmount.setFocusableInTouchMode(true);
                    etAmount.setFocusable(true);

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(etAmount, InputMethodManager.SHOW_IMPLICIT);

                    etAmount.setText(arrayListPromoCode.get(i).getOfferValue());
                    etOffer.setText(arrayListPromoCode.get(i).getOffer());
                    tvDate.setText(arrayListPromoCode.get(i).getDate());
                    btnSave.setVisibility(View.GONE);
                    ivBtnEdit.setVisibility(View.GONE);
                    tvDetails.setVisibility(View.GONE);

                    btnUpdate.setVisibility(View.VISIBLE);
                    ivBtnancel.setVisibility(View.VISIBLE);

                    ivBtnancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            etAmount.setFocusable(false);
                            etOffer.setFocusable(false);
                            etAmount.setText(arrayListPromoCode.get(itemPosition).getOfferValue());
                            etOffer.setText(arrayListPromoCode.get(itemPosition).getOffer());
                            tvDate.setText(arrayListPromoCode.get(itemPosition).getDate());


                            btnUpdate.setVisibility(View.GONE);
                            ivBtnancel.setVisibility(View.GONE);

                            ivBtnEdit.setVisibility(View.VISIBLE);
                            tvDetails.setVisibility(View.VISIBLE);

                        }
                    });
                }
            }
        });
*/
    }

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


        btnSave = dialoglayout.findViewById(R.id.btnSave);
        btnCancel = dialoglayout.findViewById(R.id.btnCancel);
        btnUpdate = dialoglayout.findViewById(R.id.btnUpdate);

        etFromDate.setOnClickListener(new View.OnClickListener() {
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
                        etFromDate.setText(day + "/" + mon + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });


        etToDate.setOnClickListener(new View.OnClickListener() {
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
                        etToDate.setText(day + "/" + mon + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();

            }
        });


        if (editedItemPosition != 0) {
            tvTitleAdd.setText("Edit Promocode");
            btnSave.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            etPrice.setText(arrayListPromoCode.get(editedItemPosition).getOffer());
            etPromoCode.setText(arrayListPromoCode.get(editedItemPosition).getOfferValue());
            etFromDate.setText("");
            etToDate.setText("");

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initRetrofitCall();
                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                    mRetrofitService = new RetrofitService(mResultCallBack, dialoglayout.getContext());
                    mRetrofitService.retrofitData(EDIT_PROMOCODE, service.editPromoCode("", etPrice.getText().toString(),
                            etPromoCode.getText().toString(),
                            etDescription.getText().toString(),
                            etFromDate.getText().toString(),
                            etToDate.getText().toString()));


                }
            });


        } else {
            btnSave.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);
            tvTitleAdd.setText("Add Promocode");

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initRetrofitCall();
                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                    mRetrofitService = new RetrofitService(mResultCallBack, dialoglayout.getContext());
                    mRetrofitService.retrofitData(ADD_PROMOCODE, service.addPromoCode(etPrice.getText().toString(),
                            etPromoCode.getText().toString(),
                            etDescription.getText().toString(),
                            etFromDate.getText().toString(),
                            etToDate.getText().toString()));


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

    private void showDatePicker() {

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

    /* @Override
     public void onCancelled() {

     }

     @Override
     public void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes) {
         if (firstDate != null) {
             if (secondDate == null) {
                 firstDate.set(Calendar.HOUR_OF_DAY, hours);
                 firstDate.set(Calendar.MINUTE, minutes);
                 Toast.makeText(
                         this,
                         new SimpleDateFormat(getString(R.string.timeFormat), Locale.getDefault()).format(firstDate.getTime()),
                         Toast.LENGTH_LONG

                 ).show();
             } else {
                 Toast.makeText(
                         this,
                         getString(
                                 R.string.period,
                                 new SimpleDateFormat(getString(R.string.dateFormat), Locale.getDefault()).format(firstDate.getTime()),
                                 new SimpleDateFormat(getString(R.string.timeFormat), Locale.getDefault()).format(secondDate.getTime())
                         ),
                         Toast.LENGTH_LONG

                 ).show();
             }
         }
     }
 */
    private void init() {
        rvPromocode = findViewById(R.id.rv_promocode);
        mToolbar = findViewById(R.id.toolbar);
        tvToolBarTitle = findViewById(R.id.tx_title);
        arrayListPromoCode = new ArrayList<>();
        // etAmount = findViewById(R.id.et_amount);
        tvDate = findViewById(R.id.tv_date);
        //  etOffer = findViewById(R.id.etv_offer);
        btnSave = findViewById(R.id.btn_save);
        // ivBtnEdit = findViewById(R.id.iv_btn_edit);
        btnUpdate = findViewById(R.id.btn_update);
        // tvDetails = findViewById(R.id.tv_details);
        // ivBtnancel = findViewById(R.id.iv_btn_cancel);
        flAddPromoCode = findViewById(R.id.ivAddPromoCode);

    }


}
