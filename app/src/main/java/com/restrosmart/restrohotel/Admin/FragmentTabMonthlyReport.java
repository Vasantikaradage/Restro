package com.restrosmart.restrohotel.Admin;

import android.annotation.SuppressLint;
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
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.MonthlyReportForm;
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

import static com.restrosmart.restrohotel.ConstantVariables.ORDER_DETAILS;

@SuppressLint("ValidFragment")
public class FragmentTabMonthlyReport extends Fragment {

    private LineChart lineChartMonthlyReport;

    private TextView tvSelectMonth;

    XAxis xAxis;
    YAxis yVals1,yVals2;

    private ArrayList<Entry> entries;
    private ArrayList<String> labels;
    private  ArrayList<Entry> data;
    private  ArrayList <Entry> liquorsArray;
    private  ArrayList<Entry> hotColdArray;

    private  String[] months={"1 week","2 week","3 week","4 week"};

    private  String[] veg={"100","200","300","50"};

    private  String[] nonVeg={"200","500","600","500"};
    private  String[] Liquors={"100","400","700","200"};
    private  String[] hotCold={"600","500","600","800"};
    private    Calendar calendar;
    private  String dateinfo;
    private  RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private  ArrayList<MonthlyReportForm> arrayLisMonthlyreport;
    private SpinKitView skLoading;
    private LinearLayout llNodata;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_monthly_report, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        skLoading.setVisibility(View.GONE);

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
                                dateinfo = sdf.format(myCalendar.getTime());
                                tvSelectMonth.setText(dateinfo);

                                initRetrofitCallback();
                                skLoading.setVisibility(View.VISIBLE);
                                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                                mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                                mRetrofitService.retrofitData(ORDER_DETAILS, (service.Order(1)));

                            }

                        }, year, month, day);

                dp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dp.setTitle("Set Monthly Overview");
                dp.show();


            }
        });
    }

    private void initRetrofitCallback() {
        mResultCallBack=new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                skLoading.setVisibility(View.GONE);
                // String strObject=jsonObject.toString();
                llNodata.setVisibility(View.GONE);
                String strObject = "{\"status\":\"1\",\"MonthlyReport\":[{\"Cat_Veg\":\"50\",\"Cat_Non_Veg\":\"45\",\"Cat_Liquors\":\"20\",\"Cat_Refresher\":\"70\",\"Week\":\"1 week\"},{\"Cat_Veg\":\"30\",\"Cat_Non_Veg\":\"48\",\"Cat_Liquors\":\"60\",\"Cat_Refresher\":\"30\",\"Week\":\"2 week\"},{\"Cat_Veg\":\"50\",\"Cat_Non_Veg\":\"16\",\"Cat_Liquors\":\"34\",\"Cat_Refresher\":\"90\",\"Week\":\"3 week\"},{\"Cat_Veg\":\"100\",\"Cat_Non_Veg\":\"20\",\"Cat_Liquors\":\"60\",\"Cat_Refresher\":\"20\",\"Week\":\"4 week\"}]}";
                try {
                    JSONObject object = new JSONObject(strObject);
                    int status = object.getInt("status");

                    if (status == 1) {
                        JSONArray jsonArray = object.getJSONArray("MonthlyReport");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject monthlyReportObj = jsonArray.getJSONObject(i);
                            MonthlyReportForm monthlyReportForm = new MonthlyReportForm();
                            monthlyReportForm.setCat_Veg(monthlyReportObj.getString("Cat_Veg"));
                            monthlyReportForm.setCat_Non_Veg(monthlyReportObj.getString("Cat_Non_Veg"));
                            monthlyReportForm.setCat_Liquors(monthlyReportObj.getString("Cat_Liquors"));
                            monthlyReportForm.setCat_Refresher(monthlyReportObj.getString("Cat_Refresher"));
                            monthlyReportForm.setWeek(monthlyReportObj.getString("Week"));
                            arrayLisMonthlyreport.add(monthlyReportForm);
                        }
                        getMonthlyReport(arrayLisMonthlyreport);
                    } else {
                        llNodata.setVisibility(View.VISIBLE);
                    }
                    skLoading.setVisibility(View.GONE);
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

    private void getMonthlyReport(ArrayList<MonthlyReportForm> monthlyReportFormArrayList) {
      //  progressBar.setVisibility(View.GONE);
        skLoading.setVisibility(View.GONE);
        LineDataSet set1, set2;
        lineChartMonthlyReport.setVisibility(View.VISIBLE);

      /*  Legend legend = lineChartMonthlyReport.getLegend();
        legend.setYOffset(40);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextColor(Color.WHITE);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        legend.setTextSize(200);
        lineChartMonthlyReport.invalidate();*/

        entries.clear();
        for (int x = 0; x < monthlyReportFormArrayList.size(); x++) {
            entries.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat(monthlyReportFormArrayList.get(x).getCat_Veg())));
        }

        data.clear();
        for (int x = 0; x < monthlyReportFormArrayList.size(); x++) {
            data.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat(monthlyReportFormArrayList.get(x).getCat_Non_Veg())));
        }

        liquorsArray.clear();
        for (int x = 0; x < monthlyReportFormArrayList.size(); x++) {
            liquorsArray.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat(monthlyReportFormArrayList.get(x).getCat_Liquors())));
        }


        hotColdArray.clear();
        for (int x = 0; x < monthlyReportFormArrayList.size(); x++) {
            hotColdArray.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat(monthlyReportFormArrayList.get(x).getCat_Refresher())));
        }


        labels.clear();
        for (int x = 0; x <monthlyReportFormArrayList.size(); x++) {
            labels.add(monthlyReportFormArrayList.get(x).getWeek());
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


        ArrayList<ILineDataSet> lines = new ArrayList<ILineDataSet> ();

        LineDataSet lDataSet1 = new LineDataSet(entries, "Veg");
        lDataSet1.setColor(Color.rgb(193, 37, 82));
        lDataSet1.setDrawFilled(true);
        lDataSet1.setFillAlpha(100);
        lDataSet1.setCircleColor(Color.rgb(193, 37, 82));
        lDataSet1.setFillColor(Color.rgb(193, 37, 82));
        lines.add(lDataSet1);

        LineDataSet lDataSet2 = new LineDataSet(data, "Non Veg");
        lDataSet2.setColor(Color.rgb(106, 150, 31));
        lDataSet2.setCircleColor(Color.rgb(106, 150, 31));
        lDataSet2.setDrawFilled(true);
        lDataSet2.setFillAlpha(100);
        lDataSet2.setFillColor(Color.rgb(106, 150, 31));
        lines.add(lDataSet2);

        LineDataSet lDataSet3 = new LineDataSet(liquorsArray, "Liquors");
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
        lines.add(lDataSet4);

        Legend legend = lineChartMonthlyReport.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        lineChartMonthlyReport.getLegend().setWordWrapEnabled(true);

        legend.setDrawInside(true);

        CustomMarkerView mv = new CustomMarkerView (getActivity(), R.layout.rader_markview,labels);
        lineChartMonthlyReport.setMarkerView(mv);



        lineChartMonthlyReport.setData(new LineData(lines));
        lineChartMonthlyReport.animateY(3000);


    }

    private void init() {
        lineChartMonthlyReport= getView().findViewById(R.id.linechart);
        entries = new ArrayList<>();
        labels = new ArrayList<>();
        data=new ArrayList<>();
        hotColdArray=new ArrayList<>();
        liquorsArray=new ArrayList<>();
        llNodata=getView().findViewById(R.id.llMonthlyReportNoData);
        arrayLisMonthlyreport=new ArrayList<>();
        skLoading=getView().findViewById(R.id.skLoading);
        tvSelectMonth =getView().findViewById(R.id.tv_monthly_report);
    }
}
