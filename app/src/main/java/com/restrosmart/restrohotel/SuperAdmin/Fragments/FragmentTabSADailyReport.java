package com.restrosmart.restrohotel.SuperAdmin.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.restrosmart.restrohotel.Admin.DailyReportValueFormatter;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentTabSADailyReport extends Fragment {

    private List<PieEntry> entries;
    private ArrayList<String> labels;

    private PieChart pieChartDailyReport;
    private View view;
    private LinearLayout llPlacementNoData;
    private ProgressBar progressBar;
    private  ArrayList<String> arrayListDailyreport;

    private  String[] dailyreport={"1000","700","200","500"};
    private  String[] daily={"Veg","Non Veg","Liquors","Hot and Cold"};



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

        for(int i=0; i<dailyreport.length; i++){
            arrayListDailyreport.add(dailyreport[i]);
        }
        selectHotel();
        getDailyReportData();
    }

    private void selectHotel() {
    }


    private void getDailyReportData() {

        entries.clear();
        progressBar.setVisibility(View.GONE);
        for (int x = 0; x < arrayListDailyreport.size(); x++) {
            entries.add(new PieEntry(Float.parseFloat(arrayListDailyreport.get(x).toString() + "f"), daily[x]));
        }

        int[] COLORFUL_COLORS = {
                Color.rgb(106, 150, 31),
                Color.rgb(193, 37, 82),
                Color.rgb(79,129,189),
                Color.rgb(245, 199, 0)};

        PieDataSet dataset = new PieDataSet(entries, "");
        pieChartDailyReport.animateY(5000);
        pieChartDailyReport.setTouchEnabled(true);

        dataset.setValueFormatter(new DailyReportValueFormatter());
        dataset.setColors(COLORFUL_COLORS);
        dataset.setValueTextColor(Color.parseColor("#FFFFFFFF"));
        pieChartDailyReport.setDrawSliceText(false);
        pieChartDailyReport.setDrawEntryLabels(true);




       /* Legend legend = pieChartDailyReport.getLegend();

        labels.clear();
        labels.add("Veg");
        labels.add("Non Veg");
        labels.add("liquors");
        labels.add("Hot & Cold");
        legend.setEnabled(true);

        legend.setComputedLabels(labels);*/
        /*barChartAdmission.setDragEnabled(true);
        barChartAdmission.setScaleEnabled(true);
        barChartAdmission.setDoubleTapToZoomEnabled(true);*/

        pieChartDailyReport.animateX(1000);
        pieChartDailyReport.setDescription("");

        pieChartDailyReport.setDescriptionColor(getContext().getResources().getColor(R.color.blue));
        // barChartAdmission.setDescriptionPosition(120, 50);
        //barChartAdmission.setNoDataText("Data not available");

        /*XAxis xAxis = barChartAdmission.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(true);
        xAxis.setTypeface(Typeface.DEFAULT_BOLD);
        xAxis.setTextColor(getContext().getResources().getColor(R.color.lightred));*/

       /* YAxis leftAxis = barChartAdmission.getAxisLeft();
        leftAxis.setLabelCount(8, false);

        YAxis rightAxis = barChartAdmission.getAxisRight();
        rightAxis.setLabelCount(8, false);*/

      /*  Legend legend = pieChartDailyReport.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);*/
        //  pieChartDailyReport.getLegend().setWordWrapEnabled(true);

        //legend.setWordWrapEnabled(true);



        PieData data = new PieData(dataset);
        data.setValueTextSize(16f);
        pieChartDailyReport.setData(data);

        pieChartDailyReport.setCenterText(getString(R.string.daily_report));
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
        llPlacementNoData = view.findViewById(R.id.llDailyReportNoData);
        progressBar = view.findViewById(R.id.progressBar);
        arrayListDailyreport=new ArrayList<>();

    }



}
