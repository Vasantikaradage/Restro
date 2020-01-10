package com.restrosmart.restrohotel.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class OrderModel  implements Parcelable {

    private int order_id,tableId;
    private  String time, OrderStatusName,custName, custMob,tableNo, orderMsg,orderTitle,custId;
    private ArrayList<AdminOrderModel> arrayList;

    protected OrderModel(Parcel in) {
        order_id = in.readInt();
        tableId = in.readInt();
        time = in.readString();
        OrderStatusName = in.readString();
        custName = in.readString();
        custMob = in.readString();
        tableNo = in.readString();
        orderMsg = in.readString();
        orderTitle = in.readString();
        custId = in.readString();
        arrayList = in.createTypedArrayList(AdminOrderModel.CREATOR);
    }

    public static final Creator<OrderModel> CREATOR = new Creator<OrderModel>() {
        @Override
        public OrderModel createFromParcel(Parcel in) {
            return new OrderModel(in);
        }

        @Override
        public OrderModel[] newArray(int size) {
            return new OrderModel[size];
        }
    };

    public String getOrderStatusName() {
        return OrderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        OrderStatusName = orderStatusName;
    }







    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public String getCustMob() {
        return custMob;
    }

    public void setCustMob(String custMob) {
        this.custMob = custMob;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<AdminOrderModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<AdminOrderModel> arrayList) {
        this.arrayList = arrayList;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }


    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getOrderMsg() {
        return orderMsg;
    }

    public void setOrderMsg(String orderMsg) {
        this.orderMsg = orderMsg;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public OrderModel() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(order_id);
        dest.writeInt(tableId);
        dest.writeString(time);
        dest.writeString(OrderStatusName);
        dest.writeString(custName);
        dest.writeString(custMob);
        dest.writeString(tableNo);
        dest.writeString(orderMsg);
        dest.writeString(orderTitle);
        dest.writeString(custId);
        dest.writeTypedList(arrayList);
    }
}
