package com.restrosmart.restrohotel.Admin;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class FragmentTabWeeklyReport extends Fragment {

    private BarChart barChart;
    private ProgressBar progressBar;
    private LinearLayout llWeeklyReportNoData;
    int size=6;
    private ArrayList<String> labels;

    private String[] Days = {"Mon", "Tue", "Wen", "Thu", "Fri", "Sat", "Sun"};


    private  String[] veg={"10","20","30","40","50","60","70"};
    private  String[] novveg={"20","30","30","40","50","70","75"};
    private  String[] liq={"10","20","30","40","50","60","70"};
    private  String[] hotCold={"20","30","30","40","50","70","75"};



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
        
        getYearlyReport();
    }

    private void getYearlyReport() {

        progressBar.setVisibility(View.GONE);
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();



        for (int i = 0; i < size + 1; i++) {
            float mult = (size + 1);

            float val1 = Float.parseFloat(veg[i]);
            float val2 = Float.parseFloat(novveg[i]);
            float val3 = Float.parseFloat(liq[i]);
            float val4 = Float.parseFloat(hotCold[i]);


           /* float val1 = (float) (Math.random() * mult) + mult / 3;
            float val2 = (float) (Math.random() * mult) + mult / 3;
            float val3 = (float) (Math.random() * mult) + mult / 3;
            float val4 = (float) (Math.random() * mult) + mult / 3;*/

            yVals1.add(new BarEntry(
                    i,
                    new float[]{val1, val2,val3,val4},
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
                    Color.rgb(79,129,189),
                    Color.rgb(245, 199, 0)};


            set1.setColors(COLORFUL_COLORS);

            set1.setStackLabels(new String[]{"Veg", "Nov Veg", "Liquors","Hot and Cold"});

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);


            labels.clear();
            for (int x = 0; x < Days.length; x++) {
                labels.add(Days[x]);
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
            data.setBarWidth(0.9f);
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
        labels=new ArrayList<>();
        barChart =getView().findViewById(R.id.barchart);
        progressBar=getView().findViewById(R.id.progressBar);
        llWeeklyReportNoData =getView().findViewById(R.id.llWeeklyReportNoData);
    }



}
