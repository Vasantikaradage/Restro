package com.restrosmart.restrohotel.Model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class TableForm implements Parcelable {
    private  int areaId,tableCount;
    private  String areaName;
    private  int Area_Status;
    private ArrayList<TableFormId> arrayTableFormIds;

    public TableForm() {
    }

    public TableForm(Parcel in) {
        areaId = in.readInt();
        tableCount = in.readInt();
        areaName = in.readString();
        Area_Status = in.readInt();
    }

    public static final Creator<TableForm> CREATOR = new Creator<TableForm>() {
        @Override
        public TableForm createFromParcel(Parcel in) {
            return new TableForm(in);
        }

        @Override
        public TableForm[] newArray(int size) {
            return new TableForm[size];
        }
    };

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

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(areaId);
        parcel.writeInt(tableCount);
        parcel.writeString(areaName);
        parcel.writeInt(Area_Status);
    }

    @Override
    public String toString() {
        return areaName;
    }
}
