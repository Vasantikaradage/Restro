package com.restrosmart.restrohotel.SuperAdmin.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class HotelForm implements Parcelable {
    private  String hotelName,hotelImg,hotelMob,hotelPhone,hotelEmail,hotelAddress
            ,hotelCountry,hotelState,hotelCity,hotelArea,hotelLatitude,hotelLongitude,gstnNo,
    hoteltype,hotelRating,hotelCgst,hotelSgst,hotelRegDate,hotelVisitCnt,hotelRanking,hotelStatus,hotelTableCount,
    hotelStartTime,hotelEndTime;

    private  int hotelId,superAdminId;


    private ArrayList<CuisineForm>  cuisineFormArrayList;
    private  ArrayList<TagsForm> tagsFormArrayList;
    private  ArrayList<HotelImageForm> hotelImageFormArrayList;

    public ArrayList<HotelImageForm> getHotelImageFormArrayList() {
        return hotelImageFormArrayList;
    }

    public HotelForm() {
    }

    protected HotelForm(Parcel in) {
        hotelName = in.readString();
        hotelImg = in.readString();
        hotelMob = in.readString();
        hotelPhone = in.readString();
        hotelEmail = in.readString();
        hotelAddress = in.readString();
        hotelCountry = in.readString();
        hotelState = in.readString();
        hotelCity = in.readString();
        hotelArea = in.readString();
        hotelLatitude = in.readString();
        hotelLongitude = in.readString();
        gstnNo = in.readString();
        hoteltype = in.readString();
        hotelRating = in.readString();
        hotelCgst = in.readString();
        hotelSgst = in.readString();
        hotelRegDate = in.readString();
        hotelVisitCnt = in.readString();
        hotelRanking = in.readString();
        hotelStatus = in.readString();
        hotelTableCount = in.readString();
        hotelStartTime = in.readString();
        hotelEndTime = in.readString();
        hotelId = in.readInt();
        superAdminId = in.readInt();
    }

    public static final Creator<HotelForm> CREATOR = new Creator<HotelForm>() {
        @Override
        public HotelForm createFromParcel(Parcel in) {
            return new HotelForm(in);
        }

        @Override
        public HotelForm[] newArray(int size) {
            return new HotelForm[size];
        }
    };

    public String getHotelTableCount() {
        return hotelTableCount;
    }

    public void setHotelTableCount(String hotelTableCount) {
        this.hotelTableCount = hotelTableCount;
    }

    public String getHotelStartTime() {
        return hotelStartTime;
    }

    public void setHotelStartTime(String hotelStartTime) {
        this.hotelStartTime = hotelStartTime;
    }

    public String getHotelEndTime() {
        return hotelEndTime;
    }

    public void setHotelEndTime(String hotelEndTime) {
        this.hotelEndTime = hotelEndTime;
    }





    public void setHotelImageFormArrayList(ArrayList<HotelImageForm> hotelImageFormArrayList) {
        this.hotelImageFormArrayList = hotelImageFormArrayList;
    }

    public ArrayList<CuisineForm> getCuisineFormArrayList() {
        return cuisineFormArrayList;
    }

    public void setCuisineFormArrayList(ArrayList<CuisineForm> cuisineFormArrayList) {
        this.cuisineFormArrayList = cuisineFormArrayList;
    }

    public ArrayList<TagsForm> getTagsFormArrayList() {
        return tagsFormArrayList;
    }

    public void setTagsFormArrayList(ArrayList<TagsForm> tagsFormArrayList) {
        this.tagsFormArrayList = tagsFormArrayList;
    }


    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelImg() {
        return hotelImg;
    }

    public void setHotelImg(String hotelImg) {
        this.hotelImg = hotelImg;
    }

    public String getHotelMob() {
        return hotelMob;
    }

    public void setHotelMob(String hotelMob) {
        this.hotelMob = hotelMob;
    }

    public String getHotelEmail() {
        return hotelEmail;
    }

    public void setHotelEmail(String hotelEmail) {
        this.hotelEmail = hotelEmail;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public String getHotelCountry() {
        return hotelCountry;
    }

    public void setHotelCountry(String hotelCountry) {
        this.hotelCountry = hotelCountry;
    }

    public String getHotelState() {
        return hotelState;
    }

    public void setHotelState(String hotelState) {
        this.hotelState = hotelState;
    }

    public String getHotelCity() {
        return hotelCity;
    }

    public void setHotelCity(String hotelCity) {
        this.hotelCity = hotelCity;
    }

    public String getHotelArea() {
        return hotelArea;
    }

    public void setHotelArea(String hotelArea) {
        this.hotelArea = hotelArea;
    }

    public String getHotelLatitude() {
        return hotelLatitude;
    }

    public void setHotelLatitude(String hotelLatitude) {
        this.hotelLatitude = hotelLatitude;
    }

    public String getHotelLongitude() {
        return hotelLongitude;
    }

    public void setHotelLongitude(String hotelLongitude) {
        this.hotelLongitude = hotelLongitude;
    }

    public String getGstnNo() {
        return gstnNo;
    }

    public void setGstnNo(String gstnNo) {
        this.gstnNo = gstnNo;
    }

    public String getHoteltype() {
        return hoteltype;
    }

    public void setHoteltype(String hoteltype) {
        this.hoteltype = hoteltype;
    }

    public String getHotelRating() {
        return hotelRating;
    }

    public void setHotelRating(String hotelRating) {
        this.hotelRating = hotelRating;
    }

    public String getHotelCgst() {
        return hotelCgst;
    }

    public void setHotelCgst(String hotelCgst) {
        this.hotelCgst = hotelCgst;
    }

    public String getHotelSgst() {
        return hotelSgst;
    }

    public void setHotelSgst(String hotelSgst) {
        this.hotelSgst = hotelSgst;
    }

    public String getHotelRegDate() {
        return hotelRegDate;
    }

    public void setHotelRegDate(String hotelRegDate) {
        this.hotelRegDate = hotelRegDate;
    }

    public String getHotelVisitCnt() {
        return hotelVisitCnt;
    }

    public void setHotelVisitCnt(String hotelVisitCnt) {
        this.hotelVisitCnt = hotelVisitCnt;
    }

    public String getHotelRanking() {
        return hotelRanking;
    }

    public void setHotelRanking(String hotelRanking) {
        this.hotelRanking = hotelRanking;
    }

    public String getHotelStatus() {
        return hotelStatus;
    }

    public void setHotelStatus(String hotelStatus) {
        this.hotelStatus = hotelStatus;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getSuperAdminId() {
        return superAdminId;
    }

    public void setSuperAdminId(int superAdminId) {
        this.superAdminId = superAdminId;
    }

    public String getHotelPhone() {
        return hotelPhone;
    }

    public void setHotelPhone(String hotelPhone) {
        this.hotelPhone = hotelPhone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hotelName);
        dest.writeString(hotelImg);
        dest.writeString(hotelMob);
        dest.writeString(hotelPhone);
        dest.writeString(hotelEmail);
        dest.writeString(hotelAddress);
        dest.writeString(hotelCountry);
        dest.writeString(hotelState);
        dest.writeString(hotelCity);
        dest.writeString(hotelArea);
        dest.writeString(hotelLatitude);
        dest.writeString(hotelLongitude);
        dest.writeString(gstnNo);
        dest.writeString(hoteltype);
        dest.writeString(hotelRating);
        dest.writeString(hotelCgst);
        dest.writeString(hotelSgst);
        dest.writeString(hotelRegDate);
        dest.writeString(hotelVisitCnt);
        dest.writeString(hotelRanking);
        dest.writeString(hotelStatus);
        dest.writeString(hotelTableCount);
        dest.writeString(hotelStartTime);
        dest.writeString(hotelEndTime);
        dest.writeInt(hotelId);
        dest.writeInt(superAdminId);
    }

    @Override
    public String toString() {
        return hotelName;
    }
}
