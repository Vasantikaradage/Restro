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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Formatter.PlacementValueFormatter;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.DailyReportForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.SuperAdmin.Models.HotelForm;
import com.restrosmart.restrohotel.Utils.Sessionmanager;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.DAILY_SA_REPORT;
import static com.restrosmart.restrohotel.ConstantVariables.GET_SA_ALL_HOTEL;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.EMP_ID;

public class FragmentTabSADailyReport extends Fragment {

    private List<PieEntry> entries;
    private ArrayList<String> labels;

    private PieChart pieChartDailyReport;
    private View view;
    private LinearLayout llNoData;

    private ArrayList<DailyReportForm> arrayListDailyreport;
    private TextView tvSelectDate;


    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;
    private HashMap<String, String> superAdminInfo;
    private ArrayList<HotelForm> hotelFormArrayList;
    private SearchableSpinner spHotel;
    private int position, selectedHotelId;
    private PieDataSet dataset;
    private SpinKitView skLoading;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_daily_sa_report, null);
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
        // spinKitView.setVisibility(View.VISIBLE);
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(GET_SA_ALL_HOTEL, (service.getSAHotelDetails(Integer.parseInt(superAdminInfo.get(EMP_ID)))));


        spHotel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedHotelId = hotelFormArrayList.get(position).getHotelId();

                tvSelectDate.setOnClickListener(new View.OnClickListener() {
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
                                String format = "dd/MM/yyyy";

                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
                                SimpleDateFormat sdf1 = new SimpleDateFormat(format, Locale.ENGLISH);

                                tvSelectDate.setText(sdf1.format(myCalendar.getTime()));

                                initRetrofitCallback();
                                skLoading.setVisibility(View.VISIBLE);
                                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                                mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                                mRetrofitService.retrofitData(DAILY_SA_REPORT, (service.Order(1)));

                            }
                        }, mYear, mMonth, mDay);
                        mDatePicker.setTitle("Select date");
                        mDatePicker.show();
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

                    case DAILY_SA_REPORT:

                        String strObject = "{\n" +
                                "  \"status\": \"1\",\n" +
                                "  \"msg\": \"Records Found\",\n" +
                                "  \"DailyReport\": [\n" +
                                "    {\n" +
                                "      \"Category_Name\": \"Food\",\n" +
                                "      \"Sale_Count\": 1000\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"Category_Name\": \"Liquors\",\n" +
                                "      \"Sale_Count\": 300\n" +
                                "    }\n" +
                                "  ]\n" +
                                "}";
                        try {
                            JSONObject object = new JSONObject(strObject);
                            int status = object.getInt("status");

                            if (status == 1) {
                                skLoading.setVisibility(View.GONE);
                                JSONArray jsonArray = object.getJSONArray("DailyReport");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject dailyOfferObj = jsonArray.getJSONObject(i);
                                    DailyReportForm dailyReportForm = new DailyReportForm();
                                    dailyReportForm.setCategoryName(dailyOfferObj.getString("Category_Name"));
                                    dailyReportForm.setSaleCount(dailyOfferObj.getInt("Sale_Count"));
                                    arrayListDailyreport.add(dailyReportForm);
                                }
                                getDailyReportData(arrayListDailyreport);
                            } else {
                                llNoData.setVisibility(View.VISIBLE);
                                skLoading.setVisibility(View.GONE);
                            }
                            //  spinKitView.setVisibility(View.GONE);
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


    private void getDailyReportData(ArrayList<DailyReportForm> arrayListDailyreportData) {

        entries.clear();
        for (int x = 0; x < arrayListDailyreportData.size(); x++) {
            entries.add(new PieEntry(Float.parseFloat(arrayListDailyreportData.get(x).getSaleCount() + "f"), x));
        }

        int[] COLORFUL_COLORS = {
                Color.rgb(106, 150, 31),
                Color.rgb(193, 37, 82),
                Color.rgb(79, 129, 189),
                Color.rgb(245, 199, 0)};

        dataset = new PieDataSet(entries, getString(R.string.daily_report));
        pieChartDailyReport.animateY(5000);
        pieChartDailyReport.setTouchEnabled(true);

        dataset.setValueFormatter(new PlacementValueFormatter());
        dataset.setColors(COLORFUL_COLORS);
        dataset.setValueTextColor(Color.parseColor("#FFFFFFFF"));
        pieChartDailyReport.setDrawSliceText(false);

        pieChartDailyReport.animateX(1000);
        pieChartDailyReport.setDescription("");

        pieChartDailyReport.setDescriptionColor(getContext().getResources().getColor(R.color.lightred));

        labels.clear();
        for (int x = 0; x < arrayListDailyreportData.size(); x++) {
            labels.add(arrayListDailyreportData.get(x).getCategoryName());
        }

        PieData data = new PieData(dataset);
        data.setValueTextSize(16f);
        pieChartDailyReport.setData(data);
        pieChartDailyReport.setCenterText("Daily Report");
        pieChartDailyReport.setCenterTextSize(14f);
        pieChartDailyReport.setCenterTextColor(getContext().getResources().getColor(R.color.colorAccent));

        if (pieChartDailyReport != null) {
            pieChartDailyReport.notifyDataSetChanged();
            pieChartDailyReport.invalidate();
        }
    }

    private void init() {
        entries = new ArrayList<>();
        labels = new ArrayList<>();

        pieChartDailyReport = view.findViewById(R.id.piechart);
        llNoData = view.findViewById(R.id.llDailyReportNoData);

        arrayListDailyreport = new ArrayList<>();
        tvSelectDate = view.findViewById(R.id.tv_select_date);
        hotelFormArrayList = new ArrayList<>();
        spHotel = view.findViewById(R.id.sp_hotel_name);
        skLoading = view.findViewById(R.id.skLoading);
    }
}
