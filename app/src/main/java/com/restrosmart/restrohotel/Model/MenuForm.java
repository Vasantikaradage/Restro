package com.restrosmart.restrohotel.Model;

import java.util.ArrayList;

public  class MenuForm {
    private String menuName,menuDisp,liqMLQty;
    private  int menuPrice,menuQty;

    private ArrayList<ToppingsForm> arrayListToppings;

    public String getLiqMLQty() {
        return liqMLQty;
    }

    public void setLiqMLQty(String liqMLQty) {
        this.liqMLQty = liqMLQty;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuDisp() {
        return menuDisp;
    }

    public void setMenuDisp(String menuDisp) {
        this.menuDisp = menuDisp;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    public int getMenuQty() {
        return menuQty;
    }

    public void setMenuQty(int menuQty) {
        this.menuQty = menuQty;
    }

    public ArrayList<ToppingsForm> getArrayListToppings() {
        return arrayListToppings;
    }

    public void setArrayListToppings(ArrayList<ToppingsForm> arrayListToppings) {
        this.arrayListToppings = arrayListToppings;
    }
}
