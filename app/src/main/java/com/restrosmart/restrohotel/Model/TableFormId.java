package com.restrosmart.restrohotel.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class TableFormId  implements Parcelable {
    private  int tableId;
    private  int tableStatus;
    private  int tableNo;

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public TableFormId() {
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getTableStatus() {
        return tableStatus;
    }

    public void setTableStatus(int tableStatus) {
        this.tableStatus = tableStatus;
    }

    protected TableFormId(Parcel in) {
        tableId = in.readInt();
        tableStatus = in.readInt();
        tableNo=in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tableId);
        dest.writeInt(tableStatus);
        dest.writeInt(tableNo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TableFormId> CREATOR = new Creator<TableFormId>() {
        @Override
        public TableFormId createFromParcel(Parcel in) {
            return new TableFormId(in);
        }

        @Override
        public TableFormId[] newArray(int size) {
            return new TableFormId[size];
        }
    };
}
