package com.restrosmart.restrohotel.SuperAdmin.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class HotelImageForm implements Parcelable {

    private  int hotelImageId;
    private  String hotelImageName;

    public HotelImageForm() {
    }

    protected HotelImageForm(Parcel in) {
        hotelImageId = in.readInt();
        hotelImageName = in.readString();
    }

    public static final Creator<HotelImageForm> CREATOR = new Creator<HotelImageForm>() {
        @Override
        public HotelImageForm createFromParcel(Parcel in) {
            return new HotelImageForm(in);
        }

        @Override
        public HotelImageForm[] newArray(int size) {
            return new HotelImageForm[size];
        }
    };

    public int getHotelImageId() {
        return hotelImageId;
    }

    public void setHotelImageId(int hotelImageId) {
        this.hotelImageId = hotelImageId;
    }

    public String getHotelImageName() {
        return hotelImageName;
    }

    public void setHotelImageName(String hotelImageName) {
        this.hotelImageName = hotelImageName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(hotelImageId);
        dest.writeString(hotelImageName);
    }
}
