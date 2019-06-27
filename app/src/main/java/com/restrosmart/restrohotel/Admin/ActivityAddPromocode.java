package com.restrosmart.restrohotel.Admin;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.restrosmart.restrohotel.Adapter.AdapterDisplayFlavour;
import com.restrosmart.restrohotel.Adapter.AdapterRVPromocode;
import com.restrosmart.restrohotel.Interfaces.ButtonListerner;
import com.restrosmart.restrohotel.Interfaces.PositionListener;
import com.restrosmart.restrohotel.Model.PromoCodeForm;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ActivityAddPromocode extends AppCompatActivity {

    private RecyclerView rvPromocode;
    private TextView tvToolBarTitle;
    private Toolbar mToolbar;
    private ArrayList<PromoCodeForm> arrayListPromoCode;
    private LinearLayout linearLayout;
    private EditText etAmount;
    private TextView tvDate,tvOffer;
    private Button btnSave,btnUpdate;
    private ImageView ivBtnEdit;

    private  String date[]={"17 Jun","18 Jun","19 Jun","20 Jun"};
    private  String offer[]={"FLAT 10","FLAT 80","FLAT 20","FLAT 50"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_promocode);

        init();
        setUpToolbar();
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openRangePicker();
            }
        });

        for(int i=0;i<date.length; i++)
        {
            PromoCodeForm promoCodeForm=new PromoCodeForm();
            promoCodeForm.setDate(date[i]);
            promoCodeForm.setOffer(offer[i]);
            arrayListPromoCode.add(promoCodeForm);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rvPromocode.setHasFixedSize(true);
        rvPromocode.setLayoutManager(linearLayoutManager);
        AdapterRVPromocode adapterRVPromocode= new AdapterRVPromocode(this,arrayListPromoCode, new PositionListener() {
            @Override
            public void positionListern(int position) {
                etAmount.setFocusable(false);
                etAmount.setText(arrayListPromoCode.get(position).getOffer());
                tvOffer.setText(arrayListPromoCode.get(position).getOffer());
                tvDate.setText(arrayListPromoCode.get(position).getDate());
                btnSave.setVisibility(View.GONE);

            }
        }, new ButtonListerner() {
            @Override
            public void getEditListenerPosition(int position) {
                etAmount.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etAmount, InputMethodManager.SHOW_IMPLICIT);
                tvOffer.setText("");
                tvDate.setText("select Date");
                btnSave.setVisibility(View.VISIBLE);

            }
        }) ;

        rvPromocode.scrollToPosition(arrayListPromoCode.size() - 1);
        rvPromocode.setAdapter(adapterRVPromocode);

        for (int i=0;i<arrayListPromoCode.size();i++)
        {
            etAmount.setFocusable(false);
            etAmount.setText(arrayListPromoCode.get(i).getOffer());
            tvOffer.setText(arrayListPromoCode.get(i).getOffer());
            tvDate.setText(arrayListPromoCode.get(i).getDate());
            btnSave.setVisibility(View.GONE);

        }

        ivBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0;i<arrayListPromoCode.size();i++)
                {

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(etAmount, InputMethodManager.SHOW_IMPLICIT);

                    etAmount.setText(arrayListPromoCode.get(i).getOffer());
                    tvOffer.setText(arrayListPromoCode.get(i).getOffer());
                    tvDate.setText(arrayListPromoCode.get(i).getDate());
                    btnSave.setVisibility(View.GONE);
                    ivBtnEdit.setVisibility(View.GONE);
                    btnUpdate.setVisibility(View.VISIBLE);


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

    private void init() {
        rvPromocode=findViewById(R.id.rv_promocode);
        mToolbar=findViewById(R.id.toolbar);
        tvToolBarTitle=findViewById(R.id.tx_title);
        arrayListPromoCode=new ArrayList<>();
        linearLayout=findViewById(R.id.linear);

        etAmount=findViewById(R.id.et_amount);
        tvDate=findViewById(R.id.tv_date);
        tvOffer=findViewById(R.id.tv_offer);
        btnSave=findViewById(R.id.btn_save);
        ivBtnEdit=findViewById(R.id.iv_btn_edit);
        btnUpdate=findViewById(R.id.btn_update);
    }


}
