package com.restrosmart.restrohotel.Captain.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CapOrderModel implements Parcelable {

    private int orderId;
    private String orderDate, orderStatus;
    private float subTotal;
    private ArrayList<CapMenuModel> capMenuModelArrayList;

    public CapOrderModel() {
    }

    protected CapOrderModel(Parcel in) {
        orderId = in.readInt();
        orderDate = in.readString();
        orderStatus = in.readString();
        subTotal = in.readFloat();
        capMenuModelArrayList = in.createTypedArrayList(CapMenuModel.CREATOR);
    }

    public static final Creator<CapOrderModel> CREATOR = new Creator<CapOrderModel>() {
        @Override
        public CapOrderModel createFromParcel(Parcel in) {
            return new CapOrderModel(in);
        }

        @Override
        public CapOrderModel[] newArray(int size) {
            return new CapOrderModel[size];
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public float getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }

    public ArrayList<CapMenuModel> getCapMenuModelArrayList() {
        return capMenuModelArrayList;
    }

    public void setCapMenuModelArrayList(ArrayList<CapMenuModel> capMenuModelArrayList) {
        this.capMenuModelArrayList = capMenuModelArrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(orderId);
        dest.writeString(orderDate);
        dest.writeString(orderStatus);
        dest.writeFloat(subTotal);
        dest.writeTypedList(capMenuModelArrayList);
    }
}
