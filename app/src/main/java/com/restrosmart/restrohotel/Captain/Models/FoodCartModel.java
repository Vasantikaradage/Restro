package com.restrosmart.restrohotel.Captain.Models;

import java.util.ArrayList;

public class FoodCartModel {

    private int orderDetailId, menuId, menuQty;
    private String menuName, menuOrderMsg;
    private float menuPrice, menuQtyPrice;
    private ArrayList<ToppingsModel> toppingsModelArrayList;
    private ArrayList<ToppingsModel> AllToppingsModelArrayList;

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getMenuQty() {
        return menuQty;
    }

    public void setMenuQty(int menuQty) {
        this.menuQty = menuQty;
    }

    public String getMenuOrderMsg() {
        return menuOrderMsg;
    }

    public void setMenuOrderMsg(String menuOrderMsg) {
        this.menuOrderMsg = menuOrderMsg;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public float getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(float menuPrice) {
        this.menuPrice = menuPrice;
    }

    public float getMenuQtyPrice() {
        return menuQtyPrice;
    }

    public void setMenuQtyPrice(float menuQtyPrice) {
        this.menuQtyPrice = menuQtyPrice;
    }

    public ArrayList<ToppingsModel> getToppingsModelArrayList() {
        return toppingsModelArrayList;
    }

    public void setToppingsModelArrayList(ArrayList<ToppingsModel> toppingsModelArrayList) {
        this.toppingsModelArrayList = toppingsModelArrayList;
    }

    public ArrayList<ToppingsModel> getAllToppingsModelArrayList() {
        return AllToppingsModelArrayList;
    }

    public void setAllToppingsModelArrayList(ArrayList<ToppingsModel> allToppingsModelArrayList) {
        AllToppingsModelArrayList = allToppingsModelArrayList;
    }
}
