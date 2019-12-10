package com.restrosmart.restrohotel.Model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.restrosmart.restrohotel.Captain.Models.CapMenuModel;

import java.util.ArrayList;


public class AdminOrderModel implements Parcelable {
    private int orderId;
    private String orderDate;
    private ArrayList<MenuForm> adminMenuModelArrayList;

    public AdminOrderModel() {
    }

    protected AdminOrderModel(Parcel in) {
        orderId = in.readInt();
        orderDate = in.readString();
        adminMenuModelArrayList = in.createTypedArrayList(MenuForm.CREATOR);
    }

    public static final Creator<AdminOrderModel> CREATOR = new Creator<AdminOrderModel>() {
        @Override
        public AdminOrderModel createFromParcel(Parcel in) {
            return new AdminOrderModel(in);
        }

        @Override
        public AdminOrderModel[] newArray(int size) {
            return new AdminOrderModel[size];
        }
    };

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public ArrayList<MenuForm> getAdminMenuModelArrayList() {
        return adminMenuModelArrayList;
    }

    public void setAdminMenuModelArrayList(ArrayList<MenuForm> adminMenuModelArrayList) {
        this.adminMenuModelArrayList = adminMenuModelArrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(orderId);
        dest.writeString(orderDate);
        dest.writeTypedList(adminMenuModelArrayList);
    }
}
