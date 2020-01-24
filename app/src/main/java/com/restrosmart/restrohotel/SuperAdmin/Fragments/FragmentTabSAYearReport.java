package com.restrosmart.restrohotel.SuperAdmin.Fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
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
import android.widget.Toast;

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
import com.restrosmart.restrohotel.Admin.LineValueFormatter;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.SuperAdmin.Models.HotelForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.YearlySAReportForm;
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
import static com.restrosmart.restrohotel.ConstantVariables.YEARLY_SA_REPORT;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.EMP_ID;

public class FragmentTabSAYearReport extends Fragment {
    private View view;
    private LinearLayout llNoData;
    private SpinKitView skLoading;
    private RetrofitService mRetrofitService;
    private HashMap<String, String> superAdminInfo;
    private IResult mResultCallBack;
    private ArrayList<HotelForm> hotelFormArrayList;
    private SearchableSpinner spHotel;
    private int position, selectedHotelId;
    private LineChart lineChartYearlyReport;
    private  ArrayList<Entry> arrayListLiquors,arrayListFood;
    private ArrayList<String> labels;



    private TextView tvSelectYear;
    private ArrayList<YearlySAReportForm> yearlySAReportFormArrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_yearly_sa_report, null);
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
      //  skLoading.setVisibility(View.VISIBLE);
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(GET_SA_ALL_HOTEL, (service.getSAHotelDetails(Integer.parseInt(superAdminInfo.get(EMP_ID)))));


        spHotel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedHotelId = hotelFormArrayList.get(position).getHotelId();

                if (selectedHotelId != 0) {

                    tvSelectYear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar mcurrentDate = Calendar.getInstance();
                            int mYear = mcurrentDate.get(Calendar.YEAR);
                            int mMonth = mcurrentDate.get(Calendar.MONTH);
                            int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                            DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                                public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                                    Calendar myCalendar = Calendar.getInstance();
                                    myCalendar.set(Calendar.YEAR, selectedyear);
                                    myCalendar.set(Calendar.MONTH, selectedmonth);
                                    myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                                    String myFormat = "yyyy-MM-dd h:mm:ss a";
                                    String format = "yyyy";

                                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
                                    SimpleDateFormat sdf1 = new SimpleDateFormat(format, Locale.ENGLISH);

                                    tvSelectYear.setText(sdf1.format(myCalendar.getTime()));

                                    initRetrofitCallback();
                                    skLoading.setVisibility(View.VISIBLE);
                                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                                    mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                                    mRetrofitService.retrofitData(YEARLY_SA_REPORT, (service.Order(1)));

                                }
                            }, mYear, mMonth, mDay);
                            mDatePicker.setTitle("Select date");
                            mDatePicker.show();
                        }
                    });

                } else {
                    Toast.makeText(getActivity(), "Please select hotel", Toast.LENGTH_SHORT).show();
                }
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

                    case YEARLY_SA_REPORT:
                        try {
                            String strObject = "{\n" +
                                    "  \"status\": \"1\",\n" +
                                    "  \"msg\": \"Records Found\",\n" +
                                    "  \"yearReport\": [\n" +
                                    "    {\n" +
                                    "      \"Cat_Food\": \"45\",\n" +
                                    "      \"Cat_Liquors\": \"20\",\n" +
                                    "      \"Month\": \"Jan\"\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"Cat_Food\": \"48\",\n" +
                                    "      \"Cat_Liquors\": \"60\",\n" +
                                    "      \"Month\": \"Feb\"\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"Cat_Food\": \"16\",\n" +
                                    "      \"Cat_Liquors\": \"34\",\n" +
                                    "      \"Month\": \"Mar\"\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"Cat_Food\": \"20\",\n" +
                                    "      \"Cat_Liquors\": \"60\",\n" +
                                    "      \"Month\": \"Apr\"\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"Cat_Food\": \"120\",\n" +
                                    "      \"Cat_Liquors\": \"160\",\n" +
                                    "      \"Month\": \"May\"\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"Cat_Food\": \"210\",\n" +
                                    "      \"Cat_Liquors\": \"610\",\n" +
                                    "      \"Month\": \"Jun\"\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"Cat_Food\": \"20\",\n" +
                                    "      \"Cat_Liquors\": \"62\",\n" +
                                    "      \"Month\": \"Jul\"\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"Cat_Food\": \"120\",\n" +
                                    "      \"Cat_Liquors\": \"62\",\n" +
                                    "      \"Month\": \"Aug\"\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"Cat_Food\": \"20\",\n" +
                                    "      \"Cat_Liquors\": \"20\",\n" +
                                    "      \"Month\": \"Sep\"\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"Cat_Food\": \"100\",\n" +
                                    "      \"Cat_Liquors\": \"23\",\n" +
                                    "      \"Month\": \"Oct\"\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"Cat_Food\": \"320\",\n" +
                                    "      \"Cat_Liquors\": \"234\",\n" +
                                    "      \"Month\": \"Nov\"\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"Cat_Food\": \"250\",\n" +
                                    "      \"Cat_Liquors\": \"250\",\n" +
                                    "      \"Month\": \"Dec\"\n" +
                                    "    }\n" +
                                    "  ]\n" +
                                    "}";

                            JSONObject object = new JSONObject(strObject);
                            int status = object.getInt("status");

                            if (status == 1) {
                              // llNoData.setVisibility(View.GONE);

                                JSONArray jsonArray = object.getJSONArray("yearReport");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject monthlyReportObj = jsonArray.getJSONObject(i);
                                    YearlySAReportForm yearlySAReportForm = new YearlySAReportForm();
                                    yearlySAReportForm.setCategoryFood(monthlyReportObj.getString("Cat_Food"));
                                    yearlySAReportForm.setCategoryLiquors(monthlyReportObj.getString("Cat_Liquors"));
                                    yearlySAReportForm.setMonth(monthlyReportObj.getString("Month"));
                                    yearlySAReportFormArrayList.add(yearlySAReportForm);
                                }
                                ShowYearlyRepory(yearlySAReportFormArrayList);
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

    private void ShowYearlyRepory(ArrayList<YearlySAReportForm> yearlySAReportFormArrayList) {
        skLoading.setVisibility(View.GONE);
        LineDataSet set1, set2;
        arrayListFood.clear();
        for (int x = 0; x < yearlySAReportFormArrayList.size(); x++) {
            arrayListFood.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat( yearlySAReportFormArrayList.get(x).getCategoryFood())));
        }

        arrayListLiquors.clear();
        for (int x = 0; x <  yearlySAReportFormArrayList.size(); x++) {
            arrayListLiquors.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat(yearlySAReportFormArrayList.get(x).getCategoryLiquors())));
        }
      /*  arrayListLiquors.clear();
        for (int x = 0; x <  yearlySAReportFormArrayList.size(); x++) {
            arrayListLiquors.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat(yearlySAReportFormArrayList.get(x).getCat_Liquors())));
        }

        arrayListHotCold.clear();
        for (int x = 0; x <  yearlySAReportFormArrayList.size(); x++) {
            arrayListHotCold.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat(yearlySAReportFormArrayList.get(x).getCat_Refresher())));
        }*/


        labels.clear();
        for (int x = 0; x <  yearlySAReportFormArrayList.size(); x++) {
            labels.add(yearlySAReportFormArrayList.get(x).getMonth());
        }

        XAxis xAxis = lineChartYearlyReport.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularityEnabled(true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                //   Log.d("asa", "arrayListNonveg X" + labels);
                return labels.get((int) value % labels.size());
            }
        });

        //lineChartYearlyReport.getAxisLeft().setDrawGridLines(false);
        // lineChartYearlyReport.getXAxis().setDrawGridLines(false);
        lineChartYearlyReport.setDescription("Months");

        ArrayList<ILineDataSet> lines = new ArrayList<ILineDataSet> ();

        LineDataSet lDataSet1 = new LineDataSet(arrayListFood, "Food");
        lDataSet1.setColor(Color.rgb(106, 150, 31));
        //  lDataSet1.setDrawFilled(true);
        lDataSet1.setCircleColor(Color.rgb(106, 150, 31));
        //  lDataSet1.setFillColor(Color.rgb(106, 150, 31));

        lDataSet1.setValueFormatter(new LineValueFormatter());
        lines.add(lDataSet1);

        LineDataSet lDataSet2 = new LineDataSet(arrayListLiquors, "Liquors");
        lDataSet2.setColor(Color.rgb(193, 37, 82));
        lDataSet2.setCircleColor(Color.rgb(193, 37, 82));
        // lDataSet2.setDrawFilled(true);
        //  lDataSet2.setFillColor(Color.rgb(193, 37, 82));
        lines.add(lDataSet2);

       /* LineDataSet lDataSet3 = new LineDataSet(arrayListLiquors, "Liquors");
        lDataSet3.setColor(Color.rgb(79,129,189));
        lDataSet3.setCircleColor(Color.rgb(79,129,189));
        // lDataSet2.setDrawFilled(true);
        //  lDataSet2.setFillColor(Color.rgb(193, 37, 82));
        lines.add(lDataSet3);

        LineDataSet lDataSet4 = new LineDataSet(arrayListHotCold, "Hot and Cold");
        lDataSet4.setColor(Color.rgb(245, 199, 0));
        lDataSet4.setCircleColor(Color.rgb(245, 199, 0));
        // lDataSet2.setDrawFilled(true);
        //  lDataSet2.setFillColor(Color.rgb(193, 37, 82));
        lines.add(lDataSet4);*/
        lineChartYearlyReport.animateY(3000);
        lineChartYearlyReport.setTouchEnabled(true);
        CustomMarkerView mv = new CustomMarkerView (getActivity(), R.layout.rader_markview, labels);
        lineChartYearlyReport.setMarkerView(mv);

        Legend legend = lineChartYearlyReport.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        lineChartYearlyReport.getLegend().setWordWrapEnabled(true);

        legend.setDrawInside(true);
        lineChartYearlyReport.setData(new LineData(lines));

    }

    private void init() {
        lineChartYearlyReport = getView().findViewById(R.id.linechart);
        tvSelectYear = view.findViewById(R.id.tv_select_year);
        hotelFormArrayList = new ArrayList<>();
        spHotel = view.findViewById(R.id.sp_hotel_name);
        skLoading = view.findViewById(R.id.skLoading);
        yearlySAReportFormArrayList =new ArrayList<>();
        arrayListLiquors=new ArrayList<>();
        arrayListFood=new ArrayList<>();
        labels=new ArrayList<>();
        llNoData=view.findViewById(R.id.llyearlyReportNoData);
    }
}
