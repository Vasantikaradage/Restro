package com.restrosmart.restrohotel.Captain.Models;

public class ScanTableModel {

    private int tableId, tableNo;
    private String custName, custMob;

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

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustMob() {
        return custMob;
    }

    public void setCustMob(String custMob) {
        this.custMob = custMob;
    }
}
