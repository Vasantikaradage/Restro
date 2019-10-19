package com.restrosmart.restrohotel.Captain.Models;

import java.util.ArrayList;

public class LiquorCartModel {

    private int orderDetailId, liqId, liqQty;
    private String liqName, liqMLQty, LiqOrderMsg;
    private float liqPrice, liqQtyPrice;
    private ArrayList<ToppingsModel> toppingsModelArrayList, allToppingsModelArrayList;

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getLiqId() {
        return liqId;
    }

    public void setLiqId(int liqId) {
        this.liqId = liqId;
    }

    public int getLiqQty() {
        return liqQty;
    }

    public void setLiqQty(int liqQty) {
        this.liqQty = liqQty;
    }

    public String getLiqName() {
        return liqName;
    }

    public void setLiqName(String liqName) {
        this.liqName = liqName;
    }

    public String getLiqMLQty() {
        return liqMLQty;
    }

    public void setLiqMLQty(String liqMLQty) {
        this.liqMLQty = liqMLQty;
    }

    public String getLiqOrderMsg() {
        return LiqOrderMsg;
    }

    public void setLiqOrderMsg(String liqOrderMsg) {
        LiqOrderMsg = liqOrderMsg;
    }

    public float getLiqPrice() {
        return liqPrice;
    }

    public void setLiqPrice(float liqPrice) {
        this.liqPrice = liqPrice;
    }

    public float getLiqQtyPrice() {
        return liqQtyPrice;
    }

    public void setLiqQtyPrice(float liqQtyPrice) {
        this.liqQtyPrice = liqQtyPrice;
    }

    public ArrayList<ToppingsModel> getToppingsModelArrayList() {
        return toppingsModelArrayList;
    }

    public void setToppingsModelArrayList(ArrayList<ToppingsModel> toppingsModelArrayList) {
        this.toppingsModelArrayList = toppingsModelArrayList;
    }

    public ArrayList<ToppingsModel> getAllToppingsModelArrayList() {
        return allToppingsModelArrayList;
    }

    public void setAllToppingsModelArrayList(ArrayList<ToppingsModel> allToppingsModelArrayList) {
        this.allToppingsModelArrayList = allToppingsModelArrayList;
    }
}
