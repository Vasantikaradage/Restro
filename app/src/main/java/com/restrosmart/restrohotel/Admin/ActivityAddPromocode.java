package com.restrosmart.restrohotel.Admin;


import android.content.Context;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.restrosmart.restrohotel.Adapter.AdapterRVPromocode;
import com.restrosmart.restrohotel.Interfaces.ButtonListerner;
import com.restrosmart.restrohotel.Interfaces.PositionListener;

import com.restrosmart.restrohotel.Model.PromoCodeForm;
import com.restrosmart.restrohotel.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Locale;

public class ActivityAddPromocode extends AppCompatActivity implements SlyCalendarDialog.Callback {

    private RecyclerView rvPromocode;
    private TextView tvToolBarTitle, tvDetails;
    private Toolbar mToolbar;
    private ArrayList<PromoCodeForm> arrayListPromoCode;
    private EditText etAmount, etOffer;
    private TextView tvDate;
    private Button btnSave, btnUpdate;
    private ImageView ivBtnEdit, ivBtnancel;
    private String date[] = {"17-19 Jun", "18-25 Jun", "19-21 Jun", "20-28 Jun"};
    private String offer[] = {"FLAT", "FLAT", "FLAT", "FLAT"};
    private String offerValue[] = {"10", "20", "30", "40"};
    private int itemPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_promocode);

        init();
        setUpToolbar();
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SlyCalendarDialog()
                        .setSingle(false)
                        .setFirstMonday(false)
                        .setCallback(ActivityAddPromocode.this)
                        .show(getSupportFragmentManager(), "TAG_SLYCALENDAR");
                Calendar startSelectionDate = Calendar.getInstance();
                startSelectionDate.add(Calendar.MONTH, -1);
                Calendar endSelectionDate = (Calendar) startSelectionDate.clone();
                endSelectionDate.add(Calendar.DATE, 40);
            }
        });


        for (int i = 0; i < date.length; i++) {
            PromoCodeForm promoCodeForm = new PromoCodeForm();
            promoCodeForm.setDate(date[i]);
            promoCodeForm.setOffer(offer[i]);
            promoCodeForm.setOfferValue(offerValue[i]);
            arrayListPromoCode.add(promoCodeForm);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rvPromocode.setHasFixedSize(true);
        rvPromocode.setLayoutManager(linearLayoutManager);
        AdapterRVPromocode adapterRVPromocode = new AdapterRVPromocode(this, arrayListPromoCode, new PositionListener() {
            @Override
            public void positionListern(int position) {
                itemPosition = position;
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

            }
        }, new ButtonListerner() {
            @Override
            public void getEditListenerPosition(int position) {
                etAmount.setText("");
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
                tvDetails.setVisibility(View.GONE);

            }
        });

        rvPromocode.scrollToPosition(arrayListPromoCode.size() - 1);
        rvPromocode.setAdapter(adapterRVPromocode);

        for (int i = 0; i < arrayListPromoCode.size(); i++) {
            etAmount.setFocusable(false);
            etOffer.setFocusable(false);

            etAmount.setText(arrayListPromoCode.get(i).getOfferValue());
            etOffer.setText(arrayListPromoCode.get(i).getOffer());
            tvDate.setText(arrayListPromoCode.get(i).getDate());
            btnSave.setVisibility(View.GONE);
            tvDetails.setVisibility(View.VISIBLE);

        }

        ivBtnEdit.setOnClickListener(new View.OnClickListener() {
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

    @Override
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

    private void init() {
        rvPromocode = findViewById(R.id.rv_promocode);
        mToolbar = findViewById(R.id.toolbar);
        tvToolBarTitle = findViewById(R.id.tx_title);
        arrayListPromoCode = new ArrayList<>();
        etAmount = findViewById(R.id.et_amount);
        tvDate = findViewById(R.id.tv_date);
        etOffer = findViewById(R.id.etv_offer);
        btnSave = findViewById(R.id.btn_save);
        ivBtnEdit = findViewById(R.id.iv_btn_edit);
        btnUpdate = findViewById(R.id.btn_update);
        tvDetails = findViewById(R.id.tv_details);
        ivBtnancel = findViewById(R.id.iv_btn_cancel);

    }
}
