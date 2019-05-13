package com.restrosmart.restrohotel.Captain.Models;

import java.util.ArrayList;

public class AreaTableModel {

    private int areaId;
    private String areaName;
    private ArrayList<ScanTableModel> scanTableModelArrayList;

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public ArrayList<ScanTableModel> getScanTableModelArrayList() {
        return scanTableModelArrayList;
    }

    public void setScanTableModelArrayList(ArrayList<ScanTableModel> scanTableModelArrayList) {
        this.scanTableModelArrayList = scanTableModelArrayList;
    }
}
