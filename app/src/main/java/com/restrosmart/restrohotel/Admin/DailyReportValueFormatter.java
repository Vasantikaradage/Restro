package com.restrosmart.restrohotel.Admin;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

class DailyReportValueFormatter implements ValueFormatter {

    private DecimalFormat mFormat;

    public DailyReportValueFormatter() {
        mFormat = new DecimalFormat("###,###,###"); // use no decimals
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        if (value <= 0) return "";

        return mFormat.format(value);
    }
}