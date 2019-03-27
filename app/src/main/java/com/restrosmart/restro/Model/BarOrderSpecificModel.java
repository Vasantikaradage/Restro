package com.restrosmart.restro.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class BarOrderSpecificModel implements Parcelable {

    private String BarOrderName;
    private int BarOrderQty;

    public BarOrderSpecificModel() {
    }

    protected BarOrderSpecificModel(Parcel in) {
        BarOrderName = in.readString();
        BarOrderQty = in.readInt();
    }

    public static final Creator<BarOrderSpecificModel> CREATOR = new Creator<BarOrderSpecificModel>() {
        @Override
        public BarOrderSpecificModel createFromParcel(Parcel in) {
            return new BarOrderSpecificModel(in);
        }

        @Override
        public BarOrderSpecificModel[] newArray(int size) {
            return new BarOrderSpecificModel[size];
        }
    };

    public String getBarOrderName() {
        return BarOrderName;
    }

    public void setBarOrderName(String barOrderName) {
        BarOrderName = barOrderName;
    }

    public int getBarOrderQty() {
        return BarOrderQty;
    }

    public void setBarOrderQty(int barOrderQty) {
        BarOrderQty = barOrderQty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(BarOrderName);
        parcel.writeInt(BarOrderQty);
    }
}
