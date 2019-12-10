package com.restrosmart.restrohotel.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ToppingsForm implements Parcelable {
    private  String toppingsName;
    private int toppingsPrice;
    private  int pcId;
    private  int toppingId;
    private  String image;
    private  int topQty;
    public boolean selected=false;

    protected ToppingsForm(Parcel in) {
        toppingsName = in.readString();
        toppingsPrice = in.readInt();
        pcId = in.readInt();
        toppingId = in.readInt();
        image = in.readString();
        topQty=in.readInt();
        selected = in.readByte() != 0;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ToppingsForm() {
    }

    public int getTopQty() {
        return topQty;
    }

    public void setTopQty(int topQty) {
        this.topQty = topQty;
    }

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
        parcel.writeString(image);
        parcel.writeInt(topQty);
        parcel.writeByte((byte) (selected ? 1 : 0));
    }


}
