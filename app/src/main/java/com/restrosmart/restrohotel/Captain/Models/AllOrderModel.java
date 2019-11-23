package com.restrosmart.restrohotel.Captain.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class AllOrderModel implements Parcelable {

    private String CustId, CustName, CustMob;
    private int tableNo;
    private ArrayList<CapOrderModel> capOrderModelArrayList;

    public AllOrderModel() {
    }

    protected AllOrderModel(Parcel in) {
        CustId = in.readString();
        CustName = in.readString();
        CustMob = in.readString();
        tableNo = in.readInt();
        capOrderModelArrayList = in.createTypedArrayList(CapOrderModel.CREATOR);
    }

    public static final Creator<AllOrderModel> CREATOR = new Creator<AllOrderModel>() {
        @Override
        public AllOrderModel createFromParcel(Parcel in) {
            return new AllOrderModel(in);
        }

        @Override
        public AllOrderModel[] newArray(int size) {
            return new AllOrderModel[size];
        }
    };

    public String getCustId() {
        return CustId;
    }

    public void setCustId(String custId) {
        CustId = custId;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        CustName = custName;
    }

    public String getCustMob() {
        return CustMob;
    }

    public void setCustMob(String custMob) {
        CustMob = custMob;
    }

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    public ArrayList<CapOrderModel> getCapOrderModelArrayList() {
        return capOrderModelArrayList;
    }

    public void setCapOrderModelArrayList(ArrayList<CapOrderModel> capOrderModelArrayList) {
        this.capOrderModelArrayList = capOrderModelArrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(CustId);
        dest.writeString(CustName);
        dest.writeString(CustMob);
        dest.writeInt(tableNo);
        dest.writeTypedList(capOrderModelArrayList);
    }
}
