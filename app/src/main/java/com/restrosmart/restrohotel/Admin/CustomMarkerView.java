package com.restrosmart.restrohotel.Admin;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.restrosmart.restrohotel.R;

import java.util.ArrayList;

public  class CustomMarkerView extends MarkerView {

    private TextView tvContent;

    private  ArrayList<String> labels;

    public CustomMarkerView(Context context, int layoutResource, ArrayList<String> labels) {
        super(context, layoutResource);
        // this markerview only displays a textview

        this.labels=labels;
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {


        String xValue = labels.get((int) e.getX());

        // String xVal= (String) labels.get(e.toString());
        tvContent.setText("" +"("+ (xValue)+", "+e.getY()+")");
    }

    @Override
    public int getXOffset(float xpos) {
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset(float ypos) {
        return -getHeight();
    }
}
