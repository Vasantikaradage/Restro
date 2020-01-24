package com.restrosmart.restrohotel.Admin;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.AxisValueFormatter;

public class IAxisValueFormatter implements AxisValueFormatter {
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        if(value <= 0) return "";
        return  null;
    }

    @Override
    public int getDecimalDigits() {
        return 0;
    }
}
