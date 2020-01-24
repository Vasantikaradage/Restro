package com.restrosmart.restrohotel.Admin;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.WeeklyReportForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import retrofit2.Response;
import static com.restrosmart.restrohotel.ConstantVariables.WEEKLY_REPORT;

@SuppressLint("ValidFragment")
public class FragmentTabWeeklyReport extends Fragment {

    private BarChart barChart;
    private SpinKitView skLoading;
    private LinearLayout llWeeklyReportNoData;
    int size = 7;
    private ArrayList<String> labels;
    private TextView tvSelectFromDate, tvSelectToDate;
    private Calendar mcurrentDate;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;

    private String[] Days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};


    private String[] veg = {"10", "20", "30", "40", "50", "60", "70"};
    private String[] novveg = {"20", "30", "30", "40", "50", "70", "75"};
    private String[] liq = {"10", "20", "30", "40", "50", "60", "70"};
    private String[] hotCold = {"20", "30", "30", "40", "50", "70", "75"};

    private ArrayList<WeeklyReportForm> weeklyReportFormArrayList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_weekly_report, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        tvSelectFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mcurrentDate = Calendar.getInstance();
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
                        String format = "dd-MM-yyyy";

                        SimpleDateFormat sdf1 = new SimpleDateFormat(format, Locale.ENGLISH);
                        tvSelectFromDate.setText(sdf1.format(myCalendar.getTime()));

                     /*   initRetrofitCallback();
                        spinKitView.setVisibility(View.VISIBLE);
                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                        mRetrofitService.retrofitData(ORDER_DETAILS, (service.Order(1)));*/

                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();

            }
        });

        tvSelectToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcurrentDate = Calendar.getInstance();
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
                        String format = "dd-MM-yyyy";

                        SimpleDateFormat sdf1 = new SimpleDateFormat(format, Locale.ENGLISH);
                        tvSelectToDate.setText(sdf1.format(myCalendar.getTime()));


                        initRetrofitCallback();
                        // spinKitView.setVisibility(View.VISIBLE);
                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                        mRetrofitService.retrofitData(WEEKLY_REPORT, (service.Order(1)));

                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();


            }
        });


    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                //  String strStstus=jsonObject.toString();
                String strStstus = "{\n" +
                        "  \"status\": \"1\",\n" +
                        "  \"WeeklyReport\": [\n" +
                        "    {\n" +
                        "      \"Cat_Veg\": \"50\",\n" +
                        "      \"Cat_Non_Veg\": \"45\",\n" +
                        "      \"Cat_Liquors\": \"20\",\n" +
                        "      \"Cat_Refresher\": \"70\",\n" +
                        "      \"Day\": \"Mon\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"Cat_Veg\": \"30\",\n" +
                        "      \"Cat_Non_Veg\": \"48\",\n" +
                        "      \"Cat_Liquors\": \"60\",\n" +
                        "      \"Cat_Refresher\": \"30\",\n" +
                        "      \"Day\": \"Tue\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"Cat_Veg\": \"50\",\n" +
                        "      \"Cat_Non_Veg\": \"16\",\n" +
                        "      \"Cat_Liquors\": \"34\",\n" +
                        "      \"Cat_Refresher\": \"90\",\n" +
                        "      \"Day\": \"Wen\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"Cat_Veg\": \"100\",\n" +
                        "      \"Cat_Non_Veg\": \"20\",\n" +
                        "      \"Cat_Liquors\": \"60\",\n" +
                        "      \"Cat_Refresher\": \"20\",\n" +
                        "      \"Day\": \"Thu\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"Cat_Veg\": \"50\",\n" +
                        "      \"Cat_Non_Veg\": \"60\",\n" +
                        "      \"Cat_Liquors\": \"100\",\n" +
                        "      \"Cat_Refresher\": \"40\",\n" +
                        "      \"Day\": \"Fri\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"Cat_Veg\": \"20\",\n" +
                        "      \"Cat_Non_Veg\": \"50\",\n" +
                        "      \"Cat_Liquors\": \"60\",\n" +
                        "      \"Cat_Refresher\": \"230\",\n" +
                        "      \"Day\": \"Sat\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"Cat_Veg\": \"100\",\n" +
                        "      \"Cat_Non_Veg\": \"40\",\n" +
                        "      \"Cat_Liquors\": \"28\",\n" +
                        "      \"Cat_Refresher\": \"123\",\n" +
                        "      \"Day\": \"Sun\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}";

                try {
                    JSONObject object = new JSONObject(strStstus);
                    int status = object.getInt("status");
                    if (status == 1) {
                        llWeeklyReportNoData.setVisibility(View.GONE);
                        JSONArray jsonArray = object.getJSONArray("WeeklyReport");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject weekObject = jsonArray.getJSONObject(i);
                            WeeklyReportForm weeklyReportForm = new WeeklyReportForm();
                            weeklyReportForm.setCat_Veg(weekObject.getString("Cat_Veg"));
                            weeklyReportForm.setCat_Non_Veg(weekObject.getString("Cat_Non_Veg"));
                            weeklyReportForm.setCat_Liquors(weekObject.getString("Cat_Liquors"));
                            weeklyReportForm.setCat_Refresher(weekObject.getString("Cat_Refresher"));
                            weeklyReportForm.setDay(weekObject.getString("Day"));
                            weeklyReportFormArrayList.add(weeklyReportForm);

                        }
                        skLoading.setVisibility(View.VISIBLE);
                        getYearlyReport(weeklyReportFormArrayList);
                    } else {
                        llWeeklyReportNoData.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("", "requestId" + requestId);
                Log.d("", "retrofitError" + error);

            }
        };
    }

    private void getYearlyReport(ArrayList<WeeklyReportForm> weeklyReportFormArrayList) {

        skLoading.setVisibility(View.GONE);
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();


        for (int i = 0; i < weeklyReportFormArrayList.size(); i++) {
            // float mult = (size + 1);

            float val1 = Float.parseFloat(weeklyReportFormArrayList.get(i).getCat_Veg());
            float val2 = Float.parseFloat(weeklyReportFormArrayList.get(i).getCat_Non_Veg());
            float val3 = Float.parseFloat(weeklyReportFormArrayList.get(i).getCat_Liquors());
            float val4 = Float.parseFloat(weeklyReportFormArrayList.get(i).getCat_Refresher());


           /* float val1 = (float) (Math.random() * mult) + mult / 3;
            float val2 = (float) (Math.random() * mult) + mult / 3;
            float val3 = (float) (Math.random() * mult) + mult / 3;
            float val4 = (float) (Math.random() * mult) + mult / 3;*/

            yVals1.add(new BarEntry(
                    i,
                    new float[]{val1, val2, val3, val4},
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
                    Color.rgb(79, 129, 189),
                    Color.rgb(245, 199, 0)};


            set1.setColors(COLORFUL_COLORS);

            set1.setStackLabels(new String[]{"Veg", "Nov Veg", "Liquors", "Hot and Cold"});

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);


            labels.clear();
            for (int x = 0; x < weeklyReportFormArrayList.size(); x++) {
                labels.add(weeklyReportFormArrayList.get(x).getDay());
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
        labels = new ArrayList<>();
        barChart = getView().findViewById(R.id.barchart);
        skLoading = getView().findViewById(R.id.skLoading);
        llWeeklyReportNoData = getView().findViewById(R.id.llWeeklyReportNoData);
        tvSelectFromDate = getView().findViewById(R.id.tv_select_from_date);
        tvSelectToDate = getView().findViewById(R.id.tv_select_to_date);
        weeklyReportFormArrayList = new ArrayList<>();
    }


}
