package com.restrosmart.restrohotel.Captain.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class OrderModel implements Parcelable {

    private int order_id, tableId;
    private String custName, custMobNo, time;
    private ArrayList<MenuModel> arrayList;

    public OrderModel(Parcel in) {
        order_id = in.readInt();
        tableId = in.readInt();
        custName = in.readString();
        custMobNo = in.readString();
        time = in.readString();
    }

    public OrderModel() {
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

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustMobNo() {
        return custMobNo;
    }

    public void setCustMobNo(String custMobNo) {
        this.custMobNo = custMobNo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<MenuModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<MenuModel> arrayList) {
        this.arrayList = arrayList;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(order_id);
        dest.writeInt(tableId);
        dest.writeString(custName);
        dest.writeString(custMobNo);
        dest.writeString(time);
    }
}
