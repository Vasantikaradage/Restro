package com.restrosmart.restrohotel.Captain.Models;

import java.util.ArrayList;

public class OrderStatusOrderList {

    private int oId;
    private String orderId, orderStatus;
    private ArrayList<OrderStatusOrders> orderStatusOrders;

    public int getoId() {
        return oId;
    }

    public void setoId(int oId) {
        this.oId = oId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public ArrayList<OrderStatusOrders> getOrderStatusOrders() {
        return orderStatusOrders;
    }

    public void setOrderStatusOrders(ArrayList<OrderStatusOrders> orderStatusOrders) {
        this.orderStatusOrders = orderStatusOrders;
    }

    @Override
    public String toString() {
        return orderId;
    }
}
