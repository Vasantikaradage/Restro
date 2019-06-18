package com.restrosmart.restrohotel.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class OrderModel  implements Parcelable {

    private int  cust_mob_no,order_id,tableId;
    private  String time,Order_Status_Name;
    private ArrayList<MenuForm> arrayList;

    public String getOrder_Status_Name() {
        return Order_Status_Name;
    }

    public void setOrder_Status_Name(String order_Status_Name) {
        Order_Status_Name = order_Status_Name;
    }

    protected OrderModel(Parcel in) {
        cust_mob_no = in.readInt();
        order_id = in.readInt();
        tableId = in.readInt();
        Order_Status_Name = in.readString();
        time = in.readString();
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





    public int getCust_mob_no() {
        return cust_mob_no;
    }

    public void setCust_mob_no(int cust_mob_no) {
        this.cust_mob_no = cust_mob_no;
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

    public ArrayList<MenuForm> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<MenuForm> arrayList) {
        this.arrayList = arrayList;
    }

    public OrderModel() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(cust_mob_no);
        parcel.writeInt(order_id);
        parcel.writeInt(tableId);
        parcel.writeString(Order_Status_Name);
        parcel.writeString(time);
    }
}
