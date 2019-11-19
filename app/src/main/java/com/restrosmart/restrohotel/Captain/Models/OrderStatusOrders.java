package com.restrosmart.restrohotel.Captain.Models;

import java.util.ArrayList;

public class OrderStatusOrders {

    private String orderDetailId;
    private String orderMenuId;
    private String orderMenuName;
    private float orderMenuPrice, totalMenuPrice;
    private int orderMenuQty;
    private ArrayList<ToppingsModel> toppingsModelArrayList;

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getOrderMenuId() {
        return orderMenuId;
    }

    public void setOrderMenuId(String orderMenuId) {
        this.orderMenuId = orderMenuId;
    }

    public String getOrderMenuName() {
        return orderMenuName;
    }

    public void setOrderMenuName(String orderMenuName) {
        this.orderMenuName = orderMenuName;
    }

    public float getOrderMenuPrice() {
        return orderMenuPrice;
    }

    public void setOrderMenuPrice(float orderMenuPrice) {
        this.orderMenuPrice = orderMenuPrice;
    }

    public float getTotalMenuPrice() {
        return totalMenuPrice;
    }

    public void setTotalMenuPrice(float totalMenuPrice) {
        this.totalMenuPrice = totalMenuPrice;
    }

    public int getOrderMenuQty() {
        return orderMenuQty;
    }

    public void setOrderMenuQty(int orderMenuQty) {
        this.orderMenuQty = orderMenuQty;
    }

    public ArrayList<ToppingsModel> getToppingsModelArrayList() {
        return toppingsModelArrayList;
    }

    public void setToppingsModelArrayList(ArrayList<ToppingsModel> toppingsModelArrayList) {
        this.toppingsModelArrayList = toppingsModelArrayList;
    }
}
