package com.restrosmart.restrohotel.Captain.Models;

import java.util.ArrayList;

public class FoodMenuModel {

    private int menuId, menuTaste;
    private String menuName, menuDesc, menuImage;
    private float menuPrice;
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

    public String getMenuDesc() {
        return menuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }

    public String getMenuImage() {
        return menuImage;
    }

    public void setMenuImage(String menuImage) {
        this.menuImage = menuImage;
    }

    public float getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(float menuPrice) {
        this.menuPrice = menuPrice;
    }

    public int getMenuTaste() {
        return menuTaste;
    }

    public void setMenuTaste(int menuTaste) {
        this.menuTaste = menuTaste;
    }

    public ArrayList<ToppingsModel> getToppingsModelArrayList() {
        return toppingsModelArrayList;
    }

    public void setToppingsModelArrayList(ArrayList<ToppingsModel> toppingsModelArrayList) {
        this.toppingsModelArrayList = toppingsModelArrayList;
    }
}
