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
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class FragmentTabMonthlyReport extends Fragment {

    private LineChart lineChartMonthlyReport;
    private ProgressBar progressBar;

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
        
        getMonthlyReport();
    }

    private void getMonthlyReport() {
        progressBar.setVisibility(View.GONE);

        LineDataSet set1, set2;

      /*  Legend legend = lineChartMonthlyReport.getLegend();
        legend.setYOffset(40);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextColor(Color.WHITE);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        legend.setTextSize(200);
        lineChartMonthlyReport.invalidate();*/

        entries.clear();
        for (int x = 0; x < veg.length; x++) {
            entries.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat(veg[x])));
        }

        data.clear();
        for (int x = 0; x < nonVeg.length; x++) {
            data.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat(nonVeg[x])));
        }

        liquorsArray.clear();
        for (int x = 0; x < Liquors.length; x++) {
            liquorsArray.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat(Liquors[x])));
        }


        hotColdArray.clear();
        for (int x = 0; x < hotCold.length; x++) {
            hotColdArray.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat(hotCold[x])));
        }


        labels.clear();
        for (int x = 0; x < months.length; x++) {
            labels.add(months[x]);
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
        progressBar=getView().findViewById(R.id.progressBar);

    }

}
