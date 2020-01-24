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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Admin.CustomMarkerView;
import com.restrosmart.restrohotel.Admin.IAxisValueFormatter;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.SuperAdmin.Models.HotelForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.MonthlySAReportForm;
import com.restrosmart.restrohotel.Utils.Sessionmanager;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.GET_SA_ALL_HOTEL;
import static com.restrosmart.restrohotel.ConstantVariables.MONTHLY_SA_REPORT;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.EMP_ID;

public class FragmentTabSAMonthlyReport extends Fragment {
    private View view;
    private LinearLayout llNoData;
    private SpinKitView skLoading;
    private RetrofitService mRetrofitService;
    private HashMap<String, String> superAdminInfo;
    private IResult mResultCallBack;
    private ArrayList<HotelForm> hotelFormArrayList;
    private SearchableSpinner spHotel;
    private int position, selectedHotelId;

    private TextView tvSelectMonth;
    private ArrayList<MonthlySAReportForm> monthlySAReportFormArrayList;

    private ArrayList<Entry> entries;
    private ArrayList<String> labels;
    private ArrayList<Entry> data;
    private ArrayList<Entry> liquorsArray;
    private ArrayList<Entry> hotColdArray;
    private LineChart lineChartMonthlyReport;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_monthly_sa_report, null);
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


        spHotel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedHotelId = hotelFormArrayList.get(position).getHotelId();

                tvSelectMonth.setOnClickListener(new View.OnClickListener() {
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

                                        String myFormat = "MMM - yyyy";

                                        //Date date = dayOfMonth + monthOfYear + monthOfYear;

                                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
                                        String dateinfo = sdf.format(myCalendar.getTime());
                                        tvSelectMonth.setText(dateinfo);

                                        initRetrofitCallback();
                                        skLoading.setVisibility(View.VISIBLE);
                                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                                        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                                        mRetrofitService.retrofitData(MONTHLY_SA_REPORT, (service.Order(1)));

                                    }

                                }, year, month, day);

                        dp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dp.setTitle("Set Monthly Overview");
                        dp.show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                    case MONTHLY_SA_REPORT:
                        String strObject = "{\n" +
                                "  \"status\": \"1\",\n" +
                                "  \"MonthlyReport\": [\n" +
                                "    {\n" +
                                "      \"Cat_Food\": \"50\",\n" +
                                "      \"Cat_Liquors\": \"45\",\n" +
                                "      \"Week\": \"1 week\"\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"Cat_Food\": \"30\",\n" +
                                "      \"Cat_Liquors\": \"30\",\n" +
                                "      \"Week\": \"2 week\"\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"Cat_Food\": \"50\",\n" +
                                "      \"Cat_Liquors\": \"90\",\n" +
                                "      \"Week\": \"3 week\"\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"Cat_Food\": \"100\",\n" +
                                "      \"Cat_Liquors\": \"20\",\n" +
                                "      \"Week\": \"4 week\"\n" +
                                "    }\n" +
                                "  ]\n" +
                                "}";
                        try {


                            JSONObject object = new JSONObject(strObject);
                            int status = object.getInt("status");

                            if (status == 1) {
                              //  llNoData.setVisibility(View.GONE);
                                JSONArray jsonArray = object.getJSONArray("MonthlyReport");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject monthlyReportObj = jsonArray.getJSONObject(i);
                                    MonthlySAReportForm monthlySAReportForm = new MonthlySAReportForm();
                                    monthlySAReportForm.setCategoryFood(monthlyReportObj.getString("Cat_Food"));
                                    monthlySAReportForm.setCategoryLiquors(monthlyReportObj.getString("Cat_Liquors"));
                                    monthlySAReportForm.setWeek(monthlyReportObj.getString("Week"));
                                    monthlySAReportFormArrayList.add(monthlySAReportForm);
                                }
                                getMonthlyReport(monthlySAReportFormArrayList);
                            } else {
                              //  llNoData.setVisibility(View.VISIBLE);
                            }
                            skLoading.setVisibility(View.GONE);
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
                skLoading.setVisibility(View.GONE);
            }
        };
    }

    private void getMonthlyReport(ArrayList<MonthlySAReportForm> monthlySAReportFormListData) {
        //  progressBar.setVisibility(View.GONE);
        skLoading.setVisibility(View.GONE);
        LineDataSet set1, set2;

      /*  Legend legend = lineChartMonthlyReport.getLegend();
        legend.setYOffset(40);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextColor(Color.WHITE);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        legend.setTextSize(200);
        lineChartMonthlyReport.invalidate();*/

        entries.clear();
        for (int x = 0; x < monthlySAReportFormListData.size(); x++) {
            entries.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat(monthlySAReportFormListData.get(x).getCategoryFood())));
        }

        data.clear();
        for (int x = 0; x < monthlySAReportFormListData.size(); x++) {
            data.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat(monthlySAReportFormListData.get(x).getCategoryLiquors())));
        }

        /*liquorsArray.clear();
        for (int x = 0; x < monthlySAReportFormListData.size(); x++) {
            liquorsArray.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat(monthlySAReportFormListData.get(x).getCat_Liquors())));
        }


        hotColdArray.clear();
        for (int x = 0; x < monthlySAReportFormListData.size(); x++) {
            hotColdArray.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat(monthlySAReportFormListData.get(x).getCat_Refresher())));
        }*/


        labels.clear();
        for (int x = 0; x < monthlySAReportFormListData.size(); x++) {
            labels.add(monthlySAReportFormListData.get(x).getWeek());
        }

        XAxis xAxis = lineChartMonthlyReport.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularityEnabled(true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                //   Log.d("asa", "data X" + labels);
                return labels.get((int) value % labels.size());
            }
        });

        //lineChartMonthlyReport.getAxisLeft().setDrawGridLines(false);
        // lineChartMonthlyReport.getXAxis().setDrawGridLines(false);

        lineChartMonthlyReport.setDescription("Weeks");


        ArrayList<ILineDataSet> lines = new ArrayList<ILineDataSet>();

        LineDataSet lDataSet1 = new LineDataSet(entries, "Category Food");
        lDataSet1.setColor(Color.rgb(193, 37, 82));
        lDataSet1.setDrawFilled(true);
        lDataSet1.setFillAlpha(100);
        lDataSet1.setCircleColor(Color.rgb(193, 37, 82));
        lDataSet1.setFillColor(Color.rgb(193, 37, 82));
        lines.add(lDataSet1);

        LineDataSet lDataSet2 = new LineDataSet(data, "Category Liquors");
        lDataSet2.setColor(Color.rgb(106, 150, 31));
        lDataSet2.setCircleColor(Color.rgb(106, 150, 31));
        lDataSet2.setDrawFilled(true);
        lDataSet2.setFillAlpha(100);
        lDataSet2.setFillColor(Color.rgb(106, 150, 31));
        lines.add(lDataSet2);

       /* LineDataSet lDataSet3 = new LineDataSet(liquorsArray, "Liquors");
        lDataSet3.setColor(Color.rgb(79,129,189));
        lDataSet3.setCircleColor(Color.rgb(79,129,189));
        lDataSet3.setDrawFilled(true);
        lDataSet3.setFillAlpha(100);
        lDataSet3.setFillColor(Color.rgb(79,129,189));
        lines.add(lDataSet3);

        LineDataSet lDataSet4 = new LineDataSet(hotColdArray, "Hot and Cold");
        lDataSet4.setColor(Color.rgb(245, 199, 0));
        lDataSet4.setCircleColor(Color.rgb(245, 199, 0));
        lDataSet4.setDrawFilled(true);
        lDataSet4.setFillAlpha(100);
        lDataSet4.setFillColor(Color.rgb(245, 199, 0));
        lines.add(lDataSet4);*/

        Legend legend = lineChartMonthlyReport.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        lineChartMonthlyReport.getLegend().setWordWrapEnabled(true);

        legend.setDrawInside(true);

        CustomMarkerView mv = new CustomMarkerView(getActivity(), R.layout.rader_markview, labels);
        lineChartMonthlyReport.setMarkerView(mv);


        lineChartMonthlyReport.setData(new LineData(lines));
        lineChartMonthlyReport.animateY(3000);

    }

    private void init() {
        lineChartMonthlyReport = getView().findViewById(R.id.linechart);
        monthlySAReportFormArrayList = new ArrayList<>();
        tvSelectMonth = view.findViewById(R.id.tv_select_month);
        hotelFormArrayList = new ArrayList<>();
        spHotel = view.findViewById(R.id.sp_hotel_name);
        skLoading = view.findViewById(R.id.skLoading);

        entries = new ArrayList<>();
        labels = new ArrayList<>();
        data = new ArrayList<>();
        hotColdArray = new ArrayList<>();
        liquorsArray = new ArrayList<>();
        llNoData = view.findViewById(R.id.llMonthlyReportNoData);
    }
}
