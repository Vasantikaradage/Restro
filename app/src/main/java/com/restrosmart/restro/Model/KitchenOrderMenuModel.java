package com.restrosmart.restro.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class KitchenOrderMenuModel implements Parcelable {

    private String orderMenuName;
    private int orderMenuQty;

    public KitchenOrderMenuModel() {
    }

    protected KitchenOrderMenuModel(Parcel in) {
        orderMenuName = in.readString();
        orderMenuQty = in.readInt();
    }

    public static final Creator<KitchenOrderMenuModel> CREATOR = new Creator<KitchenOrderMenuModel>() {
        @Override
        public KitchenOrderMenuModel createFromParcel(Parcel in) {
            return new KitchenOrderMenuModel(in);
        }

        @Override
        public KitchenOrderMenuModel[] newArray(int size) {
            return new KitchenOrderMenuModel[size];
        }
    };

    public String getOrderMenuName() {
        return orderMenuName;
    }

    public void setOrderMenuName(String orderMenuName) {
        this.orderMenuName = orderMenuName;
    }

    public int getOrderMenuQty() {
        return orderMenuQty;
    }

    public void setOrderMenuQty(int orderMenuQty) {
        this.orderMenuQty = orderMenuQty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(orderMenuName);
        parcel.writeInt(orderMenuQty);
    }
}
