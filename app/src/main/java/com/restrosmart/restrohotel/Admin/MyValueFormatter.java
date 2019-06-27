package com.restrosmart.restrohotel.Admin;


import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

class MyValueFormatter implements ValueFormatter {

    private final DecimalFormat mFormat;
    private String suffix;

    public MyValueFormatter() {
        mFormat = new DecimalFormat("###,###,###,##0.0");
        this.suffix = suffix;
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return mFormat.format(value) ;
    }
}
