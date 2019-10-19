package com.restrosmart.restrohotel.Captain.Models;

import java.util.ArrayList;

public class FoodSubMenuModel {

    private int submenuId;
    private String submenuName;
    private ArrayList<FoodMenuModel> arrayList;

    public int getSubmenuId() {
        return submenuId;
    }

    public void setSubmenuId(int submenuId) {
        this.submenuId = submenuId;
    }

    public String getSubmenuName() {
        return submenuName;
    }

    public void setSubmenuName(String submenuName) {
        this.submenuName = submenuName;
    }

    public ArrayList<FoodMenuModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<FoodMenuModel> arrayList) {
        this.arrayList = arrayList;
    }
}
