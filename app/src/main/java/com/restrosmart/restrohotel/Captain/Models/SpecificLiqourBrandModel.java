package com.restrosmart.restrohotel.Captain.Models;

import java.util.ArrayList;

public class SpecificLiqourBrandModel {

    private int menuId;
    private String menuName, menuImage;
    private ArrayList<FlavoursModel> flavoursModelArrayList;
    private ArrayList<ToppingsModel> toppingsModelArrayList;

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuImage() {
        return menuImage;
    }

    public void setMenuImage(String menuImage) {
        this.menuImage = menuImage;
    }

    public ArrayList<FlavoursModel> getFlavoursModelArrayList() {
        return flavoursModelArrayList;
    }

    public void setFlavoursModelArrayList(ArrayList<FlavoursModel> flavoursModelArrayList) {
        this.flavoursModelArrayList = flavoursModelArrayList;
    }

    public ArrayList<ToppingsModel> getToppingsModelArrayList() {
        return toppingsModelArrayList;
    }

    public void setToppingsModelArrayList(ArrayList<ToppingsModel> toppingsModelArrayList) {
        this.toppingsModelArrayList = toppingsModelArrayList;
    }
}
