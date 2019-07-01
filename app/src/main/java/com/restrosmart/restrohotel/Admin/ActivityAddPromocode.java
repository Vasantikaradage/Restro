package com.restrosmart.restrohotel.Admin;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.restrosmart.restrohotel.Adapter.AdapterDisplayFlavour;
import com.restrosmart.restrohotel.Adapter.AdapterRVPromocode;
import com.restrosmart.restrohotel.Interfaces.ButtonListerner;
import com.restrosmart.restrohotel.Interfaces.PositionListener;
import com.restrosmart.restrohotel.MainActivity;
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
    private View dialoglayout;
    private AlertDialog dialog;

    private  String date[]={"17 Jun","18 Jun","19 Jun","20 Jun"};
    private  String offer[]={"FLAT 10","FLAT 80","FLAT 20","FLAT 50"};

    private DateRangeCalendarView calendar;
    private  Button reset;

   // private RelativeLayout LayoutCalender;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_promocode);

        init();
        setUpToolbar();
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // LayoutCalender.setVisibility(View.VISIBLE);

                View view1 = getLayoutInflater().inflate(R.layout.mylayout, null);
              //  dialoglayout = view1.inflate(R.layout.mylayout, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAddPromocode.this);
                builder.setView(view1);
                AlertDialog alert = builder.create();
                alert.show();

              /*  LayoutInflater factory = LayoutInflater.from(getApplicationContext());
                final View deleteDialogView = factory.inflate(R.layout.mylayout, null);
                final AlertDialog deleteDia
                log = new AlertDialog.Builder(getBaseContext()).create();
                deleteDialog.setView(deleteDialogView);



                deleteDialog.show();*/
                calendar = view1.findViewById(R.id.calendar);
                reset=view1.findViewById(R.id.btnReset);

                calendar.resetAllSelectedViews();

               // Typeface font = Typeface.createFromAsset(getAssets(), "fonts/terminal.ttf");
               // Typeface typeface = Typeface.createFromAsset(getAssets(), "JosefinSans-Regular.ttf");
    //        Typeface typeface = Typeface.createFromAsset(getAssets(), "LobsterTwo-Regular.ttf");
              //  calendar.setFonts(font);

                calendar.setCalendarListener(new DateRangeCalendarView.CalendarListener() {
                    @Override
                    public void onFirstDateSelected(Calendar startDate) {
                        Toast.makeText(ActivityAddPromocode.this, "Start Date: " + startDate.getTime().toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDateRangeSelected(Calendar startDate, Calendar endDate) {
                        Toast.makeText(ActivityAddPromocode.this, "Start Date: " + startDate.getTime().toString() + " End date: " + endDate.getTime().toString(), Toast.LENGTH_SHORT).show();
                    }

                });

                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calendar.resetAllSelectedViews();
                    }
                });


//        calendar.setNavLeftImage(ContextCompat.getDrawable(this,R.drawable.ic_left));
//        calendar.setNavRightImage(ContextCompat.getDrawable(this,R.drawable.ic_right));

              /*  Calendar now = Calendar.getInstance();
                now.add(Calendar.MONTH, -2);
                Calendar later = (Calendar) now.clone();
                later.add(Calendar.MONTH, 5);

                calendar.setVisibleMonthRange(now,later);*/

                Calendar startSelectionDate = Calendar.getInstance();
                startSelectionDate.add(Calendar.MONTH, -1);
                Calendar endSelectionDate = (Calendar) startSelectionDate.clone();
                endSelectionDate.add(Calendar.DATE, 40);

                calendar.setSelectedDateRange(startSelectionDate, endSelectionDate);

                Calendar current = Calendar.getInstance();
                calendar.setCurrentMonth(current);
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
                etAmount.setFocusableInTouchMode(true);
                etAmount.setFocusable(true);
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

                    etAmount.setFocusableInTouchMode(true);
                    etAmount.setFocusable(true);

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
       // LayoutCalender=findViewById(R.id.relative_layout);
    }


}
