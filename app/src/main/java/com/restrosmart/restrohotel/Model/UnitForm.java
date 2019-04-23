package com.restrosmart.restrohotel.Model;

import android.widget.EditText;

public class UnitForm {
    private  int id;
    private EditText unitName;
    private  EditText unitPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EditText getUnitName() {
        return unitName;
    }

    public void setUnitName(EditText unitName) {
        this.unitName = unitName;
    }

    public EditText getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(EditText unitPrice) {
        this.unitPrice = unitPrice;
    }
}
