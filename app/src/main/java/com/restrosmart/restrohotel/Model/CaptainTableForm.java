package com.restrosmart.restrohotel.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CaptainTableForm implements Parcelable {

    private  int areaId,tableCount;
    private  String captainName;
    private  String Area_Name;
    private  int Area_Status;
    private  int captainId;

    private ArrayList<TableFormId> arrayTableFormIds;

    public String getArea_Name() {
        return Area_Name;
    }

    public void setArea_Name(String area_Name) {
        Area_Name = area_Name;
    }

    public CaptainTableForm() {
    }

    protected CaptainTableForm(Parcel in) {
        areaId = in.readInt();
        tableCount = in.readInt();
        captainName = in.readString();
        Area_Status = in.readInt();
        Area_Name=in.readString();
        captainId=in.readInt();
        arrayTableFormIds = in.createTypedArrayList(TableFormId.CREATOR);
    }

    public static final Creator<CaptainTableForm> CREATOR = new Creator<CaptainTableForm>() {
        @Override
        public CaptainTableForm createFromParcel(Parcel in) {
            return new CaptainTableForm(in);
        }

        @Override
        public CaptainTableForm[] newArray(int size) {
            return new CaptainTableForm[size];
        }
    };

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getTableCount() {
        return tableCount;
    }

    public void setTableCount(int tableCount) {
        this.tableCount = tableCount;
    }

    public String getCaptainName() {
        return captainName;
    }

    public void setCaptainName(String captainName) {
        this.captainName = captainName;
    }

    public int getArea_Status() {
        return Area_Status;
    }

    public void setArea_Status(int area_Status) {
        Area_Status = area_Status;
    }

    public ArrayList<TableFormId> getArrayTableFormIds() {
        return arrayTableFormIds;
    }

    public void setArrayTableFormIds(ArrayList<TableFormId> arrayTableFormIds) {
        this.arrayTableFormIds = arrayTableFormIds;
    }

    public int getCaptainId() {
        return captainId;
    }

    public void setCaptainId(int captainId) {
        this.captainId = captainId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(areaId);
        parcel.writeInt(tableCount);
        parcel.writeString(captainName);
        parcel.writeInt(Area_Status);
        parcel.writeTypedList(arrayTableFormIds);
        parcel.writeString(Area_Name);
        parcel.writeInt(captainId);
    }
}
