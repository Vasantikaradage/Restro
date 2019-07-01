package com.restrosmart.restrohotel.Admin;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class FragmentTabYearReport extends Fragment {

    private LineChart lineChartMonthlyReport;
    private ProgressBar progressBar;

    XAxis xAxis;
    YAxis yVals1,yVals2;

    private ArrayList<Entry> arrayListVeg;
    private ArrayList<String> labels;
    private  ArrayList<Entry> arrayListNonveg;
    private  ArrayList<Entry> arrayListLiquors;
    private  ArrayList<Entry> arrayListHotCold;

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

        getYearlyReport();
    }

    private void getYearlyReport() {
        progressBar.setVisibility(View.GONE);

        LineDataSet set1, set2;

      /*  Legend legend = lineChartMonthlyReport.getLegend();
        legend.setYOffset(40);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextColor(Color.WHITE);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        legend.setTextSize(200);
        lineChartMonthlyReport.invalidate();*/

        arrayListVeg.clear();
        for (int x = 0; x < arrayVeg.length; x++) {
            arrayListVeg.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat(arrayVeg[x])));
        }

        arrayListNonveg.clear();
        for (int x = 0; x < arrayNonVeg.length; x++) {
            arrayListNonveg.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat(arrayNonVeg[x])));
        }
        arrayListLiquors.clear();
        for (int x = 0; x < arrayLiquors.length; x++) {
            arrayListLiquors.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat(arrayLiquors[x])));
        }

        arrayListHotCold.clear();
        for (int x = 0; x < arrayHotCold.length; x++) {
            arrayListHotCold.add(new Entry(Float.parseFloat(x + "f"), Float.parseFloat(arrayHotCold[x])));
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

                //   Log.d("asa", "arrayListNonveg X" + labels);
                return labels.get((int) value % labels.size());
            }
        });

        //lineChartMonthlyReport.getAxisLeft().setDrawGridLines(false);
        // lineChartMonthlyReport.getXAxis().setDrawGridLines(false);
        lineChartMonthlyReport.setDescription("Months");

        ArrayList<ILineDataSet> lines = new ArrayList<ILineDataSet> ();

        LineDataSet lDataSet1 = new LineDataSet(arrayListVeg, "DataSet1");
        lDataSet1.setColor(Color.rgb(106, 150, 31));
      //  lDataSet1.setDrawFilled(true);
        lDataSet1.setCircleColor(Color.rgb(106, 150, 31));
       //  lDataSet1.setFillColor(Color.rgb(106, 150, 31));
        lines.add(lDataSet1);

        LineDataSet lDataSet2 = new LineDataSet(arrayListNonveg, "DataSet2");
        lDataSet2.setColor(Color.rgb(193, 37, 82));
        lDataSet2.setCircleColor(Color.rgb(193, 37, 82));
       // lDataSet2.setDrawFilled(true);
       //  lDataSet2.setFillColor(Color.rgb(193, 37, 82));
        lines.add(lDataSet2);

        LineDataSet lDataSet3 = new LineDataSet(arrayListLiquors, "DataSet3");
        lDataSet3.setColor(Color.rgb(79,129,189));
        lDataSet3.setCircleColor(Color.rgb(79,129,189));
        // lDataSet2.setDrawFilled(true);
        //  lDataSet2.setFillColor(Color.rgb(193, 37, 82));
        lines.add(lDataSet3);

        LineDataSet lDataSet4 = new LineDataSet(arrayListHotCold, "DataSet4");
        lDataSet4.setColor(Color.rgb(245, 199, 0));
        lDataSet4.setCircleColor(Color.rgb(245, 199, 0));
        // lDataSet2.setDrawFilled(true);
        //  lDataSet2.setFillColor(Color.rgb(193, 37, 82));
        lines.add(lDataSet4);


        lineChartMonthlyReport.setData(new LineData(lines));
    }

    private void init() {

        lineChartMonthlyReport= getView().findViewById(R.id.linechart);
        arrayListVeg = new ArrayList<>();

        arrayListLiquors=new ArrayList<>();
        arrayListHotCold=new ArrayList<>();
        labels = new ArrayList<>();
        arrayListNonveg =new ArrayList<>();
        progressBar=getView().findViewById(R.id.progressBar);
    }

}
