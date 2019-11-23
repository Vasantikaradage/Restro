package com.restrosmart.restrohotel.Captain.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CapMenuModel implements Parcelable {

    private int orderDetailId, menuQty;
    private String menuId, menuName, liqMLQty;
    private float menuPrice;
    private ArrayList<ToppingsModel> toppingsModelArrayList;

    public CapMenuModel() {
    }

    protected CapMenuModel(Parcel in) {
        orderDetailId = in.readInt();
        menuId = in.readString();
        liqMLQty = in.readString();
        menuQty = in.readInt();
        menuName = in.readString();
        menuPrice = in.readFloat();
        toppingsModelArrayList = in.createTypedArrayList(ToppingsModel.CREATOR);
    }

    public static final Creator<CapMenuModel> CREATOR = new Creator<CapMenuModel>() {
        @Override
        public CapMenuModel createFromParcel(Parcel in) {
            return new CapMenuModel(in);
        }

        @Override
        public CapMenuModel[] newArray(int size) {
            return new CapMenuModel[size];
        }
    };

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getLiqMLQty() {
        return liqMLQty;
    }

    public void setLiqMLQty(String liqMLQty) {
        this.liqMLQty = liqMLQty;
    }

    public int getMenuQty() {
        return menuQty;
    }

    public void setMenuQty(int menuQty) {
        this.menuQty = menuQty;
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

    public ArrayList<ToppingsModel> getToppingsModelArrayList() {
        return toppingsModelArrayList;
    }

    public void setToppingsModelArrayList(ArrayList<ToppingsModel> toppingsModelArrayList) {
        this.toppingsModelArrayList = toppingsModelArrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(orderDetailId);
        dest.writeString(menuId);
        dest.writeString(liqMLQty);
        dest.writeInt(menuQty);
        dest.writeString(menuName);
        dest.writeFloat(menuPrice);
        dest.writeTypedList(toppingsModelArrayList);
    }
}
