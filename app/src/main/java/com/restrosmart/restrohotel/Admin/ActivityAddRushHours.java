package com.restrosmart.restrohotel.Admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;


import com.restrosmart.restrohotel.Interfaces.ButtonListerner;
import com.restrosmart.restrohotel.Interfaces.PositionListener;
import com.restrosmart.restrohotel.Model.RushHourForm;
import com.restrosmart.restrohotel.R;


import java.util.ArrayList;
import java.util.Calendar;


public class ActivityAddRushHours extends AppCompatActivity {

    private RecyclerView rvRushHours;
    private TextView tvToolBarTitle;
    private Toolbar mToolbar;
    private ArrayList<RushHourForm> rushHourFormArrayList;
    private LinearLayout linearLayout;
    private EditText etAmount;
    private static TextView tvStratTime;
    private static TextView tvEndTime;
    private Button btnSave, btnUpdate;
    private ImageView ivBtnEdit;
    private View dialoglayout;
    private AlertDialog dialog;
    private static String format;
    private static boolean status;

    private String date[] = {"17 Jun", "18 Jun", "19 Jun", "20 Jun"};
    private String startTime[] = {"4pm", "1pm", "9am", "2pm"};
    private String endTime[] = {"8pm", "5pm", "11am", "10pm"};

    private Button reset;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rush_hours);


        init();
        setUpToolbar();
        tvStratTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = true;
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        tvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = false;
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");

            }
        });


        for (int i = 0; i < date.length; i++) {
            RushHourForm rushHourForm = new RushHourForm();
            rushHourForm.setDate(date[i]);
            rushHourForm.setStartTime(startTime[i]);
            rushHourForm.setEndTime(endTime[i]);
            rushHourFormArrayList.add(rushHourForm);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rvRushHours.setHasFixedSize(true);
        rvRushHours.setLayoutManager(linearLayoutManager);
        AdapterRVRushHours adapterRVRushHours = new AdapterRVRushHours(this, rushHourFormArrayList, new PositionListener() {
            @Override
            public void positionListern(int position) {
                //etAmount.setFocusable(false);
                //  etAmount.setText(rushHourFormArrayList.get(position).getOffer());
                // tvOffer.setText(rushHourFormArrayList.get(position).getOffer());
                tvStratTime.setText(rushHourFormArrayList.get(position).getStartTime());
                tvEndTime.setText(rushHourFormArrayList.get(position).getEndTime());
                btnSave.setVisibility(View.GONE);

            }
        }, new ButtonListerner() {
            @Override
            public void getEditListenerPosition(int position) {
                //  etAmount.setText("");
                //  etAmount.setFocusableInTouchMode(true);
                //  etAmount.setFocusable(true);
                /*InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etAmount, InputMethodManager.SHOW_IMPLICIT);
                tvOffer.setText("");
                tvDate.setText("select Date");*/
                btnSave.setVisibility(View.VISIBLE);

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
                    tvStratTime.setText(rushHourFormArrayList.get(i).getStartTime());
                    tvEndTime.setText(rushHourFormArrayList.get(i).getEndTime());
                    btnSave.setVisibility(View.GONE);
                    ivBtnEdit.setVisibility(View.GONE);
                    btnUpdate.setVisibility(View.VISIBLE);


                }
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
        tvStratTime = findViewById(R.id.tv_from_time);
        tvEndTime = findViewById(R.id.tv_end_time);
        btnSave = findViewById(R.id.btn_save);
        ivBtnEdit = findViewById(R.id.iv_btn_edit);
        btnUpdate = findViewById(R.id.btn_update);
        // LayoutCalender=findViewById(R.id.relative_layout);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }


        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int i1) {

            if (hourOfDay == 0) {
                hourOfDay += 12;
                format = "AM";
            } else if (hourOfDay == 12) {
                format = "PM";
            } else if (hourOfDay > 12) {
                hourOfDay -= 12;

                format = "PM";
            } else {
                format = "AM";
                if (status == true) {
                    tvStratTime.setText(/*tvStratTime.getText() + " -" +*/ hourOfDay + ":" + i1 + format);
                } else {
                    tvEndTime.setText(/*tvStratTime.getText() + " -" +*/ hourOfDay + ":" + i1 + format);
                }

            }
        }

    }
}
