package com.restrosmart.restrohotel.SuperAdmin.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class CuisineForm implements Parcelable {
    private  int cuisineId;
    private  String cuisineName;
    public boolean selected = false;

    public CuisineForm() {
    }

    protected CuisineForm(Parcel in) {
        cuisineId = in.readInt();
        cuisineName = in.readString();
        selected = in.readByte() != 0;
    }

    public static final Creator<CuisineForm> CREATOR = new Creator<CuisineForm>() {
        @Override
        public CuisineForm createFromParcel(Parcel in) {
            return new CuisineForm(in);
        }

        @Override
        public CuisineForm[] newArray(int size) {
            return new CuisineForm[size];
        }
    };

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getCuisineId() {
        return cuisineId;
    }

    public void setCuisineId(int cuisineId) {
        this.cuisineId = cuisineId;
    }

    public String getCuisineName() {
        return cuisineName;
    }

    public void setCuisineName(String cuisineName) {
        this.cuisineName = cuisineName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cuisineId);
        dest.writeString(cuisineName);
        dest.writeByte((byte) (selected ? 1 : 0));
    }
}
