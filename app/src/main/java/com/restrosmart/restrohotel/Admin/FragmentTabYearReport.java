package com.restrosmart.restrohotel.Admin;

import android.annotation.SuppressLint;
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
import com.restrosmart.restrohotel.Model.YearlyReportForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.ORDER_DETAILS;

@SuppressLint("ValidFragment")
public class FragmentTabYearReport extends Fragment {

    private LineChart lineChartYearlyReport;
    private SpinKitView skLoading;

    XAxis xAxis;
    YAxis yVals1,yVals2;

    private ArrayList<Entry> arrayListVeg;
    private ArrayList<String> labels;
    private  ArrayList<Entry> arrayListNonveg;
    private  ArrayList<Entry> arrayListLiquors;
    private  ArrayList<Entry> arrayListHotCold;
    private TextView tvSelectYear;
    private  RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private LinearLayout llNodata;


    private ArrayList<YearlyReportForm> yearlyReportFormArrayList;
    private  String[] months={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

    private  String[] arrayVeg ={"100","200","100","250","100","200","100","250","100","200","100","250"};

    private  String[] arrayNonVeg ={"200","500","600","300","200","500","800","300","200","500","200","300"};

    private  String[] arrayLiquors ={"200","300","400","350","200","300","200","350","200","400","200","350"};

    private  String[] arrayHotCold ={"100","400","300","200","100","400","700","200","300","400","100","200"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_yearly_report, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        skLoading.setVisibility(View.GONE);

        tvSelectYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar mcurrentDate = Calendar.getInstance();
                final int mYear = mcurrentDate.get(Calendar.YEAR);
                final int mMonth = mcurrentDate.get(Calendar.MONTH);
                final int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                android.app.DatePickerDialog mDatePicker = new android.app.DatePickerDialog(getActivity(), new android.app.DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);

                        String format = "yyyy";
                        SimpleDateFormat sdf1 = new SimpleDateFormat(format, Locale.ENGLISH);
                        tvSelectYear.setText(sdf1.format(myCalendar.getTime()));

                        initRetrofitCallback();
                        skLoading.setVisibility(View.VISIBLE);
                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                        mRetrofitService.retrofitData(ORDER_DETAILS, (service.Order(1)));


                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

        getYearlyReport(yearlyReportFormArrayList);
    }

    private void initRetrofitCallback() {
        mResultCallBack=new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                // String strObject=jsonObject.toString();
                llNodata.setVisibility(View.GONE);
                String strObject = "{\n" +
                        "  \"status\": \"1\",\n" +
                        "  \"yearlyReport\": [\n" +
                        "    {\n" +
                        "      \"Cat_Veg\": \"100\",\n" +
                        "      \"Cat_Non_Veg\": \"305\",\n" +
                        "      \"Cat_Liquors\": \"500\",\n" +
                        "      \"Cat_Refresher\": \"770\",\n" +
                        "      \"Month\": \"Jan\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"Cat_Veg\": \"300\",\n" +
                        "      \"Cat_Non_Veg\": \"480\",\n" +
                        "      \"Cat_Liquors\": \"600\",\n" +
                        "      \"Cat_Refresher\": \"300\",\n" +
                        "      \"Month\": \"Feb\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"Cat_Veg\": \"200\",\n" +
                        "      \"Cat_Non_Veg\": \"480\",\n" +
                        "      \"Cat_Liquors\": \"650\",\n" +
                        "      \"Cat_Refresher\": \"100\",\n" +
                        "      \"Month\": \"Mar\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"Cat_Veg\": \"500\",\n" +
                        "      \"Cat_Non_Veg\": \"200\",\n" +
                        "      \"Cat_Liquors\": \"350\",\n" +
                        "      \"Cat_Refresher\": \"200\",\n" +
                        "      \"Month\": \"Apr\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"Cat_Veg\": \"230\",\n" +
                        "      \"Cat_Non_Veg\": \"345\",\n" +
                        "      \"Cat_Liquors\": \"234\",\n" +
                        "      \"Cat_Refresher\": \"678\",\n" +
                        "      \"Month\": \"May\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"Cat_Veg\": \"123\",\n" +
                        "      \"Cat_Non_Veg\": \"234\",\n" +
                        "      \"Cat_Liquors\": \"345\",\n" +
                        "      \"Cat_Refresher\": \"456\",\n" +
                        "      \"Month\": \"Jun\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"Cat_Veg\": \"234\",\n" +
                        "      \"Cat_Non_Veg\": \"345\",\n" +
                        "      \"Cat_Liquors\": \"456\",\n" +
                        "      \"Cat_Refresher\": \"567\",\n" +
                        "      \"Month\": \"Jul\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"Cat_Veg\": \"345\",\n" +
                        "      \"Cat_Non_Veg\": \"456\",\n" +
                        "      \"Cat_Liquors\": \"567\",\n" +
                        "      \"Cat_Refresher\": \"100\",\n" +
                        "      \"Month\": \"Aug\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"Cat_Veg\": \"134\",\n" +
                        "      \"Cat_Non_Veg\": \"234\",\n" +
                        "      \"Cat_Liquors\": \"100\",\n" +
                        "      \"Cat_Refresher\": \"233\",\n" +
                        "      \"Month\": \"Spt\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"Cat_Veg\": \"300\",\n" +
                        "      \"Cat_Non_Veg\": \"456\",\n" +
                        "      \"Cat_Liquors\": \"122\",\n" +
                        "      \"Cat_Refresher\": \"111\",\n" +
                        "      \"Month\": \"Oct\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"Cat_Veg\": \"222\",\n" +
                        "      \"Cat_Non_Veg\": \"333\",\n" +
                        "      \"Cat_Liquors\": \"645\",\n" +
                        "      \"Cat_Refresher\": \"100\",\n" +
                        "      \"Month\": \"Nov\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"Cat_Veg\": \"262\",\n" +
                        "      \"Cat_Non_Veg\": \"133\",\n" +
                        "      \"Cat_Liquors\": \"145\",\n" +
                        "      \"Cat_Refresher\": \"210\",\n" +
                        "      \"Month\": \"Dec\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}";
                try {
                    JSONObject object = new JSONObject(strObject);
                    int status = object.getInt("status");

                    if (status == 1) {
                        JSONArray jsonArray = object.getJSONArray("yearlyReport");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject yearlReportObj = jsonArray.getJSONObject(i);
                            YearlyReportForm yearlyReportForm = new YearlyReportForm();
                            yearlyReportForm.setCat_Veg(yearlReportObj.getString("Cat_Veg"));
                            yearlyReportForm.setCat_Non_Veg(yearlReportObj.getString("Cat_Non_Veg"));
                            yearlyReportForm.setCat_Liquors(yearlReportObj.getString("Cat_Liquors"));
                            yearlyReportForm.setCat_Refresher(yearlReportObj.getString("Cat_Refresher"));
                            yearlyReportForm.setMonth(yearlReportObj.getString("Month"));
                            yearlyReportFormArrayList.add(yearlyReportForm);
                        }
                        getYearlyReport(yearlyReportFormArrayList);
                    } else {
                        llNodata.setVisibility(View.VISIBLE);
                    }
                    skLoading.setVisibility(View.GONE);
                } catch (Exception e) {

                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("","requestId"+requestId);
                Log.d("","retrofitError"+error);


            }
        };
    }

    private void getYearlyReport(ArrayList<YearlyReportForm> yearlyReportFormArrayList) {
        skLoading.setVisibility(View.GONE);
        lineChartYearlyReport.setVisibility(View.VISIBLE);
        LineDataSet set1, set2;

      /*  Legend legend = lineChartYearlyReport.getLegend();
        legend.setYOffset(40);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextColor(Color.WHITE);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        legend.setTextSize(200);
        lineChartYearlyReport.invalidate();*/

        arrayListVeg.clear();
        for (int x = 0; x < yearlyReportFormArrayList.size(); x++) {
            arrayListVeg.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat( yearlyReportFormArrayList.get(x).getCat_Veg())));
        }

        arrayListNonveg.clear();
        for (int x = 0; x <  yearlyReportFormArrayList.size(); x++) {
            arrayListNonveg.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat(yearlyReportFormArrayList.get(x).getCat_Non_Veg())));
        }
        arrayListLiquors.clear();
        for (int x = 0; x <  yearlyReportFormArrayList.size(); x++) {
            arrayListLiquors.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat(yearlyReportFormArrayList.get(x).getCat_Liquors())));
        }

        arrayListHotCold.clear();
        for (int x = 0; x <  yearlyReportFormArrayList.size(); x++) {
            arrayListHotCold.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat(yearlyReportFormArrayList.get(x).getCat_Refresher())));
        }


        labels.clear();
        for (int x = 0; x <  yearlyReportFormArrayList.size(); x++) {
            labels.add(yearlyReportFormArrayList.get(x).getMonth());
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

        LineDataSet lDataSet1 = new LineDataSet(arrayListVeg, "Veg");
        lDataSet1.setColor(Color.rgb(106, 150, 31));
      //  lDataSet1.setDrawFilled(true);
        lDataSet1.setCircleColor(Color.rgb(106, 150, 31));
       //  lDataSet1.setFillColor(Color.rgb(106, 150, 31));

        lDataSet1.setValueFormatter(new LineValueFormatter());
        lines.add(lDataSet1);

        LineDataSet lDataSet2 = new LineDataSet(arrayListNonveg, "Non Veg");
        lDataSet2.setColor(Color.rgb(193, 37, 82));
        lDataSet2.setCircleColor(Color.rgb(193, 37, 82));
       // lDataSet2.setDrawFilled(true);
       //  lDataSet2.setFillColor(Color.rgb(193, 37, 82));
        lines.add(lDataSet2);

        LineDataSet lDataSet3 = new LineDataSet(arrayListLiquors, "Liquors");
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
        lines.add(lDataSet4);
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
        arrayListVeg = new ArrayList<>();

        arrayListLiquors=new ArrayList<>();
        arrayListHotCold=new ArrayList<>();
        labels = new ArrayList<>();
        arrayListNonveg =new ArrayList<>();
        skLoading=getView().findViewById(R.id.skLoading);
        tvSelectYear =getView().findViewById(R.id.tv_year_select);
        yearlyReportFormArrayList=new ArrayList<>();
        llNodata=getView().findViewById(R.id.yearlyReportNoData);
    }

}
