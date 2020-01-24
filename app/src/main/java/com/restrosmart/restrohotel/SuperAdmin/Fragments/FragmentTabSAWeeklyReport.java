package com.restrosmart.restrohotel.SuperAdmin.Fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Admin.IAxisValueFormatter;
import com.restrosmart.restrohotel.Admin.MyValueFormatter;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.SuperAdmin.Models.HotelForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.WeeklySAReportForm;
import com.restrosmart.restrohotel.Utils.Sessionmanager;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.GET_SA_ALL_HOTEL;
import static com.restrosmart.restrohotel.ConstantVariables.WEEKLY_SA_REPORT;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.EMP_ID;

public class FragmentTabSAWeeklyReport extends Fragment {
    private View view;
    private SpinKitView skLoading;
    private LinearLayout llNoData;
    private BarChart barChart;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    HashMap<String, String> superAdminInfo;
    private TextView tvFormDate;
    private ArrayList<HotelForm> hotelFormArrayList;
    private SearchableSpinner spHotel;
    private ArrayList<String> labels;
    private  ArrayList<WeeklySAReportForm> weeklySAReportFormArrayList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_weekly_sa_report, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

        skLoading.setVisibility(View.GONE);
        Sessionmanager sharedPreferanceManage = new Sessionmanager(getActivity());
        superAdminInfo = sharedPreferanceManage.getSuperAdminDetails();

        initRetrofitCallback();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(GET_SA_ALL_HOTEL, (service.getSAHotelDetails(Integer.parseInt(superAdminInfo.get(EMP_ID)))));


        tvFormDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dp = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                //Check if selected month/year is in future of today.

                                Calendar myCalendar = Calendar.getInstance();
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, monthOfYear);
                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                String myFormat = "dd/MM/yyyy";

                                //Date date = dayOfMonth + monthOfYear + monthOfYear;

                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
                                String dateinfo = sdf.format(myCalendar.getTime());

                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(myCalendar.getTime());
                                calendar.add(Calendar.DAY_OF_YEAR, +7);
                               // Date newDate = calendar.getTime();

                                String afterDate=sdf.format( calendar.getTime());
                                tvFormDate.setText(dateinfo+" - "+afterDate);

                                initRetrofitCallback();
                                skLoading.setVisibility(View.VISIBLE);
                                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                                mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                                mRetrofitService.retrofitData(WEEKLY_SA_REPORT, (service.Order(1)));

                            }

                        }, year, month, day);

                dp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dp.setTitle("Set Weekly Overview");
                dp.show();
            }
        });

    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String strObjInfo = jsonObject.toString();

                switch (requestId) {
                    case GET_SA_ALL_HOTEL:
                        try {
                            JSONObject object = new JSONObject(strObjInfo);
                            int status = object.getInt("status");
                            if (status == 1) {
                                hotelFormArrayList.clear();
                                HotelForm hotelForm1 = new HotelForm();
                                hotelForm1.setHotelId(0);
                                hotelForm1.setHotelName("Select Hotel");
                                hotelFormArrayList.add(hotelForm1);
                                JSONArray jsonArray = object.getJSONArray("Hotellist");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject hotelObject = jsonArray.getJSONObject(i);
                                    HotelForm hotelForm = new HotelForm();
                                    hotelForm.setHotelId(hotelObject.getInt("Hotel_Id"));
                                    hotelForm.setHotelName(hotelObject.getString("Hotel_Name"));
                                    hotelFormArrayList.add(hotelForm);
                                }
                            }
                            ArrayAdapter<HotelForm> stringArrayAdapter = new ArrayAdapter<HotelForm>(getActivity(), R.layout.spinner_background_text, hotelFormArrayList);
                            stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spHotel.setAdapter(stringArrayAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case WEEKLY_SA_REPORT:

                        String strWeekReport="{\n" +
                                "  \"status\": \"1\",\n" +
                                "  \"msg\": \"Records Found\",\n" +
                                "  \"WeeklyReport\": [\n" +
                                "    {\n" +
                                "      \"Cat_Food\": \"45\",\n" +
                                "      \"Cat_Liquors\": \"20\",\n" +
                                "      \"Day\": \"Mon\"\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"Cat_Food\": \"48\",\n" +
                                "      \"Cat_Liquors\": \"60\",\n" +
                                "      \"Day\": \"Tue\"\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"Cat_Food\": \"16\",\n" +
                                "      \"Cat_Liquors\": \"34\",\n" +
                                "      \"Day\": \"Wen\"\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"Cat_Food\": \"20\",\n" +
                                "      \"Cat_Liquors\": \"60\",\n" +
                                "      \"Day\": \"Thu\"\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"Cat_Food\": \"60\",\n" +
                                "      \"Cat_Liquors\": \"100\",\n" +
                                "      \"Day\": \"Fri\"\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"Cat_Food\": \"50\",\n" +
                                "      \"Cat_Liquors\": \"60\",\n" +
                                "      \"Day\": \"Sat\"\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"Cat_Food\": \"40\",\n" +
                                "      \"Cat_Liquors\": \"28\",\n" +
                                "      \"Day\": \"Sun\"\n" +
                                "    }\n" +
                                "  ]\n" +
                                "}";

                        try {
                            JSONObject object=new JSONObject(strWeekReport);
                            int status=object.getInt("status");
                            if(status==1)
                            {

                                JSONArray jsonArray=object.getJSONArray("WeeklyReport");
                                for(int i =0; i<jsonArray.length(); i++)
                                {
                                    JSONObject objectWeek=jsonArray.getJSONObject(i);
                                    WeeklySAReportForm weeklySAReportForm=new WeeklySAReportForm();
                                    weeklySAReportForm.setCatFood(objectWeek.getString("Cat_Food"));
                                    weeklySAReportForm.setCatLiquors(objectWeek.getString("Cat_Liquors"));
                                    weeklySAReportForm.setDay(objectWeek.getString("Day"));
                                    weeklySAReportFormArrayList.add(weeklySAReportForm);
                                }
                                weeklyReport(weeklySAReportFormArrayList);
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
                Log.d("", "retrofitError" + error);

            }
        };
    }

    private void weeklyReport(ArrayList<WeeklySAReportForm> weeklySAReportFormArrayList) {
        skLoading.setVisibility(View.GONE);
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < weeklySAReportFormArrayList.size(); i++) {
            // float mult = (size + 1);

            float val1 = Float.parseFloat(weeklySAReportFormArrayList.get(i).getCatFood());
            float val2 = Float.parseFloat(weeklySAReportFormArrayList.get(i).getCatFood());



           /* float val1 = (float) (Math.random() * mult) + mult / 3;
            float val2 = (float) (Math.random() * mult) + mult / 3;
            float val3 = (float) (Math.random() * mult) + mult / 3;
            float val4 = (float) (Math.random() * mult) + mult / 3;*/

            yVals1.add(new BarEntry(
                    i,
                    new float[]{val1, val2},
                    String.valueOf(getResources().getDrawable(R.drawable.bg_img_green))));
        }

        BarDataSet set1;

        barChart.setDescription("Days");


        if (barChart.getData() != null &&
                barChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "");
            // set1.setDrawIcons(false);
            int[] COLORFUL_COLORS = {
                    Color.rgb(106, 150, 31),
                    Color.rgb(193, 37, 82),
                   /* Color.rgb(79, 129, 189),
                    Color.rgb(245, 199, 0)*/};


            set1.setColors(COLORFUL_COLORS);

            set1.setStackLabels(new String[]{"Food","Liquors"});

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);


            labels.clear();
            for (int x = 0; x < weeklySAReportFormArrayList.size(); x++) {
                labels.add(weeklySAReportFormArrayList.get(x).getDay());
            }

            XAxis xAxis = barChart.getXAxis();

            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return labels.get((int) value % labels.size());
                }
            });

            Legend legend = barChart.getLegend();
            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            barChart.getLegend().setWordWrapEnabled(true);
            legend.setDrawInside(true);


            BarData data = new BarData(dataSets);
            data.setBarWidth(0.7f);
            data.setValueFormatter(new MyValueFormatter());
            data.setValueTextColor(Color.WHITE);

            barChart.setData(data);

            barChart.setDrawValueAboveBar(false);
            barChart.setHighlightFullBarEnabled(false);
        }


        barChart.animateY(3000);
        barChart.setFitBars(true);
        barChart.invalidate();
    }

    private void init() {
        skLoading = view.findViewById(R.id.skLoading);
        llNoData = view.findViewById(R.id.llweeklyReportNoData);
        barChart = view.findViewById(R.id.barchart);
        tvFormDate = view.findViewById(R.id.tv_from_date);
        hotelFormArrayList=new ArrayList<>();
        spHotel=view.findViewById(R.id.sp_hotel_name);
        labels=new ArrayList<>();
        weeklySAReportFormArrayList=new ArrayList<>();
    }
}
