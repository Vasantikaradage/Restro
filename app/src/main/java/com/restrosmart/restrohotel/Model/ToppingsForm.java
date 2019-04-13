package com.restrosmart.restrohotel.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ToppingsForm implements Parcelable {
    private  String toppingsName;
    private int toppingsPrice;
    private  int pcId;
    private  int toppingId;

    public ToppingsForm() {
    }

    public ToppingsForm(Parcel in) {
        toppingsName = in.readString();
        toppingsPrice = in.readInt();
        pcId = in.readInt();
        toppingId=in.readInt();
    }

    public static final Creator<ToppingsForm> CREATOR = new Creator<ToppingsForm>() {
        @Override
        public ToppingsForm createFromParcel(Parcel in) {
            return new ToppingsForm(in);
        }

        @Override
        public ToppingsForm[] newArray(int size) {
            return new ToppingsForm[size];
        }
    };

    public String getToppingsName() {
        return toppingsName;
    }

    public void setToppingsName(String toppingsName) {
        this.toppingsName = toppingsName;
    }

    public int getToppingsPrice() {
        return toppingsPrice;
    }

    public void setToppingsPrice(int toppingsPrice) {
        this.toppingsPrice = toppingsPrice;
    }

    public int getPcId() {
        return pcId;
    }

    public void setPcId(int pcId) {
        this.pcId = pcId;
    }

    public int getToppingId() {
        return toppingId;
    }

    public void setToppingId(int toppingId) {
        this.toppingId = toppingId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(toppingsName);
        parcel.writeInt(toppingsPrice);
        parcel.writeInt(pcId);
        parcel.writeInt(toppingId);
    }
}
