package com.restrosmart.restrohotel.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class FreeTables implements Parcelable {
    private int tableId, tableNo;

    public FreeTables() {
    }

    private FreeTables(Parcel in) {
        tableId = in.readInt();
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    @Override
    public String toString() {
        return String.valueOf(tableNo);
    }

    public static final Creator<FreeTables> CREATOR = new Creator<FreeTables>() {
        @Override
        public FreeTables createFromParcel(Parcel in) {
            return new FreeTables(in);
        }

        @Override
        public FreeTables[] newArray(int size) {
            return new FreeTables[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tableId);
    }
}
