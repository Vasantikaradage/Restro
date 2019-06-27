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

    private  String[] months={"1 week","2 week","3 week","4 week"};

    private  String[] sale={"100","200","300","50"};

    private  String[] sale1={"200","500","600","500"};




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

        entries.clear();
        for (int x = 0; x < sale.length; x++) {
            entries.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat(sale[x])));
        }

        data.clear();
        for (int x = 0; x < sale1.length; x++) {
            data.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat(sale1[x])));
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


        ArrayList<ILineDataSet> lines = new ArrayList<ILineDataSet> ();

        LineDataSet lDataSet1 = new LineDataSet(entries, "DataSet1");
        lDataSet1.setColor(Color.rgb(106, 150, 31));
        lDataSet1.setCircleColor(Color.rgb(106, 150, 31));
        lines.add(lDataSet1);

        LineDataSet lDataSet2 = new LineDataSet(data, "DataSet2");
        lDataSet2.setColor(Color.rgb(193, 37, 82));
        lDataSet2.setCircleColor(Color.rgb(193, 37, 82));
        lines.add(lDataSet2);


        lineChartMonthlyReport.setData(new LineData(lines));

    }

    private void init() {
        lineChartMonthlyReport= getView().findViewById(R.id.linechart);
        entries = new ArrayList<>();
        labels = new ArrayList<>();
        data=new ArrayList<>();
        progressBar=getView().findViewById(R.id.progressBar);

    }

}
