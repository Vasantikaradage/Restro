package com.restrosmart.restrohotel.Model;

import java.util.ArrayList;

public class TableForm {
    private  int areaId,tableCount;
    private  String areaName;
    private  int Area_Status;
    private ArrayList<TableFormId> arrayTableFormIds;


    public int getArea_Status() {
        return Area_Status;
    }

    public void setArea_Status(int area_Status) {
        Area_Status = area_Status;
    }

    public ArrayList<TableFormId> getArrayTableFormIds() {
        return arrayTableFormIds;
    }

    public void setArrayTableFormIds(ArrayList<TableFormId> arrayTableFormIds) {
        this.arrayTableFormIds = arrayTableFormIds;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getTableCount() {
        return tableCount;
    }

    public void setTableCount(int tableCount) {
        this.tableCount = tableCount;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
