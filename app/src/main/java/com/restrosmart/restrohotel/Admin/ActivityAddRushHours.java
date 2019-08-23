package com.restrosmart.restrohotel.Admin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.mcsoft.timerangepickerdialog.RangeTimePickerDialog;
import com.restrosmart.restrohotel.Adapter.AdapterRVRushHours;
import com.restrosmart.restrohotel.Interfaces.ButtonListerner;
import com.restrosmart.restrohotel.Interfaces.PositionListener;
import com.restrosmart.restrohotel.Model.RushHourForm;
import com.restrosmart.restrohotel.R;
import java.util.ArrayList;
import java.util.Calendar;


public class ActivityAddRushHours extends AppCompatActivity implements RangeTimePickerDialog.ISelectedTime {

    private RecyclerView rvRushHours;
    private TextView tvToolBarTitle;
    private Toolbar mToolbar;
    private ArrayList<RushHourForm> rushHourFormArrayList;
    private LinearLayout linearLayout;
    private EditText etAmount, etMessage;
    private static TextView tvSelectDate;
    private static TextView tvSelectTime;
    private Button btnSave, btnUpdate;
    private ImageView ivBtnEdit, ivBtnCancel;
    private  int itemPosition;

    private View dialoglayout;
    private AlertDialog dialog;
    private static String format;
    private static boolean status;

    private String date[] = {"17 Jun", "18 Jun", "19 Jun", "20 Jun"};
    private String startTime[] = {"4pm-8pm", "1pm-5pm", "9am-11pm", "2pm-10pm"};

    private String messgae[] = {"Reserve to get buy 1, get 1 free on speciality medium/large pizza", "Reserve to get buy 1, get 1 free on speciality medium/large pizza",
            "Reserve to get buy 1, get 1 free on speciality medium/large pizza", "Reserve to get buy 1, get 1 free on speciality medium/large pizza"};

    private Button reset;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rush_hours);

        init();
        setUpToolbar();

        tvSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityAddRushHours.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                tvSelectDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        tvSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RangeTimePickerDialog dialog = new RangeTimePickerDialog();
                dialog.newInstance();
                dialog.setIs24HourView(true);
                dialog.setRadiusDialog(20);
                dialog.setTextTabStart("Start");
                dialog.setTextTabEnd("End");
                dialog.setTextBtnPositive("Accept");
                dialog.setTextBtnNegative("Close");
                dialog.setValidateRange(false);
                dialog.setColorBackgroundHeader(R.color.colorPrimary);
                dialog.setColorBackgroundTimePickerHeader(R.color.colorPrimary);
                dialog.setColorTextButton(R.color.colorPrimaryDark);
                dialog.enableMinutes(true);
                dialog.setStartTabIcon(R.drawable.ic_access_time_black_24dp);
                dialog.setEndTabIcon(R.drawable.ic_timelapse_black_24dp);

                FragmentManager fragmentManager = getFragmentManager();
                dialog.show(fragmentManager, "");


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
        AdapterRVRushHours adapterRVRushHours = new AdapterRVRushHours(this, rushHourFormArrayList, new PositionListener() {
            @Override
            public void positionListern(int position) {
                itemPosition=position;
                etMessage.setFocusable(false);
                //  etAmount.setText(rushHourFormArrayList.get(position).getOffer());
                // tvOffer.setText(rushHourFormArrayList.get(position).getOffer());
                tvSelectDate.setText(rushHourFormArrayList.get(position).getDate());
                tvSelectTime.setText(rushHourFormArrayList.get(position).getTime());
                etMessage.setText(rushHourFormArrayList.get(position).getMessage());

                btnSave.setVisibility(View.GONE);
                ivBtnCancel.setVisibility(View.GONE);
                ivBtnEdit.setVisibility(View.VISIBLE);


            }
        }, new ButtonListerner() {
            @Override
            public void getEditListenerPosition(int position) {
                etMessage.setHint("Enter message");
                etMessage.setFocusableInTouchMode(true);
                etMessage.setFocusable(true);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etMessage, InputMethodManager.SHOW_IMPLICIT);


                tvSelectDate.setText("Select Date");
                tvSelectTime.setText("Select Time");
                ivBtnEdit.setVisibility(View.GONE);
                ivBtnCancel.setVisibility(View.VISIBLE);

                btnSave.setVisibility(View.VISIBLE);
                btnUpdate.setVisibility(View.GONE);

            }
        });

        rvRushHours.scrollToPosition(rushHourFormArrayList.size() - 1);
        rvRushHours.setAdapter(adapterRVRushHours);

        for (int i = 0; i < rushHourFormArrayList.size(); i++) {
            //   etAmount.setFocusable(false);
            //  etAmount.setText(rushHourFormArrayList.get(i).getOffer());
            //  tvOffer.setText(rushHourFormArrayList.get(i).getOffer());
            //  tvDate.setText(rushHourFormArrayList.get(i).getDate());
            btnSave.setVisibility(View.GONE);

        }

        ivBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etMessage.setFocusable(false);
                //  etAmount.setText(rushHourFormArrayList.get(position).getOffer());
                // tvOffer.setText(rushHourFormArrayList.get(position).getOffer());
                tvSelectDate.setText(rushHourFormArrayList.get(itemPosition).getDate());
                tvSelectTime.setText(rushHourFormArrayList.get(itemPosition).getTime());
                etMessage.setText(rushHourFormArrayList.get(itemPosition).getMessage());

                btnSave.setVisibility(View.GONE);
                ivBtnCancel.setVisibility(View.GONE);
                ivBtnEdit.setVisibility(View.VISIBLE);
                btnUpdate.setVisibility(View.GONE);

            }
        });

        ivBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < rushHourFormArrayList.size(); i++) {

                    //   etAmount.setFocusableInTouchMode(true);
                    // etAmount.setFocusable(true);

                   /* InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(etAmount, InputMethodManager.SHOW_IMPLICIT);
*/
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
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivBtnEdit.setVisibility(View.VISIBLE);
                ivBtnCancel.setVisibility(View.GONE);
                btnUpdate.setVisibility(View.GONE
                );
            }
        });

    }


    private void setUpToolbar() {
        tvToolBarTitle.setText("Rush Hours");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void init() {
        rvRushHours = findViewById(R.id.rv_rushhours);
        mToolbar = findViewById(R.id.toolbar);
        tvToolBarTitle = findViewById(R.id.tx_title);
        rushHourFormArrayList = new ArrayList<>();
        linearLayout = findViewById(R.id.linear);

        etAmount = findViewById(R.id.et_amount);
        tvSelectDate = findViewById(R.id.tv_date);
        tvSelectTime = findViewById(R.id.tv_time);
        btnSave = findViewById(R.id.btn_save);
        ivBtnEdit = findViewById(R.id.iv_btn_edit);
        ivBtnCancel = findViewById(R.id.iv_btn_cancel);
        btnUpdate = findViewById(R.id.btn_update);
        etMessage = findViewById(R.id.etv_enter_message);
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

        tvSelectTime.setText( hourStart + ":" + minuteStart +" - "+hourEnd + ":" + minuteEnd);
    }
}
