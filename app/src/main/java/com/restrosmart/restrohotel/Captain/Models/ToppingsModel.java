package com.restrosmart.restrohotel.Captain.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class ToppingsModel implements Parcelable {

    private int toppingsId;
    private String toppingsName;
    private float toppingsPrice;

    public ToppingsModel(Parcel in) {
        toppingsId = in.readInt();
        toppingsName = in.readString();
        toppingsPrice = in.readFloat();
    }

    public ToppingsModel() {
    }

    public static final Creator<ToppingsModel> CREATOR = new Creator<ToppingsModel>() {
        @Override
        public ToppingsModel createFromParcel(Parcel in) {
            return new ToppingsModel(in);
        }

        @Override
        public ToppingsModel[] newArray(int size) {
            return new ToppingsModel[size];
        }
    };

    public int getToppingsId() {
        return toppingsId;
    }

    public void setToppingsId(int toppingsId) {
        this.toppingsId = toppingsId;
    }

    public String getToppingsName() {
        return toppingsName;
    }

    public void setToppingsName(String toppingsName) {
        this.toppingsName = toppingsName;
    }

    public float getToppingsPrice() {
        return toppingsPrice;
    }

    public void setToppingsPrice(float toppingsPrice) {
        this.toppingsPrice = toppingsPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(toppingsId);
        dest.writeString(toppingsName);
        dest.writeFloat(toppingsPrice);
    }
}
