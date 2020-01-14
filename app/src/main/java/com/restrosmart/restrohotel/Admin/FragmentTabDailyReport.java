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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.ORDER_DETAILS;

@SuppressLint("ValidFragment")
public class FragmentTabDailyReport extends Fragment {

    private List<PieEntry> entries;
    private ArrayList<String> labels;

    private PieChart pieChartDailyReport;
    private View view;
    private LinearLayout llPlacementNoData;
    // private ProgressBar progressBar;
    private ArrayList<DailyReportForm> arrayListDailyreport;
    private Button btnSelectDate;
    private Calendar mcurrentDate;
    private String getStartDate;

    private String[] dailyreport = {"1000", "700", "200", "500"};
    private String[] daily = {"Veg", "Non Veg", "Liquors", "Hot and Cold"};
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private SpinKitView spinKitView;
    private  PieDataSet dataset;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_daily_report, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        llPlacementNoData.setVisibility(View.GONE);
        spinKitView.setVisibility(View.GONE);

        btnSelectDate.setOnClickListener(new View.OnClickListener() {
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

                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
                        SimpleDateFormat sdf1 = new SimpleDateFormat(format, Locale.ENGLISH);
                        getStartDate = sdf.format(myCalendar.getTime());
                        btnSelectDate.setText(sdf1.format(myCalendar.getTime()));

                        initRetrofitCallback();
                        spinKitView.setVisibility(View.VISIBLE);
                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                        mRetrofitService.retrofitData(ORDER_DETAILS, (service.Order(1)));

                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                String formattedDate = df.format(c);
                try {
                    Date d = df.parse(formattedDate);
                    mDatePicker.getDatePicker().setMinDate(d.getTime());

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mDatePicker.show();
            }
        });

    }

    private void initRetrofitCallback() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                // String strObject=jsonObject.toString();
                llPlacementNoData.setVisibility(View.GONE);
                String strObject = "{\"status\":\"1\",\"DailyReport\":[{\"Category_Name\":\"Veg\",\"Sale_Count\":1000},{\"Category_Name\":\"Non Veg\",\"Sale_Count\":800},{\"Category_Name\":\"Liquors\",\"Sale_Count\":300},{\"Category_Name\":\"Hot and Cold\",\"Sale_Count\":500}]}";
                try {
                    JSONObject object = new JSONObject(strObject);
                    int status = object.getInt("status");

                    if (status == 1) {
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
                        llPlacementNoData.setVisibility(View.VISIBLE);
                    }
                    spinKitView.setVisibility(View.GONE);
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
        btnSelectDate = view.findViewById(R.id.tv_dp_select_date);

        pieChartDailyReport = view.findViewById(R.id.piechart);
        llPlacementNoData = view.findViewById(R.id.llDailyReportNoData);
        spinKitView = view.findViewById(R.id.skLoading);
        arrayListDailyreport = new ArrayList<>();

    }

}
