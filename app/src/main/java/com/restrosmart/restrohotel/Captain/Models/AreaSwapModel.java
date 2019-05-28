package com.restrosmart.restrohotel.Captain.Models;

import java.util.ArrayList;

public class AreaSwapModel {

    private String areaName;
    private ArrayList<TableSwapModel> tableSwapModelArrayList;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public ArrayList<TableSwapModel> getTableSwapModelArrayList() {
        return tableSwapModelArrayList;
    }

    public void setTableSwapModelArrayList(ArrayList<TableSwapModel> tableSwapModelArrayList) {
        this.tableSwapModelArrayList = tableSwapModelArrayList;
    }
}
