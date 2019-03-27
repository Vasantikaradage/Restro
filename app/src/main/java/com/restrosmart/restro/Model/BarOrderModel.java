package com.restrosmart.restro.Model;

import java.util.ArrayList;

public class BarOrderModel {

    private int BarOrderNo, BarTableNo;
    private ArrayList<BarOrderSpecificModel> arrayList;

    public int getBarOrderNo() {
        return BarOrderNo;
    }

    public void setBarOrderNo(int barOrderNo) {
        BarOrderNo = barOrderNo;
    }

    public int getBarTableNo() {
        return BarTableNo;
    }

    public void setBarTableNo(int barTableNo) {
        BarTableNo = barTableNo;
    }

    public ArrayList<BarOrderSpecificModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<BarOrderSpecificModel> arrayList) {
        this.arrayList = arrayList;
    }
}
